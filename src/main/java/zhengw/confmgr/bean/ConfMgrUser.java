package zhengw.confmgr.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class ConfMgrUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private final User user;

	public User getUser() {
		return this.user;
	}

	public int getUserId() {
		return this.user.getId();
	}

	public String getDisplayName() {
		return this.user.getName();
	}

	public ConfMgrUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), true, true, true, true, authorities);

		this.user = user;
	}

}
