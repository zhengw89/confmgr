package zhengw.confmgr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.AppRepository;
import zhengw.confmgr.repository.EnvRepository;
import zhengw.confmgr.service.operator.AppCreator;
import zhengw.confmgr.service.operator.AppDeleter;
import zhengw.confmgr.utility.ListUtility;

@Service
public class AppService extends BaseService {

	private AppRepository appRepository;

	private EnvRepository envRepository;

	@Autowired
	public void setAppRepository(AppRepository appRepository) {
		this.appRepository = appRepository;
	}

	@Autowired
	public void setEvnRepository(EnvRepository envRepository) {
		this.envRepository = envRepository;
	}

	public App findAppByName(String name) {
		return appRepository.findByName(name);
	}

	public List<App> getAllApp() {
		return ListUtility.fromIterable(appRepository.findAll());
	}

	public Page<App> findAppByPage(int pageIndex, int pageSize) {
		return appRepository.findAll(new PageRequest(pageIndex - 1, pageSize, new Sort("id")));
	}

	public List<Env> getAllEnvs() {
		return ListUtility.fromIterable(envRepository.findAll());
	}

	public Env findEnvByName(String name) {
		return envRepository.findByName(name);
	}

	@Transactional
	public Tuple<Boolean, String> createApp(String name, String description, User user) {

		AppCreator appCreator = super.beanFactory.getBean(AppCreator.class, name, description, user);
		return super.exeOperate(appCreator);
	}

	@Transactional
	public Tuple<Boolean, String> deleteApp(String appName, User user) {

		AppDeleter appDeleter = super.beanFactory.getBean(AppDeleter.class, appName, user);
		return super.exeOperate(appDeleter);
	}

}
