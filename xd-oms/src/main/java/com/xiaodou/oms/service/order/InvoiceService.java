package com.xiaodou.oms.service.order;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xiaodou.oms.dao.order.InvoiceDao;
import com.xiaodou.oms.entity.order.Invoice;
import com.xiaodou.summer.dao.pagination.Page;

public class InvoiceService {
	@Resource
	InvoiceDao invoiceDao;
	
	public Invoice addEntity(Invoice entity)
	{
		return invoiceDao.addEntity(entity);
	}
	
	public Page<Invoice> findEntityListByCond(Map<String, Object> cond, Page<Invoice> paginationInfo)
	{
		return invoiceDao.findInvoiceEntityListByCond(cond, paginationInfo);
	}
	
	 /**
	    * 修改payment记录
	    * @param payRecord
	    */
	 public Integer updateEntity(Invoice condition,Invoice entity){
		   return invoiceDao.updateEntity(condition,entity);
	 }
	  
	 /**
	  * 批量更新发票信息，将未开状态改为已开，同事说更新发票号
	  * @param invoiceList
	  * @return
	  */
	public Integer batchUpdateEntity(List<Invoice> invoiceList){
		return invoiceDao.batchUpdateEntity(invoiceList);
	}
	
}
