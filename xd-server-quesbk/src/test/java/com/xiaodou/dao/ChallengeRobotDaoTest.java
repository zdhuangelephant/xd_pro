package com.xiaodou.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dao.product.ChallengeRobotDao;
import com.xiaodou.domain.product.ChallengeRobot;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.IUpdateParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.dao.param.UpdateParam;

public class ChallengeRobotDaoTest  extends BaseUnitils {
  
  @SpringBean("challengeRobotDao")
  ChallengeRobotDao challengeRobotDao;
  
  
  
  @Test
  public void findEntityListByCond() {
    try {
      IQueryParam param = new QueryParam();
      param.addInput("id", 10486684);
      param.addOutputs(CommUtil.getAllField(ChallengeRobot.class));
      System.out.println(FastJsonUtil.toJson(param));
      Page<ChallengeRobot> page = challengeRobotDao.findEntityListByCond(param, null);
      System.out.println(page.getResult());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Ignore
  public void updateEntityByCond() {
    System.out.println("=======================");
    try {
      IUpdateParam param = new UpdateParam();
      param.addInput("id", 10486684);
      param.addValue("targetAbility", 50);
//      System.out.println(FastJsonUtil.toJson(param));
      System.out.println(challengeRobotDao.updateEntityByCond(param));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  
}
