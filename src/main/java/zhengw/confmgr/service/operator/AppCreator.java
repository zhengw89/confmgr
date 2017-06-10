package zhengw.confmgr.service.operator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.AppLog;
import zhengw.confmgr.bean.OptType;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.AppLogRepository;
import zhengw.confmgr.repository.AppRepository;

public class AppCreator extends BaseOperator {

	private final String name, description;

	private int appId;

	private AppRepository appRepository;
	private AppLogRepository appLogRepository;

	@Autowired
	public void setAppRepository(AppRepository appRepository) {
		this.appRepository = appRepository;
	}

	@Autowired
	public void setAppLogRepository(AppLogRepository appLogRepository) {
		this.appLogRepository = appLogRepository;
	}

	protected AppCreator(String name, String description, User optUser) {
		super(optUser);

		this.name = name;
		this.description = description;
	}

	@Override
	protected Tuple<Boolean, String> CheckDataCore() {

		if (StringUtils.isEmpty(this.name)) {
			return super.failResult("名称为空");
		}

		if (this.appRepository.exists(this.name)) {
			return super.failResult("已存在相同名称");
		}

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> OperateCore() {

		App app = new App();
		app.setName(this.name);
		app.setDescription(this.description);

		this.appRepository.save(app);

		this.appId = app.getId();

		return super.successResult();
	}

	@Override
	protected Tuple<Boolean, String> RecordLog() {

		AppLog log = new AppLog();
		log.setAppId(this.appId);
		log.setAppName(this.name);
		log.setEmail(super.getOptUser().getEmail());
		log.setOptTime(new Date());
		log.setOptType(OptType.Create);
		log.setUserId(super.getOptUser().getId());

		this.appLogRepository.save(log);

		return super.successResult();
	}

}
