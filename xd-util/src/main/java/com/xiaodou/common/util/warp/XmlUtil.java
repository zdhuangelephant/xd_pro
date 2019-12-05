package com.xiaodou.common.util.warp;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.DefaultMapper;

import java.util.Map;

/**
 * Xml解析 转换
 * 
 * Date: 2014-5-7 Time: 下午2:12:30
 * 
 * @author tian.dong@corp.elong.com
 */
@SuppressWarnings({"unchecked", "deprecation"})
public class XmlUtil {

  /**
   * 对象转xml
   * 
   * @param obj
   * @return
   */
  public static String toXml(Object obj) {
    XStream xstream = new XStream();
    return xstream.toXML(obj);
  }

  /**
   * 对象转xml
   * 
   * @param obj
   * @param isHaveAnnotation
   * @return
   */
  public static String toXml(Object obj, boolean isHaveAnnotation) {
    XStream xstream = new XStream();

    if (isHaveAnnotation) {
      // 通过注解方式的，一定要有这句话
      xstream.processAnnotations(obj.getClass());
    }
    return xstream.toXML(obj);
  }


  /**
   * xml转对象
   * 
   * @param xml
   * @return
   */
  public static <T> T fromXml(String xml) {
    XStream xstream = new XStream(new DomDriver());
    T obj = (T) xstream.fromXML(xml);
    return obj;
  }

  /**
   * xml转对象
   * 
   * @param xml
   * @param cls
   * @param isHaveAnnotation
   * @return
   */
  public static <T> T fromXml(String xml, Class<T> cls, boolean isHaveAnnotation) {
    XStream xstream = new XStream(new DomDriver());
    if (isHaveAnnotation) {
      xstream.processAnnotations(cls);
    }
    T obj = (T) xstream.fromXML(xml);
    return obj;
  }

  public static String mapToXml(Map<String, Object> request) {
    return mapToXml(request, "request");
  }

  public static String mapToXml(Map<String, Object> request, String alias) {
    XStream xStream = new XStream();
    xStream.registerConverter(new MapCustomConverter(new DefaultMapper(XmlUtil.class
        .getClassLoader())));
    xStream.alias(alias, Map.class);
    return xStream.toXML(request);
  }

  public static Map<String, Object> xmlToMap(String responseXml) {
    XStream xStream = new XStream();
    xStream.registerConverter(new MapCustomConverter(new DefaultMapper(XmlUtil.class
        .getClassLoader())));
    xStream.alias("response", Map.class);
    return (Map<String, Object>) xStream.fromXML(responseXml);
  }

}
