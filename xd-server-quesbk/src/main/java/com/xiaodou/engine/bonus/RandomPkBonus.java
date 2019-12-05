package com.xiaodou.engine.bonus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.domain.product.RedBonus.CourseBonus;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.service.QueueService;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.util.ConsistentHashPool;
import com.xiaodou.vo.response.CourseScore;
import com.xiaodou.vo.response.CourseScore.ChapterScore;
import com.xiaodou.vo.response.CourseScore.ItemScore;
import com.xiaodou.vo.task.UseBonusToChapterItem;

public class RandomPkBonus extends AbstractBonus {

  public RandomPkBonus(RedBonus bonus) {
    super(bonus);
  }

  @Override
  public CourseBonus getCourseBonus() {
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    UserExamTotal examTotal =
        quesOperationFacade.queryExamTotal(getBonus().getUserId(), getBonus().getCourseId());
    Long score = Math.round(examTotal.getScore());
    if (score < 60l || score == 100l) return null;
    Long m = 100l - score;
    LinkedList<Long> bonusScore = getBonusScore(m);
    CourseBonus bonus = new CourseBonus();
    bonus.setOriginalScore(score);
    bonus.setBonusScore(bonusScore.get(0));
    bonus.setOther1(bonusScore.get(1));
    bonus.setOther2(bonusScore.get(2));
    CourseScore courseScore = new CourseScore();
    courseScore.init(getBonus().getModule(), getBonus().getCourseId(), getBonus().getUserId(),
        examTotal, null);
    // ADD_TO_ITEM
    ADD_TO_ITEM: {
      double chapterSummaryAllScore = 0d;
      double itemAllScore = 0d;
      double itemNeedScore = 0d;
      double chapterSummaryNeedScore = 0d;
      int ChapterSummaryCount = courseScore.getChapterSummaryCount();
      int itemCount = courseScore.getItemCount();
      Map<String, Object> cond = Maps.newHashMap();
      cond.put("userId", getBonus().getUserId());
      cond.put("courseId", getBonus().getCourseId());
      List<UserChapterRecord> chaperRecordList = quesOperationFacade.queryUserChapterRecord(cond);
      if (null == chaperRecordList || chaperRecordList.size() == 0) break ADD_TO_ITEM;
      for (UserChapterRecord chapterRecord : chaperRecordList) {
        if (chapterRecord.getChapterId().toString().equals(chapterRecord.getItemId().toString())) {
          chapterSummaryAllScore += chapterRecord.getScore();
        } else {
          itemAllScore += chapterRecord.getScore();
        }
      }
      Double ChapterSummaryTotal = 100d * ChapterSummaryCount;
      Double ChapterSummarySurp = ChapterSummaryTotal - chapterSummaryAllScore;
      Double ChapterSummarytotalScore = chapterSummaryNeedScore * ChapterSummaryCount;
      // 普通章节
      Double itemTotal = 100d * itemCount;
      Double itemSurp = itemTotal - itemAllScore;
      Double totalScore = itemNeedScore * itemCount;
      if (null == chaperRecordList || chaperRecordList.size() == 0) break ADD_TO_ITEM;
      for (UserChapterRecord chapterRecord : chaperRecordList) {
        if (chapterRecord.getChapterId().toString().equals(chapterRecord.getItemId().toString())) {
          ItemScore itemScore =
              courseScore.getChapterSummaryItem(chapterRecord.getItemId().toString());
          itemScore.setOriginalScore(chapterRecord.getScore());
          itemScore.setBonusScore(Math.round(ChapterSummarytotalScore
              * (100d - chapterRecord.getScore()) / ChapterSummarySurp));
        } else {
          ItemScore itemScore = courseScore.getItem(chapterRecord.getItemId().toString());
          itemScore.setOriginalScore(chapterRecord.getScore());
          itemScore.setBonusScore(Math.round(totalScore * (100d - chapterRecord.getScore())
              / itemSurp));
        }
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
    Double cCurrScore = courseBonus.getBonusScore() + cOriginalScore;
    UserExamTotal _examTotal = new UserExamTotal();
    _examTotal.setId(examTotal.getId());
    _examTotal.setScore(cCurrScore);
    quesOperationFacade.updateExamTotal(_examTotal);
    // ADD_TO_ITEM
    ADD_TO_ITEM: {
      CourseScore courseScore = courseBonus.getCourseScore();
      if (null == courseScore.getChapterScoreList()
          || courseScore.getChapterScoreList().size() == 0) break ADD_TO_ITEM;
      for (ChapterScore chapterScore : courseScore.getChapterScoreList()) {
        if (null == chapterScore.getItemScoreList() || chapterScore.getItemScoreList().size() == 0)
          continue;
        for (ItemScore itemScore : chapterScore.getItemScoreList()) {
          UseBonusToChapterItem vo = new UseBonusToChapterItem();
          vo.setModule(getBonus().getModule());
          vo.setTypeCode(getBonus().getTypeCode());
          vo.setUserId(getBonus().getUserId());
          vo.setCourseId(courseScore.getCourseId());
          vo.setChapterId(chapterScore.getChapterId());
          vo.setItemId(itemScore.getItemId());
          vo.setBonusScore(Long.valueOf(itemScore.getBonusScore()));
          QueueService queueService = SpringWebContextHolder.getBean("queueService");
          queueService.addUseBonusToChapterItem(vo);
        }
      }
    }
  }

}
