package com.xiaodou.st.dashboard.service.message;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.Data;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.constants.enums.AlarmLevelEnum;
import com.xiaodou.st.dashboard.constants.enums.AlarmTypeEnum;
import com.xiaodou.st.dashboard.constants.enums.PretreatmentEnum;
import com.xiaodou.st.dashboard.constants.enums.StatusEnum;
import com.xiaodou.st.dashboard.domain.alarm.AlarmRecordDO;
import com.xiaodou.st.dashboard.domain.alarm.LoginInfoDO;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.message.ApplyCallBackMessageBody;
import com.xiaodou.st.dashboard.domain.message.ApplyCallBackMessageBody.ApplyCallBackMessageBodyDTO;
import com.xiaodou.st.dashboard.domain.message.ApplyMessage;
import com.xiaodou.st.dashboard.domain.message.ApplyMessage.ApplyMessageBody;
import com.xiaodou.st.dashboard.domain.message.ApplyMessage.ApplyMessageBodyDTO;
import com.xiaodou.st.dashboard.domain.message.StudentCallBackMessageBody;
import com.xiaodou.st.dashboard.domain.message.StudentCallBackMessageBody.StudentCallBackMessageBodyDTO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.alarm.AlarmService;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.task.StudentTask;
import com.xiaodou.st.dashboard.util.LockFactory;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * 收消息
 * 
 * @name MqMessageService CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年4月20日
 * @description TODO
 * @version 1.0
 */
@Service("mqMessageService")
public class MqMessageService {

  @Resource
  IStServiceFacade stServiceFacade;

  @Resource
  StudentTask studentTask;

  @Resource
  AlarmService alarmService;

  /**
   * @deprecated
   * @description 教师端后台，定时任务
   * @author 李德洪
   * @Date 2017年4月19日
   * @param pojo
   * @return
   */
  public MessageRemoteResult studentSchedulerCallBack(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    StudentCallBackMessageBody messageBody =
        FastJsonUtil.fromJson(pojo.getMessage(), StudentCallBackMessageBody.class);
    try {
      for (StudentCallBackMessageBodyDTO stm : messageBody.getMessageBody()) {
        StudentDO studentDO = new StudentDO();
        studentDO.setId(stm.getStudentId());
        studentDO.setUserId(stm.getUserId());
        studentDO.setStudentStatus(stm.getStudentStatus());
        stServiceFacade.updateStudent(studentDO);
      }
    } catch (Exception e) {
      LoggerUtil.error("导入用户异常", e);
      messageRemoteResult.setExceptionMessgage("导入用户异常。");
      return messageRemoteResult;
    } finally {
      // 发消息
      this.sendApplyMessage();
    }
    return messageRemoteResult;
  }

  /**
   * @deprecated
   * @description TODO
   * @author 李德洪
   * @Date 2017年5月10日
   */
  public void sendApplyMessage() {
    List<ApplyMessageBodyDTO> ambList = Lists.newArrayList();
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("applyStatus", Constants.APPLY_SUCCESS);
    inputs.put("studentId", 303);
    Map<String, Object> outputs = Maps.newHashMap();
    outputs.put("userId", "1");
    outputs.put("productId", "1");
    Page<ApplyDO> applyPage = stServiceFacade.listApplyAndStudent(inputs, outputs);
    if (null != applyPage && null != applyPage.getResult() && applyPage.getResult().size() > 0) {
      for (ApplyDO applyDO : applyPage.getResult()) {
        ApplyMessageBodyDTO amb = new ApplyMessageBodyDTO();
        amb.setUserId(applyDO.getUserId());
        amb.setProductId(applyDO.getProductId());
        amb.setStudentId(applyDO.getStudentId());
        ambList.add(amb);
      }
    }
    ApplyMessageBody messageBody = new ApplyMessageBody();
    messageBody.setMessageBody(ambList);
    RabbitMQSender.getInstance().send(new ApplyMessage(messageBody));
  }

  /**
   * @deprecated
   * @description TODO
   * @author 李德洪
   * @Date 2017年5月10日
   * @param pojo
   * @return
   */
  public MessageRemoteResult applySchedulerCallBack(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    ApplyCallBackMessageBody messageBody =
        FastJsonUtil.fromJson(pojo.getMessage(), ApplyCallBackMessageBody.class);
    try {
      for (ApplyCallBackMessageBodyDTO acbm : messageBody.getMessageBody()) {
        Map<String, Object> inputs = Maps.newHashMap();
        inputs.put("productId", acbm.getProductId());
        inputs.put("studentId", acbm.getStudentId());
        ApplyDO applyDO = new ApplyDO();
        applyDO.setApplyStatus(Constants.BUSINESS_APPLY_SUCCESS);
        stServiceFacade.updateApplyByCond(inputs, applyDO);
        // 更新学生数
        studentTask.updateStudentCount();
      }
    } catch (Exception e) {
      LoggerUtil.error("更改报名状态异常", e);
      messageRemoteResult.setExceptionMessgage("更改报名状态异常。");
      return messageRemoteResult;
    }
    return messageRemoteResult;
  }

  public MessageRemoteResult loginInfo(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    LoginInfoDTO lidto = FastJsonUtil.fromJson(pojo.getMessage(), LoginInfoDTO.class);
    try {
      LockFactory.getLoginInfoDoor();
      LoginInfoDO lido = this.getARDOInstance(lidto);
      if (null == lido) {
        messageRemoteResult.setExceptionMessgage("用户数据没有统一（业务中心的用户在云测评中不存在）。");
        return messageRemoteResult;
      }
      stServiceFacade.saveLoginInfo(lido);
    } catch (Exception e) {
      LoggerUtil.error("添加登录记录异常", e);
      messageRemoteResult.setExceptionMessgage("添加登录记录异常。");
      return messageRemoteResult;
    }finally {
      LockFactory.returnLoginInfoDoor();
    }
    return messageRemoteResult;
  }

