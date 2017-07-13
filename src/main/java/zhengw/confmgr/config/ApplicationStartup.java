package zhengw.confmgr.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import zhengw.confmgr.service.ZkService;
import zhengw.confmgr.utility.zk.ZkUtility;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private ZkService zkService;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		try {

			CuratorFramework client = ZkUtility.getClient(appConfig.getConnectionString(), appConfig.getTimeout(), appConfig.getRetry());
			ZkUtility.createOrUpdate(client, ZkUtility.ROOT_PATH, String.valueOf(System.currentTimeMillis()));

			zkService.apps(client);

			CloseableUtils.closeQuietly(client);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return;
	}

}
