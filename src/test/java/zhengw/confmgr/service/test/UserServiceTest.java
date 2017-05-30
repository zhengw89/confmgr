package zhengw.confmgr.service.test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Ignore
	@Test
	public void createUserTest() {

		User user = new User();
		user.setEmail("user@user.com");
		user.setName("User");
		user.setPassword("pwd");

		Tuple<Boolean, String> createResult = userService.createUser(user);

		assertTrue(createResult.getItem1());
	}

	@Test
	public void modifyPasswordTest() {
		Tuple<Boolean, String> result = userService.modifyPassword(1, "123", "123");

		assertTrue(result.getItem1());
	}
}
