package zhengw.confmgr.service.operator;

import java.util.Date;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.AppLog;
import zhengw.confmgr.bean.OptType;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.AppLogRepository;
import zhengw.confmgr.repository.AppRepository;
import zhengw.confmgr.repository.ConfigRepository;

public class AppDeleter extends BaseOperator {

	private final String appName;
	private App appToDelete;

	private BeanFactory beanFactory;

	private ZkOperator zkOperator;

	private AppRepository appRepository;
	private AppLogRepository appLogRepository;
	private ConfigRepository configRepository;

	@Autowired
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Autowired
	public void setZkOperator(ZkOperator zkOperator) {
		this.zkOperator = zkOperator;
	}

	@Autowired
	public void setAppRepository(AppRepository appRepository) {
		this.appRepository = appRepository;
	}

	@Autowired
	public void setAppLogRepository(AppLogRepository appLogRepository) {
		this.appLogRepository = appLogRepository;
	}

	@Autowired
	public void setConfigRepository(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	protected AppDeleter(String appName, User optUser) {
		super(true, optUser);

		this.appName = appName;
	}

	@Override
	protected Tuple<Boolean, String> CheckDataCore() {

		if (StringUtils.isEmpty(this.appName)) {
			return super.failResult("名称为空");
		}

		if (!this.appRepository.exists(this.appName)) {
			return super.failResult("不存在相应应用");
		}

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> OperateCore() {

		this.appToDelete = this.appRepository.findByName(this.appName);

		if (this.appToDelete == null) {
			return super.failResult("不存在相应应用");
		}

		List<Integer> configIds = this.configRepository.findConfigIdsByAppId(this.appToDelete.getId());
		for (Integer configId : configIds) {

			ConfigDeletor configDeletor = this.beanFactory.getBean(ConfigDeletor.class, configId, false, super.getOptUser());
			Tuple<Boolean, String> configDeleteResult = configDeletor.Operate();
			if (!configDeleteResult.getItem1()) {
				return configDeleteResult;
			}

		}
		
		this.appRepository.delete(this.appToDelete.getId());

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> ZkUpdateCore(CuratorFramework client) throws Exception {

		this.zkOperator.deleteApp(client, this.appName);

		return super.ZkUpdateCore(client);
	}

	@Override
	protected Tuple<Boolean, String> RecordLog() {

		AppLog log = new AppLog();
		log.setAppId(this.appToDelete.getId());
		log.setAppName(this.appName);
		log.setEmail(super.getOptUser().getEmail());
		log.setOptTime(new Date());
		log.setOptType(OptType.Create);
		log.setUserId(super.getOptUser().getId());

		this.appLogRepository.save(log);

		return super.successResult();
	}

}
