package com.xiaodou.resources.service.product.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.resources.model.admin.Admin;
import com.xiaodou.resources.model.comment.CommentModel;
import com.xiaodou.resources.model.order.ProductOrderModel;
import com.xiaodou.resources.model.product.CourseProduct;
import com.xiaodou.resources.model.product.ProductCategoryModel;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.model.user.UserChapterRecordModel;
import com.xiaodou.resources.model.user.UserLearnAchieveModel;
import com.xiaodou.resources.model.user.UserLearnProcessModel;
import com.xiaodou.resources.model.user.UserLearnRecordDayModel;
import com.xiaodou.resources.model.user.UserLearnRecordModel;
import com.xiaodou.resources.model.user.UserLearnStaticsModel;
import com.xiaodou.resources.model.user.UserLearnTaskModel;
import com.xiaodou.resources.model.user.UserProductOrderModel;
import com.xiaodou.resources.vo.user.UserChapterRecordVo;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

/**
 * @name @see com.xiaodou.course.service.facade.ProductServiceFacade.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月20日
 * @description 产品数据源上层facade接口
 * @version 1.0
 */
public interface ProductServiceFacade {

  /** module slide */
  // Page<ProductModuleSlideModel> queryModuleSlideListByCondWithOutPage(Map<String, Object> cond,
  // Map<String, Object> output);

  /** product */
  ProductModel queryProductById(ProductModel productModel);

  CourseProduct queryProduct(String productId, String userId);

  CourseProduct queryTotalProduct(String productId, String userId);

  Page<ProductModel> queryCascadeProductByCond(IJoinQueryParam param);

  /** product category */
  Page<ProductCategoryModel> queryProductCategoryByCond(IJoinQueryParam param);

  ProductCategoryModel queryProductCategoryById(ProductCategoryModel entity);

  /** product item */
  ProductItemModel queryProductItemById(ProductItemModel cond);

  Page<ProductItemModel> queryListByPaging(Map<String, Object> cond, Map<String, Object> output,
      Page<ProductItemModel> page, Map<String, Sort> sorts);

  void updateProductItemCommentNumById(Long itemId, Integer commentNum);

  void updateProductItemPraiseNumById(Long itemId, Integer praiseNum);

  public void updateProductItemStatisticsById(Long itemId, Integer completeNum, String topUserList);

  /** user learn process */
  UserLearnProcessModel insertUserLearnProcess(UserLearnProcessModel learnProcess);

  Page<UserLearnProcessModel> queryUserLearnProcessListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  /** user learn record */
  UserLearnRecordModel insertUserLearnRecord(UserLearnRecordModel learnRecord);

  Page<UserLearnRecordModel> queryUserLearnRecordListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  UserLearnRecordModel queryUserLearnRecordById(UserLearnRecordModel entity);

  Boolean updateUserLearnRecord(Map<String, Object> cond, UserLearnRecordModel learnRecord);

  /** user learn statics */
  UserLearnStaticsModel insertUserLearnStatics(UserLearnStaticsModel learnStatics);

  void updateUserLearnStatics(Map<String, Object> cond, UserLearnStaticsModel learnStatics);

  UserLearnStaticsModel queryUserLearnStaticsByUserAndProduct(Integer productId, Integer userId);

  /** user learn task */
  UserLearnTaskModel insertUserLearnTask(UserLearnTaskModel learnTask);

  Page<UserLearnTaskModel> queryUserLearnTaskListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  /** User Product Order */
  List<UserProductOrderModel> queryUserProductOrderByCond(Map<String, Object> cond,
      Map<String, Object> output);

  UserProductOrderModel insertUserProductOrder(UserProductOrderModel userProductOrderModel);

  boolean updateUserProductOrderModel(Map<String, Object> cond, UserProductOrderModel model);

  /** User Chapter Record */
  public List<UserChapterRecordVo> queryChapterRecordList(String userId, String courseId);

  public UserChapterRecordVo queryChapterRecord(String userId, String courseId, String chapterId);

  public List<UserChapterRecordVo> queryItemRecordList(String userId, String courseId,
      String chapterId);

  public Page<UserChapterRecordModel> queryUserChapterRecordByCond(Map<String, Object> cond,
      Map<String, Object> output, Page<UserChapterRecordModel> page);

  List<UserChapterRecordModel> queryUserChapterRecordByCond(Map<String, Object> cond,
      Map<String, Object> output);

  UserChapterRecordModel insertUserChapterRecord(UserChapterRecordModel UserChapterRecordModel);

  /** 评论 */

  CommentModel insertCommentEntity(CommentModel forumCommentModel);

  Page<CommentModel> queryCommentByCond(Map<String, Object> cond);

  public Integer queryCommentNumber(Long itemId);

  /* user learn record day */

  UserLearnRecordDayModel insertRecordDayEntity(UserLearnRecordDayModel entity);

  boolean updateLearnRecordDay(Map<String, Object> cond, UserLearnRecordDayModel model);

  List<UserLearnRecordDayModel> queryLearnRecordDay(Map<String, Object> cond,
      Map<String, Object> output);

  List<UserLearnRecordDayModel> selectLearnTimeByDay(Map<String, Object> cond,
      Map<String, Object> output);


  /** 产品订单记录 */
  ProductOrderModel insertProductOrderModel(ProductOrderModel productOrderModel);

  Page<ProductOrderModel> queryProductOrderModelByCond(IQueryParam param);

  ProductOrderModel queryProductOrderModelById(String id);

  boolean updateProductOrderModelByCond(Map<String, Object> param, ProductOrderModel model);

  /* UserLearnAchieveModel 记录用户最近学习课程至第几章第几节 */

  List<UserLearnAchieveModel> queryUserLearnAchieve(Map<String, Object> cond,
      Map<String, Object> output);

  UserLearnAchieveModel insertUserLearnAchieve(UserLearnAchieveModel entity);

  boolean updateUserLearnAchieve(Map<String, Object> cond, UserLearnAchieveModel model);

  boolean deleteUserLearnAchieve(Map<String, Object> cond);

  /* admin */
  public Admin queryAdminById(Integer adminId);

  void updateProductModel(ProductModel updateModel);

  void insertUserScanLog(String userId, String productId);
}
