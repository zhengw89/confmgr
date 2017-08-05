package zhengw.confmgr.repository.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.AppLog;
import zhengw.confmgr.bean.OptType;
import zhengw.confmgr.repository.AppLogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppLogRepositoryTest {

	@Autowired
	private AppLogRepository appLogRepository;

	@Ignore
	@Test
	public void saveTest() {
		AppLog log = new AppLog();
		log.setAppId(-1);
		log.setAppName("appName");
		log.setEmail("email");
		log.setOptTime(new Date());
		log.setOptType(OptType.Create);
		log.setUserId(-1);

		appLogRepository.save(log);

		Assert.assertTrue(log.getId() > 0);
	}

}
