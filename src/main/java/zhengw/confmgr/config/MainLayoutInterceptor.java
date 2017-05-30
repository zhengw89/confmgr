package zhengw.confmgr.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import zhengw.confmgr.bean.ConfMgrUser;

@Component
public class MainLayoutInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if (modelAndView != null) {
			ConfMgrUser user = (ConfMgrUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			modelAndView.addObject("currentUserDisplayName", user.getDisplayName());
			modelAndView.addObject("currentUserId", user.getUserId());
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

}
