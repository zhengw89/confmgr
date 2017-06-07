package zhengw.confmgr.service.operator;

import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;

public abstract class BaseOperator {

	private final User optUser;

	protected User getOptUser() {
		return this.optUser;
	}

	protected BaseOperator(User optUser) {
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

		return RecordLog();
	}

	protected abstract Tuple<Boolean, String> CheckDataCore();

	protected abstract Tuple<Boolean, String> OperateCore();

	protected abstract Tuple<Boolean, String> RecordLog();

	protected final Tuple<Boolean, String> successResult() {
		return Tuple.create(true, null);
	}

	protected final Tuple<Boolean, String> failResult(String msg) {
		return Tuple.create(false, msg);
	}
}
