package zhengw.confmgr.service;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.repository.ConfigRepository;

@Service
public class ConfigService {

	// private AppRepository appRepository;
	//
	// private EnvRepository envRepository;

	private ConfigRepository configRepository;

	// @Autowired
	// public void setAppRepository(AppRepository appRepository) {
	// this.appRepository = appRepository;
	// }
	//
	// @Autowired
	// public void setEnvRepository(EnvRepository envRepository) {
	// this.envRepository = envRepository;
	// }

	@Autowired
	public void setConfigRepository(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	public Page<Config> findConfigByPage(int appId, int envId, int pageIndex, int pageSize) {

		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
		Specification<Config> specification = new Specification<Config>() {

			@Override
			public Predicate toPredicate(Root<Config> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Path<Integer> appIdPath = root.get("appId");
				Path<Integer> envIdPath = root.get("envId");

				Predicate appPredicate = cb.equal(appIdPath, appId);
				Predicate envPredicate = cb.equal(envIdPath, envId);

				Predicate p = cb.and(appPredicate, envPredicate);

				query.where(p);

				return null;
			}

		};

		return configRepository.findAll(specification, pageable);
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
