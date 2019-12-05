package com.xiaodou.jmsg.client.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.xiaodou.amqp.sedecodehelper.CodecHelper;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.Constants;
import com.xiaodou.jmsg.client.PersistentMessageQueue;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.DefaultMessage;

/**
 * 本地持久化文件处理线程
 * 
 */
public class ReadFileTask implements Runnable {

  private static String filePath;

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
    try {
      String[] files = new String[0];
      File fileDir = new File(filePath);
      if (fileDir.exists()) {
        files = fileDir.list();
      }

      List<String> lastFiles = filterFiles(files);
      if (lastFiles.size() > 0 || PersistentMessageQueue.getBlockingQueue().size() > 0) {
        LoggerUtil.debug(String.format("filesSize: %s, BlockingQueueSize : %s ", lastFiles.size(),
            PersistentMessageQueue.getBlockingQueue().size()));
      }

      for (String file : lastFiles) {
        processFile(file);
      }

    } catch (Exception e) {
      LoggerUtil.error("readFile failed", e);
    }
  }

  private void processFile(String file) {
    long time = 0;
    String[] date = file.split("\\.");
    if (date.length > 1) {
      String fileTime = date[0];
      time = transferStringDateToLong("yyyy-MM-dd HH-mm-ss", fileTime);
    }

    if (time != 0 && isNeedReadByFileTime(time)) {
      String fileFullName = filePath + Constants.FILE_PREFIX_FOR_JMSG + file;
      boolean isDeletable = readFileAndSend(fileFullName);
      if (true == isDeletable) {
        File readFile = new File(fileFullName);
        readFile.delete();
        LoggerUtil.debug("delete " + fileFullName + " success");
      } else {
        try {
          Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {}
      }
    }
  }

  private boolean isNeedReadByFileTime(long fileTime) {
    long now = System.currentTimeMillis();
    if ((now - fileTime) < 1 * 5 * 1000) {
      return false;
    }
    return true;
  }

  private long transferStringDateToLong(String formatDate, String date) {
    SimpleDateFormat df = new SimpleDateFormat(formatDate);
    Date dt;
    try {
      dt = df.parse(date);
    } catch (ParseException e) {
      return 0;
    }
    return dt.getTime();
  }

  private boolean readFileAndSend(String file) {
    boolean isSuccess = true;
    BufferedReader bufferedReader = null;
    try {
      InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
      bufferedReader = new BufferedReader(reader);
      boolean isFirstLine = true;

      String msg = bufferedReader.readLine();
      while (null != msg && !"".equals(msg)) {
        boolean isSend = doSendMessage(msg);
        if (false == isSend) {
          if (false == isFirstLine) {
            PersistentMessageQueue.getInstance().putQueue(msg);
          } else {
            isSuccess = false;
            break;
          }
        }
        isFirstLine = false;
        msg = bufferedReader.readLine();
      }

      return isSuccess;
    } catch (Exception e) {
      LoggerUtil.error("send msg failed", e);
      return false;
    } finally {
      if (null != bufferedReader) {
        try {
          bufferedReader.close();
        } catch (IOException e) {}
      }
    }
  }

  private boolean doSendMessage(String msg) throws IOException {
    DefaultMessage defaultMessage = FastJsonUtil.fromJson(msg, DefaultMessage.class);
    try {
      RabbitMQSender instance = (RabbitMQSender) RabbitMQSender.getInstance();
      instance.send(defaultMessage, defaultMessage.getCustomTag(), defaultMessage.getMessageName(),
          false);
    } catch (Exception ex) {
      return false;
    }
    return true;
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

  private List<String> filterFiles(String[] files) {
    if (files.length == 0) {
      return Arrays.asList(files);
    }
    List<String> lastFiles = new ArrayList<String>();
    for (String file : files) {
      if (!file.startsWith(Constants.FILE_PREFIX_FOR_JMSG)) continue;
      file = file.substring(Constants.FILE_PREFIX_FOR_JMSG.length());
      lastFiles.add(file);
    }
    return lastFiles;
  }
}
