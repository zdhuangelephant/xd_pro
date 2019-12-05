package com.xiaodou.course.service.product;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.common.enums.ResourceType;
import com.xiaodou.course.constant.Constant;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.forum.ForumModel;
import com.xiaodou.course.model.jsonParse.ProductExamDetail;
import com.xiaodou.course.model.product.FinalExamModel;
import com.xiaodou.course.model.product.MajorCourseModel;
import com.xiaodou.course.model.product.MajorCourseModel.MajorCourseInfo;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.product.ProductMiscInfo;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.model.user.UserChapterRecordModel;
import com.xiaodou.course.model.user.UserLearnAchieveModel;
import com.xiaodou.course.model.user.UserLearnRecordDayModel;
import com.xiaodou.course.model.user.UserSignRecordModel;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.service.facade.ForumServiceFacade;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.forum.ForumService;
import com.xiaodou.course.util.DateUtil;
import com.xiaodou.course.vo.product.Chapter;
import com.xiaodou.course.vo.product.ChapterResource;
import com.xiaodou.course.vo.product.ChildChapter;
import com.xiaodou.course.vo.product.CourseDetail;
import com.xiaodou.course.vo.product.ExamDateDetail;
import com.xiaodou.course.vo.product.StCourseInfo;
import com.xiaodou.course.vo.user.UserChapterRecordVo.RecordDetail;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.request.forum.ProductForumListRequest;
import com.xiaodou.course.web.request.forum.ShareForumListRequest;
import com.xiaodou.course.web.request.product.PersonProductInfoRequest;
import com.xiaodou.course.web.request.product.ProductDetailRequest;
import com.xiaodou.course.web.request.product.RecordDetailRequest;
import com.xiaodou.course.web.request.product.UserNotValidCourseRequest;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.forum.ForumListResponse;
import com.xiaodou.course.web.response.product.AddCourseListResponse;
import com.xiaodou.course.web.response.product.AddCourseListResponse.AddCourse;
import com.xiaodou.course.web.response.product.CostLearnTimeResponse;
import com.xiaodou.course.web.response.product.HomeResponse;
import com.xiaodou.course.web.response.product.ProductDetailResponse;
import com.xiaodou.course.web.response.product.RecordDetailResponse;
import com.xiaodou.course.web.response.product.UserNotValidCourseResponse;
import com.xiaodou.course.web.response.product.UserRankResponse;
import com.xiaodou.course.web.response.product.UserRankResponse.RankInfo;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.course.web.response.user.PersonInfoResponse;
import com.xiaodou.frameworkcache.page.PageManager;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;


/**
 * productService
 */
@Service("productService")
public class ProductService extends AbstractService {

  @Resource
  PageManager pageManager;

  @Resource
  ForumService forumService;

  @Resource
  UserLearnRecordService userLearnRecordService;

  @Resource
  UserLearnRecordDayService userLearnRecordDayService;

  @Resource
  ForumServiceFacade forumServiceFacade;

  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource
  ModuleSlideService moduleSlideService;

  @Resource
  ProductCategoryService productCategoryService;

  @Resource
  ProductItemService productItemService;

  @Resource
  UserLearnStaticsService userLearnStaticsService;

  @Resource
  UserProductOrderService userProductOrderService;

  @Resource
  CourseExamTypeService courseExamTypeService;

  @Resource
  UserLearnAchieveService userLearnAchieveService;

  @Resource
  ProductExamService productExamService;

  /**
   * 查找条目
   * 
   * @param itemId
   * @return
   */
  public ProductItemModel findItemById(Long itemId) {
    ProductItemModel productItemModel = new ProductItemModel();
    productItemModel.setId(itemId);
    return productServiceFacade.queryProductItemById(productItemModel);
  }

  /**
   * 课程详情
   * 
   * @param detailRequest
   * @return
   */
  public ProductDetailResponse productDetail(ProductDetailRequest detailRequest) {
    ProductDetailResponse reponse = null;
    try {
      reponse = new ProductDetailResponse(ResultType.SUCCESS);
      ProductModel productModel = this.findProductById(Long.valueOf(detailRequest.getCourseId()));
      // 获取抓取课程信息
      String courseCode = productModel.getCourseCode();
      if (StringUtils.isNotBlank(courseCode)) {
        MajorCourseModel majorCourseModel =
            productServiceFacade.queryMajorCourseByIdAndRegion(courseCode,
                detailRequest.getModule());
        CourseDetail courseDetail = this.getCourseDetail(productModel, majorCourseModel);
        if (null == courseDetail) {
          reponse = new ProductDetailResponse(ResultType.SYSFAIL);
          reponse.setRetdesc("本课程不存在");
          return reponse;
        }
        reponse.setCourseDetail(courseDetail);
      }
    } catch (Exception e) {
      LoggerUtil.error("查询课程详情失败", e);
      reponse = new ProductDetailResponse(ResultType.SYSFAIL);
    }
    return reponse;
  }

  /**
   * 课程转换 ProductModel ->CourseDetail
   * 
   * @param ProductModel product
   * @return
   */
  public CourseDetail getCourseDetail(ProductModel product) {
    if (product == null) return null;
    CourseDetail courseDetail = new CourseDetail();
    /* 此处留下判null处理，需记住：数据库中null与EmptyString 的影响 */
    if (null != product.getCourseCheckType())
      courseDetail.setCourseCheckType(product.getCourseCheckType());
    courseDetail.setCourseName(product.getName());
    courseDetail.setCourseCode(product.getCourseCode());
    courseDetail.setCourseCredit(product.getCourseCredit());
    courseDetail.setCourseDesc(product.getDetail());
    courseDetail.setCourseQuality(product.getCourseQuality());
    if (StringUtils.isJsonNotBlank(product.getExamDetail())) {
      ProductExamDetail examDetail =
          courseExamTypeService.getCourseExamDate(product.getExamDetail());
      if (examDetail != null) {
        courseDetail.setExamDate(examDetail.getExamDate());
      }
    }
    String beginApplyTime = StringUtils.EMPTY;
    String endApplyTime = StringUtils.EMPTY;
    if (null != product.getBeginApplyTime())
      beginApplyTime = com.xiaodou.common.util.DateUtil.SDF_YMD.format(product.getBeginApplyTime());
    if (null != product.getEndApplyTime())
      endApplyTime = com.xiaodou.common.util.DateUtil.SDF_YMD.format(product.getEndApplyTime());
    courseDetail.setExamDate(String.format("%s~%s", beginApplyTime, endApplyTime));
    return courseDetail;
  }

