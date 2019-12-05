package com.xiaodou.webfetch.util;

import java.io.File;

import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.webfetch.util.Path.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月11日
 * @description Path拼接工具类
 * @version 1.0
 */
public class PathUtil {

  private static String BASE_PATH = System.getProperty("user.dir");

  public static String defaultBasePath() {
    return BASE_PATH;
  }

  public static Path path() {
    return path(null);
  }

  public static Path path(String basePath) {
    Path _path = new Path();
    if (org.apache.commons.lang.StringUtils.isNotBlank(basePath)) {
      _path._basePath = basePath;
    }
    return _path;
  }

  /**
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年12月12日
   * @description 路径对象
   * @version 1.0
   */
  public static class Path {
    private String _basePath = StringUtils.EMPTY;
    private StringBuilder _path = new StringBuilder(200);

    /** 不允许外部构造 */
    private Path() {}

    public Path in(String path) {
      this._path.append(File.separator).append(path);
      return this;
    }

    public String basePath() {
      return org.apache.commons.lang.StringUtils.isNotBlank(_basePath) ? _basePath : BASE_PATH;
    }

    public String path() {
      return _path.insert(0, basePath()).toString();
    }
  }

}
