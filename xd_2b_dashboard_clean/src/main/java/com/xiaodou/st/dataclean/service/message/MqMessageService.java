package com.xiaodou.st.dataclean.service.message;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.st.dataclean.constant.Constant;
import com.xiaodou.st.dataclean.exception.DcMsgConsuFailException;
import com.xiaodou.st.dataclean.model.domain.dashboard.score.ScoreDO;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFaceRecognitionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.model.transport.TransferFaceRecognitionData;
import com.xiaodou.st.dataclean.model.transport.TransferFinishAllMissionData;
import com.xiaodou.st.dataclean.model.transport.TransferFinishMissionData;
import com.xiaodou.st.dataclean.model.transport.TransferProductCategoryData;
import com.xiaodou.st.dataclean.model.transport.TransferProductData;
import com.xiaodou.st.dataclean.model.transport.TransferProductRelationData;
import com.xiaodou.st.dataclean.model.transport.TransferScoreRuleData;
import com.xiaodou.st.dataclean.model.transport.TransferStudentData;
import com.xiaodou.st.dataclean.model.transport.TransferUserExamTotalData;
import com.xiaodou.st.dataclean.model.transport.TransferUserLearnRecordData;
import com.xiaodou.st.dataclean.service.QueueService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.service.raw.RawDataCleanService;
import com.xiaodou.st.dataclean.util.ErrorsWrapper;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.summer.web.BaseController;

@Service("mqMessageService")
public class MqMessageService extends BaseController {
  /**
   * 通用订单service
   */
  @Resource
  QueueService queueService;

  @Resource
  RawDataCleanService rawDataCleanService;

