package com.xiaodou.forum.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.forum.dao.forum.ForumCommentModelDao;
import com.xiaodou.forum.model.forum.ForumCommentModel;

/**
 * 话题评论Dao测试  对表做增删改查
 * @author wuyunkuo
 * 
 */
public class ForumCommentModelDaoTest extends BaseUnitils {
	
	@SpringBean("forumCommentModelDao")
	ForumCommentModelDao forumCommentModelDao;
	
	/**
	 * @Title:addEntity
	 * @Description:添加评论
	 */
//	@Test
	public void addEntity(){
		ForumCommentModel entity = new ForumCommentModel();
		entity.setOperatorip("operatorip");
		entity.setOperator("operator");
		entity.setImages("images");
		entity.setContent("content");
		entity.setTargeContent("targecontent");
		entity.setPraiseNumber(0);
		entity.setTargeCommentId((long)0);
		entity.setCreateTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		forumCommentModelDao.addEntity(entity);
	}
	
	
	/**
	 * @Title:deleteList
	 * @Description:根据条件删除话题回复
	 */
//	@Test
	public void deleteList(){
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("id", 2);
		forumCommentModelDao.deleteList(map);
	}
	
	/**
	 * @Title:updateEntity
	 * @Description:修改话题回复
	 * @param condition 更新条件
	 */
//	@Test
	public void updateEntity(){
		//修改的内容
		ForumCommentModel entity = new ForumCommentModel();
		entity.setOperator("operator");
		entity.setOperatorip("operatorip");
		entity.setPraiseNumber(0);
		entity.setTargeId((long) 0);
		entity.setCreateTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		//修改的条件
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("id", 2);
		forumCommentModelDao.updateEntity(condition,entity);
	}
	
	/**
	 * @Title:selectList
	 * @Description:多条数据查询
	 */
	@Test
	public void selectList(){
		Map<String , Object> input = new HashMap<String , Object>();
		input.put("id", 1);
		input.put("forumId", 1);
		Map<String , Object> output = new HashMap<String , Object>();
		output.put("reply_id", 1);
		forumCommentModelDao.queryList(input, output);
	}
	
	/**
	 * @Title:selectEntity
	 * @Description:根据Id查询一条
	 */
	//@Test
	public void selectEntity(){
		ForumCommentModel entity = new ForumCommentModel();
		entity.setId((long) 0);
		forumCommentModelDao.findEntityById(entity);
	}

}
