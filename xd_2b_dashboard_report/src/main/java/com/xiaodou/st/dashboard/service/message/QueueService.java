package com.xiaodou.st.dashboard.service.message;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.AbstractMQClient.MessageBox;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
import com.xiaodou.st.dashboard.domain.http.AdmissionCardCodeResponse.AdmissionCardCodeQueueMessageDTO;
import com.xiaodou.st.dashboard.domain.http.ApplyResponse.ApplyQueueMessageDTO;
import com.xiaodou.st.dashboard.domain.http.StudentResponse.StudentQueueMessageDTO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.task.DefaultWorker;

@Service("queueService")
public class QueueService {

  public enum Message {
    UpdateClassName, UpdateStudent, UpdateClassStudentCount, UpdateAndSaveStudent, 
    UpdateAndSaveApply, UpdateAndSaveAdmissionCardCode, SyncFinish,UpdateTelephone
  }

  private IMQClient m = new AbstractMQClient(DefaultWorker.class, DefaultMessageQueueManager.class);

  public void pushMessageBox(MessageBox msgBox) {
    m.sendMessage(msgBox);
  }

  // 发送修改班级名称消息
  public void pushUpdateClassName(ClassDO o) {
    m.sendMessage(Message.UpdateClassName.name(), o);
  }

  public void pushUpdateApplyByStudent(StudentDO o) {
    m.sendMessage(Message.UpdateStudent.name(), o);
  }

  public void pushUpdateClassStudentCount(List<Long> cIds) {
    m.sendMessage(Message.UpdateClassStudentCount.name(), cIds);
  }

  public void pushUpdateAndSaveStudent(StudentQueueMessageDTO o) {
    m.sendMessage(Message.UpdateAndSaveStudent.name(), o);
  }

  public void pushUpdateAndSaveApply(ApplyQueueMessageDTO o) {
    m.sendMessage(Message.UpdateAndSaveApply.name(), o);
  }

  public void pushUpdateAndSaveAdmissionCardCode(AdmissionCardCodeQueueMessageDTO o) {
    m.sendMessage(Message.UpdateAndSaveAdmissionCardCode.name(), o);
  }
  
  public void pushUpdateTelephone(String telephone){
    m.sendMessage(Message.UpdateTelephone.name(), telephone);
  }
}
