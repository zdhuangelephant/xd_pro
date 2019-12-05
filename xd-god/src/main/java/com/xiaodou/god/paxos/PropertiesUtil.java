package com.xiaodou.god.paxos;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.xiaodou.god.paxos.PeerConfig.ConfigException;

/**
 * @name PropertiesUtil CopyRright (c) 2018 by
 *       <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月5日
 * @description 读取properties文件的工具类
 * @version 1.0
 */
public class PropertiesUtil {

  static Logger logger = Logger.getLogger(PropertiesUtil.class);

  public static PeerConfig serverConfig;

  public static Properties props;
  public static String FilePath = "/server.properties";
  public static String GodConfigPath = "/godServer.properties";
  public static String configString;

  private static final String CR = System.getProperty("line.separator");


  /**
   * @param path
   * @throws IOException
   */
  static {
    try {
      load(FilePath);
    } catch (IOException e) {
      logger.info("读取配置文件失败");
      e.printStackTrace();
    }
  }

  public PropertiesUtil(String path) {
    PropertiesUtil.FilePath = path;
  }

  /**
   * 加载jar包内的properties文件
   * 
   * @throws IOException
   */
  public static Properties load(String path) throws IOException {
    props = new Properties();
    InputStream ips = StartUpGod.class.getResourceAsStream(path);
    BufferedReader ipss = new BufferedReader(new InputStreamReader(ips));
    props.load(ipss);
    return props;
  }

  /**
   * @param key
   * @return value 获取properties中的某个值
   */
  public static String getValue(String key) {
    return props.getProperty(key);
  }

  /**
   * 返回加载到的props文件
   * 
   * @throws IOException
   */
  public static Properties getProperties() throws IOException {
    if (props == null) {
      load(FilePath);
    }
    return props;
  }

  public static String getLocalAddeess() {
    String localAddress;
    try {
      InetAddress address = InetAddress.getLocalHost();// 获取的是本地的IP地址
      localAddress = address.getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
      localAddress = "无法获得本地ip";
    }
    return localAddress;
  }

  public static synchronized String writeProperties(String keyname, String value) {
    try {
      // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
      // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
      OutputStream fos = new FileOutputStream(FilePath);
      props.setProperty(keyname, value);
      // 以适合使用 load 方法加载到 Properties 表中的格式，
      // 将此 Properties 表中的属性列表（键和元素对）写入输出流
      props.store(fos, "Update '" + keyname + value + "' value");
      // 向string中append
      return appendConfigString(keyname, value);

    } catch (IOException e) {
      System.err.println("属性文件更新错误");
      return "属性文件更新错误";
    }
  }

  public static PeerConfig getGetPeerConfig() {

    serverConfig = new PeerConfig();
    logger.debug("server config file: " + Files.simplifyPath(FilePath));
    try {
      serverConfig.parse(FilePath);
    } catch (ConfigException e) {
      e.printStackTrace();
    }
    return serverConfig;
  }

  //
  public static String getConfigString() {
    if (configString == null || configString.trim().length() <= 0) {
      serverConfig = getGetPeerConfig();
      configString = serverConfig.getConfigString();
    }

    return configString;
  }

  public static String appendConfigString(String keyname, String value) {
    if (configString == null || configString.trim().length() <= 0) {
      serverConfig = getGetPeerConfig();
      configString = serverConfig.getConfigString();
    }
    configString = configString + keyname + value;
    return configString;
  }


  /**
   * @return
   */
  public static Properties getGodServerInfo() {
    Properties props = new Properties();
    InputStream ips = StartUpGod.class.getResourceAsStream(GodConfigPath);
    BufferedReader ipss = new BufferedReader(new InputStreamReader(ips));
    try {
      props.load(ipss);
    } catch (IOException e) {
      logger.info("读取God配置文件失败");
      e.printStackTrace();
    }
    return props;
  }
}