  public MessageRemoteResult routeMessage(JMSGPojo pojo) throws Exception {
    MessageRemoteResult result = new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.eventBodyNull());
      return null;
    }
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || StringUtils.isBlank(event.getString("eventName"))) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.unknowEventType());
      return null;
    }
    Method method = MqMessageService.class.getMethod(event.getString("eventName"), JMSGPojo.class);
    method.invoke(this, pojo);
    return result;
  }

  /**
   * 用户总分事件
   */
  public void userExamTotalEvent(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferUserExamTotalData transferModel =
        event.getObject("dataModel", TransferUserExamTotalData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    RawDataExamTotalModel rdet = rawDataCleanService.cleanRawDataExamTotal(transferModel);
    if (null == rdet) {
      return;
    }
    String chiefPilotCat =
        String.format("%s:%s:%s:%s:%s", rdet.getTaughtUnitId().toString(), rdet.getChiefUnitId()
            .toString(), rdet.getPilotUnitId().toString(), rdet.getProductCategoryId(), rdet
            .getProductId());
    Constant.UserExamTotalMap.put(chiefPilotCat, rdet);
    queueService.pushExamTotal(rdet);
    /*************/
    ScoreDO scoreDO = ScoreDO.getInstance(rdet);
    if (null != scoreDO) {
      // 试点单位
      scoreDO.setRoleType(rdet.getPioltUnit().getRole());
      scoreDO.setUnitId(rdet.getPioltUnit().getId());
      this.updateOrAddScore(scoreDO);
      scoreDO.setId(null);
      // 主考院校
      scoreDO.setRoleType(rdet.getCheifUnit().getRole());
      scoreDO.setUnitId(rdet.getCheifUnit().getId());
      this.updateOrAddScore(scoreDO);
      scoreDO.setId(null);
      // 自考办
      scoreDO.setRoleType(rdet.getTaughtUnit().getRole());
      scoreDO.setUnitId(rdet.getTaughtUnit().getId());
      this.updateOrAddScore(scoreDO);
    }
  }

  public void updateOrAddScore(ScoreDO scoreDO) {
    DashBoardServiceFacade dashBoardServiceFacade =
        SpringWebContextHolder.getBean("dashBoardServiceFacade");
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("productId", scoreDO.getProductId());
    inputs.put("studentId", scoreDO.getStudentId());
    inputs.put("roleType", scoreDO.getRoleType());
    inputs.put("unitId", scoreDO.getUnitId());
    Page<ScoreDO> page =
        dashBoardServiceFacade.listScore(inputs, CommUtil.getAllField(ScoreDO.class));
    if (null != page && null != page.getResult() && !page.getResult().isEmpty()
        && null != page.getResult().get(0) && null != page.getResult().get(0).getId()) {
      inputs.clear();
      inputs.put("id", page.getResult().get(0).getId());
      dashBoardServiceFacade.updateScoreByCond(inputs, scoreDO).toString();
    } else {
      scoreDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
      dashBoardServiceFacade.saveScore(scoreDO);
    }
  }

  /**
   * 用户学习记录事件
   */
  public void userLearnRecordEvent(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferUserLearnRecordData transferModel =
        event.getObject("dataModel", TransferUserLearnRecordData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    RawDataLearnRecordModel rawData = rawDataCleanService.cleanRawDataLearnRecord(transferModel);
    if (null == rawData) return;
    String chiefPilotCat =
        String.format("%s:%s:%s:%s:%s", rawData.getTaughtUnitId().toString(), rawData
            .getChiefUnitId().toString(), rawData.getPilotUnitId().toString(), rawData
            .getProductCategoryId(), rawData.getRecordTime());
    Constant.UserLearnMap.put(chiefPilotCat, rawData);
    queueService.pushLearnRecord(rawData);
  }

  /**
   * 面部识别认证事件
   */
  public void faceRecognitionEvent(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferFaceRecognitionData transferModel =
        event.getObject("dataModel", TransferFaceRecognitionData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    RawDataFaceRecognitionModel rawData =
        rawDataCleanService.cleanRawDataFaceRecognition(transferModel);
    if (null == rawData) return;
    queueService.pushFaceRecognition(rawData);
  }

  /**
   * 完成任务事件
   */
  public void finishMissionEvent(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferFinishMissionData transferModel =
        event.getObject("dataModel", TransferFinishMissionData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    RawDataFinishMissionModel rawData =
        rawDataCleanService.cleanRawDataFinishMission(transferModel);
    if (null == rawData) return;
    String chiefPilotCat =
        String.format("%s:%s:%s:%s:%s", rawData.getTaughtUnitId().toString(), rawData
            .getChiefUnitId().toString(), rawData.getPilotUnitId().toString(), rawData
            .getProductCategoryId(), rawData.getRecordTime());
    Constant.FinishMissionMap.put(chiefPilotCat, rawData);
  }

  /**
   * 完成全部任务事件
   */
  public void finishAllMissionEvent(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferFinishAllMissionData transferModel =
        event.getObject("dataModel", TransferFinishAllMissionData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.cleanRawDataFinishAllMission(transferModel);
  }

  /**
   * 添加专业事件
   */
  public void addProductCategory(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferProductCategoryData transferModel =
        event.getObject("dataModel", TransferProductCategoryData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.addRawDataProductCategory(transferModel);
  }

  /**
   * 修改专业事件
   */
  public void modifyProductCategory(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferProductCategoryData transferModel =
        event.getObject("dataModel", TransferProductCategoryData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.modifyRawDataProductCategory(transferModel);
  }

  /**
   * 删除专业事件
   */
  public void deleteProductCategory(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferProductCategoryData transferModel =
        event.getObject("dataModel", TransferProductCategoryData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.deleteRawDataProductCategory(transferModel);
  }

  /**
   * 添加产品事件
   */
  public void addProduct(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferProductData transferModel = event.getObject("dataModel", TransferProductData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.addRawDataProduct(transferModel);
  }

  /**
   * 修改产品事件
   */
  public void modifyProduct(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferProductData transferModel = event.getObject("dataModel", TransferProductData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.modifyRawDataProduct(transferModel);
  }

  /**
   * 删除产品事件
   */
  public void deleteProduct(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferProductData transferModel = event.getObject("dataModel", TransferProductData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.deleteRawDataProduct(transferModel);
  }

  /**
   * 添加专业产品关系事件
   */
  public void addProductRelation(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferProductRelationData transferModel =
        event.getObject("dataModel", TransferProductRelationData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.addRawDataProductRelation(transferModel);
  }

  /**
   * 修改专业产品关系事件
   */
  public void modifyProductRelation(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferProductRelationData transferModel =
        event.getObject("dataModel", TransferProductRelationData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.modifyRawDataProductRelation(transferModel);
  }

  /**
   * 删除专业产品关系事件
   */
  public void deleteProductRelation(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferProductRelationData transferModel =
        event.getObject("dataModel", TransferProductRelationData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.deleteRawDataProductRelation(transferModel);
  }

  /**
   * 更新用户头像
   */
  public void updateStudentFace(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferStudentData transferModel = event.getObject("dataModel", TransferStudentData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.updateStudentFace(transferModel);
  }



  /**
   * 添加计分规则
   */
  public void addScoreRule(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferScoreRuleData transferModel = event.getObject("dataModel", TransferScoreRuleData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.addRawDataScoreRule(transferModel);
  }


  /**
   * 修改计分规则
   */
  public void modifyScoreRule(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferScoreRuleData transferModel = event.getObject("dataModel", TransferScoreRuleData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.modifyRawDataScoreRule(transferModel);
  }



  /**
   * 删除产品事件
   */
  public void deleteScoreRule(JMSGPojo pojo) {
    JSONObject event = JSON.parseObject(pojo.getMessage());
    if (null == event || null == event.get("dataModel")) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.srcRecordNull());
      return;
    }
    TransferScoreRuleData transferModel = event.getObject("dataModel", TransferScoreRuleData.class);
    Errors error = transferModel.validate();
    if (error.hasErrors()) {
      ErrorsWrapper.getWrapper().setValue(DcMsgConsuFailException.coreParamNull(getErrMsg(error)));
      return;
    }
    rawDataCleanService.deleteScoreRule(transferModel);
  }


}
