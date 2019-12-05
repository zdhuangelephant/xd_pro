package com.xiaodou.resources.service.product.facade;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.resources.dao.admin.AdminDao;
import com.xiaodou.resources.dao.comment.CommentModelDao;
import com.xiaodou.resources.dao.log.UserScanModelMapper;
import com.xiaodou.resources.dao.order.ProductOrderDao;
import com.xiaodou.resources.dao.product.ProductCategoryDao;
import com.xiaodou.resources.dao.product.ProductDao;
import com.xiaodou.resources.dao.product.ProductItemDao;
import com.xiaodou.resources.dao.user.UserChapterDao;
import com.xiaodou.resources.dao.user.UserChapterVoDao;
import com.xiaodou.resources.dao.user.UserLearnAchieveDao;
import com.xiaodou.resources.dao.user.UserLearnProcessDao;
import com.xiaodou.resources.dao.user.UserLearnRecordDao;
import com.xiaodou.resources.dao.user.UserLearnRecordDayDao;
import com.xiaodou.resources.dao.user.UserLearnStaticsDao;
import com.xiaodou.resources.dao.user.UserLearnTaskDao;
import com.xiaodou.resources.dao.user.UserProductOrderDao;
import com.xiaodou.resources.enums.product.ResourceType;
import com.xiaodou.resources.model.admin.Admin;
import com.xiaodou.resources.model.comment.CommentModel;
import com.xiaodou.resources.model.log.UserScanModel;
import com.xiaodou.resources.model.order.ProductOrderModel;
import com.xiaodou.resources.model.product.CourseProduct;
import com.xiaodou.resources.model.product.CourseProductChapter;
import com.xiaodou.resources.model.product.CourseProductItem;
import com.xiaodou.resources.model.product.CourseProductResource;
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
import com.xiaodou.summer.dao.param.JoinQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

/**
 * @name @see com.xiaodou.course.service.facade.ProductServiceFacadeImpl.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月20日
 * @description 产品数据源上层facade实现类
 * @version 1.0
 */
@Service("productServiceFacade")
public class ProductServiceFacadeImpl implements ProductServiceFacade {

  @Resource
  ProductDao productDao;

  @Resource
  ProductItemDao productItemDao;

  @Resource
  ProductCategoryDao productCategoryDao;

  @Resource
  UserLearnProcessDao userLearnProcessDao;

  @Resource
  UserLearnRecordDao userLearnRecordDao;

  @Resource
  UserLearnStaticsDao userLearnStaticsDao;

  @Resource
  UserLearnTaskDao userLearnTaskDao;

  @Resource
  UserChapterVoDao userChapterVoDao;

  @Resource
  UserProductOrderDao userProductOrderDao;

  @Resource
  UserChapterDao userChapterDao;

  @Resource
  CommentModelDao commentModelDao;

  @Resource
  ProductOrderDao productOrderDao;

  @Resource
  UserLearnRecordDayDao userLearnRecordDayDao;

  @Resource
  UserLearnAchieveDao userLearnAchieveDao;

  @Resource
  AdminDao adminDao;

  @Resource
  UserScanModelMapper userScanModelMapper;

  @Override
  public CourseProduct queryProduct(String productId, String userId) {
    IJoinQueryParam param = new JoinQueryParam();
    param.addJoin("userId", userId);
    param.addInput("id", productId);
    param.addOutputs(CommUtil.getAllField(ProductModel.class));
    Page<ProductModel> productPage = productDao.casecadeQueryByCond(param, null);
    if (null == productPage || null == productPage.getResult()
        || productPage.getResult().size() == 0) return null;
    return new CourseProduct(productPage.getResult().get(0));
  }

