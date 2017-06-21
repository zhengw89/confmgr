package zhengw.confmgr.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.service.AppService;
import zhengw.confmgr.service.ConfigService;

@Controller
@RequestMapping("/api")
public class ApiController {

	private AppService appService;

	private ConfigService configService;

	@Autowired
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	@Autowired
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	@RequestMapping(path = "/configs/{app}/{env}", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> getConfigs(@PathVariable("app") String appName, @PathVariable("env") String envName) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		App app = this.appService.findAppByName(appName);
		if (app == null)
			return map;

		Env env = this.appService.findEnvByName(envName);
		if (env == null)
			return map;

		List<Config> configs = this.configService.findByAppAndEnv(app.getId(), env.getId());
		for (Config config : configs) {
			map.put(config.getName(), config.getValue());
		}

		return map;
	}

	@RequestMapping(path = "/config/{app}/{env}/{config}", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> getConfig(@PathVariable("app") String appName, @PathVariable("env") String envName,
			@PathVariable("config") String configName) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		App app = this.appService.findAppByName(appName);
		if (app == null)
			return map;

		Env env = this.appService.findEnvByName(envName);
		if (env == null)
			return map;

		Config config = this.configService.findByName(app.getId(), env.getId(), configName);
		if (config == null)
			return map;

		map.put(config.getName(), config.getValue());

		return map;
	}
}
