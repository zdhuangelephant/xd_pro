package com.xiaodou.resources.task.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.push.agent.util.PushUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.model.forum.RelateInfoUserModel;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("CommentPushTask")
public class CommentPushTaskWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 8687484005603376303L;

  private static String[] SMESSAGECONTENT = {null, "%s 评论了您的%s", "%s 赞了您的%s"};

  private static String FORUMID = "forumId";

  private static String COMMENTID = "commentId";

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    RelateInfoUserModel forumRelateInfoModel =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), RelateInfoUserModel.class);
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    if (null != forumRelateInfoModel.getTargeId() && null != forumRelateInfoModel.getType()) {
      try {
        forumServiceFacade.insertForumRelateInfo(forumRelateInfoModel);
        pushInfo(forumRelateInfoModel);
      } catch (Exception e) {
        LoggerUtil.error("插入通知消息失败.", e);
      }
    }

  }

  public void pushInfo(RelateInfoUserModel forumRelateInfoModel) {
    Message ms = new Message();
    String messageType =
        null == forumRelateInfoModel.getTargeCommentId()
            || 0 >= forumRelateInfoModel.getTargeCommentId() ? "话题" : "评论";
    String messageContent =
        String.format(SMESSAGECONTENT[forumRelateInfoModel.getType()], forumRelateInfoModel
            .getUser().getNickName(), messageType);
    ms.setMessageContent(messageContent);
    ms.setNoticeContent(messageContent);
    ms.setMessageType(MessageType.ALL);
    ms.setScope(SpreadRange.ALIAS);
    ms.setTarget(TargetType.ALL);
    Map<String, String> messageextras = new HashMap<String, String>();
    Map<String, String> noticeextras = new HashMap<String, String>();
    ms.addReciever(new String[] {forumRelateInfoModel.getTargetUser().getUserName()});
    Long forumId = forumRelateInfoModel.getResourcesId();
    Long commentId = forumRelateInfoModel.getCommentId();
    PushUtil.setRetCode(noticeextras, ForumResType.NEWCOMMENT.getCode());
    PushUtil.setRetDesc(noticeextras, ForumResType.NEWCOMMENT.getMsg());
    noticeextras.put(FORUMID, null == forumId ? "0" : forumId.toString());
    noticeextras.put(COMMENTID, null == commentId ? "0" : commentId.toString());
    PushUtil.setRetCode(messageextras, ForumResType.NEWCOMMENT.getCode());
    PushUtil.setRetDesc(messageextras, ForumResType.NEWCOMMENT.getMsg());
    messageextras.put(FORUMID, null == forumId ? "0" : forumId.toString());
    messageextras.put(COMMENTID, null == commentId ? "0" : commentId.toString());
    ms.setMessageextras(messageextras);// 消息参数
    ms.setNoticeextras(noticeextras);// 通知参数
    PushClient.push(ms);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("插入推送任务", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("插入推送任务", t);
  }

}