  @Override
  public CourseProduct queryTotalProduct(String productId, String userId) {
    CourseProduct product = queryProduct(productId, userId);
    IJoinQueryParam param = new JoinQueryParam();
    param.addInput("productId", product.getId());
    param.addJoin("userId", userId);
    param.addSort("listOrder", Sort.ASC);
    param.addOutputs(CommUtil.getAllField(ProductItemModel.class));
    Page<ProductItemModel> productItemList = productItemDao.casecadeQueryByCond(param, null);
    if (null == productItemList || null == productItemList.getResult()
        || productItemList.getResult().size() == 0) return product;
    Map<Long, ProductItemModel> itemMap = Maps.newHashMap();
    List<ProductItemModel> processItemList = Lists.newArrayList();
    for (ProductItemModel _item : productItemList.getResult()) {
      ResourceType _itemResType = ResourceType.getByTypeId(_item.getResourceType());
      switch (_itemResType) {
        case CHAPTER:
          if (_item.getParentId() == 0) {
            CourseProductChapter chapter = new CourseProductChapter(_item);
            itemMap.put(chapter.getId(), chapter);
            product.getChapterList().add(chapter);
          } else {
            CourseProductItem item = new CourseProductItem(_item);
            itemMap.put(item.getId(), item);
            processItemList.add(item);
          }
          break;
        case VIDEO:
        case DOC:
        case QUIZ:
        case TALK:
        case TASK:
        case EXAM:
        case FINAL:
          CourseProductResource res = new CourseProductResource(_item);
          if (res.getParentId() == 0)
            product.getResourceList().add(res);
          else
            processItemList.add(res);
          break;
        default:
          break;
      }
    }
    for (ProductItemModel itemModel : processItemList) {
      if (itemModel.getParentId() == 0) continue;
      ProductItemModel parent = itemMap.get(itemModel.getParentId());
      if (parent.getParentId() == 0) {
        if (itemModel instanceof CourseProductResource)
          ((CourseProductChapter) parent).getResourceList().add((CourseProductResource) itemModel);
        if (itemModel instanceof CourseProductItem)
          ((CourseProductChapter) parent).getItemList().add((CourseProductItem) itemModel);
      } else {
        if (itemModel instanceof CourseProductResource)
          ((CourseProductItem) parent).getResourceList().add((CourseProductResource) itemModel);
      }
    }
    return product;
  }

  @Override
  public Page<ProductCategoryModel> queryProductCategoryByCond(IJoinQueryParam param) {
    return productCategoryDao.findEntityListByCond(param, null);
  }

  @Override
  public ProductCategoryModel queryProductCategoryById(ProductCategoryModel entity) {
    return productCategoryDao.findEntityById(entity);
  }

  @Override
  public ProductItemModel queryProductItemById(ProductItemModel entity) {
    return productItemDao.findEntityById(entity);
  }

  @Override
  public Page<ProductItemModel> queryListByPaging(Map<String, Object> input,
      Map<String, Object> output, Page<ProductItemModel> page, Map<String, Sort> sorts) {
    IJoinQueryParam param = new JoinQueryParam();
    if (null != input.get("userId")) param.addJoin("userId", input.get("userId"));
    param.addInputs(input);
    param.addOutputs(output);
    // param.addSorts(sorts);
    if (null != sorts.get("listOrder")) param.addSort("listOrder", sorts.get("listOrder"));
    if (null != sorts.get("createTime")) param.addSort("createTime", sorts.get("createTime"));
    return productItemDao.casecadeQueryByCond(param, page);
  }

  @Override
  public UserLearnProcessModel insertUserLearnProcess(UserLearnProcessModel learnProcess) {
    return userLearnProcessDao.addEntity(learnProcess);
  }

  @Override
  public Page<UserLearnProcessModel> queryUserLearnProcessListByCondWithOutPage(
      Map<String, Object> cond, Map<String, Object> output) {
    return userLearnProcessDao.queryListByCondWithOutPage(cond, output);
  }

  @Override
  public ProductModel queryProductById(ProductModel productModel) {
    return productDao.findEntityById(productModel);
  }

  @Override
  public Page<ProductModel> queryCascadeProductByCond(IJoinQueryParam param) {
    return productDao.casecadeQueryByCond(param, null);
  }

  @Override
  public UserLearnRecordModel insertUserLearnRecord(UserLearnRecordModel learnRecord) {
    return userLearnRecordDao.addEntity(learnRecord);
  }

  @Override
  public Page<UserLearnRecordModel> queryUserLearnRecordListByCondWithOutPage(
      Map<String, Object> cond, Map<String, Object> output) {
    return userLearnRecordDao.queryListByCondWithOutPage(cond, output);
  }

