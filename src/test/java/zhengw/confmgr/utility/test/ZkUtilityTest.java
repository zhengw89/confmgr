package zhengw.confmgr.utility.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Assert;
import org.junit.Ignore;
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

	@Ignore
	@Test
	public void deleteTest() {

		CuratorFramework client = ZkUtility.getClient(appConfig.getConnectionString(), appConfig.getTimeout(), appConfig.getRetry());

		try {
			// ZkUtility.delete(client, ZkUtility.append(ZkUtility.ROOT_PATH,
			// "1"));

			SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

			for (int i = 0; i < 10; i++) {

				String timeStr = formatter.format(new Date());
				ZkUtility.createOrUpdate(client, "/confmgr/CCC/online/C1", timeStr);
				System.out.println("-------------" + timeStr);
				Thread.sleep(1000 * 5);

			}

			Assert.assertTrue(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			Assert.assertTrue(false);
			;
		}
	}
}