  /**
   * 课程转换 MajorCourseModel->CourseDetail
   * 
   * @param MajorCourseModel majorCourseModel
   * @return
   */
  public CourseDetail getCourseDetail(MajorCourseModel majorCourseModel) {
    if (majorCourseModel == null) return null;
    CourseDetail courseDetail = new CourseDetail();
    courseDetail.setCourseCode(majorCourseModel.getId());
    courseDetail.setCourseName(majorCourseModel.getName());
    if (StringUtils.isNotBlank(majorCourseModel.getMajorCourseInfo())) {
      MajorCourseInfo majorCourseInfo =
          FastJsonUtil.fromJson(majorCourseModel.getMajorCourseInfo(), MajorCourseInfo.class);
      if (null != majorCourseInfo) {
        courseDetail.setCourseQuality(majorCourseInfo.getCourseType());
        courseDetail.setCourseCredit(majorCourseInfo.getCredit());
        courseDetail.setCourseDesc(majorCourseInfo.getDetail());
        courseDetail.setCourseCheckType(majorCourseInfo.getMode());
        courseDetail.setCourseCredit(majorCourseInfo.getCredit());
        courseDetail.setExamDate(majorCourseInfo.getExamDate());
      }
    }
    return courseDetail;
  }


  public CourseDetail getCourseDetail(ProductModel productModel, MajorCourseModel majorCourseModel) {
    if (majorCourseModel == null) return null;
    CourseDetail courseDetail = new CourseDetail();
    courseDetail.setCourseName(productModel.getName());
    courseDetail.setCourseDesc(productModel.getBriefInfo());
    courseDetail.setCourseCode(productModel.getCourseCode());
    if (StringUtils.isNotBlank(majorCourseModel.getMajorCourseInfo())) {
      MajorCourseInfo majorCourseInfo =
          FastJsonUtil.fromJson(majorCourseModel.getMajorCourseInfo(), MajorCourseInfo.class);
      if (null != majorCourseInfo) {
        courseDetail.setCourseQuality(majorCourseInfo.getCourseType());
        courseDetail.setCourseCredit(majorCourseInfo.getCredit());
        // courseDetail.setCourseDesc(majorCourseInfo.getDetail());
        courseDetail.setCourseCheckType(majorCourseInfo.getMode());
        courseDetail.setCourseCredit(majorCourseInfo.getCredit());
        // courseDetail.setExamDate(majorCourseInfo.getExamDate());
      }
    }
    String beginApplyTime = StringUtils.EMPTY;
    String endApplyTime = StringUtils.EMPTY;
    if (null != productModel.getBeginApplyTime())
      beginApplyTime =
          com.xiaodou.common.util.DateUtil.SDF_YMD.format(productModel.getBeginApplyTime());
    if (null != productModel.getEndApplyTime())
      endApplyTime =
          com.xiaodou.common.util.DateUtil.SDF_YMD.format(productModel.getEndApplyTime());
    courseDetail.setExamDate(String.format("%s~%s", beginApplyTime, endApplyTime));

    return courseDetail;
  }

  /**
   * 课程详情
   * 
   * @param productId
   */
  public List<Chapter> productDetail(Long productId) {

    // 查询产品条目信息
    List<ProductItemModel> productItems = productItemService.queryItemListByProductId(productId);
    Map<Long, List<ProductItemModel>> itemParentMap = new HashMap<>();
    for (ProductItemModel productItem : productItems) {
      List<ProductItemModel> childList = new ArrayList<>();
      if (itemParentMap.containsKey(productItem.getParentId())) {
        childList = itemParentMap.get(productItem.getParentId());
      }
      childList.add(productItem);
      itemParentMap.put(productItem.getParentId(), childList);
    }

    // 从是0的开始
    if (null == itemParentMap || itemParentMap.size() == 0) return Lists.newArrayList();
    List<ProductItemModel> chapterItems = itemParentMap.get(0);
    List<Chapter> chapterList = new ArrayList<>();
    for (ProductItemModel chapterItem : chapterItems) {
      Chapter chapter = new Chapter();
      chapter.setChapterId(chapterItem.getId());
      chapter.setChapterName(chapterItem.getName());
      chapter.setFree(chapterItem.getIsFree());
      chapter.setImportance(chapterItem.getImportanceLevel());
      chapter.setChapterIdAlias(chapterItem.getChapterId());
      chapter.setQuestionNum(chapterItem.getQuesNum());
      List<ChildChapter> childChapterList = new ArrayList<>();
      List<ChapterResource> resources = new ArrayList<>();
      List<ProductItemModel> childChapterItems = itemParentMap.get(chapterItem.getId());
      if (childChapterItems != null && childChapterItems.size() > 0) {
        for (ProductItemModel childChapterItem : childChapterItems) {
          if (childChapterItem.getResourceType().equals(ResourceType.CHAPTER.getTypeId())) {
            ChildChapter childChapter = new ChildChapter();
            childChapter.setImportance(childChapterItem.getImportanceLevel());
            childChapter.setFree(childChapterItem.getIsFree());
            childChapter.setItemId(childChapterItem.getId());
            childChapter.setItemName(childChapterItem.getName());
            childChapter.setChapterIdAlias(childChapterItem.getChapterId());
            childChapter.setQuestionNum(childChapter.getQuestionNum());
            List<ProductItemModel> resourceList = itemParentMap.get(childChapterItem.getId());
            List<ChapterResource> chapterResourceList = new ArrayList<>();
            if (resourceList != null && resourceList.size() > 0) {
              for (ProductItemModel resource : resourceList) {
                ChapterResource chapterResource = new ChapterResource();
                chapterResource.setFree(resource.getIsFree());
                chapterResource.setResourceId(resource.getId());
                chapterResource.setResourceName(resource.getName());
                chapterResource.setResourceType(resource.getResourceType());
                chapterResourceList.add(chapterResource);
              }
            }
            childChapter.setResourceList(chapterResourceList);
            childChapterList.add(childChapter);
          } else {
            ChapterResource chapterResource = new ChapterResource();
            chapterResource.setFree(childChapterItem.getIsFree());
            chapterResource.setResourceId(childChapterItem.getId());
            chapterResource.setResourceName(childChapterItem.getName());
            chapterResource.setResourceType(childChapterItem.getResourceType());
            resources.add(chapterResource);
          }
        }
        chapter.setResourceList(resources);
        chapter.setChildList(childChapterList);
      }
      chapterList.add(chapter);
    }
    return chapterList;
  }

