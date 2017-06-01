package zhengw.confmgr.repository.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.Config;
import zhengw.confmgr.repository.ConfigRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigRepositoryTest {

	@Autowired
	private ConfigRepository configRepository;

	@Test
	public void countByNameTest() {
		assertTrue(configRepository.countByName(-1, -1, "123") > 0);
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
