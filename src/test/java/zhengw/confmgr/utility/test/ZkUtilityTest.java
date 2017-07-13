package zhengw.confmgr.utility.test;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import zhengw.confmgr.config.AppConfig;
import zhengw.confmgr.utility.zk.ZkUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZkUtilityTest {

	@Autowired
	private AppConfig appConfig;

	@Test
	public void deleteTest() {

		CuratorFramework client = ZkUtility.getClient(appConfig.getConnectionString(), appConfig.getTimeout(), appConfig.getRetry());

		try {
			ZkUtility.delete(client, ZkUtility.append(ZkUtility.ROOT_PATH, "1"));

			Assert.assertTrue(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			Assert.assertTrue(false);
			;
		}
	}
}
