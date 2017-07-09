package zhengw.confmgr.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private AppConfig appConfig;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		try {

			ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(appConfig.getTimeout(), 3);
			CuratorFramework client = CuratorFrameworkFactory.newClient(appConfig.getConnectionString(), retryPolicy);

			client.start();

			if (client.checkExists().forPath("/confmgr") == null) {
				client.create().forPath("/confmgr", String.valueOf(System.currentTimeMillis()).getBytes());
			} else {
				client.setData().forPath("/confmgr", String.valueOf(System.currentTimeMillis()).getBytes());
			}

			CloseableUtils.closeQuietly(client);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

}