  /**
   * 查找产品信息
   * 
   * @param id
   * @return
   */
  public ProductModel findProductById(Long id) {
    ProductModel productModel = new ProductModel();
    productModel.setId(id);
    return productServiceFacade.queryProductById(productModel);
  }

  /**
   * 查看闯关记录详情 学习记录页面调用数据（不对课程有效性做校验）
   * 
   * @param request
   * @return
   */
  public RecordDetailResponse recordDetail(RecordDetailRequest request) {
    RecordDetailResponse response = new RecordDetailResponse(ResultType.SUCCESS);
    try {
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", request.getUid());
      Map<Long, List<UserChapterRecordModel>> ucrMap = this.packageUserChapterRecordMap(cond);
      RecordDetail recordDetail =
          this.getStarDetail(ucrMap.get(Long.parseLong(request.getCourseId())),
              request.getCourseId());
      if (null != recordDetail) {
        if (null != recordDetail.getAchieveStarCount())
          response.setAchieveStarCount(recordDetail.getAchieveStarCount().toString());
        if (null != recordDetail.getTotalStarCount())
          response.setTotalStarCount(recordDetail.getTotalStarCount().toString());
        if (null != recordDetail.getChallengeLevelCount())
          response.setChallengeLevelCount(recordDetail.getChallengeLevelCount().toString());
        if (null != recordDetail.getTotalLevelCount())
          response.setTotalLevelCount(recordDetail.getTotalLevelCount().toString());
      }
    } catch (Exception e) {
      LoggerUtil.error("获取首页数据出错", e);
      return new RecordDetailResponse(ResultType.SYSFAIL);
    }
    return response;

  }

  public RecordDetail getStarDetail(List<UserChapterRecordModel> userChapterRecordList,
      String courseId) {
    Integer achieveStarCount = 0;
    Integer challengeLevelCount = 0;
    FINAL_EXAM_RECORD_LIST: {
      if (null == userChapterRecordList || userChapterRecordList.isEmpty())
        break FINAL_EXAM_RECORD_LIST;
      for (UserChapterRecordModel record : userChapterRecordList) {
        if (null == record) break;
        Short starLevel =
            Short.valueOf(null != record.getStarLevel() ? record.getStarLevel() : "0");
        if (starLevel >= 1) {
          ++challengeLevelCount;
        }
        achieveStarCount = achieveStarCount + starLevel;
      }
    }
    RecordDetail recordDetail = new RecordDetail();
    recordDetail.setAchieveStarCount(achieveStarCount);
    recordDetail.setChallengeLevelCount(challengeLevelCount);
    if (StringUtils.isNotEmpty(courseId)) {
      Integer totalLevelCount = 0;
      Integer totalStarCount = 0;
      // 引入缓存结构
      List<ProductItemModel> itemList =
          productItemService.queryItemListByProductId(Long.valueOf(courseId));
      ITEM_LIST: {
        if (null == itemList || itemList.isEmpty()) break ITEM_LIST;
        totalLevelCount = itemList.size();
        totalStarCount = (itemList.size()) * 3;
      }
      List<FinalExamModel> finalExamList = productItemService.queryFinalExamList(courseId);
      FINAL_EXAM_LIST: {
        if (null == finalExamList || finalExamList.isEmpty()) break FINAL_EXAM_LIST;
        totalLevelCount += finalExamList.size();
        totalStarCount += (finalExamList.size()) * 3;
      }
      recordDetail.setTotalLevelCount(totalLevelCount);
      recordDetail.setTotalStarCount(totalStarCount);
    }
    return recordDetail;
  }

