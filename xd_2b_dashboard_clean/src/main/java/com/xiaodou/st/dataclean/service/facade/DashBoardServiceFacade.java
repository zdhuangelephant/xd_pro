package com.xiaodou.st.dataclean.service.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xiaodou.summer.dao.param.IQueryParam;

public interface DashBoardServiceFacade {
  // 基础数据表

  RawDataLearnRecordModel insertRawDataLearnRecordModel(RawDataLearnRecordModel model);

  List<RawDataLearnRecordModel> getRawDataLearnRecordModelList(Map<String, Object> cond);
  
  RawDataExamTotalModel insertRawDataExamTotalModel(RawDataExamTotalModel model);
  
  List<RawDataExamTotalModel> getRawDataExamTotalModelList(Map<String, Object> cond);

  Boolean updateRawDataExamTotalModel(RawDataExamTotalModel model);

  RawDataExamTotalModel queryRawDataExamTotalModel(String userId, String module, String productId);

  RawDataFaceRecognitionModel insertRawDataFaceRecognitionModel(RawDataFaceRecognitionModel model);


  RawDataFinishMissionModel insertRawDataFinishMission(RawDataFinishMissionModel model);

  List<RawDataFinishMissionModel> getRawDataFinishMissionModelList(Map<String, Object> cond);
  
  RawDataFinishAllMissionModel insertRawDataFinishAllMission(RawDataFinishAllMissionModel rawData);

  void deleteTodayRawDataFinishMission(RawDataFinishMissionModel missionModel);

  // 产品
  RawDataProductModel getRawDataProductById(Long id);

  // 专业
  RawDataProductCategoryModel getRawDataProductCategoryById(Integer id);

  RawDataProductCategoryModel getRawDataProductCategoryByTypeCode(String typeCode);

  // 任务
  RawDataFinishMissionModel queryRawDataFinishMissionModel(String userId, String module,
      String majorId, String courseId, String recordDate);

  RawDataFinishAllMissionModel queryRawDataFinishAllMissionModel(String userId, String module,
      String majorId, String courseId);

  Page<RawDataFinishAllMissionModel> queryAllRawDataFinishAllMissionModel();

  // 单位

  // 自考办
  Unit queryTaughtUnit();

  // 主考院校
  Page<Unit> queryChiefUnit();

  // 助学单位
  Page<Unit> queryPilotUnit();

  Unit queryUnitById(Integer id);

  // 学习统计表
  Page<RawDataExamTotalModel> queryTotalStatistic(IQueryParam param);

  // 每日学习时间表
  List<DaysLearnTimeModel> getDaysLearnTimeModelByCond(Map<String, Object> inputArgument);

  DaysLearnTimeModel addDaysLearnTime(DaysLearnTimeModel entity);

  boolean editDaysLearnTime(Map<String, Object> cond, DaysLearnTimeModel daysLearnTimeModel);

  Integer queryDaysLearnTimeCount(Map<String, Object> inputArgument);

  // 学生表
  StudentModel getStudentByUserId(String userId);

  List<StudentModel> getStudentModelByCond(Map<String, Object> inputArgument);

  Integer queryStudentCountByCond(Map<String, Object> inputArgument);

  Boolean updateStudentFaceByUserId(String userId, String faceUrl, String clientType);

  // 工作台趋势
  List<SessionLearnPercentModel> getSessionLearnPercentModelByCond(Map<String, Object> inputArgument);

  Double getAvgSessionLearnPercentModelByCond(Map<String, Object> inputArgument);
  
  SessionLearnPercentModel addSessionLearnPercent(SessionLearnPercentModel entity);

  boolean editSessionLearnPercent(Map<String, Object> cond, SessionLearnPercentModel entity);

  Double querySessionLearnTimeByCond(Map<String, Object> inputArgument);

  Double queryAllMissionPercent(Map<String, Object> input4);
  
  Double queryAvgMissionPercent(Map<String, Object> input4);

  // 专业趋势
  List<CategroyUnitSessionLearnPercentModel> getCategroySessionLearnPercentModelByCond(
      Map<String, Object> cond);

