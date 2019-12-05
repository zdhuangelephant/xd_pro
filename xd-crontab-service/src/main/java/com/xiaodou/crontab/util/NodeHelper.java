package com.xiaodou.crontab.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;

import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.crontab.util.NodeHelper.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月2日
 * @description 节点帮助类
 * @version 1.0
 */
public class NodeHelper {

  /** CONFIG_PATH_TEMP CONFIG路径模板 */
  private static final String CONFIG_PATH_TEMP = "/config/%s";
  /** EXCUTOR_PATH_TEMP EXCUTOR路径模板 */
  private static final String EXCUTOR_PATH_TEMP = "/excutor/%s";
  /** JOB_PATH_TEMP JOB路径模板 */
  private static final String JOB_PATH_TEMP = "/job/%s";
  /** RESET_PATH_TEMP RESET路径模板 */
  private static final String RESET_PATH_TEMP = "/reset/%s";
  /** OK_PATH_TEMP OK路径模板 */
  private static final String OK_PATH_TEMP = "/ok/%s";

  /**
   * 删除节点操作
   */
  public static void deleteNode(CuratorFramework client, NodeCache cache) {
    try {
      client.delete().forPath(cache.getCurrentData().getPath());
    } catch (Exception e) {
      LoggerUtil.error("删除重置节点失败", e);
    }
  }

  /** 获取配置路径 */
  public static String getConfigPath(String configId) {
    return String.format(CONFIG_PATH_TEMP, configId);
  }

  /** 获取执行路径 */
  public static String getExcutorPath(String configId) {
    return String.format(EXCUTOR_PATH_TEMP, configId);
  }

  /** 获取任务路径 */
  public static String getJobPath(String configId) {
    return String.format(JOB_PATH_TEMP, configId);
  }

  /** 获取重置路径 */
  public static String getResetPath(String configId) {
    return String.format(RESET_PATH_TEMP, configId);
  }

  /** 获取刷新路径 */
  public static String getOkPath(String configId) {
    return String.format(OK_PATH_TEMP, configId);
  }
}
