package zhengw.confmgr.utility.zk;

import java.util.List;

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

	public static void delete(CuratorFramework client, String path) throws Exception {

		if (client.checkExists().forPath(path) != null) {
			List<String> children = client.getChildren().forPath(path);
			if (!children.isEmpty()) {
				for (String child : children) {
					delete(client, append(path, child));
				}
			}
			client.delete().forPath(path);
		}

	}

	public static String append(String path, String nodeName) {
		return path + SEPARATE + nodeName;
	}
}
