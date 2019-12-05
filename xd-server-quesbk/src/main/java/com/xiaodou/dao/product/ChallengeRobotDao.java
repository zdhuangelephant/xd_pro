package com.xiaodou.dao.product;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.ChallengeRobot;
import com.xiaodou.summer.dao.pagination.Page;

@Repository
public class ChallengeRobotDao extends ProcessBaseDao<ChallengeRobot> {

  public Page<ChallengeRobot> findListByCond(Map<String, Object> cond, Page<ChallengeRobot> page) {
    return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".findEntityListByCond",
      cond, page);
  }


}
