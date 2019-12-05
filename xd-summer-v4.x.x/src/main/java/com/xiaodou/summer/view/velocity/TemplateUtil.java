package com.xiaodou.summer.view.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.log.NullLogChute;

/**
 * Velocity模板解析类
 * <p/>
 * Date: 2014/5/14 Time: 11:07
 *
 * @author Tian.Dong
 */
public class TemplateUtil {

  private String srcPath;
  private static final Object clock = new Object();
  private static final VelocityContext context = new VelocityContext();

  public static VelocityContext getContext() {
    return context;
  }

  public TemplateUtil(String srcPath) {
    this.srcPath = srcPath;
  }

  private static RuntimeInstance ri = new RuntimeInstance();

  public static Template getTemplate(String relativePath, String fileName) {
    synchronized (clock) {
      if (!ri.isInitialized()) {
        ri.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
        String path = TemplateUtil.class.getClassLoader().getResource("").getPath() + relativePath;
        ri.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, path);
        ri.init();
      }
    }

    Template template = ri.getTemplate(fileName);
    return template;
  }

  public Template getTemplate(String fileName) {
    return getTemplate(srcPath, fileName);
  }
public static void main(String[] args){
  System.out.println(TemplateUtil.class.getClassLoader().getResource("").getPath());
}
}
