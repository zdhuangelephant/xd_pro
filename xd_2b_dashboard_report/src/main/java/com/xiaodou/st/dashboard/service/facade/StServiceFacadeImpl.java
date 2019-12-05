package com.xiaodou.st.dashboard.service.facade;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.constants.enums.OrderStatusEnum;
import com.xiaodou.st.dashboard.dao.GroupParam;
import com.xiaodou.st.dashboard.dao.IGroupParam;
import com.xiaodou.st.dashboard.dao.admin.AdminDao;
import com.xiaodou.st.dashboard.dao.admin.PrivilegeDao;
import com.xiaodou.st.dashboard.dao.admin.RoleDao;
import com.xiaodou.st.dashboard.dao.admin.RolePrivilegeDao;
import com.xiaodou.st.dashboard.dao.alarm.AlarmRecordDao;
import com.xiaodou.st.dashboard.dao.alarm.LoginInfoDao;
import com.xiaodou.st.dashboard.dao.alarm.RawDataFaceRecognitionDao;
import com.xiaodou.st.dashboard.dao.apply.ApplyCountDao;
import com.xiaodou.st.dashboard.dao.apply.ApplyDao;
import com.xiaodou.st.dashboard.dao.dashboard.EverydaySummaryDao;
import com.xiaodou.st.dashboard.dao.dashboard.EverydaySummaryDetailDao;
import com.xiaodou.st.dashboard.dao.dashboard.LastWeekLearnTimeDao;
import com.xiaodou.st.dashboard.dao.dashboard.LastWeekMissionPercentDao;
import com.xiaodou.st.dashboard.dao.dashboard.SessionLearnPercentDao;
import com.xiaodou.st.dashboard.dao.finalExam.DashboardFinalExamDao;
import com.xiaodou.st.dashboard.dao.finalExam.RawDataFinalExamDao;
import com.xiaodou.st.dashboard.dao.grade.ClassDao;
import com.xiaodou.st.dashboard.dao.order.OrderDao;
import com.xiaodou.st.dashboard.dao.product.RawDataProductCategoryDao;
import com.xiaodou.st.dashboard.dao.product.RawDataProductDao;
import com.xiaodou.st.dashboard.dao.score.LearnRecordDao;
import com.xiaodou.st.dashboard.dao.score.ScoreDao;
import com.xiaodou.st.dashboard.dao.session.CategorySessionPercentDao;
import com.xiaodou.st.dashboard.dao.session.CategoryUnitSessionLearnPercentDao;
import com.xiaodou.st.dashboard.dao.session.CategoryUnitSessionPercentDao;
import com.xiaodou.st.dashboard.dao.session.CategoryUnitSessionProductAvgScoreDao;
import com.xiaodou.st.dashboard.dao.sms.SmsLogDao;
import com.xiaodou.st.dashboard.dao.staticinfo.LogSyncDao;
import com.xiaodou.st.dashboard.dao.staticinfo.StaticInfoDao;
import com.xiaodou.st.dashboard.dao.staticinfo.SyncAdmissionCardCodeDao;
import com.xiaodou.st.dashboard.dao.staticinfo.SyncApplyDao;
import com.xiaodou.st.dashboard.dao.staticinfo.SyncStudentDao;
import com.xiaodou.st.dashboard.dao.student.StudentBaseUserDao;
import com.xiaodou.st.dashboard.dao.student.StudentDao;
import com.xiaodou.st.dashboard.dao.student.StudentMisDao;
import com.xiaodou.st.dashboard.dao.unit.ChiefUnitRelationDao;
import com.xiaodou.st.dashboard.dao.unit.UnitDao;
import com.xiaodou.st.dashboard.domain.admin.AdminDO;
import com.xiaodou.st.dashboard.domain.admin.PrivilegeDO;
import com.xiaodou.st.dashboard.domain.admin.RoleDO;
import com.xiaodou.st.dashboard.domain.admin.RolePrivilegeDO;
import com.xiaodou.st.dashboard.domain.alarm.AlarmRecordDO;
import com.xiaodou.st.dashboard.domain.alarm.LoginInfoDO;
import com.xiaodou.st.dashboard.domain.alarm.RawDataFaceRecognitionDO;
import com.xiaodou.st.dashboard.domain.apply.ApplyCountDO;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.dashboard.EverydaySummaryDetailVO;
import com.xiaodou.st.dashboard.domain.dashboard.EverydaySummaryVO;
import com.xiaodou.st.dashboard.domain.dashboard.LastWeekLearnTimeDO;
import com.xiaodou.st.dashboard.domain.dashboard.LastWeekMissionPercentDO;
import com.xiaodou.st.dashboard.domain.dashboard.SessionLearnPercentDO;
import com.xiaodou.st.dashboard.domain.finalExam.DashboardFinalExamDO;
import com.xiaodou.st.dashboard.domain.finalExam.RawDataFinalExamDO;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
import com.xiaodou.st.dashboard.domain.order.OrderDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductCategoryDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductDO;
import com.xiaodou.st.dashboard.domain.score.LearnRecordDO;
import com.xiaodou.st.dashboard.domain.score.ScoreDO;
import com.xiaodou.st.dashboard.domain.session.CategorySessionPercentDO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionLearnPercentDO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionPercentDO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionProductAvgScoreDO;
import com.xiaodou.st.dashboard.domain.sms.SmsLogDO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncAdmissionCardCodeDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncApplyDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncLogDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncStudentDO;
import com.xiaodou.st.dashboard.domain.student.StudentBaseUserDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.domain.student.StudentMisDO;
import com.xiaodou.st.dashboard.domain.unit.ChiefUnitRelationDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("stServiceFacade")
public class StServiceFacadeImpl extends BaseDashboardService implements IStServiceFacade {
  @Resource
  ClassDao classDao;
  @Resource
  LastWeekLearnTimeDao lastWeekLearnTimeDao;
  @Resource
  LastWeekMissionPercentDao lastWeekMissionPercentDao;
  @Resource
  SessionLearnPercentDao sessionLearnPercentDao;
  @Resource
  StudentDao studentDao;
  @Resource
  StudentMisDao studentMisDao;
  @Resource
  CategorySessionPercentDao categorySessionPercentDao;
  @Resource
  CategoryUnitSessionLearnPercentDao categoryUnitSessionLearnPercentDao;
  @Resource
  CategoryUnitSessionPercentDao categoryUnitSessionPercentDao;
  @Resource
  CategoryUnitSessionProductAvgScoreDao categoryUnitSessionProductAvgScoreDao;
  @Resource
  ScoreDao scoreDao;
  @Resource
  LearnRecordDao learnRecordDao;
  @Resource
  AlarmRecordDao alarmRecordDao;
  @Resource
  RawDataFaceRecognitionDao rawDataFaceRecognitionDao;
  @Resource
  LoginInfoDao loginInfoDao;
  @Resource
  ApplyDao applyDao;
  @Resource
  ApplyCountDao applyCountDao;
  @Resource
  OrderDao orderDao;
  @Resource
  RawDataProductCategoryDao rawDataProductCategoryDao;
  @Resource
  RawDataProductDao rawDataProductDao;
  @Resource
  UnitDao unitDao;
  @Resource
  ChiefUnitRelationDao chiefUnitRelationDao;
  @Resource
  RoleDao roleDao;
  @Resource
  AdminDao adminDao;
  @Resource
  PrivilegeDao privilegeDao;
  @Resource
  RolePrivilegeDao rolePrivilegeDao;
  @Resource
  DashboardFinalExamDao finalExamDao;
  @Resource
  RawDataFinalExamDao rawDataFinalExamDao;
  @Resource
  StaticInfoDao staticInfoDao;
  @Resource
  LogSyncDao logSyncDao;
  @Resource
  SyncStudentDao syncStudentDao;
  @Resource
  SyncApplyDao syncApplyDao;
  @Resource
  SyncAdmissionCardCodeDao syncAdmissionCardCodeDao;
  @Resource
  SmsLogDao smsLogDao;
  @Resource
  StudentBaseUserDao studentBaseUserDao;
  @Resource
  EverydaySummaryDao everydaySummaryDao;
  @Resource
  EverydaySummaryDetailDao everydaySummaryDetailDao;

