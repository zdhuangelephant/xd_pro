package com.xiaodou.resources.service.product;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.cache.forum.ForumPraiseCache;
import com.xiaodou.resources.constant.product.CourseConstant;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.enums.product.BuyProductStatus;
import com.xiaodou.resources.model.admin.Admin;
import com.xiaodou.resources.model.forum.CommentUserModel;
import com.xiaodou.resources.model.forum.ForumPraiseModel;
import com.xiaodou.resources.model.forum.ModuleSlideModel;
import com.xiaodou.resources.model.product.CourseProduct;
import com.xiaodou.resources.model.product.ProductCategoryModel;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductItemModel.Resourcer;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.model.user.UserProductOrderModel;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.resources.request.forum.ForumPraiseRequest;
import com.xiaodou.resources.request.product.CourseDetailRequest;
import com.xiaodou.resources.request.product.CourseListRequest;
import com.xiaodou.resources.request.product.NewestCourseRequest;
import com.xiaodou.resources.request.product.TalkCommentListRequest;
import com.xiaodou.resources.request.product.TalkDetailRequest;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.forum.ForumResponse;
import com.xiaodou.resources.response.product.AddCourseListResponse;
import com.xiaodou.resources.response.product.CourseDetailResponse;
import com.xiaodou.resources.response.product.CourseListResponse;
import com.xiaodou.resources.response.product.CourseResourceResponse;
import com.xiaodou.resources.response.product.MyCourseListResponse;
import com.xiaodou.resources.response.product.NewestCourseResponse;
import com.xiaodou.resources.response.product.NewestCourseResponse.NewestResource;
import com.xiaodou.resources.response.product.RecommendCourseResponse;
import com.xiaodou.resources.response.product.TalkCommentListResponse;
import com.xiaodou.resources.response.product.TalkDetailResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.service.forum.ForumDetailService;
import com.xiaodou.resources.service.forum.ModuleSlideService;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.service.product.facade.ProductServiceFacade;
import com.xiaodou.resources.util.IDGenerator;
import com.xiaodou.resources.vo.forum.Comment;
import com.xiaodou.resources.vo.product.ChapterInfo;
import com.xiaodou.resources.vo.product.ChapterInfo.ItemInfo;
import com.xiaodou.resources.vo.product.ChapterResource;
import com.xiaodou.resources.vo.product.ClassifyInfo;
import com.xiaodou.resources.vo.product.CourseDetail;
import com.xiaodou.resources.vo.product.CourseInfo;
import com.xiaodou.resources.vo.product.ResourceVo;
import com.xiaodou.resources.vo.product.Topic;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultType;


/**
 * productService
 */
@Service("productService")
public class ProductService extends AbstractService {

  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource(name = "moduleSlideService")
  ModuleSlideService moduleSlideService;

  @Resource
  ProductCategoryService productCategoryService;

  @Resource
  ProductItemService productItemService;

  @Resource
  UserProductOrderService userProductOrderService;

  DecimalFormat df = new DecimalFormat("######0.00");

  @Resource
  ForumServiceFacade forumServiceFacade;

  @Resource
  QueueService queueService;

  @Resource
  ForumDetailService forumDetailService;

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
   * 课程转换 ProductModel ->CourseDetail
   * 
   * @param ProductModel product
   * @return
   */
  public CourseDetail getCourseDetail(ProductModel product) {
    if (product == null) return null;
    CourseDetail courseDetail = new CourseDetail();
    /* 此处留下判null处理，需记住：数据库中null与EmptyString 的影响 */
    // if (null != product.getCourseCheckType())
    // courseDetail.setCourseCheckType(product.getCourseCheckType());
    courseDetail.setCourseName(product.getName());
    // courseDetail.setCourseCode(product.getCourseCode());
    // courseDetail.setCourseCredit(product.getCourseCredit());
    courseDetail.setCourseDesc(product.getDetail());
    // courseDetail.setCourseQuality(product.getCourseQuality());
    // if (StringUtils.isJsonNotBlank(product.getExamDetail())) {
    // ProductExamDetail examDetail =
    // courseExamTypeService.getCourseExamDate(product.getExamDetail());
    // if (examDetail != null) {
    // courseDetail.setExamDate(examDetail.getExamDate());
    // }
    // }
    return courseDetail;
  }


