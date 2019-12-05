package com.xiaodou.common.util.log;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.xiaodou.common.util.log.model.ActionEntity;
import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.log.model.BatchProcessEntity;
import com.xiaodou.common.util.log.model.IAlarmEntity;
import com.xiaodou.common.util.log.model.InOutEntity;
import com.xiaodou.common.util.log.model.MessageEntity;
import com.xiaodou.common.util.log.model.OutInEntity;
import com.xiaodou.common.util.log.model.SmsEntity;
import com.xiaodou.common.util.log.model.TraceEntity;

public class LoggerUtil {
  /**
   * debugLogger
   */
  public static void debug(Object msg) {
    debug("debugLogger", msg);
  }

  /**
   * 记录本系统调用外部系统接口的Logger
   */
  public static void inOutInfo(InOutEntity msg) {
    info("inOutLogger", msg);
  }

  /**
   * 记录外部系统调用本系统接口的Logger
   */
  public static void outInInfo(OutInEntity msg) {

    info("outInLogger", msg);
  }

  /**
   * 日志分析记录Logger
   */
  @Deprecated
  public static void actionInfo(Object msg) {
    info("actionLogger", msg);
  }

  /**
   * 日志分析记录Logger
   */
  public static void actionInfo(ActionEntity msg) {
    info("actionLogger", msg);
  }
  /**
   * 日志分析记录Logger
   */
  public static void traceInfo(TraceEntity msg) {
    info("traceLogger", msg);
  }
  /**
   * 记录本系统调用外部系统接口的Logger
   */
  @Deprecated
  public static void inOutInfo(String msg) {
    info("inOutLogger", msg);
  }

  /**
   * 记录外部系统调用本系统接口的Logger
   */
  @Deprecated
  public static void outInInfo(String msg) {
    info("outInLogger", msg);
  }

  /** 短信模块的Logger */
  public static void smInfo(SmsEntity entity) {
    info("smLogger", entity);
  }

  /** 消息队列模块的Logger */
  public static void messageInfo(MessageEntity<?> entity) {
    info("messageLogger", entity);
  }

  /** 批处理模块的Logger */
  public static void batchProcessInfo(BatchProcessEntity entity) {
    info("batchProcessLogger", entity);
  }

  /**
   * 缓存存取的Logger
   */
  public static void cacheInfo(Object msg) {
    info("cacheLogger", msg);
  }

  /**
   * 缓存存取的Logger --记录异常信息
   */
  public static void cacheInfo(Object msg, Throwable throwable) {
    info("cacheLogger", msg);
    error(msg.toString(), throwable);
  }

  /** 需人工干预的报警 - 仅仅记录alarm */
  public static void logAlarmInfo(IAlarmEntity entity) {
    Logger.getLogger("alarmLogger").info(entity.getLoggerEntity());
  }

  /** 需人工干预的报警 - 记alarm和发短信邮件的报警 */
  public static void alarmInfo(AlarmEntityImpl entity) {
    Logger.getLogger("alarmLogger").info(entity.getLoggerEntity());
    DashBoardAlarm.alarm(entity);
  }

  /** 需人工干预的报警 - 记alarm和发短信邮件的报警 */
  @Deprecated
  public static void alarmInfo(String msg, AlarmEntityImpl entity) {
    Logger.getLogger("alarmLogger").info(msg);
    DashBoardAlarm.alarm(entity);
  }

  /** 需人工干预的报警 - 记alarm日志的报警 */
  @Deprecated
  public static void alarmInfo(String msg) {
    Logger.getLogger("alarmLogger").info(msg);
  }

  public static void printAlarm(Logger logger, String msg, Exception e) {
    if (msg != null) {
      logger.info(msg);
    }
    if (e != null) {
      ByteArrayOutputStream buf = new ByteArrayOutputStream();
      e.printStackTrace(new PrintWriter(buf, true));
      logger.error(buf.toString());
    }
  }

  /** errorLogger */
  public static void error(String msg, Exception e) {
    printError(Logger.getLogger("errorLogger"), msg, e);
  }

  /**
   * errorLogger
   */
  public static void error(String msg, Throwable e) {
    printError(Logger.getLogger("errorLogger"), msg, e);
  }

  public static void printError(Logger logger, String msg, Throwable e) {
    if (null != logger && logger.isEnabledFor(Level.ERROR)) {
      if (msg != null) {
        logger.error(msg);
      }
      if (e != null) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        e.printStackTrace(new PrintWriter(buf, true));
        logger.error(buf.toString());
      }
    }
  }

  private static void debug(String logger, Object msg) {
    if (null != logger && Logger.getLogger(logger).isDebugEnabled()) {
      Logger.getLogger(logger).debug(msg);
    }
  }

  private static void info(String logger, Object msg) {
    if (null != logger && Logger.getLogger(logger).isInfoEnabled()) {
      Logger.getLogger(logger).info(msg);
    }
  }

}
