package zhengw.confmgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfMgrApplication {

	private static final Logger logger = LoggerFactory.getLogger(ConfMgrApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(ConfMgrApplication.class, args);

		logger.info("Server start");
	}
}
