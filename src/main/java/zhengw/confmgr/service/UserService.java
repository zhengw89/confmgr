package zhengw.confmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Page<User> findUserByPage(int pageIndex, int pageSize) {
		return userRepository.findAll(new PageRequest(pageIndex, pageSize, null));
	}
}
