package com.xiaodou.server.mapi.engine.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * <p>
 * 默认Document加载器
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月16日
 */
public class DefaultDocumentLoader implements DocumentLoader {

  @Override
  public Document loadDocument(String documentPath) throws DocumentException,
      FileNotFoundException, SAXException, IOException, ParserConfigurationException {
    return loadDocument(new File(documentPath));
  }

  @Override
  public Document loadDocument(File documentFile) throws DocumentException,
  FileNotFoundException, SAXException, IOException, ParserConfigurationException {
    DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
    DocumentBuilder newDocumentBuilder = newInstance.newDocumentBuilder();
    return newDocumentBuilder.parse(new FileInputStream(documentFile));
  }

}
