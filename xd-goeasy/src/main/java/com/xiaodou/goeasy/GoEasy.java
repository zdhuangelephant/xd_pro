package com.xiaodou.goeasy;

import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.goeasy.publish.PublishListener;
import com.xiaodou.goeasy.publish.Publisher;

public class GoEasy {
  public static String artifactVersion = "0.0";
  private Publisher publisher;

  public GoEasy(String appkey) {
    this.publisher = new Publisher(appkey);
  }

  public void publish(String channel, String content) {
    publish(channel, content, new PublishListener());
  }

  public void publish(String channel, String content, PublishListener publishListener) {
    publish(channel, content, false, publishListener);
  }

  public void publish(String channel, String content, boolean retained) {
    publish(channel, content, retained, new PublishListener());
  }

  public void publish(String channel, String content, boolean retained,
      PublishListener publishListener) {
    this.publisher.publish(channel, content, retained, publishListener);
  }

  public void setHttps(boolean https) {
    this.publisher.setHttps(https);
  }

  public void setHost(String host) {
    this.publisher.setPublisUrl(host + "/goeasy/publish");
  }

  static {
    try {
      Class<Publisher> clazz = Publisher.class;
      String className = clazz.getSimpleName() + ".class";
      String classPath = clazz.getResource(className).toString();
      if (classPath.startsWith("jar")) {
        String manifestPath =
            classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
        Manifest manifest = new Manifest(new URL(manifestPath).openStream());
        Attributes attr = manifest.getMainAttributes();
        String goeasyVersion = attr.getValue("goeasy-sdk-artifact-version");
        if (goeasyVersion != null) artifactVersion = goeasyVersion;
      }
    } catch (Exception e) {
      LoggerUtil.error("load artifact version failed.", e);
    }
  }
}