  CategroyUnitSessionLearnPercentModel addCategroySessionLearnPercent(
      CategroyUnitSessionLearnPercentModel entity);

  boolean editCategroySessionLearnPercent(Map<String, Object> cond,
      CategroyUnitSessionLearnPercentModel entity);

  // 报名表
  Integer queryStudentCountByApply(Map<String, Object> cond);

  ApplyModel queryStudentApplyCatInfo(Integer studentId, Integer catId, Integer productId);

  List<Integer> queryPilotUnitIdByCatId(List<Integer> catIdList);

  List<Integer> queryCatIdByPilotUnitId(List<Integer> pilotUnitIdList);

  // 原始表获取总学习时间
  Integer queryRawLearnTimeCountByCond(Map<String, Object> cond);

  Integer queryRawStudentCountByCond(Map<String, Object> cond);

  List<RawDataLearnRecordModel> getLaskWeekLearnTimeRank(Map<String, Object> cond);

  // 查询主考院校旗下专业
  List<ChiefUnitRelationModel> getUnitByChief(Integer id);

  // 根据专业ID查询主考院校
  ChiefUnitRelationModel getChiefByCatId(Integer catId);

  // 专业信息
  List<CategorySessionPercentModel> getCategorySessionPercentModelByCond(
      Map<String, Object> inputArgument);

  CategorySessionPercentModel addCategorySessionPercent(CategorySessionPercentModel entity);

  boolean editCategorySessionPercentModel(Map<String, Object> cond,
      CategorySessionPercentModel entity);

  // 试点单位
  List<CategoryUnitSessionPercentModel> getCategoryUnitSessionPercentModelByCond(
      Map<String, Object> inputArgument);

  CategoryUnitSessionPercentModel addCategoryUnitSessionPercentModel(
      CategoryUnitSessionPercentModel entity);

  boolean editCategoryUnitSessionPercentModel(Map<String, Object> cond,
      CategoryUnitSessionPercentModel entity);

  // 基础成绩表 获取平均正确率
  Double queryAvgRightPercent(Map<String, Object> cond);

  Double queryAvgScore(Map<String, Object> input4);

  // 平均成绩表
  CateGoryUnitProductAvgScoreModel addcateGoryUnitProductAvgScoreDao(
      CateGoryUnitProductAvgScoreModel model);

  List<CateGoryUnitProductAvgScoreModel> getCateGoryUnitProductAvgScoreModelByCond(
      Map<String, Object> cond);

  boolean editCateGoryUnitProductAvgScoreModel(Map<String, Object> cond,
      CateGoryUnitProductAvgScoreModel entity);

  // 任务相关
  Integer getFinishMissionStudentCount(Map<String, Object> input4);

  Integer getCurrentFinishMissionStudentCount(Map<String, Object> input4);

  // 报警
  AlarmRecordModel insertAlarmRecord(AlarmRecordModel model);
  
  List<AlarmRecordModel> queryAlarmModelByCond(Map<String,Object> input);

  Page<RawDataFaceRecognitionModel> queryFaceRecognitionPage(Integer studentId, String testId);

  AlarmRecordModel queryAlarmModel(Integer studentId, String testId);

  // 上周学霸榜
  LastWeekLearnTimeModel insertLastWeekLearnTime(LastWeekLearnTimeModel model);

  boolean deleteLastWeekLearnTimeByRank(Integer unitId);

  // 上周每日任务完成度榜
  LastWeekMissionPercentModel insertLastWeekMissionPercent(LastWeekMissionPercentModel model);

  LastWeekMissionPercentModel queryLastWeekMissionPercentByPilotIdAndTaughtId(Integer pilotId,
      Integer taughtId);

  boolean deleteLastWeekMissionPercent(Integer unitId);

  boolean deleteLastWeekMissionPercent(RoleTypeEnum roleType);

  // 专业
  RawDataProductCategoryModel insertRawDataProductCategory(RawDataProductCategoryModel model);

  boolean editRawDataProductCategoryModel(Map<String, Object> cond,
      RawDataProductCategoryModel entity);

  public boolean delRawDataProductCategory(RawDataProductCategoryModel entity);
  
