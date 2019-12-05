package com.xiaodou.task;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserExamRecordDetail;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.util.BonusUtil;
import com.xiaodou.util.QuesCaculateUtil;
import com.xiaodou.util.QuesCaculateUtil.IQuesCaculateTask;
import com.xiaodou.vo.task.UseBonusToChapterItem;

@HandlerMessage("UseBonusToChapterItem")
public class UseBonusToChapterItemWork extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 7650617455269969079L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    UseBonusToChapterItem bonusDetail =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UseBonusToChapterItem.class);
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    updateUserItemRecord(bonusDetail, quesOperationFacade);
  }

  private void updateUserItemRecord(UseBonusToChapterItem bonusDetail,
      QuesOperationFacade quesOperationFacade) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", bonusDetail.getUserId());
    cond.put("courseId", bonusDetail.getCourseId());
    if (BonusUtil.checkNotEmpty(bonusDetail.getChapterId()))
      cond.put("chapterId", bonusDetail.getChapterId());
    if (BonusUtil.checkNotEmpty(bonusDetail.getItemId()))
      cond.put("itemId", bonusDetail.getItemId());
    List<UserChapterRecord> recordList = quesOperationFacade.queryUserChapterRecord(cond);
    if (null == recordList || recordList.size() == 0) return;
    UserChapterRecord record = recordList.get(0);
    Long originalScore = Math.round(record.getScore());
    Long currScore = originalScore + bonusDetail.getBonusScore();
    if (currScore > 100l) currScore = 100l;
    record.setScore(new Double(currScore));
    SET_STAR_LEVEL: {
      UserExamRecordDetail detail = new UserExamRecordDetail(bonusDetail.getUserId());
      detail.setScore(new Double(currScore));
      IQuesCaculateTask task = QuesCaculateUtil.getTask(bonusDetail.getCourseId());
      if (null == task) break SET_STAR_LEVEL;
      Integer starLevel = task.getStarLevel(detail);
      if (null == starLevel) break SET_STAR_LEVEL;
      record.setStarLevel(starLevel.toString());
    }
    record.markModify();
    quesOperationFacade.updateUserChapterRecord(record);
  }


  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {}

}
