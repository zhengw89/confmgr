package zhengw.confmgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.CreateConfigRequest;
import zhengw.confmgr.bean.EditConfigRequest;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.service.AppService;
import zhengw.confmgr.service.ConfigService;

@Controller
public class ConfigController {

	@Autowired
	private AppService appService;

	@Autowired
	private ConfigService configService;

	@RequestMapping(path = "/config/create/{appName}/{envName}", method = RequestMethod.GET)
	public String showConfigCreate(Model model, @PathVariable(required = true) String appName, @PathVariable(required = false) String envName) {

		App app = appService.findAppByName(appName);
		Env env = appService.findEnvByName(envName);

		model.addAttribute("app", app);
		model.addAttribute("env", env);

		model.addAttribute("request", new CreateConfigRequest(app.getId(), env.getId()));

		return "config/configCreate";
	}

	@RequestMapping(path = "/config/create/{appName}/{envName}", method = RequestMethod.POST)
	public String configCreate(Model model, @ModelAttribute(value = "request") CreateConfigRequest request,
			@PathVariable(required = true) String appName, @PathVariable(required = false) String envName) {

		App app = appService.findAppByName(appName);
		Env env = appService.findEnvByName(envName);

		if (!request.isValid()) {

			model.addAttribute("error", request.getInvalidMsg());

			model.addAttribute("app", app);
			model.addAttribute("env", env);

			model.addAttribute("request", request);

			return "config/configCreate";
		} else {
			Tuple<Boolean, String> createResult = this.configService.createConfig(app, env, request.getName(), request.getValue());
			if (!createResult.getItem1()) {
				model.addAttribute("error", createResult.getItem2());

				model.addAttribute("app", app);
				model.addAttribute("env", env);

				model.addAttribute("request", request);

				return "config/configCreate";
			}
		}

		return String.format("redirect:/app/%d/%d", appName, envName);
	}

	@RequestMapping(path = "/config/edit/{appName}/{envName}/{configName}", method = RequestMethod.GET)
	public String showConfigEdit(Model model, @PathVariable(required = true) String appName, @PathVariable(required = true) String envName,
			@PathVariable(required = true) String configName) {

		App app = appService.findAppByName(appName);
		Env env = appService.findEnvByName(envName);
		Config config = configService.findByName(app.getId(), env.getId(), configName);

		model.addAttribute("app", app);
		model.addAttribute("env", env);
		model.addAttribute("config", config);

		model.addAttribute("request", new EditConfigRequest(app.getId(), env.getId(), config.getId()));

		return "config/configEdit";
	}

	@RequestMapping(path = "/config/edit/{appName}/{envName}/{configName}", method = RequestMethod.POST)
	public String configEdit(Model model, @ModelAttribute(value = "request") EditConfigRequest request, @PathVariable(required = true) String appName,
			@PathVariable(required = false) String envName, @PathVariable(required = true) String configName) {

		if (!request.isValid()) {

			model.addAttribute("error", request.getInvalidMsg());

			App app = appService.findAppByName(appName);
			Env env = appService.findEnvByName(envName);
			Config config = configService.findByName(app.getId(), env.getId(), configName);

			model.addAttribute("app", app);
			model.addAttribute("env", env);
			model.addAttribute("config", config);

			model.addAttribute("request", request);

			return "config/configEdit";
		} else {

			Tuple<Boolean, String> editResult = this.configService.editConfig(request.getAppId(), request.getEnvId(), request.getConfigId(),
					request.getNewValue());
			if (!editResult.getItem1()) {

				model.addAttribute("error", editResult.getItem2());

				App app = appService.findAppByName(appName);
				Env env = appService.findEnvByName(envName);
				Config config = configService.findByName(app.getId(), env.getId(), configName);

				model.addAttribute("app", app);
				model.addAttribute("env", env);
				model.addAttribute("config", config);

				model.addAttribute("request", request);

				return "config/configEdit";
			}
		}

		return String.format("redirect:/app/%d/%d", appName, envName);
	}
}
