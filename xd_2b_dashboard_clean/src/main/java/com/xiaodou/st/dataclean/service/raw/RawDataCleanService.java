package com.xiaodou.st.dataclean.service.raw;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dataclean.exception.DcMsgConsuFailException;
import com.xiaodou.st.dataclean.model.domain.dashboard.ApplyModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.ChiefUnitRelationModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.StudentModel;
import com.xiaodou.st.dataclean.model.domain.dashboard.Unit;
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
import com.xiaodou.st.dataclean.model.transport.TransferFaceRecognitionData;
import com.xiaodou.st.dataclean.model.transport.TransferFinishAllMissionData;
import com.xiaodou.st.dataclean.model.transport.TransferFinishMissionData;
import com.xiaodou.st.dataclean.model.transport.TransferProductCategoryData;
import com.xiaodou.st.dataclean.model.transport.TransferProductData;
import com.xiaodou.st.dataclean.model.transport.TransferProductRelationData;
import com.xiaodou.st.dataclean.model.transport.TransferScoreRuleData;
import com.xiaodou.st.dataclean.model.transport.TransferStudentData;
import com.xiaodou.st.dataclean.model.transport.TransferUserExamTotalData;
import com.xiaodou.st.dataclean.model.transport.TransferUserExamTotalData.ChapterNode;
import com.xiaodou.st.dataclean.model.transport.TransferUserExamTotalData.FinalExamNode;
import com.xiaodou.st.dataclean.model.transport.TransferUserLearnRecordData;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.util.ErrorsWrapper;

/**
 * @name @see com.xiaodou.st.dataclean.service.raw.RawDataCleanService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月30日
 * @description 原始数据清洗Service
 * @version 1.0
 */
@Service("rawDataCleanService")
public class RawDataCleanService {

  @Resource
  DashBoardServiceFacade dashBoardServiceFacade;

