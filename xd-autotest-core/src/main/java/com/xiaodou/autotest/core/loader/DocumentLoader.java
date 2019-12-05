package com.xiaodou.autotest.core.loader;

import java.io.File;

/**
 * @name @see com.xiaodou.autotest.core.loader.DocumentLoader.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月15日
 * @description Document加载器 加载json文件
 * @version 1.0
 */
public interface DocumentLoader {

  /**
   * 从抽象路径读取document
   * 
   * @param documentPath document路径
   * @return document文件
   * @throws Exception 异常
   */
  public String loadDocument(String documentPath) throws Exception;

  /**
   * 从File文件中读取document
   * 
   * @param documentFile document文件
   * @return document文件
   * @throws Exception 异常
   */
  public String loadDocument(File documentFile) throws Exception;

}
