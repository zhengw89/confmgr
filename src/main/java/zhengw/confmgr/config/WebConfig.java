package zhengw.confmgr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);

		registry.addInterceptor(new MainLayoutInterceptor()).addPathPatterns("/","/users/**","/user/**","/apps/**","/app/**","/config/**");
	}

}
