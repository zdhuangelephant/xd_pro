package com.xiaodou.st.dashboard.service.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

public interface IStServiceFacade {

  <T> Page<T> list(IQueryParam queryParam, BaseDao<T> baseDao);

  /* dashboard */
  Page<LastWeekLearnTimeDO> listLastWeekLearnTime(Map<String, Object> inputs,
      Map<String, Object> outputs);

  Page<LastWeekMissionPercentDO> listLastWeekMissionPercent(Map<String, Object> inputs,
      Map<String, Object> outputs);

  Page<SessionLearnPercentDO> listSessionLearnPercent(Map<String, Object> inputs,
      Map<String, Object> outputs);

  /* session */
  Page<CategorySessionPercentDO> listCategorySessionPercent(Map<String, Object> inputs,
      Map<String, Object> outputs);

  Boolean updateCategorySessionPercent(Map<String, Object> inputs,
      CategorySessionPercentDO categorySessionPercentDO);

  Page<CategoryUnitSessionPercentDO> listCategoryUnitSessionPercent(Map<String, Object> inputs,
      Map<String, Object> outputs);

  Boolean updateCategoryUnitSessionPercent(Map<String, Object> inputs,
      CategoryUnitSessionPercentDO categoryUnitSessionPercentDO);

  Page<CategoryUnitSessionLearnPercentDO> listCategoryUnitSessionLearnPercent(
      Map<String, Object> inputs, Map<String, Object> outputs);

  Page<CategoryUnitSessionProductAvgScoreDO> listCategoryUnitSessionProductAvgScore(
      Map<String, Object> inputs, Map<String, Object> outputs);

  /* score */
  Page<ScoreDO> listScore(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<ScoreDO> pg);

  Page<ScoreDO> listScoreNoCommon(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<ScoreDO> pg);


  ScoreDO getScoreById(Long id);

  Page<LearnRecordDO> listLearnRecord(Map<String, Object> inputs, Map<String, Object> outputs);

  /* alarm */
  Page<AlarmRecordDO> listAlarmRecord(Map<String, Object> inputs, Map<String, Object> outputs);

  Page<AlarmRecordDO> listAlarmRecordByCond(Map<String, Object> inputs,
      Map<String, Object> outputs);

  AlarmRecordDO getAlarmRecord(Integer alarmId);

  Boolean updateAlarmRecord(AlarmRecordDO alarmRecordDO);

  Integer findCountByCond(Map<String, Object> inputs);

  Page<RawDataFaceRecognitionDO> listRawDataFaceRecognition(Map<String, Object> inputs,
      Map<String, Object> outputs);

  Page<LoginInfoDO> listLoginInfo(Map<String, Object> inputs, Map<String, Object> outputs);

  Boolean saveLoginInfo(LoginInfoDO loginInfo);

  Boolean saveAlarmRecord(AlarmRecordDO alarmRecordDO);

  /* student */
  Page<StudentDO> listStudent(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<StudentDO> page);

  Page<StudentDO> findStudentCountListGByPilotUnit();

  StudentDO getStudent(Integer studentId);

  Boolean saveStudent(StudentDO studentDO);

  Integer saveStudentList(List<StudentDO> value, Map<String, Object> column);

  Boolean updateStudent(StudentDO studentDO);

  Boolean updateStudentByCond(Map<String, Object> inputs, StudentDO studentDO);

  Integer updateOrAddStudentList(List<StudentDO> value, Map<String, Object> column);

  Boolean removeStudent(Integer studentId);

  Integer removeStudentByCond(Map<String, Object> input);

  /* class */
  Page<ClassDO> listClass(Map<String, Object> inputs, Map<String, Object> outputs);

  ClassDO getClass(Long classId);

  Boolean saveClass(ClassDO classDO);

  Boolean updateClass(ClassDO classDO);

  Boolean removeClass(Long classId);

  /* apply */
  Page<ApplyDO> listApply(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<ApplyDO> pg);

  Page<ApplyDO> listApplyAndStudent(Map<String, Object> inputs, Map<String, Object> outputs);

