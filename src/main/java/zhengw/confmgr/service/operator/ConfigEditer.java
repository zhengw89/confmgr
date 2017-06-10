package zhengw.confmgr.service.operator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.ConfigLog;
import zhengw.confmgr.bean.OptType;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.ConfigLogRepository;
import zhengw.confmgr.repository.ConfigRepository;

public class ConfigEditer extends BaseOperator {

	private final int appId, envId, configId;
	private final String value;
	private String beforeValue;

	private ConfigRepository configRepository;
	private ConfigLogRepository configLogRepository;

	@Autowired
	public void setConfigRepository(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	@Autowired
	public void setConfigLogRepository(ConfigLogRepository configLogRepository) {
		this.configLogRepository = configLogRepository;
	}

	protected ConfigEditer(int appId, int envId, int configId, String value, User optUser) {
		super(optUser);

		this.appId = appId;
		this.envId = envId;
		this.configId = configId;
		this.value = value;
	}

	@Override
	protected Tuple<Boolean, String> CheckDataCore() {

		if (this.value == null)
			return super.failResult("配置值为空");

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> OperateCore() {

		Config config = configRepository.findById(appId, envId, configId);
		if (config == null) {
			return Tuple.create(false, "配置不存在");
		}
		this.beforeValue = config.getValue();
		config.setValue(this.value);
		this.configRepository.save(config);

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> RecordLog() {

		ConfigLog log = new ConfigLog();
		log.setAfterValue(this.value);
		log.setBeforeValue(this.beforeValue);
		log.setConfigId(this.configId);
		log.setEmail(super.getOptUser().getEmail());
		log.setOptTime(new Date());
		log.setOptType(OptType.Update);
		log.setUserId(super.getOptUser().getId());

		this.configLogRepository.save(log);

		return super.successResult();
	}

}