  /**
   *  自考app 首页service
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public HomeResponse home(BaseRequest pojo) {
    HomeResponse response = new HomeResponse(ResultType.SUCCESS);
    try {
      ProductCategoryModel productCategory =
          productCategoryService.findCategoryByAppCode(pojo.getTypeCode(), pojo.getModule());
      if (null == productCategory) {
        return new HomeResponse(UcenterResType.UnCompleteInfo);
      }
      response.setMajorName(productCategory.getName());
      // 产品列表(根据专业查询我的课程)
      List<ProductModel> myProductModelList =
          super.cascadeQueryMyProductByCatId(Integer.valueOf(productCategory.getId()),
              pojo.getUid(), pojo.getModule());
      response.setRecentExamDate(courseExamTypeService.getRecentExamStr());
      // 重构提前方法（1、查询闯关信息2、查询上次记录）
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", pojo.getUid());
      Map<Long, List<UserChapterRecordModel>> ucrMap = this.packageUserChapterRecordMap(cond);
      Map<Long, List<UserLearnAchieveModel>> ulaMap = this.packageUserLearnAchieveMap(cond);
      // 修改购买表中的考期(修改方式呢)
      if (null != myProductModelList && myProductModelList.size() > 0) {
        List<String> forumTagList = Lists.newArrayList();
        for (ProductModel myCourse : myProductModelList) {
          if (DateUtil.ifIsValid(myCourse.getBeginApplyTime(), myCourse.getEndApplyTime())) {
            forumTagList.add(myCourse.getCourseCode());
          }
          this.packageAndPushCourseInfo(ucrMap.get(myCourse.getId()), ulaMap.get(myCourse.getId()),
              pojo, myCourse, response);
        }
        // 知识分享
        Integer knowledgeShare =
            forumServiceFacade.countUnreadForumShare(pojo.getUid(), pojo.getModule(),
                pojo.getTypeCode(), forumTagList, new Timestamp(DateUtil.getTimesmorning(0)));
        if (knowledgeShare > 0)
          response.getForumCard().setDesc(
              String.format(ConfigProp.getParams("forumcard.desc"), knowledgeShare));
        this.packageExamDetail(myProductModelList, response);
      }
      // 新手課程
      List<ProductModel> newbieProductModelList =
          super.getNewbieProductList(pojo.getModule(), pojo.getUid());
      if (null != newbieProductModelList)
        for (ProductModel newbieProductModel : newbieProductModelList) {
          if (null != newbieProductModel)
            this.packageAndPushCourseInfo(ucrMap.get(newbieProductModel.getId()),
                ulaMap.get(newbieProductModel.getId()), pojo, newbieProductModel, response);
        }
    } catch (Exception e) {
      LoggerUtil.error("获取首页数据出错", e);
      response = new HomeResponse(ResultType.VALFAIL);
    }
    return response;
  }

  private void packageExamDetail(List<ProductModel> productList, HomeResponse response) {
    Timestamp now = new Timestamp(System.currentTimeMillis());
    Timestamp beginApplyTime = null;
    Timestamp endApplyTime = null;
    for (ProductModel productModel : productList) {
      if (!DateUtil.ifIsValid(productModel.getBeginApplyTime(), productModel.getEndApplyTime()))
        continue;
      if (null != beginApplyTime) {
        beginApplyTime =
            beginApplyTime.before(productModel.getBeginApplyTime()) ? beginApplyTime : productModel
                .getBeginApplyTime();
      } else {
        beginApplyTime = productModel.getBeginApplyTime();
      }
      if (null != endApplyTime) {
        endApplyTime =
            endApplyTime.after(productModel.getEndApplyTime()) ? endApplyTime : productModel
                .getEndApplyTime();
      } else {
        endApplyTime = productModel.getEndApplyTime();
      }
      ExamDateDetail detail = new ExamDateDetail();
      detail.setCourseId(productModel.getId());
      detail.setCourseName(productModel.getName());
    }
    if (null == beginApplyTime) beginApplyTime = now;
    if (null == endApplyTime) endApplyTime = now;
    response.setCountDownTime(DateUtil.getDaysBetween(new Timestamp(System.currentTimeMillis()),
        endApplyTime) + "");
  }

  /**
   * 
   * @description 查询闯关信息
   * @author 李德洪
   * @Date 2017年8月29日
   * @param cond
   * @return
   */
  public Map<Long, List<UserChapterRecordModel>> packageUserChapterRecordMap(
      Map<String, Object> cond) {
    List<UserChapterRecordModel> userChapterRecordList =
        productServiceFacade.queryUserChapterRecordByCond(cond,
            CommUtil.getAllField(UserChapterRecordModel.class));
    Map<Long, List<UserChapterRecordModel>> ucrMap = Maps.newHashMap();
    for (UserChapterRecordModel ucrModel : userChapterRecordList) {
      Long courseId = ucrModel.getCourseId();
      if (!ucrMap.containsKey(courseId)) {
        ucrMap.put(courseId, Lists.newArrayList(ucrModel));
      } else {
        ucrMap.get(courseId).add(ucrModel);
      }
    }
    return ucrMap;
  }

  /**
   * 
   * @description 查询上次记录
   * @author 李德洪
   * @Date 2017年8月29日
   * @param cond
   * @return
   */
  public Map<Long, List<UserLearnAchieveModel>> packageUserLearnAchieveMap(Map<String, Object> cond) {
    List<UserLearnAchieveModel> learnAchieveList =
        productServiceFacade.queryUserLearnAchieve(cond,
            CommUtil.getAllField(UserLearnAchieveModel.class));
    Map<Long, List<UserLearnAchieveModel>> ulaMap = Maps.newHashMap();
    for (UserLearnAchieveModel ulaModel : learnAchieveList) {
      Long courseId = ulaModel.getProductId();
      if (!ulaMap.containsKey(courseId)) {
        ulaMap.put(courseId, Lists.newArrayList(ulaModel));
      } else {
        ulaMap.get(courseId).add(ulaModel);
      }
    }
    return ulaMap;
  }

  /**
   * 获取用户打卡记录
   * 
   * @param pojo
   * @return
   */
  // private SignCard getSignCard(BaseRequest pojo) {
  // SignCard signCard = new SignCard();
  // UserSignStatistic statistic =
  // productServiceFacade.queryUserSignStatistic(pojo.getUid(), pojo.getModule());
  // if (null != statistic) signCard.setStatistic(statistic);
  // UserSignRecordModel signRecord =
  // productServiceFacade.queryUserSignRecord(pojo.getUid(), pojo.getModule(), new Date());
  // if (null != signRecord) {
  // signCard.setRecord(signRecord);
  // } else {
  // Long learnTime = 0l, targetTime = Long.parseLong(ConfigProp.getParams("signcard.targettime"));
  // Map<String, Object> cond = Maps.newHashMap();
  // cond.put("userId", pojo.getUid());
  // cond.put("recordTime", DateUtil.SDF_YMD.format(new Date()));
  // List<UserLearnRecordDayModel> userLearnRecordDay =
  // userLearnRecordDayService.queryLearnRecordDay(cond);
  // if (null != userLearnRecordDay && userLearnRecordDay.size() > 0) {
  // for (UserLearnRecordDayModel record : userLearnRecordDay)
  // learnTime += record.getLearnTime();
  // }
  // signCard.setLearnTime(learnTime);
  // signCard.setTargetTime(targetTime);
  // // 修复BUG:1220 当学习时长大于等于目标时长,设置打卡状态为可以打卡
  // if (learnTime >= targetTime) signCard.setState(CourseConstant.WAIT_SING);
  // }
  // return signCard;
  // }

