package com.xiaodou.sms.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.sms.BaseUnitils;
import com.xiaodou.sms.dao.ms.SmsChannelModelDao;
import com.xiaodou.sms.model.SmsChannelModel;


/**
 * 话题分类dao测试 对标做增删改查
 * @author bing.cheng
 *
 */
public class SmsChannelModellDaoTest extends BaseUnitils {

	@SpringBean("smsChannelModelDao")
	SmsChannelModelDao smsChannelModelDao;

	
	/**
	 * 
	 * @Title: addEntity
	 * @Description: 添加
	 */
	@Test
	public void addEntity() {
		SmsChannelModel entity = new SmsChannelModel();
		entity.setBalance(0);
		entity.setChannelUrl("channelUrl");
		entity.setControlMaxCount(0);
		entity.setCreateTime(new Date());
		entity.setMerchantId(0);
		entity.setName("name");
		entity.setPort(0);
		entity.setPriority(0);
		entity.setSecretKey("secretKey");
		entity.setStatus(0);
		entity.setTimeOut(0);
		entity.setUserName("userName");
		smsChannelModelDao.addEntity(entity);
	}
	
	/**
	 * @Title: deleteList
	 * @Description: 删除
	 */
	@Test
	public void deleteList(){
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("id", 2);
		smsChannelModelDao.deleteList(map);
	}
	
	/**
	 * @Title: update
	 * @Description: 修改
	 */
	@Test
	public void updateEntity(){
		SmsChannelModel entity = new SmsChannelModel();
		entity.setBalance(1);
		entity.setChannelUrl("channelUrl");
		entity.setControlMaxCount(1);
		entity.setStatus(1);
		entity.setTimeOut(1);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("id", 10);
		smsChannelModelDao.updateEntity(condition, entity);
	}
	
	/**
	 * @Title: select
	 * @Description: 查询
	 */
	@Test
	public void selectList(){
		Map<String , Object> input = new HashMap<String , Object>();
		input.put("id", 1);
		Map<String , Object> output = new HashMap<String , Object>();
		smsChannelModelDao.queryList(input, output);
	}

}
