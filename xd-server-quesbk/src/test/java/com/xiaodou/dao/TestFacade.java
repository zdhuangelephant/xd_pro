package com.xiaodou.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.Assert;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.dao.behavior.UserWrongRecordDao;
import com.xiaodou.dao.product.ChallengeRobotDao;
import com.xiaodou.domain.behavior.UserWrongRecord;
import com.xiaodou.domain.product.ChallengeRobot;
import com.xiaodou.summer.dao.pagination.Page;

public class TestFacade extends BaseUnitils {

  @SpringBean("userWrongRecordDao")
  UserWrongRecordDao userWrongRecordDao;
  @SpringBean("challengeRobotDao")
  ChallengeRobotDao challengeRobotDao;

  @Test
  public void testfindEntityByCond() {
    Map<String,Object> cond = new HashMap<String,Object>();
    cond.put("id", 2236);
    Page<UserWrongRecord> page = new Page<UserWrongRecord>();
    page.setPageNo(1);
    userWrongRecordDao.findEntityByCond(cond, page);
  }

  @Test
  public void testchallengeRobotDao() {

     Map<String, Object> cond = new HashMap<String, Object>();
     Map<String, Object> input = new HashMap<String, Object>();
     input.put("id", 47);
     cond.put("input", input);
     cond.put("output", CommUtil.getAllField(ChallengeRobot.class));
     Page<ChallengeRobot> page = new Page<ChallengeRobot>();
     page.setPageNo(1);
     Page<ChallengeRobot> findListByCond = challengeRobotDao.findListByCond(cond,page);
     System.out.println(findListByCond.getResult().get(0).getId());
    
     Assert.notNull(findListByCond);

  }

}
