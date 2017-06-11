package zhengw.confmgr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.CreateAppRequest;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.bean.PaginationViewModel;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.service.AppService;
import zhengw.confmgr.service.ConfigService;

@Controller
public class AppController extends BaseController {

	@Autowired
	private AppService appService;

	@Autowired
	private ConfigService configService;

	@RequestMapping(path = { "/", "/apps", "/apps/{page}" }, method = RequestMethod.GET)
	public String apps(Model model, @PathVariable(required = false) Integer page) {

		if (page == null)
			page = super.DEFAULT_PAGE_INDEX;

		Page<App> apps = appService.findAppByPage(page, super.DEFAULT_PAGE_SIZE);

		model.addAttribute("apps", new PaginationViewModel<App>(apps));

		return "app/apps";
	}

	@RequestMapping(path = "/app/create", method = RequestMethod.GET)
	public String showAppCreate(Model model) {

		model.addAttribute("request", new CreateAppRequest());

		return "app/appCreate";
	}

	@RequestMapping(path = "/app/create", method = RequestMethod.POST)
	public String appCreate(@ModelAttribute(value = "request") CreateAppRequest request, Model model) {

		if (!request.isValid()) {
			model.addAttribute("request", request);
			model.addAttribute("error", request.getInvalidMsg());

			return "app/appCreate";
		} else {

			Tuple<Boolean, String> createResult = null;

			try {
				createResult = appService.createApp(request.getName(), request.getDescription(), super.getCurrentUser());
			} catch (Exception e) {
				createResult = Tuple.create(false, e.getMessage());
			}

			if (!createResult.getItem1()) {
				model.addAttribute("request", request);
				model.addAttribute("error", createResult.getItem2());

				return "app/appCreate";

			} else {
				return "redirect:/apps";
			}
		}
	}

	@RequestMapping(path = { "/app/{appName}", "/app/{appName}/{env}", "/app/{appName}/{env}/{page}" }, method = RequestMethod.GET)
	public String showAppDetail(Model model, @PathVariable(required = true) String appName, @PathVariable(required = false) String env,
			@PathVariable(required = false) Integer page) {

		if (page == null)
			page = super.DEFAULT_PAGE_INDEX;

		List<Env> envs = appService.getAllEnvs();
		model.addAttribute("envs", envs);

		String selectEnvName = envs.get(0).getName();
		if (env != null) {
			selectEnvName = env;
		}
		model.addAttribute("selectEnv", selectEnvName);

		Env selectEnv = this.appService.findEnvByName(selectEnvName);

		App app = appService.findAppByName(appName);
		model.addAttribute("app", app);

		Page<Config> configs = this.configService.findConfigByPage(app.getId(), selectEnv.getId(), page, super.DEFAULT_PAGE_SIZE);
		model.addAttribute("configs", new PaginationViewModel<Config>(configs));

		return "app/appDetail";
	}
}
