package com.xiaodou.st.dashboard.service.http;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.goeasy.GoEasy;
import com.xiaodou.queue.client.AbstractMQClient.MessageBox;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.constants.enums.SyncResult;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.http.AdmissionCardCodeRequest.AdmissionCardCodeRequestDTO;
import com.xiaodou.st.dashboard.domain.http.ApplyRequest.ApplyRequestDTO;
import com.xiaodou.st.dashboard.domain.http.StudentRequest.StudentRequestDTO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductCategoryDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncLogDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.prop.StandardProp;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.message.QueueService;
import com.xiaodou.st.dashboard.service.message.QueueService.Message;
import com.xiaodou.st.dashboard.util.IDGenerator;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.st.dashboard.util.SyncLogUtil;
import com.xiaodou.summer.dao.pagination.Page;


@Service("httpService")
public class HttpService extends BaseDashboardService {

  @Resource
  IStServiceFacade stServiceFacade;

  @Resource
  QueueService queueService;
  public static GoEasy goEasy;
  static {
    if (null == goEasy) goEasy = new GoEasy(StaticInfoProp.goEasyCommonkey());
  }

  // 定时执行添加学生
  public String quartzStudent() {
    goEasy.publish("update_save_student_channel", "执行中...");
    List<StudentRequestDTO> srdList = Lists.newArrayList();
    Map<String, Object> inputs = Maps.newHashMap();
    // inputs.put("studentStatus", Constants.NOT_REGISTER);
    inputs.put("studentStatusList",
        Lists.newArrayList(Constants.NOT_REGISTER, Constants.ERROR_REGISTER));
    Page<StudentDO> page =
        stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), null);
    if (null == page || null == page.getResult() || page.getResult().size() < 1)
      return "未检测到可以同步的数据，请稍后再试。";
    // 初始化专业Map
    Map<Integer, String> catMap = packageCatMap();
    Map<Integer, Integer> applyMap = packageApplyMap(page.getResult());
    for (StudentDO sdo : page.getResult()) {
      Integer catId = applyMap.get(sdo.getId());
      if (null == catId) continue;
      StudentRequestDTO srqd = new StudentRequestDTO();
      String typeCode = catMap.get(catId);
      if (StringUtils.isBlank(typeCode)) continue;
      srqd.setRegionId(StandardProp.getRegionId());
      srqd.setRegionName(StandardProp.getRegionId());
      srqd.setTypeCode(typeCode);
      srqd.setStudentId(sdo.getId());
      srqd.setAdmissionCardCode(sdo.getAdmissionCardCode());
      srqd.setGender(sdo.getGender());
      srqd.setIdentificationCardCode(sdo.getIdentificationCardCode());
      srqd.setPilotUnitName(sdo.getPilotUnitName());
      srqd.setRealName(sdo.getRealName());
      srqd.setTelephone(sdo.getTelephone());
      srdList.add(srqd);
    }
    if (null == srdList || srdList.isEmpty()) return "未检测到可以同步的数据，请稍后再试。";
    return this.updateStudent(srdList);
  }

  private Map<Integer, String> packageCatMap() {
    Map<Integer, String> catMap = Maps.newHashMap();
    Map<String, Object> output = Maps.newHashMap();
    output.put("id", StringUtils.EMPTY);
    output.put("typeCode", StringUtils.EMPTY);
    Page<RawDataProductCategoryDO> rdoPage =
        stServiceFacade.listRawDataProductCategory(null, output);
    if (null == rdoPage || rdoPage.getResult() == null || rdoPage.getResult().size() == 0)
      return catMap;
    for (RawDataProductCategoryDO rdo : rdoPage.getResult())
      catMap.put(rdo.getId(), rdo.getTypeCode());
    return catMap;
  }

  private Map<Integer, Integer> packageApplyMap(List<StudentDO> stoList) {
    Map<Integer, Integer> applyMap = Maps.newHashMap();
    List<Integer> studentIdList = Lists.newArrayList();
    for (StudentDO sto : stoList) {
      studentIdList.add(sto.getId());
    }
    Map<String, Object> input = Maps.newHashMap();
    if (null != studentIdList && studentIdList.size() > 0)
      input.put("studentIdList", studentIdList);
    Page<ApplyDO> applyPage =
        stServiceFacade.listApply(input, CommUtil.getAllField(ApplyDO.class), null);
    if (null == applyPage || applyPage.getResult() == null || applyPage.getResult().size() == 0)
      return applyMap;
    for (ApplyDO apply : applyPage.getResult())
      applyMap.put(apply.getStudentId(), apply.getCatId());
    return applyMap;
  }

  // 更改学生状态
  public String updateStudent(List<StudentRequestDTO> srdList) {
    SyncLogDO syncLogDO = new SyncLogDO();
    String syncId = IDGenerator.getSeqID();
    syncLogDO.setSyncId(syncId);
    AdminUser adminUser = super.getAdminUser();
    syncLogDO.setSyncAdminId(adminUser.getId());
    syncLogDO.setSyncAdminName(adminUser.getRealName());
    syncLogDO.setSyncType(Constants.SYNC_STUDENT);
    syncLogDO.setSyncTime(new Timestamp(System.currentTimeMillis()));
    syncLogDO.setSyncResult(SyncResult.SyncWait.getCode());
    syncLogDO.setSyncResultMsg(SyncResult.SyncWait.getDesc());
    boolean flag = stServiceFacade.saveSyncLog(syncLogDO);
    SyncLogUtil.setTotalCount(syncId, srdList.size());
    MessageBox mesBox = new MessageBox();
    for (StudentRequestDTO srd : srdList) {
      srd.setSyncId(syncId);
      mesBox.addCurrentLevelMessage(Message.UpdateAndSaveStudent.name(), srd);
    }
    mesBox.addNextLevelMessage(Message.SyncFinish.name(), syncLogDO);
    queueService.pushMessageBox(mesBox);
    if (flag) {
      SyncLogUtil.lastSyncLogCacheMap.put(Constants.SYNC_STUDENT, syncLogDO);
    }
    return String.valueOf(flag);
  }

  // 定时执行添加报名课程
  public String quartzApply() {
    goEasy.publish("update_save_apply_channel", "执行中...");
    List<ApplyRequestDTO> arqList = Lists.newArrayList();
    Map<String, Object> inputs = Maps.newHashMap();
    // 后台报名
    inputs.put("applyStatus", Constants.APPLY_SUCCESS);
    // 已缴费
    inputs.put("orderStatus", Constants.ALREADYPAYMENT);
    // inputs.put("studentId", 11072);
    Map<String, Object> outputs = Maps.newHashMap();
    outputs.put("userId", "1");
    outputs.put("productId", "1");
    outputs.put("studentId", "1");
    Page<ApplyDO> page = stServiceFacade.listApplyAndStudent(inputs, outputs);
    if (null == page || null == page.getResult() || page.getResult().size() < 1)
      return "未检测到可以同步的数据，请稍后再试。";
    for (ApplyDO applyDO : page.getResult()) {
      ApplyRequestDTO arq = new ApplyRequestDTO();
      if (null == applyDO || null == applyDO.getUserId() || null == applyDO.getProductId()
          || null == applyDO.getStudentId()) continue;
      arq.setUserId(applyDO.getUserId());
      arq.setProductId(applyDO.getProductId());
      arq.setStudentId(applyDO.getStudentId());
      arqList.add(arq);
    }
    if (null == arqList || arqList.isEmpty()) return "未检测到可以同步的数据，请稍后再试。";
    return this.updateApply(arqList);
  }

  public String updateApply(List<ApplyRequestDTO> arqList) {
    SyncLogDO syncLogDO = new SyncLogDO();
    String syncId = IDGenerator.getSeqID();
    syncLogDO.setSyncId(syncId);
    AdminUser adminUser = super.getAdminUser();
    syncLogDO.setSyncAdminId(adminUser.getId());
    syncLogDO.setSyncAdminName(adminUser.getRealName());
    syncLogDO.setSyncType(Constants.SYNC_APPLY);
    syncLogDO.setSyncTime(new Timestamp(System.currentTimeMillis()));
    syncLogDO.setSyncResult(SyncResult.SyncWait.getCode());
    syncLogDO.setSyncResultMsg(SyncResult.SyncWait.getDesc());
    boolean flag = stServiceFacade.saveSyncLog(syncLogDO);
    SyncLogUtil.setTotalCount(syncId, arqList.size());
    MessageBox mesBox = new MessageBox();
    for (ApplyRequestDTO arq : arqList) {
      arq.setSyncId(syncId);
      mesBox.addCurrentLevelMessage(Message.UpdateAndSaveApply.name(), arq);
    }
    mesBox.addNextLevelMessage(Message.SyncFinish.name(), syncLogDO);
    queueService.pushMessageBox(mesBox);
    if (flag) {
      SyncLogUtil.lastSyncLogCacheMap.put(Constants.SYNC_APPLY, syncLogDO);
    }
    return String.valueOf(flag);

  }

  // 同步准考证号
  public String quartzAdminssionCardCode() {
    goEasy.publish("update_save_admissionCardCode_channel", "执行中...");
    Map<String, Object> inputs = Maps.newHashMap();
    // 注册成功的用户
    inputs.put("studentStatus", Constants.SUCCESS_REGISTER);
    Page<StudentDO> page =
        stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), null);
    if (null == page || null == page.getResult() || page.getResult().size() < 1)
      return "未检测到可以同步的数据，请稍后再试。";
    List<AdmissionCardCodeRequestDTO> accrdtoList = Lists.newArrayList();
    for (StudentDO sdo : page.getResult()) {
      if (null == sdo || null == sdo.getId() || null == sdo.getUserId()
      // || StringUtils.isBlank(sdo.getAdmissionCardCode())
          || StringUtils.isBlank(sdo.getTelephone())) continue;
      AdmissionCardCodeRequestDTO accrdto = new AdmissionCardCodeRequestDTO();
      accrdto.setStudentId(sdo.getId());
      accrdto.setUserId(sdo.getUserId());
      accrdto.setAdmissionCardCode(sdo.getAdmissionCardCode());
      accrdto.setTelephone(sdo.getTelephone());
      accrdtoList.add(accrdto);
    }
    if (null == accrdtoList || accrdtoList.isEmpty()) return "未检测到可以同步的数据，请稍后再试。";
    return this.updateAdmissionCardCode(accrdtoList);
  }

  // 更改学生(准考证号)状态
  public String updateAdmissionCardCode(List<AdmissionCardCodeRequestDTO> accrdtoList) {
    SyncLogDO syncLogDO = new SyncLogDO();
    String syncId = IDGenerator.getSeqID();
    syncLogDO.setSyncId(syncId);
    AdminUser adminUser = super.getAdminUser();
    syncLogDO.setSyncAdminId(adminUser.getId());
    syncLogDO.setSyncAdminName(adminUser.getRealName());
    syncLogDO.setSyncType(Constants.SYNC_CARD);
    syncLogDO.setSyncTime(new Timestamp(System.currentTimeMillis()));
    syncLogDO.setSyncResult(SyncResult.SyncWait.getCode());
    syncLogDO.setSyncResultMsg(SyncResult.SyncWait.getDesc());
    boolean flag = stServiceFacade.saveSyncLog(syncLogDO);
    SyncLogUtil.setTotalCount(syncId, accrdtoList.size());
    MessageBox mesBox = new MessageBox();
    for (AdmissionCardCodeRequestDTO accrdto : accrdtoList) {
      accrdto.setSyncId(syncId);
      mesBox.addCurrentLevelMessage(Message.UpdateAndSaveAdmissionCardCode.name(), accrdto);
    }
    mesBox.addNextLevelMessage(Message.SyncFinish.name(), syncLogDO);
    queueService.pushMessageBox(mesBox);
    if (flag) {
      SyncLogUtil.lastSyncLogCacheMap.put(Constants.SYNC_CARD, syncLogDO);
    }
    return String.valueOf(flag);
  }

}
