package com.xiaodou.ms.service.product;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.exam.QuestionBankExamRuleDao;
import com.xiaodou.ms.dao.mission.MissionDao;
import com.xiaodou.ms.dao.product.FinalExamDao;
import com.xiaodou.ms.dao.product.ProductDao;
import com.xiaodou.ms.dao.product.ProductItemDao;
import com.xiaodou.ms.dao.product.ProductQuestionDao;
import com.xiaodou.ms.dao.robot.RobotDao;
import com.xiaodou.ms.dao.user.UserDao;
import com.xiaodou.ms.model.exam.QuestionBankExamRuleModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionType;
import com.xiaodou.ms.model.exam.QuestionBankSetting;
import com.xiaodou.ms.model.mission.MissionModel;
import com.xiaodou.ms.model.product.FinalExamModel;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.ms.model.product.ProductItemModel;
import com.xiaodou.ms.model.product.ProductMiscInfo;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.product.ProductQuestionModel;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.model.robot.ChallengeRobotModel;
import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.ms.service.exam.QuestionBankExamRuleService;
import com.xiaodou.ms.service.mission.MissionService;
import com.xiaodou.ms.service.robot.RobotService;
import com.xiaodou.ms.util.IDGenerator;
import com.xiaodou.ms.vo.ExamRuleVO;
import com.xiaodou.ms.vo.ExamRuleVO.InnerObj;
import com.xiaodou.ms.vo.PreCondListVO;
import com.xiaodou.ms.vo.mq.AddProductEvent;
import com.xiaodou.ms.vo.mq.AddProductEvent.TransferProductData;
import com.xiaodou.ms.vo.mq.DeleteProductEvent;
import com.xiaodou.ms.vo.mq.ModifyProductEvent;
import com.xiaodou.ms.web.request.mission.MissionRequest;
import com.xiaodou.ms.web.request.product.FinalExamRequest;
import com.xiaodou.ms.web.request.product.ProductCreateRequest;
import com.xiaodou.ms.web.request.product.ProductEditRequest;
import com.xiaodou.ms.web.request.product.ProductQueryConds;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.ResourceCourseCreateResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.JoinQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 15/4/19.
 */
@Service("productService")
public class ProductService {

  @Resource
  ProductCategoryService productCategoryService;

  @Resource
  MissionService missionService;

  @Resource
  QuestionBankExamRuleService questionBankExamRuleService;

  @Resource
  ProductItemService productItemService;

  @Resource
  ProductQuestionService productQuestionService;

  @Resource
  FinalExamService finalExamService;

  @Resource
  RobotService robotService;

  @Resource
  FinalExamDao finalExamDao;
  @Resource
  ProductDao productDao;
  @Resource
  MissionDao missionDao;
  @Resource
  ProductQuestionDao productQuestionDao;
  @Resource
  ProductItemDao productItemDao;
  @Resource
  QuestionBankExamRuleDao questionBankExamRuleDao;
  @Resource
  RobotDao robotDao;
  @Resource
  RegionService regionService;
  @Resource
  UserDao userDao;

  /**
   * 查询
   * 
   * @param id
   * @return
   */
  public ProductModel findSubjectById(Long id) {
    ProductModel cond = new ProductModel();
    cond.setId(id);
    return productDao.findEntityById(cond);
  }

  /**
   * 新增课程
   * 
   * @param entity
   * @return
   */
  public ProductModel addSubject(ProductModel entity) {
    productDao.addEntity(entity);
    // 判断不是新手课程
    // 00000是新手课程
    if (!XdmsConstant.MODULE.equals(entity.getModuleCourse())
        && !entity.getCourseCode().equals(XdmsConstant.QUICK_START_COURSE)) {
      AddProductEvent event = new AddProductEvent();
      event.setModule(entity.getModule());
      event.setDataModel(new TransferProductData(entity));
      event.send();
    }
    return entity;
  }

