package com.xiaodou.dao.product;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Repository
public class CourseProductItemDao extends ProcessBaseDao<CourseProductItem> {

  private static final int DEFAULT_PARENT_ID = 0;

  /**
   * 根据产品ID获取产品章节列表
   * 
   * @param productId 产品ID
   * @return 产品章节列表
   */
  public List<CourseProductItem> selectByProductId(String productId) {
    IQueryParam param = new QueryParam();
    param.addInput("productId", productId);
    param.addInput("resourceType", QuesBaseConstant.RESOURCE_TYPE_QUESBASE);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByProductId", super.getCondWrap(param));

  }

  /**
   * 根据产品ID和用户ID获取产品章节列表
   * 
   * @param productId 产品ID
   * @param uid 用户ID
   * @return 产品章节列表
   */
  public List<CourseProductItem> selectByProductIdAndUserId(String productId, String uid) {
    IQueryParam param = new QueryParam();
    param.addInput("productId", productId);
    param.addInput("userId", uid);
    param.addInput("resourceType", QuesBaseConstant.RESOURCE_TYPE_QUESBASE);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectByProductIdAndUserId",
        super.getCondWrap(param));
  }

  /**
   * 根据产品ID获取章列表
   * 
   * @param courseId 课程ID
   * @return 章列表
   */
  public List<CourseProductItem> selectChapterByProductId(String productId) {
    IQueryParam param = new QueryParam();
    param.addInput("productId", productId);
    param.addInput("resourceType", QuesBaseConstant.RESOURCE_TYPE_QUESBASE);
    param.addInput("parentId", DEFAULT_PARENT_ID);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectChapterByProductId",
        super.getCondWrap(param));
  }

  /**
   * 根据产品ID和章ID获取节列表
   * 
   * @param courseId 课程ID
   * @param chapterId 章ID
   * @return 节列表
   */
  public List<CourseProductItem> selectItemByProductIdAndChapterId(String productId, String parentId) {
    IQueryParam param = new QueryParam();
    param.addInput("productId", productId);
    if (StringUtils.isNotBlank(parentId)) {
      param.addInput("parentId", parentId);
    }
    param.addInput("resourceType", QuesBaseConstant.RESOURCE_TYPE_QUESBASE);
    param.addInput("parentIdLower", DEFAULT_PARENT_ID);
    return this.getSqlSession().selectList(
        this.getEntityClass().getSimpleName() + ".selectItemByProductIdAndChapterId",
        super.getCondWrap(param));
  }

  /**
   * 根据产品ID章ID节ID获取节信息
   * 
   * @param courseId 课程ID
   * @param chapterId 章ID
   * @param itemId 节ID
   * @return 节信息
   */
  public CourseProductItem selectItemByProductIdAndChapterIdAndItemId(String courseId,
      String chapterId, String itemId) {
    IQueryParam param = new QueryParam();
    param.addInput("productId", courseId);
    param.addInput("parentId", chapterId);
    param.addInput("id", itemId);
    param.addInput("resourceType", QuesBaseConstant.RESOURCE_TYPE_QUESBASE);
    param.addInput("parentIdLower", DEFAULT_PARENT_ID);
    List<CourseProductItem> list =
        this.getSqlSession().selectList(
            this.getEntityClass().getSimpleName() + ".selectItemByProductIdAndChapterIdAndItemId",
            super.getCondWrap(param));
    list.size();
    return (list == null || list.isEmpty()) ? null : list.get(0);
  }


  /**
   * 根据产品ID章ID获取章信息
   * 
   * @param courseId 课程ID
   * @param chapterId 章ID
   * @return 节信息
   */
  public CourseProductItem selectChapterByProductIdAndChapterId(String courseId, String chapterId) {
    IQueryParam param = new QueryParam();
    param.addInput("productId", courseId);
    param.addInput("id", chapterId);
    param.addInput("resourceType", QuesBaseConstant.RESOURCE_TYPE_QUESBASE);
    param.addInput("parentId", DEFAULT_PARENT_ID);
    List<CourseProductItem> list =
        this.getSqlSession().selectList(
            this.getEntityClass().getSimpleName() + ".selectChapterByProductIdAndChapterId",
            super.getCondWrap(param));
    return (list == null || list.isEmpty()) ? null : list.get(0);
  }
}