  @Resource
  ManageStaticInfoService manageStaticInfoService;

  @Override
  public <T> Page<T> list(IQueryParam queryParam, BaseDao<T> baseDao) {
    return baseDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<ClassDO> listClass(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return classDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<LastWeekLearnTimeDO> listLastWeekLearnTime(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("rank", Sort.ASC);
    return lastWeekLearnTimeDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<LastWeekMissionPercentDO> listLastWeekMissionPercent(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("rank", Sort.ASC);
    return lastWeekMissionPercentDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<SessionLearnPercentDO> listSessionLearnPercent(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("dateTime", Sort.ASC);
    return sessionLearnPercentDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<StudentDO> listStudent(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<StudentDO> page) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("createTime", Sort.DESC);
    queryParam.addSort("className", Sort.ASC);
    queryParam.addSort("realName", Sort.ASC);
    return studentDao.findEntityListByCond(queryParam, page);
  }

  @Override
  public Page<StudentDO> findStudentCountListGByPilotUnit() {
    return studentDao.findStudentCountListGByPilotUnit();
  }

  @Override
  public StudentDO getStudent(Integer studentId) {
    StudentDO sd = new StudentDO();
    sd.setId(studentId);
    return studentDao.findEntityById(sd);
  }

  @Override
  public Page<CategorySessionPercentDO> listCategorySessionPercent(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return categorySessionPercentDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Boolean updateCategorySessionPercent(Map<String, Object> inputs,
      CategorySessionPercentDO categorySessionPercentDO) {
    return categorySessionPercentDao.updateEntityByCond(inputs, categorySessionPercentDO);
  }

  @Override
  public Page<CategoryUnitSessionPercentDO> listCategoryUnitSessionPercent(
      Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return categoryUnitSessionPercentDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Boolean updateCategoryUnitSessionPercent(Map<String, Object> inputs,
      CategoryUnitSessionPercentDO categoryUnitSessionPercentDO) {
    return categoryUnitSessionPercentDao.updateEntityByCond(inputs, categoryUnitSessionPercentDO);
  }

  @Override
  public Page<CategoryUnitSessionLearnPercentDO> listCategoryUnitSessionLearnPercent(
      Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("dateTime", Sort.ASC);
    return categoryUnitSessionLearnPercentDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<CategoryUnitSessionProductAvgScoreDO> listCategoryUnitSessionProductAvgScore(
      Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return categoryUnitSessionProductAvgScoreDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<ScoreDO> listScore(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<ScoreDO> pg) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    // queryParam.addSort("createTime", Sort.DESC);
    queryParam.addSort("admissionCardCode", Sort.ASC);
    queryParam.addSort("productCode", Sort.ASC);
    return scoreDao.findEntityListByCond(queryParam, pg);
  }

  @Override
  public Page<ScoreDO> listScoreNoCommon(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<ScoreDO> pg) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("admissionCardCode", Sort.ASC);
    queryParam.addSort("productCode", Sort.ASC);
    return scoreDao.findEntityListByCond(queryParam, pg);
  }

  @Override
  public ScoreDO getScoreById(Long scoreId) {
    ScoreDO scoreDO = new ScoreDO();
    scoreDO.setId(scoreId);
    return scoreDao.findEntityById(scoreDO);
  }

  @Override
  public Page<LearnRecordDO> listLearnRecord(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("createTime", Sort.DESC);
    queryParam.addSort("recordTime", Sort.DESC);
    queryParam.addSort("studentId", Sort.ASC);
    queryParam.addSort("learnTime", Sort.DESC);
    return learnRecordDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<AlarmRecordDO> listAlarmRecord(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("alarmTime", Sort.DESC);
    return alarmRecordDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<AlarmRecordDO> listAlarmRecord(Map<String, Object> inputs,
      Map<String, Object> outputs, Page<AlarmRecordDO> page) {
    IQueryParam param = super.getCommonQueryParam();
    param.addInputs(inputs);
    param.addOutputs(outputs);
    param.addSort("alarmTime", Sort.DESC);
    return alarmRecordDao.findEntityListByCond(param, page);
  }


  @Override
  public Page<AlarmRecordDO> listAlarmRecordByCond(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("alarmTime", Sort.DESC);
    return alarmRecordDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public AlarmRecordDO getAlarmRecord(Integer alarmId) {
    AlarmRecordDO ado = new AlarmRecordDO();
    ado.setId(alarmId);
    return alarmRecordDao.findEntityById(ado);
  }

  @Override
  public Boolean updateAlarmRecord(AlarmRecordDO alarmRecordDO) {
    return alarmRecordDao.updateEntityById(alarmRecordDO);
  }

  @Override
  public Integer findCountByCond(Map<String, Object> inputs) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    return alarmRecordDao.findCountByCond(queryParam);
  }

  @Override
  public Page<RawDataFaceRecognitionDO> listRawDataFaceRecognition(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("collectTime", Sort.ASC);
    return rawDataFaceRecognitionDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<LoginInfoDO> listLoginInfo(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("loginTime", Sort.DESC);
    return loginInfoDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Boolean saveLoginInfo(LoginInfoDO loginInfo) {
    return null != loginInfoDao.addEntity(loginInfo);
  };

  @Override
  public Boolean saveAlarmRecord(AlarmRecordDO alarmRecordDO) {
    return null != alarmRecordDao.addEntity(alarmRecordDO);
  };

  @Override
  public Boolean saveStudent(StudentDO studentDO) {
    return null != studentDao.addEntity(studentDO);
  }

  @Override
  public Integer saveStudentList(List<StudentDO> value, Map<String, Object> column) {
    return studentDao.addEntityList(value, column);
  }

  @Override
  public Boolean updateStudent(StudentDO studentDO) {
    return studentDao.updateEntityById(studentDO);
  }

  @Override
  public Boolean updateStudentByCond(Map<String, Object> inputs, StudentDO studentDO) {
    return studentDao.updateEntityByCond(inputs, studentDO);
  }

  @Override
  public Integer updateOrAddStudentList(List<StudentDO> value, Map<String, Object> column) {
    return studentDao.updateOrAddEntityList(value, column);
  }

  @Override
  public Boolean removeStudent(Integer studentId) {
    StudentDO studentDO = new StudentDO();
    studentDO.setId(studentId);
    return studentDao.deleteEntityById(studentDO);
  }

  @Override
  public Integer removeStudentByCond(Map<String, Object> input) {
    return studentDao.deleteStudentByCond(input);
  }

  @Override
  public ClassDO getClass(Long classId) {
    ClassDO classDO = new ClassDO();
    classDO.setId(classId);
    return classDao.findEntityById(classDO);
  }

  @Override
  public Boolean saveClass(ClassDO classDO) {
    return null != classDao.addEntity(classDO);
  }

  @Override
  public Boolean updateClass(ClassDO classDO) {
    return classDao.updateEntityById(classDO);
  }

  @Override
  public Boolean removeClass(Long classId) {
    ClassDO classDO = new ClassDO();
    classDO.setId(classId);
    return classDao.deleteEntityById(classDO);
  }

  @Override
  public Page<ApplyDO> listApply(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<ApplyDO> pg) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    // queryParam.addSort("createTime", Sort.DESC);
    queryParam.addSort("admissionCardCode", Sort.ASC);
    queryParam.addSort("productCode", Sort.ASC);
    return applyDao.findEntityListByCond(queryParam, pg);
  }

  @Override
  public Page<ApplyCountDO> groupCatApply(Map<String, Object> inputs, Map<String, Object> outputs,
      List<String> groups) {
    IGroupParam queryParam = new GroupParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addGroups(groups);
    return applyCountDao.groupCatApply(queryParam, null);

  }

  @Override
  public Page<ApplyDO> listApplyAndStudent(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return applyDao.listApplyAndStudent(queryParam, null);
  }

  @Override
  public Boolean saveApply(ApplyDO applyDO) {
    return null != applyDao.addEntity(applyDO);
  }

  @Override
  public Integer saveApplyList(List<ApplyDO> applyDOList) {
    return applyDao.addEntityList(applyDOList);
  }

  @Override
  public Boolean updateApplyByCond(Map<String, Object> inputs, ApplyDO applyDO) {
    return applyDao.updateEntityByCond(inputs, applyDO);
  }

  @Override
  public Integer updateOrAddApplyList(List<ApplyDO> value, Map<String, Object> column) {
    return applyDao.updateOrAddEntityList(value, column);
  }

  @Override
  public Integer removeApplyByCond(Map<String, Object> inputs) {
    return applyDao.deleteApplyByCond(inputs);
  }

  @Override
  public Boolean removeApply(Long applyId) {
    ApplyDO applyDO = new ApplyDO();
    applyDO.setId(applyId);
    return applyDao.deleteEntityById(applyDO);
  }

  @Override
  public Page<OrderDO> listOrder(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("createTime", Sort.DESC);
    return orderDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public OrderDO getOrder(Long orderId) {
    OrderDO od = new OrderDO();
    od.setId(orderId);
    return orderDao.findEntityById(od);
  }

  @Override
  public OrderDO saveOrder(OrderDO orderDO) {
    return orderDao.addEntity(orderDO);
  }

  @Override
  public Boolean updateOrder(OrderDO orderDO) {
    return orderDao.updateEntityById(orderDO);
  }

  @Override
  public Boolean updateOrderByCond(Map<String, Object> inputs, OrderDO orderDO) {
    return orderDao.updateEntityByCond(inputs, orderDO);
  }

  @Override
  public Boolean removeOrder(Long orderId) {
    OrderDO orderDO = new OrderDO();
    orderDO.setId(orderId);
    return orderDao.deleteEntityById(orderDO);
  }

  @Override
  public Page<RawDataProductCategoryDO> listRawDataProductCategory(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return rawDataProductCategoryDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public RawDataProductCategoryDO getRawDataProductCategory(Integer catId) {
    RawDataProductCategoryDO rdo = new RawDataProductCategoryDO();
    rdo.setId(catId);
    return rawDataProductCategoryDao.findEntityById(rdo);
  }

  @Override
  public Page<RawDataProductDO> listRawDataProduct(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return rawDataProductDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<RawDataProductDO> findExamDate() {
    return rawDataProductDao.findExamDate();
  }

  @Override
  public RawDataProductDO getRawDataProductById(Integer productId) {
    RawDataProductDO rpdo = new RawDataProductDO();
    rpdo.setId(productId);
    return rawDataProductDao.findEntityById(rpdo);
  }

  @Override
  public Page<UnitDO> listUnit(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return unitDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public UnitDO getUnit(Long unitId) {
    UnitDO udo = new UnitDO();
    udo.setId(unitId);
    return unitDao.findEntityById(udo);
  }

  @Override
  public Page<ChiefUnitRelationDO> listChiefUnitRelation(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return chiefUnitRelationDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public ChiefUnitRelationDO getChiefUnitByCatId(Integer catId) {
    return chiefUnitRelationDao.findEntityByCatId(catId);
  }

  @Override
  public ChiefUnitRelationDO getChiefUnitByUnitId(Long unitId) {
    return chiefUnitRelationDao.findEntityByUnitId(unitId);
  }

  @Override
  public Boolean saveRelate(ChiefUnitRelationDO curdo) {
    return null != chiefUnitRelationDao.addEntity(curdo);
  }

  @Override
  public Boolean updateRelate(Map<String, Object> inputs, ChiefUnitRelationDO curdo) {
    return chiefUnitRelationDao.updateEntityByCond(inputs, curdo);
  }

  @Override
  public Page<RoleDO> listManageRole(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return roleDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<UnitDO> listManageUnit(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("createTime", Sort.DESC);
    return unitDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<AdminDO> listManageAdmin(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("createTime", Sort.DESC);
    return adminDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public RoleDO getRoleById(Integer role) {
    RoleDO roleDO = new RoleDO();
    roleDO.setId(role);
    return roleDao.findEntityById(roleDO);
  }

  @Override
  public Boolean saveRole(RoleDO roleDO) {
    return null != roleDao.addEntity(roleDO);
  }

  @Override
  public Boolean updateRole(RoleDO roleDO) {
    return roleDao.updateEntityById(roleDO);
  }

  @Override
  public Boolean removeRole(Integer role) {
    RoleDO roleDO = new RoleDO();
    roleDO.setId(role);
    return roleDao.deleteEntityById(roleDO);
  }

  @Override
  public Boolean saveUnit(UnitDO unitDO) {
    return null != unitDao.addEntity(unitDO);
  }

  @Override
  public Boolean updateUnit(UnitDO unitDO) {
    return unitDao.updateEntityById(unitDO);
  }

  @Override
  public Boolean removeUnit(Long unitId) {
    UnitDO unitDO = new UnitDO();
    unitDO.setId(unitId);
    return unitDao.deleteEntityById(unitDO);
  }

  @Override
  public AdminDO getAdmin(Integer adminId) {
    AdminDO adminDO = new AdminDO();
    adminDO.setId(adminId);
    return adminDao.findEntityById(adminDO);
  }

  @Override
  public Boolean saveAdmin(AdminDO adminDO) {
    return null != adminDao.addEntity(adminDO);
  }

  @Override
  public Boolean updateAdmin(AdminDO adminDO) {
    return adminDao.updateEntityById(adminDO);
  }

  @Override
  public Boolean removeAdmin(Integer adminId) {
    AdminDO adminDO = new AdminDO();
    adminDO.setId(adminId);
    return adminDao.deleteEntityById(adminDO);
  }

  @Override
  public Page<PrivilegeDO> listManagePrivilege(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("sortOrder", Sort.ASC);
    return privilegeDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public PrivilegeDO getPrivilegeById(Integer privilegeId) {
    PrivilegeDO privilegeDO = new PrivilegeDO();
    privilegeDO.setId(privilegeId);
    return privilegeDao.findEntityById(privilegeDO);
  }

  @Override
  public Boolean savePrivilege(PrivilegeDO privilegeDO) {
    privilegeDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return null != privilegeDao.addEntity(privilegeDO);
  }

  @Override
  public Boolean updatePrivilegeById(PrivilegeDO privilegeDO) {
    return privilegeDao.updateEntityById(privilegeDO);
  }

  @Override
  public Boolean removePrivilege(Integer privilegeId) {
    PrivilegeDO privilegeDO = new PrivilegeDO();
    privilegeDO.setId(privilegeId);
    return privilegeDao.deleteEntityById(privilegeDO);
  }

  @Override
  public Boolean removePrivilegeByCond(Map<String, Object> inputs) {
    return privilegeDao.deleteEntityByCond(inputs);
  }

  @Override
  public Page<RolePrivilegeDO> listManageRolePrivilege(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("createTime", Sort.DESC);
    return rolePrivilegeDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public RolePrivilegeDO getRolePrivilege(Integer rolePrivilegeId) {
    RolePrivilegeDO rolePrivilegeDO = new RolePrivilegeDO();
    rolePrivilegeDO.setId(rolePrivilegeId);
    return rolePrivilegeDao.findEntityById(rolePrivilegeDO);
  }

  @Override
  public Boolean saveRolePrivilege(RolePrivilegeDO rolePrivilegeDO) {
    rolePrivilegeDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return null != rolePrivilegeDao.addEntity(rolePrivilegeDO);
  }

  @Override
  public Boolean updateRolePrivilege(RolePrivilegeDO rolePrivilegeDO) {
    return rolePrivilegeDao.updateEntityById(rolePrivilegeDO);
  }

  @Override
  public Boolean removeRolePrivilege(Integer rolePrivilegeId) {
    RolePrivilegeDO rolePrivilegeDO = new RolePrivilegeDO();
    rolePrivilegeDO.setId(rolePrivilegeId);
    return rolePrivilegeDao.deleteEntityById(rolePrivilegeDO);
  }

  @Override
  public Boolean removeRolePrivilegeByCond(Map<String, Object> inputs) {
    return rolePrivilegeDao.deleteEntityByCond(inputs);
  }

  @Override
  public Page<DashboardFinalExamDO> listDbFinalExam(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("createTime", Sort.DESC);
    return finalExamDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public DashboardFinalExamDO getDbFinalExamById(Long finalExamId) {
    DashboardFinalExamDO finalExamDO = new DashboardFinalExamDO();
    finalExamDO.setId(finalExamId);
    return finalExamDao.findEntityById(finalExamDO);
  }

  @Override
  public Page<RawDataFinalExamDO> listRdFinalExam(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("order", Sort.ASC);
    return rawDataFinalExamDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public RawDataFinalExamDO getRdFinalExamById(Long finalExamId) {
    RawDataFinalExamDO finalExamDO = new RawDataFinalExamDO();
    finalExamDO.setId(finalExamId);
    return rawDataFinalExamDao.findEntityById(finalExamDO);
  }

  @Override
  public Page<StaticInfoDO> listStaticInfo(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return staticInfoDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Boolean updateStaticInfo(StaticInfoDO staticInfoDO) {
    return staticInfoDao.updateEntityById(staticInfoDO);
  }

  @Override
  public Page<SyncLogDO> listSyncLog(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("syncTime", Sort.DESC);
    return logSyncDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Boolean saveSyncLog(SyncLogDO logSyncDO) {
    logSyncDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return null != logSyncDao.addEntity(logSyncDO);
  }

  @Override
  public Boolean updateSyncLog(SyncLogDO syncLogDO) {
    return logSyncDao.updateEntityById(syncLogDO);
  }

  @Override
  public Page<SyncStudentDO> listSyncStudent(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return syncStudentDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Boolean saveSyncStudent(SyncStudentDO syncStudentDO) {
    syncStudentDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return null != syncStudentDao.addEntity(syncStudentDO);
  }

  @Override
  public Integer saveSyncStudentList(List<SyncStudentDO> value, Map<String, Object> column) {
    return syncStudentDao.addEntityList(value, column);
  }

  @Override
  public Integer saveSyncApplyList(List<SyncApplyDO> value, Map<String, Object> column) {
    return syncApplyDao.addEntityList(value, column);
  }

  @Override
  public Integer saveSyncCardList(List<SyncAdmissionCardCodeDO> value, Map<String, Object> column) {
    return syncAdmissionCardCodeDao.addEntityList(value, column);
  }

  @Override
  public Boolean updateApply(ApplyDO applyDO) {
    return applyDao.updateEntityById(applyDO);
  }

  @Override
  public Boolean saveSyncApply(SyncApplyDO sado) {
    sado.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return syncApplyDao.addEntity(sado) != null;
  }

  @Override
  public Boolean saveSyncCard(SyncAdmissionCardCodeDO sccdo) {
    return syncAdmissionCardCodeDao.addEntity(sccdo) != null;
  }

  @Override
  public Page<SyncApplyDO> listSyncApply(Map<String, Object> inputs, Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return syncApplyDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<SyncAdmissionCardCodeDO> listSyncCard(Map<String, Object> inputs,
      Map<String, Object> outputs) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return syncAdmissionCardCodeDao.findEntityListByCond(queryParam, null);
  }

  @Override
  public Page<SmsLogDO> listSmsLog(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<SmsLogDO> pg) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    return smsLogDao.findEntityListByCond(queryParam, pg);
  }

  // 修改学生手机号码
  @Override
  public Boolean updateStudentTelephone(StudentDO studentDO) {
    StudentBaseUserDO studentBaseUserDO = new StudentBaseUserDO();
    studentBaseUserDO.setModule(Constants.MODULE);
    studentBaseUserDO.setTelephone(studentDO.getTelephone());
    studentBaseUserDO.setNewTelephone(studentDO.getRegTelephone());
    Boolean flag = studentBaseUserDao.updateTelephone(studentBaseUserDO);
    if (flag) {
      return studentDao.updateDashboardStudentTelephone(studentDO);
    }
    return flag;
  }

  // 查询学生头像
  @Override
  public StudentMisDO findUserOfficialInfoByUserId(StudentMisDO studentMisDO) {
    return studentMisDao.findUserOfficialInfoByUserId(studentMisDO);
  }

  // 重置学生头像
  @Override
  public Boolean resetStudentPortrait(StudentDO studentDO) {
    return studentDao.resetDashboardStudentPortrait(studentDO);
  }

  @Override
  public Boolean updateUserOfficialInfoByUserId(StudentMisDO studentMisDO) {
    return studentMisDao.updateUserOfficialInfoByUserId(studentMisDO);
  }

  @Override
  public List<StudentBaseUserDO> findTelByTelephoneAndModule(StudentBaseUserDO studentBaseUserDO) {
    return studentBaseUserDao.findTelByTelephoneAndModule(studentBaseUserDO);
  }

  @Override
  public Boolean updateScoreByDailyScore(ScoreDO vo) {
    return scoreDao.updateEntityById(vo);
  }

  @Override
  public Page<ScoreDO> findPureScoreListByCond(Map<String, Object> inputs,
      Map<String, Object> outputs, Page<ScoreDO> pg) {
    IQueryParam queryParam = new QueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("createTime", Sort.DESC);
    return scoreDao.findPureScoreListByCond(queryParam, pg);
  }

  @Override
  public Page<ScoreDO> findScoreListJoinProduct(Map<String, Object> inputs,
      Map<String, Object> outputs, Page<ScoreDO> pg) {
    IQueryParam queryParam = super.getCommonQueryParam();
    queryParam.addInputs(inputs);
    queryParam.addOutputs(outputs);
    queryParam.addSort("admissionCardCode", Sort.ASC);
    queryParam.addSort("productCode", Sort.ASC);
    return scoreDao.findScoreListJoinProduct(queryParam, pg);
  }

  @Override
  public Boolean saveScore(ScoreDO sdo) {
    return scoreDao.addEntity(sdo) != null;
  }

  @Override
  public Boolean updateScoreByCond(Map<String, Object> inputs, ScoreDO sdo) {
    return scoreDao.updateEntityByCond(inputs, sdo);
  }

  @Override
  public Page<EverydaySummaryVO> getEveryDaySummary(Long unitId, Date date) {
    // step1 判断是否已经过了考期
    StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
    try {
      if (DateUtil.SDF_YMD.parse(staticInfo.getEndApplyTime()).getTime() - date.getTime() <= 0) {
        HashMap<String, Object> input = Maps.newHashMap();
        input.put("updateTimeLower", DateUtil.SDF_YMD.parse(staticInfo.getEndApplyTime()));
        input.put("unitId", unitId);
        IQueryParam param = new QueryParam();
        param.addOutputs(CommUtil.getGeneralField(EverydaySummaryVO.class));
        param.addInputs(input);
        Page<EverydaySummaryVO> page = everydaySummaryDao.findEntityListByCond(param, null);
        return page;
      }
    } catch (Exception e) {}

    HashMap<String, Object> input = Maps.newHashMap();
    input.put("updateTimeLower", DateUtil.SDF_YMD.format(date));
    input.put("unitId", unitId);
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getGeneralField(EverydaySummaryVO.class));
    param.addInputs(input);
    Page<EverydaySummaryVO> page = everydaySummaryDao.findEntityListByCond(param, null);
    if (null != page && page.getResult().size() > 0) return page;

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    input.put("updateTimeLower", DateUtil.SDF_YMD.format(cal.getTime()));
    param.addInputs(input);
    page = everydaySummaryDao.findEntityListByCond(param, null);
    return page;
  }


  @Override
  public Page<EverydaySummaryDetailVO> getEveryDaySummary(List<Long> unitIds, Date date) {
    // step1 判断是否已经过了考期
    StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
    try {
      if (DateUtil.SDF_YMD.parse(staticInfo.getEndApplyTime()).getTime() - date.getTime() <= 0) {
        HashMap<String, Object> input = Maps.newHashMap();
        input.put("updateTimeLower", DateUtil.SDF_YMD.parse(staticInfo.getEndApplyTime()));
        input.put("unitIdList", unitIds);
        IQueryParam param = new QueryParam();
        param.addOutputs(CommUtil.getGeneralField(EverydaySummaryDetailVO.class));
        param.addInputs(input);
        Page<EverydaySummaryDetailVO> page =
            everydaySummaryDetailDao.findEntityListByCond(param, null);
        return page;
      }
    } catch (Exception e) {}

    HashMap<String, Object> input = Maps.newHashMap();
    input.put("updateTimeLower", DateUtil.SDF_YMD.format(date));
    input.put("unitIdList", unitIds);
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getGeneralField(EverydaySummaryDetailVO.class));
    param.addInputs(input);
    Page<EverydaySummaryDetailVO> page = everydaySummaryDetailDao.findEntityListByCond(param, null);
    if (null != page && page.getResult().size() > 0) return page;
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    input.put("updateTimeLower", DateUtil.SDF_YMD.format(cal.getTime()));
    param.addInputs(input);
    page = everydaySummaryDetailDao.findEntityListByCond(param, null);
    return page;
  }

  @Override
  public List<UnitDO> getPilotsByTaught() {
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("role", Constants.POILT_UNIT_ROLE);
    IQueryParam param = new QueryParam();
    param.addInputs(input);
    param.addOutputs(CommUtil.getAllField(UnitDO.class));
    Page<UnitDO> page = unitDao.findEntityListByCond(param, null);
    if (null == page) return Collections.emptyList();
    return page.getResult();
  }

  @Override
  public List<ApplyDO> getPilotsByChief(Integer catId) {
    IQueryParam param = new QueryParam();
    param.addInput("catId", catId);
    param.addInput("orderStatus", OrderStatusEnum.OrderStatus_1.getCode());
    param.addInput("applyStatus", Constants.BUSINESS_APPLY_SUCCESS);
    param.addOutputs(CommUtil.getAllField(ApplyDO.class));
    Page<ApplyDO> page = applyDao.findEntityListByCond(param, null);
    if (null == page) return Collections.emptyList();
    return page.getResult();
  }


  public Page<EverydaySummaryDetailVO> getEveryDaySummary(List<Long> pilotUnitIdList, Long unitId,
      Date date) {
    // step1 判断是否已经过了考期
    StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
    try {
      if (DateUtil.SDF_YMD.parse(staticInfo.getEndApplyTime()).getTime() - date.getTime() <= 0) {
        HashMap<String, Object> input = Maps.newHashMap();
        input.put("updateTimeLower", DateUtil.SDF_YMD.parse(staticInfo.getEndApplyTime()));
        input.put("unitId", unitId);
        input.put("pilotUnitIdList", pilotUnitIdList);

        IQueryParam param = new QueryParam();
        param.addOutputs(CommUtil.getGeneralField(EverydaySummaryDetailVO.class));
        param.addInputs(input);
        Page<EverydaySummaryDetailVO> page =
            everydaySummaryDetailDao.findEntityListByCond(param, null);
        return page;
      }
    } catch (Exception e) {}

    HashMap<String, Object> input = Maps.newHashMap();
    input.put("updateTimeLower", DateUtil.SDF_YMD.format(date));
    input.put("unitId", unitId);
    input.put("pilotUnitIdList", pilotUnitIdList);
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getGeneralField(EverydaySummaryDetailVO.class));
    param.addInputs(input);
    Page<EverydaySummaryDetailVO> page = everydaySummaryDetailDao.findEntityListByCond(param, null);
    if (null != page && page.getResult().size() > 0) return page;

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    input.put("updateTimeLower", DateUtil.SDF_YMD.format(cal.getTime()));
    param.addInputs(input);
    page = everydaySummaryDetailDao.findEntityListByCond(param, null);
    return page;
  }

  @Override
  public StaticInfoDO staticInfo() {
    return manageStaticInfoService.staticInfo();
  }


}
