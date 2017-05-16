package zhengw.confmgr.repository.test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Ignore
	@Test
	public void findByEmailTest() {

		User user = userRepository.findByEmail("admin@admin.com");

		assertNotNull(user);
		assertEquals(user.getName(), "Admin");
	}

	@Ignore
	@Test
	public void findByPage() {
		Page<User> users = userRepository.findAll(new PageRequest(0, 1, new Sort(Direction.DESC, "id")));

		assertNotNull(users);
		assertEquals(users.getSize(), 1);
	}
}
