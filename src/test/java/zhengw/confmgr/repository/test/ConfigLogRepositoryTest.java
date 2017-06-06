package zhengw.confmgr.repository.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.ConfigLog;
import zhengw.confmgr.repository.ConfigLogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigLogRepositoryTest {

	@Autowired
	private ConfigLogRepository configLogRepository;

	@Test
	public void createConfigLogTest() {

		ConfigLog log = new ConfigLog();
		log.setAfterValue("afterValue");
		log.setBeforeValue("beforeValue");
		log.setEmail("email");
		log.setConfigId(-1);
		log.setUpdateTime(new Date());

		configLogRepository.save(log);
		
		Assert.assertTrue(true);
	}
}
