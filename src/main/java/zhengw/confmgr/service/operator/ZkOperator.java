package zhengw.confmgr.service.operator;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zhengw.confmgr.bean.Env;
import zhengw.confmgr.service.AppService;
import zhengw.confmgr.utility.zk.ZkUtility;

@Component
public class ZkOperator {

	@Autowired
	private AppService appService;

	public void createOrUpdateApp(CuratorFramework client, String appName) throws Exception {

		String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, appName);

		ZkUtility.createOrUpdate(client, appNodePath, String.valueOf(System.currentTimeMillis()));

		List<Env> envs = this.appService.getAllEnvs();
		for (Env env : envs) {
			String envNodePath = ZkUtility.append(appNodePath, env.getName());
			ZkUtility.createOrUpdate(client, envNodePath, String.valueOf(System.currentTimeMillis()));
		}

	}

	public void createOrUpdateConfig(CuratorFramework client, String appName, String envName, String configName) throws Exception {

		String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, appName);
		String envNodePath = ZkUtility.append(appNodePath, envName);
		String configNodePath = ZkUtility.append(envNodePath, configName);

		ZkUtility.createOrUpdate(client, configNodePath, String.valueOf(System.currentTimeMillis()));
	}

	public void deleteApp(CuratorFramework client, String appName) throws Exception {

		String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, appName);

		ZkUtility.delete(client, appNodePath);
	}

	public void deleteConfig(CuratorFramework client, String appName, String envName, String configName) throws Exception {

		String appNodePath = ZkUtility.append(ZkUtility.ROOT_PATH, appName);
		String envNodePath = ZkUtility.append(appNodePath, envName);
		String configNodePath = ZkUtility.append(envNodePath, configName);

		ZkUtility.delete(client, configNodePath);
	}

}
