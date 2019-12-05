package com.xiaodou.st.dashboard.service.task.staticinfo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.validation.Errors;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.goeasy.GoEasy;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.http.AdmissionCardCodeRequest;
import com.xiaodou.st.dashboard.domain.http.AdmissionCardCodeRequest.AdmissionCardCodeRequestDTO;
import com.xiaodou.st.dashboard.domain.http.AdmissionCardCodeResponse;
import com.xiaodou.st.dashboard.domain.http.AdmissionCardCodeResponse.AdmissionCardCodeResponseDTO;
import com.xiaodou.st.dashboard.domain.http.Protocol;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncAdmissionCardCodeDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.http.FlowService;
import com.xiaodou.st.dashboard.util.HttpConfig;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.st.dashboard.util.SyncLogUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;


@HandlerMessage(value = "UpdateAndSaveAdmissionCardCode", type = MessageType.Single)
public class UpdateAndSaveAdmissionCardCode extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;
  public static GoEasy goEasy;
  static {
    if (null == goEasy) goEasy = new GoEasy(StaticInfoProp.goEasyCommonkey());
  }
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    // 加锁 TODO
    goEasy.publish("update_save_admissionCardCode_channel", "消息处理执行中...");
    if (StringUtils.isBlank(message.getMessageBodyJson())) return;
    IStServiceFacade stServiceFacade = SpringWebContextHolder.getBean("stServiceFacade");
    AdmissionCardCodeRequestDTO accrdto =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), AdmissionCardCodeRequestDTO.class);
    if(StringUtils.isBlank(accrdto.getAdmissionCardCode())) accrdto.setAdmissionCardCode(StringUtils.EMPTY);
    // 发送请求
    AdmissionCardCodeRequest request = new AdmissionCardCodeRequest();
    request.setListAdmissionCardCodeRequestDTO(Lists.newArrayList(accrdto));
    Errors error = request.validate();
    if (error.hasErrors()){
      goEasy.publish("update_save_admissionCardCode_channel", "消息处理执行失败!");
      return;
    }
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpConfig.quartzAdmissionCardCodeUrl());
    AdmissionCardCodeResponse response =
        FlowService.doFlow(request, AdmissionCardCodeResponse.class, protocol);
    List<AdmissionCardCodeResponseDTO> accrdtoList = response.getListAdmissionCardCodeResponse();
    String syncId = accrdto.getSyncId();
    if (null == accrdtoList || accrdtoList.isEmpty() || StringUtils.isEmpty(syncId)){
      goEasy.publish("update_save_admissionCardCode_channel", "ucenter包同步不成功!");
      return;
    } 
    // 更新学生(准考证号)状态
    for (AdmissionCardCodeResponseDTO accrdrto : accrdtoList) {
      SyncAdmissionCardCodeDO sccdo = new SyncAdmissionCardCodeDO();
      StudentDO studentDO = new StudentDO();
      studentDO.setId(accrdrto.getStudentId());
      studentDO.setUserId(accrdrto.getUserId());
      studentDO.setStudentStatus(accrdrto.getStudentStatus());
      studentDO.setAdmissionCardCode(null);
      stServiceFacade.updateStudent(studentDO);
      /** ************ */
      sccdo.setSyncId(syncId);
      sccdo.setStudentId(accrdrto.getStudentId());
      sccdo.setTelephone(accrdrto.getTelephone());
      sccdo.setCreateTime(new Timestamp(System.currentTimeMillis()));
      /* 学生状态 0、未注册，1、注册成功，2、注册失败，已经存在该学生 ，3、注册异常，4、成功导入 */
      String msg = "";
      switch (accrdrto.getStudentStatus()) {
        case Constants.SUCCESS_REGISTER:
          msg = "当前状态：注册成功（更新业务库准考证号未成功）";
          break;
        case Constants.SUCCESS_IMPORT:
          msg = "当前状态：成功导入";
          if(StringUtils.isBlank(accrdto.getAdmissionCardCode()))
            msg += "，将空的准考证号覆盖到业务数据库中";
          break;
        default:
          break;
      }
      sccdo.setMsg(msg);
      stServiceFacade.saveSyncCard(sccdo);
      SyncLogUtil.incrCompleteCount(syncId);
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("学生(准考证号)状态 信息 异步更新 失败", t);
  }
}