  /**
   * 我的课程列表页
   * 
   * @param pojo
   * @return
   */
  public MyCourseListResponse mycourseList(BaseRequest pojo) {
    MyCourseListResponse response = new MyCourseListResponse(ResultType.SUCCESS);
    try {
      List<ProductModel> myProductModelList =
          super.cascadeQueryMyProductByCond(CourseConstant.ORDER_STATUS_NORMAL, pojo.getUid(),
              pojo.getModule());
      /** cominglist 未开课 */
      List<CourseInfo> cominglist = Lists.newArrayList();
      /** progresslist 进行中 */
      List<CourseInfo> progresslist = Lists.newArrayList();
      /** endlist 已结束 */
      List<CourseInfo> endlist = Lists.newArrayList();
      for (ProductModel productModel : myProductModelList) {
        Timestamp beginTime = productModel.getBeginApplyTime();
        Timestamp endTime = productModel.getEndApplyTime();
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        CourseInfo courseInfo = this.getCourseInfo(pojo.getModule(), productModel, pojo.getUid());
        if (nowTime.before(beginTime)) {
          cominglist.add(courseInfo);
        } else if (nowTime.after(endTime)) {
          endlist.add(courseInfo);
        } else {
          progresslist.add(courseInfo);
        }
      }
      response.setCominglist(cominglist);
      response.setProgresslist(progresslist);
      response.setEndlist(endlist);
    } catch (ParseException e) {
      LoggerUtil.error("课程页课程列表展示 异常", e);
      response = new MyCourseListResponse(ResultType.VALFAIL);
    }
    return response;
  }

  private CourseInfo getCourseInfo(String module, ProductModel productModel, String userId)
      throws ParseException {
    Admin admin = super.queryAdminById(productModel.getUserId());
    CourseInfo courseInfo = new CourseInfo();
    courseInfo.getCourseInfoByProduct(productModel);
    courseInfo.getCourseInfoByAdmin(admin);
    boolean flag = super.ifOrderProduct(productModel.getId().toString(), userId);
    if (flag)
      courseInfo.setBuyStatus(BuyProductStatus.PURCHASE.getStatusId());
    else if (!flag) courseInfo.setBuyStatus(BuyProductStatus.NOPURCHASE.getStatusId());
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("courseId", productModel.getId());
    List<UserProductOrderModel> listOrder =
        productServiceFacade.queryUserProductOrderByCond(cond,
            CommUtil.getAllField(UserProductOrderModel.class));
    if (null != listOrder) courseInfo.setSignNum(String.valueOf(listOrder.size()));
    return courseInfo;
  }

  /**
   * 课程栏目列表
   * 
   * @param pojo
   * @return
   * @throws Exception
   */
  public AddCourseListResponse categoryCourseList(BaseRequest pojo) {
    AddCourseListResponse response = new AddCourseListResponse(ResultType.SUCCESS);
    /** topicList 轮播图列表 */
    List<Topic> topicList = Lists.newArrayList();
    /** recommendCourseList 推荐课程列表 */
    List<CourseInfo> recommendCourseList = Lists.newArrayList();
    /** commonClassifyList 分类列表 */
    List<ClassifyInfo> commonClassifyList = Lists.newArrayList();
    try {
      List<ProductModel> productModelList =
          this.cascadeQueryProductByTypeCode(CourseConstant.RECOMMEND_COURSE_CODE);
      // 推荐课程列表
      for (ProductModel productModel : productModelList) {
        CourseInfo courseInfo = this.getCourseInfo(pojo.getModule(), productModel, pojo.getUid());
        recommendCourseList.add(courseInfo);
      }
      List<ProductCategoryModel> categoryList = productCategoryService.queryAllCategory();
      // 其它栏目课程列表
      for (ProductCategoryModel category : categoryList) {
        if (CourseConstant.RECOMMEND_COURSE_CODE.equals(category.getTypeCode())) continue;
        ClassifyInfo classifyInfo = new ClassifyInfo();
        classifyInfo.setClassifyId(category.getId().toString());
        classifyInfo.setClassifyName(category.getName());
        List<ProductModel> catProductList =
            this.cascadeQueryProductByMod(category.getId().toString());
        List<CourseInfo> courseList = Lists.newArrayList();
        for (ProductModel productModel : catProductList) {
          // if (myProductIds.contains(productModel.getId())) continue;
          CourseInfo courseInfo = this.getCourseInfo(pojo.getModule(), productModel, pojo.getUid());
          courseList.add(courseInfo);
        }
        classifyInfo.setCourseList(courseList);
        commonClassifyList.add(classifyInfo);
      }
      // 轮播图列表
      List<ModuleSlideModel> slideList =
          moduleSlideService.moduleSlideList(CourseConstant.MODULE_SLIDE_PRODUCT);
      for (ModuleSlideModel slide : slideList) {
        Topic topic = new Topic();
        topic.setContentUrl(slide.getLinkUrl());
        topic.setPicUrl(slide.getImageUrl());
        topicList.add(topic);
      }
      response.setCommonClassifyList(commonClassifyList);
      response.setRecommendCourseList(recommendCourseList);
      response.setTopicList(topicList);

    } catch (Exception e) {
      LoggerUtil.error("课程栏目展示列表异常", e);
      response = new AddCourseListResponse(ResultType.VALFAIL);
    }
    return response;
  }


