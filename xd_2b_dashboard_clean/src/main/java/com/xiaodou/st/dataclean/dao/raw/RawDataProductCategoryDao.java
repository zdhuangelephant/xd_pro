package com.xiaodou.st.dataclean.dao.raw;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.xiaodou.st.dataclean.model.domain.raw.RawDataProductCategoryModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.st.dataclean.dao.raw.RawDataProductCategoryDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 原始数据:产品专业模型
 * @version 1.0
 */
@Repository("rawDataProductCategoryDao")
public class RawDataProductCategoryDao extends BaseDao<RawDataProductCategoryModel> {
  public RawDataProductCategoryModel addEntity(RawDataProductCategoryModel entity) {
    SqlSession sqlSession = this.getSqlSession();
    sqlSession.insert(this.getEntityClass().getSimpleName() + ".addEntity", entity);
    return entity;
  }
}
