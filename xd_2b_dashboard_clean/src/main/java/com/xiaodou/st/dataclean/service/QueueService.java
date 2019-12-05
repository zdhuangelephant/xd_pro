package com.xiaodou.st.dataclean.service;

import org.springframework.stereotype.Service;

import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.st.dataclean.model.domain.dashboard.alarm.AlarmRecordModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFaceRecognitionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishAllMissionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.st.dataclean.task.DefaultWorker;

/**
 * 
 * 
 * @author zhaodan
 * 
 */
@Service("queueService")
public class QueueService {

  public enum Message {
    UserExamTotalEvent,UserExamTotalEventJob, AfterUserExamTotalEvent, UserLearnRecord, 
    UserLearnRecordJob,AfterUserLearnRecord, FinishMission,FinishMissionJob, FinishMissionAfter, 
    FaceRecognition, RawDataFinalExam, UserLearnTracker
  }

  private IMQClient m = new AbstractMQClient(DefaultWorker.class, DefaultMessageQueueManager.class);

  public void pushExamTotal(RawDataExamTotalModel o) {
    m.sendMessage(Message.UserExamTotalEvent.name(), o);
  }
  public void pushExamTotalJob(RawDataExamTotalModel o) {
	    m.sendMessage(Message.UserExamTotalEventJob.name(), o);
	  }

  public void pushAfterExamTotal(RawDataExamTotalModel o) {
    m.sendMessage(Message.AfterUserExamTotalEvent.name(), o);
  }
  
  public void pushLearnRecord(RawDataLearnRecordModel o) {
    m.sendMessage(Message.UserLearnRecord.name(), o);
  }
  public void pushLearnRecordJob(RawDataLearnRecordModel o) {
	    m.sendMessage(Message.UserLearnRecordJob.name(), o);
	  }

  public void pushAfterLearnRecord(RawDataLearnRecordModel o) {
    m.sendMessage(Message.AfterUserLearnRecord.name(), o);
  }

  public void pushFinishAllMission(RawDataFinishAllMissionModel o) {
    m.sendMessage(Message.FinishMissionJob.name(), o);
  }

  public void pushFinishMissionJob(RawDataFinishMissionModel o) {
	    m.sendMessage(Message.FinishMissionJob.name(), o);
	  }
  

  public void pushFinishMissionAfter(RawDataFinishMissionModel o) {
    m.sendMessage(Message.FinishMissionAfter.name(), o);
  }

  public void pushFaceRecognition(RawDataFaceRecognitionModel o) {
    m.sendMessage(Message.FaceRecognition.name(), o);
  }
  public void pushUserLearnTracker(AlarmRecordModel o) {
    m.sendMessage(Message.UserLearnTracker.name(), o);
  }

}