  @Override
  public UserLearnRecordModel queryUserLearnRecordById(UserLearnRecordModel entity) {
    return userLearnRecordDao.findEntityById(entity);
  }

  @Override
  public Boolean updateUserLearnRecord(Map<String, Object> cond, UserLearnRecordModel learnRecord) {
    return 1 == userLearnRecordDao.updateEntity(cond, learnRecord);
  }


  @Override
  public UserLearnStaticsModel insertUserLearnStatics(UserLearnStaticsModel learnStatics) {
    return userLearnStaticsDao.addEntity(learnStatics);
  }

  @Override
  public void updateUserLearnStatics(Map<String, Object> cond, UserLearnStaticsModel learnStatics) {
    userLearnStaticsDao.updateEntity(cond, learnStatics);
  }

  @Override
  public UserLearnStaticsModel queryUserLearnStaticsByUserAndProduct(Integer productId,
      Integer userId) {
    return userLearnStaticsDao.findEntityByUserAndProduct(productId, userId);
  }

  @Override
  public UserLearnTaskModel insertUserLearnTask(UserLearnTaskModel learnTask) {
    return userLearnTaskDao.addEntity(learnTask);
  }

  @Override
  public Page<UserLearnTaskModel> queryUserLearnTaskListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output) {
    return userLearnTaskDao.queryListByCondWithOutPage(cond, output);
  }

  @Override
  public List<UserProductOrderModel> queryUserProductOrderByCond(Map<String, Object> cond,
      Map<String, Object> output) {
    IJoinQueryParam queryParam = new JoinQueryParam();
    queryParam.addInputs(cond);
    queryParam.addOutputs(CommUtil.getAllField(ProductModel.class));
    Page<UserProductOrderModel> page = userProductOrderDao.findEntityListByCond(queryParam, null);
    return (null != page && null != page.getResult()) ? page.getResult() : null;
  }

  @Override
  public UserProductOrderModel insertUserProductOrder(UserProductOrderModel userProductOrderModel) {
    return userProductOrderDao.addEntity(userProductOrderModel);
  }

  @Override
  public boolean updateUserProductOrderModel(Map<String, Object> cond, UserProductOrderModel model) {
    return 1 == userProductOrderDao.updateEntity(cond, model);
  }

  @Override
  public List<UserChapterRecordVo> queryChapterRecordList(String userId, String courseId) {
    return userChapterVoDao.queryChapterRecordList(userId, courseId);
  }

  @Override
  public UserChapterRecordVo queryChapterRecord(String userId, String courseId, String chapterId) {
    return userChapterVoDao.queryChapterRecord(userId, courseId, chapterId);
  }

  @Override
  public List<UserChapterRecordVo> queryItemRecordList(String userId, String courseId,
      String chapterId) {
    return userChapterVoDao.queryItemRecordList(userId, courseId, chapterId);
  }

  @Override
  public List<UserChapterRecordModel> queryUserChapterRecordByCond(Map<String, Object> cond,
      Map<String, Object> output) {
    return userChapterDao.queryList(cond, output);
  }

  @SuppressWarnings("deprecation")
  @Override
  public Page<UserChapterRecordModel> queryUserChapterRecordByCond(Map<String, Object> input,
      Map<String, Object> output, Page<UserChapterRecordModel> page) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    cond.put("output", output);
    return userChapterDao.findEntityListByCond(cond, page);
  }

  @Override
  public UserChapterRecordModel insertUserChapterRecord(
      UserChapterRecordModel UserChapterRecordModel) {
    return userChapterDao.addEntity(UserChapterRecordModel);
  }

  @Override
  public CommentModel insertCommentEntity(CommentModel forumCommentModel) {
    return commentModelDao.addEntity(forumCommentModel);
  }

  @SuppressWarnings("deprecation")
  @Override
  public Page<CommentModel> queryCommentByCond(Map<String, Object> cond) {
    return commentModelDao.findEntityListByCond(cond, null);
  }

  @Override
  public Integer queryCommentNumber(Long itemId) {
    return commentModelDao.queryCommentNumber(itemId);
  }

  @Override
  public void updateProductItemCommentNumById(Long itemId, Integer commentNum) {
    ProductItemModel item = new ProductItemModel();
    item.setId(itemId);
    item.setCommentCount(commentNum);
    productItemDao.updateEntityById(item);
  }

  @Override
  public void updateProductItemPraiseNumById(Long itemId, Integer praiseNum) {
    ProductItemModel item = new ProductItemModel();
    item.setId(itemId);
    item.setPraiseCount(praiseNum);
    productItemDao.updateEntityById(item);
  }

  @Override
  public void updateProductItemStatisticsById(Long itemId, Integer completeNum, String topUserList) {
    ProductItemModel item = new ProductItemModel();
    item.setId(itemId);
    item.setTopUserList(topUserList);
    item.setCompleteCount(completeNum);
    productItemDao.updateEntityById(item);
  }

  @Override
  public UserLearnRecordDayModel insertRecordDayEntity(UserLearnRecordDayModel entity) {
    return userLearnRecordDayDao.addEntity(entity);
  }

  @Override
  public boolean updateLearnRecordDay(Map<String, Object> cond, UserLearnRecordDayModel model) {
    return 1 == userLearnRecordDayDao.updateEntity(cond, model);
  }

  @Override
  public List<UserLearnRecordDayModel> queryLearnRecordDay(Map<String, Object> cond,
      Map<String, Object> output) {
    return userLearnRecordDayDao.queryList(cond, output);
  }

  @Override
  public List<UserLearnRecordDayModel> selectLearnTimeByDay(Map<String, Object> cond,
      Map<String, Object> output) {
    return userLearnRecordDayDao.selectLearnTimeByDay(cond, output);
  }

  @Override
  public ProductOrderModel insertProductOrderModel(ProductOrderModel productOrderModel) {
    return productOrderDao.addEntity(productOrderModel);
  }

  @Override
  public Page<ProductOrderModel> queryProductOrderModelByCond(IQueryParam param) {
    return productOrderDao.findEntityListByCond(param, null);
  }

  @Override
  public ProductOrderModel queryProductOrderModelById(String id) {
    ProductOrderModel productOrderModel = new ProductOrderModel();
    productOrderModel.setId(id);
    return productOrderDao.findEntityById(productOrderModel);
  }

  @Override
  public boolean updateProductOrderModelByCond(Map<String, Object> param, ProductOrderModel model) {
    return productOrderDao.updateEntityByCond(param, model);
  }

  @Override
  public List<UserLearnAchieveModel> queryUserLearnAchieve(Map<String, Object> cond,
      Map<String, Object> output) {
    return userLearnAchieveDao.queryList(cond, output);
  }

  @Override
  public UserLearnAchieveModel insertUserLearnAchieve(UserLearnAchieveModel entity) {
    return userLearnAchieveDao.addEntity(entity);
  }

  @Override
  public boolean updateUserLearnAchieve(Map<String, Object> cond, UserLearnAchieveModel entity) {
    return userLearnAchieveDao.updateEntityByCond(cond, entity);
  }

  @Override
  public boolean deleteUserLearnAchieve(Map<String, Object> cond) {
    return userLearnAchieveDao.deleteEntityByCond(cond);
  }

  @Override
  public Admin queryAdminById(Integer adminId) {
    Admin admin = new Admin();
    admin.setId(adminId);
    return adminDao.findEntityById(admin);
  }

  // @Override
  // public List<CourseResourceDocModel> queryCourseResourceDoc(Map<String, Object> cond,
  // Map<String, Object> output) {
  // return courseResourceDocDao.queryList(cond, output);
  // }
  //
  // @Override
  // public List<CourseResourceVideoModel> queryCourseResourceVideo(Map<String, Object> cond,
  // Map<String, Object> output) {
  // return courseResourceVideoDao.queryList(cond, output);
  // }

  @Override
  public void updateProductModel(ProductModel updateModel) {
    productDao.updateEntityById(updateModel);
  }

  @Override
  public void insertUserScanLog(String userId, String productId) {
    UserScanModel scanlog = new UserScanModel();
    scanlog.setId(UUID.randomUUID().toString());
    scanlog.setUserId(userId);
    scanlog.setProductId(productId);
    scanlog.setScanTime(new Timestamp(System.currentTimeMillis()));
    userScanModelMapper.addEntity(scanlog);
  }
}
