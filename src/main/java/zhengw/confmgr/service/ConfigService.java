package zhengw.confmgr.service;

import java.util.List;

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
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.ConfigRepository;
import zhengw.confmgr.service.operator.ConfigCreator;
import zhengw.confmgr.service.operator.ConfigEditer;

@Service
public class ConfigService extends BaseService {

	private ConfigRepository configRepository;

	@Autowired
	public void setConfigRepository(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	public List<Config> findByAppAndEnv(int appId, int envId) {
		return this.configRepository.findByAppIdAndEvnId(appId, envId);
	}

	public Page<Config> findConfigByPage(final int appId, final int envId, int pageIndex, int pageSize) {

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

	@Transactional(rollbackFor = Exception.class)
	public Tuple<Boolean, String> editConfig(int appId, int envId, int configId, String value, User user) {

		ConfigEditer configEditer = this.beanFactory.getBean(ConfigEditer.class, appId, envId, configId, value, user);
		return super.exeOperate(configEditer);
	}

	public Config findByName(int appId, int envId, String configName) {
		return this.configRepository.findByName(appId, envId, configName);
	}
}