  /**
   * 用户打卡方法
   * 
   * @param pojo
   * @return
   */
  public BaseResponse sign(BaseRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    UserSignRecordModel signRecord =
        productServiceFacade.queryUserSignRecord(pojo.getUid(), pojo.getModule(), new Date());
    if (null != signRecord) return new BaseResponse(ProductResType.HasSigned);
    Long learnTime = 0l, targetTime = Long.parseLong(ConfigProp.getParams("signcard.targettime"));
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", pojo.getUid());
    cond.put("recordTime", DateUtil.SDF_YMD.format(new Date()));
    List<UserLearnRecordDayModel> userLearnRecordDay =
        userLearnRecordDayService.selectLearnTimeByDay(cond);
    if (null != userLearnRecordDay && userLearnRecordDay.size() > 0) {
      for (UserLearnRecordDayModel record : userLearnRecordDay)
        learnTime += record.getLearnTime();
    }
    if (learnTime > targetTime) {
      signRecord = new UserSignRecordModel();
      signRecord.setId(Long.parseLong(RandomUtil.randomNumber(8)));
      signRecord.setUserId(pojo.getUid());
      signRecord.setModule(pojo.getModule());
      signRecord.setSignDate(DateUtil.SDF_YMD.format(new Date()));
      signRecord.setSignTime(new Timestamp(System.currentTimeMillis()));
      signRecord.setLearnTime(targetTime);
      signRecord.setTargetTime(targetTime);
      productServiceFacade.insertUserSignRecord(signRecord);
    } else {
      return new BaseResponse(ProductResType.CantSign);
    }
    return response;
  }

  /**
   * 获取专业知识分享列表
   * 
   * @param pojo
   * @return
   */
  public ForumListResponse forumList(ProductForumListRequest pojo) {
    try {
      ProductCategoryModel productCategory =
          productCategoryService.findCategoryByAppCode(pojo.getTypeCode(), pojo.getModule());
      if (null == productCategory) return new ForumListResponse(ResultType.SUCCESS);
      // 产品列表(根据专业查询我的课程)
      List<ProductModel> myProductModelList =
          super.cascadeQueryMyProductByCatId(Integer.valueOf(productCategory.getId()),
              pojo.getUid(), pojo.getModule());
      // 修改购买表中的考期(修改方式呢)
      if (null != myProductModelList && myProductModelList.size() > 0) {
        List<String> forumTagList = Lists.newArrayList();
        for (ProductModel myCourse : myProductModelList) {
          if (DateUtil.ifIsValid(myCourse.getBeginApplyTime(), myCourse.getEndApplyTime())) {
            forumTagList.add(myCourse.getCourseCode());
          }
        }
        ShareForumListRequest request = new ShareForumListRequest();
        if (!StringUtils.isBlank(pojo.getForumId())) {
          ForumModel forum = forumService.getForumModelById(pojo.getForumId());
          if (forum != null) {
            request.setCreateTimeUpper(forum.getCreateTime());
          }
        }
        request.setBaseRequest(pojo);
        request.setTagList(forumTagList);
        return forumService.getShareForumListByTag(request);
      }
    } catch (Exception e) {
      LoggerUtil.error("获取知识分享列表出错", e);
      return new ForumListResponse(ResultType.VALFAIL);
    }
    return new ForumListResponse(ResultType.SUCCESS);
  }

  /**
   * 封装课程信息并压入response中
   * 
   * @param pojo
   * @param myCourse
   * @param response
   */
  private void packageAndPushCourseInfo(List<UserChapterRecordModel> userChapterRecordList,
      List<UserLearnAchieveModel> learnAchieveList, BaseRequest pojo, ProductModel myCourse,
      HomeResponse response) {
    if (null == myCourse) return;
    StCourseInfo stCourseInfo = new StCourseInfo();
    if (null != myCourse.getId()) stCourseInfo.setCourseId(myCourse.getId().toString());
    if (StringUtils.isNotBlank(myCourse.getCourseCode())) {
      stCourseInfo.setCourseCode(myCourse.getCourseCode());
    }
    stCourseInfo.setCourseName(myCourse.getName());
    if (null != myCourse.getScore())
      stCourseInfo.setScore(MathUtil.getIntStringValue(myCourse.getScore()));
    stCourseInfo.setCourseImage(myCourse.getImageUrl());
    RecordDetail recordDetail =
        this.getStarDetail(userChapterRecordList, myCourse.getId().toString());
    Integer achieveStarCount = recordDetail.getAchieveStarCount();
    Integer totalStarCount = recordDetail.getTotalStarCount();
    StCourseInfo _st = this.getStCourseInfo(learnAchieveList, myCourse.getId().toString());
    if (null != _st) {
      stCourseInfo.setLearnChapterId(_st.getLearnChapterId());
      stCourseInfo.setLearnChapterRate(_st.getLearnChapterRate());
      stCourseInfo.setLearnItemId(_st.getLearnItemId());
      stCourseInfo.setLearnItemRate(_st.getLearnItemRate());
      stCourseInfo.setHasLearned(_st.getHasLearned());
    }
    if (null != achieveStarCount) stCourseInfo.setGetStarCount(achieveStarCount.toString());
    if (null != totalStarCount) stCourseInfo.setTotalStarCount(totalStarCount.toString());
    if (DateUtil.ifIsValid(myCourse.getBeginApplyTime(), myCourse.getEndApplyTime())) {
      stCourseInfo.setCourseStatus(CourseConstant.IS_VALID);
      response.getRecentExamCourseList().add(stCourseInfo);
    } else {
      stCourseInfo.setCourseStatus(CourseConstant.IS_NOT_VALID);
      response.getOtherExamCourseList().add(stCourseInfo);
    }
  }

  /**
   * 
   * @description 无效课程数
   * @author 李德洪
   * @Date 2017年11月10日
   * @param pojo
   * @return
   */
  public String countNotValidCourse(BaseRequest pojo) {
    Integer count = 0;
    try {
      // 所有过期课程
      List<ProductModel> myProductModelList =
          super.cascadeQueryMyProductByUpperId(null, null, pojo.getUid(), pojo.getModule());
      if (null != myProductModelList && !myProductModelList.isEmpty()) {
        count = myProductModelList.size();
      }
    } catch (Exception e) {
      LoggerUtil.error("listNotValidCourseCount", e);
    }
    return count.toString();
  }

