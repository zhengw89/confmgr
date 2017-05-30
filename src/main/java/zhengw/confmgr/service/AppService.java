package zhengw.confmgr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.repository.AppRepository;
import zhengw.confmgr.repository.EnvRepository;
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

	public List<Env> getAllEnvs() {
		return ListUtility.fromIterable(envRepository.findAll());
	}

	public Tuple<Boolean, String> createApp(App app) {

		if (appRepository.countByName(app.getName()) > 0) {
			return Tuple.create(false, "应用程序名称已存在");
		}

		appRepository.save(app);

		return Tuple.create(true, null);
	}

}
