package com.xiaodou.summer.vo.out;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.xiaodou.common.util.StringUtils;

public abstract class SumResType implements IResultType {

	private static String serverIp = "127.0.0.0";

	@Override
	public String getServerIp() {
		if (StringUtils.isBlank(serverIp))
			serverIp = "127.0.0.0";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			serverIp = addr.getHostAddress().toString();// 获得本机IP
		} catch (UnknownHostException e) {
		}
		return serverIp;
	}

}
