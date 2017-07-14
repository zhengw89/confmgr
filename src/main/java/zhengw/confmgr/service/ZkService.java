package zhengw.confmgr.service;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhengw.confmgr.bean.App;
import zhengw.confmgr.bean.Config;
import zhengw.confmgr.bean.Env;
import zhengw.confmgr.utility.zk.ZkUtility;

@Service
public class ZkService {

	@Autowired
	private AppService appService;

	@Autowired
	private ConfigService configService;

	public void apps(CuratorFramework client) throws Exception {

		List<App> apps = appService.getAllApp();
		List<String> children = client.getChildren().forPath(ZkUtility.ROOT_PATH);
		boolean isUselessChild;
		for (String child : children) {
			isUselessChild = true;
			for (App app : apps) {
				if (app.getName().equals(child)) {
					isUselessChild = false;
					break;
				}
			}
			if (isUselessChild) {
				ZkUtility.delete(client, ZkUtility.append(ZkUtility.ROOT_PATH, child));
			}
		}

		for (App app : apps) {
			String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, app.getName());
			ZkUtility.createOrUpdate(client, appNodePath, String.valueOf(System.currentTimeMillis()));

			envs(client, app);
		}
	}

	public void envs(CuratorFramework client, App app) throws Exception {

		String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, app.getName());
		List<Env> envs = appService.getAllEnvs();
		List<String> children = client.getChildren().forPath(appNodePath);
		boolean isUselessChild;
		for (String child : children) {
			isUselessChild = true;
			for (Env env : envs) {
				if (env.getName().equals(child)) {
					isUselessChild = false;
					break;
				}
			}
			if (isUselessChild) {
				ZkUtility.delete(client, ZkUtility.append(appNodePath, child));
			}
		}

		for (Env env : envs) {
			String envNodePath = ZkUtility.append(appNodePath, env.getName());
			ZkUtility.createOrUpdate(client, envNodePath, String.valueOf(System.currentTimeMillis()));

			configs(client, app, env);
		}
	}

	public void configs(CuratorFramework client, App app, Env env) throws Exception {

		String envNodePath = ZkUtility.append(ZkUtility.append(ZkUtility.ROOT_PATH, app.getName()), env.getName());
		List<Config> configs = configService.findByAppAndEnv(app.getId(), env.getId());
		List<String> children = client.getChildren().forPath(envNodePath);
		boolean isUselessChild;
		for (String child : children) {
			isUselessChild = true;
			for (Config config : configs) {
				if (config.getName().equals(child)) {
					isUselessChild = false;
					break;
				}
			}
			if (isUselessChild) {
				ZkUtility.delete(client, ZkUtility.append(envNodePath, child));
			}
		}

		for (Config config : configs) {
			String configNodePath = ZkUtility.append(envNodePath, config.getName());
			ZkUtility.createOrUpdate(client, configNodePath, String.valueOf(System.currentTimeMillis()));
		}
	}

	public void config(CuratorFramework client, App app, Env env, Config config) throws Exception {
		
		String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, app.getName());
		String envNodePath = ZkUtility.append(appNodePath, env.getName());
		String configNodePath = ZkUtility.append(envNodePath, config.getName());

		ZkUtility.createOrUpdate(client, configNodePath, String.valueOf(System.currentTimeMillis()));
	}
	
	public void deleteApp(CuratorFramework client, App app) throws Exception {
		
		String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, app.getName());
		
		ZkUtility.delete(client, appNodePath);
	}

	public void deleteConfig(CuratorFramework client, App app, Env env, Config config) throws Exception {
		
		String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, app.getName());
		String envNodePath = ZkUtility.append(appNodePath, env.getName());
		String configNodePath = ZkUtility.append(envNodePath, config.getName());
		
		ZkUtility.delete(client, configNodePath);
	}
}
