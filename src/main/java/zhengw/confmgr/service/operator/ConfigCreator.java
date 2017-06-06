package zhengw.confmgr.service.operator;

import org.springframework.beans.factory.annotation.Autowired;

import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.ConfigRepository;

public class ConfigCreator extends BaseOperator {

	private ConfigRepository configRepository;

	@Autowired
	public void setConfigRepository(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	public ConfigCreator(User optUser) {
		super(optUser);
	}

	@Override
	protected Tuple<Boolean, String> CheckDataCore() {

		if (this.configRepository == null) {
			System.out.println("ConfigRepository is null");
		} else {
			System.out.println("ConfigRepository is not null");
		}

		System.out.println("CheckDataCore");
		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> OperateCore() {
		System.out.println("OperateCore");
		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> RecordLog() {
		return super.successResult();
	}

}
