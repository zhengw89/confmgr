package zhengw.confmgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import zhengw.confmgr.bean.PaginationViewModel;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.service.UserService;

@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping({ "/users", "/users/{page}" })
	public String users(Model model, @PathVariable(required = false) Integer page) {

		if (page == null)
			page = super.DEFAULT_PAGE_INDEX;

		Page<User> users = userService.findUserByPage(page, super.DEFAULT_PAGE_SIZE);
//		model.addAttribute("users", users);

		model.addAttribute("users", new PaginationViewModel<User>(users));

		return "users";
	}

}
