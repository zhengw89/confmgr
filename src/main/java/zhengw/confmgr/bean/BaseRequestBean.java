package zhengw.confmgr.bean;

public abstract class BaseRequestBean {

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
