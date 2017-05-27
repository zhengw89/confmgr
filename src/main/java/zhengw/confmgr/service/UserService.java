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
			return new Tuple<Boolean, String>(false, "邮箱被占用，已存在相同用户");
		}

		userRepository.save(user);

		return new Tuple<Boolean, String>(true, null);
	}

	public Page<User> findUserByPage(int pageIndex, int pageSize) {
		return userRepository.findAll(new PageRequest(pageIndex - 1, pageSize, null));
	}

}
