package com.xiaodou.oms.dao.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.summer.dao.BaseDao;

/**
 * 
 * @Title:PayRecordDao.java
 * 
 * @Description:payment记录Dao
 *
 * @author  zhaoyang
 * @date    Mar 16, 2014 10:09:59 AM
 * @version V1.0
 */
@Repository("payRecordDao")
public class PayRecordDao extends BaseDao<PayRecord>{

	
   public PayRecord queryLastPayNo(PayRecord payRecord){
	   
	   List payRecordList = getSqlSession().selectList("PayRecord.queryLastPayNo", payRecord);
	   if(payRecordList!=null && payRecordList.size()>0){
	      return (PayRecord)payRecordList.get(0);
	   }else{
		   return null;
	   }
   }
   
   public boolean updateEntity(PayRecord input,PayRecord entity) {
	    Map map = new HashMap();
	    map.put("input", input);
	    map.put("entity", entity);
		int result = this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateEntity",
				map);
		return result == 1;
	}
   
   public Integer queryPayRecordCountForContinuePay(String gorderId){
	   return (Integer)getSqlSession().selectOne("PayRecord.queryPayRecordCountForContinuePay", gorderId);
   }
   
   public void addEntityFromPayRequest(PayRecord payRecord){
	   getSqlSession().insert("PayRecord.addEntityFromPayRequest", payRecord);
   }
   
   
   
   
}
