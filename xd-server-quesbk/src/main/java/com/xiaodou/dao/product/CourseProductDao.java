package com.xiaodou.dao.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.xiaodou.constant.ProductConstants;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.thrift.annotion.ThriftMethod;
import com.xiaodou.thrift.annotion.ThriftService;

@ThriftService
@Repository()
public class CourseProductDao extends ProcessBaseDao<CourseProduct> {

  /**
   * 根据产品ID查询产品信息
   * 
   * @param productId 产品ID
   * @return 产品信息实体
   */
  @ThriftMethod
  public CourseProduct selectById(String productId) {
     CourseProduct courseProduct = new CourseProduct();
    courseProduct.setId(Long.valueOf(productId));
    courseProduct = super.findEntityById(courseProduct);
    if (null  != courseProduct && ProductConstants.PRODUCT_STATUS_IS_SHOW_INT == courseProduct.getShowStatus()) {
      return courseProduct;
    }
    return null;
  }

  /**
   * 根据模块ID和typeCode获取产品列表
   * 
   * @param moduleId 模块ID
   * @param typeCode typeCode
   * @return 产品列表
   */
  public List<CourseProduct> selectByModuleAndTypeCode(String moduleId, String typeCode) {
    IQueryParam param = new QueryParam();
    param.addInput("moduleId", moduleId);
    param.addInput("typeCode", typeCode);
    param.addInput("showStatus", ProductConstants.PRODUCT_STATUS_IS_SHOW_INT);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByModuleAndTypeCode",
        super.getCondWrap(param));
  }

  /**
   * 根据模块ID和courseId获取typeCode列表
   * 
   * @param moduleId 模块ID
   * @param courseId 课程ID
   * @return typeCode列表
   */
  public List<String> selectTypeCodeByModuleAndCourseId(String moduleId, String courseId) {
    IQueryParam param = new QueryParam();
    param.addInput("moduleId", moduleId);
    param.addInput("courseId", courseId);
    param.addInput("showStatus", ProductConstants.PRODUCT_STATUS_IS_SHOW_INT);
    List<String> list = this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectTypeCodeByModuleAndCourseId",
        super.getCondWrap(param));
    return list;
  }

  /**
   * 根据条件获取课程列表
   * 
   * @param params
   * @return
   */
  public List<CourseProduct> selectBuyProductByCond(Map<String, Object> params) {
    IQueryParam param = new QueryParam();
    param.addInputs(params);
    param.addInput("showStatus", ProductConstants.PRODUCT_STATUS_IS_SHOW_INT);
    param.addInput("orderStatus", ProductConstants.PRODUCT_STATUS_IS_ORDER_INT);

    Map<String, Object> cond = super.getCondWrap(param);
    SqlSession sqlSession = this.getSqlSession();
    List<CourseProduct> result =
        sqlSession.selectList(this.getEntityClass().getSimpleName() + ".selectBuyProductByCond",
            cond);
    return result;
  }

  /**
   * 根据条件获取课程列表
   * 
   * @param params
   * @return
   */
  public List<CourseProduct> selectBuyProductByCond0(Map<String, Object> params) {
    IQueryParam param = new QueryParam();
    param.addInputs(params);
    param.addInput("showStatus", ProductConstants.PRODUCT_STATUS_IS_SHOW_INT);
    param.addInput("orderStatus", ProductConstants.PRODUCT_STATUS_IS_ORDER_INT);

    Map<String, Object> cond = super.getCondWrap(param);
    SqlSession sqlSession = this.getSqlSession();
    List<CourseProduct> result =
        sqlSession.selectList(this.getEntityClass().getSimpleName() + ".selectBuyProductByCond0",
            cond);
    return result;
  }
}
