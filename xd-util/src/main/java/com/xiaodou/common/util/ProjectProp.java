package com.xiaodou.common.util;


/**
 * 全局config配置文件
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-1-26
 */
public class ProjectProp {

  /**
   * 配置文件
   */
  private static FileUtil config = FileUtil.getInstance("/conf/custom/notenv/project.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (config == null) synchronized (ProjectProp.class) {
      if (config == null) config = FileUtil.getInstance("/conf/custom/notenv/project.properties");
    }
    return config;
  }

  /**
   * 获取参数值
   * 
   * @param code 参数key
   * @return 参数值
   */
  public static String getParams(String code) {
    return getProperty().getProperties(code);
  }
  
  /**
   * 获取工程groupid
   * @return
   */
  public static String getProjectGroupId(){
	  return getProperty().getProperties("project.groupId");
  }
  
  /**
   * 获取工程artifactId
   * @return
   */
  public static String getProjectArtifactId(){
	  return getProperty().getProperties("project.artifactId");
  }

}
