package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.RandomPkEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name @see com.xiaodou.mission.engine.action.PkEventAction.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年3月15日
 * @description PK事件动作
 * @version 1.0
 */
public class RandomPkEventAction extends AbstractAction<RandomPkEvent> {

  @Override
  public void processCoreParam(RandomPkEvent event, UserCollectDataInstance coreParam) {
    if (null != event.getCount()) {
      Integer pkTotalTimes = coreParam.getPkTotalTimesForStatistic();
      coreParam.setPkTotalTimesForStatistic(++pkTotalTimes);
      Integer answerTotal = coreParam.getAnswerTotal();
      Integer answerOneday = coreParam.getAnswerOneday();
      coreParam.setAnswerTotal(answerTotal + event.getCount());
      coreParam.setAnswerOneday(answerOneday + event.getCount());
    }
    if (event.getIsWinner()) {
      Integer pkWinsTotal = coreParam.getPkWinTimes();
      Integer pkTotalTimes = coreParam.getPkTotalTimesForCaculate();
      coreParam.setPkWinTimes(++pkWinsTotal);
      coreParam.setPkTotalTimesForCaculate(++pkTotalTimes);
      if (null != coreParam.getPkInfo()) {
        Integer pkWInsSameperson = coreParam.getPkInfo().getPkWinTimesSameperson();
        Integer pkTotalTimesSameperson = coreParam.getPkInfo().getPkTotalTimesSameperson();
        coreParam.getPkInfo().setPkWinTimesSameperson(++pkWInsSameperson);
        coreParam.getPkInfo().setPkTotalTimesSameperson(++pkTotalTimesSameperson);
        coreParam.getPkInfo().setPkWinPercentSameperson(
            new Double(coreParam.getPkInfo().getPkWinTimesSameperson())
                / new Double(coreParam.getPkInfo().getPkTotalTimesSameperson()));
      }
    }
    if (event.getIsFailer()) {
      Integer pkFailTotal = coreParam.getPkFailTimes();
      Integer pkTotalTimes = coreParam.getPkTotalTimesForCaculate();
      coreParam.setPkFailTimes(++pkFailTotal);
      coreParam.setPkTotalTimesForCaculate(++pkTotalTimes);
      if (null != coreParam.getPkInfo()) {
        Integer pkFailSameperson = coreParam.getPkInfo().getPkFailTimesSameperson();
        Integer pkTotalTimesSameperson = coreParam.getPkInfo().getPkTotalTimesSameperson();
        coreParam.getPkInfo().setPkFailTimesSameperson(++pkFailSameperson);
        coreParam.getPkInfo().setPkTotalTimesSameperson(++pkTotalTimesSameperson);
        coreParam.getPkInfo().setPkFailPercentSameperson(
            new Double(coreParam.getPkInfo().getPkFailTimesSameperson())
                / new Double(coreParam.getPkInfo().getPkTotalTimesSameperson()));
      }
    }
    if (null != event.getCredit() && null != coreParam.getCreditOneday()) {
      Integer creditOneday = coreParam.getCreditOneday();
      coreParam.setCreditOneday(creditOneday + event.getCredit());
    }

  }

  @Override
  public void processOtherParam(RandomPkEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(RandomPkEvent event, Map<String, Object> shareParam) {}

}
