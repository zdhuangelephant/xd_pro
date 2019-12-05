package com.xiaodou.jmsg.service;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.constant.JMSGConstant;
import com.xiaodou.jmsg.model.RabbitMQConfig;
import com.xiaodou.amqp.config.RabbitConfig;
import com.xiaodou.amqp.connectpool.RabbitConnectPool;

public class RabbitConnectionPoolService {

	static {
		initPool(null);
	}

	public static RabbitConnectPool getPool() {
		return RabbitConnectPool.getInstance();
	}

	/**
	 * 初始化pool
	 * 
	 * @return
	 */
	public static void initPool(RabbitMQConfig c) {
		LoggerUtil.debug("Initializing rabbitMQ connection pool...");
		RabbitMQConfig configItem = null;
		if (c != null) {
			configItem = c;
		} else {
			RabbitMQConfig configs = ConfigService
					.getRabbitMqConfig(RabbitMQConfig.class);
			if (configs != null) {
				configItem = configs;
			}
		}
		if (configItem != null) {
			JMSGConstant.poolConfigs = FastJsonUtil.toJson(configItem);
			LoggerUtil.debug("RabbitMQServer config: "
					+ configItem.getServerIp() + ":" + configItem.getPort());
			RabbitConfig.getInstance().setHostName(configItem.getServerIp());
			RabbitConfig.getInstance().setPort(configItem.getPort());
			RabbitConfig.getInstance().setUserName(configItem.getUserName());
			RabbitConfig.getInstance().setPassWord(configItem.getPassWord());
			RabbitConfig.getInstance().setMaxPoolSize(
					configItem.getMaxPoolSize());
			RabbitConfig.getInstance().setConnectionTimeOut(
					configItem.getConnectionTimeOut());
			RabbitConfig.getInstance().setSendTimeOut(
					configItem.getSendTimeOut());
			RabbitConfig.getInstance().setReceiveTimeOut(
					configItem.getReceiveTimeOut());
			RabbitConfig.getInstance().setRequestedConnectionTimeOut(
					configItem.getRequestedConnectionTimeOut());
			if (configItem.getSendLogBaseDir().isEmpty()) {
				String dir = RabbitConnectionPoolService.class.getResource("/")
						.toString();
				if (dir.endsWith("/") == false) {
					dir += "/";
				}
				String logDir = dir + "../log/rabbitmq_send/";
				RabbitConfig.getInstance().setSendLogBaseDir(logDir);
			} else {
				String dir = configItem.getSendLogBaseDir();
				if (dir.endsWith("/") == false) {
					dir += "/";
				}
				RabbitConfig.getInstance().setSendLogBaseDir(dir);
			}
			LoggerUtil.debug("Set send log base dir: "
					+ RabbitConfig.getInstance().getSendLogBaseDir());

			if (configItem.getFailedLogBaseDir().isEmpty()) {
				String dir2 = RabbitConnectionPoolService.class
						.getResource("/").toString();
				String failedLogDir = "../log/rabbitmq_failed/";
				if (dir2.endsWith("/") == false) {
					dir2 += "/";
				}
				failedLogDir = dir2 + failedLogDir;
				RabbitConfig.getInstance().setFailedLogBaseDir(failedLogDir);
			} else {
				String dir = configItem.getFailedLogBaseDir();
				if (dir.endsWith("/") == false) {
					dir += "/";
				}
				RabbitConfig.getInstance().setFailedLogBaseDir(dir);
			}
			LoggerUtil.debug("Set failed log base dir: "
					+ RabbitConfig.getInstance().getFailedLogBaseDir());

			RabbitConnectPool.getInstance().init();
			LoggerUtil.debug("Initialization of connection pool completed.");
		}
	}
}
