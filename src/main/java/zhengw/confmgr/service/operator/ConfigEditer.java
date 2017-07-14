package zhengw.confmgr.service.operator;

import java.util.Date;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.ConfigLog;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.bean.OptType;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.AppRepository;
import zhengw.confmgr.repository.ConfigLogRepository;
import zhengw.confmgr.repository.ConfigRepository;
import zhengw.confmgr.repository.EnvRepository;

public class ConfigEditer extends BaseOperator {

	private final int appId, envId, configId;
	private final String value;
	private String beforeValue;

	private ZkOperator zkOperator;

	private AppRepository appRepository;
	private EnvRepository envRepository;
	private ConfigRepository configRepository;
	private ConfigLogRepository configLogRepository;

	@Autowired
	public void setZkOperator(ZkOperator zkOperator) {
		this.zkOperator = zkOperator;
	}

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

	protected ConfigEditer(int appId, int envId, int configId, String value, User optUser) {
		super(true, optUser);

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
	protected Tuple<Boolean, String> ZkUpdateCore(CuratorFramework client) throws Exception {

		App app = this.appRepository.findOne(this.appId);
		Env env = this.envRepository.findOne(this.envId);
		Config config = configRepository.findById(appId, envId, configId);

		if (app != null && env != null && config != null) {
			this.zkOperator.createOrUpdateConfig(client, app.getName(), env.getName(), config.getName());
		}

		return super.ZkUpdateCore(client);
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
