package zhengw.confmgr.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import zhengw.confmgr.bean.ConfMgrUser;
import zhengw.confmgr.bean.User;

public abstract class BaseController {

	protected final int DEFAULT_PAGE_INDEX = 1;
	protected final int DEFAULT_PAGE_SIZE = 10;

	protected User getCurrentUser() {
		ConfMgrUser user = (ConfMgrUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return user.getUser();
	}
}
