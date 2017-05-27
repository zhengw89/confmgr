package zhengw.confmgr.bean;

import org.springframework.util.StringUtils;

public class CreateUserRequest extends BaseRequestBean {

	private String email;

	private String name;

	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("email: %s;", StringUtils.isEmpty(this.email) ? "" : this.email));
		sb.append(String.format("name: %s;", StringUtils.isEmpty(this.name) ? "" : this.name));
		sb.append(String.format("password: %s;", StringUtils.isEmpty(this.password) ? "" : this.password));

		return sb.toString();
	}

	@Override
	protected boolean validateModel() {
		if (StringUtils.isEmpty(this.email) || StringUtils.isEmpty(this.name) || StringUtils.isEmpty(this.password)) {
			return false;
		}

		return true;
	}

	public User toUser() {
		User user = new User();
		user.setEmail(this.email);
		user.setName(this.name);
		user.setPassword(this.password);
		return user;
	}

	@Override
	protected String modelInvalidMsg() {

		if (StringUtils.isEmpty(this.email)) {
			return "用户邮箱参数为空";
		}

		if (StringUtils.isEmpty(this.name)) {
			return "用户名称参数为空";
		}

		if (StringUtils.isEmpty(this.password)) {
			return "用户密码参数为空";
		}

		return null;
	}
}