  Boolean saveApply(ApplyDO applyDO);

  Integer saveApplyList(List<ApplyDO> applyDOList);

  Boolean updateApplyByCond(Map<String, Object> inputs, ApplyDO applyDO);

  Integer updateOrAddApplyList(List<ApplyDO> value, Map<String, Object> column);

  Integer removeApplyByCond(Map<String, Object> inputs);

  Boolean removeApply(Long applyId);

  /* order */
  Page<OrderDO> listOrder(Map<String, Object> inputs, Map<String, Object> outputs);

  OrderDO getOrder(Long orderId);

  OrderDO saveOrder(OrderDO orderDO);

  Boolean updateOrder(OrderDO orderDO);

  Boolean updateOrderByCond(Map<String, Object> inputs, OrderDO orderDO);

  Boolean removeOrder(Long orderId);

  /* product */
  Page<RawDataProductCategoryDO> listRawDataProductCategory(Map<String, Object> inputs,
      Map<String, Object> outputs);

  RawDataProductCategoryDO getRawDataProductCategory(Integer catId);

  Page<RawDataProductDO> listRawDataProduct(Map<String, Object> inputs,
      Map<String, Object> outputs);

  Page<RawDataProductDO> findExamDate();

  RawDataProductDO getRawDataProductById(Integer productId);

  // 根据专业id查询，专业与第二级单位信息

  /* unit */
  Page<UnitDO> listUnit(Map<String, Object> inputs, Map<String, Object> outputs);

  UnitDO getUnit(Long unitId);

  Page<ChiefUnitRelationDO> listChiefUnitRelation(Map<String, Object> inputs,
      Map<String, Object> outputs);

  ChiefUnitRelationDO getChiefUnitByCatId(Integer catId);

  ChiefUnitRelationDO getChiefUnitByUnitId(Long unitId);

  Boolean saveRelate(ChiefUnitRelationDO curdo);

  Boolean updateRelate(Map<String, Object> inputs, ChiefUnitRelationDO curdo);

  /* manageRole */
  Page<RoleDO> listManageRole(Map<String, Object> inputs, Map<String, Object> outputs);

  RoleDO getRoleById(Integer role);

  Boolean saveRole(RoleDO roleDO);

  Boolean updateRole(RoleDO roleDO);

  Boolean removeRole(Integer role);

  /* manageUnit */
  Page<UnitDO> listManageUnit(Map<String, Object> inputs, Map<String, Object> outputs);

  Boolean saveUnit(UnitDO unitDO);

  Boolean updateUnit(UnitDO unitDO);

  Boolean removeUnit(Long unitId);

  /* manageAdmin */
  Page<AdminDO> listManageAdmin(Map<String, Object> inputs, Map<String, Object> outputs);

  AdminDO getAdmin(Integer adminId);

  Boolean saveAdmin(AdminDO adminDO);

  Boolean updateAdmin(AdminDO adminDO);

  Boolean removeAdmin(Integer adminId);

  /* privilege */
  Page<PrivilegeDO> listManagePrivilege(Map<String, Object> inputs, Map<String, Object> outputs);

  PrivilegeDO getPrivilegeById(Integer privilegeId);

  Boolean savePrivilege(PrivilegeDO privilegeDO);

  Boolean updatePrivilegeById(PrivilegeDO privilegeDO);

  Boolean removePrivilege(Integer privilegeId);

  Boolean removePrivilegeByCond(Map<String, Object> inputs);

  /* RolePrivilegeDO */
  Page<RolePrivilegeDO> listManageRolePrivilege(Map<String, Object> inputs,
      Map<String, Object> outputs);

  RolePrivilegeDO getRolePrivilege(Integer rolePrivilegeId);

  Boolean saveRolePrivilege(RolePrivilegeDO rolePrivilegeDO);

  Boolean updateRolePrivilege(RolePrivilegeDO rolePrivilegeDO);

  Boolean removeRolePrivilege(Integer rolePrivilegeId);

  Boolean removeRolePrivilegeByCond(Map<String, Object> inputs);


