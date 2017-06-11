package zhengw.confmgr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

	@Value("${zk.connectionString}")
	private String connectionString;
	
	@Value("${zk.timeout}")
	private int timeout;

	public String getConnectionString() {
		return connectionString;
	}

	public int getTimeout() {
		return timeout;
	}

}
