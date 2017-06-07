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
}
