package com.xiaodou.ms.service.mission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.dao.mission.MissionDao;
import com.xiaodou.ms.dao.mission.UserMissionRecordDao;
import com.xiaodou.ms.model.mission.MissionModel;
import com.xiaodou.ms.model.mission.MissionPreConditionModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.ms.vo.MissionVo;
import com.xiaodou.ms.web.request.mission.MissionRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.ResourceCourseCreateResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 15/4/19.
 */

@Service("missionService")
public class MissionService {

  // 任务
  @Resource
  MissionDao missionDao;
  /** userMissionRecordDao 用户任务记录操作Dao */
  @Resource
  UserMissionRecordDao userMissionRecordDao;
  // 课程章节
  @Resource
  CourseChapterService courseChapterService;

  @Resource
  CourseSubjectService courseSubjectService;

  @Resource
  ProductService productService;

  /**
   * 改造后的地域
   * */
  @Resource
  RegionService regionService;

  /**
   * 通用任务列表
   * 
   * @return
   */
  @SuppressWarnings({})
  public Page<MissionModel> missionTableTree(Integer pageNo, String courseName, String type,
      String courseId) {
    Page<MissionModel> page = new Page<MissionModel>();
    page.setPageSize(Page.DEFAULT_PAGESIZE);
    if (null == pageNo) {
      page = null;
    } else {
      page.setPageNo(pageNo);
    }

    Map<String, Object> cond = new HashMap<>();
    if (StringUtils.isNotBlank(courseId)) {
      cond.put("courseId", courseId);
    }
    if (StringUtils.isNotBlank(type)) {
      cond.put("taskType", type);
    }
    if (StringUtils.isNotBlank(courseName)) {
      cond.put("courseNameLike", courseName);
    }
    // Map<String, Object> output = new HashMap<>();
    // output.put("id", "");
    // output.put("missionName", "");
    // output.put("missionDesc", "");
    // output.put("missionOrder", "");
    // output.put("missionOrder", "");
    // output.put("courseId", "");
    // Page<MissionModel> missionModelPage = missionDao.queryListByCondWithOutPage(cond,
    // CommUtil.getAllField(MissionModel.class));

    return missionDao.queryListByCond(cond, CommUtil.getAllField(MissionModel.class), page);
    // return missionModelPage.getResult();
  }

  /**
   * 通用任务修改
   * 
   * @return
   */
  public MissionModel findMissionById(String id) {
    MissionModel entity = new MissionModel();
    entity.setId(id);
    return missionDao.findEntityById(entity);
  }
  


  /**
   * 通用任务删除
   * 
   * @param id
   * @return
   */
  public Boolean deleteMission(String id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    return missionDao.deleteEntity(cond);
  }

  /**
   * 通用新增任务
   * 
   * @param entity
   * @return
   */
  public MissionModel addMission(MissionModel entity) {
    return missionDao.addEntity(entity);
  }

