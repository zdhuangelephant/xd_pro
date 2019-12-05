package com.xiaodou.st.dataclean.service.facade;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.st.dataclean.constant.Constant;
import com.xiaodou.st.dataclean.dao.dashboard.ApplyDao;
import com.xiaodou.st.dataclean.dao.dashboard.CateGoryUnitProductAvgScoreDao;
import com.xiaodou.st.dataclean.dao.dashboard.ChiefUnitRelationDao;
import com.xiaodou.st.dataclean.dao.dashboard.DaysLearnTimeDao;
import com.xiaodou.st.dataclean.dao.dashboard.StudentDao;
import com.xiaodou.st.dataclean.dao.dashboard.UnitDao;
import com.xiaodou.st.dataclean.dao.dashboard.alarm.AlarmRecordDao;
import com.xiaodou.st.dataclean.dao.dashboard.everyday.EverydaySummaryDao;
import com.xiaodou.st.dataclean.dao.dashboard.everyday.EverydaySummaryDetailDao;
import com.xiaodou.st.dataclean.dao.dashboard.last_week.LastWeekLearnTimeDao;
import com.xiaodou.st.dataclean.dao.dashboard.last_week.LastWeekMissionPercentDao;
import com.xiaodou.st.dataclean.dao.dashboard.learn_percent.CategroyUnitSessionLearnPercentDao;
import com.xiaodou.st.dataclean.dao.dashboard.learn_percent.SessionLearnPercentDao;
import com.xiaodou.st.dataclean.dao.dashboard.score.LearnRecordDao;
import com.xiaodou.st.dataclean.dao.dashboard.score.ScoreDao;
import com.xiaodou.st.dataclean.dao.dashboard.session_percent.CategorySessionPercentDao;
import com.xiaodou.st.dataclean.dao.dashboard.session_percent.CategoryUnitSessionPercentDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataExamTotalDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataFaceRecognitionDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataFinishAllMissionDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataFinishMissionDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataLearnRecordDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataProductCategoryDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataProductDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataProductRelationDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataProductScorePointRuleDao;
import com.xiaodou.st.dataclean.dao.raw.RawDataUserScorePointRecordDao;
import com.xiaodou.st.dataclean.dao.staticinfo.StaticInfoDao;
import com.xiaodou.st.dataclean.enums.RoleTypeEnum;
import com.xiaodou.st.dataclean.model.domain.dashboard.ApplyModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.CateGoryUnitProductAvgScoreModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.ChiefUnitRelationModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.DaysLearnTimeModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.StudentModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.Unit;
import com.xiaodou.st.dataclean.model.domain.dashboard.alarm.AlarmRecordModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.everyday.EverydaySummaryDetailVO;
import com.xiaodou.st.dataclean.model.domain.dashboard.everyday.EverydaySummaryVO;
import com.xiaodou.st.dataclean.model.domain.dashboard.lastweek.LastWeekLearnTimeModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.lastweek.LastWeekMissionPercentModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.learnpercent.CategroyUnitSessionLearnPercentModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.learnpercent.SessionLearnPercentModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.score.LearnRecordDO;
import com.xiaodou.st.dataclean.model.domain.dashboard.score.ScoreDO;
import com.xiaodou.st.dataclean.model.domain.dashboard.sessionpercent.CategorySessionPercentModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.sessionpercent.CategoryUnitSessionPercentModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFaceRecognitionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishAllMissionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataProductCategoryModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataProductModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataProductRelationModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataProductScorePointRule;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataUserScorePointRecord;
import com.xiaodou.st.dataclean.model.domain.staticinfo.StaticInfoDO;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.DeleteParam;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.IUpdateParam;
import com.xiaodou.summer.dao.param.UpdateParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("dashBoardServiceFacade")
public class DashBoardServiceFacadeImpl implements DashBoardServiceFacade {
  /** 原始数据 */
  @Resource
  RawDataExamTotalDao rawDataExamTotalDao;
  @Resource
  RawDataLearnRecordDao rawDataLearnRecordDao;
  @Resource
  RawDataFaceRecognitionDao rawDataFaceRecognitionDao;
  @Resource
  RawDataProductDao rawDataProductDao;
  @Resource
  RawDataProductCategoryDao rawDataProductCategoryDao;
  @Resource
  RawDataProductRelationDao rawDataProductRelationDao;
  @Resource
  RawDataFinishAllMissionDao rawDataFinishAllMissionDao;
  @Resource
  RawDataFinishMissionDao rawDataFinishMissionDao;
  @Resource
  RawDataProductScorePointRuleDao rawDataProductScorePointRuleDao;
  @Resource
  RawDataUserScorePointRecordDao rawDataUserScorePointRecordDao;


