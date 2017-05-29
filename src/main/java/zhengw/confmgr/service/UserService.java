package zhengw.confmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Tuple<Boolean, String> createUser(User user) {

		if (userRepository.countByEmail(user.getEmail()) > 0) {
			return Tuple.create(false, "邮箱被占用，已存在相同用户");
		}

		userRepository.save(user);

		return Tuple.create(true, null);
	}

	public Tuple<Boolean, String> deleteUser(int userId) {

		if (!userRepository.exists(userId)) {
			return Tuple.create(false, "用户不存在");
		}

		userRepository.delete(userId);

		return Tuple.create(true, null);
	}

	public Tuple<Boolean, String> modifyPassword(int userId, String password) {

		User user = userRepository.findOne(userId);
		if (user == null) {
			return Tuple.create(false, "用户不存在");
		}

		user.setPassword(password);

		userRepository.save(user);

		return Tuple.create(true, null);
	}

	public Page<User> findUserByPage(int pageIndex, int pageSize) {

		return userRepository.findAll(new PageRequest(pageIndex - 1, pageSize, null));
	}

}
