package zhengw.confmgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zhengw.confmgr.bean.CreateUserRequest;
import zhengw.confmgr.bean.PaginationViewModel;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.bean.User;
import zhengw.confmgr.service.UserService;

@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping(path = { "/users", "/users/{page}" }, method = RequestMethod.GET)
	public String users(Model model, @PathVariable(required = false) Integer page) {

		if (page == null)
			page = super.DEFAULT_PAGE_INDEX;

		Page<User> users = userService.findUserByPage(page, super.DEFAULT_PAGE_SIZE);

		model.addAttribute("users", new PaginationViewModel<User>(users));

		return "user/users";
	}

	@RequestMapping(path = "user/create", method = RequestMethod.GET)
	public String snowCreateUser(Model model) {

		model.addAttribute("request", new CreateUserRequest());

		return "user/userCreate";
	}

	@RequestMapping(path = "user/create", method = RequestMethod.POST)
	public String createUser(@ModelAttribute(value = "request") CreateUserRequest request, Model model) {

		if (!request.isValid()) {
			model.addAttribute("request", request);
			model.addAttribute("error", request.getInvalidMsg());

			return "user/userCreate";
		} else {
			User userToCreate = request.toUser();

			Tuple<Boolean, String> userCreateResult = userService.createUser(userToCreate);
			if (!userCreateResult.getItem1()) {
				model.addAttribute("request", request);
				model.addAttribute("error", userCreateResult.getItem2());

				return "user/userCreate";
			} else {
				return "redirect:/users";
			}
		}

	}

}
