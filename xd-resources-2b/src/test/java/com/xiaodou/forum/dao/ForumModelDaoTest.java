package com.xiaodou.forum.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.resources.dao.forum.ForumModelDao;
import com.xiaodou.resources.model.forum.ForumModel;

/**
 * 话题  用户访问列表 dao测试 对标做增删改查
 * @author bing.cheng
 *
 */
public class ForumModelDaoTest extends BaseUnitils {
	
	@SpringBean("forumModelDao")
	ForumModelDao forumModelDao;
	
	
//	@Test
	public void addEntity(){
	  ForumModel entity=new ForumModel();
	  entity.setAssign(4);
	  entity.setCategoryId("2002");
	  entity.setCreateTime(new Timestamp(new Date().getTime()));
	  entity.setDigest(1);
	  entity.setOperator("小冰冰");
	  entity.setOperatorip("127.0.0.1");
	  entity.setOutline("outline");
	  entity.setPraiseNumber(20);
	  entity.setPublisherId(201l);
	  entity.setRecommend(1);
	  entity.setRepliesNumber(4);
	  entity.setTag(1);
	  entity.setTitle("titile1");
	  entity.setTop(1);
	  entity.setContent("content1");
	  entity.setUpdateTime(new Timestamp(new Date().getTime()));
	  entity.setImages("|");
	  forumModelDao.addEntity(entity);
	}
	
	@Test
    public void updateEntity(){
	  Map<String, Object> input=new HashMap<String, Object>();
	  input.put("id", 2);
	  ForumModel entity=new ForumModel();
	  entity.setAssign(10);
	  entity.setContent("更新操作SUCCESS");
      forumModelDao.updateEntity(input, entity);
    }
	
//	@Test
//    public void find(){
//	  Map<String, Object> cond=new HashMap<String, Object>();
//	  Map<String, Object> input=new HashMap<String, Object>();
//	  Map<String, Object> output=new HashMap<String, Object>();
//	  Map<String, Object> sort=new HashMap<String, Object>();
//	  sort.put("id", "DESC");
//	  input.put("id", 2);
//	  output.put("id", "");
//	  cond.put("input", input);
//	  cond.put("output", output);
//	  cond.put("sort", sort);
//	  Page<ForumModel> list=new Page<ForumModel>(0, 20);
//      list = forumModelDao.findEntityListByCond(cond, list);
//      System.out.println(JSON.toJSONString(list));
//    }
	
	
}
