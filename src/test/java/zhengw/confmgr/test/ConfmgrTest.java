package zhengw.confmgr.test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.config.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfmgrTest {

	@Autowired
	private AppConfig appConfig;

//	@Ignore
	@Test
	public void testAppConfig() {

		// 创建一个与服务器的连接
//		try {
//			ZooKeeper zk = new ZooKeeper(appConfig.getConnectionString(), 1000, new Watcher() {
//				// 监控所有被触发的事件
//				public void process(WatchedEvent event) {
//					System.out.println("已经触发了" + event.getType() + "事件！");
//				}
//			});
//
//			// 创建一个目录节点
//			zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//
//			zk.close();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		Assert.assertEquals(appConfig.getConnectionString(), "zk.confmgr.com:2181");
		Assert.assertEquals(appConfig.getTimeout(), 1000);
	}
}