  /* FinalExamDO */
  Page<DashboardFinalExamDO> listDbFinalExam(Map<String, Object> inputs,
      Map<String, Object> outputs);

  DashboardFinalExamDO getDbFinalExamById(Long finalExamId);

  /* RawDataFinalExamDO */
  Page<RawDataFinalExamDO> listRdFinalExam(Map<String, Object> inputs, Map<String, Object> outputs);

  RawDataFinalExamDO getRdFinalExamById(Long finalExamId);

  /* StaticInfoDO */
  Page<StaticInfoDO> listStaticInfo(Map<String, Object> inputs, Map<String, Object> outputs);

  Boolean updateStaticInfo(StaticInfoDO staticInfoDO);

  Page<ApplyCountDO> groupCatApply(Map<String, Object> inputs, Map<String, Object> allField,
      List<String> groups);

  /* LogSyncDao */
  Page<SyncLogDO> listSyncLog(Map<String, Object> inputs, Map<String, Object> outputs);

  Boolean saveSyncLog(SyncLogDO syncLogDO);

  Boolean updateSyncLog(SyncLogDO syncLogDO);

  /* SyncStudentDO */
  Page<SyncStudentDO> listSyncStudent(Map<String, Object> inputs, Map<String, Object> outputs);

  Boolean saveSyncStudent(SyncStudentDO SyncStudentDO);

  Integer saveSyncStudentList(List<SyncStudentDO> value, Map<String, Object> column);

  /* SyncApplyDO */
  Page<SyncApplyDO> listSyncApply(Map<String, Object> inputs, Map<String, Object> outputs);

  Integer saveSyncApplyList(List<SyncApplyDO> value, Map<String, Object> column);

  Boolean updateApply(ApplyDO applyDO);

  Boolean saveSyncApply(SyncApplyDO sado);

  /* SyncAdmissionCardCodeDO */
  Page<SyncAdmissionCardCodeDO> listSyncCard(Map<String, Object> inputs,
      Map<String, Object> outputs);

  Boolean saveSyncCard(SyncAdmissionCardCodeDO sccdo);

  Integer saveSyncCardList(List<SyncAdmissionCardCodeDO> value, Map<String, Object> column);

  /* SmsLogDO */
  Page<SmsLogDO> listSmsLog(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<SmsLogDO> pg);

  // 修改手机号码
  Boolean updateStudentTelephone(StudentDO studentDO);

  // 查询用户头像信息
  StudentMisDO findUserOfficialInfoByUserId(StudentMisDO studentMisDO);

  // 重置用户头像
  Boolean resetStudentPortrait(StudentDO studentDO);

  // 更新用户头像信息
  Boolean updateUserOfficialInfoByUserId(StudentMisDO studentMisDO);

  List<StudentBaseUserDO> findTelByTelephoneAndModule(StudentBaseUserDO studentBaseUserDO);

  Boolean updateScoreByDailyScore(ScoreDO vo);

  Page<ScoreDO> findPureScoreListByCond(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<ScoreDO> pg);

  Page<ScoreDO> findScoreListJoinProduct(Map<String, Object> inputs, Map<String, Object> outputs,
      Page<ScoreDO> pg);

  Boolean saveScore(ScoreDO vo);

  Boolean updateScoreByCond(Map<String, Object> inputs, ScoreDO sdo);

  Page<EverydaySummaryVO> getEveryDaySummary(Long unitId, Date date);

  Page<EverydaySummaryDetailVO> getEveryDaySummary(List<Long> unitIds, Date date);

  List<UnitDO> getPilotsByTaught();

  List<ApplyDO> getPilotsByChief(Integer catId);
  
  Page<AlarmRecordDO> listAlarmRecord(Map<String, Object> inputs,Map<String, Object> outputs, Page<AlarmRecordDO> page);
  Page<EverydaySummaryDetailVO> getEveryDaySummary(List<Long> pilotUnitIdList, Long unitId,
                                                   Date date);
  
  public StaticInfoDO staticInfo();

}