  public PersonInfoResponse personProductInfo(PersonProductInfoRequest pojo) {
    String key = pojo.getCacheKey();
    PersonInfoResponse response = pageManager.getPage(key);
    if (null == response) {
      response = personProductInfo0(pojo);
      pageManager.addPage(key, response, CourseConstant.MONGO_CACHE_TIME_DAYS, true, TimeUnit.DAYS);
    }
    response.setOtherExamCourseCount(countNotValidCourse(pojo));
    return response;
  }

  public PersonInfoResponse personProductInfo0(PersonProductInfoRequest pojo) {
    PersonInfoResponse response = new PersonInfoResponse(ResultType.SUCCESS);
    response.setLearnTime(userLearnRecordService.sumLearnTime(pojo));
    response.setLearnDays(userLearnRecordDayService.getLearnDays(pojo));
    return response;
  }

  /**
   * 
   * @description 用户在次模块下的购买的所有过期课程
   * @author 李德洪
   * @Date 2017年11月10日
   * @param pojo
   * @return
   */
  public UserNotValidCourseResponse listNotValidCourse(UserNotValidCourseRequest pojo) {
    UserNotValidCourseResponse response = new UserNotValidCourseResponse(ResultType.SUCCESS);
    try {
      // 所有过期课程
      List<ProductModel> myProductModelList =
          super.cascadeQueryMyProductByUpperId(Constant.LIMIT_COUNT, pojo.getUpperId(),
              pojo.getUid(), pojo.getModule());
      if (CollectionUtils.isEmpty(myProductModelList)) {
        return response;
      }
      List<StCourseInfo> list = Lists.newArrayList();
      // 重构提前方法（1、查询闯关信息2、查询上次记录）
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", pojo.getUid());
      Map<Long, List<UserChapterRecordModel>> ucrMap = this.packageUserChapterRecordMap(cond);
      Map<Long, List<UserLearnAchieveModel>> ulaMap = this.packageUserLearnAchieveMap(cond);
      for (ProductModel myCourse : myProductModelList) {
        StCourseInfo stInfo =
            this.packageNotValidCourseInfo(ucrMap.get(myCourse.getId()),
                ulaMap.get(myCourse.getId()), myCourse);
        if (null != stInfo) {
          list.add(stInfo);
        }
      }
      response.setOtherExamCourseList(list);
      return response;
    } catch (Exception e) {
      LoggerUtil.error("listNotValidCourse", e);
      return new UserNotValidCourseResponse(ResultType.SYSFAIL);
    }
  }

  /**
   * 封装无效课程信息并压入response中
   * 
   * @param pojo
   * @param myCourse
   * @param response
   */
  private StCourseInfo packageNotValidCourseInfo(
      List<UserChapterRecordModel> userChapterRecordList,
      List<UserLearnAchieveModel> learnAchieveList, ProductModel myCourse) {
    if (null == myCourse) return null;
    // if (DateUtil.ifIsValid(myCourse.getBeginApplyTime(), myCourse.getEndApplyTime())) {
    // return null;
    // }
    StCourseInfo stCourseInfo = new StCourseInfo();
    if (null != myCourse.getId()) stCourseInfo.setCourseId(myCourse.getId().toString());
    if (StringUtils.isNotBlank(myCourse.getCourseCode())) {
      stCourseInfo.setCourseCode(myCourse.getCourseCode());
    }
    stCourseInfo.setCourseName(myCourse.getName());
    if (null != myCourse.getScore())
      stCourseInfo.setScore(MathUtil.getIntStringValue(myCourse.getScore()));
    stCourseInfo.setCourseImage(myCourse.getImageUrl());
    RecordDetail recordDetail =
        this.getStarDetail(userChapterRecordList, myCourse.getId().toString());
    Integer achieveStarCount = recordDetail.getAchieveStarCount();
    Integer totalStarCount = recordDetail.getTotalStarCount();
    if (null != achieveStarCount) stCourseInfo.setGetStarCount(achieveStarCount.toString());
    if (null != totalStarCount) stCourseInfo.setTotalStarCount(totalStarCount.toString());
    stCourseInfo.setCourseStatus(CourseConstant.IS_VALID);
    return stCourseInfo;
  }