  public RawDataLearnRecordModel cleanRawDataLearnRecord(TransferUserLearnRecordData learnRecordData) {
    if (null == learnRecordData) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return null;
    }
    if (null == learnRecordData.getSrcRecordId()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("SrcRecordId"));
      return null;
    }
    if (null == learnRecordData.getUserId()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("UserId"));
      return null;
    }
    if (null == learnRecordData.getProductId()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("ProductId"));
      return null;
    }
    // base_data_field
    RawDataLearnRecordModel rawData = new RawDataLearnRecordModel();
    if (null != learnRecordData.getModuleId()) {
      rawData.setModuleId(learnRecordData.getModuleId().toString());
    }
    rawData.setSrcRecordId(learnRecordData.getSrcRecordId().toString());
    StudentModel student =
        dashBoardServiceFacade.getStudentByUserId(learnRecordData.getUserId().toString());
    if (null == student) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.studentInfoNull(learnRecordData.getUserId()));
      return null;
    }
    rawData.setStudent(student);
    Unit pilotUnit = dashBoardServiceFacade.queryUnitById(student.getPilotUnitId());
    if (null == pilotUnit) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.pilotInfoNull(student.getPilotUnitId()));
      return null;
    }
    rawData.setPioltUnit(pilotUnit);
    rawData.setUserId(learnRecordData.getUserId().toString());
    rawData.setStudentId(student.getId());
    RawDataProductCategoryModel productCategory =
        dashBoardServiceFacade.getRawDataProductCategoryByTypeCode(learnRecordData.getTypeCode());
    if (null == productCategory) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateNullByTypeCode(learnRecordData.getTypeCode()));
      return null;
    }
    rawData.setCategoryModel(productCategory);
    RawDataProductModel productModel =
        dashBoardServiceFacade.getRawDataProductById(learnRecordData.getProductId().longValue());
    if (null == productModel) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proInfoNull(learnRecordData.getProductId()));
      return null;
    }
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    if (null == productModel.getBeginApplyTime() || null == productModel.getEndApplyTime()) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(productModel.getBeginApplyTime(),
              productModel.getEndApplyTime()));
      return null;
    }
    if (productModel.getBeginApplyTime().after(currentTime)
        || currentTime.after(productModel.getEndApplyTime())) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(productModel.getBeginApplyTime(),
              productModel.getEndApplyTime()));
      return null;
    }
    rawData.setProductModel(productModel);
    ApplyModel applyInfo =
        dashBoardServiceFacade.queryStudentApplyCatInfo(student.getId(), productCategory.getId(),
            learnRecordData.getProductId());
    if (null == applyInfo) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.applyInfoCheckFail(student.getId(), productCategory.getId()));
      return null;
    }
    rawData.setApplyInfo(applyInfo);
    rawData.setTypeCode(learnRecordData.getTypeCode());
    rawData.setProductCategoryId(productCategory.getId().toString());
    rawData.setProductId(learnRecordData.getProductId().toString());
    Unit taughtUnit = dashBoardServiceFacade.queryTaughtUnit();
    if (null == taughtUnit) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.taughtInfoCheckFail());
      return null;
    }
    rawData.setTaughtUnit(taughtUnit);
    rawData.setTaughtUnitId(taughtUnit.getId());
    ChiefUnitRelationModel chief = dashBoardServiceFacade.getChiefByCatId(productCategory.getId());
    if (null == chief) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), null));
      return null;
    }
    Unit chiefUnit = dashBoardServiceFacade.queryUnitById(chief.getUnitId());
    if (null == chiefUnit) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), chief.getUnitId()));
      return null;
    }
    rawData.setCheifUnit(chiefUnit);
    rawData.setChiefUnitId(chief.getUnitId());
    rawData.setPilotUnitId(student.getPilotUnitId());
    // clean_data_field
    rawData.setRecordTime(DateUtil.SDF_YMD.format(learnRecordData.getRecordTime()));
    rawData.setLearnTime(learnRecordData.getLearnTime());
    rawData.setLearnType(learnRecordData.getLearnType());
    rawData.setLearnContent(learnRecordData.getLearnContent());
    dashBoardServiceFacade.insertRawDataLearnRecordModel(rawData);
    return rawData;
  }

  public RawDataExamTotalModel cleanRawDataExamTotal(TransferUserExamTotalData examTotalData) {
    if (null == examTotalData) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return null;
    }
    if (null == examTotalData.getUserId()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("UserId"));
      return null;
    }
    if (null == examTotalData.getModule()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("Module"));
      return null;
    }
    if (null == examTotalData.getCourseId()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("CourseId"));
      return null;
    }
    RawDataExamTotalModel rawData =
        dashBoardServiceFacade.queryRawDataExamTotalModel(examTotalData.getUserId(),
            examTotalData.getModule(), examTotalData.getCourseId().toString());
    if (null == rawData) {
      // base_data_field
      rawData = new RawDataExamTotalModel();
    }
    rawData.setModuleId(examTotalData.getModule().toString());
    StudentModel student =
        dashBoardServiceFacade.getStudentByUserId(examTotalData.getUserId().toString());
    if (null == student) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.studentInfoNull(examTotalData.getUserId()));
      return null;
    }
    rawData.setStudent(student);
    Unit pilotUnit = dashBoardServiceFacade.queryUnitById(student.getPilotUnitId());
    if (null == pilotUnit) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.pilotInfoNull(student.getPilotUnitId()));
      return null;
    }
    rawData.setPioltUnit(pilotUnit);
    rawData.setUserId(examTotalData.getUserId().toString());
    rawData.setStudentId(student.getId());
    RawDataProductCategoryModel productCategory =
        dashBoardServiceFacade.getRawDataProductCategoryByTypeCode(examTotalData.getTypeCode());
    if (null == productCategory) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateNullByTypeCode(examTotalData.getTypeCode()));
      return null;
    }
    rawData.setCategoryModel(productCategory);
    RawDataProductModel productModel =
        dashBoardServiceFacade.getRawDataProductById(examTotalData.getCourseId().longValue());
    if (null == productModel) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proInfoNull(examTotalData.getCourseId()));
      return null;
    }
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    if (null == productModel.getBeginApplyTime() || null == productModel.getEndApplyTime()) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(productModel.getBeginApplyTime(),
              productModel.getEndApplyTime()));
      return null;
    }
    if (productModel.getBeginApplyTime().after(currentTime)
        || currentTime.after(productModel.getEndApplyTime())) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(productModel.getBeginApplyTime(),
              productModel.getEndApplyTime()));
      return null;
    }
    rawData.setProductModel(productModel);
    ApplyModel applyInfo =
        dashBoardServiceFacade.queryStudentApplyCatInfo(student.getId(), productCategory.getId(),
            Integer.valueOf(examTotalData.getCourseId().toString()));
    if (null == applyInfo) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.applyInfoCheckFail(student.getId(), productCategory.getId()));
      return null;
    }
    rawData.setApplyInfo(applyInfo);
    rawData.setTypeCode(examTotalData.getTypeCode());
    rawData.setProductCategoryId(productCategory.getId().toString());
    rawData.setProductId(examTotalData.getCourseId().toString());
    Unit taughtUnit = dashBoardServiceFacade.queryTaughtUnit();
    if (null == taughtUnit) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.taughtInfoCheckFail());
      return null;
    }
    rawData.setTaughtUnit(taughtUnit);
    rawData.setTaughtUnitId(taughtUnit.getId());
    ChiefUnitRelationModel chief = dashBoardServiceFacade.getChiefByCatId(productCategory.getId());
    if (null == chief) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), null));
      return null;
    }
    Unit chiefUnit = dashBoardServiceFacade.queryUnitById(chief.getUnitId());
    if (null == chiefUnit) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), chief.getUnitId()));
      return null;
    }
    rawData.setCheifUnit(chiefUnit);
    rawData.setChiefUnitId(chief.getUnitId());
    rawData.setPilotUnitId(student.getPilotUnitId());

    // clean_data_field
    rawData.setTotalQues(examTotalData.getTotalQues());
    rawData.setTotalRank(examTotalData.getTotalRank());
    rawData.setRightQues(examTotalData.getRightQues());
    rawData.setRightRank(examTotalData.getRightRank());
    rawData.setRightPercent(examTotalData.getRightPercent());
    rawData.setScore(examTotalData.getScore());
    if (null != examTotalData.getChapterNodeList() && !examTotalData.getChapterNodeList().isEmpty()) {
      Collections.sort(examTotalData.getChapterNodeList(), new Comparator<ChapterNode>() {
        @Override
        public int compare(ChapterNode o1, ChapterNode o2) {
          Long i = o1.getOrder() - o2.getOrder();
          return Integer.valueOf(i + "");
        }
      });
      rawData.setChapterNodeList(FastJsonUtil.toJson(examTotalData.getChapterNodeList()));
    }
    if (null != examTotalData.getFinalExamNodeList()
        && !examTotalData.getFinalExamNodeList().isEmpty()) {
      Collections.sort(examTotalData.getFinalExamNodeList(), new Comparator<FinalExamNode>() {
        @Override
        public int compare(FinalExamNode o1, FinalExamNode o2) {
          int i = o1.getOrder() - o2.getOrder();
          return i;
        }
      });
      rawData.setFinalExamNodeList(FastJsonUtil.toJson(examTotalData.getFinalExamNodeList()));
    }

    List<RawDataUserScorePointRecord> userScorePointRecordList =
        examTotalData.getUserScorePointRecordList();
    if (null == userScorePointRecordList || CollectionUtils.isEmpty(userScorePointRecordList)) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.userScorePointFail(examTotalData.getModule(),
              examTotalData.getCourseId(), chief.getUnitId(), examTotalData.getUserId()));
    }
    for (RawDataUserScorePointRecord scorePoint : userScorePointRecordList) {
      if (scorePoint == null) continue;
      RawDataUserScorePointRecord uspr = new RawDataUserScorePointRecord();
      uspr.setId(scorePoint.getId());
      uspr.setRuleType(scorePoint.getRuleType());
      uspr.setModule(scorePoint.getModule());
      uspr.setProductId(scorePoint.getProductId());
      uspr.setUserId(scorePoint.getUserId());
      uspr.setScore(scorePoint.getScore());
      uspr.setCompletePercent(scorePoint.getCompletePercent());
      uspr.setCreateTime(new Timestamp(System.currentTimeMillis()));
      uspr.setModifyTime(scorePoint.getModifyTime());
      if (null == uspr.getId()) {
        dashBoardServiceFacade.insertRawDataUserScorePointRecord(uspr);
      } else {
        dashBoardServiceFacade.updateRawDataUserScorePointRecord(uspr);
      }
    }

    if (null == rawData.getId()) {
      dashBoardServiceFacade.insertRawDataExamTotalModel(rawData);
    } else {
      dashBoardServiceFacade.updateRawDataExamTotalModel(rawData);
    }
    return rawData;
  }

  public RawDataFaceRecognitionModel cleanRawDataFaceRecognition(
      TransferFaceRecognitionData faceRecognitionData) {
    if (null == faceRecognitionData) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return null;
    }
    RawDataFaceRecognitionModel rawData = new RawDataFaceRecognitionModel();
    rawData.setModule(faceRecognitionData.getModule());
    rawData.setTestId(faceRecognitionData.getPaperId());
    StudentModel student =
        dashBoardServiceFacade.getStudentByUserId(faceRecognitionData.getUserId());
    if (null == student) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.studentInfoNull(faceRecognitionData.getUserId()));
      return null;
    }
    rawData.setUserId(faceRecognitionData.getUserId());
    rawData.setStudentId(student.getId());
    RawDataProductCategoryModel productCategory =
        dashBoardServiceFacade.getRawDataProductCategoryByTypeCode(faceRecognitionData
            .getTypeCode());
    if (null == productCategory) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateNullByTypeCode(faceRecognitionData.getTypeCode()));
      return null;
    }
    ApplyModel applyInfo =
        dashBoardServiceFacade.queryStudentApplyCatInfo(student.getId(), productCategory.getId(),
            Integer.valueOf(faceRecognitionData.getProductId()));
    if (null == applyInfo) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.applyInfoCheckFail(student.getId(), productCategory.getId()));
      return null;
    }
    rawData.setCatName(productCategory.getName());
    RawDataProductModel product =
        dashBoardServiceFacade.getRawDataProductById(Long.parseLong(faceRecognitionData
            .getProductId()));
    if (null == product) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proInfoNull(faceRecognitionData.getProductId()));
      return null;
    }
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    if (null == product.getBeginApplyTime() || null == product.getEndApplyTime()) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(product.getBeginApplyTime(),
              product.getEndApplyTime()));
      return null;
    }
    if (product.getBeginApplyTime().after(currentTime)
        || currentTime.after(product.getEndApplyTime())) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(product.getBeginApplyTime(),
              product.getEndApplyTime()));
      return null;
    }
    rawData.setProductName(product.getName());
    Unit taughtUnit = dashBoardServiceFacade.queryTaughtUnit();
    if (null == taughtUnit) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.taughtInfoCheckFail());
      return null;
    }
    rawData.setTaughtUnitId(taughtUnit.getId());
    ChiefUnitRelationModel chief = dashBoardServiceFacade.getChiefByCatId(productCategory.getId());
    if (null == chief) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), null));
      return null;
    }
    Unit chiefUnit = dashBoardServiceFacade.queryUnitById(chief.getUnitId());
    if (null == chiefUnit) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), chief.getUnitId()));
      return null;
    }
    rawData.setCheifUnit(chiefUnit);
    rawData.setChiefUnitId(chief.getUnitId());
    rawData.setPilotUnitId(student.getPilotUnitId());
    rawData.setTestPoint(faceRecognitionData.getChapterName());
    rawData.setCollectTime(faceRecognitionData.getRecordTime());
    rawData.setCollectPortrait(faceRecognitionData.getFaceUrl());
    rawData.setSimilarity(faceRecognitionData.getSimilarity());
    rawData.setDeviceId(faceRecognitionData.getDeviceId());
    if (StringUtils.isNotBlank(faceRecognitionData.getIsSelf())) {
      rawData.setResult(Short.valueOf(faceRecognitionData.getIsSelf()));
    }
    rawData.setCreateTime(new Timestamp(System.currentTimeMillis()));
    dashBoardServiceFacade.insertRawDataFaceRecognitionModel(rawData);
    return rawData;
  }

  public RawDataFinishAllMissionModel cleanRawDataFinishAllMission(
      TransferFinishAllMissionData finishAllMission) {
    if (null == finishAllMission) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return null;
    }
    if (StringUtils.isOrBlank(finishAllMission.getUserId(), finishAllMission.getModule(),
        finishAllMission.getMajorId(), finishAllMission.getCourseId())) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.coreParamNull("UserId|Module|MajorId|CourseId"));
      return null;
    }
    RawDataFinishAllMissionModel rawData =
        dashBoardServiceFacade.queryRawDataFinishAllMissionModel(finishAllMission.getUserId(),
            finishAllMission.getModule(), finishAllMission.getMajorId(),
            finishAllMission.getCourseId());
    if (null != rawData) {
      return rawData;
    }
    // base_data_field
    rawData = new RawDataFinishAllMissionModel();
    rawData.setModuleId(finishAllMission.getModule().toString());
    StudentModel student =
        dashBoardServiceFacade.getStudentByUserId(finishAllMission.getUserId().toString());
    if (null == student) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.studentInfoNull(finishAllMission.getUserId()));
      return null;
    }
    rawData.setUserId(finishAllMission.getUserId().toString());
    rawData.setStudentId(student.getId());
    RawDataProductCategoryModel productCategory =
        dashBoardServiceFacade.getRawDataProductCategoryById(Integer.parseInt(finishAllMission
            .getMajorId()));
    if (null == productCategory) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateNullById(finishAllMission.getMajorId()));
      return null;
    }
    ApplyModel applyInfo =
        dashBoardServiceFacade.queryStudentApplyCatInfo(student.getId(), productCategory.getId(),
            Integer.valueOf(finishAllMission.getCourseId()));
    if (null == applyInfo) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.applyInfoCheckFail(student.getId(), productCategory.getId()));
      return null;
    }
    rawData.setProductCategoryId(productCategory.getId().toString());
    RawDataProductModel product =
        dashBoardServiceFacade
            .getRawDataProductById(Long.parseLong(finishAllMission.getCourseId()));
    if (null == product) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proInfoNull(finishAllMission.getCourseId()));
      return null;
    }
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    if (null == product.getBeginApplyTime() || null == product.getEndApplyTime()) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(product.getBeginApplyTime(),
              product.getEndApplyTime()));
      return null;
    }
    if (product.getBeginApplyTime().after(currentTime)
        || currentTime.after(product.getEndApplyTime())) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(product.getBeginApplyTime(),
              product.getEndApplyTime()));
      return null;
    }
    rawData.setProductId(finishAllMission.getCourseId());
    Unit taughtUnit = dashBoardServiceFacade.queryTaughtUnit();
    if (null == taughtUnit) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.taughtInfoCheckFail());
      return null;
    }
    rawData.setTaughtUnitId(taughtUnit.getId());
    ChiefUnitRelationModel chief = dashBoardServiceFacade.getChiefByCatId(productCategory.getId());
    if (null == chief) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), null));
      return null;
    }
    Unit chiefUnit = dashBoardServiceFacade.queryUnitById(chief.getUnitId());
    if (null == chiefUnit) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), chief.getUnitId()));
      return null;
    }
    rawData.setCheifUnit(chiefUnit);
    rawData.setChiefUnitId(chief.getUnitId());
    rawData.setPilotUnitId(student.getPilotUnitId());
    // clean_data_field
    rawData.setCreateTime(new Timestamp(System.currentTimeMillis()));
    dashBoardServiceFacade.insertRawDataFinishAllMission(rawData);
    { // 删除今日完成任务记录
      RawDataFinishMissionModel missionModel = new RawDataFinishMissionModel();
      missionModel.setModuleId(rawData.getModuleId());
      missionModel.setProductCategoryId(rawData.getProductCategoryId());
      missionModel.setRecordTime(DateUtil.SDF_YMD.format(rawData.getCreateTime()));
      missionModel.setStudentId(rawData.getStudentId());
      missionModel.setUserId(rawData.getUserId());
      dashBoardServiceFacade.deleteTodayRawDataFinishMission(missionModel);
    }
    return rawData;
  }

  public RawDataFinishMissionModel cleanRawDataFinishMission(TransferFinishMissionData finishMission) {
    if (null == finishMission) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return null;
    }
    if (null == finishMission.getUserId()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("UserId"));
      return null;
    }
    if (null == finishMission.getModule()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("Module"));
      return null;
    }
    if (null == finishMission.getMajorId()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("MajorId"));
      return null;
    }
    if (null == finishMission.getCourseId()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("CourseId"));
      return null;
    }
    if (null == finishMission.getCurrentTime()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull("CurrentTime"));
      return null;
    }
    String recordTime = DateUtil.SDF_YMD.format(finishMission.getCurrentTime());
    RawDataFinishMissionModel rawData =
        dashBoardServiceFacade.queryRawDataFinishMissionModel(finishMission.getUserId(),
            finishMission.getModule(), finishMission.getMajorId(), finishMission.getCourseId(),
            recordTime);
    if (null != rawData) {
      return rawData;
    }
    // base_data_field
    rawData = new RawDataFinishMissionModel();
    rawData.setModuleId(finishMission.getModule().toString());
    StudentModel student =
        dashBoardServiceFacade.getStudentByUserId(finishMission.getUserId().toString());
    if (null == student) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.studentInfoNull(finishMission.getUserId()));
      return null;
    }
    rawData.setUserId(finishMission.getUserId().toString());
    rawData.setStudentId(student.getId());
    RawDataProductCategoryModel productCategory =
        dashBoardServiceFacade.getRawDataProductCategoryById(Integer.parseInt(finishMission
            .getMajorId()));
    if (null == productCategory) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateNullById(finishMission.getMajorId()));
      return null;
    }
    ApplyModel applyInfo =
        dashBoardServiceFacade.queryStudentApplyCatInfo(student.getId(), productCategory.getId(),
            Integer.valueOf(finishMission.getCourseId()));
    if (null == applyInfo) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.applyInfoCheckFail(student.getId(), productCategory.getId()));
      return null;
    }
    rawData.setProductCategoryId(productCategory.getId().toString());
    RawDataProductModel product =
        dashBoardServiceFacade.getRawDataProductById(Long.parseLong(finishMission.getCourseId()));
    if (null == product) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proInfoNull(finishMission.getCourseId()));
      return null;
    }
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    if (null == product.getBeginApplyTime() || null == product.getEndApplyTime()) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(product.getBeginApplyTime(),
              product.getEndApplyTime()));
      return null;
    }
    if (product.getBeginApplyTime().after(currentTime)
        || currentTime.after(product.getEndApplyTime())) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(product.getBeginApplyTime(),
              product.getEndApplyTime()));
      return null;
    }
    rawData.setProductId(finishMission.getCourseId());
    Unit taughtUnit = dashBoardServiceFacade.queryTaughtUnit();
    if (null == taughtUnit) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proApplyTimeCheckFail(product.getBeginApplyTime(),
              product.getEndApplyTime()));
      return null;
    }
    rawData.setTaughtUnitId(taughtUnit.getId());
    ChiefUnitRelationModel chief = dashBoardServiceFacade.getChiefByCatId(productCategory.getId());
    if (null == chief) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), null));
      return null;
    }
    Unit chiefUnit = dashBoardServiceFacade.queryUnitById(chief.getUnitId());
    if (null == chiefUnit) {
      ErrorsWrapper.getWrapper().setValue(
          DcMsgConsuFailException.proCateChefFail(productCategory.getId(), chief.getUnitId()));
      return null;
    }
    rawData.setCheifUnit(chiefUnit);
    rawData.setChiefUnitId(chief.getUnitId());
    rawData.setPilotUnitId(student.getPilotUnitId());
    // clean_data_field
    rawData.setRecordTime(recordTime);
    rawData.setCreateTime(finishMission.getCurrentTime());
    if (null == dashBoardServiceFacade.queryRawDataFinishAllMissionModel(finishMission.getUserId(),
        finishMission.getModule(), finishMission.getMajorId(), finishMission.getCourseId())) {
      dashBoardServiceFacade.insertRawDataFinishMission(rawData);
    }
    return rawData;
  }

  public void addRawDataProductCategory(TransferProductCategoryData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductCategoryModel model = this.getRawDataProductCategoryModel(transferModel);
    dashBoardServiceFacade.insertRawDataProductCategory(model);
  }

  public void modifyRawDataProductCategory(TransferProductCategoryData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductCategoryModel model = this.getRawDataProductCategoryModel(transferModel);
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("id", transferModel.getId());
    dashBoardServiceFacade.editRawDataProductCategoryModel(cond, model);
  }

  public void deleteRawDataProductCategory(TransferProductCategoryData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductCategoryModel model = this.getRawDataProductCategoryModel(transferModel);
    dashBoardServiceFacade.delRawDataProductCategory(model);
  }

  public void addRawDataProduct(TransferProductData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductModel model = this.getRawDataProductModel(transferModel);
    dashBoardServiceFacade.insertRawDataProduct(model);
  }

  public void modifyRawDataProduct(TransferProductData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductModel model = this.getRawDataProductModel(transferModel);
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("id", transferModel.getId());
    dashBoardServiceFacade.editRawDataProductModel(cond, model);

  }

  public void deleteRawDataProduct(TransferProductData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductModel model = this.getRawDataProductModel(transferModel);
    dashBoardServiceFacade.delRawDataProductModel(model);
  }

  public void addRawDataProductRelation(TransferProductRelationData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductRelationModel model = this.getRawDataProductRelationModel(transferModel);
    dashBoardServiceFacade.insertRawDataProductRelation(model);
  }

  public void modifyRawDataProductRelation(TransferProductRelationData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductRelationModel model = this.getRawDataProductRelationModel(transferModel);
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("id", transferModel.getId());
    dashBoardServiceFacade.editRawDataProductRelationModel(cond, model);
  }

  public void deleteRawDataProductRelation(TransferProductRelationData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductRelationModel model = this.getRawDataProductRelationModel(transferModel);
    dashBoardServiceFacade.delRawDataProductRelationModel(model);
  }

  public void updateStudentFace(TransferStudentData transferModel) {
    if (transferModel == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    dashBoardServiceFacade.updateStudentFaceByUserId(transferModel.getUserId(),
        transferModel.getSourcePortrait(), transferModel.getClientType());
  }

  private RawDataProductCategoryModel getRawDataProductCategoryModel(
      TransferProductCategoryData transferModel) {
    RawDataProductCategoryModel model = new RawDataProductCategoryModel();
    model.setId(transferModel.getId());
    model.setAllChildId(transferModel.getAllChildId());
    model.setAllParentId(transferModel.getAllParentId());
    model.setCourseCategoryType(transferModel.getCourseCategoryType());
    model.setChildId(transferModel.getChildId());
    model.setCreateTime(transferModel.getCreateTime());
    model.setDetail(transferModel.getDetail());
    model.setIsLeaf(transferModel.getIsLeaf());
    model.setLevel(transferModel.getLevel());
    model.setMisc(transferModel.getMisc());
    model.setModule(transferModel.getModule());
    model.setName(transferModel.getName());
    model.setParentId(transferModel.getParentId());
    model.setShowStatus(transferModel.getShowStatus());
    model.setTypeCode(transferModel.getTypeCode());
    model.setClassify(transferModel.getClassify());
    model.setUpdateTime(transferModel.getUpdateTime());
    return model;
  }

  private RawDataProductModel getRawDataProductModel(TransferProductData transferModel) {
    RawDataProductModel model = new RawDataProductModel();
    model.setId(transferModel.getId());
    model.setBeginApplyTime(transferModel.getBeginApplyTime());
    model.setBriefInfo(transferModel.getBriefInfo());
    model.setCreateTime(transferModel.getCreateTime());
    model.setCurrApplyCount(transferModel.getCurrApplyCount());
    model.setDetail(transferModel.getDetail());
    model.setEndApplyTime(transferModel.getEndApplyTime());
    model.setImageUrl(transferModel.getImageUrl());
    model.setMisc(transferModel.getMisc());
    model.setName(transferModel.getName());
    model.setOriginalAmount(transferModel.getOriginalAmount());
    model.setPayAmount(transferModel.getPayAmount());
    model.setPraiseCount(transferModel.getPraiseCount());
    model.setQuestionBankSetting(transferModel.getQuestionBankSetting());
    model.setResourceSubject(transferModel.getResourceSubject());
    model.setShareUrl(transferModel.getShareUrl());
    model.setShowStatus(transferModel.getShowStatus());
    model.setTotalApplyCount(transferModel.getTotalApplyCount());
    model.setUpdateTime(transferModel.getUpdateTime());
    model.setCourseCode(transferModel.getCourseCode());
    model.setExamDate(transferModel.getExamDate());
    return model;
  }

  private RawDataProductRelationModel getRawDataProductRelationModel(
      TransferProductRelationData transferModel) {
    RawDataProductRelationModel model = new RawDataProductRelationModel();
    model.setId(transferModel.getId());
    model.setProductCategoryId(transferModel.getProductCategoryId());
    model.setProductId(transferModel.getProductId());
    model.setRelationState(transferModel.getRelationState());
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return model;
  }


  public void addRawDataScoreRule(TransferScoreRuleData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductScorePointRule model = this.getRawDataScoreRuleModel(transferModel);
    dashBoardServiceFacade.insertRawDataProductScorePointRule(model);
  }

  private RawDataProductScorePointRule getRawDataScoreRuleModel(TransferScoreRuleData transferModel) {
    RawDataProductScorePointRule entity = new RawDataProductScorePointRule();
    entity.setCreateTime(transferModel.getCreateTime());
    entity.setId(transferModel.getId());
    entity.setModifyTime(transferModel.getModifyTime());
    entity.setRuleDesc(transferModel.getRuleDesc());
    entity.setRuleDetail(transferModel.getRuleDetail());
    entity.setRuleName(transferModel.getRuleName());
    entity.setScope(transferModel.getScope());
    return entity;
  }

  public void modifyRawDataScoreRule(TransferScoreRuleData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    RawDataProductScorePointRule model = this.getRawDataScoreRuleModel(transferModel);
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("id", transferModel.getId());
    dashBoardServiceFacade.editRawDataProductScorePointRule(cond, model);
  }

  public Boolean deleteScoreRule(TransferScoreRuleData transferModel) {
    if (transferModel == null || transferModel.getId() == null) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return false;
    }
    RawDataProductScorePointRule model = this.getRawDataScoreRuleModel(transferModel);
    return dashBoardServiceFacade.delRawDataProductScorePointRule(model);
  }



}