  /** 展示数据 */
  @Resource
  UnitDao unitDao;
  @Resource
  DaysLearnTimeDao daysLearnTimeDao;
  @Resource
  StudentDao studentDao;
  @Resource
  SessionLearnPercentDao sessionLearnPercentDao;
  @Resource
  CategroyUnitSessionLearnPercentDao categroyUnitSessionLearnPercentDao;
  @Resource
  ApplyDao applyDao;
  @Resource
  ChiefUnitRelationDao chiefUnitRelationDao;
  @Resource
  CategorySessionPercentDao categorySessionPercentDao;
  @Resource
  CategoryUnitSessionPercentDao categoryUnitSessionPercentDao;
  @Resource
  CateGoryUnitProductAvgScoreDao cateGoryUnitProductAvgScoreDao;
  @Resource
  AlarmRecordDao alarmRecordDao;
  @Resource
  LastWeekLearnTimeDao lastWeekLearnTimeDao;
  @Resource
  LastWeekMissionPercentDao lastWeekMissionPercentDao;
  @Resource
  LearnRecordDao learnRecordDao;
  @Resource
  ScoreDao scoreDao;

  @Resource
  EverydaySummaryDao everydaySummaryDao;
  @Resource
  EverydaySummaryDetailDao everydaySummaryDetailDao;

  @Resource
  StaticInfoDao staticInfoDao;

  @Override
  public RawDataExamTotalModel insertRawDataExamTotalModel(RawDataExamTotalModel model) {
    return rawDataExamTotalDao.addEntity(model);
  }

  @Override
  public RawDataFaceRecognitionModel insertRawDataFaceRecognitionModel(
      RawDataFaceRecognitionModel model) {
    return rawDataFaceRecognitionDao.addEntity(model);
  }

  @Override
  public RawDataLearnRecordModel insertRawDataLearnRecordModel(RawDataLearnRecordModel model) {
    return rawDataLearnRecordDao.addEntity(model);
  }

  @Override
  public RawDataProductModel insertRawDataProduct(RawDataProductModel model) {
    return rawDataProductDao.addEntity(model);
  }

  @Override
  public RawDataProductCategoryModel insertRawDataProductCategory(RawDataProductCategoryModel model) {
    return rawDataProductCategoryDao.addEntity(model);
  }

  @Override
  public RawDataProductRelationModel insertRawDataProductRelation(RawDataProductRelationModel model) {
    return rawDataProductRelationDao.addEntity(model);
  }

  @Override
  public RawDataFinishMissionModel insertRawDataFinishMission(RawDataFinishMissionModel model) {
    return rawDataFinishMissionDao.addEntity(model);
  }

  @Override
  public RawDataFinishAllMissionModel insertRawDataFinishAllMission(
      RawDataFinishAllMissionModel model) {
    return rawDataFinishAllMissionDao.addEntity(model);
  }

  @Override
  public void deleteTodayRawDataFinishMission(RawDataFinishMissionModel missionModel) {
    Map<String, Object> cond = Maps.newHashMap();
    CommUtil.transferFromVO2Map(cond, missionModel);
    IDeleteParam param = new DeleteParam();
    param.addInputs(cond);
    rawDataFinishMissionDao.deleteEntityByCond(param);
  }

  @Override
  public Boolean updateRawDataExamTotalModel(RawDataExamTotalModel model) {
    return rawDataExamTotalDao.updateEntityById(model);
  }

  @Override
  public RawDataExamTotalModel queryRawDataExamTotalModel(String userId, String module,
      String productId) {
    Assert.isTrue(StringUtils.isOrNotBlank(userId, module, productId),
        "userId module productId can't be null.");
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("module", module);
    param.addInput("productId", productId);
    param.addOutputs(CommUtil.getAllField(RawDataExamTotalModel.class));
    Page<RawDataExamTotalModel> examTotalPage =
        rawDataExamTotalDao.findEntityListByCond(param, null);
    if (null == examTotalPage || examTotalPage.getResult() == null
        || examTotalPage.getResult().size() == 0) return null;
    return examTotalPage.getResult().get(0);
  }

  @Override
  public RawDataFinishMissionModel queryRawDataFinishMissionModel(String userId, String module,
      String productCategoryId, String productId, String recordTime) {
    Assert.isTrue(StringUtils.isOrNotBlank(userId, module, productCategoryId, productId),
        "userId module productCategoryId productId recordTime can't be null.");
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("module", module);
    param.addInput("productCategoryId", productCategoryId);
    param.addInput("productId", productId);
    param.addInput("recordTime", recordTime);
    param.addOutputs(CommUtil.getAllField(RawDataFinishMissionModel.class));
    Page<RawDataFinishMissionModel> finishMissionPage =
        rawDataFinishMissionDao.findEntityListByCond(param, null);
    if (null == finishMissionPage || finishMissionPage.getResult() == null
        || finishMissionPage.getResult().size() == 0) return null;
    return finishMissionPage.getResult().get(0);
  }

