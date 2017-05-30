package zhengw.confmgr.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class ConfMgrUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private int userId;

	private String displayName;

	public int getUserId() {
		return this.userId;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public ConfMgrUser(int userId, String username, String password, String displayName, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);

		this.userId = userId;
		this.displayName = displayName;
	}

}