  private Integer getStudentId(Long userId) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    Page<StudentDO> page =
        stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), null);
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return null;
    StudentDO student = page.getResult().get(0);
    return student.getId();
  }


  public MessageRemoteResult alarmRecord(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    AlarmRecordDTO ardto = FastJsonUtil.fromJson(pojo.getMessage(), AlarmRecordDTO.class);
    try {
      LockFactory.getAlarmInfoDoor();
      if (null == ardto) return messageRemoteResult;
      Map<String, Object> inputs = Maps.newHashMap();
      inputs.put("userId", ardto.getUserId());
      Page<StudentDO> page =
          stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), null);
      if (null == page || null == page.getResult() || page.getResult().isEmpty())
        return messageRemoteResult;
      AlarmRecordDO ardo = this.getARDOInstance(ardto);
      if (null != ardo) alarmService.saveAlarmRecord(ardo);
    } catch (Exception e) {
      LoggerUtil.error("添加登录报警记录异常", e);
      messageRemoteResult.setExceptionMessgage("添加登录报警记录异常。");
      return messageRemoteResult;
    }finally {
      LockFactory.returnAlarmInfoDoor();
    }
    return messageRemoteResult;
  }

  public MessageRemoteResult sendAlarmWebMessage(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    AlarmRecordDO ardo = FastJsonUtil.fromJson(pojo.getMessage(), AlarmRecordDO.class);
    try {
      if (null != ardo) alarmService.sendWebMessage(ardo.getRoleType(), ardo.getUnitId());
    } catch (Exception e) {
      LoggerUtil.error("发送web消息异常", e);
      messageRemoteResult.setExceptionMessgage("发送web消息异常。");
      return messageRemoteResult;
    }
    return messageRemoteResult;
  }

  @Data
  public static class AlarmRecordDTO {
    /* 学生id */
    private Long userId;
    /* 触发报警时的设备号 */
    private String deviceId;
    /* 触发报警的id 登录记录id */
    private Long loginInfoId;
    /* alarmLevel 报警级别 初级，中级，高级 */
    private String alarmLevel;
    /* alarmType 报警类型 */
    private String alarmType;
    /* status 状态 */
    private String status;
    /* pretreatment 预处理 */
    private String pretreatment;
    /* alarmTime 报警时间 */
    private String alarmTime;
  }


  public AlarmRecordDO getARDOInstance(AlarmRecordDTO ardto) {
    if (null == ardto) return null;
    AlarmRecordDO alarmRecordDO = new AlarmRecordDO();
    if (null != ardto.getUserId())
      alarmRecordDO.setStudentId(this.getStudentId(ardto.getUserId()));
    alarmRecordDO.setDeviceId(ardto.getDeviceId());
    if (null != ardto.getLoginInfoId()) {
      // Long loginInfoId = alarmService.getLoginInfoIdByBid(ardto.getLoginInfoId());
      if (null != ardto.getLoginInfoId()) {
        alarmRecordDO.setTriggerId(ardto.getLoginInfoId().toString());
      }
    }
    alarmRecordDO.setTriggerType(Constants.LOGIN_TRIGGER);
    if (null != ardto.getAlarmLevel())
      alarmRecordDO.setAlarmLevel(AlarmLevelEnum.valueOf(ardto.getAlarmLevel()));
    if (null != ardto.getAlarmType())
      alarmRecordDO.setAlarmType(AlarmTypeEnum.valueOf(ardto.getAlarmType()));
    if (null != ardto.getStatus()) alarmRecordDO.setStatus(StatusEnum.valueOf(ardto.getStatus()));
    if (null != ardto.getPretreatment())
      alarmRecordDO.setPretreatment(PretreatmentEnum.valueOf(ardto.getPretreatment()));
    if (null != ardto.getAlarmTime())
      alarmRecordDO.setAlarmTime(Timestamp.valueOf(ardto.getAlarmTime()) );
    alarmRecordDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    alarmRecordDO.setReadStatus(Constants.UN_READ_STATUS);
    return alarmRecordDO;
  }

  @Data
  public static class LoginInfoDTO {
    /* id 主键id */
    private Long businessId;
    /* 学生id */
    private Long userId;
    /* systemType 系统类型 */
    private String systemType;
    /* deviceId 设备号 */
    private String deviceId;
    /* loginTime 登录时间 */
    private String loginTime;
    private String area;
  }

  public LoginInfoDO getARDOInstance(LoginInfoDTO lidto) {
    if (null == lidto) return null;
    LoginInfoDO loginInfo = new LoginInfoDO();
    if (null == lidto.getUserId()) return null;
    Integer studentId = this.getStudentId(lidto.getUserId());
    if (null == studentId) return null;
    loginInfo.setStudentId(studentId);
    loginInfo.setSystemType(lidto.getSystemType());
    loginInfo.setDeviceId(lidto.getDeviceId());
    if (null != lidto.getLoginTime())
      loginInfo.setLoginTime(Timestamp.valueOf(lidto.getLoginTime()));
    loginInfo.setArea(lidto.getArea());
    loginInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
    if (null == lidto.getBusinessId()) return null;
    loginInfo.setBusinessId(lidto.getBusinessId());
    return loginInfo;
  }

}
