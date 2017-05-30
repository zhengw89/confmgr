package zhengw.confmgr.bean;

import org.springframework.util.StringUtils;

public class ModifyPasswordRequest extends BaseRequestBean {

	private int userId;

	private String oldPassword;

	private String newPassword;

	private String confirmPassword;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	protected boolean validateModel() {
		if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword))
			return false;
		if (!newPassword.equals(confirmPassword))
			return false;

		return true;
	}

	@Override
	protected String modelInvalidMsg() {
		if (StringUtils.isEmpty(oldPassword))
			return "旧密码不可为空";
		if (StringUtils.isEmpty(newPassword))
			return "新密码不可为空";
		if (StringUtils.isEmpty(confirmPassword))
			return "确认密码不可为空";
		if (!newPassword.equals(confirmPassword))
			return "新密码与确认密码不配陪";

		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("userId:%d,", userId));
		sb.append(String.format("oldPassword:%s,", StringUtils.isEmpty(oldPassword) ? "" : oldPassword));
		sb.append(String.format("newPassword:%s,", StringUtils.isEmpty(newPassword) ? "" : newPassword));
		sb.append(String.format("confirmPassword:%s", StringUtils.isEmpty(confirmPassword) ? "" : confirmPassword));

		return sb.toString();
	}

}
