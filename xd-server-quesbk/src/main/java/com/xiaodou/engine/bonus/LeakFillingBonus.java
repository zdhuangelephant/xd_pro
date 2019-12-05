package com.xiaodou.engine.bonus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.xiaodou.constant.ProductConstants;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.domain.product.RedBonus.CourseBonus;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.TollgateEvent;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.util.ConsistentHashPool;
import com.xiaodou.vo.response.CourseScore;
import com.xiaodou.vo.response.CourseScore.ItemScore;

public class LeakFillingBonus extends AbstractBonus {

  public LeakFillingBonus(RedBonus bonus) {
    super(bonus);
  }

  @Override
  public CourseBonus getCourseBonus() {
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    UserExamTotal examTotal =
        quesOperationFacade.queryExamTotal(getBonus().getUserId(), getBonus().getCourseId());
    Long score = Math.round(examTotal.getScore());
    Long m = 100l - score;
    LinkedList<Long> bonusScore = getBonusScore(m);
    CourseBonus bonus = new CourseBonus();
    bonus.setOriginalScore(score);
    bonus.setBonusScore(bonusScore.get(0));
    bonus.setOther1(bonusScore.get(1));
    bonus.setOther2(bonusScore.get(2));
    CourseScore courseScore = new CourseScore();
    courseScore.init(getBonus().getModule(), getBonus().getCourseId(), getBonus().getUserId(),
        examTotal, bonus.getBonusScore().toString());
    // ADD_TO_ITEM
    ADD_TO_ITEM: {
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", getBonus().getUserId());
      cond.put("courseId", getBonus().getCourseId());
      List<UserChapterRecord> chaperRecordList = quesOperationFacade.queryUserChapterRecord(cond);
      if (null == chaperRecordList || chaperRecordList.size() == 0) break ADD_TO_ITEM;
      for (UserChapterRecord chapterRecord : chaperRecordList) {
        if (null == chapterRecord || null == chapterRecord.getItemId()) continue;
        ItemScore itemScore = courseScore.getItem(chapterRecord.getItemId().toString());
        if (null == itemScore) continue;
        itemScore.setOriginalScore(chapterRecord.getScore());
      }
    }
    bonus.setCourseScore(courseScore);
    return bonus;
  }



  @Override
  public LinkedList<Long> getBonusScore(Long m) {
    LinkedList<Double> manicList = Lists.newLinkedList();
    LinkedList<Long> scoreList = Lists.newLinkedList();
    double _0_25 = (1.00d + new Random().nextInt(25)) / 100;
    manicList.add(_0_25);
    double _25_75 = (25.00d + new Random().nextInt(50)) / 100;
    manicList.add(_25_75);
    double _75_100 = (75.00d + new Random().nextInt(25)) / 100;
    manicList.add(_75_100);
    ConsistentHashPool<Integer> pool = new ConsistentHashPool<Integer>(Hashing.md5(), 1, "UTF-8");
    pool.add("_0_25", 0, 85);
    pool.add("_25_75", 1, 10);
    pool.add("_75_100", 2, 5);
    Integer chooseManic = pool.get(UUID.randomUUID().toString());
    double addScore = m * manicList.get(chooseManic);
    if (addScore < 1) addScore = 1d;
    scoreList.add(Math.round(addScore));
    manicList.remove(chooseManic.intValue());
    for (Double manic : manicList)
      scoreList.add(Math.round(100 * manic));
    return scoreList;
  }

  @Override
  protected void useBonus0(CourseBonus courseBonus) {
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    // UPDATE COURSE SCORE
    UserExamTotal examTotal =
        quesOperationFacade.queryExamTotal(getBonus().getUserId(), getBonus().getCourseId());
    Double cOriginalScore = examTotal.getScore();
    UserScorePointRecord record =
        quesOperationFacade.queryScorePointRecord(getBonus().getModule(), getBonus().getTypeCode(),
            Long.parseLong(getBonus().getCourseId()), Long.parseLong(getBonus().getUserId()),
            ProductConstants.RULE_TYPE_LEAK_FILLING);
    Double supplementScore = record.getScore() + courseBonus.getBonusScore();
    Double cCurrScore = courseBonus.getBonusScore() + cOriginalScore;
    record.setScore(supplementScore);
    record.setCompletePercent(1d);
    if (cCurrScore >= 100) {
      cCurrScore = 100d;
    }
    quesOperationFacade.insertOrUpdateUserScorePointRecord(record);
    quesOperationFacade.updateUserScoreNoEvent(getBonus().getModule(),
        Long.parseLong(getBonus().getCourseId()), getBonus().getUserId(), getBonus().getTypeCode());
  }

  @Override
  protected void followUp0(CourseBonus courseBonus) {
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    UserExamTotal examTotal =
        quesOperationFacade.queryExamTotal(getBonus().getUserId(), getBonus().getCourseId());
    Double score = examTotal.getScore();
    TollgateEvent event = EventBuilder.buildTollgateEvent();
    event.setUserId(examTotal.getUserId());
    event.setModule(getBonus().getModule());
    event.setMajorId(QuesBaseConstant.COMMON_MAJOR_ID);
    event.setCourseId(getBonus().getCourseId());
    event.setTollgateId(getBonus().getCourseId());
    event.setCount(0);
    event.setCredit(0);
    event.setScore(score);
    event.setStarLevel(score >= 100 ? 3 : score >= 80 ? 2 : score >= 60 ? 1 : 0);
    event.send();
  }

}
