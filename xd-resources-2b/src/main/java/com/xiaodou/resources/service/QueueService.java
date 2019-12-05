package com.xiaodou.resources.service;

import org.springframework.stereotype.Service;

import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.queue.worker.AliyunWorker;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.model.forum.ForumPraiseModel;
import com.xiaodou.resources.model.forum.ForumRelateInfoModel;
import com.xiaodou.resources.model.forum.RelateInfoUserModel;
import com.xiaodou.resources.model.quesbk.UserChapterRecord;
import com.xiaodou.resources.model.quesbk.UserExamRecord;
import com.xiaodou.resources.model.quesbk.UserExamTotal;
import com.xiaodou.resources.model.quesbk.UserStoreRecordCollect;
import com.xiaodou.resources.model.quesbk.UserWrongRecord;
import com.xiaodou.resources.request.forum.ForumRequest;
import com.xiaodou.resources.vo.task.RefreshCourseWrongQuesNum;
import com.xiaodou.resources.vo.task.UpdateTalkComment;
import com.xiaodou.resources.vo.task.WrongQuesCountVo;

/**
 * 异步记录错题队列
 * 
 * @author zhaodan
 * 
 */
@Service("queueService")
public class QueueService {

  public enum Message {
    UpdateCommentNumber, UpdatePartakeNumber, UpdatePraiseNumber,UpdateTalkPraiseNumber, UpdateForumCategoryNumber, CommentPushTask, UpdateRelateInfo, RefreshFollowerNum, RefreshOpusNum,NoticeToFollower,
    WrongRecord, ExamRecord, ExamTotal, StoreCollect, UpdateChallengeRecord, UserChapterRecord, RobotChallenge, RefreshCourseWrongQuesNum, UseBonusToChapterItem, WrongQuesCount,
    UpdateTalkUnmber
  }

  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

  //forum
  
  public void updateCommentNumber(String forumId) {
    m.sendMessage(Message.UpdateCommentNumber.toString(), forumId);
  }

  public void updatePartakeNumber(String forumCategoryId) {
    m.sendMessage(Message.UpdatePartakeNumber.toString(), forumCategoryId);
  }

  public void updatePraiseNumber(ForumPraiseModel praiseModel) {
    m.sendMessage(Message.UpdatePraiseNumber.toString(), praiseModel);
  }

  public void updateTalkPraiseNumber(ForumPraiseModel praiseModel) {
    m.sendMessage(Message.UpdateTalkPraiseNumber.toString(), praiseModel);
  }
  
  public void updateForumCategoryNumber(ForumRequest forumRequest) {
    m.sendMessage(Message.UpdateForumCategoryNumber.toString(), forumRequest);
  }

  public void addPushTask(RelateInfoUserModel relateInfoUserModel) {
    m.sendMessage(Message.CommentPushTask.toString(), relateInfoUserModel);
  }

  public void updateRelateInfo(ForumRelateInfoModel relateInfoModel) {
    m.sendMessage(Message.UpdateRelateInfo.toString(), relateInfoModel);
  }

  public void refreshFollowerNum(String columnistId) {
    m.sendMessage(Message.RefreshFollowerNum.toString(), columnistId);
  }
  
  public void refreshOpusNum(ForumModel forumModel) {
    m.sendMessage(Message.RefreshOpusNum.toString(), forumModel);
  }
  
  public void sendNoticeToColumnFollower(ForumModel forumModel) {
    m.sendMessage(Message.NoticeToFollower.toString(), forumModel);
  }
  
  //quesbk
  
  public void addWrongQues(UserWrongRecord record) {
    m.sendMessage(Message.WrongRecord.toString(), record);
  }

  public void addExamRecord(UserExamRecord userExamRecord) {
    m.sendMessage(Message.ExamRecord.toString(), userExamRecord);
  }

  public void addExamTotal(UserExamTotal examTotal) {
    m.sendMessage(Message.ExamTotal.toString(), examTotal);
  }

  public void addStoreCollect(UserStoreRecordCollect record) {
    m.sendMessage(Message.StoreCollect.toString(), record);
  }

  public void addAliyunMessage(String messageName, Object message) {
    m.sendMessage(messageName, message);
  }

  public void addUserChapterRecord(UserChapterRecord record) {
    m.sendMessage(Message.UserChapterRecord.toString(), record);
  }

  public void addRefreshCourseWrongQuesNum(RefreshCourseWrongQuesNum vo) {
    m.sendMessage(Message.RefreshCourseWrongQuesNum.toString(), vo);
  }

  public void addWrongQuesCount(WrongQuesCountVo vo){
    m.sendMessage(Message.WrongQuesCount.name(), vo);
  }

  public void updateTalkUnmber(UpdateTalkComment vo) {
    m.sendMessage(Message.UpdateTalkUnmber.name(), vo);
  }
  
}
