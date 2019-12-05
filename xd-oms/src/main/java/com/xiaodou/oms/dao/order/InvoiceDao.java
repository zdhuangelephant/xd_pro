package com.xiaodou.oms.dao.order;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.xiaodou.oms.entity.order.Invoice;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

@Repository("invoiceDao")
public class InvoiceDao extends BaseDao<Invoice>{
	
	@SuppressWarnings("unchecked")
	public Page<Invoice> findInvoiceEntityListByCond(Map parameterMap,Page<Invoice> page) {
		
		List<Invoice> resultList = Collections.EMPTY_LIST;
		int totalCount, totalPage;
		// 当page为null时，不用分页查询
		if (page == null) {
			page = new Page();
			resultList = this.getSqlSession().selectList("Invoice.findEntityListByCond", parameterMap);		
			totalCount = resultList.size();
			totalPage = 1;
		}  else {
			resultList = this.getSqlSession().selectList("Invoice.findEntityListByCond", parameterMap, new RowBounds(page.getSkipResults(), page.getPageSize()));
			totalCount = (Integer) getSqlSession().selectOne("Invoice.queryEntityListCnt", parameterMap);
			totalPage = totalCount / page.getPageSize() + 1;
		}
		page.setResult(resultList);
		page.setTotalCount(totalCount);
		page.setTotalPage(totalPage);
		return page;
	}
	
	 @SuppressWarnings("unchecked")
	public Integer updateEntity(Invoice input,Invoice entity) {
	    Map map = new HashMap();
	    map.put("input", input);
	    map.put("entity", entity);
		int result = this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateEntity",
				map);
		return result ;
	}
	 
	 @SuppressWarnings("unchecked")
	public Integer batchUpdateEntity(List<Invoice> invoiceList){
		 Map map=new HashMap();
		 map.put("invoicelist", invoiceList);
		 int result=this.getSqlSession().update(this.getEntityClass().getSimpleName()+".batchUpdateEntity", map);
		 return result;
	 }
	   
}
