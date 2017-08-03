package zhengw.confmgr.service.operator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import zhengw.confmgr.bean.User;

@Component
public class OperatorFactory {

	@Bean
	@Scope(value = "prototype")
	@Lazy(value = true)
	public ConfigCreator configCreator(int appId, int envId, String name, String value, User user) {

		ConfigCreator creator = new ConfigCreator(appId, envId, name, value, user);
		return creator;
	}

	@Bean
	@Scope(value = "prototype")
	@Lazy(value = true)
	public ConfigEditer configEditer(int appId, int envId, int configId, String value, User user) {

		ConfigEditer editer = new ConfigEditer(appId, envId, configId, value, user);
		return editer;
	}

	@Bean
	@Scope(value = "prototype")
	@Lazy(value = true)
	public ConfigDeletor configDeletor(int configId, User user) {

		return this.configDeletor(configId, true, user);
	}

	@Bean
	@Scope(value = "prototype")
	@Lazy(value = true)
	public ConfigDeletor configDeletor(int configId, Boolean zkUpdate, User user) {

		ConfigDeletor deletor = new ConfigDeletor(configId, zkUpdate, user);
		return deletor;
	}

	@Bean
	@Scope(value = "prototype")
	@Lazy(value = true)
	public AppCreator appCreator(String name, String description, User user) {

		AppCreator creator = new AppCreator(name, description, user);
		return creator;
	}

	@Bean
	@Scope(value = "prototype")
	@Lazy(value = true)
	public AppDeleter appDeleter(String appName, User user) {

		AppDeleter deleter = new AppDeleter(appName, user);
		return deleter;
	}
}
