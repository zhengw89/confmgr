package zhengw.confmgr.repository.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.ConfigLog;
import zhengw.confmgr.bean.OptType;
import zhengw.confmgr.repository.ConfigLogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigLogRepositoryTest {

	@Autowired
	private ConfigLogRepository configLogRepository;

	@Ignore
	@Test
	public void createConfigLogTest() {

		Config config = new Config();
		config.setAppId(-1);
		config.setEnvId(-1);
		config.setId(-1);
		config.setName("configName");

		ConfigLog log = new ConfigLog("appName", "envName", config);
		log.setAfterValue("afterValue");
		log.setBeforeValue("beforeValue");
		log.setEmail("email");
		log.setOptTime(new Date());
		log.setOptType(OptType.Create);
		log.setUserId(-1);

		configLogRepository.save(log);

		Assert.assertTrue(true);
	}
}
