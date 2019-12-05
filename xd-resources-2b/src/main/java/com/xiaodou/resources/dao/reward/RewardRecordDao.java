package com.xiaodou.resources.dao.reward;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.resources.model.reward.RewardRecord;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name @see com.xiaodou.forum.dao.reward.RewardRecordDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月18日
 * @description 资源赞赏记录DAO
 * @version 1.0
 */
@Repository("rewardRecord")
public class RewardRecordDao extends BaseDao<RewardRecord> {

  public Integer findEntityCountByCond(IQueryParam param) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0)
      cond.put("input", param.getInput());
    return (Integer) getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".findEntityCountByCond", cond);
  }

}
