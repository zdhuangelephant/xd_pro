package com.xiaodou.sms.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.sms.BaseUnitils;
import com.xiaodou.sms.dao.ms.SmsMerchantModelDao;
import com.xiaodou.sms.model.SmsMerchantModel;


public class SmsMerchantModelDaoTest extends BaseUnitils {
	
	@SpringBean("smsMerchantModelDao")
	SmsMerchantModelDao smsMerchantModelDao;
	
	/**
	 * 
	 * @Title: addEntity
	 * @Description: 添加
	 */
	@Test
	public void addEntity() {
		SmsMerchantModel entity = new SmsMerchantModel();
		entity.setAddress("address");
		entity.setContactPerson("contactPerson");
		entity.setName("name");
		entity.setShortName("shortName");
		entity.setTelephone("telephone");
		smsMerchantModelDao.addEntity(entity);
	}
	
	/**
	 * @Title: deleteList
	 * @Description: 删除
	 */
	@Test
	public void deleteList(){
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("id", 2);
		smsMerchantModelDao.deleteList(map);
	}
	
	/**
	 * @Title: update
	 * @Description: 修改
	 */
	@Test
	public void updateEntity(){
		SmsMerchantModel entity = new SmsMerchantModel();
		entity.setName("name1");
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("id", 1);
		smsMerchantModelDao.updateEntity(condition, entity);
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
		smsMerchantModelDao.queryList(input, output);
	}

}
