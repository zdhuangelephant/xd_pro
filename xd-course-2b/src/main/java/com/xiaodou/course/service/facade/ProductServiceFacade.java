package com.xiaodou.course.service.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xiaodou.course.model.comment.CommentModel;
import com.xiaodou.course.model.notes.NotesModel;
import com.xiaodou.course.model.order.ProductOrderModel;
import com.xiaodou.course.model.product.CourseResourceVideoModel;
import com.xiaodou.course.model.product.FinalExamModel;
import com.xiaodou.course.model.product.MajorCourseModel;
import com.xiaodou.course.model.product.MajorDataModel;
import com.xiaodou.course.model.product.ModuleSlideModel;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.model.user.UserChapterRecordModel;
import com.xiaodou.course.model.user.UserCourseHourProgress;
import com.xiaodou.course.model.user.UserLearnAchieveModel;
import com.xiaodou.course.model.user.UserLearnProcessModel;
import com.xiaodou.course.model.user.UserLearnRecordDayModel;
import com.xiaodou.course.model.user.UserLearnRecordModel;
import com.xiaodou.course.model.user.UserLearnStaticsModel;
import com.xiaodou.course.model.user.UserLearnTaskModel;
import com.xiaodou.course.model.user.UserProductOrderModel;
import com.xiaodou.course.model.user.UserSignRecordModel;
import com.xiaodou.course.model.user.UserSignStatistic;
import com.xiaodou.course.vo.user.UserChapterRecordVo;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

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
  Page<ModuleSlideModel> queryModuleSlideListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  /** product */
  ProductModel queryProductById(ProductModel productModel);

  Page<ProductModel> queryCascadeProductByCond(Map<String, Object> cond, Map<String, Object> output);

  /* 用户购买product */
  Page<ProductModel> queryCascadeMyProductByCond(Map<String, Object> cond,
      Map<String, Object> output);

  Page<ProductModel> cascadeQueryMyProduct0(Integer limitCount,Map<String, Object> cond,
    Map<String, Object> output);
  
  /** product category */
  Page<ProductCategoryModel> queryProductCategoryListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  ProductCategoryModel queryProductCategoryById(ProductCategoryModel entity);

  /** product item */
  ProductItemModel queryProductItemById(ProductItemModel cond);

  Page<ProductItemModel> queryAllProductItem(String productId);

  Page<ProductItemModel> queryProductItemPage(IQueryParam param);
  
  Page<ProductItemModel> queryProductItemListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output);

  void updateProductItemCommentNumById(Long itemId, Integer commentNum);

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
  
  Integer sumLearnTimeByCond(Map<String, Object> cond);

  /** user learn statics */
  UserLearnStaticsModel insertUserLearnStatics(UserLearnStaticsModel learnStatics);

  void updateUserLearnStatics(Map<String, Object> cond, UserLearnStaticsModel learnStatics);

  UserLearnStaticsModel queryUserLearnStaticsByUserAndProduct(Long productId, Long userId);

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

  List<UserChapterRecordModel> queryChapterRecordListGroup(Map<String, Object> input);

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

  UserLearnRecordDayModel queryCostLearnTime(String userId);

  List<UserLearnRecordDayModel> findEntityListByGroup(String userId);

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

  /* MajorCourseModel 抓取课程信息 */
  MajorCourseModel queryMajorCourseByIdAndRegion(String majorCourseId, String region);


  List<MajorCourseModel> getMajorCourseByIds(List<String> idList);

  /* MajorDataModel 抓取专业信息 */
  MajorDataModel queryMajorDataByIdAndRegion(String majorDataId,  String region);

  /* 查询新手课程 */
  public Page<ProductModel> cascadeQueryMyNewbieProduct(Map<String, Object> cond,
      Map<String, Object> output);

  public Page<NotesModel> queryUserNotesByCond(Map<String, Object> cond, Map<String, Object> output);

  public UserSignRecordModel queryUserSignRecord(String userId, String module, Date signDate);

  public UserSignStatistic queryUserSignStatistic(String userId, String module);

  UserSignRecordModel insertUserSignRecord(UserSignRecordModel signRecord);

  FinalExamModel queryFinalExamById(Long itemId);

  List<FinalExamModel> queryFinalExamList(String courseId);

  List<FinalExamModel> findFinalExamListByCond(String courseId, String userId, String finalExamId);

  Page<UserCourseHourProgress> queryUserCourseHourProgress(IQueryParam param, Page<UserCourseHourProgress> page);

  UserCourseHourProgress insertUserCourseHourProgress(UserCourseHourProgress info);

  Boolean updateUserCourseHourProgress(UserCourseHourProgress info);

  Page<UserCourseHourProgress> findUserCourseHourProgress(IQueryParam param, Page<UserCourseHourProgress> object);

  CourseResourceVideoModel queryResourceVideoById(Long resourceId);

}