  /**
   * 课程详情
   * 
   * @param pojo
   * @return
   */
  public CourseDetailResponse courseDetail(CourseDetailRequest pojo) {
    CourseDetailResponse response = new CourseDetailResponse(ResultType.SUCCESS);
    try {
      ProductModel productModel = this.findProductById(Long.valueOf(pojo.getCourseId()));
      if (null == productModel) {
        response = new CourseDetailResponse(ResultType.SYSFAIL);
        response.setRetdesc("本课程不存在");
        return response;
      }
      CourseInfo courseInfo = this.getCourseInfo(pojo.getModule(), productModel, pojo.getUid());
      response.setCourseInfo(courseInfo);
      /** coursePlan 教学计划 */
      List<ChapterInfo> coursePlan = Lists.newArrayList();
      List<ProductItemModel> chapterList =
          productItemService.queryCourseItemList(Integer.valueOf(pojo.getCourseId()), 0, null);
      for (ProductItemModel chapter : chapterList) {
        ChapterInfo chapterInfo = new ChapterInfo();
        if (null != chapter.getId()) chapterInfo.setChapterId(chapter.getId().toString());
        chapterInfo.setChapterName(chapter.getName());
        List<ItemInfo> itemInfoList = Lists.newArrayList();
        List<ProductItemModel> itemrList =
            productItemService.queryCourseItemList(Integer.valueOf(pojo.getCourseId()),
                chapter.getId(), null);
        for (ProductItemModel item : itemrList) {
          ItemInfo itemInfo = new ItemInfo();
          if (null != item.getId()) itemInfo.setItemId(item.getId().toString());
          itemInfo.setItemName(item.getName());
          itemInfoList.add(itemInfo);
        }
        chapterInfo.setItemList(itemInfoList);
        coursePlan.add(chapterInfo);
      }
      response.setCoursePlan(coursePlan);
    } catch (ParseException e) {
      LoggerUtil.error("课程详情异常", e);
      response = new CourseDetailResponse(ResultType.VALFAIL);
    }
    return response;
  }


  /**
   * 获取推荐列表
   * 
   * @param pojo
   * @return
   */
  public RecommendCourseResponse recommendCourseList(BaseRequest pojo) {
    RecommendCourseResponse response = new RecommendCourseResponse(ResultType.SUCCESS);
    try {
      List<CourseInfo> recommendCourseList = Lists.newArrayList();
      List<ProductModel> productModelList =
          this.cascadeQueryProductByTypeCode(CourseConstant.RECOMMEND_COURSE_CODE);
      // 推荐课程列表
      for (ProductModel productModel : productModelList) {
        recommendCourseList.add(this.getCourseInfo(pojo.getModule(), productModel, pojo.getUid()));
      }
      response.setRecommendCourseList(recommendCourseList);
    } catch (ParseException e) {
      LoggerUtil.error("获取推荐列表异常", e);
      response = new RecommendCourseResponse(ResultType.VALFAIL);
    }
    return response;
  }

  /**
   * 查找课程
   * 
   * @param pojo
   * @return
   */
  public CourseListResponse search(CourseListRequest pojo) {
    CourseListResponse response = new CourseListResponse(ResultType.SUCCESS);
    try {
      if (StringUtils.isBlank(pojo.getCourseName())) return response;
      List<CourseInfo> courseList = Lists.newArrayList();
      List<ProductModel> productModelList = this.cascadeQueryProductByName(pojo.getCourseName());
      for (ProductModel productModel : productModelList) {
        courseList.add(this.getCourseInfo(pojo.getModule(), productModel, pojo.getUid()));
      }
      response.setCourseList(courseList);
    } catch (ParseException e) {
      LoggerUtil.error("查找课程异常", e);
      response = new CourseListResponse(ResultType.VALFAIL);
    }
    return response;
  }

