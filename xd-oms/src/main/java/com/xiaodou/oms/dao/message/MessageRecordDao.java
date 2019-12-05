
package com.xiaodou.oms.dao.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.oms.util.model.MessageRecord;
import com.xiaodou.summer.dao.BaseDao;


/**
 * @author Guanguo.Gao
 * @version V1.0
 * @ClassName: MessageRecordDao
 * @Description: 消息mq_record中的记录处理
 * @date 2014年9月3日 下午1:01:31
 */
@Repository("messageRecordDao")
public class MessageRecordDao extends BaseDao<MessageRecord> {

  /**
   * 根据条件Map查询MessageRecordList
   *
   * @param cond
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<MessageRecord> getMessageRecordListByCond(Map<String, Object> cond) {
    return this.getSqlSession().selectList("MessageRecord.queryMessageRecord", cond);
  }

  /**
   * 根据条件Map查询MessageTagList
   *
   * @param cond
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<String> getMessageTagListByCond(Map<String, Object> cond) {
    List<String> ls = this.getSqlSession().selectList("MessageRecord.queryMessageTag", cond);
    return ls;
  }

  /**
   * 根据tag 获得记录
   *
   * @param cond
   * @return
   */
  public MessageRecord getMessageRecordByTag(Map<String, Object> cond) {
    return (MessageRecord) this.getSqlSession().selectOne("MessageRecord.queryMessageRecord", cond);
  }

  public MessageRecord getMessageRecordByTag(String tag) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<String, Object>();
    input.put("tag", tag);
    output.put("id", "");
    output.put("tag", "");
    output.put("productLine", "");
    output.put("insertTime", "");
    output.put("content", "");
    output.put("messageName", "");
    cond.put("input", input);
    cond.put("output", output);
    return (MessageRecord) this.getSqlSession().selectOne("MessageRecord.queryMessageRecord", cond);

  }

  /**
   * 插入MessageRecord记录
   *
   * @param entity MessageRecord实例
   * @return 插入结果
   */
  public boolean insertMessageRecord(MessageRecord entity) {
    if (null == entity) throw new RuntimeException("[待向第三方下单][紧急][插入记录为空]");
    int insert = this.getSqlSession().insert("MessageRecord.insertMessageRecord", entity);
    return insert == 1;
  }

  /**
   * 删除记录
   */
  public int deleteMessage(Map<String, Object> cond) {
    return this.getSqlSession().delete("MessageRecord.deleteMessage", cond);
  }

  /**
   * 清空表
   */
  public void truncateMessage() {
    this.getSqlSession().delete("MessageRecord.truncateMessage");
  }


}
