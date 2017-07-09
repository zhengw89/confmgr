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

public class ConfigDeletor extends BaseOperator {

	private final int configId;
	private Config toDeleteConfig;

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

	protected ConfigDeletor(int configId, User optUser) {
		super(optUser);

		this.configId = configId;
	}

	@Override
	protected Tuple<Boolean, String> CheckDataCore() {

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> OperateCore() {

		this.toDeleteConfig = this.configRepository.findOne(this.configId);
		if (this.toDeleteConfig != null) {
			this.configRepository.delete(this.configId);
		}

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> RecordLog() {

		if (this.toDeleteConfig != null) {
			ConfigLog log = new ConfigLog();
			log.setAfterValue(null);
			log.setBeforeValue(this.toDeleteConfig.getValue());
			log.setConfigId(this.configId);
			log.setEmail(super.getOptUser().getEmail());
			log.setOptTime(new Date());
			log.setOptType(OptType.Delete);
			log.setUserId(super.getOptUser().getId());

			this.configLogRepository.save(log);

			if (log.getId() <= 0) {
				return super.failResult("配置操作日志记录失败");
			}
		}

		return super.successResult();
	}

}
