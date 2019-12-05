package com.xiaodou.ms.service.order;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.order.ProductOrderDao;
import com.xiaodou.ms.model.order.ProductOrderModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("productOrderService")
public class ProductOrderService {
	@Resource
	ProductOrderDao productOrderDao;
	
	public List<ProductOrderModel> findListByProductId(Integer id) {
		IQueryParam param = new QueryParam();
		param.addOutput("uid", "");
		param.addInput("productId", id);
		Page<ProductOrderModel> results = productOrderDao.findEntityListByCond(param, null);
		if (results != null ) {
			return results.getResult();
		}
		return null;
	}
	
}