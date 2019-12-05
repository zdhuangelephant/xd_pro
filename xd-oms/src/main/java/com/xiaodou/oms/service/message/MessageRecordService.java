package com.xiaodou.oms.service.message;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.oms.dao.OperationType;
import com.xiaodou.oms.dao.OperationTypeWrapper;
import com.xiaodou.oms.dao.message.BizMessageDao;
import com.xiaodou.oms.dao.message.MessageRecordDao;
import com.xiaodou.oms.util.CollectionUtil;
import com.xiaodou.oms.util.model.MessageRecord;
import com.xiaodou.oms.vo.input.task.CheckMessageBatchPojo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

@Service("messageRecordService")
public class MessageRecordService {

  //消息dao
  @Resource
  private BizMessageDao bizMessageDao;

  @Resource
  private MessageRecordDao recordDao;

  @Resource
  RabbitMQSender rabbitMQSender;


  /**
   * 根据tag查询MessageRecord
   *
   * @return
   */
  public MessageRecord getMessageRecordListByTag(String tag) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("tag", tag);
    cond.put("input", input);
    cond.put("output", MessageRecord.getOutputMap());
    return recordDao.getMessageRecordByTag(cond);
  }


  /**
   * 插入新MessageRecord记录
   *
   * @param tag         tag标记
   * @param productLine 所属业务线
   * @return 插入结果
   */
  public boolean insertNewMessage(String tag, String productLine) {
    try {
      Timestamp currTime = new Timestamp(System.currentTimeMillis());
      return recordDao.insertMessageRecord(new MessageRecord(tag, productLine, currTime));
    } catch (Exception e) {
      LoggerUtil.error("recordDao.insertMessageRecord异常", e);
      return false;
    }
  }

  /**
   * 插入新MessageRecord记录
   *
   * @return 插入结果
   */
  public boolean insertNewMessage(MessageRecord messageRecord) {
    try {
      return recordDao.insertMessageRecord(messageRecord);
    } catch (Exception e) {
      LoggerUtil.error("recordDao.insertMessageRecord异常", e);
      return false;
    }
  }


  /**
   * 根据TagList删除数据
   *
   * @param tagList TagList
   */
  public int deleteMessageByTagList(List<String> tagList) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("listTag", tagList);
    cond.put("input", input);
    return recordDao.deleteMessage(cond);
  }

  /**
   * 根据Tag删除数据
   *
   * @param tag Tag
   */
  public int deleteMessageByTag(String tag) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("tag", tag);
    cond.put("input", input);
    return recordDao.deleteMessage(cond);
  }

  /**
   * 根据StatusList删除数据
   *
   * @param statusList 状态列表
   */
  public int deleteMessageByStatusList(List<String> statusList) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("listStatus", statusList);
    cond.put("input", input);
    return recordDao.deleteMessage(cond);
  }

  /**
   * 删除某一时间点之前的消息
   *
   * @param upperTime 下限
   * @return
   */
  public int deleteMessageBefore(Timestamp upperTime) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("insertTimeUpper", upperTime);
    cond.put("input", input);
    return recordDao.deleteMessage(cond);
  }

  /**
   * 删除某一时间点之后的消息
   *
   * @param lower 上限
   * @return
   */
  public int deleteMessageAfter(Timestamp lower) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("insertTimeLower", lower);
    cond.put("input", input);
    return recordDao.deleteMessage(cond);
  }

  /**
   * 删除某一时间段内的消息
   *
   * @param lower     上限
   * @param upperTime 下限
   * @return
   */
  public int deleteMessageBetween(Timestamp lower, Timestamp upperTime) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("insertTimeLower", lower);
    input.put("insertTimeUpper", upperTime);
    cond.put("input", input);
    return recordDao.deleteMessage(cond);
  }

  /**
   * 业务系统批处理用根据状态列表,截止时间上线删除Limit长度消息
   *
   * @param upperTimer
   * @param limit
   * @return
   */
  public int deleteLimitMessage(Timestamp upperTimer, Integer limit) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("insertTimeUpper", upperTimer);
    input.put("limit", limit);
    cond.put("input", input);
    return recordDao.deleteMessage(cond);
  }

  /**
   * @param productLine
   * @throws
   * @Description: 消息比对
   */
  public ResultInfo checkMessageLostThenSend(String productLine) {
    OperationTypeWrapper.getWrapper().setValue(OperationType.READ);
    if (null == productLine) {
      throw new RuntimeException("[消息校验失败[业务线代码为空]");
    }

    //获得时间
    Long nowTime = System.currentTimeMillis();
    //消息比对多长ms之前的数据 触发时的10分钟前
    Long messageUpperTime = nowTime - Long.parseLong(ConfigProp.getParams("oms.messageCheckTimeUpper"));
    //触发时的10分钟前 减去 10分钟
    Long messageLowerTime = messageUpperTime - Long.parseLong(ConfigProp.getParams("oms.messageCheckInterval"));

    //查询条件
    Map<String, Object> cond = new HashMap<>();
    Map<String, Object> input = new HashMap<>();
    input.put("productLine", productLine);    //产品线
    input.put("insertTimeLower", new Timestamp(messageLowerTime));
    input.put("insertTimeUpper", new Timestamp(messageUpperTime));
    cond.put("input", input);

    //oms 数据
    List<String> omsTagList = recordDao.getMessageTagListByCond(cond);
    if (omsTagList == null || omsTagList.size() == 0) {
      return new ResultInfo(ResultType.SUCCESS);
    }

    //需要延迟的毫秒数，因为从oms发送到业务需要经过一定的时间，这个主要是rabbitmq的处理时间，
    //rabbitmq有三次重试机制，每次大约是5s，如果负载比较严重的话，等待时间可能在1-2分钟
    //这样设置增加5分钟的延迟，基本覆盖了消息的延迟。
    Long delayTime = 5 * 60 * 1000L;
    //业务库数据查询
    Long bizMessageUpperTime = messageUpperTime + delayTime;
    Map<String, Object> bizCondMap = new HashMap<>();
    bizCondMap.put("productLine", productLine);
    bizCondMap.put("checkTimeLower", new Timestamp(messageLowerTime));
    bizCondMap.put("checkTimeUpper", new Timestamp(bizMessageUpperTime));
    //业务收到的tag列表
    List<String> bizTagList = bizMessageDao.queryBizTagList(bizCondMap);
    //获得结果集
    List<String> resultList = CollectionUtil.compareAndReturnFirstPlus(omsTagList, bizTagList);
    //有结果报警
    if (resultList != null && resultList.size() > 0) {
      for (String tag : resultList) {
        MessageRecord messageRecord = recordDao.getMessageRecordByTag(tag);
        try {
          //发送异步消息 并记录、报警
          MessageAgentSendService.send(productLine, messageRecord);
        } catch (Exception e) {
          LoggerUtil.error("MessageAgentSendService.send异常", e);
        }
      }
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  /**
   * productLine   05
   * start         开始时间字符串
   * end           结束时间字符串
   */
  public ResultInfo checkMessageLostThenSend(CheckMessageBatchPojo pojo) throws ParseException {
    OperationTypeWrapper.getWrapper().setValue(OperationType.READ);
    if (null == pojo.getProductLine()) {
      throw new RuntimeException("[消息校验失败[业务线代码为空]");
    }
    if (StringUtils.isNotBlank(pojo.getEndTime()) && StringUtils.isNotBlank(pojo.getStartTime())) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date startTime = sdf.parse(pojo.getStartTime());
      Date endTime = sdf.parse(pojo.getEndTime());
      return checkMessageLostThenSend(pojo.getProductLine(), startTime.getTime(), endTime.getTime());
    } else {
      //获得时间
      Long nowTime = System.currentTimeMillis();
      //消息比对多长ms之前的数据 触发时的10分钟前
      Long messageUpperTime = nowTime - Long.parseLong(ConfigProp.getParams("oms.messageCheckTimeUpper"));
      //触发时的10分钟前 减去 10分钟
      Long messageLowerTime = messageUpperTime - Long.parseLong(ConfigProp.getParams("oms.messageCheckInterval"));
      return checkMessageLostThenSend(pojo.getProductLine(), messageLowerTime, messageUpperTime);
    }
  }


  private ResultInfo checkMessageLostThenSend(String productLine, long start, long end) {
    OperationTypeWrapper.getWrapper().setValue(OperationType.READ);
    if (null == productLine) {
      throw new RuntimeException("[消息校验失败[业务线代码为空]");
    }

    //时间上限
    Long messageUpperTime = end;
    //时间下限
    Long messageLowerTime = start;

    //查询条件
    Map<String, Object> cond = new HashMap<>();
    Map<String, Object> input = new HashMap<>();
    input.put("productLine", productLine);    //产品线
    input.put("insertTimeLower", new Timestamp(messageLowerTime));
    input.put("insertTimeUpper", new Timestamp(messageUpperTime));
    cond.put("input", input);

    //oms 数据
    List<String> omsTagList = recordDao.getMessageTagListByCond(cond);
    if (omsTagList == null || omsTagList.size() == 0) {
      return new ResultInfo(ResultType.SUCCESS);
    }

    //需要延迟的毫秒数，因为从oms发送到业务需要经过一定的时间，这个主要是rabbitmq的处理时间，
    //rabbitmq有三次重试机制，每次大约是5s，如果负载比较严重的话，等待时间可能在1-2分钟
    //这样设置增加5分钟的延迟，基本覆盖了消息的延迟。
    Long delayTime = 5 * 60 * 1000L;
    //业务库数据查询
    Long bizMessageUpperTime = messageUpperTime + delayTime;
    Map<String, Object> bizCondMap = new HashMap<>();
    bizCondMap.put("productLine", productLine);
    bizCondMap.put("checkTimeLower", new Timestamp(messageLowerTime));
    bizCondMap.put("checkTimeUpper", new Timestamp(bizMessageUpperTime));
    //业务收到的tag列表
    List<String> bizTagList = bizMessageDao.queryBizTagList(bizCondMap);
    //获得结果集
    List<String> resultList = CollectionUtil.compareAndReturnFirstPlus(omsTagList, bizTagList);
    //有结果报警
    if (resultList != null && resultList.size() > 0) {
      for (String tag : resultList) {
        MessageRecord messageRecord = recordDao.getMessageRecordByTag(tag);
        try {
          //发送异步消息 并记录、报警
          MessageAgentSendService.send(productLine, messageRecord);
        } catch (Exception e) {
          LoggerUtil.error("MessageAgentSendService.send异常", e);
        }
      }
    }
    return new ResultInfo(ResultType.SUCCESS);
  }


}