  /**
   * 课程页课程列表展示  添加课程页面中展示的课程为本专业中未开通过的课程
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public AddCourseListResponse addCourseList(BaseRequest pojo) {
    AddCourseListResponse response = new AddCourseListResponse(ResultType.SUCCESS);
    try {
      List<AddCourse> courseList = Lists.newArrayList();
      // 栏目
      ProductCategoryModel productCategory =
          productCategoryService.findCategoryByAppCode(pojo.getTypeCode(), pojo.getModule());
      if (null == productCategory) return response;
      // return new AddCourseListResponse(ProductResType.NotFindCourse);
      // 栏目下的课程(有效期)
      Page<ProductModel> productModelPage =
          this.cascadeQueryProductByCatId(Integer.valueOf(productCategory.getId()),
              pojo.getModule());
      if (null == productModelPage || null == productModelPage.getResult()) return response;// new
                                                                                            // AddCourseListResponse(ProductResType.NotFindCourse);
      List<ProductModel> productList = productModelPage.getResult();
      if (null == productList || productList.size() < 1) return response;// new
                                                                         // AddCourseListResponse(ProductResType.NotFindCourse);
      // 产品列表(根据专业查询我的课程)
      List<ProductModel> myProductModelList =
          super.cascadeQueryMyProductByCatId(Integer.valueOf(productCategory.getId()),
              pojo.getUid(), pojo.getModule());
      List<Long> ids = Lists.newArrayList();
      if (null != myProductModelList) {
        if (null != myProductModelList && myProductModelList.size() > 0) {
          // flag = productList.removeAll(myProductList);
          for (ProductModel productModel : myProductModelList) {
            ids.add(productModel.getId());
          }
        }
      }
      for (ProductModel productModel : productList) {
        if (null != ids && ids.size() > 0 && ids.contains(productModel.getId())) {
          ids.remove(productModel.getId());
          continue;
        }
        AddCourse course = new AddCourse();
        if (null == productModel) continue;
        course.setCourseId(productModel.getId().toString());
        course.setCourseCode(productModel.getCourseCode());
        course.setCourseImage(productModel.getImageUrl());
        // 考期
        course.setExamDate(productModel.getExamDate());
        // 课程有效期
        String t1 = new SimpleDateFormat("yyyy年MM月dd日").format(productModel.getBeginApplyTime());
        String t2 = new SimpleDateFormat("yyyy年MM月dd日").format(productModel.getEndApplyTime());
        course.setExpDate(String.format("%s至%s", t1, t2));
        course.setCourseName(productModel.getName());
        String courseCode = productModel.getCourseCode();
        if (StringUtils.isNotBlank(courseCode)) {
          MajorCourseModel majorCourseModel =
              productServiceFacade.queryMajorCourseByIdAndRegion(courseCode, pojo.getModule());
          if (null != majorCourseModel
              && StringUtils.isNotBlank(majorCourseModel.getMajorCourseInfo())) {
            MajorCourseInfo majorCourseInfo =
                FastJsonUtil.fromJson(majorCourseModel.getMajorCourseInfo(), MajorCourseInfo.class);
            course.setCourseCredit(majorCourseInfo.getCredit());
          }
        }
        if (null != productModel.getOriginalAmount())
          course.setCourseOriginalPrice(productModel.getOriginalAmount().toString());
        if (null != productModel.getPayAmount())
          course.setCoursePreferPrice(productModel.getPayAmount().toString());
        if (StringUtils.isJsonNotBlank(productModel.getMisc())) {
          ProductMiscInfo misc =
              FastJsonUtil.fromJson(productModel.getMisc(), ProductMiscInfo.class);
          if (null != misc && null != misc.getChapterCount())
            course.setCourseChapterCount(misc.getChapterCount().toString());
        }
        courseList.add(course);
      }
      response.setCourseList(courseList);
    } catch (Exception e) {
      LoggerUtil.error("课程页课程列表展示 异常", e);
      e.printStackTrace();
    }
    return response;
  }

  public StCourseInfo getStCourseInfo(List<UserLearnAchieveModel> learnAchieveList, String courseId) {
    StCourseInfo stCourseInfo = new StCourseInfo();
    String learnChapterId = StringUtils.EMPTY;
    String learnChapterRate = StringUtils.EMPTY;
    String learnItemId = StringUtils.EMPTY;
    String learnItemRate = StringUtils.EMPTY;
    String hasLearned = CourseConstant.HAS_LEARNED;;
    // UserLearnProcessModel processModel =
    // userLearnProcessService.lookupLearnProcess(courseId, userId, moduleId);
    // 没有进度的话直接返回第一章第一节
    // 后台录章节数据的时候，保证数据的完整性。（有章有节）
    if (null != learnAchieveList && learnAchieveList.size() == 1) {
      ProductItemModel itemModel =
          productItemService.findItemById(learnAchieveList.get(0).getItemId());
      if (null != itemModel && null != itemModel.getParentId()) {
        if (0 == itemModel.getParentId()) {
          learnChapterId = itemModel.getId().toString();
          learnChapterRate = itemModel.getChapterId();
          learnItemId = itemModel.getId().toString();
          learnItemRate = "章总结";
        } else {
          learnChapterId = itemModel.getParentId().toString();
          ProductItemModel chapter = productItemService.findItemById(itemModel.getParentId());
          if (null != chapter) {
            learnChapterRate = chapter.getChapterId();
            learnItemId = itemModel.getId().toString();
            learnItemRate = itemModel.getChapterId();
          }
        }
        hasLearned = CourseConstant.HAS_LEARNED;
      }
    } else {
      List<ProductItemModel> defaultItemList =
          productItemService.queryItemList(courseId, null, null);
      if (null != defaultItemList && defaultItemList.size() > 0) {
        for (ProductItemModel item : defaultItemList) {
          if (StringUtils.isEmpty(learnChapterId) && item.getParentId() == 0) {
            learnChapterId = item.getId().toString();
          }
          if (StringUtils.isEmpty(learnItemId) && item.getParentId() != 0) {
            learnItemId = item.getId().toString();
          }
          if (StringUtils.isNotEmpty(learnChapterId) && StringUtils.isNotEmpty(learnItemId)) {
            break;
          }
        }
        hasLearned = CourseConstant.NOT_HAS_LEARNED;
      }
    }
    stCourseInfo.setLearnChapterId(learnChapterId);// id
    stCourseInfo.setLearnChapterRate(learnChapterRate);// 索引
    stCourseInfo.setLearnItemId(learnItemId);
    stCourseInfo.setLearnItemRate(learnItemRate);
    stCourseInfo.setHasLearned(hasLearned);
    return stCourseInfo;
  }


  /**
   * 有效期内的课程
   * 
   * @param catId(专业id)
   * @return
   */
  public Page<ProductModel> cascadeQueryProductByCatId(Integer catId, String module) {
    List<ProductCategoryModel> productCategoryModels =
        productCategoryService.queryAllChildCategory(catId);
    List<Integer> ids = new ArrayList<>();
    for (ProductCategoryModel categoryModel : productCategoryModels) {
      ids.add(categoryModel.getId());
    }
    ids.add(catId);
    Map<String, Object> cond = new HashMap<>();
    cond.put("module", module);
    cond.put("productCategoryIds", ids);
    cond.put("showStatus", 1);
    cond.put("applyTime", new Timestamp(System.currentTimeMillis()));// 在课程有效期内
    Map<String, Object> output = new HashMap<>();
    CommUtil.getGeneralField(output, ProductModel.class);
    return productServiceFacade.queryCascadeProductByCond(cond, output);
  }

  /**
   * 有效期内的课程
   * 
   * @param catId(专业id)
   * @return
   */
  public Page<ProductModel> cascadeQueryProductByCatId(Integer catId) {
    List<ProductCategoryModel> productCategoryModels =
        productCategoryService.queryAllChildCategory(catId);
    List<Integer> ids = new ArrayList<>();
    for (ProductCategoryModel categoryModel : productCategoryModels) {
      ids.add(categoryModel.getId());
    }
    ids.add(catId);
    Map<String, Object> cond = new HashMap<>();
    cond.put("productCategoryIds", ids);
    cond.put("showStatus", 1);
    cond.put("applyTime", new Timestamp(System.currentTimeMillis()));// 在课程有效期内
    Map<String, Object> output = new HashMap<>();
    CommUtil.getGeneralField(output, ProductModel.class);
    return productServiceFacade.queryCascadeProductByCond(cond, output);
  }

