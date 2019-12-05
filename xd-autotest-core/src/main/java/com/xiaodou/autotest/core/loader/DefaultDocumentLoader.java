package com.xiaodou.autotest.core.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

/**
 * @name @see com.xiaodou.autotest.core.loader.DefaultDocumentLoader.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月15日
 * @description 默认Document加载器
 * @version 1.0
 */
public class DefaultDocumentLoader implements DocumentLoader {

  @Override
  public String loadDocument(String documentPath) throws DocumentException, FileNotFoundException,
      SAXException, IOException, ParserConfigurationException {
    return loadDocument(new File(documentPath));
  }

  @Override
  public String loadDocument(File documentFile) throws DocumentException, FileNotFoundException,
      SAXException, IOException, ParserConfigurationException {
    BufferedReader reader = null;
    StringBuffer buffer = new StringBuffer(2000);
    try {
      InputStreamReader isr = new InputStreamReader(new FileInputStream(documentFile), "utf8");
      reader = new BufferedReader(isr);
      String line = null;
      while ((line = reader.readLine()) != null) {
        buffer.append(line);
      }
    } catch (Exception e) {
      throw e;
    } finally {
      if (reader != null) {
        reader.close();
        reader = null;
      }
    }
    return buffer.toString();
  }
}
