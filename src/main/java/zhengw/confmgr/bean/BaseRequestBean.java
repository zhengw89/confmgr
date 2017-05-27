package zhengw.confmgr.bean;

public abstract class BaseRequestBean {

//	private String errorMsg;
//
//	public String getErrorMsg() {
//		return errorMsg;
//	}
//
//	public void setErrorMsg(String errorMsg) {
//		this.errorMsg = errorMsg;
//	}

	public boolean isValid() {
		if (validateModel())
			return true;
		return false;
	}

	public String getInvalidMsg() {
		return modelInvalidMsg();
	}

	protected abstract boolean validateModel();

	protected abstract String modelInvalidMsg();
}
