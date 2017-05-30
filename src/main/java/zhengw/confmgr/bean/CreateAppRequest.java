package zhengw.confmgr.bean;

import org.springframework.util.StringUtils;

public class CreateAppRequest extends BaseRequestBean {

	private String name;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public App toApp() {
		App app = new App();
		app.setName(this.name);
		app.setDescription(this.description);
		return app;
	}

	@Override
	protected boolean validateModel() {

		if (StringUtils.isEmpty(this.name))
			return false;

		return true;
	}

	@Override
	protected String modelInvalidMsg() {

		if (StringUtils.isEmpty(this.name))
			return "名称不可为空";

		return null;
	}

}
