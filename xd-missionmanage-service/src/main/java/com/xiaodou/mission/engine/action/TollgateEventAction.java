package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.TollgateEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.TollgateEventAction.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月15日
 * @description 闯关事件动作
 * @version 1.0
 */
public class TollgateEventAction extends AbstractAction<TollgateEvent> {

  @Override
  public void processCoreParam(TollgateEvent event, UserCollectDataInstance coreParam) {
    boolean sameCourse = coreParam.getTollgateCourseId().equals(event.getCourseId());
    coreParam.getTollgateSet().add(event.getTollgateId());
    coreParam.setTollgateTotalTimes(coreParam.getTollgateSet().size());
    if (sameCourse && null != coreParam.getTollgateInfo()) {
      coreParam.getTollgateInfo().getTollgateSetSamecourse().add(event.getTollgateId());
      coreParam.getTollgateInfo().setTollgateTotalTimesSamecourse(
          coreParam.getTollgateInfo().getTollgateSetSamecourse().size());
      coreParam.getTollgateInfo().setLastestTollgateChapterId(event.getChapterId());
      coreParam.getTollgateInfo().setLastestTollgateItemId(event.getTollgateId());
    }
    switch (event.getStarLevel()) {
      case 1:
        coreParam.getTollgateOnestarSet().add(event.getTollgateId());
        coreParam.setTollgateOnestarCount(coreParam.getTollgateOnestarSet().size());
        coreParam.getTollgateTwostarSet().remove(event.getTollgateId());
        coreParam.setTollgateTwostarCount(coreParam.getTollgateTwostarSet().size());
        coreParam.getTollgateThreestarSet().remove(event.getTollgateId());
        coreParam.setTollgateThreestarCount(coreParam.getTollgateThreestarSet().size());
        if (sameCourse && null != coreParam.getTollgateInfo()) {
          coreParam.getTollgateInfo().getTollgateOnestarSetSamecourse().add(event.getTollgateId());
          coreParam.getTollgateInfo().setTollgateOnestarCountSamecourse(
              coreParam.getTollgateInfo().getTollgateOnestarSetSamecourse().size());
          coreParam.getTollgateInfo().getTollgateTwostarSetSamecourse()
              .remove(event.getTollgateId());
          coreParam.getTollgateInfo().setTollgateTwostarCountSamecourse(
              coreParam.getTollgateInfo().getTollgateTwostarSetSamecourse().size());
          coreParam.getTollgateInfo().getTollgateThreestarSetSamecourse()
              .remove(event.getTollgateId());
          coreParam.getTollgateInfo().setTollgateThreestarCountSamecourse(
              coreParam.getTollgateInfo().getTollgateThreestarSetSamecourse().size());
        }
        break;
      case 2:
        coreParam.getTollgateOnestarSet().remove(event.getTollgateId());
        coreParam.setTollgateOnestarCount(coreParam.getTollgateOnestarSet().size());
        coreParam.getTollgateTwostarSet().add(event.getTollgateId());
        coreParam.setTollgateTwostarCount(coreParam.getTollgateTwostarSet().size());
        coreParam.getTollgateThreestarSet().remove(event.getTollgateId());
        coreParam.setTollgateThreestarCount(coreParam.getTollgateThreestarSet().size());
        if (sameCourse && null != coreParam.getTollgateInfo()) {
          coreParam.getTollgateInfo().getTollgateOnestarSetSamecourse()
              .remove(event.getTollgateId());
          coreParam.getTollgateInfo().setTollgateOnestarCountSamecourse(
              coreParam.getTollgateInfo().getTollgateOnestarSetSamecourse().size());
          coreParam.getTollgateInfo().getTollgateTwostarSetSamecourse().add(event.getTollgateId());
          coreParam.getTollgateInfo().setTollgateTwostarCountSamecourse(
              coreParam.getTollgateInfo().getTollgateTwostarSetSamecourse().size());
          coreParam.getTollgateInfo().getTollgateThreestarSetSamecourse()
              .remove(event.getTollgateId());
          coreParam.getTollgateInfo().setTollgateThreestarCountSamecourse(
              coreParam.getTollgateInfo().getTollgateThreestarSetSamecourse().size());
        }
        break;
      case 3:
        coreParam.getTollgateOnestarSet().remove(event.getTollgateId());
        coreParam.setTollgateOnestarCount(coreParam.getTollgateOnestarSet().size());
        coreParam.getTollgateTwostarSet().remove(event.getTollgateId());
        coreParam.setTollgateTwostarCount(coreParam.getTollgateTwostarSet().size());
        coreParam.getTollgateThreestarSet().add(event.getTollgateId());
        coreParam.setTollgateThreestarCount(coreParam.getTollgateThreestarSet().size());
        if (sameCourse && null != coreParam.getTollgateInfo()) {
          coreParam.getTollgateInfo().getTollgateOnestarSetSamecourse()
              .remove(event.getTollgateId());
          coreParam.getTollgateInfo().setTollgateOnestarCountSamecourse(
              coreParam.getTollgateInfo().getTollgateOnestarSetSamecourse().size());
          coreParam.getTollgateInfo().getTollgateTwostarSetSamecourse()
              .remove(event.getTollgateId());
          coreParam.getTollgateInfo().setTollgateTwostarCountSamecourse(
              coreParam.getTollgateInfo().getTollgateTwostarSetSamecourse().size());
          coreParam.getTollgateInfo().getTollgateThreestarSetSamecourse()
              .add(event.getTollgateId());
          coreParam.getTollgateInfo().setTollgateThreestarCountSamecourse(
              coreParam.getTollgateInfo().getTollgateThreestarSetSamecourse().size());
        }
        break;
      default:
        break;
    }
    Integer creditOneday = coreParam.getCreditOneday();
    coreParam.setCreditOneday(creditOneday + event.getCredit());
    Integer answerTotal = coreParam.getAnswerTotal();
    Integer answerOneday = coreParam.getAnswerOneday();
    coreParam.setAnswerTotal(answerTotal + event.getCount());
    coreParam.setAnswerOneday(answerOneday + event.getCount());
  }

  @Override
  public void processOtherParam(TollgateEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(TollgateEvent event, Map<String, Object> shareParam) {}

}