  /**
   * 课程主页-目录
   * 
   * @param pojo
   * @return
   */
  public CourseResourceResponse directory(CourseDetailRequest pojo) {
    CourseResourceResponse response = new CourseResourceResponse(ResultType.SUCCESS);
    try {
      CheckResult checkRes = super.checkCourseId(pojo.getCourseId());
      if (!checkRes.isRetOk()) return new CourseResourceResponse(checkRes.getResType());
      CourseProduct product =
          productServiceFacade.queryTotalProduct(pojo.getCourseId(), pojo.getUid());
      if (null != product) response.setProduct(product);
    } catch (Exception e) {
      LoggerUtil.error("查找课程主页-目录异常", e);
      response = new CourseResourceResponse(ResultType.VALFAIL);
    }
    return response;
  }

  /**
   * 期末考试
   * 
   * @param courseId
   * @param itemId
   * @return
   */
  private ResourceVo getItemResourceForFinalExam(ProductItemModel finalExam) {
    if (null == finalExam) return null;
    ResourceVo resourceVo = new ResourceVo();
    resourceVo.getResourceFromProductItem(finalExam);
    if (null != finalExam.getScore())
      resourceVo.setJoinResult(String.format("最终得分%s分，总分100", finalExam.getScore()));
    else
      resourceVo.setJoinResult("总分100");
    // 考核 -1:已结束，0：去测验，1：查看测验 资源状态
    Timestamp currTime = new Timestamp(System.currentTimeMillis());
    if (null != finalExam.getDeadline() && finalExam.getDeadline().before(currTime)) {
      resourceVo.setResourceStatus("-1");
    } else if (null != finalExam.getScore()) {
      resourceVo.setResourceStatus("1");
    } else {
      resourceVo.setResourceStatus("0");
    }
    return resourceVo;
  }

  /**
   * 课程主页-考核
   * 
   * @param pojo
   * @return
   */
  public CourseResourceResponse check(CourseDetailRequest pojo) {
    CourseResourceResponse response = new CourseResourceResponse(ResultType.SUCCESS);
    try {
      CheckResult checkRes = super.checkCourseId(pojo.getCourseId());
      if (!checkRes.isRetOk()) return new CourseResourceResponse(checkRes.getResType());
      List<ChapterResource> directory = Lists.newArrayList();
      List<ProductItemModel> chapterList =
          productItemService.queryCourseItemList(Integer.valueOf(pojo.getCourseId()), 0, null);
      if (null != chapterList && chapterList.size() > 0)
        for (ProductItemModel chapter : chapterList) {
          ChapterResource chapterResource = new ChapterResource();
          if (null != chapter.getId()) chapterResource.setChapterId(chapter.getId().toString());
          chapterResource.setChapterName(chapter.getName());
          // 章下挂载的资源
          List<ResourceVo> resourceList = Lists.newArrayList();
          List<ProductItemModel> chapterResourceList = Lists.newArrayList();
          ProductItemModel task =
              productItemService.queryTASK(pojo.getUid(), Integer.valueOf(pojo.getCourseId()),
                  chapter.getId(), null);
          if (null != task) chapterResourceList.add(task);
          ProductItemModel exam =
              productItemService.queryEXAM(pojo.getUid(), Integer.valueOf(pojo.getCourseId()),
                  chapter.getId(), null);
          if (null != exam) chapterResourceList.add(exam);
          if (null != chapterResourceList && chapterResourceList.size() > 0)
            for (ProductItemModel chapterr : chapterResourceList) {
              ResourceVo resource = new ResourceVo();
              resource.getResourceFromProductItem(chapterr);
              if (null != chapterr.getScore())
                resource.setJoinResult(String.format("最终得分%s分，总分100", chapterr.getScore()));
              else
                resource.setJoinResult("总分100");
              // 考核 -1:已结束，0：去测验，1：查看测验 资源状态
              Timestamp currTime = new Timestamp(System.currentTimeMillis());
              if (null != chapterr.getDeadline() && chapterr.getDeadline().before(currTime)) {
                resource.setResourceStatus("-1");
              } else if (null != chapterr.getScore()) {
                resource.setResourceStatus("1");
              } else {
                resource.setResourceStatus("0");
              }
              resourceList.add(resource);
            }
          if (null != resourceList) chapterResource.setResourceList(resourceList);
          if (null != chapterResource) directory.add(chapterResource);
          if (null != directory) response.setDirectory(directory);
          ProductItemModel finalExamM =
              productItemService.queryFINALEXAM(pojo.getUid(), pojo.getCourseId(), 0, null);
          ResourceVo finalExam = this.getItemResourceForFinalExam(finalExamM);
          if (null != finalExam) response.setFinalExam(finalExam);
          CourseProduct product =
              productServiceFacade.queryTotalProduct(pojo.getCourseId(), pojo.getUid());
          if (null != product.getScore()) {
            Timestamp endTime = finalExamM.getDeadline();
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            if (nowTime.after(endTime))
              response.setScore(QuesBaseConstant.D_FORMAT.format(product.getScore()));
          }
        }
    } catch (Exception e) {
      LoggerUtil.error("查找课程主页-考核异常", e);
      response = new CourseResourceResponse(ResultType.VALFAIL);
    }
    return response;
  }


