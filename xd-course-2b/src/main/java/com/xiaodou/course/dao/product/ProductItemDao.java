package com.xiaodou.course.dao.product;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.product.ProductItemModel;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("productItemDao")
public class ProductItemDao extends BaseProcessDao<ProductItemModel> {
}
