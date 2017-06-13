package zhengw.confmgr.zk;

public interface ZookeeperClientListener {

	void onConnected();
	
	void onDisconnected();
	
	void onNodeDataChanged(String path,String value);
}