  /**
   * 课程主页-讨论
   * 
   * @param pojo
   * @return
   */
  public CourseResourceResponse talk(CourseDetailRequest pojo) {
    CourseResourceResponse response = new CourseResourceResponse(ResultType.SUCCESS);
    try {
      if (!super.checkCourseId(pojo.getCourseId()).isRetOk())
        return new CourseResourceResponse(super.checkCourseId(pojo.getCourseId()).getResType());
      List<ChapterResource> directory = Lists.newArrayList();
      List<ProductItemModel> chapterList =
          productItemService.queryCourseItemList(Integer.valueOf(pojo.getCourseId()), 0, null);
      if (null != chapterList && chapterList.size() > 0)
        for (ProductItemModel chapter : chapterList) {
          ChapterResource chapterResource = new ChapterResource();
          if (null != chapter.getId()) chapterResource.setChapterId(chapter.getId().toString());
          chapterResource.setChapterName(chapter.getName());
          // 章下挂载的资源
          List<ResourceVo> resourceList = Lists.newArrayList();
          List<ProductItemModel> chapterResourceList = Lists.newArrayList();
          ProductItemModel queryTALK =
              productItemService.queryTALK(pojo.getUid(), Integer.valueOf(pojo.getCourseId()),
                  chapter.getId(), null);
          if (null != queryTALK) chapterResourceList.add(queryTALK);
          if (null != chapterResourceList && chapterResourceList.size() > 0)
            for (ProductItemModel chapterr : chapterResourceList) {
              ResourceVo resource = new ResourceVo();
              resource.getResourceFromProductItem(chapterr);
              // 讨论 -1：参与讨论，0：查看详情
              if (chapterr.getRecordNum() == 0) {
                resource.setJoinResult("你还没有发言，快去参于讨论吧");
                resource.setResourceStatus("-1");
              } else {
                resource.setJoinResult(String.format("已有%s次发言", chapterr.getRecordNum()));
                resource.setResourceStatus("0");
              }
              resourceList.add(resource);
            }
          chapterResource.setResourceList(resourceList);
          directory.add(chapterResource);
          if (null != directory) response.setDirectory(directory);
        }
    } catch (Exception e) {
      LoggerUtil.error("查找课程主页-讨论异常", e);
      response = new CourseResourceResponse(ResultType.VALFAIL);
    }
    return response;
  }

  /**
   * 课程主页-最新
   * 
   * @param pojo
   * @return
   */
  public NewestCourseResponse newestCourse(NewestCourseRequest pojo) {
    NewestCourseResponse response = new NewestCourseResponse(ResultType.SUCCESS);
    try {
      if (!super.checkCourseId(pojo.getCourseId()).isRetOk())
        return new NewestCourseResponse(super.checkCourseId(pojo.getCourseId()).getResType());
      List<NewestResource> latestList = Lists.newArrayList();
      List<ProductItemModel> chapterResourceList =
          productItemService.queryResourceItemList(Integer.valueOf(pojo.getCourseId()), null,
              pojo.getSinceId());
      Map<String, List<ResourceVo>> resourceListMap =
          new TreeMap<String, List<ResourceVo>>(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
              // 降序排序
              return obj2.compareTo(obj1);
            }
          });
      // 构造树
      if (null != chapterResourceList && chapterResourceList.size() > 0) {
        for (ProductItemModel item : chapterResourceList) {
          if (null != item) {
            ResourceVo resource = new ResourceVo();
            if (null != item.getResourceId()) resource.setResourceId(item.getId().toString());
            resource.setResourceName(item.getName());
            if (null != item.getResourceType())
              resource.setResourceType(item.getResourceType().toString());
            if (StringUtils.isJsonNotBlank(item.getResource())) {
              Resourcer _resource = FastJsonUtil.fromJson(item.getResource(), Resourcer.class);
              resource.setResourceUrl(_resource.getFileUrl());
              resource.setResourcePic(_resource.getCover());
            }
            String resourceTime = CourseConstant.SDF_MD.format(item.getCreateTime());
            if (!resourceListMap.containsKey(resourceTime)) {
              List<ResourceVo> lists = Lists.newArrayList();
              lists.add(resource);
              resourceListMap.put(resourceTime, lists);
            } else {
              List<ResourceVo> lists = resourceListMap.get(resourceTime);
              lists.add(resource);
              resourceListMap.put(resourceTime, lists);
            }
          }
        }
      }
      // 替换时间
      for (String resourceTime : resourceListMap.keySet()) {
        NewestResource newestResource = new NewestResource();
        if (CourseConstant.SDF_MD.format(new Timestamp(System.currentTimeMillis())).equals(
            resourceTime)) {
          newestResource.setTime(CourseConstant.NOWTIMESTRING);
        } else {
          newestResource.setTime(resourceTime);
        }
        newestResource.setResourceList(resourceListMap.get(resourceTime));
        latestList.add(newestResource);
      }
      if (null != latestList) response.setLatestList(latestList);

