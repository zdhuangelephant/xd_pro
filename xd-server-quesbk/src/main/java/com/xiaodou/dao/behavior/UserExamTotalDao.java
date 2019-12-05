package com.xiaodou.dao.behavior;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name UserExamTotalDao CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年1月5日
 * @description 改造自UserExamTotalMapper
 * @version 1.0
 */
@Repository
public class UserExamTotalDao extends ProcessBaseDao<UserExamTotal> {

  /**
   * @param id
   * @return 返回影响的结果 1：成功 ，0：失败
   */
  public int deleteByPrimaryKey(String id) {
    UserExamTotal entity = new UserExamTotal();
    entity.setId(Long.valueOf(id));
    boolean deleteEntityById = super.deleteEntityById(entity);
    return deleteEntityById ? 1 : 0;

  }

  public int selectAllUserCountBySubjectId(String subject) {
    return this.getSqlSession().insert(
        this.getEntityClass().getSimpleName() + ".selectAllUserCountBySubjectId", subject);
  }

  /**
   * @param record
   * @return integer insert的结果
   */
  public int insert(UserExamTotal record) {
    return super.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".insert", record);
  }

  /**
   * @param record
   * @return integer insert的结果
   */
  public int insertSelective(UserExamTotal record) {
    return super.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".insertSelective",
        record);
  }

  /**
   * @param value
   * @param column
   * @return insert的结果 onDuplicate
   */
  public int updateOrAddEntity(UserExamTotal value, Map<String, Object> column) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("column", column);
    cond.put("value", value);
    return this.getSqlSession().insert(
        this.getEntityClass().getSimpleName() + ".updateOrAddEntity", cond);
  }

  /**
   * @param id
   * @return UserExamTotal
   */
  public UserExamTotal selectByPrimaryKey(String id) {
    return this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".selectByPrimaryKey", id);
  }

  /**
   * @param record
   * @return
   */
  public int updateByPrimaryKeySelective(UserExamTotal record) {
    int result =
        this.getSqlSession().update(
            this.getEntityClass().getSimpleName() + ".updateByPrimaryKeySelective", record);
    return result;
  }

  public int updateByPrimaryKey(UserExamTotal record) {
    int result =
        this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateByPrimaryKey",
            record);
    return result;
  }

  /**
   * @param uid
   * @param subjectId
   * @return
   */
  public UserExamTotal selectByUidAndSubjectId(String uid, String subjectId) {
    IQueryParam param = new QueryParam();
    param.addInput("uid", uid);
    param.addInput("subjectId", subjectId);
    param.addOutputs(CommUtil.getAllField(UserExamTotal.class));

    return this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".selectByUidAndSubjectId",
        super.getCondWrap(param));
  }

  /**
   * @param uid
   * @return List<UserExamTotal>
   */
  public List<UserExamTotal> selectByUid(String uid) {
    return this.getSqlSession().selectList(this.getEntityClass().getSimpleName() + ".selectByUid",
        uid);
  }

  /**
   * @param input
   * @return List<UserExamTotal>
   */
  public List<UserExamTotal> selectByUidNotAll(Map<String, Object> input) {
    IQueryParam param = new QueryParam();
    param.addInputs(input);
    param.addOutputs(CommUtil.getAllField(UserExamTotal.class));
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByUidNotAll", super.getCondWrap(param));
  }

  public List<UserExamTotal> queryByCond(Map<String, Object> cond) {
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(CommUtil.getAllField(UserExamTotal.class));
    return this.getSqlSession().selectList(this.getEntityClass().getSimpleName() + ".queryByCond",
        super.getCondWrap(param));
  }

  public Integer queryTotalRankByCond(String count, String uid, String courseId) {

    Map<String, Object> cond = Maps.newHashMap();
    cond.put("count", count);
    cond.put("uid", uid);
    cond.put("courseId", courseId);
    return this.getSqlSession().insert(
        this.getEntityClass().getSimpleName() + ".queryTotalRankByCond", cond);
  }

  public Integer queryRightRankByCond(String string, String uid, String courseId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("string", string);
    cond.put("uid", uid);
    cond.put("courseId", courseId);
    return this.getSqlSession().insert(
        this.getEntityClass().getSimpleName() + ".queryRightRankByCond", cond);


  }



}
