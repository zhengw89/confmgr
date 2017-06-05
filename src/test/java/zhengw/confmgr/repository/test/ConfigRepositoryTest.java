package zhengw.confmgr.repository.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.Config;
import zhengw.confmgr.repository.ConfigRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigRepositoryTest {

	@Autowired
	private ConfigRepository configRepository;

	@Ignore
	@Test
	public void countByNameTest() {

		assertTrue(configRepository.countByName(-1, -1, "123") > 0);
	}

	@Ignore
	@Test
	public void findByConditionTest() {

		int appId = 1;
		int envId = 2;

		Pageable pageable = new PageRequest(0, 20);

		Specification<Config> spp = new Specification<Config>() {

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

		Page<Config> configs = configRepository.findAll(spp, pageable);
		for (Config config : configs) {
			System.out.println(config.getName());
		}

		assertTrue(true);
	}

	@Ignore
	@Test
	public void saveTest() {
		Config config = new Config();
		config.setAppId(-1);
		config.setEnvId(-1);
		config.setCreateTime(new Date());
		config.setName("123");
		config.setValue("123");

		configRepository.save(config);

		assertTrue(config.getId() > 0);
	}

}
