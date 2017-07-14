package zhengw.confmgr.service.operator;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;

import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.config.AppConfig;
import zhengw.confmgr.utility.zk.ZkUtility;

public abstract class BaseOperator {

	private final boolean zkUpdate;
	private final User optUser;

	protected User getOptUser() {
		return this.optUser;
	}

	private AppConfig appConfig;

	@Autowired
	public void setAppConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}

	protected BaseOperator(User optUser) {
		this(false, optUser);
	}

	protected BaseOperator(Boolean zkUpdate, User optUser) {
		this.zkUpdate = zkUpdate;
		this.optUser = optUser;
	}

	public final Tuple<Boolean, String> Operate() {

		if (optUser == null)
			return this.failResult("操作用户为空");

		Tuple<Boolean, String> checkResult = CheckDataCore();
		if (!checkResult.getItem1())
			return checkResult;

		Tuple<Boolean, String> optResult = OperateCore();
		if (!optResult.getItem1())
			return optResult;

		if (zkUpdate) {
			Tuple<Boolean, String> zkResult = ZkUpdate();
			if (!zkResult.getItem1())
				return zkResult;
		}

		return RecordLog();
	}

	protected abstract Tuple<Boolean, String> CheckDataCore();

	protected abstract Tuple<Boolean, String> OperateCore();

	private Tuple<Boolean, String> ZkUpdate() {

		CuratorFramework client = ZkUtility.getClient(appConfig.getConnectionString(), appConfig.getTimeout(), appConfig.getRetry());
		try {
			client = ZkUtility.getClient(appConfig.getConnectionString(), appConfig.getTimeout(), appConfig.getRetry());
			return ZkUpdateCore(client);
		} catch (Exception ex) {
			return this.failResult(String.format("Zk异常: %s", ex.getMessage()));
		} finally {
			if (client != null)
				ZkUtility.close(client);
		}
	}

	protected Tuple<Boolean, String> ZkUpdateCore(CuratorFramework client) throws Exception {
		return this.successResult();
	}

	protected abstract Tuple<Boolean, String> RecordLog();

	protected final Tuple<Boolean, String> successResult() {
		return Tuple.create(true, null);
	}

	protected final Tuple<Boolean, String> failResult(String msg) {
		return Tuple.create(false, msg);
	}
}
