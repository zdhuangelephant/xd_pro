package com.xiaodou.dao.behavior;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.behavior.UserExamRecord;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name UserExamRecordDao CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年1月5日
 * @description UserExamRecordMapper 改造而来
 * @version 1.0
 */
@Repository
public class UserExamRecordDao extends ProcessBaseDao<UserExamRecord> {

  /**
   * @param id
   * @return 返回影响的结果 1：成功 ，0：失败
   */
  public int deleteByPrimaryKey(String id) {
    int result =
        this.getSqlSession().delete(this.getEntityClass().getSimpleName() + ".deleteByPrimaryKey",
            id);
    return result;
  }

  /**
   * @param UserExamRecord record
   * @return 返回影响的结果 1：成功 ，0：失败
   */
  public int insert(UserExamRecord record) {

    int addEntity =
        this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".insert", record);
    return addEntity;
  }

  /**
   * @param UserExamRecord record
   * @return 返回影响的结果 1：成功 ，0：失败
   */
  public int insertSelective(UserExamRecord record) {
    int addEntity =
        this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".insertSelective",
            record);
    return addEntity;
  }

  /**
   * @param id
   * @return UserExamRecord
   */
  public UserExamRecord selectByPrimaryKey(String id) {
    return (UserExamRecord) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".selectByPrimaryKey", id);
  }

  /**
   * @param id
   * @param uid
   * @return UserExamRecord
   */
  public UserExamRecord selectByUidAndExamId(String id, String uid) {
    IQueryParam param = new QueryParam();
    param.addInput("id", id);
    param.addInput("uid", uid);
    param.addOutputs(CommUtil.getAllField(UserExamRecord.class));

    return this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".selectByUidAndExamId", super.getCondWrap(param));
  }

  /**
   * @param paperId
   * @param uid
   * @return UserExamRecord
   */
  public UserExamRecord selectByUidAndPaperId(String paperId, String uid) {
    IQueryParam param = new QueryParam();
    param.addInput("paperId", paperId);
    param.addInput("uid", uid);
    param.addOutputs(CommUtil.getAllField(UserExamRecord.class));
    List<UserExamRecord> recordList =
        this.getSqlSession().selectList(
            this.getEntityClass().getSimpleName() + ".selectByUidAndPaperId",
            super.getCondWrap(param));
    if (null == recordList || recordList.isEmpty()) {
      return null;
    }
    return recordList.get(0);
  }

  /**
   * @param userId
   * @param subject
   * @return List<UserExamRecord>
   */
  public List<UserExamRecord> selectByUidAndSubjectId(String userId, String subject) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("subject", subject);
    param.addOutputs(CommUtil.getAllField(UserExamRecord.class));

    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByUidAndSubjectId",
        super.getCondWrap(param));

  }

  /**
   * @param userId
   * @param subject
   * @param examTypeId
   * @return List<UserExamRecord>
   */
  public List<UserExamRecord> selectByUidSubjectIdAndExamTypeId(String userId, String subject,
      String examTypeId) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("subject", subject);
    param.addInput("examTypeId", examTypeId);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByUidSubjectIdAndExamTypeId",
        super.getCondWrap(param));
  }

  /**
   * @param params
   * @return List<UserExamRecord>
   */
  public List<UserExamRecord> selectByNotUidSubjectIdAndExamTypeId(Map<String, Object> params) {

    IQueryParam param = new QueryParam();
    param.addInputs(params);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByNotUidSubjectIdAndExamTypeId",
        super.getCondWrap(param));
  }

  /**
   * @param paperId
   * @return List<UserExamRecord>
   */
  public List<UserExamRecord> selectByPaperId(String paperId) {
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByPaperId", paperId);
  }

  /**
   * @param record
   * @return 返回影响的结果 1：成功 ，0：失败
   */
  public int updateByPrimaryKeySelective(UserExamRecord record) {
    int result =
        this.getSqlSession().update(
            this.getEntityClass().getSimpleName() + ".updateByPrimaryKeySelective", record);
    return result;
  }

  /**
   * @param record
   * @return 返回影响的结果 1：成功 ，0：失败
   */
  public int updateByPrimaryKey(UserExamRecord record) {
    int result =
        this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateByPrimaryKey",
            record);
    return result;
  }

  /**
   * @param condition
   * @return List<UserExamRecord>
   */
  public List<UserExamRecord> selectExamCostByExamCost(Map<String, Object> cond) {

    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectExamCostByExamCost", cond);
  }

}
