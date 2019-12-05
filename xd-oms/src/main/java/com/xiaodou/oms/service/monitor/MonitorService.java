package com.xiaodou.oms.service.monitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.monitor.PropertiesFiles;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.util.OrderLoggerUtil;

/**
 * 监控conf改动 并修改
 * <p/>
 * Date: 2014/9/26 Time: 17:02
 * 
 * @author Tian.Dong
 */
// @Service
public class MonitorService {

  @Resource
  SpringBeanUpdateService springBeanUpdateService;

  @Resource
  AgentPropertiesUpdateService agentPropertiesUpdateService;

  private boolean isStart = true;

  /**
   * 当前待处理的fileName集合
   */
  private Set<String> files = new HashSet<String>();

  /**
   * 锁 用于禁止多线程同时去做File的reload和Spring update bean
   */
  private final Object lock = new Object();

  public MonitorService() {
    this.start();
  }

  /**
   * key = watcher.take(); 这行代码是阻塞的
   */
  public void start() {
    new Thread() {
      public void run() {
        try {
          Thread.sleep(30000);
        } catch (InterruptedException e) {
          LoggerUtil.error("MonitorService start.Thread sleep异常", e);
        }
        // 先暂停半分钟再开始监听
        String dirPath = MonitorService.class.getClassLoader().getResource("").getPath();
        if (dirPath.contains("WEB-INF/classes/")) {
          dirPath = dirPath.replaceAll("WEB-INF/classes/", "");
        }
        dirPath += "conf/custom/env";
        OrderLoggerUtil.loggerConfModifyInfo("开始监控路径为:" + dirPath);
        Path path = new File(dirPath).toPath();
        WatchService watcher;
        try {
          watcher = FileSystems.getDefault().newWatchService();
          path.register(watcher, StandardWatchEventKinds.OVERFLOW,
              StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE,
              StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
          LoggerUtil.error("MonitorService path.register异常", e);
          return;
        }

        while (isStart) {
          WatchKey key;
          try {
            // watcher.tack()是阻塞的
            key = watcher.take();
          } catch (InterruptedException e) {
            LoggerUtil.error("MonitorService watcher.take异常", e);
            try {
              Thread.sleep(60000);
              continue;
            } catch (InterruptedException e1) {
              continue;
            }
          }
          process(key);
        }
      }
    }.start();

  }

  private void process(WatchKey key) {
    for (WatchEvent<?> event : key.pollEvents()) {
      Path fileName = (Path) event.context();
      files.add(fileName.toString());
      new Thread() {
        public void run() {
          reload();
        }
      }.start();
    }
    key.reset();
  }

  /**
   * 由于同时修改多个文件，会触发多个文件修改的事件 需要做到几秒之后，reload所有修改过的文件，并对Spring的bean只refresh一次
   * 故每个线程都先睡几秒觉，等所有文件修改时间触发完毕并加到files集合里 再争抢一段同步代码
   */
  private void reload() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      LoggerUtil.error("MonitorService reload sleep3000异常", e);
    }
    synchronized (lock) {
      if (files.size() == 0) {
        return;
      }
      // reload配置文件
      for (Iterator<String> iterator = files.iterator(); iterator.hasNext();) {
        String fileName = iterator.next();
        OrderLoggerUtil.loggerConfModifyInfo("文件名:" + fileName);
        try {
          this.copyToWebInfoClassesConf(fileName);
        } catch (Exception e) {
          LoggerUtil.error("copyToWebInfoClassesConf失败", e);
          OrderLoggerUtil.loggerConfModifyInfo("copyToWebInfoClassesConf失败:" + fileName);
          continue;
        }
        OrderLoggerUtil.loggerConfModifyInfo("copyToWebInfoClassesConf成功:" + fileName);
        FileUtil util = PropertiesFiles.get(fileName);
        if (util != null) {
          util.reload();
        }
        // 更新Spring
        springBeanUpdateService.refresh(fileName);
        // 更新agent用到的这个文件
        agentPropertiesUpdateService.refresh(fileName);
        iterator.remove();
      }
      OrderLoggerUtil.loggerConfModifyInfo("全部修改完毕.");
    }
  }

  private void copyToWebInfoClassesConf(String fileName) throws Exception {
    String toPath = MonitorService.class.getClassLoader().getResource("").getPath();
    String fromPath = "";
    toPath += "conf/custom/env/" + fileName;
    if (toPath.contains("WEB-INF/classes/")) {
      fromPath = toPath.replaceAll("WEB-INF/classes/", "");
    }
    FileInputStream fis = new FileInputStream(fromPath);
    FileOutputStream fos = new FileOutputStream(toPath);
    byte[] buf = new byte[1024];
    int c;
    while ((c = fis.read(buf)) != -1) {
      fos.write(buf, 0, c);
    }
    fos.flush();
    fis.close();
    fos.close();
  }
}
