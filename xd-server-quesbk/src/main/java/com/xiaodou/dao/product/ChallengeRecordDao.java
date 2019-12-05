package com.xiaodou.dao.product;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.ChallengeRecord;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.thrift.annotion.ThriftMethod;

@Repository
public class ChallengeRecordDao extends ProcessBaseDao<ChallengeRecord> {
  @ThriftMethod
  public int deleteByPrimaryKey(String id) {
    ChallengeRecord record = new ChallengeRecord();
    record.setId(id);
    return super.deleteEntityById(record) ? 1 : 0;
  }

  public int insertSelective(ChallengeRecord record) {
    return addEntity(record).getId() != null ? 1 : 0;
  }

  public ChallengeRecord selectByPrimaryKey(String id) {
    ChallengeRecord record = new ChallengeRecord();
    record.setId(id);
    return super.findEntityById(record);
  }

  public List<ChallengeRecord> selectByCond(Map<String, Object> cond) {
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(CommUtil.getAllField(ChallengeRecord.class));
    Page<ChallengeRecord> resLists = super.findEntityListByCond(param, null);
    return (resLists == null || resLists.getResult().size() == 0) ? null : resLists.getResult();
  }

  public int updateByPrimaryKeySelective(ChallengeRecord record) {
    return super.updateEntityById(record) ? 1 : 0;
  }

  public List<ChallengeRecord> selectTodayByCond(Map<String, Object> cond) {
    return null;
  }
}
