package zhengw.confmgr.service.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
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

	@Ignore
	@Test
	public void editTest() {
		Tuple<Boolean, String> editResult = configService.editConfig(1, 1, 13, "22", new User());
		Assert.assertTrue(editResult.getItem1());
	}

	@Ignore
	@Test
	public void deleteTest() {
		
		User mockUser = Mockito.mock(User.class);
		Mockito.when(mockUser.getName()).thenReturn("Name");
		Mockito.when(mockUser.getEmail()).thenReturn("EMail");
		Mockito.when(mockUser.getId()).thenReturn(1);

		Tuple<Boolean, String> deleteResult = configService.deleteConfig(2, mockUser);

		Assert.assertTrue(deleteResult.getItem1());
	}
}
