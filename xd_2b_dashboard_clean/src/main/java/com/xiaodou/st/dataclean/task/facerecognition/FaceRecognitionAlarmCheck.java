package com.xiaodou.st.dataclean.task.facerecognition;

import java.sql.Timestamp;
import java.util.List;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.st.dataclean.constant.Constant;
import com.xiaodou.st.dataclean.enums.RoleTypeEnum;
import com.xiaodou.st.dataclean.model.domain.dashboard.alarm.AlarmRecordModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFaceRecognitionModel;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.st.dataclean.vo.mq.AlarmMessage;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage(value = "FaceRecognition", type = MessageType.Multiple)
public class FaceRecognitionAlarmCheck extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /** limitCount 触发报警次数 */
  private Integer limitCount = 3;

  /**
   * 完成
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    if (StringUtils.isBlank(message.getMessageBodyJson())) return;
    DashBoardServiceFacade dashBoardServiceFacade =
        SpringWebContextHolder.getBean("dashBoardServiceFacade");
    RawDataFaceRecognitionModel rawData =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), RawDataFaceRecognitionModel.class);
    if (null == rawData || StringUtils.isBlank(rawData.getTestId())
        || null == rawData.getStudentId() || Constant.IS_SELF == rawData.getResult()) return;
    rawData.setModule(rawData.getModule());
    Page<RawDataFaceRecognitionModel> recognitionPage =
        dashBoardServiceFacade
            .queryFaceRecognitionPage(rawData.getStudentId(), rawData.getTestId());
    if (null == recognitionPage || recognitionPage.getResult() == null
        || recognitionPage.getResult().size() < limitCount) return;
    Integer notSelfCount = 0;
    for (RawDataFaceRecognitionModel faceRecognitionModel : recognitionPage.getResult()) {
      // 这里统计连续失败次数,如果有成功记录,重置统计次数
      if (Constant.IS_NOT_SELF == faceRecognitionModel.getResult()) {
        notSelfCount++;
        if (notSelfCount >= limitCount) break;
      }
      // else {
      // notSelfCount = 0;
      // }
    }
    if (notSelfCount < limitCount) return;
    AlarmRecordModel alarmRecord =
        dashBoardServiceFacade.queryAlarmModel(rawData.getStudentId(), rawData.getTestId());
    if (null != alarmRecord) return;
    alarmRecord = new AlarmRecordModel();
    alarmRecord.setModule(rawData.getModule());
    alarmRecord.setStudentId(rawData.getStudentId());
    alarmRecord.setTriggerId(rawData.getTestId());
    alarmRecord.setTriggerType(Constant.TRIGGRT_TYPE_FACE_RECOGNITION);
    alarmRecord.setAlarmTime(rawData.getCollectTime());
    alarmRecord.setReadStatus(Constant.RECORD_STATUS_UNREAD);
    alarmRecord.setDeviceId(rawData.getDeviceId());
    alarmRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
    // TAUGHT_UNIT
    {
      alarmRecord.setId(null);
      alarmRecord.setRoleType(RoleTypeEnum.RoleTypeEnum_Taught_Unit.getCode().shortValue());
      alarmRecord.setUnitId(rawData.getTaughtUnitId());
      dashBoardServiceFacade.insertAlarmRecord(alarmRecord);
      RabbitMQSender.getInstance().send(new AlarmMessage(alarmRecord));
    }
    // CHIEF_UNIT
    {
      alarmRecord.setId(null);
      alarmRecord.setRoleType(RoleTypeEnum.RoleTypeEnum_Chief_Unit.getCode().shortValue());
      alarmRecord.setUnitId(rawData.getChiefUnitId());
      dashBoardServiceFacade.insertAlarmRecord(alarmRecord);
      RabbitMQSender.getInstance().send(new AlarmMessage(alarmRecord));
    }
    // PILOT_UNIT
    {
      alarmRecord.setId(null);
      alarmRecord.setRoleType(RoleTypeEnum.RoleTypeEnum_Pilot_Unit.getCode().shortValue());
      alarmRecord.setUnitId(rawData.getPilotUnitId());
      dashBoardServiceFacade.insertAlarmRecord(alarmRecord);
      RabbitMQSender.getInstance().send(new AlarmMessage(alarmRecord));
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
    LoggerUtil.error("人脸识别报警检查异常", t);
  }
}
