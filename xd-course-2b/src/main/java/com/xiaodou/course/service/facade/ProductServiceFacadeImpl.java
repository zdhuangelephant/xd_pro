package com.xiaodou.course.service.facade;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.course.dao.comment.CommentModelDao;
import com.xiaodou.course.dao.notes.NotesDao;
import com.xiaodou.course.dao.order.ProductOrderDao;
import com.xiaodou.course.dao.product.CourseResourceVideoDao;
import com.xiaodou.course.dao.product.FinalExamDao;
import com.xiaodou.course.dao.product.MajorCourseDao;
import com.xiaodou.course.dao.product.MajorDataDao;
import com.xiaodou.course.dao.product.ModuleSlideDao;
import com.xiaodou.course.dao.product.ProductCategoryDao;
import com.xiaodou.course.dao.product.ProductDao;
import com.xiaodou.course.dao.product.ProductItemDao;
import com.xiaodou.course.dao.product.UserSignRecordDao;
import com.xiaodou.course.dao.product.UserSignStatisticDao;
import com.xiaodou.course.dao.user.UserChapterDao;
import com.xiaodou.course.dao.user.UserChapterVoDao;
import com.xiaodou.course.dao.user.UserCourseHourProgressDao;
import com.xiaodou.course.dao.user.UserLearnAchieveDao;
import com.xiaodou.course.dao.user.UserLearnProcessDao;
import com.xiaodou.course.dao.user.UserLearnRecordDao;
import com.xiaodou.course.dao.user.UserLearnRecordDayDao;
import com.xiaodou.course.dao.user.UserLearnStaticsDao;
import com.xiaodou.course.dao.user.UserLearnTaskDao;
import com.xiaodou.course.dao.user.UserProductOrderDao;
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
import com.xiaodou.course.util.SignUtil;
import com.xiaodou.course.vo.message.AddCreditMessage;
import com.xiaodou.course.vo.message.UserLearnRecordEvent;
import com.xiaodou.course.vo.message.UserLearnRecordEvent.TransferUserLearnRecordData;
import com.xiaodou.course.vo.user.UserChapterRecordVo;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.DeleteParam;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.IUpdateParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.dao.param.UpdateParam;
import com.xiaodou.ucerCenter.agent.request.AddCreditRequest;

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

  @Resource(name = "productdao")
  ProductDao productDao;

  @Resource
  ProductItemDao productItemDao;

  @Resource
  ProductCategoryDao productCategoryDao;

  @Resource
  ModuleSlideDao moduleSlideDao;

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
  NotesDao notesDao;

  @Resource
  UserLearnAchieveDao userLearnAchieveDao;

  @Resource(name = "majorCourseDao")
  MajorCourseDao majorCourseDao;

  @Resource
  MajorDataDao majorDataDao;

  @Resource
  UserSignRecordDao userSignRecordDao;

  @Resource
  UserSignStatisticDao userSignStatisticDao;

  @Resource
  FinalExamDao finalExamDao;

  @Resource
  UserCourseHourProgressDao userCourseTaskInfoDao;

  @Resource
  CourseResourceVideoDao courseResourceVideoDao;

  @Override
  public Page<ModuleSlideModel> queryModuleSlideListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output) {
    return moduleSlideDao.queryListByCondWithOutPage(cond, output);
  }

  @Override
  public Page<ProductCategoryModel> queryProductCategoryListByCondWithOutPage(
      Map<String, Object> cond, Map<String, Object> output) {
    return productCategoryDao.queryListByCondWithOutPage(cond, output);
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
  public Page<ProductItemModel> queryAllProductItem(String productId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("productId", productId);
    cond.put("showStatus", "1");
    return queryProductItemListByCondWithOutPage(cond, CommUtil.getAllField(ProductItemModel.class));
  }

  @Override
  public Page<ProductItemModel> queryProductItemPage(IQueryParam param) {
    return productItemDao.findEntityListByCond(param, null);
  }

  @Override
  public Page<ProductItemModel> queryProductItemListByCondWithOutPage(Map<String, Object> cond,
      Map<String, Object> output) {
    return productItemDao.queryListByCondWithOutPage(cond, output);
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
    return productDao.queryEntityById(productModel);
  }

  @Override
  public Page<ProductModel> queryCascadeProductByCond(Map<String, Object> cond,
      Map<String, Object> output) {
    return productDao.cascadeQueryProduct(cond, output);
  }

  @Override
  public Page<ProductModel> queryCascadeMyProductByCond(Map<String, Object> cond,
      Map<String, Object> output) {
    return productDao.cascadeQueryMyProduct(cond, output);
  }

  @Override
  public Page<ProductModel> cascadeQueryMyProduct0(Integer limitCount, Map<String, Object> cond,
      Map<String, Object> output) {
    return productDao.cascadeQueryMyProduct0(limitCount, cond, output);
  }

  @Override
  public UserLearnRecordModel insertUserLearnRecord(UserLearnRecordModel learnRecord) {
    learnRecord = userLearnRecordDao.addEntity(learnRecord);
    UserLearnRecordEvent dataCleanEvent = new UserLearnRecordEvent();
    dataCleanEvent.setModule(learnRecord.getModuleId().toString());
    dataCleanEvent.setDataModel(new TransferUserLearnRecordData(learnRecord));
    dataCleanEvent.send();
    return learnRecord;
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
  public Integer sumLearnTimeByCond(Map<String, Object> cond) {
    return userLearnRecordDao.sumLearnTimeByCond(cond);
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
  public UserLearnStaticsModel queryUserLearnStaticsByUserAndProduct(Long productId, Long userId) {
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
    return userProductOrderDao.queryList(cond, output);
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
    // Map<String, Object> cond = Maps.newHashMap();
    // cond.put("input", input);
    // cond.put("output", output);
    IQueryParam param = new QueryParam();
    param.addInputs(input);
    param.addOutputs(output);
    return userChapterDao.findEntityListByCond(param, page);
  }

  @Override
  public UserChapterRecordModel insertUserChapterRecord(
      UserChapterRecordModel UserChapterRecordModel) {
    return userChapterDao.addEntity(UserChapterRecordModel);
  }

  @Override
  public List<UserChapterRecordModel> queryChapterRecordListGroup(Map<String, Object> input) {
    return userChapterDao.findEntityListByGroup(input);
  }

  @Override
  public CommentModel insertCommentEntity(CommentModel forumCommentModel) {
    return commentModelDao.addEntity(forumCommentModel);
  }

  @SuppressWarnings("deprecation")
  @Override
  public Page<CommentModel> queryCommentByCond(Map<String, Object> cond) {
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(CommUtil.getAllField(CommentModel.class));
    return commentModelDao.findEntityListByCond(param, null);
  }

  @Override
  public Integer queryCommentNumber(Long itemId) {
    return commentModelDao.queryCommentNumber(itemId);
  }

  @Override
  public void updateProductItemCommentNumById(Long itemId, Integer commentNum) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("id", itemId);
    ProductItemModel item = new ProductItemModel();
    item.setCommentCount(commentNum);
    productItemDao.updateEntity(input, item);
  }

  @Override
  public void updateProductItemStatisticsById(Long itemId, Integer completeNum, String topUserList) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("id", itemId);
    ProductItemModel item = new ProductItemModel();
    item.setTopUserList(topUserList);
    item.setCompleteCount(completeNum);
    productItemDao.updateEntity(input, item);
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
  public UserLearnRecordDayModel queryCostLearnTime(String userId) {
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("userId", userId);
    return userLearnRecordDayDao.queryCostLearnTime(input,
        CommUtil.getAllField(UserLearnRecordDayModel.class));
  }

  @Override
  public List<UserLearnRecordDayModel> findEntityListByGroup(String userId) {
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("userId", userId);
    return userLearnRecordDayDao.findEntityListByGroup(input,
        CommUtil.getAllField(UserLearnRecordDayModel.class));
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
  public boolean updateProductOrderModelByCond(Map<String, Object> input, ProductOrderModel model) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(input);
    Map<String, Object> container = Maps.newHashMap();
    CommUtil.transferFromVO2Map(container, model);
    param.addValues(container);
    return productOrderDao.updateEntityByCond(param);
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
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    Map<String, Object> container = Maps.newHashMap();
    CommUtil.transferFromVO2Map(container, entity);
    param.addValues(container);
    return userLearnAchieveDao.updateEntityByCond(param);
  }

  @Override
  public boolean deleteUserLearnAchieve(Map<String, Object> cond) {
    IDeleteParam param = new DeleteParam();
    param.addInputs(cond);
    return userLearnAchieveDao.deleteEntityByCond(param);
  }

  @Override
  public MajorCourseModel queryMajorCourseByIdAndRegion(String majorCourseId, String region) {
    IQueryParam param = new QueryParam();
    param.addInput("id", majorCourseId);
    param.addInput("region", region);
    param.addOutputs(MajorCourseModel.class);
    Page<MajorCourseModel> coursePage = majorCourseDao.findEntityListByCond(param, null);
    if (null == coursePage || null == coursePage.getResult() || coursePage.getResult().isEmpty()) {
      return null;
    }
    return coursePage.getResult().get(0);
  }

  @Override
  public MajorDataModel queryMajorDataByIdAndRegion(String majorDataId, String region) {
    IQueryParam param = new QueryParam();
    param.addInput("id", majorDataId);
    param.addInput("region", region);
    param.addOutputs(MajorDataModel.class);
    Page<MajorDataModel> majorPage = majorDataDao.findEntityListByCond(param, null);
    if (null == majorPage || null == majorPage.getResult() || majorPage.getResult().isEmpty()) {
      return null;
    }
    return majorPage.getResult().get(0);
  }

  @Override
  public Page<ProductModel> cascadeQueryMyNewbieProduct(Map<String, Object> cond,
      Map<String, Object> output) {
    return productDao.cascadeQueryMyNewbieProduct(cond, output);
  }

  @Override
  public Page<NotesModel> queryUserNotesByCond(Map<String, Object> input, Map<String, Object> output) {
    return notesDao.queryListByCondWithOutPage(input, output);
  }

  @Override
  public UserSignRecordModel queryUserSignRecord(String userId, String module, Date signDate) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("module", module);
    param.addInput("signDate", DateUtil.SDF_YMD.format(signDate));
    param.addOutputs(CommUtil.getAllField(UserSignRecordModel.class));
    Page<UserSignRecordModel> recordPage = userSignRecordDao.findEntityListByCond(param, null);
    if (null == recordPage || null == recordPage.getResult() || recordPage.getResult().size() == 0)
      return null;
    return recordPage.getResult().get(0);
  }

  @Override
  public UserSignStatistic queryUserSignStatistic(String userId, String module) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("module", module);
    param.addOutputs(CommUtil.getAllField(UserSignStatistic.class));
    Page<UserSignStatistic> recordPage = userSignStatisticDao.findEntityListByCond(param, null);
    if (null == recordPage || null == recordPage.getResult() || recordPage.getResult().size() == 0)
      return null;
    return recordPage.getResult().get(0);
  }

  @Override
  public UserSignRecordModel insertUserSignRecord(UserSignRecordModel signRecord) {
    signRecord = userSignRecordDao.addEntity(signRecord);
    UserSignStatistic signStatistic =
        queryUserSignStatistic(signRecord.getUserId(), signRecord.getModule());
    if (null == signStatistic) {
      signStatistic = new UserSignStatistic();
      String currentDate = DateUtil.SDF_YMD.format(new Date());
      signStatistic.setId(Long.parseLong(RandomUtil.randomNumber(8)));
      signStatistic.setUserId(signRecord.getUserId());
      signStatistic.setModule(signRecord.getModule());
      signStatistic.setCreateTime(currentDate);
      signStatistic.setUpdateTime(currentDate);
      signStatistic.setTotalSign(1);
      signStatistic.setContinSign(1);
      userSignStatisticDao.addEntity(signStatistic);
    } else {
      String currentDate = DateUtil.SDF_YMD.format(new Date());
      if (currentDate.equals(signStatistic.getUpdateTime())) return signRecord;
      signStatistic.setTotalSign(signStatistic.getTotalSign() + 1);
      String lastDate = DateUtil.SDF_YMD.format(new Timestamp(DateUtil.getTimesmorning(-1)));
      if (lastDate.equals(signStatistic.getUpdateTime())) {
        signStatistic.setContinSign(signStatistic.getContinSign() + 1);
      } else {
        signStatistic.setContinSign(1);
      }
      signStatistic.setUpdateTime(currentDate);
      Integer credit = SignUtil.getAddCredit(signStatistic.getContinSign());
      if (credit > 0) {
        AddCreditRequest request = new AddCreditRequest();
        request.setUid(signRecord.getUserId());
        request.setModule(signRecord.getModule());
        request.setCreditUpper(credit);
        RabbitMQSender.getInstance().send(new AddCreditMessage(signRecord.getModule(), request));
      }
      userSignStatisticDao.updateEntityById(signStatistic);
    }
    return signRecord;
  }

  @Override
  public List<MajorCourseModel> getMajorCourseByIds(List<String> idList) {
    // TODO Auto-generated method stub
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("ids", idList);
    Map<String, Object> output = Maps.newHashMap();
    output.put("distinctId", StringUtils.EMPTY);
    output.put("name", StringUtils.EMPTY);
    return majorCourseDao.queryList(cond, output);
  }

  @Override
  public FinalExamModel queryFinalExamById(Long itemId) {
    FinalExamModel model = new FinalExamModel();
    model.setId(itemId);
    return finalExamDao.findEntityById(model);
  }

  @Override
  public List<FinalExamModel> queryFinalExamList(String courseId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("courseId", courseId);
    return finalExamDao.queryList(cond, CommUtil.getAllField(FinalExamModel.class));
  }

  @Override
  public List<FinalExamModel> findFinalExamListByCond(String courseId, String userId,
      String finalExamId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("courseId", courseId);
    cond.put("userId", userId);
    if (finalExamId != null) {
      cond.put("id", finalExamId);
    }
    Map<String, Object> output = Maps.newHashMap();
    CommUtil.getGeneralField(output, FinalExamModel.class);
    return finalExamDao.findFinalExamListByCond(cond, output);
  }

  @Override
  public Page<UserCourseHourProgress> queryUserCourseHourProgress(IQueryParam param,
      Page<UserCourseHourProgress> page) {
    return userCourseTaskInfoDao.findEntityListByCond(param, page);
  }

  @Override
  public UserCourseHourProgress insertUserCourseHourProgress(UserCourseHourProgress info) {
    return userCourseTaskInfoDao.addEntity(info);
  }

  @Override
  public Boolean updateUserCourseHourProgress(UserCourseHourProgress info) {
    return userCourseTaskInfoDao.updateEntityById(info);
  }

  @Override
  public Page<UserCourseHourProgress> findUserCourseHourProgress(IQueryParam param,
      Page<UserCourseHourProgress> page) {
    return userCourseTaskInfoDao.findEntityListByCond(param, page);
  }

  @Override
  public CourseResourceVideoModel queryResourceVideoById(Long resourceId) {
    if (null == resourceId) return null;
    CourseResourceVideoModel entity = new CourseResourceVideoModel();
    entity.setId(resourceId);
    return courseResourceVideoDao.findEntityById(entity);
  }

}
