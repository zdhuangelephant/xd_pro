package com.xiaodou.ms.dao.product;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.product.ModuleSlideModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * Created by zyp on 15/8/18.
 */
@Repository("moduleSlideDao")
public class ModuleSlideDao extends BaseProcessDao<ModuleSlideModel> {
	public Page<ModuleSlideModel> findUniqueModule(IQueryParam param) {
		 Map<String, Object> cond = Maps.newHashMap();
		    if (null != param && null != param.getInput()) cond.put("input", param.getInput());
		    if (null != param && null != param.getOutput()) cond.put("output", param.getOutput());
		    if (null != param && null != param.getSort()) cond.put("sort", param.getSort());
		    if (null != param && null != param.getLimit()) cond.put("limit", param.getLimit());
		return this.selectPaginationList(
		        this.getEntityClass().getSimpleName() + ".findUniqueModule", cond, null);
	}
}
