package com.xiaodou.webfetch.plugin.phantom;

import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.OSInfo;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.webfetch.util.PhantomProp.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月12日
 * @description Phantom配置项
 * @version 1.0
 */
public class PhantomProp {

  /** CODE_TMP 参数模板 */
  private static String CODE_TMP = "%s.%s";
  /** MACOSX macosx系统前缀 */
  private static String MACOSX = "phantom.plugin.path.macosx";
  /** LINUX linux系统前缀 */
  private static String LINUX = "phantom.plugin.path.linux";
  /** WINDOWS windows系统前缀 */
  private static String WINDOWS = "phantom.plugin.path.windows";
  /** X64 64位后缀 */
  private static String X64 = "x64";
  /** X86 32位后缀 */
  private static String X86 = "x86";
  /** API_JS API JS路径 */
  private static String API_JS = "phantom.plugin.path.apijs";

  /**
   * 配置文件
   */
  private static FileUtil phantomPlugin = FileUtil
      .getInstance("/conf/custom/env/phantom_plugin.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (phantomPlugin == null)
      synchronized (ConfigProp.class) {
        if (phantomPlugin == null)
          phantomPlugin = FileUtil.getInstance("/conf/custom/env/phantom_plugin.properties");
      }
    return phantomPlugin;
  }

  /**
   * 获取插件路径
   * 
   * @return 插件路径
   */
  public static String getPluginPath() {
    String OS = org.apache.commons.lang.StringUtils.EMPTY, ARCH = org.apache.commons.lang.StringUtils.EMPTY;
    if (OSInfo.isX64()) {
      ARCH = X64;
    } else if (OSInfo.isX86()) {
      ARCH = X86;
    }
    if (OSInfo.isMacOSX() || OSInfo.isMacOS()) {
      OS = MACOSX;
    } else if (OSInfo.isWindows()) {
      OS = WINDOWS;
    } else if (OSInfo.isLinux()) {
      OS = LINUX;
    }
    if (StringUtils.isOrBlank(OS, ARCH)) {
      return org.apache.commons.lang.StringUtils.EMPTY;
    }
    return getProperty().getProperties(String.format(CODE_TMP, OS, ARCH));
  }

  /**
   * 获取API脚本路径
   * 
   * @return API脚本
   */
  public static String getApiJsPath() {
    return getProperty().getProperties(API_JS);
  }

}
