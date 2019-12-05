package com.xiaodou.forum.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.alibaba.fastjson.JSON;
import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.resources.dao.forum.ForumPraiseModelDao;
import com.xiaodou.resources.model.forum.ForumPraiseModel;

/**
 * 话题 用户访问列表 dao测试 对标做增删改查
 * 
 * @author bing.cheng
 * 
 */
public class ForumUserPraiseModelDaoTest extends BaseUnitils {

  @SpringBean("forumPraiseModelDao")
  ForumPraiseModelDao forumPraiseModelDao;


  // @Test
  public void updateEntity() {
    ForumPraiseModel entity = new ForumPraiseModel();
    Map<String, String> condition = new HashMap<String, String>();
    condition.put("id", "1");
    // condition.put("forumId", "2");
    entity.setCommentId(3l);
    entity.setResourcesId(3l);
    entity.setReplyId(3l);
    entity.setTag(3);
    entity.setOperator("admin");
    entity.setOperatorip("adminip");
    Integer isUpt = forumPraiseModelDao.updateEntity(condition, entity);
    Assert.assertNotNull(isUpt);
  }

  // @Test
  // public void addEntity() {
  // ForumPraiseModel entity = new ForumPraiseModel();
  // entity.setCommentId(1l);
  // entity.setForumId(1l);
  // entity.setReplyId(1l);
  // entity.setTag(1);
  // entity.setOperator("Operator");
  // entity.setOperatorip("Operatorip");
  // forumPraiseModelDao.addEntity(entity);
  // System.out.println("=========="+JSON.toJSONString(entity));
  // }

  @Test
  public void getEntity() {
    Map<String, String> input = new HashMap<String, String>();
    input.put("id", "1");
    Map<String, String> output = new HashMap<String, String>();
    output.put("operator", "operator");
    List<ForumPraiseModel> list = forumPraiseModelDao.queryList(input, output);
    System.out.println("===============" + JSON.toJSONString(list));
    for (int i = 0; i < list.size(); i++) {
      System.out.println(i + "===============" + list.get(i));
    }
  }
}
