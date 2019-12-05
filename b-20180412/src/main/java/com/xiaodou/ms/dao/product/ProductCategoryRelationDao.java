package com.xiaodou.ms.dao.product;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.model.product.ProductCategoryRelation;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.ms.dao.product.ProductCategoryRelationDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月21日
 * @description 产品分类与产品关系映射dao
 * @version 1.0
 */
@Repository("productCategoryRelationDao")
public class ProductCategoryRelationDao extends BaseDao<ProductCategoryRelation> {}
