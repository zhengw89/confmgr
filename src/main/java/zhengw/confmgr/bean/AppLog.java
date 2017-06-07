package zhengw.confmgr.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AppLog extends BaseLog {
	
	@Column
	private int appId;
	
	@Column
	private String appName;

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
	
}