  /**
   * 更新课程
   * 
   * @param productModel
   * @return
   */
  public Boolean editSubject(ProductModel productModel) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", productModel.getId());
    Boolean result = productDao.updateEntity(cond, productModel);
    if (result) {
      ModifyProductEvent event = new ModifyProductEvent();
      event.setModule(productModel.getModule());
      event.setDataModel(new TransferProductData(productModel));
      event.send();
    }
    return result;
  }

  /**
   * 更新课程
   * 
   * @param request
   * @return
   */
  public Boolean editSubject(ProductEditRequest request) {
    ProductModel productModel = new ProductModel();
    productModel.setDetail(request.getDetail());
    productModel.setId(request.getId());
    productModel.setName(request.getName());
    productModel.setBriefInfo(request.getBriefInfo());
    if (StringUtils.isNotBlank(request.getImageUrl())) {
      productModel.setImageUrl(request.getImageUrl());
    }
    productModel.setOriginalAmount(request.getOriginalAmount());
    productModel.setPayAmount(request.getPayAmount());
    productModel.setShareUrl(request.getShareUrl());
    productModel.setShowStatus(request.getShowStatus());
    productModel.setResourceSubject(request.getResourceSubject());
    productModel.setModule(request.getModule());
    List<RegionModel> region = regionService.queryRegionWithModule(request.getModule());
    if (region != null) {
      productModel.setRuleId(region.get(0).getRuleId());
    }

    try {
      productModel.setBeginApplyTime(new Timestamp(DateUtil.SDF_FULL.parse(
          request.getBeginApplyTime()).getTime()));
      productModel.setEndApplyTime(new Timestamp(DateUtil.SDF_FULL.parse(request.getEndApplyTime())
          .getTime()));
    } catch (Exception e) {}
    return this.editSubject(productModel);
  }

  /**
   * 查询所有的课程
   * 
   * @return
   */
  public Page<ProductModel> queryAllCourse() {
    Map<String, Object> cond = new HashMap<>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("createTime", "");
    return productDao.queryListByCond0(cond, output, null);
  }

  /**
   * 根据栏目查询
   * 
   * @param catId
   * @return
   */
  public Page<ProductModel> queryCourseByCatId(Integer catId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("productCategoryId", catId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("productCategoryId", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("createTime", "");
    return productDao.queryListByCond0(cond, output, null);
  }

  /**
   * 根据栏目级联查询课程
   * 
   * @param catId
   * @return
   */
  public Page<ProductModel> cascadeQueryCourseByCatId(ProductQueryConds req, Integer pageNo/*
                                                                                            * , Long
                                                                                            * catId,
                                                                                            * String
                                                                                            * courseCode
                                                                                            */) {
    Page<ProductModel> page = new Page<ProductModel>();
    page.setPageSize(10);
    if (null == pageNo) {
      page = null;
    } else {
      page.setPageNo(pageNo);
    }

    Page<ProductCategoryModel> productCategoryModels =
        productCategoryService.queryAllChildCategory(null, null, req.getCatId(), null);
    List<Long> ids = new ArrayList<>();
    for (ProductCategoryModel categoryModel : productCategoryModels.getResult()) {
      ids.add(categoryModel.getId());
    }
    ids.add(req.getCatId());
    IJoinQueryParam param = new JoinQueryParam();
    param.addJoin("productCategoryIds", ids);
    // if (StringUtils.isNotBlank(req.getCourseCode()))
    // param.addInput("courseCode",
    // req.getCourseCode());
    param.addInput("moduleCourse", 0);

    // 模糊查询的条件
    if (StringUtils.isNotBlank(req.getCourseCode())) {
      param.addInput("courseCodeLike", req.getCourseCode());
    }
    if (StringUtils.isNotBlank(req.getName())) {
      param.addInput("nameLike", req.getName());
    }
    if (null != req.getCourseId()) {
      param.addInput("id", req.getCourseId());
    }

    if (StringUtils.isNotBlank(req.getIsExpired()) && req.getIsExpired().equals("1")) {
      // TODO 过期课程的处理
      param.addInput("endApplyTimeUpper", new Timestamp(System.currentTimeMillis()));
    } else if (StringUtils.isNotBlank(req.getIsExpired()) && req.getIsExpired().equals("0")) {
      // TODO 未过期课程的处理
      param.addInput("endApplyTimeLower", new Timestamp(System.currentTimeMillis()));
    }
    if (StringUtils.isNotBlank(req.getShowStatus())) {
      param.addInput("showStatus", req.getShowStatus());
    }

    param.addOutputs(CommUtil.getAllField(ProductModel.class));
    param.addSort("productCategoryId", Sort.DESC);
    param.addSort("updateTime", Sort.DESC);
    param.addSort("createTime", Sort.DESC);
    return productDao.cascadeQueryProduct(param, page);
  }

  /**
   * 根据课程码查询课程
   * 
   * @param courseCode
   * @return
   */
  public Page<ProductModel> queryCourseByCode(ProductQueryConds req, Integer pageNo,
      String examDate, String moduleCourse) {
    Page<ProductModel> page = new Page<ProductModel>();
    page.setPageSize(15);
    if (null == pageNo) {
      page = null;
    } else {
      page.setPageNo(pageNo);
    }

    IQueryParam param = new QueryParam();
    if (StringUtils.isNotBlank(examDate)) {
      param.addInput("examDate", examDate);
    }
    // 分开查看新手课程，暂时把新手课程放在所有课程里面
    /*
     * if (moduleCourse.equals("0")) { param.addInput("moduleCourse", moduleCourse); } else {
     * param.addInput("HasModuleCourse", "0"); }
     */

    INPUT: {
      if (req == null) {
        break INPUT;
      }
      if (StringUtils.isNotBlank(req.getCourseCode())) {
        param.addInput("courseCodeLike", req.getCourseCode());
      }
      if (StringUtils.isNotBlank(req.getName())) {
        param.addInput("nameLike", req.getName());
      }
      if (null != req.getCourseId()) {
        param.addInput("id", req.getCourseId().longValue());
      }
      if (StringUtils.isNotBlank(req.getModule())) {
        param.addInput("module", req.getModule());
      }

      if (StringUtils.isNotBlank(req.getIsExpired()) && req.getIsExpired().equals("1")) {
        // TODO 过期课程的处理
        param.addInput("endApplyTimeUpper", new Timestamp(System.currentTimeMillis()));
      } else if (StringUtils.isNotBlank(req.getIsExpired()) && req.getIsExpired().equals("0")) {
        // TODO 未过期课程的处理
        param.addInput("endApplyTimeLower", new Timestamp(System.currentTimeMillis()));
      }
      if (StringUtils.isNotBlank(req.getShowStatus())) {
        param.addInput("showStatus", req.getShowStatus());
      }
    }

    param.addSort("createTime", Sort.DESC);
    param.addSort("updateTime", Sort.ASC);
    param.addOutputs(CommUtil.getGeneralField(ProductModel.class));
    return productDao.findEntityListByCond(param, page);
  }

  /**
   * 删除课程同时删除课程的任务
   * 
   * @param id
   * @return
   */
  public Boolean deleteSubject(Long id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    missionService.deleteDyMission(id.toString());
    Boolean result = productDao.deleteEntity(cond);
    if (result) {
      questionBankExamRuleService.deleteProductRule(id);
      ProductModel entity = new ProductModel();
      entity.setId(id.longValue());
      entity = productDao.findEntityById(entity);
      // 机器人也要删除
      DeleteProductEvent event = new DeleteProductEvent();
      event.setModule(entity.getModule());
      event.setDataModel(new TransferProductData(id, entity.getModule()));
      event.send();
    }
    return result;
  }

  /**
   * 新增课程同时增加动态任务-随机PK任务消灭错题任务
   * 
   * @param request
   * @return
   * @throws ParseException
   */
  public ResourceCourseCreateResponse addSubject(ProductCreateRequest request, Integer isBeginner)
      throws ParseException {
    //
    ProductModel subjectModel = new ProductModel();
    subjectModel.setId(Long.parseLong(RandomUtil.randomNumber(9)));
    subjectModel.setName(request.getName());
    subjectModel.setDetail(request.getDetail());
    subjectModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    subjectModel.setType(request.getType());
    subjectModel.setResourceSubject(request.getResourceSubject());
    subjectModel.setOriginalAmount(request.getOriginalAmount());
    subjectModel.setPayAmount(request.getPayAmount());
    subjectModel.setShowStatus(request.getShowStatus());
    subjectModel.setImageUrl(request.getImageUrl());
    subjectModel.setBriefInfo(request.getBriefInfo());
    subjectModel.setShareUrl(request.getShareUrl());
    subjectModel.setCourseCode(request.getCourseCode());
    subjectModel.setQuestionBankSetting(builderQuesSetting());
    if (isBeginner == 1) {
      subjectModel.setModuleCourse(request.getModule());// app端的判断标准
    } else {
      subjectModel.setModuleCourse("0");// app端的判断标准 ,
    }
    subjectModel.setModule(request.getModule());// 地域
    subjectModel.setRuleId(request.getRuleId());// 规则id
    if (StringUtils.isNotBlank(request.getExamDate())) {
      subjectModel.setExamDate(request.getExamDate());
    }
    if (StringUtils.isNotBlank(request.getBeginApplyTime())) {
      Date date = DateUtil.SDF_FULL.parse(request.getBeginApplyTime());
      subjectModel.setBeginApplyTime(new Timestamp(date.getTime()));
    }
    if (StringUtils.isNotBlank(request.getEndApplyTime())) {
      Date date = DateUtil.SDF_FULL.parse(request.getEndApplyTime());
      subjectModel.setEndApplyTime(new Timestamp(date.getTime()));
    }
    ProductMiscInfo misc = new ProductMiscInfo();
    subjectModel.setMisc(misc);
    ProductModel productModel = this.addSubject(subjectModel);
    if (!request.getCourseCode().equals("00000")) {
      MissionRequest missionRequest = new MissionRequest();
      missionRequest.setModule(request.getModule());
      missionRequest.setCourseId(productModel.getId().toString());
      missionRequest.setCondType("randomPk");
      missionService.addDynamicMissionByCourse(missionRequest);
      missionRequest.setCondType("wrongques");
      missionService.addDynamicMissionByCourse(missionRequest);
      missionRequest.setCondType("dailyPractice");
      missionService.addDynamicMissionByCourse(missionRequest);
      missionRequest.setCondType("leakFilling");
      missionService.addDynamicMissionByCourse(missionRequest);
    }

    questionBankExamRuleService.initFromDefaultRule(productModel.getId());


    // 在添加课程时默认绑定3门期末测试
    FinalExamRequest finalExamRequest = new FinalExamRequest();
    String[] examNames = new String[] {"一", "二", "三"};
    String examName = null;
    Integer questionNums = 10;
    Integer examSort = 10000;
    Long courseId = productModel.getId();
    int defaultExamNum = 3;
    for (int i = 0; i < defaultExamNum; i++) {
      examName = "期末测试" + examNames[i];
      finalExamRequest.setExamName(examName);
      finalExamRequest.setQuestionNums(questionNums);
      finalExamRequest.setExamSort(examSort + i);
      finalExamRequest.setCourseId(courseId);
      finalExamService.doAdd(finalExamRequest);
    }

    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);



    /**
     * 给刚生成的课程分配机器人
     * 
     * @return
     */
    IQueryParam param = new QueryParam();
    param.addInput("userType", -1);
    param.addOutputs(CommUtil.getGeneralField(UserModel.class));
    Page<UserModel> pageUser = userDao.findEntityListByCond(param, null);
    List<UserModel> result = pageUser.getResult();
    Collections.shuffle(result);
    for (int index = 0; index < result.size(); index++) {
      if (index > 9) break;
      UserModel robot = result.get(index);
      ChallengeRobotModel _model = new ChallengeRobotModel();
      _model.setUserId(robot.getId());
      _model.setMajorId("-1"); // 机器人中的专业默认设置为-1
      _model.setCourseId(subjectModel.getId());
      _model.setCategoryId(0L);
      _model.setTargetAbility(50);
      _model.setModule(subjectModel.getModule());
      robotDao.addEntity(_model);
    }
    return response;
  }

  /**
   * 题库设置默认单选
   * 
   * @return
   */
  private String builderQuesSetting() {
    QuestionBankSetting setting = new QuestionBankSetting();
    setting.setTotalScore(100);
    List<QuestionBankQuestionType> typeList = Lists.newArrayList();
    QuestionBankQuestionType type = new QuestionBankQuestionType();
    type.setAnswerType("0");
    type.setId(1);
    type.setListOrder(0);
    type.setQuestionNum(10);
    type.setScore(10);
    type.setTypeName("单项选择题");
    typeList.add(type);
    setting.setTypeList(typeList);
    return FastJsonUtil.toJson(setting);
  }



  public void updateSubjectMisc(Long id, ProductMiscInfo misc) {
    ProductModel model = new ProductModel();
    model.setMisc(misc);
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("id", id);
    if (productDao.updateEntity(cond, model)) {
      ModifyProductEvent event = new ModifyProductEvent();
      model.setId(id);
      ProductModel entity = productDao.findEntityById(model);
      event.setModule(entity.getModule());
      event.setDataModel(new TransferProductData(model));
      event.send();
    }
  }

  public List<ProductModel> findListByCourseCode(String forumTag) {
    IQueryParam param = new QueryParam();
    param.addOutput("id", "");
    param.addInput("courseCode", forumTag);
    Page<ProductModel> results = productDao.findEntityListByCond(param, null);
    if (results != null) {
      return results.getResult();
    }
    return null;
  }

  /**
   * <p>
   * 查询出该课程下所有的产品
   * </p>
   * 
   * @param courseCode
   * @return
   */
  public List<ProductModel> queryProductByCourse(String courseCode) {
    IQueryParam param = new QueryParam();
    param.addInput("courseCode", courseCode);
    param.addOutputs(CommUtil.getGeneralField(ProductModel.class));
    Page<ProductModel> entitys = productDao.findEntityListByCond(param, null);
    return entitys == null ? null : entitys.getResult();
  }

  /**
   * 复制课程同时增加动态任务-随机PK任务消灭错题任务
   * 
   * @param request
   * @return
   * @throws ParseException
   */
  public synchronized ResourceCourseCreateResponse doCopySubject(ProductCreateRequest request)
      throws ParseException {
    // step2: 复制xd_course_product_item
    List<ProductItemModel> allItemsOfProduct = productItemService.queryAllItem(request.getId());
    if (allItemsOfProduct.isEmpty()) {
      return new ResourceCourseCreateResponse(ResultType.LACK_CHAPTER_ITEM_INVALID);
    }

    // step1: 复制xd_course_proudct
    ProductModel cloneProudct = copyProudct(request);
    // 映射现有id和旧id
    Map<Long, Long> idmaps = Maps.newConcurrentMap();
    for (ProductItemModel pim : allItemsOfProduct) {
      pim.setProductId(cloneProudct.getId());
      idmaps.put(pim.getId(), Long.parseLong(IDGenerator.getSeqID()));
    }

    // step5: 复制 xd_course_product_final_exam
    Page<FinalExamModel> allFinals = finalExamService.queryAllRecord(request.getId());
    HashMap<Long, Long> finalMaps = Maps.newHashMap();
    if (null != allFinals && allFinals.getResult().size() > 0) {
      for (FinalExamModel finalExamModel : allFinals.getResult()) {
        if (finalExamModel == null) {
          continue;
        }
        finalMaps.put(finalExamModel.getId(), Long.parseLong(IDGenerator.getSeqID()));
      }
    }

    if (null != allFinals && allFinals.getResult().size() != 0) {
      for (FinalExamModel finalExam : allFinals.getResult()) {
        if (null == finalExam) {
          continue;
        }
        finalExam.setCourseId(cloneProudct.getId().longValue());
        if (finalMaps.containsKey(finalExam.getId())) {
          finalExam.setId(finalMaps.get(finalExam.getId()));
        }
        finalExam.setCreateTime(new Timestamp(System.currentTimeMillis()));
        // 保存 TODO
        finalExam = finalExamDao.addEntity(finalExam);
      }
    }

    // step6: 复制 xd_mission_model
    List<MissionModel> allMissions =
        missionService.queryMissionByProductId(null, request.getId() + "");
    HashMap<String, String> missionMap = Maps.newHashMap();
    if (allMissions != null && allMissions.size() > 0) {
      for (MissionModel missionModel : allMissions) {
        missionMap.put(missionModel.getId(), UUID.randomUUID().toString());
      }
    }
    if (allMissions != null && allMissions.size() != 0) {
      for (MissionModel newMission : allMissions) {
        if (newMission == null) {
          continue;
        }
        if (!CollectionUtils.isEmpty(missionMap) && missionMap.containsKey(newMission.getId())) {
          newMission.setId(missionMap.get(newMission.getId()));
        }
        newMission.setCourseId(cloneProudct.getId().toString());
        if (idmaps.containsKey(Long.parseLong(newMission.getChapterId()))) {
          newMission.setChapterId(idmaps.get(Long.parseLong(newMission.getChapterId())).toString());
        }
        if (newMission.getTaskType().equals("standard")) {
          // threshold = 章节ID
          if (!newMission.getChapterId().equals("-1")) {
            if (idmaps.containsKey(Long.parseLong(newMission.getThreshold()))) {
              newMission.setThreshold(idmaps.get(Long.parseLong(newMission.getThreshold()))
                  .toString());
            }
          } else {
            // threshold = final_examl_id
            if (finalMaps != null && finalMaps.size() > 0
                && finalMaps.containsKey(Integer.parseInt(newMission.getThreshold()))) {
              newMission.setThreshold(finalMaps.get(Integer.parseInt(newMission.getThreshold()))
                  .toString());
            }
          }
        }
        // threshold = courseId
        else if (newMission.getTaskType().equals("dynamic")
            && !newMission.getCourseId().equals("-1")) {
          newMission.setThreshold(newMission.getCourseId());
        }

        // 修改其precond_list数据
        UPDATE_PRE_COND: {
          if (newMission.getChapterId().equals("-1")) {
            String preCondList = newMission.getPreCondList();
            PreCondListVO vo = FastJsonUtil.fromJson(preCondList, PreCondListVO.class);
            if (null != vo && StringUtils.isNotBlank(vo.getThreshold())) {
              if (StringUtils.isBlank(vo.getThreshold())) {
                break UPDATE_PRE_COND;
              }
              if (request.getId().toString().equals(vo.getThreshold())) {
                vo.setThreshold(cloneProudct.getId().toString());
              } else if (idmaps.containsKey(Long.parseLong(vo.getThreshold()))) {
                vo.setThreshold(idmaps.get(Long.parseLong(vo.getThreshold())).toString());
              }
              newMission.setPreCondList(JSON.toJSONString(vo));
            }
          }
        }

        // 对mission_order进行设置
        if (null != newMission.getMissionOrder()
            && newMission.getMissionOrder().toString().equals(request.getId().toString())) {
          newMission.setMissionOrder(cloneProudct.getId().intValue());
        }
        newMission.setModule(request.getModule()); // 设置MISSION地域为用户选择地域
        newMission = missionDao.addEntity(newMission);
      }
    }

    // step3: 复制 xd_course_product_question
    List<ProductQuestionModel> ques =
        productQuestionService.queryQuesListByProductId(request.getId());
    if (ques != null && ques.size() > 0) {
      for (ProductQuestionModel pqm : ques) {
        pqm.setId(Long.parseLong(IDGenerator.getSeqID()));
        pqm.setProductId(cloneProudct.getId());
        if (idmaps.containsKey(pqm.getChapterId())) {
          pqm.setChapterId(idmaps.get(pqm.getChapterId()));
        }
        pqm.setCreateTime(new Timestamp(System.currentTimeMillis()));
        // 保存 TODO
        productQuestionDao.addEntity(pqm);
      }
    }

    // 重新洗牌allItemsOfProduct
    rebuiltItemsOfProduct(allItemsOfProduct, idmaps, cloneProudct);
    // 保存 TODO
    if (allItemsOfProduct.size() != 0) {
      for (ProductItemModel pim : allItemsOfProduct) {
        if (pim == null) {
          continue;
        }
        pim.setCreateTime(new Timestamp(System.currentTimeMillis()));
        productItemDao.addEntity(pim);
      }
    }

    // step4: 复制 xd_quesbk_exam_rule
    List<QuestionBankExamRuleModel> examRulesOfProduct =
        questionBankExamRuleService.findExamRulesByProductId(request.getId());
    if (examRulesOfProduct != null && examRulesOfProduct.size() != 0) {
      for (QuestionBankExamRuleModel qberm : examRulesOfProduct) {
        if (null == qberm) {
          continue;
        }
        qberm.setId(Integer.parseInt(IDGenerator.getSeqID()));
        qberm.setProductId(cloneProudct.getId());

        // 修改rule_detail
        if (StringUtils.isNotBlank(qberm.getRuleDetail())) {
          ExamRuleVO ExamRuleVOList =
              FastJsonUtil.fromJsons(qberm.getRuleDetail(), new TypeReference<ExamRuleVO>() {});
          if (null == ExamRuleVOList || ExamRuleVOList.getItemList().size() == 0) {
            continue;
          }
          for (InnerObj rule : ExamRuleVOList.getItemList()) {
            Long productChapterId = rule.getProductChapterId();
            if (null != productChapterId && productChapterId != -1L) {
              rule.setProductChapterId(cloneProudct.getId());
            } else {
              rule.setProductChapterId(-1L);
            }
          }
          qberm.setRuleDetail(FastJsonUtil.toJson(ExamRuleVOList));
        }
        // 保存 TODO
        questionBankExamRuleDao.addEntity(qberm);
      }
    }

    // step6: 复制 xd_challenge_robot
    List<ChallengeRobotModel> allRecords = robotService.queryByProductId(request.getId());
    if (!CollectionUtils.isEmpty(allRecords)) {
      for (ChallengeRobotModel challengeRobotModel : allRecords) {
        ChallengeRobotModel model = new ChallengeRobotModel();
        model.setCourseId(cloneProudct.getId());
        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        model.setId(Long.parseLong(IDGenerator.getSeqID()));
        model.setMajorId(challengeRobotModel.getMajorId());
        model.setTargetAbility(challengeRobotModel.getTargetAbility());
        model.setUserId(challengeRobotModel.getUserId());
        // 保存 TODO
        robotDao.addEntity(model);
      }
    }

    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
    return response;
  }

  /**
   * 复制product
   * 
   * @param request
   * @return
   */
  private ProductModel copyProudct(ProductCreateRequest request) {
    ProductModel entity = new ProductModel();
    entity.setId(request.getId());
    entity = productDao.findEntityById(entity);
    ProductModel model = new ProductModel();
    // 不需要copy的
    model.setName(StringUtils.isNotBlank(request.getName()) ? request.getName() : entity.getName());
    model.setExamDate(StringUtils.isNotBlank(request.getExamDate())
        ? request.getExamDate()
        : entity.getExamDate());
    model.setImageUrl(StringUtils.isNotBlank(request.getImageUrl())
        ? request.getImageUrl()
        : entity.getImageUrl());
    model.setBriefInfo(StringUtils.isNotBlank(request.getBriefInfo())
        ? request.getBriefInfo()
        : entity.getBriefInfo());
    model.setOriginalAmount(request.getOriginalAmount());
    model.setPayAmount(request.getPayAmount());
    model.setResourceSubject(request.getResourceSubject());
    model.setShowStatus(request.getShowStatus());
    model.setId(Long.parseLong(IDGenerator.getSeqID()));
    model.setBeginApplyTime(StringUtils.isNotBlank(request.getBeginApplyTime()) ? Timestamp
        .valueOf(request.getBeginApplyTime()) : entity.getBeginApplyTime());
    model.setEndApplyTime(StringUtils.isNotBlank(request.getEndApplyTime()) ? Timestamp
        .valueOf(request.getEndApplyTime()) : entity.getEndApplyTime());

    model.setCategoryName(entity.getCategoryName());
    model.setCourseCheckType(entity.getCourseCheckType());
    model.setCourseCode(entity.getCourseCode());
    model.setCourseCredit(entity.getCourseCredit());
    model.setCourseInfo(entity.getCourseInfo());
    model.setCourseName(entity.getCourseName());
    model.setCourseQuality(entity.getCourseQuality());
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    model.setCurrApplyCount(entity.getCurrApplyCount());
    model.setDetail(entity.getDetail());

    model.setExamDateType(entity.getExamDateType());
    model.setExamDetail(entity.getExamDetail());

    model.setMisc(entity.getMisc());
    if (model.getCourseCode().equals("00000")) {
      model.setModuleCourse(request.getModule());
    } else {
      model.setModuleCourse("0");
    }
    model.setModule(request.getModule());// 从request来的地域

    List<RegionModel> region = regionService.queryRegionWithModule(request.getModule());
    if (!CollectionUtils.isEmpty(region)) {
      model.setRuleId(region.get(0).getRuleId());
    }

    model.setPraiseCount(entity.getPraiseCount());
    // model.setProductCategoryId(entity.getProductCategoryId());
    model.setQuestionBankSetting(entity.getQuestionBankSetting());
    model.setRelationState(entity.getRelationState());
    model.setRelationTime(entity.getRelationTime());

    model.setShareUrl(entity.getShareUrl());

    model.setTotalApplyCount(entity.getTotalApplyCount());
    model.setType(entity.getType());
    model.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    return productDao.addEntity(model);
  }

  /**
   * @param allItemsOfProduct 根据源课程查出的item数据
   * @param idmaps item表item主键映射容器
   * @param pm pm克隆生成的product
   */
  private void rebuiltItemsOfProduct(List<ProductItemModel> allItemsOfProduct,
      Map<Long, Long> idmaps, ProductModel pm) {
    Assert.notEmpty(allItemsOfProduct, "allItemsOfProduct must have elements");

    for (ProductItemModel pim : allItemsOfProduct) {
      if (pim == null) {
        continue;
      }
      // 主键
      if (idmaps.containsKey(pim.getId())) {
        pim.setId(idmaps.get(pim.getId()));
      }

      // 修改 productId
      pim.setProductId(pm.getId());

      // 修改parentId
      if (idmaps.containsKey(pim.getParentId())) {
        pim.setParentId(idmaps.get(pim.getParentId()));
      }

      // 修改all_parent_id
      pim.setAllParentId(batchRebuildIds(pim.getAllParentId(), idmaps));

      // 修改child_id
      pim.setChildId(batchRebuildIds(pim.getChildId(), idmaps));

      // 修改all_child_id
      pim.setAllChildId(batchRebuildIds(pim.getAllChildId(), idmaps));

      // 修改relation_item
      if (pim.getRelationItem() != 0 && idmaps.containsKey(pim.getRelationItem())) {
        pim.setRelationItem(idmaps.get(pim.getRelationItem()));
      }

      // others
      pim.setCreateTime(new Timestamp(System.currentTimeMillis()));
    }
  }

  private String batchRebuildIds(String ids, Map<Long, Long> idmaps) {
    if (StringUtils.isBlank(ids)) {
      return StringUtils.EMPTY;
    }
    StringBuilder sb = new StringBuilder();
    String[] split = ids.split(",");
    INPUTS: {
      if (split == null || split.length == 0) {
        break INPUTS;
      }
      for (String line : split) {
        if (idmaps.containsKey(line)) {
          sb.append(idmaps.get(line)).append(",");
          continue;
        }
        sb.append(line).append(",");
      }
    }
    return sb.toString();
  }

  public Page<ProductModel> findListByCond(ProductModel cond) {
    IQueryParam param = new QueryParam();
    param.addInput("moduleCourse", cond.getModuleCourse());
    param.addOutputs(CommUtil.getGeneralField(ProductModel.class));
    return productDao.findEntityListByCond(param, null);
  }

  /**
   * 查找该地域下的新手课程
   * 
   * @param cond
   * @return
   */
  public Page<ProductModel> findListByCourseCode(ProductModel cond) {
    IQueryParam param = new QueryParam();
    param.addInput("courseCode", cond.getCourseCode());
    param.addInput("module", cond.getModule());
    param.addOutputs(CommUtil.getGeneralField(ProductModel.class));
    return productDao.findEntityListByCond(param, null);
  }

}
