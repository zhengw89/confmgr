package zhengw.confmgr.utility.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkUtility {

	public final static String ROOT_PATH = "/confmgr";
	private final static String SEPARATE = "/";

	public static CuratorFramework getClient(String connectionString, int timeout, int retry) {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(timeout, retry);
		CuratorFramework client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
		client.start();
		return client;
	}

	public static void createOrUpdate(CuratorFramework client, String path, String value) throws Exception {
		if (client.checkExists().forPath(path) == null) {
			client.create().forPath(path, String.valueOf(System.currentTimeMillis()).getBytes());
		} else {
			client.setData().forPath(path, String.valueOf(System.currentTimeMillis()).getBytes());
		}
	}

	public static String append(String path, String nodeName) {
		return path + SEPARATE + nodeName;
	}
}
