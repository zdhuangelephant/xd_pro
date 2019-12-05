package com.xiaodou.jmsg.client.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.client.Constants;
import com.xiaodou.jmsg.client.PersistentMessageQueue;

/**
 * 本地持久化文件写入线程
 * 
 */
public class WriteFileTask implements Runnable {
  private static long lastGenFileTime;
  private static String filePath;
  private static String fileName;

  static {
    InputStream input = null;
    try {
      input =
          Thread.currentThread().getContextClassLoader()
              .getResourceAsStream("conf/custom/env/jmsg-client-output.properties");
      if (null != input) {
        initPath(input);
      } else {
        filePath = System.getProperty("user.dir") + "/jmsgfailedmsglogs/";
      }
    } catch (Exception e) {
      LoggerUtil.error("initialize filePath error", e);
      throw new RuntimeException(e);
    } finally {
      if (null != input) {
        try {
          input.close();
        } catch (IOException e) {}
      }
    }
  }

  @Override
  public void run() {
    commit();
  }

  private void commit() {
    try {
      String msg = PersistentMessageQueue.getInstance().consume();
      if (null == msg) {
        return;
      }
      if (ShouldGenFile()) GenFileName();
      File file = new File(filePath);
      if (!file.exists()) file.mkdirs();
      File dir = new File(file, fileName);

      OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(dir, true), "utf-8");
      BufferedWriter bw = new BufferedWriter(writer);
      while (null != msg) {
        bw.write(msg);
        bw.newLine();
        bw.flush();
        if (ShouldGenFile()) {
          GenFileName();
          break;
        }
        msg = PersistentMessageQueue.getInstance().consume();
      }
      writer.close();
      bw.close();
    } catch (Exception e) {
      LoggerUtil.error("WriteFile Failed", e);
    }
  }

  private boolean ShouldGenFile() {
    long now = System.currentTimeMillis();

    if ((now - lastGenFileTime) < 1 * 2 * 1000) {
      return false;
    }

    lastGenFileTime = now;
    return true;
  }

  private void GenFileName() {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    fileName = Constants.FILE_PREFIX_FOR_JMSG + df.format(new Date()) + ".txt";
  }

  private static void initPath(InputStream input) throws IOException {
    Properties prop = new Properties();
    prop.load(input);
    if (File.separator.equals("/") && prop.containsKey("file_dir_for_linux.path")) {
      filePath = prop.getProperty("file_dir_for_linux.path");
    } else if (!File.separator.equals("/") && prop.containsKey("file_dir_for_windows.path")) {
      filePath = prop.getProperty("file_dir_for_windows.path");
    }
  }
}