  /**
   * 通用编辑任务
   * 
   * @param productModel
   * @return
   */
  public Boolean editMission(MissionModel missionModel) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", missionModel.getId());
    return missionDao.updateEntity(cond, missionModel);
  }

  /**
   * 新增系统任务
   * 
   * @param request
   * @return
   */
  public ResourceCourseCreateResponse addMission(MissionRequest request) {

    MissionModel missionModel = new MissionModel();
    missionModel.setId(UUID.randomUUID().toString());
    missionModel.setCourseId("-1");
    missionModel.setChapterId("-1");
    missionModel.setCondition(request.getCondition());
    missionModel.setCondType(request.getCondType());
    missionModel.setCreditUpper(20);
    missionModel.setExpiryDate(180);
    if (request.getJumpType() != null && !request.getJumpType().equals("0")) {
      missionModel.setJumpType(request.getJumpType());
    }
    missionModel.setMissionDesc(request.getMissionDesc());
    missionModel.setMissionName(request.getMissionName());
    missionModel.setMissionOrder(request.getMissionOrder());
    missionModel.setMissionPicUrl(request.getMissionPicUrl());
    missionModel.setMissionState(request.getMissionState());
    missionModel.setMissionType(request.getMissionType());
    missionModel.setCreditUpper(request.getCreditUpper());
    missionModel.setModule("-1");// 地域
    missionModel.setScope(request.getScope());
    missionModel.setTaskType("statics");
    missionModel.setThreshold(request.getThreshold());
    if (request.getPreCondList() != null && !request.getPreCondList().equals("0")) {
      MissionPreConditionModel model = new MissionPreConditionModel();
      model.setPreCond(request.getPreCondList());
      model.setThreshold(missionModel.getThreshold());
      missionModel.setPreCondList(FastJsonUtil.toJson(model));
    }
    missionDao.addEntity(missionModel);
    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
    return response;
  }

  /**
   * 编辑系统任务
   * 
   * @param request
   * @return
   */
  public Boolean editSysMission(MissionRequest request) {
    MissionModel missionModel = new MissionModel();
    missionModel.setId(request.getId());
    missionModel.setCondition(request.getCondition());
    missionModel.setCondType(request.getCondType());
    if (request.getJumpType() != null && !request.getJumpType().equals("0")) {
      missionModel.setJumpType(request.getJumpType());
    }
    missionModel.setMissionDesc(request.getMissionDesc());
    missionModel.setMissionName(request.getMissionName());
    missionModel.setMissionOrder(request.getMissionOrder());
    missionModel.setMissionPicUrl(request.getMissionPicUrl());
    missionModel.setMissionState(request.getMissionState());
    missionModel.setMissionType(request.getMissionType());
    missionModel.setCreditUpper(request.getCreditUpper());
    missionModel.setScope(request.getScope());
    missionModel.setThreshold(request.getThreshold());
    if (request.getPreCondList() != null && !request.getPreCondList().equals("0")) {
      MissionPreConditionModel model = new MissionPreConditionModel();
      model.setPreCond(request.getPreCondList());
      model.setThreshold(missionModel.getThreshold());
      missionModel.setPreCondList(FastJsonUtil.toJson(model));
    }
    return this.editMission(missionModel);
  }

  /**
   * 新增动态任务
   * 
   * @param request
   * @return
   */
  public ResourceCourseCreateResponse addDynamicMission(MissionRequest request) {
    MissionModel missionModel = new MissionModel();
    missionModel.setId(UUID.randomUUID().toString());
    missionModel.setCourseId(request.getCourseId());
    missionModel.setChapterId("-1");
    missionModel.setCondition(request.getCondition());
    missionModel.setCondType(request.getCondType());
    missionModel.setCreditUpper(request.getCreditUpper());
    missionModel.setExpiryDate(180);
    if (request.getJumpType() != null && !request.getJumpType().equals("0")) {
      missionModel.setJumpType(request.getJumpType());
    }
    missionModel.setMissionDesc(request.getMissionDesc());
    missionModel.setMissionName(request.getMissionName());
    missionModel.setMissionOrder(request.getMissionOrder());
    missionModel.setMissionPicUrl(request.getMissionPicUrl());
    missionModel.setMissionState(request.getMissionState());
    missionModel.setMissionType(request.getMissionType());

    ProductModel productModel = productService.findSubjectById(Long.valueOf(request.getCourseId()));
    missionModel.setModule(productModel.getModule());
    missionModel.setScope(request.getScope());
    missionModel.setTaskType("dynamic");
    missionModel.setThreshold(request.getThreshold());
    missionModel.setRedBonusType(request.getRedBonusType());
    if (request.getPreCondList() != null && !request.getPreCondList().equals("0")) {
      MissionPreConditionModel model = new MissionPreConditionModel();
      model.setPreCond(request.getPreCondList());
      // 针对消灭错题任务单独处理
      if ("have_wrongques".equals(request.getPreCondList())) {
        model.setThreshold("5");
      } else {
        model.setThreshold(missionModel.getThreshold());
      }
      missionModel.setPreCondList(FastJsonUtil.toJson(model));
    }
    this.addMission(missionModel);
    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
    return response;
  }

  /**
   * 编辑动态任务
   * 
   * @param request
   * @return
   */
  public Boolean editDyMission(MissionRequest request) {
    MissionModel missionModel = new MissionModel();
    missionModel.setId(request.getId());
    missionModel.setCourseId(request.getCourseId());
    missionModel.setCondition(request.getCondition());
    missionModel.setCondType(request.getCondType());
    if (request.getJumpType() != null && !request.getJumpType().equals("0")) {
      missionModel.setJumpType(request.getJumpType());
    }
    missionModel.setMissionDesc(request.getMissionDesc());
    missionModel.setMissionName(request.getMissionName());
    missionModel.setMissionOrder(request.getMissionOrder());
    missionModel.setMissionPicUrl(request.getMissionPicUrl());
    missionModel.setMissionState(request.getMissionState());
    missionModel.setMissionType(request.getMissionType());
    missionModel.setCreditUpper(request.getCreditUpper());
    missionModel.setScope(request.getScope());
    missionModel.setThreshold(request.getThreshold());
    if (request.getPreCondList() != null && !request.getPreCondList().equals("0")) {
      MissionPreConditionModel model = new MissionPreConditionModel();
      model.setPreCond(request.getPreCondList());
      // 针对消灭错题任务单独处理
      if ("have_wrongques".equals(request.getPreCondList())) {
        model.setThreshold("5");
      } else {
        model.setThreshold(missionModel.getThreshold());
      }
      missionModel.setPreCondList(FastJsonUtil.toJson(model));
    }
    return this.editMission(missionModel);
  }

  /**
   * 自动增加动态任务接口
   * 
   * @param request
   * @return
   */
  public ResourceCourseCreateResponse addDynamicMissionByCourse(MissionRequest request) {
    MissionModel missionModel = new MissionModel();
    missionModel.setId(UUID.randomUUID().toString());
    missionModel.setCourseId(request.getCourseId());
    missionModel.setChapterId("-1");
    missionModel.setCondType(request.getCondType());
    missionModel.setCreditUpper(20);
    missionModel.setExpiryDate(180);
    missionModel.setMissionOrder(Integer.parseInt((request.getCourseId())));
    missionModel.setMissionPicUrl("");
    missionModel.setMissionType("task");
    missionModel.setModule(request.getModule());
    missionModel.setScope("EQ");
    missionModel.setTaskType("dynamic");
    missionModel.setThreshold(request.getCourseId());
    missionModel.setMissionState((short) (1));
    if (request.getCondType().equals("randomPk")) {
      missionModel.setJumpType("randomPk");
      missionModel.setMissionDesc("完成一次PK并获胜");
      missionModel.setMissionName("发起一次随机PK挑战并取得胜利");
      missionModel.setCondition("complete_random_pk");
      MissionPreConditionModel model = new MissionPreConditionModel();
      model.setPreCond("complete_pre_course");
      model.setThreshold(request.getCourseId());
      missionModel.setPreCondList(FastJsonUtil.toJson(model));
      // missionModel.setRedBonusType("Random_pk_win");
    } else if (request.getCondType().equals("wrongques")) {
      missionModel.setJumpType("wrongQues");
      missionModel.setMissionDesc("消灭错题集");
      missionModel.setMissionName("消灭课程全部错题");
      MissionPreConditionModel model = new MissionPreConditionModel();
      model.setPreCond("have_wrongques");
      model.setThreshold("5");
      missionModel.setPreCondList(FastJsonUtil.toJson(model));
      missionModel.setCondition("wrongques_solve_all");
    } else if (request.getCondType().equals("dailyPractice")) {
      missionModel.setJumpType("dailyPractice");
      missionModel.setMissionDesc("每日一练");
      missionModel.setMissionName("每日一练");
      MissionPreConditionModel model = new MissionPreConditionModel();
      model.setPreCond("every_daily_practice");
      model.setThreshold("1");
      missionModel.setPreCondList(FastJsonUtil.toJson(model));
      missionModel.setCondition("daily_practice_complete");
    }else if (request.getCondType().equals("leakFilling")) {
      missionModel.setJumpType("leakFilling");
      missionModel.setMissionDesc("查漏补缺");
      missionModel.setMissionName("查漏补缺");
      MissionPreConditionModel model = new MissionPreConditionModel();
      model.setPreCond("complete_pre_course");
      model.setThreshold(request.getCourseId());
      missionModel.setPreCondList(FastJsonUtil.toJson(model));
      missionModel.setCondition("leak_filling_complete");
    } else {
      return null;
    }
    this.addMission(missionModel);
    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
    return response;
  }

  /**
   * 动态任务删除
   * 
   * @param courseId课程Id
   * @return
   */
  public Boolean deleteDyMission(String courseId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("courseId", courseId);
    return missionDao.deleteEntity(cond);
  }

  /**
   * 动态任务查询
   * 
   * @param courseId课程Id conditionType
   * @return
   */
  public List<MissionModel> findDyMissionById(MissionRequest request) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("courseId", request.getCourseId());
    cond.put("condType", request.getCondType());
    cond.put("condition", request.getCondition());
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    Page<MissionModel> mission = missionDao.queryListByCondWithOutPage(cond, output);
    return mission.getResult();
  }

  /**
   * 动态任务查询
   * 
   * @param courseId课程Id conditionType
   * @return
   */
  public List<MissionModel> findStMissionById(String id) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("condType", "tollgate");
    cond.put("threshold", id);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    Page<MissionModel> mission = missionDao.queryListByCondWithOutPage(cond, output);
    return mission.getResult();
  }
  
  /**
   * 新增标准任务
   * 
   * @param request
   * @return
   */
  public ResourceCourseCreateResponse addStandardMission(MissionVo mission) {
    MissionModel missionModel = new MissionModel();
    missionModel.setId(UUID.randomUUID().toString());
    missionModel.setCondition("tollgate_complete_designated");
    missionModel.setCondType("tollgate");
    missionModel.setCreditUpper(20);
    missionModel.setExpiryDate(180);
    missionModel.setJumpType("tollgate");
    missionModel.setMissionDesc(mission.getDesc());
    missionModel.setMissionName("指定关卡");
    missionModel.setMissionOrder(mission.getListOrder());
    missionModel.setMissionPicUrl("");
    missionModel.setMissionState((short) 1);
    missionModel.setMissionType("task");
    missionModel.setThreshold(mission.getThreshold());
    missionModel.setChapterId(mission.getChapterId());
    missionModel.setCourseId(mission.getCourseId());
    missionModel.setModule(mission.getModule());// 地域
    missionModel.setRedBonusType("Tollgate_complete_item");
    missionModel.setScope("EQ");
    missionModel.setTaskType("standard");
    this.addMission(missionModel);
    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
    return response;
  }

  /**
   * 标准任务删除
   * 
   * @param id
   * @return
   */
  public Boolean deleteStandardMission(String id) {
    IQueryParam param = new QueryParam();
    param.addInput("threshold", id);
    param.addInput("taskType", "standard");
    param.addOutput("id", StringUtils.EMPTY);
    Page<MissionModel> missionPage = missionDao.findEntityListByCond(param, null);
    if (null != missionPage && null != missionPage.getResult()
        && missionPage.getResult().size() > 0) {
      deleteUserMissionRecord(missionPage.getResult());
    }
    Map<String, Object> cond = new HashMap<>();
    cond.put("threshold", id);
    cond.put("taskType", "standard");
    return missionDao.deleteEntity(cond);
  }

  private void deleteUserMissionRecord(List<MissionModel> missionList) {
    if (null == missionList || missionList.size() == 0) return;
    List<String> missionIdList = Lists.newArrayList();
    for (MissionModel mission : missionList) {
      if (null == mission || StringUtils.isBlank(mission.getId())) continue;
      missionIdList.add(mission.getId());
    }
    if (missionIdList.size() == 0) return;
    Map<String, Object> cond = new HashMap<>();
    cond.put("missionIdList", missionIdList);
    userMissionRecordDao.deleteEntity(cond);
  }

  /**
   * 删除一组标准任务
   * 
   * @param idList
   * @return
   */
  public Boolean deleteStandardMission(List<Long> idList) {
    if (null == idList || idList.size() == 0) return true;
    IQueryParam param = new QueryParam();
    param.addInput("thresholdList", idList);
    param.addInput("taskType", "standard");
    param.addOutput("id", StringUtils.EMPTY);
    Page<MissionModel> missionPage = missionDao.findEntityListByCond(param, null);
    if (null != missionPage && null != missionPage.getResult()
        && missionPage.getResult().size() > 0) {
      deleteUserMissionRecord(missionPage.getResult());
    }
    Map<String, Object> cond = new HashMap<>();
    cond.put("thresholdList", idList);
    cond.put("taskType", "standard");
    return missionDao.deleteEntity(cond);
  }

  /**
   * 改变标准任务任务状态
   * 
   * @param productModel
   * @return
   */
  public Boolean editMissionState(MissionModel missionModel) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("threshold", missionModel.getThreshold());
    cond.put("taskType", "standard");
    return missionDao.updateEntity(cond, missionModel);
  }

  @SuppressWarnings({})
  public List<MissionModel> queryMissionByProductId(String type, String courseId) {
    Map<String, Object> cond = new HashMap<>();
    if (StringUtils.isNotBlank(type)) {
      cond.put("taskType", type);
    }
    if (StringUtils.isNotBlank(courseId)) {
      cond.put("courseId", courseId);
    }

    Page<MissionModel> missionModelPage =
        missionDao.queryListByCondWithOutPage(cond, CommUtil.getAllField(MissionModel.class));
    return missionModelPage.getResult();
  }

  public Page<MissionModel> search4Name(String courseName) {
    Map<String, Object> cond = new HashMap<>();
    if (StringUtils.isNotBlank(courseName)) {
      cond.put("courseNameLike", courseName);
    }
    Map<String, Object> outMap = new HashMap<>();
    outMap.put("id", "");
    outMap.put("courseName", "");
    Page<MissionModel> courseNameList = missionDao.queryListByCondWithOutPage(cond, outMap);
    if (null == courseNameList) return null;
    return courseNameList;
  }

  @SuppressWarnings({})
  public List<MissionModel> queryMissionByModule(String module) {
    Map<String, Object> cond = new HashMap<>();
    if (StringUtils.isNotBlank(module)) {
      cond.put("module", module);
    }
    cond.put("courseId", "-1");
    Page<MissionModel> missionModelPage =
        missionDao.queryListByCondWithOutPage(cond, CommUtil.getAllField(MissionModel.class));
    return missionModelPage.getResult();
  }


}
