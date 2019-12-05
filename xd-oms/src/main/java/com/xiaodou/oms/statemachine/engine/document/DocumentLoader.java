package com.xiaodou.oms.statemachine.engine.document;

import java.io.File;

import org.w3c.dom.Document;

/**
 * <p>Document加载器</p>
 * 加载xml文件
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月16日
 */
public interface DocumentLoader {
  
  /**
   * 从抽象路径读取document
   * @param documentPath document路径
   * @return document文件
   * @throws Exception 异常
   */
  public Document loadDocument(String documentPath) throws Exception;
  
  /**
   * 从File文件中读取document
   * @param documentFile document文件
   * @return document文件
   * @throws Exception 异常
   */
  public Document loadDocument(File documentFile) throws Exception;

}
