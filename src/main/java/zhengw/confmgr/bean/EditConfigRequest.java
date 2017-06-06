package zhengw.confmgr.bean;

import org.springframework.util.StringUtils;

public class EditConfigRequest extends BaseRequestBean {

	private int appId;

	private int envId;

	private int configId;

	private String newValue;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getEnvId() {
		return envId;
	}

	public void setEnvId(int envId) {
		this.envId = envId;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public EditConfigRequest() {
	}

	public EditConfigRequest(int appId, int envId, int configId) {
		this.appId = appId;
		this.envId = envId;
		this.configId = configId;
	}

	@Override
	protected boolean validateModel() {

		if (StringUtils.isEmpty(this.newValue))
			return false;

		return true;
	}

	@Override
	protected String modelInvalidMsg() {

		if (StringUtils.isEmpty(this.newValue))
			return "新配置值为空";

		return null;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append(String.format("AppId: %d;", this.appId));
		sb.append(String.format("EnvId: %d;", this.envId));
		sb.append(String.format("ConfigId: %d;", this.configId));
		sb.append(String.format("NewValue: %s", this.newValue));

		return sb.toString();
	}

}
