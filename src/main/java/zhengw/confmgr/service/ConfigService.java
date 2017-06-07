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
import org.springframework.transaction.annotation.Transactional;

import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.ConfigLog;
import zhengw.confmgr.bean.OptType;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.ConfigLogRepository;
import zhengw.confmgr.repository.ConfigRepository;
import zhengw.confmgr.service.operator.ConfigCreator;

@Service
public class ConfigService extends BaseService {

	private ConfigRepository configRepository;

	private ConfigLogRepository configLogRepository;

	@Autowired
	public void setConfigRepository(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	@Autowired
	public void setConfigLogRepository(ConfigLogRepository configLogRepository) {
		this.configLogRepository = configLogRepository;
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

	@Transactional(rollbackFor = Exception.class)
	public Tuple<Boolean, String> createConfig(int appId, int envId, String name, String value, User user) {

		ConfigCreator configCreator = this.beanFactory.getBean(ConfigCreator.class, appId, envId, name, value, user);
		return super.exeOperate(configCreator);
	}

	public Tuple<Boolean, String> editConfig(int appId, int envId, int configId, String value, User user) {

		Config config = configRepository.findById(appId, envId, configId);
		if (config == null) {
			return Tuple.create(false, "配置不存在");
		}

		ConfigLog log = new ConfigLog();
		log.setAfterValue(value);
		log.setBeforeValue(config.getValue());
		log.setConfigId(config.getId());
		log.setEmail(user.getEmail());
		log.setOptTime(new Date());
		log.setOptType(OptType.Update);
		log.setUserId(user.getId());

		config.setValue(value);

		this.configRepository.save(config);
		this.configLogRepository.save(log);

		return Tuple.create(true, null);
	}

	public Config findByName(int appId, int envId, String configName) {
		return this.configRepository.findByName(appId, envId, configName);
	}
}
