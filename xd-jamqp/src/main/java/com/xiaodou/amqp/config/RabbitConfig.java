package com.xiaodou.amqp.config;

/**
 * 	RabbitMQ配置类
 * @author hexiong
 */
public class RabbitConfig {
	/**
	 * 单件模式,初始化RabbitMQ配置信息,一个客户端只能连接一个服务器,获取配置信息
	 */
	private static RabbitConfig singleTon = null;
	private static Object _LOKPAD = new Object();
	
	private RabbitConfig(){            
		this.receiveBusyInterval = 200;
		this.receiveFreeInterval = 5000;
		this.requestHeartBeat = 20;
		this.requestedConnectionTimeOut = 30 * 1000;
		this.maxReceiveProxyTime = 60 * 10;
		this.maxReceiveFailedCount = 10;
    }
	
	
	public static RabbitConfig getInstance(){
		if(singleTon == null){
			synchronized(_LOKPAD){
				if(singleTon == null){
					singleTon = new RabbitConfig();
				}
			}
		}
		return singleTon;
	}
	
	/**
	 * hostName:RabbitMQ服务地址
	 */
	private String hostName;
	/**
	 * port:RabbitMQ服务端口
	 */
	private int port;
	/**
	 * userName:用户名
	 */
	private String userName;
	/**
	 * passWord:密码
	 */
	private String passWord;
	/**
	 * connectionTimeOut:连接保持时间
	 */
	private int connectionTimeOut;
	/**
	 * sendTimeOut:发送超时时间
	 */
	private int sendTimeOut;
	/**
	 * receiveTimeOut:接收超时时间
	 */
	private int receiveTimeOut;
	/**
	 * maxPoolSize:连接池最大大小
	 */
	private int maxPoolSize;
	/**
	 * receiveBusyInterval:接收消息(有消息)的延迟时间
	 */
	private int receiveBusyInterval;
	/**
	 * receiveFreeInterval:接收消息(无消息)的延迟时间 
	 */
	private int receiveFreeInterval;
	/**
	 * qos:默认QOS
	 */
	private int qos;
	/**
	 * requestHeartBeat:默认心跳时间
	 */
	private int requestHeartBeat;
	/**
	 * maxReceiveFailedCount:接收消息最大失败数目,超过该数目重新建立连接
	 */
	private int maxReceiveFailedCount;
	/**
	 * maxReceiveProxyTime:接收消息连接最长保持时间,超过则重新建立连接
	 */
	private int maxReceiveProxyTime;
	/**
	 * requestedConnectionTimeOut:连接请求超时时间
	 */
	private int requestedConnectionTimeOut;	
	/**
	 * sendLogBaseDir:消息成功发送日志文件路径
	 */
	private String sendLogBaseDir;
	/**
	 * failedLogBaseDir:失败消息记录文件基路径
	 */
	private String failedLogBaseDir;
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}
	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}
	public int getSendTimeOut() {
		return sendTimeOut;
	}
	public void setSendTimeOut(int sendTimeOut) {
		this.sendTimeOut = sendTimeOut;
	}
	public int getReceiveTimeOut() {
		return receiveTimeOut;
	}
	public void setReceiveTimeOut(int receiveTimeOut) {
		this.receiveTimeOut = receiveTimeOut;
	}
	public int getMaxPoolSize() {
		return maxPoolSize;
	}
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public int getReceiveBusyInterval() {
		return receiveBusyInterval;
	}
	public void setReceiveBusyInterval(int receiveBusyInterval) {
		this.receiveBusyInterval = receiveBusyInterval;
	}
	public int getReceiveFreeInterval() {
		return receiveFreeInterval;
	}
	public void setReceiveFreeInterval(int receiveFreeInterval) {
		this.receiveFreeInterval = receiveFreeInterval;
	}
	public int getQos() {
		return qos;
	}
	public void setQos(int qos) {
		this.qos = qos;
	}
	public int getRequestHeartBeat() {
		return requestHeartBeat;
	}
	public void setRequestHeartBeat(int requestHeartBeat) {
		this.requestHeartBeat = requestHeartBeat;
	}
	public int getMaxReceiveFailedCount() {
		return maxReceiveFailedCount;
	}
	public void setMaxReceiveFailedCount(int maxReceiveFailedCount) {
		this.maxReceiveFailedCount = maxReceiveFailedCount;
	}
	public int getMaxReceiveProxyTime() {
		return maxReceiveProxyTime;
	}
	public void setMaxReceiveProxyTime(int maxReceiveProxyTime) {
		this.maxReceiveProxyTime = maxReceiveProxyTime;
	}
	public int getRequestedConnectionTimeOut() {
		return requestedConnectionTimeOut;
	}
	public void setRequestedConnectionTimeOut(int requestedConnectionTimeOut) {
		this.requestedConnectionTimeOut = requestedConnectionTimeOut;
	}
	public String getSendLogBaseDir() {
		return sendLogBaseDir;
	}
	public void setSendLogBaseDir(String sendLogBaseDir) {
		this.sendLogBaseDir = sendLogBaseDir;
	}
	public String getFailedLogBaseDir() {
		return failedLogBaseDir;
	}
	public void setFailedLogBaseDir(String failedLogBaseDir) {
		this.failedLogBaseDir = failedLogBaseDir;
	}
}
