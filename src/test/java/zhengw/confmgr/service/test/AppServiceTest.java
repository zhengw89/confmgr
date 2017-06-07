package zhengw.confmgr.service.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.repository.AppRepository;
import zhengw.confmgr.service.AppService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppServiceTest {

	@Autowired
	private AppService appService;

	@Test
	public void createAppTest() {

		App app = new App();
		app.setName("AAA");

		AppRepository appRepository = mock(AppRepository.class);
		when(appRepository.countByName("AAA")).thenReturn((long) 1);
		appService.setAppRepository(appRepository);

//		Tuple<Boolean, String> createResult = appService.createApp(app);

		assertFalse(true);
	}

}
