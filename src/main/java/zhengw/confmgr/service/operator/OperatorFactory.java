package zhengw.confmgr.service.operator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import zhengw.confmgr.bean.User;
import zhengw.confmgr.repository.ConfigRepository;

@Configuration
public class OperatorFactory {

	@Autowired
	private ConfigRepository configRepository;

	@Bean
	@Scope(value = "prototype")
	@Lazy(value = true)
	public ConfigCreator configCreator(User user) {
		
		System.out.println("bean function");
		
		ConfigCreator creator = new ConfigCreator(user);
		creator.setConfigRepository(configRepository);
		return creator;
	}
}