  @Override
  public RawDataFinishAllMissionModel queryRawDataFinishAllMissionModel(String userId,
      String module, String productCategoryId, String productId) {
    Assert.isTrue(StringUtils.isOrNotBlank(userId, module, productCategoryId, productId),
        "userId module productCategoryId productId can't be null.");
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("module", module);
    param.addInput("productCategoryId", productCategoryId);
    param.addInput("productId", productId);
    param.addOutputs(CommUtil.getAllField(RawDataFinishAllMissionModel.class));
    Page<RawDataFinishAllMissionModel> finishAllMissionPage =
        rawDataFinishAllMissionDao.findEntityListByCond(param, null);
    if (null == finishAllMissionPage || finishAllMissionPage.getResult() == null
        || finishAllMissionPage.getResult().size() == 0) return null;
    return finishAllMissionPage.getResult().get(0);
  }

  @Override
  public Page<RawDataFinishAllMissionModel> queryAllRawDataFinishAllMissionModel() {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(RawDataFinishAllMissionModel.class));
    return rawDataFinishAllMissionDao.findEntityListByCond(param, null);
  }

  @Override
  public Unit queryTaughtUnit() {
    Page<Unit> unitPage = queryDesignedRoleUnit(RoleTypeEnum.RoleTypeEnum_Taught_Unit);
    if (null != unitPage && null != unitPage.getResult() && unitPage.getResult().size() > 0)
      return unitPage.getResult().get(0);
    return null;
  }

  @Override
  public Page<Unit> queryChiefUnit() {
    return queryDesignedRoleUnit(RoleTypeEnum.RoleTypeEnum_Chief_Unit);
  }

  @Override
  public Page<Unit> queryPilotUnit() {
    return queryDesignedRoleUnit(RoleTypeEnum.RoleTypeEnum_Pilot_Unit);
  }

  @Override
  public Unit queryUnitById(Integer id) {
    Unit unit = new Unit();
    unit.setId(id);
    return unitDao.findEntityById(unit);
  }

  private Page<Unit> queryDesignedRoleUnit(RoleTypeEnum roleType) {
    IQueryParam param = new QueryParam();
    param.addInput("role", roleType.getCode());
    param.addOutputs(CommUtil.getAllField(Unit.class));
    return unitDao.findEntityListByCond(param, null);
  }

  @Override
  public List<DaysLearnTimeModel> getDaysLearnTimeModelByCond(Map<String, Object> inputArgument) {
    IQueryParam param = new QueryParam();
    param.addInputs(inputArgument);
    param.addOutputs(CommUtil.getAllField(DaysLearnTimeModel.class));
    return daysLearnTimeDao.findEntityListByCond(param, null).getResult();
  }

  @Override
  public DaysLearnTimeModel addDaysLearnTime(DaysLearnTimeModel entity) {
    return daysLearnTimeDao.addEntity(entity);
  }

  @Override
  public boolean editDaysLearnTime(Map<String, Object> inputArgument, DaysLearnTimeModel entity) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(inputArgument);
    param.addValues(entity);
    return daysLearnTimeDao.updateEntityByCond(param);
  }

  @Override
  public StudentModel getStudentByUserId(String userId) {
    Map<String, Object> condMap = Maps.newHashMap();
    condMap.put("userId", userId);
    List<StudentModel> studentList = getStudentModelByCond(condMap);
    if (null != studentList && studentList.size() > 0) return studentList.get(0);
    return null;
  }

  @Override
  public List<StudentModel> getStudentModelByCond(Map<String, Object> inputArgument) {
    IQueryParam param = new QueryParam();
    param.addInputs(inputArgument);
    param.addOutputs(CommUtil.getAllField(StudentModel.class));
    return studentDao.findEntityListByCond(param, null).getResult();
  }

  @Override
  public List<SessionLearnPercentModel> getSessionLearnPercentModelByCond(
      Map<String, Object> inputArgument) {
    IQueryParam param = new QueryParam();
    param.addInputs(inputArgument);
    param.addOutputs(CommUtil.getAllField(SessionLearnPercentModel.class));
    return sessionLearnPercentDao.findEntityListByCond(param, null).getResult();
  }

  @Override
  public Double getAvgSessionLearnPercentModelByCond(Map<String, Object> inputArgument) {
    return sessionLearnPercentDao.getAvgSessionMissionPercentModelByCond(inputArgument);
  }

  @Override
  public SessionLearnPercentModel addSessionLearnPercent(SessionLearnPercentModel entity) {
    return sessionLearnPercentDao.addEntity(entity);
  }

  @Override
  public boolean editSessionLearnPercent(Map<String, Object> cond, SessionLearnPercentModel entity) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(entity);
    return sessionLearnPercentDao.updateEntityByCond(param);
  }

  @Override
  public Integer queryStudentCountByCond(Map<String, Object> inputArgument) {
    return studentDao.queryStudentCountByCond(inputArgument);
  }

  @Override
  public Integer queryDaysLearnTimeCount(Map<String, Object> inputArgument) {
    return daysLearnTimeDao.queryDaysLearnTimeCount(inputArgument);
  }

  @Override
  public List<CategroyUnitSessionLearnPercentModel> getCategroySessionLearnPercentModelByCond(
      Map<String, Object> input2) {
    IQueryParam param = new QueryParam();
    param.addInputs(input2);
    param.addOutputs(CommUtil.getAllField(CategroyUnitSessionLearnPercentModel.class));
    return categroyUnitSessionLearnPercentDao.findEntityListByCond(param, null).getResult();
  }

  @Override
  public Integer queryStudentCountByApply(Map<String, Object> cond) {
    return applyDao.queryStudentCountByApply(cond);
  }

  @Override
  public ApplyModel queryStudentApplyCatInfo(Integer studentId, Integer catId, Integer productId) {
    IQueryParam param = new QueryParam();
    param.addInput("studentId", studentId);
    param.addInput("catId", catId);
    param.addInput("productId", productId);
    param.addOutputs(CommUtil.getAllField(ApplyModel.class));
    Page<ApplyModel> applyPage = applyDao.findEntityListByCond(param, null);
    if (null == applyPage || null == applyPage.getResult() || applyPage.getResult().size() == 0)
      return null;
    return applyPage.getResult().get(0);
  }

  @Override
  public Boolean updateStudentFaceByUserId(String userId, String faceUrl, String clientType) {
    StudentModel student = new StudentModel();
    student.setSourcePortrait(faceUrl);
    // 解决BUG 1601: 与源头像保持一致
    student.setPortrait(faceUrl);
    student.setCollectWay(Constant.COLLECT_WAY_BYUSER);
    student.setUploadTime(new Timestamp(System.currentTimeMillis()));
    student.setUploadDevice(clientType);
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userId);
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(student);
    return studentDao.updateEntityByCond(param);
  }

  @Override
  public List<Integer> queryPilotUnitIdByCatId(List<Integer> catIdList) {
    return applyDao.queryPilotUnitIdByCatId(catIdList);
  }

  @Override
  public List<Integer> queryCatIdByPilotUnitId(List<Integer> pilotUnitIdList) {
    return applyDao.queryCatIdByPilotUnitId(pilotUnitIdList);
  }

  @Override
  public Integer queryRawLearnTimeCountByCond(Map<String, Object> cond) {
    return rawDataLearnRecordDao.queryLearnTimeCountByCond(cond);
  }

  @Override
  public CategroyUnitSessionLearnPercentModel addCategroySessionLearnPercent(
      CategroyUnitSessionLearnPercentModel entity) {
    return categroyUnitSessionLearnPercentDao.addEntity(entity);
  }

  @Override
  public boolean editCategroySessionLearnPercent(Map<String, Object> cond,
      CategroyUnitSessionLearnPercentModel entity) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(entity);
    return categroyUnitSessionLearnPercentDao.updateEntityByCond(param);
  }

  @Override
  public List<ChiefUnitRelationModel> getUnitByChief(Integer id) {
    Map<String, Object> input = new HashMap<>();
    input.put("unitId", id);
    IQueryParam param = new QueryParam();
    param.addInputs(input);
    param.addOutputs(CommUtil.getAllField(ChiefUnitRelationModel.class));
    return chiefUnitRelationDao.findEntityListByCond(param, null).getResult();
  }

  @Override
  public ChiefUnitRelationModel getChiefByCatId(Integer catId) {
    IQueryParam param = new QueryParam();
    param.addInput("catId", catId);
    param.addOutputs(CommUtil.getAllField(ChiefUnitRelationModel.class));
    Page<ChiefUnitRelationModel> chiefPage = chiefUnitRelationDao.findEntityListByCond(param, null);
    if (null != chiefPage && chiefPage.getResult() != null && chiefPage.getResult().size() > 0)
      return chiefPage.getResult().get(0);
    return null;
  }

  @Override
  public CategorySessionPercentModel addCategorySessionPercent(CategorySessionPercentModel entity) {
    return categorySessionPercentDao.addEntity(entity);
  }

  @Override
  public List<CategorySessionPercentModel> getCategorySessionPercentModelByCond(
      Map<String, Object> inputArgument) {
    IQueryParam param = new QueryParam();
    param.addInputs(inputArgument);
    param.addOutputs(CommUtil.getAllField(CategorySessionPercentModel.class));
    return categorySessionPercentDao.findEntityListByCond(param, null).getResult();
  }

  @Override
  public Double querySessionLearnTimeByCond(Map<String, Object> inputArgument) {
    return sessionLearnPercentDao.querySessionLearnTimeByCond(inputArgument);
  }

  @Override
  public Double queryCateGorySessionLearnTimeByCond(Map<String, Object> inputArgument) {
    return categroyUnitSessionLearnPercentDao.queryCateGorySessionLearnTimeByCond(inputArgument);
  }

  @Override
  public Double queryAvgCateGorySessionLearnTimeByCond(Map<String, Object> inputArgument) {
    return categroyUnitSessionLearnPercentDao.queryAvgCateGorySessionLearnTimeByCond(inputArgument);
  }

  @Override
  public RawDataProductModel getRawDataProductById(Long id) {
    RawDataProductModel product = new RawDataProductModel();
    product.setId(id);
    return rawDataProductDao.findEntityById(product);
  }

  @Override
  public RawDataProductCategoryModel getRawDataProductCategoryById(Integer id) {
    RawDataProductCategoryModel model = new RawDataProductCategoryModel();
    model.setId(id);
    return rawDataProductCategoryDao.findEntityById(model);
  }

  @Override
  public RawDataProductCategoryModel getRawDataProductCategoryByTypeCode(String typeCode) {
    IQueryParam param = new QueryParam();
    param.addInput("typeCode", typeCode);
    param.addOutputs(CommUtil.getAllField(RawDataProductCategoryModel.class));
    Page<RawDataProductCategoryModel> categoryPage =
        rawDataProductCategoryDao.findEntityListByCond(param, null);
    if (null != categoryPage && categoryPage.getResult() != null
        && categoryPage.getResult().size() > 0) return categoryPage.getResult().get(0);
    return null;
  }

  @Override
  public boolean editCategorySessionPercentModel(Map<String, Object> cond,
      CategorySessionPercentModel entity) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(entity);
    return categorySessionPercentDao.updateEntityByCond(param);
  }

  @Override
  public List<CategoryUnitSessionPercentModel> getCategoryUnitSessionPercentModelByCond(
      Map<String, Object> inputArgument) {
    IQueryParam param = new QueryParam();
    param.addInputs(inputArgument);
    param.addOutputs(CommUtil.getAllField(CategoryUnitSessionPercentModel.class));
    return categoryUnitSessionPercentDao.findEntityListByCond(param, null).getResult();
  }

  @Override
  public CategoryUnitSessionPercentModel addCategoryUnitSessionPercentModel(
      CategoryUnitSessionPercentModel entity) {
    return categoryUnitSessionPercentDao.addEntity(entity);
  }

  @Override
  public boolean editCategoryUnitSessionPercentModel(Map<String, Object> cond,
      CategoryUnitSessionPercentModel entity) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(entity);
    return categoryUnitSessionPercentDao.updateEntityByCond(param);
  }

  @Override
  public Integer queryRawStudentCountByCond(Map<String, Object> cond) {
    return rawDataLearnRecordDao.queryStudentCountByCond(cond);
  }

  @Override
  public Page<RawDataExamTotalModel> queryTotalStatistic(IQueryParam param) {
    return rawDataExamTotalDao.queryTotalStatistic(param, null);
  }

  @Override
  public List<RawDataLearnRecordModel> getLaskWeekLearnTimeRank(Map<String, Object> cond) {
    return rawDataLearnRecordDao.getLaskWeekLearnTimeRank(cond);
  }

  @Override
  public Double queryAvgRightPercent(Map<String, Object> cond) {
    return rawDataExamTotalDao.queryAvgRightPercent(cond);
  }

  @Override
  public Double queryAvgScore(Map<String, Object> input4) {
    return rawDataExamTotalDao.queryAvgScore(input4);
  }

  @Override
  public CateGoryUnitProductAvgScoreModel addcateGoryUnitProductAvgScoreDao(
      CateGoryUnitProductAvgScoreModel model) {
    return cateGoryUnitProductAvgScoreDao.addEntity(model);
  }

  @Override
  public List<CateGoryUnitProductAvgScoreModel> getCateGoryUnitProductAvgScoreModelByCond(
      Map<String, Object> cond) {
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(CommUtil.getAllField(CateGoryUnitProductAvgScoreModel.class));
    return cateGoryUnitProductAvgScoreDao.findEntityListByCond(param, null).getResult();
  }

  @Override
  public boolean editCateGoryUnitProductAvgScoreModel(Map<String, Object> cond,
      CateGoryUnitProductAvgScoreModel entity) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(entity);
    return cateGoryUnitProductAvgScoreDao.updateEntityByCond(param);
  }

  @Override
  public Integer getFinishMissionStudentCount(Map<String, Object> input4) {
    return rawDataFinishAllMissionDao.getFinishMissionStudentCount(input4);
  }

  @Override
  public Integer getCurrentFinishMissionStudentCount(Map<String, Object> input4) {
    return rawDataFinishMissionDao.getCurrentFinishMissionStudentCount(input4);
  }

  @Override
  public Double queryAllMissionPercent(Map<String, Object> input4) {
    return categroyUnitSessionLearnPercentDao.queryAllMissionPercent(input4);
  }

  @Override
  public Double queryAvgMissionPercent(Map<String, Object> input4) {
    return categroyUnitSessionLearnPercentDao.queryAvgMissionPercent(input4);
  }

  @Override
  public AlarmRecordModel insertAlarmRecord(AlarmRecordModel model) {
    return alarmRecordDao.addEntity(model);
  }

  @Override
  public Page<RawDataFaceRecognitionModel> queryFaceRecognitionPage(Integer studentId, String testId) {
    IQueryParam param = new QueryParam();
    param.addInput("studentId", studentId);
    param.addInput("testId", testId);
    param.addOutputs(CommUtil.getAllField(RawDataFaceRecognitionModel.class));
    param.addSort("collectTime", Sort.ASC);
    return rawDataFaceRecognitionDao.findEntityListByCond(param, null);
  }

  @Override
  public AlarmRecordModel queryAlarmModel(Integer studentId, String testId) {
    IQueryParam param = new QueryParam();
    param.addInput("studentId", studentId);
    param.addInput("triggerId", testId);
    param.addInput("triggerType", Constant.TRIGGRT_TYPE_FACE_RECOGNITION);
    param.addOutputs(CommUtil.getAllField(AlarmRecordModel.class));
    Page<AlarmRecordModel> alarmRecordPage = alarmRecordDao.findEntityListByCond(param, null);
    if (null == alarmRecordPage || null == alarmRecordPage.getResult()
        || alarmRecordPage.getResult().size() == 0) return null;
    return alarmRecordPage.getResult().get(0);
  }

  @Override
  public List<AlarmRecordModel> queryAlarmModelByCond(Map<String, Object> input) {
    IQueryParam param = new QueryParam();
    param.addInputs(input);
    param.addOutputs(CommUtil.getAllField(AlarmRecordModel.class));
    Page<AlarmRecordModel> alarmRecordPage = alarmRecordDao.findEntityListByCond(param, null);
    if (null == alarmRecordPage || alarmRecordPage.getResult().size() == 0) return null;
    return alarmRecordPage.getResult();
  }

  @Override
  public LastWeekLearnTimeModel insertLastWeekLearnTime(LastWeekLearnTimeModel model) {
    return lastWeekLearnTimeDao.addEntity(model);
  }

  @Override
  public LastWeekMissionPercentModel insertLastWeekMissionPercent(LastWeekMissionPercentModel model) {
    return lastWeekMissionPercentDao.addEntity(model);
  }

  @Override
  public LastWeekMissionPercentModel queryLastWeekMissionPercentByPilotIdAndTaughtId(
      Integer pilotId, Integer unitId) {
    IQueryParam param = new QueryParam();
    param.addInput("pilotUnitId", pilotId);
    param.addInput("unitId", unitId);
    param.addOutputs(CommUtil.getAllField(LastWeekMissionPercentModel.class));
    Page<LastWeekMissionPercentModel> lastWeekMissionPercentPage =
        lastWeekMissionPercentDao.findEntityListByCond(param, null);
    if (null == lastWeekMissionPercentPage || lastWeekMissionPercentPage.getResult() == null
        || lastWeekMissionPercentPage.getResult().size() == 0) return null;
    return lastWeekMissionPercentPage.getResult().get(0);
  }

  @Override
  public boolean deleteLastWeekLearnTimeByRank(Integer unitId) {
    IDeleteParam param = new DeleteParam();
    param.addInput("unitId", unitId);
    return lastWeekLearnTimeDao.deleteEntityByCond(param);
  }

  @Override
  public boolean deleteLastWeekMissionPercent(Integer unitId) {
    IDeleteParam param = new DeleteParam();
    param.addInput("unitId", unitId);
    return lastWeekMissionPercentDao.deleteEntityByCond(param);
  }

  @Override
  public boolean deleteLastWeekMissionPercent(RoleTypeEnum roleType) {
    IDeleteParam param = new DeleteParam();
    param.addInput("roleType", roleType.getCode());
    return lastWeekMissionPercentDao.deleteEntityByCond(param);
  }

  @Override
  public boolean editRawDataProductCategoryModel(Map<String, Object> cond,
      RawDataProductCategoryModel entity) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(entity);
    return rawDataProductCategoryDao.updateEntityByCond(param);
  }

  @Override
  public boolean delRawDataProductCategory(RawDataProductCategoryModel entity) {
    return rawDataProductCategoryDao.deleteEntityById(entity);
  }

  @Override
  public boolean editRawDataProductModel(Map<String, Object> cond, RawDataProductModel entity) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(entity);
    return rawDataProductDao.updateEntityByCond(param);
  }

  @Override
  public boolean delRawDataProductModel(RawDataProductModel entity) {
    return rawDataProductDao.deleteEntityById(entity);
  }

  @Override
  public boolean editRawDataProductRelationModel(Map<String, Object> cond,
      RawDataProductRelationModel entity) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(entity);
    return rawDataProductRelationDao.updateEntityByCond(param);
  }

  @Override
  public boolean delRawDataProductRelationModel(RawDataProductRelationModel entity) {
    return rawDataProductRelationDao.deleteEntityById(entity);
  }

  @Override
  public Boolean savelearnRecord(LearnRecordDO learnRecordDO) {
    return null != learnRecordDao.addEntity(learnRecordDO);
  };

  @Override
  public Boolean saveScore(ScoreDO scoreDO) {
    return null != scoreDao.addEntity(scoreDO);
  }

  @Override
  public Page<ScoreDO> listScore(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return scoreDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Boolean updateScoreByCond(Map<String, Object> inputs, ScoreDO scoreDO) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(inputs);
    param.addValues(scoreDO);
    return scoreDao.updateEntityByCond(param);
  }

  @Override
  public List<RawDataProductCategoryModel> getRawDataProductCategoryByCond(Map<String, Object> input) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(input);
    queryParam.addOutputs(CommUtil.getAllField(RawDataProductCategoryModel.class));
    return rawDataProductCategoryDao.findEntityListByCond(queryParam, null).getResult();
  }


  @Override
  public Double queryAvgLearnActive(Map<String, Object> input4) {
    return categroyUnitSessionLearnPercentDao.queryAvgLearnActive(input4);
  }

  @Override
  public List<RawDataLearnRecordModel> getRawDataLearnRecordModelList(Map<String, Object> cond) {

    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(cond);
    queryParam.addOutputs(CommUtil.getAllField(RawDataLearnRecordModel.class));
    return rawDataLearnRecordDao.findEntityListByCond(queryParam, null).getResult();
  }

  @Override
  public List<RawDataFinishMissionModel> getRawDataFinishMissionModelList(Map<String, Object> cond) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(cond);
    queryParam.addOutputs(CommUtil.getAllField(RawDataFinishMissionModel.class));
    return rawDataFinishMissionDao.findEntityListByCond(queryParam, null).getResult();
  }

  @Override
  public List<RawDataExamTotalModel> getRawDataExamTotalModelList(Map<String, Object> cond) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(cond);
    queryParam.addOutputs(CommUtil.getAllField(RawDataExamTotalModel.class));
    return rawDataExamTotalDao.findEntityListByCond(queryParam, null).getResult();
  }

  @Override
  public StudentModel getStudentBySid(Integer sid) {
    StudentModel entity = new StudentModel();
    entity.setId(sid);
    return studentDao.findEntityById(entity);
  }

  @Override
  public List<ApplyModel> queryApplyBySid(Integer studentId) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("studentId", studentId);
    Page<ApplyModel> page =
        queryApplysByConds(inputs, CommUtil.getAllField(ApplyModel.class), null);
    if (null == page) return null;
    return page.getResult();
  }

  @Override
  public Page<ApplyModel> queryApplysByConds(Map<String, Object> inputs,
      Map<String, Object> outputs, Page<ApplyModel> pg) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return applyDao.findEntityListByCond(queryParam, pg);
  }

  @Override
  public void updateAlarmRecordById(AlarmRecordModel alarmRecordModel) {
    if (null == alarmRecordModel) return;
    alarmRecordDao.updateEntityById(alarmRecordModel);
  }

  @Override
  public List<LearnRecordDO> getStudentForMaxLearnTime(Integer stuId) {
    return learnRecordDao.getStudentForMaxLearnTime(stuId);
  }

  @Override
  public Integer querySubjectsByApply(Map<String, Object> cond) {
    return applyDao.queryStudentCountByApply(cond);
  }

  @Override
  public List<RawDataLearnRecordModel> querySubjectsByLearnRecord(Map<String, Object> input) {
    return rawDataLearnRecordDao.querySubjectsByCond(input);
  }

  @Override
  public List<ApplyModel> querySubjectsByCond(Map<String, Object> cond) {
    return applyDao.querySubjectsByCond(cond, CommUtil.getAllField(ApplyModel.class));
  }

  @Override
  public Page<EverydaySummaryVO> queryEverydaySummaryByCond(HashMap<String, Object> input,
      Map<String, Object> output) {
    IQueryParam param = new QueryParam();
    param.addInputs(input);
    param.addOutputs(output);
    param.addSort("createTime", Sort.DESC);
    return everydaySummaryDao.findEntityListByCond(param, null);
  }

  @Override
  public EverydaySummaryVO insertEverydaySummary(EverydaySummaryVO lsmTaughtUnit) {
    return everydaySummaryDao.addEntity(lsmTaughtUnit);
  }

  @Override
  public Boolean updateEverydaySummaryById(EverydaySummaryVO lsmTaughtUnit) {
    return everydaySummaryDao.updateEntityById(lsmTaughtUnit);
  }

  @Override
  public List<Integer> getStudentForNeverLearnCourse() {
    return applyDao.getStudentForNeverLearnCourse(null);
  }

  @Override
  public StaticInfoDO staticInfo() {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(StaticInfoDO.class));
    Page<StaticInfoDO> page = staticInfoDao.findEntityListByCond(param, null);
    if (null == page || null == page.getResult() || page.getResult().size() == 0) {
      throw new RuntimeException("无法获取当前考期,请联系系统管理员设置常量配置.");
    }
    return page.getResult().get(0);
  }

  @Override
  public Page<EverydaySummaryDetailVO> queryEverydaySummaryDetailByCond(
      HashMap<String, Object> input, Map<String, Object> output) {
    IQueryParam param = new QueryParam();
    param.addInputs(input);
    param.addOutputs(output);
    param.addSort("createTime", Sort.DESC);
    return everydaySummaryDetailDao.findEntityListByCond(param, null);
  }

  @Override
  public void insertEverydaySummaryDetail(EverydaySummaryDetailVO detailVo) {
    everydaySummaryDetailDao.addEntity(detailVo);
  }

  @Override
  public void updateEverydaySummaryDetailById(EverydaySummaryDetailVO detailVo) {
    everydaySummaryDetailDao.updateEntityById(detailVo);
  }

  public List<ApplyModel> getPilotsByChief(Integer catId) {
    IQueryParam param = new QueryParam();
    param.addInput("catId", catId);
    param.addInput("orderStatus", 1);
    param.addInput("applyStatus", 1);
    param.addOutputs(CommUtil.getAllField(ApplyModel.class));
    Page<ApplyModel> page = applyDao.findEntityListByCond(param, null);
    if (null == page) return Collections.emptyList();
    return page.getResult();
  }

  @Override
  public ChiefUnitRelationModel queryCategoryByChiefId(Integer chiefId) {
    IQueryParam param = new QueryParam();
    param.addInput("unitId", chiefId);
    param.addOutputs(CommUtil.getAllField(ChiefUnitRelationModel.class));
    Page<ChiefUnitRelationModel> page = chiefUnitRelationDao.findEntityListByCond(param, null);
    if (null == page || page.getResult().size() == 0) return null;
    return page.getResult().get(0);
  }

  @Override
  public Page<ScoreDO> listScore0(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return scoreDao.findEntityListByCond0(queryParam, null);
  }

  @Override
  public RawDataUserScorePointRecord insertRawDataUserScorePointRecord(
      RawDataUserScorePointRecord uspr) {
    return rawDataUserScorePointRecordDao.addEntity(uspr);
  }

  @Override
  public Boolean updateRawDataUserScorePointRecord(RawDataUserScorePointRecord uspr) {
    return rawDataUserScorePointRecordDao.updateEntityById(uspr);
  }

  @Override
  public RawDataProductScorePointRule insertRawDataProductScorePointRule(
      RawDataProductScorePointRule model) {
    return rawDataProductScorePointRuleDao.addEntity(model);
  }

  @Override
  public Boolean editRawDataProductScorePointRule(Map<String, Object> cond,
      RawDataProductScorePointRule model) {
    IUpdateParam param = new UpdateParam();
    param.addInputs(cond);
    param.addValues(model);
    return rawDataProductScorePointRuleDao.updateEntityByCond(param);
  }

  @Override
  public Boolean delRawDataProductScorePointRule(RawDataProductScorePointRule model) {
    return rawDataProductScorePointRuleDao.deleteEntityById(model);
  }

}
