package com.xiaodou.engine.bonus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.constant.ProductConstants;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.domain.product.RedBonus.CourseBonus;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.queue.client.AbstractMQClient.MessageBox;
import com.xiaodou.service.QueueService;
import com.xiaodou.service.QueueService.Message;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.util.BonusUtil;
import com.xiaodou.util.ConsistentHashPool;
import com.xiaodou.vo.response.CourseScore;
import com.xiaodou.vo.response.CourseScore.ItemScore;
import com.xiaodou.vo.task.UseBonusToChapterItem;

public class TollgateItemBonus extends AbstractBonus {

  public TollgateItemBonus(RedBonus bonus) {
    super(bonus);
  }

  @Override
  public CourseBonus getCourseBonus() {
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", getBonus().getUserId());
    cond.put("courseId", getBonus().getCourseId());
    if (BonusUtil.checkNotEmpty(getBonus().getChapterId()))
      cond.put("chapterId", getBonus().getChapterId());
    if (BonusUtil.checkNotEmpty(getBonus().getItemId()))
      cond.put("itemId", getBonus().getItemId());
    List<UserChapterRecord> recordList = quesOperationFacade.queryUserChapterRecord(cond);
    if (null == recordList || recordList.size() == 0) return null;
    UserChapterRecord record = recordList.get(0);
    Long score = Math.round(record.getScore());
    if (score < 60l) return null;
    UserExamTotal total =
        quesOperationFacade.queryExamTotal(getBonus().getUserId(), getBonus().getCourseId());
    Long m = 100l - score;
    LinkedList<Long> bonusScore = getBonusScore(m);
    CourseBonus bonus = new CourseBonus();
    bonus.setOriginalScore(score);
    bonus.setBonusScore(bonusScore.get(0));
    bonus.setOther1(bonusScore.get(1));
    bonus.setOther2(bonusScore.get(2));
    CourseScore courseScore = new CourseScore();
    courseScore.init(getBonus().getModule(), getBonus().getCourseId(), getBonus().getUserId(),
        total, null);
    // ADD_TO_ITEM
    ADD_TO_ITEM: {
      cond = Maps.newHashMap();
      cond.put("userId", getBonus().getUserId());
      cond.put("courseId", getBonus().getCourseId());
      List<UserChapterRecord> chaperRecordList = quesOperationFacade.queryUserChapterRecord(cond);
      if (null == chaperRecordList || chaperRecordList.size() == 0) break ADD_TO_ITEM;
      for (UserChapterRecord chapterRecord : chaperRecordList) {
        if (null == chapterRecord || null == chapterRecord.getItemId()) continue;
        ItemScore itemScore = courseScore.getItem(chapterRecord.getItemId().toString());
        if (null == itemScore) continue;
        if (null != getBonus() && getBonus().getItemId().equals(itemScore.getItemId()))
          itemScore.setBonusScore(bonus.getBonusScore());
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
    pool.add("_0_25", 0, 20);
    pool.add("_25_75", 1, 60);
    pool.add("_75_100", 2, 20);
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
    MessageBox box = new MessageBox();
    UseBonusToChapterItem vo = new UseBonusToChapterItem();
    vo.setModule(getBonus().getModule());
    vo.setTypeCode(getBonus().getTypeCode());
    vo.setUserId(getBonus().getUserId());
    vo.setCourseId(getBonus().getCourseId());
    vo.setChapterId(getBonus().getChapterId());
    vo.setItemId(getBonus().getItemId());
    vo.setBonusScore(courseBonus.getBonusScore());
    // queueService.addUseBonusToChapterItem(vo);
    box.addTargetLevelMessage(MessageBox.FIRST_LEVEL, Message.UseBonusToChapterItem.toString(), vo);

    UserScorePointRecord scorePointRecord = new UserScorePointRecord();
    scorePointRecord.setModule(getBonus().getModule());
    scorePointRecord.setTypeCode(getBonus().getTypeCode());
    if (StringUtils.isBlank(getBonus().getItemId())
        || getBonus().getItemId().equals(getBonus().getChapterId())) {
      scorePointRecord.setRuleType(ProductConstants.RULE_TYPE_CHAPTER_PRACTICE);
    } else {
      QuesOperationFacade quesOperationFacade =
          SpringWebContextHolder.getBean("quesOperationFacade");
      CourseProductItem item =
          quesOperationFacade.queryItem(getBonus().getCourseId(), getBonus().getChapterId(),
              getBonus().getItemId());
      if (null == item) {
        scorePointRecord.setRuleType(ProductConstants.RULE_TYPE_FINAL_EXAM);
      } else {
        scorePointRecord.setRuleType(ProductConstants.RULE_TYPE_ITEM_PRACTICE);
      }
    }
    scorePointRecord.setProductId(Long.parseLong(getBonus().getCourseId()));
    scorePointRecord.setUserId(Long.parseLong(getBonus().getUserId()));
    box.addTargetLevelMessage(MessageBox.SECOND_LEVEL, Message.ScorePoint.toString(),
        scorePointRecord);

    QueueService queueService = SpringWebContextHolder.getBean("queueService");
    queueService.sendMessageBox(box);
  }

}
