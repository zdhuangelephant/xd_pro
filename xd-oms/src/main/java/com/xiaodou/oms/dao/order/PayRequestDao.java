package com.xiaodou.oms.dao.order;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.oms.entity.order.PayRequest;
import com.xiaodou.summer.dao.BaseDao;

@Repository("payRequestDao")
public class PayRequestDao extends BaseDao<PayRequest>{
	
	 public boolean updateEntity(PayRequest input,PayRequest entity) {
		    Map map = new HashMap();
		    map.put("input", input);
		    map.put("entity", entity);
			int result = this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateEntity",
					map);
			return result == 1;
		}

}
