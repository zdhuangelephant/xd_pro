package com.xiaodou.mission.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.mission.domain.MissionModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name @see com.xiaodou.mission.dao.MissionModelDao.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月17日
 * @description 任务模型Dao
 * @version 1.0
 */
@Repository("missionModelDao")
public class MissionModelDao extends BaseDao<MissionModel> {

  public Page<MissionModel> cascadeQueryMissionModelList(Map<String, Object> cond,
      Page<MissionModel> page) {
    return this.selectPaginationList(this.getEntityClass().getSimpleName()
        + ".cascadeQueryListByCond", cond, page);
  }

}
