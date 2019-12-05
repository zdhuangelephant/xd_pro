//package com.xiaodou.autobuild.tool;
//
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.velocity.Template;
//import org.apache.velocity.runtime.RuntimeConstants;
//import org.apache.velocity.runtime.RuntimeInstance;
//import org.apache.velocity.runtime.log.NullLogChute;
//
//import com.google.common.collect.Maps;
//
///**
// * Velocity模板解析类
// * <p/>
// * Date: 2014/5/14 Time: 11:07
// * 
// * @author Tian.Dong
// */
//public class TemplateUtil {
//
//  private String srcPath;
//
//  public String getSrcPath() {
//    return srcPath;
//  }
//
//  public void setSrcPath(String srcPath) {
//    this.srcPath = srcPath;
//  }
//
//  private final static Map<String, RuntimeInstance> riMap = Maps.newConcurrentMap();
//
//  private static final Object clock = new Object();
//
//  private static RuntimeInstance getCPRuntimeInstance(String relativePath) {
//    if (StringUtils.isNotBlank(relativePath)) {
//      RuntimeInstance ri = riMap.get(relativePath);
//      if (ri == null) {
//        synchronized (clock) {
//          ri = new RuntimeInstance();
//          riMap.put(relativePath, ri);
//        }
//      }
//      if (!ri.isInitialized()) {
//        ri.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
//        String path = TemplateUtil.class.getClassLoader().getResource("").getPath() + relativePath;
//        ri.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, path);
//        ri.init();
//      }
//      return ri;
//    }
//    return null;
//  }
//
//  public static Template getTemplate(String relativePath, String fileName) {
//    RuntimeInstance ri = getCPRuntimeInstance(relativePath);
//    Template template = ri.getTemplate(fileName);
//    return template;
//  }
//
//  public Template getTemplate(String fileName) {
//    return getTemplate(srcPath, fileName);
//  }
//}