  /**
   * @author 李德洪
   * @param typeCode 专业码值
   * @param module app模块
   * @param courseCode 课程码值
   * @return
   */
  public ProductModel cascadeQueryProductByCourseCode(String typeCode, String module,
      String courseCode) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("showStatus", CourseConstant.PRODUCT_SHOW_STATUS);
    // 1、新手课程 moduleCourse为相应的模块号，2、普通课程为0
    if (StringUtils.isNotEmpty(courseCode)) {
      cond.put("module", module);
      cond.put("typeCode", typeCode);
      cond.put("courseCode", courseCode);
      cond.put("moduleCourse", 0);
    } else {
      cond.put("moduleCourse", module);
    }
    Map<String, Object> output = new HashMap<>();
    CommUtil.getGeneralField(output, ProductModel.class);
    Page<ProductModel> page = productServiceFacade.queryCascadeProductByCond(cond, output);
    if (null == page) return null;
    List<ProductModel> list = page.getResult();
    if (null == list || list.size() < 1) return null;
    ProductModel model = list.get(0);
    return model;
  }

  /**
   * 校验专业与课程的一致性
   * 
   * @deprecated
   * @param catId(专业id)
   * @param module(app模块)
   * @param courseId(课程id)
   * @return
   */
  public boolean cascadeQueryProductByTypeCodeAndCId(String typeCode, String module, String courseId) {
    // 栏目
    ProductCategoryModel productCategory =
        productCategoryService.findCategoryByAppCode(typeCode, module);
    if (null != productCategory) {
      List<ProductCategoryModel> productCategoryModels =
          productCategoryService.queryAllChildCategory(productCategory.getId());
      List<Integer> ids = new ArrayList<>();
      for (ProductCategoryModel categoryModel : productCategoryModels) {
        ids.add(categoryModel.getId());
      }
      ids.add(productCategory.getId());
      Map<String, Object> cond = new HashMap<>();
      cond.put("module", module);
      cond.put("productCategoryIds", ids);
      cond.put("showStatus", 1);
      cond.put("id", courseId);
      // List<String> moduleCourses = Lists.newArrayList();
      // moduleCourses.add(CourseConstant.COMMON_MODULE_COURSE);
      // moduleCourses.add(module);
      // cond.put("moduleCourses", moduleCourses);
      // cond.put("addModuleCourse", module);
      Map<String, Object> output = new HashMap<>();
      CommUtil.getGeneralField(output, ProductModel.class);
      Page<ProductModel> productModelList =
          productServiceFacade.queryCascadeProductByCond(cond, output);
      if (null != productModelList && null != productModelList.getResult()
          && productModelList.getResult().size() > 0) return true;
    }
    return false;
  }

  public static void main(String[] args) {
    AtomicInteger i = new AtomicInteger(0);
    i.getAndIncrement();
    System.out.println(i);
  }

  /**
   * 获取用户星星排名，限50名
   * 
   * @param pojo
   * @return
   */
  public UserRankResponse starRankList(BaseRequest pojo) {
    UserRankResponse rankResponse = new UserRankResponse(ResultType.SUCCESS);
    // 获取用户星星排名，限50名
    List<UserChapterRecordModel> userChapterRecordList =
        productServiceFacade.queryChapterRecordListGroup(null);
    List<RankInfo> rankInfoList = Lists.newArrayList();
    AtomicInteger i = new AtomicInteger(0);
    for (UserChapterRecordModel model : userChapterRecordList) {
      // 此处判断，正常情况应该不会进入
      if (null == model || null == model.getUserId()) continue;
      RankInfo rankInfo = new RankInfo();
      i.getAndIncrement();
      rankInfo.setRank(i.toString());
      if (null != model.getUserId()) rankInfo.setUserId(model.getUserId().toString());
      rankInfo.setGender(model.getGender());
      rankInfo.setNickName(model.getNickName());
      rankInfo.setPortrait(model.getPortrait());
      rankInfo.setStarLevel(model.getStarLevel());
      if (null != model.getUserId() && pojo.getUid().equals(model.getUserId().toString())) {
        rankResponse.setMyRankInfo(rankInfo);
      }
      if (i.get() <= 50) {
        rankInfoList.add(rankInfo);
      } else if (StringUtils.isNotBlank(rankResponse.getMyRankInfo().getUserId())) {
        break;
      }
    }
    if (StringUtils.isBlank(rankResponse.getMyRankInfo().getUserId())) {
      rankResponse.getMyRankInfo().setRank(String.valueOf(userChapterRecordList.size() + 1));
      rankResponse.getMyRankInfo().setStarLevel(String.valueOf(0));
    }
    rankResponse.setRankInfoList(rankInfoList);
    return rankResponse;
  }

  /**
   * 获取学习耗时
   * 
   * @param pojo
   * @return
   */
  public CostLearnTimeResponse costLearnTime(BaseRequest pojo) {
    CostLearnTimeResponse response = new CostLearnTimeResponse(ResultType.SUCCESS);
    UserLearnRecordDayModel model = productServiceFacade.queryCostLearnTime(pojo.getUid());
    Integer learnTime = 0;
    if (null != model) learnTime = model.getLearnTime();
    response.setLearnTime(learnTime.toString());
    return response;
  }

  public List<ProductModel> getAllProduct() {
    Map<String, Object> cond = new HashMap<>();
    // List<String> moduleCourses = Lists.newArrayList();
    // moduleCourses.add(CourseConstant.COMMON_MODULE_COURSE);
    // moduleCourses.add(module);
    // cond.put("moduleCourses", moduleCourses);
    // cond.put("addModuleCourse", module);
    Map<String, Object> output = new HashMap<>();
    CommUtil.getGeneralField(output, ProductModel.class);
    return productServiceFacade.queryCascadeProductByCond(cond, output).getResult();
  }


}
