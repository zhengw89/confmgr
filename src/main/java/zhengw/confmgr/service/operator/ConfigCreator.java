package zhengw.confmgr.service.operator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.ConfigLog;
import zhengw.confmgr.bean.OptType;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.AppRepository;
import zhengw.confmgr.repository.ConfigLogRepository;
import zhengw.confmgr.repository.ConfigRepository;
import zhengw.confmgr.repository.EnvRepository;

public class ConfigCreator extends BaseOperator {

	private final int appId, envId;
	private final String name, value;

	private AppRepository appRepository;
	private EnvRepository envRepository;
	private ConfigRepository configRepository;
	private ConfigLogRepository configLogRepository;

	private Config configToCreate;

	@Autowired
	public void setAppRepository(AppRepository appRepository) {
		this.appRepository = appRepository;
	}

	@Autowired
	public void setEnvRepository(EnvRepository envRepository) {
		this.envRepository = envRepository;
	}

	@Autowired
	public void setConfigRepository(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	@Autowired
	public void setConfigLogRepository(ConfigLogRepository configLogRepository) {
		this.configLogRepository = configLogRepository;
	}

	public ConfigCreator(int appId, int envId, String name, String value, User optUser) {
		super(optUser);

		this.appId = appId;
		this.envId = envId;
		this.name = name;
		this.value = value;
	}

	@Override
	protected Tuple<Boolean, String> CheckDataCore() {

		if (StringUtils.isEmpty(this.name)) {
			return super.failResult("配置名称为空");
		}

		if (this.value == null) {
			return super.failResult("配置值为空");
		}

		if (!this.appRepository.exists(this.appId)) {
			return super.failResult("不存在该应用");
		}

		if (!this.envRepository.exists(this.envId)) {
			return super.failResult("不存在该环境");
		}

		if (this.configRepository.exists(this.appId, this.envId, this.name)) {
			return super.failResult("已存在同名配置");
		}

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> OperateCore() {

		configToCreate = new Config();
		configToCreate.setAppId(this.appId);
		configToCreate.setCreateTime(new Date());
		configToCreate.setEnvId(this.envId);
		configToCreate.setName(this.name);
		configToCreate.setValue(this.value);

		this.configRepository.save(configToCreate);

		if (configToCreate.getId() <= 0) {
			return super.failResult("配置创建失败");
		}

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> RecordLog() {

		ConfigLog log = new ConfigLog();
		log.setAfterValue(this.configToCreate.getValue());
		log.setConfigId(this.configToCreate.getId());
		log.setEmail(super.getOptUser().getEmail());
		log.setOptTime(new Date());
		log.setOptType(OptType.Create);
		log.setUserId(super.getOptUser().getId());

		this.configLogRepository.save(log);

		if (log.getId() <= 0) {
			return super.failResult("配置操作日志记录失败");
		}

		return super.successResult();
	}

}