      ProductModel productModel = this.findProductById(Long.valueOf(pojo.getCourseId()));
      if (null == productModel) {
        response = new NewestCourseResponse(ResultType.SYSFAIL);
        response.setRetdesc("本课程不存在");
        return response;
      }
      CourseInfo courseInfo = this.getCourseInfo(pojo.getModule(), productModel, pojo.getUid());
      response.setCourseInfo(courseInfo);
      productServiceFacade.insertUserScanLog(pojo.getUid(), pojo.getCourseId());
    } catch (Exception e) {
      LoggerUtil.error("查找课程主页-最新异常", e);
      response = new NewestCourseResponse(ResultType.VALFAIL);
    }
    return response;

  }


  /**
   * 
   * @param catId(栏目id)
   * @return
   */
  public List<ProductModel> cascadeQueryProductByMod(String catId) {
    List<ProductModel> productModelList = Lists.newArrayList();
    Map<String, Object> cond = new HashMap<>();
    cond.put("productCategoryId", catId);
    // cond.put("module", module);
    cond.put("showStatus", CourseConstant.PRODUCT_SHOW_STATUS);
    Page<ProductModel> productModelPage = super.getProductModelPage(cond);
    if (null != productModelPage.getResult() && productModelPage.getResult().size() > 0) {
      productModelList = productModelPage.getResult();
    }
    return productModelList;
  }

  /**
   * 
   * @param courseName 课程名
   * @return
   */
  public List<ProductModel> cascadeQueryProductByName(String courseName) {
    List<ProductModel> productModelList = Lists.newArrayList();
    Map<String, Object> cond = new HashMap<>();
    cond.put("searchCourseName", courseName);
    cond.put("showStatus", CourseConstant.PRODUCT_SHOW_STATUS);
    // cond.put("addModuleCourse", module);
    Page<ProductModel> productModelPage = super.getProductModelPage(cond);
    if (null != productModelPage.getResult() && productModelPage.getResult().size() > 0) {
      productModelList = productModelPage.getResult();
    }
    return productModelList;
  }

  /**
   * @author 李德洪
   * @param typeCode
   * @return
   */
  public List<ProductModel> cascadeQueryProductByTypeCode(String typeCode) {
    List<ProductModel> productModelList = Lists.newArrayList();
    Map<String, Object> cond = new HashMap<>();
    // cond.put("module", module);
    cond.put("showStatus", CourseConstant.PRODUCT_SHOW_STATUS);
    cond.put("typeCode", typeCode);
    Page<ProductModel> productModelPage = super.getProductModelPage(cond);
    if (null != productModelPage.getResult() && productModelPage.getResult().size() > 0) {
      productModelList = productModelPage.getResult();
    }
    return productModelList;
  }

  /**
   * 校验专业与课程的一致性
   * 
   * @param catId(专业id)
   * @param module(app模块)
   * @param courseId(课程id)
   * @return
   */
  public boolean cascadeQueryProductByTypeCodeAndCId(String typeCode, String module, String courseId) {
    // 栏目
    ProductCategoryModel productCategory = productCategoryService.findCategoryByAppCode(typeCode);
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
      Page<ProductModel> productModelPage = super.getProductModelPage(cond);
      if (null != productModelPage && null != productModelPage.getResult()
          && productModelPage.getResult().size() > 0) return true;
    }
    return false;
  }

  /**
   * 讨论详情
   * 
   * @param pojo
   * @return
   */
  public TalkDetailResponse talkDetail(TalkDetailRequest pojo) {
    TalkDetailResponse response = new TalkDetailResponse(ResultType.SUCCESS);
    ProductModel productModel = super.findProductById(Long.valueOf(pojo.getCourseId()));
    if (null == productModel) return new TalkDetailResponse(ProductResType.FindCourseIdFailed);
    if (!productItemService.checkItemId(pojo.getResourceId()).isRetOk())
      return new TalkDetailResponse(ProductResType.FindItemIdFailed);
    ProductItemModel queryTALK =
        productItemService.queryTALK(null, null, null, pojo.getResourceId());
    if (null != queryTALK.getCommentCount())
      response.setCommentCount(queryTALK.getCommentCount().toString());
    if (null != queryTALK.getPraiseCount())
      response.setPraiseCount(queryTALK.getPraiseCount().toString());
    response.setTitle(queryTALK.getName());
    response.setContent(queryTALK.getDetail());
    response.setDeadline(String.format("评分截止日期 %s",
        DateUtil.SDF_FULL.format(queryTALK.getDeadline())));
    ProductItemModel itemModel = productItemService.findItemById(queryTALK.getParentId());
    if (null != itemModel)
      response.setFromResource(String.format("来自 %s 的 %s", productModel.getName(),
          itemModel.getName()));
    List<ForumPraiseModel> forumPraiseModels =
        this.queryPraiseList(pojo.getUid(), null, Long.valueOf(pojo.getResourceId()),
            Long.valueOf(pojo.getCourseId()), null);
    if (null != forumPraiseModels && forumPraiseModels.size() > 0)
      response.setIsPraise(CourseConstant.ISPRAISE);
    return response;
  }

  /**
   * 讨论模块 评论列表
   * 
   * @param pojo
   * @return
   */
  public TalkCommentListResponse talkCommentList(TalkCommentListRequest pojo) {
    TalkCommentListResponse response = new TalkCommentListResponse(ResultType.SUCCESS);
    if (!super.checkCourseId(pojo.getCourseId()).isRetOk())
      return new TalkCommentListResponse(ProductResType.FindCourseIdFailed);
    if (!productItemService.checkItemId(pojo.getResourceId()).isRetOk())
      return new TalkCommentListResponse(ProductResType.FindItemIdFailed);
    List<Comment> commentList =
        this.commentListForTalk(pojo.getUid(), pojo.getResourceId(), pojo.getCourseId(),
            pojo.getCommentId(), pojo.getSize());
    response.setCommentList(commentList);
    return response;
  }

  /**
   * 查找讨论列表
   * 
   * @param userId
   * @param itemId
   * @param productId
   * @param commentIdUpper
   * @param size
   * @return
   */
  private List<Comment> commentListForTalk(String userId, String itemId, String productId,
      String commentIdUpper, Integer size) {
    List<CommentUserModel> comments =
        forumServiceFacade.queryTalkCommentList(itemId, productId, commentIdUpper, size,
            CommUtil.getAllField(CommentUserModel.class));
    List<Comment> list = new ArrayList<Comment>();
    for (CommentUserModel model : comments) {
      Comment comment = forumDetailService.convertForumCommentToComment(model);
      List<ForumPraiseModel> forumPraiseModels =
          this.queryPraiseList(userId, null, Long.valueOf(itemId), Long.valueOf(productId),
              model.getId());
      if (null != forumPraiseModels && forumPraiseModels.size() > 0)
        comment.setIsPraise(CourseConstant.ISPRAISE);
      list.add(comment);
    }
    return list;
  }

  /**
   * 根据条件，查询赞列表
   * 
   * @param userId
   * @param resourcesId
   * @return
   */
  private List<ForumPraiseModel> queryPraiseList(String userId, Long resourcesId, Long itemId,
      Long productId, Long commentId) {
    Map<String, Object> inputArgument = new HashMap<String, Object>();
    if (StringUtils.isNotBlank("userId")) inputArgument.put("operator", userId);
    if (null != resourcesId) inputArgument.put("resourcesId", resourcesId);
    if (null != productId) inputArgument.put("productId", productId);
    if (null != itemId) inputArgument.put("itemId", itemId);
    if (null != commentId) inputArgument.put("commentId", commentId);
    List<ForumPraiseModel> forumPraiseModels =
        forumServiceFacade.queryPraiseList(inputArgument,
            CommUtil.getAllField(ForumPraiseModel.class));
    return forumPraiseModels;
  }


  /**
   * 点赞
   * 
   * @param pojo
   * @return
   */
  public BaseResponse praise(ForumPraiseRequest forumPraiseRequest) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    if (StringUtils.isBlank(forumPraiseRequest.getUid())) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    String commentId = forumPraiseRequest.getCommentId();
    CommentUserModel comment = null;
    if (StringUtils.isBlank(forumPraiseRequest.getCommentId()))
      commentId = "0";
    else {
      commentId = forumPraiseRequest.getCommentId();
      comment = forumServiceFacade.queryTalkCommentByCId(commentId);
      if (null == comment) {
        return new ForumResponse(ForumResType.NULLCOMMENT);
      }
    }
    ProductItemModel queryTALK =
        productItemService.findItemById(Long.valueOf(forumPraiseRequest.getResourcesId()));
    if (null == queryTALK) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    // 添加点赞表数据
    ForumPraiseModel forumPraiseModel =
        ForumPraiseCache.getForumPraiseFromCache("talk" + queryTALK.getId().toString(), commentId,
            forumPraiseRequest.getUid());
    if (null != forumPraiseModel) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    forumPraiseModel = new ForumPraiseModel();
    forumPraiseModel.setId(Long.parseLong(IDGenerator.getSeqID()));
    forumPraiseModel.setCommentId(Long.parseLong((commentId)));
    // 在token中取
    forumPraiseModel.setReplyId(Long.parseLong(forumPraiseRequest.getUid()));
    forumPraiseModel.setOperator(forumPraiseRequest.getUid());
    forumPraiseModel.setOperatorip(forumPraiseRequest.getClientIp());
    forumPraiseModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    forumPraiseModel.setProductId(queryTALK.getProductId());
    forumPraiseModel.setItemId(Long.valueOf(forumPraiseRequest.getResourcesId()));
    try {
      forumPraiseModel = forumServiceFacade.insertPraiseEntity(forumPraiseModel);
      if (null == forumPraiseModel) {
        return new BaseResponse(ResultType.SYSFAIL);
      }
      ForumPraiseCache.addForumPraiseCache(forumPraiseModel, "talk" + queryTALK.getId().toString(),
          commentId, forumPraiseRequest.getUid());
    } catch (Exception e) {
      LoggerUtil.error("[联合唯一索引不能有重复信息]", e);
      response.setRetcode(ResultType.VALFAIL.getCode());
      response.setRetdesc("不能重复点赞");
      return response;
    }
    // 找到对应的讨论或评论更新点赞数
    this.updatePraise(true, Long.parseLong(forumPraiseRequest.getResourcesId()),
        Long.parseLong(commentId));
    return response;
  }

  /**
   * 点赞取消点赞接口 更新点赞数
   */
  public void updatePraise(boolean praise, long itemId, long commentId) {
    ForumPraiseModel model = new ForumPraiseModel();
    model.setItemId(itemId);
    model.setCommentId(commentId);
    model.setPraise(praise);
    queueService.updateTalkPraiseNumber(model);
  }

  /**
   * 点赞
   * 
   * @param pojo
   * @return
   */
  public BaseResponse cancelPraise(ForumPraiseRequest forumPraiseRequest) {
    if (StringUtils.isBlank(forumPraiseRequest.getUid())) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    String commentId = forumPraiseRequest.getCommentId();
    CommentUserModel comment = null;
    if (StringUtils.isBlank(forumPraiseRequest.getCommentId()))
      commentId = "0";
    else {
      commentId = forumPraiseRequest.getCommentId();
      comment = forumServiceFacade.queryTalkCommentByCId(commentId);
      if (null == comment) {
        return new ForumResponse(ForumResType.NULLCOMMENT);
      }
    }
    ProductItemModel queryTALK =
        productItemService.findItemById(Long.valueOf(forumPraiseRequest.getResourcesId()));
    if (null == queryTALK) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("itemId", forumPraiseRequest.getResourcesId());
    input.put("commentId", commentId);
    input.put("replyId", forumPraiseRequest.getUid());
    int flag = forumServiceFacade.deletePraiseList(input);
    if (flag != 1) {
      BaseResponse response = new BaseResponse(ResultType.VALFAIL);
      response.setRetdesc("取消点赞失败");
      return response;
    }
    // 找到对应的话题或回复更新点赞数
    updatePraise(false, Long.parseLong(forumPraiseRequest.getResourcesId()),
        Long.parseLong(commentId));
    return new BaseResponse(ResultType.SUCCESS);
  }
}
