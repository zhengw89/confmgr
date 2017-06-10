package zhengw.confmgr.repository.test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.repository.AppRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppRepositoryTest {

	@Autowired
	private AppRepository appRepository;
	
	@Ignore
	@Test
	public void existsTest() {
		assertTrue(appRepository.exists("123"));
	}

	@Ignore
	@Test
	public void findByNameTest() {
		App app = appRepository.findByName("123");

		assertNotNull(app);
		assertEquals(app.getName(), "123");
	}
}
