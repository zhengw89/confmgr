package zhengw.confmgr.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ConfigLog extends BaseLog {

	@Column
	private int appId;

	@Column
	private String appName;

	@Column
	private int envId;

	@Column
	private String envName;

	@Column
	private int configId;

	@Column
	private String configName;

	@Column
	private String beforeValue;

	@Column
	private String afterValue;

	public int getConfigId() {
		return configId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getEnvId() {
		return envId;
	}

	public void setEnvId(int envId) {
		this.envId = envId;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getBeforeValue() {
		return beforeValue;
	}

	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}

	public String getAfterValue() {
		return afterValue;
	}

	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public ConfigLog(String appName, String envName, Config config) {

		this.appId = config.getAppId();
		this.appName = appName;

		this.envId = config.getEnvId();
		this.envName = envName;

		this.configId = config.getId();
		this.configName = config.getName();
	}

//	public ConfigLog() {
//	}
}
