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
import com.xiaodou.st.dashboard.domain.http.Protocol;
import com.xiaodou.st.dashboard.domain.http.StudentRequest;
import com.xiaodou.st.dashboard.domain.http.StudentRequest.StudentRequestDTO;
import com.xiaodou.st.dashboard.domain.http.StudentResponse;
import com.xiaodou.st.dashboard.domain.http.StudentResponse.StudentQueueMessageDTO;
import com.xiaodou.st.dashboard.domain.http.StudentResponse.StudentResponseDTO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncStudentDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.http.FlowService;
import com.xiaodou.st.dashboard.util.HttpConfig;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.st.dashboard.util.SyncLogUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UpdateAndSaveStudent", type = MessageType.Single)
public class UpdateAndSaveStudent extends AbstractDefaultWorker {
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
    // 发送页面消息
    goEasy.publish("update_save_student_channel", "消息处理执行中...");
    if (StringUtils.isBlank(message.getMessageBodyJson())) return;
    IStServiceFacade stServiceFacade = SpringWebContextHolder.getBean("stServiceFacade");
    StudentRequestDTO srd =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), StudentRequestDTO.class);
    // 发送请求
    StudentRequest request = new StudentRequest();
    request.setListStudentRequest(Lists.newArrayList(srd));
    Errors error = request.validate();
    if (error.hasErrors()) {
      goEasy.publish("update_save_student_channel", "消息处理执行失败!");
      return;
    }
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpConfig.quartzStudentUrl());
    StudentResponse response = FlowService.doFlow(request, StudentResponse.class, protocol);
    if (null == response || !response.isRetOk()) {
      // 发送页面消息
      goEasy.publish("update_save_student_channel", "消息处理执行失败，请检查业务服务器!");
      return;
    }
    // 更新学生状态
    StudentQueueMessageDTO sqmdto = new StudentQueueMessageDTO();
    sqmdto.setListStudentResponseDTO(response.getListStudentResponse());
    sqmdto.setSyncId(srd.getSyncId());
    List<StudentResponseDTO> stdList = sqmdto.getListStudentResponseDTO();
    String syncId = sqmdto.getSyncId();
    if (null == stdList || stdList.isEmpty() || StringUtils.isEmpty(syncId)) {
      goEasy.publish("update_save_student_channel", "ucenter包同步不成功!");
      return;
    }
    // 更新学生状态
    for (StudentResponseDTO srpd : stdList) {
      if (null == srpd || null == srpd.getStudentId()) continue;
      // if(null == srpd.getUserId()) continue;
      StudentDO studentDO = new StudentDO();
      studentDO.setId(srpd.getStudentId());
      studentDO.setUserId(srpd.getUserId());
      studentDO.setStudentStatus(srpd.getStudentStatus());
      studentDO.setAdmissionCardCode(null);
      stServiceFacade.updateStudent(studentDO);
      /** ************ */
      SyncStudentDO ssdo = new SyncStudentDO();
      ssdo.setStudentId(srpd.getStudentId());
      ssdo.setSyncId(syncId);
      ssdo.setTelephone(srpd.getTelephone());
      ssdo.setCreateTime(new Timestamp(System.currentTimeMillis()));
      /* 学生状态 0、未注册，1、注册成功，2、注册失败，已经存在该课程 ，3、注册异常，4、成功导入 */
      String msg = "";
      switch (srpd.getStudentStatus()) {
        case Constants.NOT_REGISTER:
          msg = "当前状态：未注册";
          break;
        case Constants.SUCCESS_REGISTER:
          msg = "当前状态：注册成功";
          break;
        case Constants.FAIL_REGISTER:
          msg = "当前状态：注册失败， 用户已存在";
          break;
        case Constants.ERROR_REGISTER:
          msg = "当前状态：注册异常";
          break;
        case Constants.SUCCESS_IMPORT:
          msg = "当前状态：成功导入";
          break;
        default:
          break;
      }
      ssdo.setMsg(msg);
      stServiceFacade.saveSyncStudent(ssdo);
    }
    SyncLogUtil.incrCompleteCount(syncId);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("学生状态 信息 异步更新 失败", t);
  }
}
