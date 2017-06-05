package zhengw.confmgr.service.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.Config;
import zhengw.confmgr.service.ConfigService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigServiceTest {

	@Autowired
	private ConfigService configService;

	@Ignore
	@Test
	public void findConfigByPageTest() {

		Page<Config> configs = configService.findConfigByPage(1, 2, 1, 10);
		for (Config config : configs) {
			System.out.println(config.getName());
		}

		Assert.assertTrue(true);
	}

}
