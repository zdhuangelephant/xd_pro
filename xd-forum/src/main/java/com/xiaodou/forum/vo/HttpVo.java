package com.xiaodou.forum.vo;

/**
 * http发送vo
 * 
 * @author bing.cheng
 *
 */
public class HttpVo {

	/** 外部系统地址 */
	private String host;
	/** 端口号 */
	private Integer port;
	/** 访问路径 */
	private String path;
	/** 重试次数 */
	private Integer retryTime;
	/** 访问超时时间 */
	private Integer timeout;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(Integer retryTime) {
		this.retryTime = retryTime;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		return "HttpVo [host=" + host + ", port=" + port + ", path=" + path
				+ ", retryTime=" + retryTime + ", timeout=" + timeout + "]";
	}
	
}
