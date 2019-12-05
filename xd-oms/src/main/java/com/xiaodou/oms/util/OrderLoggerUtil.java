package com.xiaodou.oms.util;

import org.apache.log4j.Logger;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.util.model.AgentMessageSendEntity;
import com.xiaodou.oms.util.model.FlowExcutorEntity;
import com.xiaodou.oms.util.model.IMEntity;
import com.xiaodou.oms.util.model.MessageEntity;
import com.xiaodou.oms.util.model.QueryPaymentEntity;


public class OrderLoggerUtil extends LoggerUtil {
  /**
   * 订单管理模块的Logger
   */
  public static void orderInfo(Object msg) {
    Logger.getLogger("orderLogger").info(msg);
  }

  /**
   * 缓存存取的Logger
   */
  public static void cacheInfo(Object msg) {
    Logger.getLogger("cacheLogger").info(msg);
  }

  /**
   * debugLogger
   */
  public static void debug(Object msg) {
    Logger.getLogger("debugLogger").debug(msg);
  }

  /**
   * 流程信息日志
   */
  public static void flowExcutorInfo(FlowExcutorEntity msg) {
    Logger.getLogger("flowExcutorLogger").info(msg);
  }

  /**
   * 异步消息日志
   */
  public static void messageInfo(MessageEntity messageEntity) {
    Logger.getLogger("messageLogger").info(messageEntity);
  }

  /**
   * 记录qq日志
   */
  public static void imInfo(String msg) {
    Logger.getLogger("imLogger").info(msg);
  }

  /**
   * 记录qq日志
   */
  public static void imInfo(IMEntity msg) {
    Logger.getLogger("imLogger").info(msg);
  }

  public static void loggerConfModifyInfo(String msg) {
    Logger.getLogger("confModify").info(msg);
  }

  public static void loggerRabbitInfo(String msg) {
    Logger.getLogger("rabbitLogger").info(msg);
  }

  public static void loggerQueryPaymentInfo(String msg) {
    Logger.getLogger("queryPaymentLogger").info(msg);
  }

  public static void loggerQueryPaymentInfo(QueryPaymentEntity entity) {
    Logger.getLogger("queryPaymentLogger").info(entity);
  }

  public static void loggerAgentMessageSendInfo(String msg) {
    Logger.getLogger("agentMessageSendLogger").info(msg);
  }

  public static void loggerAgentMessageSendInfo(AgentMessageSendEntity msg) {
    Logger.getLogger("agentMessageSendLogger").info(msg);
  }
}
