package zhengw.confmgr.bean;

import org.springframework.util.StringUtils;

public class CreateConfigRequest extends BaseRequestBean {

	private int appId;

	private int envId;

	private String name;

	private String value;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CreateConfigRequest() {

	}

	public CreateConfigRequest(int appId, int envId) {
		this.appId = appId;
		this.envId = envId;
	}

	@Override
	protected boolean validateModel() {

		if (StringUtils.isEmpty(this.name) || StringUtils.isEmpty(this.value))
			return false;

		return true;
	}

	@Override
	protected String modelInvalidMsg() {

		if (StringUtils.isEmpty(this.name))
			return "名称为空";
		if (StringUtils.isEmpty(this.value))
			return "配置值为空";

		return null;
	}

}
