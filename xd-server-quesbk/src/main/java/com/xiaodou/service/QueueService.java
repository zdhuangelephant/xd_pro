package com.xiaodou.service;

import org.springframework.stereotype.Service;

import com.xiaodou.domain.product.ChallengeRecord;
import com.xiaodou.queue.aliyun.worker.AliyunWorker;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.AbstractMQClient.MessageBox;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.vo.task.RobotChallengeVo;
import com.xiaodou.vo.task.UseBonusToChapterItem;

/**
 * 异步记录错题队列
 * 
 * @author zhaodan
 * 
 */
@Service("queueService")
public class QueueService {

  public enum Message {
    ExamWrongRecord, ChangeWrongRecord, ExamRecord, ExamTotal, ScorePoint, StoreCollect, UpdateChallengeRecord, UserChapterRecord, RobotChallenge, RefreshCourseWrongQuesNum, UseBonusToChapterItem, InitCreditRedBonus, TollgateEvent, DailyPracticeEvent, LeakFillingEvent, RandomPkEvent, UpdateLearnRecordView;
  }

  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

  public void addAliyunMessage(String messageName, Object message) {
    m.sendMessage(messageName, message);
  }

  public void updateChallengeRecord(ChallengeRecord record) {
    m.sendMessage(Message.UpdateChallengeRecord.toString(), record);
  }

  public void addRobotChallenge(RobotChallengeVo vo) {
    m.sendMessage(Message.RobotChallenge.toString(), vo);
  }

  public void addUseBonusToChapterItem(UseBonusToChapterItem vo) {
    m.sendMessage(Message.UseBonusToChapterItem.name(), vo);
  }

  public void sendMessageBox(MessageBox messageBox) {
    m.sendMessage(messageBox);
  }
}
