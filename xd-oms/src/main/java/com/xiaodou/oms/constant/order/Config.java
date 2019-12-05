package com.xiaodou.oms.constant.order;

import com.xiaodou.oms.util.OrderLoggerUtil;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * 配置文件中的数据
 * 
 * @author Administrator
 * 
 */
public class Config {

	static Properties config = null;
	static Properties promoter_config = null;

	// <文件名前缀, Properties> <config, configPropertis><promoter_config,
	// promoter_configP,,,>
	static Map<String, Properties> mapProperties = new HashMap<String, Properties>();


	@PostConstruct
	public void autoLoadConfig() {
		if (null == mapProperties) {
			OrderLoggerUtil.alarmInfo("没有任何*.propeties文件");
			return;
		}
		Set<String> keySet = mapProperties.keySet();
		Iterator<String> itr = keySet.iterator();
		while (itr.hasNext()) {
			String fileName = itr.next();
			try {
				getConfig(fileName, "test");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				OrderLoggerUtil.error("error loading config file", e);
			}
		}
	}

	/*
	 * public static String getMyPath(){ try { return getConfig("config",
	 * "class.path") + "||" + path1; } catch (IOException e) { // TODO
	 * Auto-generated catch block LoggerUtil.error("error loading config file",
	 * e); }
	 * 
	 * return "error loading config file"; }
	 */

	public static String getConfig(String propertyName) throws IOException {
		return getConfig("config_order.properties", propertyName);
	}

	public static String getConfig(String fileName, String propertyName)
			throws IOException {
		Properties config = mapProperties.get(fileName);
		if (null == config || config.isEmpty()) {
			InputStream in = null;

//			String fullPath = path1/* .substring(1) */+ fileName;

			in = Config.class.getResourceAsStream("/"+fileName); //new BufferedInputStream(new FileInputStream(fullPath));
			config = new Properties();

			config.load(in);
			if (null != in) {
				in.close();
			}
			mapProperties.put(fileName, config);
//			LoggerUtil.debug("config path:" + fullPath + " load success!");
		}

		String property = config.getProperty(propertyName);
		if (null == property) {
			return null;
		}

		return property;//new String(property.getBytes("ISO8859-1"), "utf8");
	}

	/**
	 * unit test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// String a = Config.getConfig("shop.netease.prikey");
		// String b = Config.getConfig("jiexun.qq.timeout");
	}

	public Map<String, Properties> getMapProperties() {
		return mapProperties;
	}

	public void setMapProperties(Map<String, Properties> mapProperties) {
		this.mapProperties = mapProperties;
	}
}