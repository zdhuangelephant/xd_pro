package com.xiaodou.mqcr.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.mqcr.model.MessageEntity;
import com.xiaodou.mqcr.model.MessageStatus;
import com.xiaodou.mqcr.model.MessageTrans;
import com.xiaodou.summer.dao.jdbc.ExtendBaseDao;
import com.xiaodou.summer.source.jdbc.datasource.SummerSqlSessionFactory;

@Service("messageEntityService")
public class MessageEntityService extends ExtendBaseDao<MessageEntity> {

  @Autowired(required = false)
  public void setSummerSqlSessionFactory(
      @Qualifier("mcrpSqlSessionFactory") SummerSqlSessionFactory sqlSessionFactory)
      throws Exception {
    super.setSummerSqlSessionFactory(sqlSessionFactory);
  }

  private static Integer UPDATE_OK = 1;

  /**
   * 更新失败记录为成功
   * 
   * @param tag 标记值
   * @return 更新结果
   */
  public boolean updateFailMessage2Succ(String tag) {
    return updateMessageEntity(tag, MessageTrans.FAIL2SUCC);
  }

  /**
   * 更新失败记录为初始化
   * 
   * @param tag
   * @return
   */
  public boolean updateFailMessage2Init(String tag) {
    return updateMessageEntity(tag, MessageTrans.FAIL2INIT);
  }

  /**
   * 更新初始化记录为成功
   * 
   * @param tag 标记值
   * @return 更新结果
   */
  public boolean updateMessage2Succ(String tag) {
    return updateMessageEntity(tag, MessageTrans.SUCC);
  }

  /**
   * 更新初始化记录为失败
   * 
   * @param tag 标记值
   * @return 更新结果
   */
  public boolean updateMessage2Fail(String tag) {
    return updateMessageEntity(tag, MessageTrans.FAIL);
  }

  /**
   * 根据标记值更新记录
   * 
   * @param tag 标记值
   * @return 更新结果
   */
  public boolean updateMessageEntity(String tag, MessageTrans trans) {
    try {
      if (StringUtils.isBlank(tag))
        throw new RuntimeException("[MQCheckRepeat][根据标记值更新记录][标记值为空]");
      trans.setTag(tag);
      return this.getSqlSession().update("MessageEntity.updateMessageEntity", trans.getSqlParam()) == UPDATE_OK;
    } catch (Exception e) {
      LoggerUtil.error("[MQCheckRepeat][根据标记值更新记录][更新失败.tag=" + tag + "]", e);
      return false;
    }

  }

  /**
   * 根据标记值列表更新记录
   * 
   * @param tags 标记值列表
   * @return 更新结果
   */
  public boolean updateMessageEntityList(List<String> tags, MessageTrans trans) {
    if (tags.isEmpty() || tags.size() == 0)
      throw new RuntimeException("[MQCheckRepeat][根据标记值列表更新记录][标记值列表为空]");
    trans.setTagLst(tags);
    return this.getSqlSession()
        .update("MessageEntity.updateMessageEntityList", trans.getSqlParam()) == tags.size();
  }

  /**
   * 根据条件Map查询MessageEntityList
   * 
   * @param cond
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<MessageEntity> getMessageEntityListByCond(Map<String, Object> cond) {
    return this.getSqlSession().selectList("MessageEntity.queryMessageEntity", cond);
  }

  /**
   * 根据tag查询MessageEntity
   * 
   * @return
   */
  public MessageEntity getMessageEntityListByTag(String tag) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("tag", tag);
    cond.put("input", input);
    cond.put("output", MessageEntity.getOutputMap());
    Object MessageEntity = this.getSqlSession().selectOne("MessageEntity.queryMessageEntity", cond);
    return null == MessageEntity ? null : (MessageEntity) MessageEntity;
  }

  /**
   * 插入MessageEntity记录
   * 
   * @param entity MessageEntity实例
   * @return 插入结果
   */
  public boolean insertMessageEntity(MessageEntity entity) {
    if (null == entity) throw new RuntimeException("[MessageEntity][插入记录为空]");
    MessageEntity messageEntity = this.getMessageEntityListByTag(entity.getTag());
    if (messageEntity != null) {
      return this.updateFailMessage2Init(entity.getTag());
    } else {
      int insert = this.getSqlSession().insert("MessageEntity.insertMessageEntity", entity);
      return insert == UPDATE_OK;
    }
  }

  /**
   * 插入新MessageEntity记录
   * 
   * @param tag tag标记
   * @return 插入结果
   */
  public boolean insertNewMessage(String tag) {
    try {
      return insertMessageEntity(new MessageEntity(tag));
    } catch (Exception e) {
      return updateMessageEntity(tag, MessageTrans.FAIL2INIT);
    }
  }

  /**
   * 插入成功MessageEntity记录
   * 
   * @param tag tag标记
   * @return 插入结果
   */
  public boolean insertSuccessMessage(String tag) {
    return insertMessageEntity(new MessageEntity(tag, MessageStatus.SUCCESS.getCode()));
  }

  /**
   * 插入失败MessageEntity记录
   * 
   * @param tag tag标记
   * @return 插入结果
   */
  public boolean insertFailMessage(String tag) {
    return insertMessageEntity(new MessageEntity(tag, MessageStatus.FAIL.getCode()));
  }

  /**
   * 插入指定状态MessageEntity记录
   * 
   * @param tag tag标记
   * @return 插入结果
   */
  public boolean insertFailMessage(String tag, MessageStatus status) {
    return insertMessageEntity(new MessageEntity(tag, status.getCode()));
  }

  /**
   * 是否可以消费该消息
   * 
   * @param tag 消息标志
   * @return 是否能消费
   */
  public boolean canConsumMessage(String tag) {
    MessageEntity messageEntityListByTag = getMessageEntityListByTag(tag);
    return null == messageEntityListByTag ? Boolean.TRUE : MessageStatus.FAIL.getCode().equals(
        messageEntityListByTag.getStatus());
  }

  /**
   * 删除记录
   */
  public int deleteMessage(Map<String, Object> cond) {
    return this.getSqlSession().delete("MessageEntity.deleteMessage", cond);
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
    return deleteMessage(cond);
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
    return deleteMessage(cond);
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
    return deleteMessage(cond);
  }

  /**
   * 清空表
   */
  public void truncateMessage() {
    this.getSqlSession().delete("MessageEntity.truncateMessage");
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
    return deleteMessage(cond);
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
    return deleteMessage(cond);
  }

  /**
   * 删除某一时间段内的消息
   * 
   * @param lower 上限
   * @param upperTime 下限
   * @return
   */
  public int deleteMessageBetween(Timestamp lower, Timestamp upperTime) {
    Map<String, Object> cond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    input.put("insertTimeLower", lower);
    input.put("insertTimeUpper", upperTime);
    cond.put("input", input);
    return deleteMessage(cond);
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
    return deleteMessage(cond);
  }
}
