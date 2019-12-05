package com.xiaodou.forum.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.forum.BaseUnitils;
import com.xiaodou.resources.dao.forum.ForumCategoryModelDao;
import com.xiaodou.resources.model.forum.ForumCategoryModel;

/**
 * 话题分类dao测试 对标做增删改查
 * @author bing.cheng
 *
 */
public class ForumCategoryModelDaoTest extends BaseUnitils {

	@SpringBean("forumCategoryModelDao")
	ForumCategoryModelDao forumCategoryModelDao;

	
	/**
	 * 
	 * @Title: addEntity
	 * @Description: 添加分类
	 */
//	@Test
	public void addEntity() {
		ForumCategoryModel entity = new ForumCategoryModel();
	    /**题目*/
	    entity.setTitle("title");
	    /**概括*/
	    entity.setOutline("outline");
	    /**话题总数目*/
	    entity.setForumNumber(100);
	    /**参与人总数*/
	    entity.setPeopleNumber(50);
	    /**标签-扩展字段*/
	    entity.setTag(0);
	    /**创建时间*/
	    entity.setCreateTime(new Date());
	    entity.setContent("content");
	    entity.setImages("Images");
	    entity.setOperator("Operator");
	    entity.setOperatorip("Operatorip");
	    forumCategoryModelDao.addEntity(entity);
	}
	
	/**
	 * 
	 * @Title: updateEntity
	 * @Description: 修改分类
	 */
//	@Test
	public void updateEntity() {
		ForumCategoryModel entity = new ForumCategoryModel();
		 /**题目*/
	    entity.setTitle("title1");
	    /**概括*/
	    entity.setOutline("outline1");
	    /**话题总数目*/
	    entity.setForumNumber(1001);
	    /**参与人总数*/
	    entity.setPeopleNumber(501);
	    /**标签-扩展字段*/
	    entity.setTag(1);
	    /**创建时间*/
	    entity.setCreateTime(new Date());
	    entity.setContent("content1");
	    entity.setImages("Images1");
	    entity.setOperator("Operator1");
	    entity.setOperatorip("Operatorip1");
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("id", "2");
		Integer isUpt = forumCategoryModelDao.updateEntity(condition, entity);
		Assert.assertNotNull(isUpt);
	}
	@Test
	public void findList(){
	  System.out.println("forumCategoryModelDaoTest is running...");
	}


}
