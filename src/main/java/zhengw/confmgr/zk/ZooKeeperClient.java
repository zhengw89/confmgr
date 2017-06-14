package zhengw.confmgr.zk;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ZooKeeperClient implements Watcher {

	private final Logger logger = LoggerFactory.getLogger(ZooKeeperClient.class);

	private final int SESSION_TIME_OUT = 1000;

	private static final Object LOCK_OBJ = new Object();

	private final String connectionString;
	private ZooKeeper zooKeeper;
	private CountDownLatch countDownLatch;

	private final ZookeeperClientListener listener;

	public ZooKeeperClient(String connectionString, ZookeeperClientListener listener) throws IOException, InterruptedException {

		this.connectionString = connectionString;
		this.listener = listener;

		connect();
	}

	public String createNode(String path, String value, List<ACL> acl, CreateMode createMode) throws KeeperException, InterruptedException {
		return this.zooKeeper.create(path, value.getBytes(), acl, createMode);
	}

	public Stat setData(String path, String value, int version) throws KeeperException, InterruptedException {
		return this.zooKeeper.setData(path, value.getBytes(), version);
	}

	public Stat exists(String path) throws KeeperException, InterruptedException {
		return this.zooKeeper.exists(path, false);
	}

	public Stat watch(String path) throws KeeperException, InterruptedException {
		return this.zooKeeper.exists(path, this);
	}

	public void close() throws InterruptedException {

		if (this.zooKeeper.getState() == States.CONNECTED) {
			this.zooKeeper.close();
		}
		this.zooKeeper = null;

		this.countDownLatch = null;

	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println(event);

		if (event.getState() == KeeperState.SyncConnected && event.getType() == EventType.None) {

			this.countDownLatch.countDown();
			this.logger.info("connect");

			if (this.listener != null) {
				this.listener.onConnected();
			}

		} else if (event.getState() == KeeperState.SyncConnected && event.getType() == EventType.NodeDataChanged) {

			try {
				String data = new String(this.zooKeeper.getData(event.getPath(), null, null));
				System.out.println(String.format("NodeDataChanged: %s", data));

				if (this.listener != null) {
					this.listener.onNodeDataChanged(event.getPath(), data);
				}

			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else if (event.getState() == KeeperState.Disconnected) {

			if (this.listener != null) {
				this.listener.onDisconnected();
			}

		} else if (event.getState() == KeeperState.Expired) {
			reconnect();
		}

		if (!StringUtils.isEmpty(event.getPath())) {
			try {
				watch(event.getPath());
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void connect() throws IOException, InterruptedException {

		this.zooKeeper = new ZooKeeper(connectionString, SESSION_TIME_OUT, this);

		this.countDownLatch = new CountDownLatch(1);
		this.countDownLatch.await(10, TimeUnit.SECONDS);
	}

	private void reconnect() {
		synchronized (LOCK_OBJ) {
			while (true) {
				try {

					if (this.zooKeeper != null && this.zooKeeper.getState() != States.CLOSED) {
						break;
					}

					close();
					connect();

				} catch (Exception e) {
					try {
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
}
