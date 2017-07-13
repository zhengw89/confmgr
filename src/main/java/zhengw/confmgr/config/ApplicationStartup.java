package zhengw.confmgr.config;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.service.AppService;
import zhengw.confmgr.service.ConfigService;
import zhengw.confmgr.utility.zk.ZkUtility;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private AppService appService;

	@Autowired
	private ConfigService configService;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		try {

			CuratorFramework client = ZkUtility.getClient(appConfig.getConnectionString(), appConfig.getTimeout(), appConfig.getRetry());
			ZkUtility.createOrUpdate(client, ZkUtility.ROOT_PATH, String.valueOf(System.currentTimeMillis()));

			List<App> apps = appService.getAllApp();
			for (App app : apps) {
				String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, app.getName());
				ZkUtility.createOrUpdate(client, appNodePath, String.valueOf(System.currentTimeMillis()));

				List<Env> envs = appService.getAllEnvs();
				for (Env env : envs) {
					String envNodePath = ZkUtility.append(appNodePath, env.getName());
					ZkUtility.createOrUpdate(client, envNodePath, String.valueOf(System.currentTimeMillis()));

					List<Config> configs = configService.findByAppAndEnv(app.getId(), env.getId());
					for (Config config : configs) {
						String configNodePath = ZkUtility.append(envNodePath, config.getName());
						ZkUtility.createOrUpdate(client, configNodePath, String.valueOf(System.currentTimeMillis()));
					}
				}
			}

			CloseableUtils.closeQuietly(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return;
	}

}