  public List<RawDataProductCategoryModel> getRawDataProductCategoryByCond(Map<String, Object> input);

  // 课程
  public RawDataProductModel insertRawDataProduct(RawDataProductModel model);

  boolean editRawDataProductModel(Map<String, Object> cond, RawDataProductModel entity);

  public boolean delRawDataProductModel(RawDataProductModel entity);

  // 专业课程关联
  public RawDataProductRelationModel insertRawDataProductRelation(RawDataProductRelationModel model);

  boolean editRawDataProductRelationModel(Map<String, Object> cond,
      RawDataProductRelationModel entity);

  public boolean delRawDataProductRelationModel(RawDataProductRelationModel entity);

  Double queryCateGorySessionLearnTimeByCond(Map<String, Object> inputArgument);

  Double queryAvgCateGorySessionLearnTimeByCond(Map<String, Object> inputArgument);

  /* dashboard_learn_record */
  Boolean savelearnRecord(LearnRecordDO alarmRecordDO);

  Boolean saveScore(ScoreDO scoreDO);
  
  Page<ScoreDO> listScore(Map<String, Object> inputs, Map<String, Object> outputs);
  /**查找当期考期的score
   * @param inputs
   * @param outputs
   * @return
   */
  Page<ScoreDO> listScore0(Map<String, Object> inputs, Map<String, Object> outputs);

  Boolean updateScoreByCond(Map<String, Object> inputs, ScoreDO scoreDO);
  
  //获取学期平均活跃度
  Double queryAvgLearnActive(Map<String, Object> input4);
  
  StudentModel getStudentBySid(Integer sid);
  
  List<ApplyModel> queryApplyBySid(Integer studentId);
  
  Page<ApplyModel> queryApplysByConds(Map<String, Object> inputs, Map<String, Object> outputs,
                          Page<ApplyModel> pg);

  void updateAlarmRecordById(AlarmRecordModel alarmRecordModel);

  /**从learn_record表内获取某个学生或者学生的最近学习记录
   * @return
   */
  List<LearnRecordDO> getStudentForMaxLearnTime(Integer stuId);
  
  
  /**当期已缴费成功的课程科次
   * @param cond
   * @return
   */
  public Integer querySubjectsByApply(Map<String, Object> cond);

  /**根据条件、获取有学习时长的(自考、主考、试点)总的科次(stuId、productId为一组)
   * @param input
   * @return
   */
  List<RawDataLearnRecordModel> querySubjectsByLearnRecord(Map<String, Object> input);

  /**获取订单表中有效科次(stuId + productId)为一个科次
   * @param cond
   * @return
   */
  List<ApplyModel> querySubjectsByCond(Map<String, Object> cond);

  Page<EverydaySummaryVO> queryEverydaySummaryByCond(HashMap<String, Object> input,
      Map<String, Object> output);
  Page<EverydaySummaryDetailVO> queryEverydaySummaryDetailByCond(HashMap<String, Object> input,
                                                     Map<String, Object> output);

  EverydaySummaryVO insertEverydaySummary(EverydaySummaryVO lsmTaughtUnit);

  Boolean updateEverydaySummaryById(EverydaySummaryVO lsmTaughtUnit);

  List<Integer> getStudentForNeverLearnCourse();
  
  public StaticInfoDO staticInfo();

  void insertEverydaySummaryDetail(EverydaySummaryDetailVO detailVo);

  void updateEverydaySummaryDetailById(EverydaySummaryDetailVO detailVo);
  
  List<ApplyModel> getPilotsByChief(Integer catId);

  ChiefUnitRelationModel queryCategoryByChiefId(Integer chiefId);

  RawDataUserScorePointRecord insertRawDataUserScorePointRecord(RawDataUserScorePointRecord uspr);

  Boolean updateRawDataUserScorePointRecord(RawDataUserScorePointRecord uspr);

  RawDataProductScorePointRule insertRawDataProductScorePointRule(RawDataProductScorePointRule model);

  Boolean editRawDataProductScorePointRule(Map<String, Object> cond, RawDataProductScorePointRule model);

  Boolean delRawDataProductScorePointRule(RawDataProductScorePointRule model);
}
