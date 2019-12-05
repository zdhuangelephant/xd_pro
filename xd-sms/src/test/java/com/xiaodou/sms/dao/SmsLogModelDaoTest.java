package com.xiaodou.sms.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.sms.BaseUnitils;
import com.xiaodou.sms.dao.ms.SmsLogModelDao;
import com.xiaodou.sms.model.SmsLogModel;


public class SmsLogModelDaoTest extends BaseUnitils {
	
	@SpringBean("smsLogModelDao")
	SmsLogModelDao smsLogModelDao;
	
	/**
	 * 
	 * @Title: addEntity
	 * @Description: 添加
	 */
	@Test
	public void addEntity() {
		SmsLogModel entity = new SmsLogModel();
		entity.setChannelId(0);
		entity.setChannelSendResult("channelSendResult");
		entity.setCreateTime(null);
		entity.setMessage("message");
		entity.setMobile("mobile");
		entity.setSendStatus(0);
		entity.setTemplateId(0);
		entity.setTypeId(0);
		smsLogModelDao.addEntity(entity);
	}
	
	/**
	 * @Title: deleteList
	 * @Description: 删除
	 */
	@Test
	public void deleteList(){
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("id", 2);
		smsLogModelDao.deleteList(map);
	}
	
	/**
	 * @Title: update
	 * @Description: 修改
	 */
	@Test
	public void updateEntity(){
		SmsLogModel entity = new SmsLogModel();
		entity.setSendStatus(1);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("id", 1);
		smsLogModelDao.updateEntity(condition, entity);
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
		smsLogModelDao.queryList(input, output);
	}

}
