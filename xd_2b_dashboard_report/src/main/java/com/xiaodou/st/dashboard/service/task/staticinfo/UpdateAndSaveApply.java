package com.xiaodou.st.dashboard.service.task.staticinfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.http.ApplyRequest;
import com.xiaodou.st.dashboard.domain.http.ApplyRequest.ApplyRequestDTO;
import com.xiaodou.st.dashboard.domain.http.ApplyResponse;
import com.xiaodou.st.dashboard.domain.http.ApplyResponse.ApplyResponseDTO;
import com.xiaodou.st.dashboard.domain.http.Protocol;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncApplyDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.http.FlowService;
import com.xiaodou.st.dashboard.service.task.StudentTask;
import com.xiaodou.st.dashboard.util.HttpConfig;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.st.dashboard.util.SyncLogUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "UpdateAndSaveApply", type = MessageType.Single)
public class UpdateAndSaveApply extends AbstractDefaultWorker {
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
    goEasy.publish("update_save_apply_channel", "消息处理执行中...");
    if (StringUtils.isBlank(message.getMessageBodyJson())) return;
    IStServiceFacade stServiceFacade = SpringWebContextHolder.getBean("stServiceFacade");
    StudentTask studentTask = SpringWebContextHolder.getBean("studentTask");
    ApplyRequestDTO arq =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), ApplyRequestDTO.class);
    ApplyRequest request = new ApplyRequest();
    request.setListApplyRequest(Lists.newArrayList(arq));
    Errors error = request.validate();
    if (error.hasErrors()){
      goEasy.publish("update_save_apply_channel", "消息处理执行失败!");
      return;
    }
    Protocol protocol = new Protocol(Protocol.POST);
    protocol.setUrl(HttpConfig.quartzApplyUrl());
    ApplyResponse response = FlowService.doFlow(request, ApplyResponse.class, protocol);
    List<ApplyResponseDTO> ardtoList = response.getListApplyResponse();
    String syncId = arq.getSyncId();
    if (null == ardtoList || ardtoList.isEmpty() || StringUtils.isEmpty(syncId)){
      goEasy.publish("update_save_apply_channel", "course包同步不成功!");
      return;
    } 
    // 更新报名状态
    List<ApplyDO> applyList = Lists.newArrayList();
    List<SyncApplyDO> syncApplyList = Lists.newArrayList();
    for (ApplyResponseDTO ardto : ardtoList) {
      Map<String,Object> inputs = Maps.newHashMap();
      inputs.put("studentId", ardto.getStudentId());
      inputs.put("productId", ardto.getProductId());
      ApplyDO applyDO = new ApplyDO();
      applyDO.setApplyStatus(ardto.getApplyStatus());
      stServiceFacade.updateApplyByCond(inputs, applyDO);
      applyList.add(applyDO);
      /** ************ */
      SyncApplyDO sado = new SyncApplyDO();
      sado.setSyncId(syncId);
      sado.setStudentId(ardto.getStudentId());
      sado.setTelephone(ardto.getTelephone());
      sado.setCreateTime(new Timestamp(System.currentTimeMillis()));
      String msg = "";
      /* 报名状态（0、后台报名完成1、业务系统报名成功2、已经购买该课程3、报名异常） */
      switch (ardto.getApplyStatus()) {
        case Constants.APPLY_SUCCESS:
          msg = "当前状态：后台报名完成（业务系统报名未成功）";
          break;
        case Constants.BUSINESS_APPLY_SUCCESS:
          msg = "当前状态：业务系统报名成功";
          break;
        case Constants.BUSINESS_APPLY_ALREADY:
          msg = "当前状态：已经购买该课程";
          break;
        case Constants.BUSINESS_APPLY_ERROR:
          msg = "当前状态：报名异常";
          break;
        default:
          break;
      }
      sado.setMsg(msg);
      stServiceFacade.saveSyncApply(sado);
      syncApplyList.add(sado);
    }
    // 更新学生数
    studentTask.updateStudentCount();
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
    LoggerUtil.error("添加报名课程信息 异步更新 失败", t);
  }
}
