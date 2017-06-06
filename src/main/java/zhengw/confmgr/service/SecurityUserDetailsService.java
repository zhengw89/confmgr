package zhengw.confmgr.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import zhengw.confmgr.bean.ConfMgrUser;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.UserRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

	private static Set<GrantedAuthority> AUTHORITIES;
	static {
		AUTHORITIES = new HashSet<GrantedAuthority>();
		AUTHORITIES.add(new GrantedAuthority() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return "ROLE_ADMIN";
			}
		});
	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null || StringUtils.isEmpty(username))
			throw new UsernameNotFoundException("用户名为空");

		User user = userRepository.findByEmail(username);
		if (user == null)
			throw new UsernameNotFoundException("用户不存在");

		return new ConfMgrUser(user, AUTHORITIES);
	}

}
