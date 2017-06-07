package zhengw.confmgr.bean;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ConfigLog extends BaseLog {

	@Column
	private int configId;

	@Column
	private String beforeValue;

	@Column
	private String afterValue;

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
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

}
