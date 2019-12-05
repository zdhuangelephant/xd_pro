package com.xiaodou.engine.rule.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.QuesbkExamRule;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.engine.rule.base.AbstractRule;
import com.xiaodou.engine.rule.base.RandomChooser;
import com.xiaodou.engine.rule.impl.ChapterPaperRule.ChapterPaperRuleChooser;
import com.xiaodou.engine.rule.impl.ChapterPaperRule.ChapterPaperRuleEntity;
import com.xiaodou.engine.rule.iterface.IRule;
import com.xiaodou.engine.rule.iterface.IRuleChooser;
import com.xiaodou.engine.rule.model.Rule;
import com.xiaodou.engine.rule.model.RuleEntity;
import com.xiaodou.engine.rule.model.RuleEntityParam;
import com.xiaodou.summer.sceduling.concurrent.SummerTask;
import com.xiaodou.util.QuesbkUtil;
import com.xiaodou.util.QuesbkUtil.QuesNum;
import com.xiaodou.util.StaticInfoProp;

/**
 * @name ChapterPaperRule
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月9日
 * @description 章节练习出题规则
 * @version 1.0
 */
public class ChapterPaperRule extends AbstractRule<ChapterPaperRuleEntity>
    implements
      IRule<ChapterPaperRuleChooser, ChapterPaperRuleEntity> {

  /**
   * @name ChapterPaperRuleChooser
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 章节练习规则选择器
   * @version 1.0
   */
  static class ChapterPaperRuleChooser extends RandomChooser<ChapterPaperRuleEntity>
      implements
        IRuleChooser<ChapterPaperRuleEntity> {
    private static final ChapterPaperRuleChooser _instance = new ChapterPaperRuleChooser();

    private static ChapterPaperRuleChooser getInstance() {
      return _instance;
    }

    @Override
    public ChapterPaperRuleEntity chooseRule(List<ChapterPaperRuleEntity> ruleList) {
      if (null != ruleList && ruleList.size() == 1) return ruleList.get(0);
      return choose(ruleList);
    }

  }

  /**
   * @name ChapterPaperRuleEntity
   * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月9日
   * @description 章节练习规则实体
   * @version 1.0
   */
  class ChapterPaperRuleEntity extends RuleEntity {
    public ChapterPaperRuleEntity(QuesbkExamRule examRule) {
      super(examRule);
    }

    @Override
    protected void _fetchRule(RuleEntityParam param, Rule rule) {
      if (StringUtils.isNotBlank(param.getItemId())) {
        rule.setProductChapterId(Integer.parseInt(param.getItemId()));
      } else {
        // 查询子章节列表
        List<CourseProductItem> itemList =
            param.getService().queryItemList(param.getCourseId(), param.getChapterId());
        if (null == itemList || itemList.size() == 0) {
          throw new RuntimeException("章节信息不存在");
        }
        rule.setItemList(Lists.transform(itemList, new Function<CourseProductItem, String>() {
          @Override
          public String apply(CourseProductItem item) {
            if (null != item && null != item.getId()) return item.getId().toString();
            return Integer.toString(Integer.MIN_VALUE);
          }
        }));
      }

    }

    @Override
    public List<Rule> initDefaultRule(RuleEntityParam param) {
      List<Rule> ruleLst = Lists.newArrayList();
      Rule rule = new Rule();
      QuesNum qusNum = null;
      if (StringUtils.isNotBlank(param.getItemId())) {
        qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.ITEM_DEFAULT_QUESNUM);
        rule.setProductChapterId(Integer.parseInt(param.getItemId()));
      } else {
        // 查询子章节列表
        List<CourseProductItem> itemList =
            param.getService().queryItemList(param.getCourseId(), param.getChapterId());
        if (null == itemList || itemList.size() == 0) {
          throw new RuntimeException("章节信息不存在");
        }
        qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.CHAPTER_DEFAULT_QUESNUM);
        rule.setProductChapterId(null);
        rule.setItemList(Lists.transform(itemList, new Function<CourseProductItem, String>() {
          @Override
          public String apply(CourseProductItem item) {
            if (null != item && null != item.getId()) return item.getId().toString();
            return Integer.toString(Integer.MIN_VALUE);
          }
        }));
      }
      rule.setQuestionType(StaticInfoProp.getInt(QuesBaseConstant.QUES_TYPE_SIMPLE_CHOICE));
      rule.setQuestionNum(qusNum.getQuesNum());
      rule.setUserId(param.getUid());
      ruleLst.add(rule);
      return ruleLst;
    }

    @Override
    protected void _processRule(final Rule rule, final RuleEntityParam param,
        final List<QuesbkQues> quesLst) {
      // List<QuesbkQues> quesList = param.getService().queryQuesList(rule);
      final List<Long> allQuesIdList = param.getService().queryQuesIdList(rule);
      if (null == allQuesIdList || allQuesIdList.size() == 0) return;
      int size = 0; // 起始页码
      int pageSize = 15; // 分页数量
      if (allQuesIdList.size() % pageSize == 0)
        size = allQuesIdList.size() / pageSize;
      else
        size = allQuesIdList.size() / pageSize + 1;
      final CountDownLatch reachGate = new CountDownLatch(size);
      for (int i = 0; i < size; i++) {
        int _start = i * pageSize;
        int _end = i * pageSize + pageSize;
        if (_end > allQuesIdList.size()) _end = allQuesIdList.size();
        final List<Long> quesIdList = allQuesIdList.subList(_start, _end);
        param.getExcutor().execute(new SummerTask() {
          @Override
          public void run() {
            try {
              if (null == quesIdList || quesIdList.isEmpty()) return;
              List<QuesbkQues> quesList =
                  param.getService().queryQuesListByIdList(rule.getUserId(), param.getCourseId(),
                      quesIdList);
              addAll(quesLst, getShuffleSubLst(quesList, quesList.size()));
              Collections.shuffle(quesLst);
            } finally {
              reachGate.countDown();
            }
          }

          @Override
          public void onException(Throwable t) {
            LoggerUtil.error("查询题目发生异常", t);
          }
        });
      }
      try {
        reachGate.await();
      } catch (InterruptedException e) {
        LoggerUtil.error("reachGate中断异常", e);
      }
    }
  }



  @Override
  public ChapterPaperRuleEntity getRuleEntity() {
    return ChapterPaperRuleChooser.getInstance().chooseRule(getRuleEntityList());
  }

  @Override
  public ChapterPaperRuleEntity transfer(QuesbkExamRule examRule) {
    return new ChapterPaperRuleEntity(examRule);
  }

}
