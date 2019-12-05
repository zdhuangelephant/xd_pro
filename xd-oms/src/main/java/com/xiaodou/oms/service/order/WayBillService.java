package com.xiaodou.oms.service.order;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xiaodou.oms.dao.order.WayBillDao;
import com.xiaodou.oms.entity.order.WayBill;
import com.xiaodou.summer.dao.pagination.Page;

public class WayBillService {
	@Resource
	WayBillDao wayBillDao;
	
	public WayBill addEntity(WayBill entity)
	{
		return wayBillDao.addEntity(entity);
	}
	
	public Page<WayBill> findEntityListByCond(Map<String, Object> cond, Page<WayBill> paginationInfo)
	{
		return wayBillDao.findEntityListByCond(cond, paginationInfo);
	}
	
	 /**
	    * 修改payment记录
	    * @param payRecord
	    */
	 public Integer updateEntity(WayBill condition,WayBill entity){
		   return wayBillDao.updateEntity(condition,entity);
	 }
	  
	 /**
	  * 批量更新发票信息，将未开状态改为已开，同事说更新发票号
	  * @param invoiceList
	  * @return
	  */
	public Integer batchUpdateEntity(List<WayBill> wayBillList){
		return wayBillDao.batchUpdateEntity(wayBillList);
	}
	
	/**
	  * 导出之后将邮寄状态为“未通知”的批量修改为“已通知”
	  * @param invoiceList
	  * @return
	  */
	public Integer batchUpdatePostStatus(List<WayBill> wayBillList){
		return wayBillDao.batchUpdatePostStatus(wayBillList);
	}
	
	/**
	  * 批量修改礼品卡运单信息
	  * @param invoiceList
	  * @return
	  */
	public Integer batchUpdateGiftCardWayBillEntity(List<WayBill> wayBillList){
		return wayBillDao.batchUpdateGiftCardWayBillEntity(wayBillList);
	}
}
