package com.xiaodou.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaodou.model.ProductModel;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("productDao")
public class ProductDao extends BaseProcessDao<ProductModel> {

	/**
	 * 根据课程ids查找课程
	 */
	@SuppressWarnings("unchecked")
	public List<ProductModel> queryCourseByIds(String ids) {
		return this.getSqlSession()
				.selectList(
						this.getEntityClass().getSimpleName()
								+ ".findEntityByIds", ids);
	}

}
