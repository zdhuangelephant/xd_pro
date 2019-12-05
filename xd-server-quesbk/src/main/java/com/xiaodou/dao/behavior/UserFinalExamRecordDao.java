package com.xiaodou.dao.behavior;

import org.springframework.stereotype.Repository;

import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.behavior.UserFinalExamRecord;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name UserFinalExamRecordDao CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年1月8日
 * @description 改编自UserFinalExamRecordMapper
 * @version 1.0
 */
@Repository
public class UserFinalExamRecordDao extends ProcessBaseDao<UserFinalExamRecord> {

  public int insert(UserFinalExamRecord record) {
    return super.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".insert", record);
  }

  public UserFinalExamRecord selectByUidAndExamId(String finalExamId, String uid) {
    IQueryParam param = new QueryParam();
    param.addInput("finalExamId", finalExamId);
    param.addInput("uid", uid);
    return this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".selectByUidAndExamId", super.getCondWrap(param));
  }

  /**
   * @param id
   * @return 返回受影响的行数 ， 1表示
   */
  public int deleteUserFinalExamRecord(String id) {
    UserFinalExamRecord entity = new UserFinalExamRecord();
    entity.setId(id);
    return this.getSqlSession().delete(
        this.getEntityClass().getSimpleName() + ".deleteUserFinalExamRecord", entity);
  }

}
