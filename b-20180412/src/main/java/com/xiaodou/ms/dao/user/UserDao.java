package com.xiaodou.ms.dao.user;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.knowledge.ForumModel;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

import org.springframework.stereotype.Repository;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("userDao")
public class UserDao extends BaseProcessDao<UserModel> {
	/**
	 * @param inputArgument
	 *            查询条件
	 * @return Page<Entity>
	 * @throws @Title:
	 *             queryListByPaging
	 * @Description: 根据条件 查询 列表 (分页)
	 */
	public Page<UserModel> cascadeQueryProduct(Map inputArgument, Map output, Page page) {
		Map<String, Map> mapParam = new HashMap<String, Map>();
		mapParam.put("input", inputArgument);
		mapParam.put("output", output);
		return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".cascadeQueryProduct", mapParam,
				page);

	}

	public Page<UserModel> findAvailableListByCond(IQueryParam param, Page<UserModel> page) {
		Map<String, Object> cond = Maps.newHashMap();
		if (null != param && null != param.getInput() && param.getInput().size() > 0)
			cond.put("input", param.getInput());
		if (null != param && null != param.getOutput() && param.getOutput().size() > 0)
			cond.put("output", param.getOutput());
		if (null != param && null != param.getSort() && param.getSort().size() > 0)
			cond.put("sort", param.getSort());
		if (null != param && null != param.getLimit() && param.getLimit().size() > 0)
			cond.put("limit", param.getLimit());
		return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".findAvailableListByCond", cond,
				page);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean updateEntityById(UserModel entity) {
		Map<String, Object> cond = Maps.newHashMap();
		if (entity instanceof Map) {
			cond.putAll((Map) entity);
		} else {
			CommUtil.transferFromVO2Map(cond, entity);
		}
		cond.put("value", entity);
		int result = this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateEntityById", cond);
		return result == 1;
	}
	
	public boolean deleteEntityById(UserModel entity) {
	    int result =
	        this.getSqlSession().delete(this.getEntityClass().getSimpleName() + ".deleteEntityById",
	            entity);
	    return result == 1;
	  }
}
