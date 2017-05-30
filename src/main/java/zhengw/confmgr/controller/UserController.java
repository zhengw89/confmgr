package zhengw.confmgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import zhengw.confmgr.bean.CreateUserRequest;
import zhengw.confmgr.bean.ModifyPasswordRequest;
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
	public String showCreateUser(Model model) {

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

	@RequestMapping(path = "user/delete", method = RequestMethod.POST)
	public String delteUser(@RequestParam(value = "userId") int userId) {

		userService.deleteUser(userId);

		return "redirect:/users";
	}

	@RequestMapping(path = "/user/modifyPassword/{userId}", method = RequestMethod.GET)
	public String showModifyPassword(@PathVariable(required = true) Integer userId, Model model) {

		User user = userService.findUser(userId);

		ModifyPasswordRequest request = new ModifyPasswordRequest();
		if (user != null) {
			request.setUserId(userId);
			model.addAttribute("user", user);
		} else {
			model.addAttribute("error", "用户不存在");
		}

		model.addAttribute("request", request);

		return "user/modifyPassword";
	}

	@RequestMapping(path = "/user/modifyPassword/{userId}", method = RequestMethod.POST)
	public String modifyPassword(@PathVariable(required = true) Integer userId, @ModelAttribute(value = "request") ModifyPasswordRequest request,
			Model model) {

		if (!request.isValid()) {
			User user = userService.findUser(userId);
			model.addAttribute("user", user);
			model.addAttribute("request", request);
			model.addAttribute("error", request.getInvalidMsg());

			return "user/modifyPassword";
		} else {
			Tuple<Boolean, String> modifyResult = userService.modifyPassword(userId, request.getOldPassword(), request.getNewPassword());
			if (modifyResult.getItem1()) {
				return "redirect:/users";
			} else {
				User user = userService.findUser(userId);
				model.addAttribute("user", user);
				model.addAttribute("request", request);
				model.addAttribute("error", modifyResult.getItem2());

				return "user/modifyPassword";
			}
		}

	}
}
