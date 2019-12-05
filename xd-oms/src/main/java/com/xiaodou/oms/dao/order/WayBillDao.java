package com.xiaodou.oms.dao.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.oms.entity.order.WayBill;
import com.xiaodou.summer.dao.BaseDao;

@Repository("wayBillDao")
public class WayBillDao extends BaseDao<WayBill>{
	
	 @SuppressWarnings("unchecked")
	public Integer updateEntity(WayBill input,WayBill entity) {
	    Map map = new HashMap();
	    map.put("input", input);
	    map.put("entity", entity);
		int result = this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateEntity",
				map);
		return result ;
	}
	 
	 @SuppressWarnings("unchecked")
	public Integer batchUpdateEntity(List<WayBill> wayBillList){
		 Map map=new HashMap();
		 map.put("wayBillList", wayBillList);
		 int result=this.getSqlSession().update(this.getEntityClass().getSimpleName()+".batchUpdateEntity", map);
		 return result;
	 }
	   
	 @SuppressWarnings("unchecked")
	public Integer batchUpdatePostStatus(List<WayBill> wayBillList){
		 Map map=new HashMap();
		 map.put("wayBillList", wayBillList);
		 int result=this.getSqlSession().update(this.getEntityClass().getSimpleName()+".batchUpdatePostStatus", map);
		 return result;
	 }
	 
	 @SuppressWarnings("unchecked")
		public Integer batchUpdateGiftCardWayBillEntity(List<WayBill> wayBillList){
			 Map map=new HashMap();
			 map.put("wayBillList", wayBillList);
			 int result=this.getSqlSession().update(this.getEntityClass().getSimpleName()+".batchUpdateGiftCardWayBillEntity", map);
			 return result;
		 }
}
