package zhengw.confmgr.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.repository.ConfigRepository;

@Service
public class ConfigService {

//	private AppRepository appRepository;
//
//	private EnvRepository envRepository;

	private ConfigRepository configRepository;

//	@Autowired
//	public void setAppRepository(AppRepository appRepository) {
//		this.appRepository = appRepository;
//	}
//
//	@Autowired
//	public void setEnvRepository(EnvRepository envRepository) {
//		this.envRepository = envRepository;
//	}

	@Autowired
	public void setConfigRepository(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	public Tuple<Boolean, String> createConfig(App app, Env env, String name, String value) {

		if (app == null) {
			return Tuple.create(false, "不存在指定应用");
		}

		if (env == null) {
			return Tuple.create(false, "不存在指定环境");
		}

		if (this.configRepository.countByName(app.getId(), env.getId(), name) > 0) {
			return Tuple.create(false, "已存在同名配置项");
		}

		Config config = new Config();
		config.setAppId(app.getId()); 
		config.setCreateTime(new Date());
		config.setEnvId(env.getId());
		config.setName(name);
		config.setValue(value);

		this.configRepository.save(config);

		return Tuple.create(true, null);
	}

}
