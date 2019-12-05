package com.xiaodou.resources.service.quesbk.rule.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.quesbk.QuesbkExamRule;
import com.xiaodou.resources.model.quesbk.QuesbkQues;
import com.xiaodou.resources.service.quesbk.rule.base.AbstractRule;
import com.xiaodou.resources.service.quesbk.rule.base.RandomChooser;
import com.xiaodou.resources.service.quesbk.rule.impl.ChapterPaperRule.ChapterPaperRuleChooser;
import com.xiaodou.resources.service.quesbk.rule.impl.ChapterPaperRule.ChapterPaperRuleEntity;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRule;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRuleChooser;
import com.xiaodou.resources.service.quesbk.rule.model.Rule;
import com.xiaodou.resources.service.quesbk.rule.model.RuleEntity;
import com.xiaodou.resources.service.quesbk.rule.model.RuleEntityParam;
import com.xiaodou.resources.util.QuesbkUtil;
import com.xiaodou.resources.util.QuesbkUtil.QuesNum;
import com.xiaodou.resources.util.StaticInfoProp;

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
    public List<Rule> initDefaultRule(RuleEntityParam param) {
      List<Rule> ruleLst = Lists.newArrayList();
      Rule rule = new Rule();
      QuesNum qusNum = null;
      if (StringUtils.isNotBlank(param.getItemId())) {
        qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.ITEM_DEFAULT_QUESNUM);
        rule.setProductChapterId(Integer.parseInt(param.getItemId()));
      } else {
        // 查询子章节列表
        List<ProductItemModel> itemList =
            param.getService().queryItemList(param.getCourseId(), param.getChapterId());
        if (null == itemList || itemList.size() == 0) {
          throw new RuntimeException("该章节不存在");
        }
        qusNum = QuesbkUtil.getQuesNum(QuesBaseConstant.CHAPTER_DEFAULT_QUESNUM);
        rule.setProductChapterId(null);
        rule.setItemList(Lists.transform(itemList, new Function<ProductItemModel, String>() {
          @Override
          public String apply(ProductItemModel item) {
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
    protected void _processRule(Rule rule, RuleEntityParam param, List<QuesbkQues> quesLst) {
      List<QuesbkQues> wrongLst = Lists.newArrayList();
      List<QuesbkQues> unExamLst = Lists.newArrayList();
      List<QuesbkQues> normalLst = Lists.newArrayList();
      List<QuesbkQues> quesList = param.getService().queryQuesList(rule);
      for (QuesbkQues ques : quesList) {
        if (ques.getStatistic().getMyWrongTimes() > 0) {
          wrongLst.add(ques);
        } else if (ques.getStatistic().getMyExamTimes() == 0) {
          unExamLst.add(ques);
        } else {
          normalLst.add(ques);
        }
      }

      if (PriorQues.WRONG.getCode().equals(param.getPriorQues())) {
        Collections.sort(wrongLst, new Comparator<QuesbkQues>() {
          @Override
          public int compare(QuesbkQues o1, QuesbkQues o2) {
            return o1.getId().intValue() - o2.getId().intValue();
          }
        });
        if (StringUtils.isNotBlank(param.getLastQuesId())) {
          List<QuesbkQues> wrongLstBak = Lists.newArrayList();
          for (QuesbkQues ques : wrongLst) {
            if (ques.getId() > Long.parseLong(param.getLastQuesId())) {
              wrongLstBak.add(ques);
            }
          }
          wrongLst = wrongLstBak;
        }
        int wrongCount = getCompareCount(wrongLst.size(), rule.getQuestionNum(), 1.0);
        addAll(quesLst, getShuffleSubLst(wrongLst, wrongCount));
      } else if (PriorQues.UNEXAM.getCode().equals(param.getPriorQues())) {
        Collections.sort(unExamLst, new Comparator<QuesbkQues>() {
          @Override
          public int compare(QuesbkQues o1, QuesbkQues o2) {
            return o1.getId().intValue() - o2.getId().intValue();
          }
        });
        if (StringUtils.isNotBlank(param.getLastQuesId())) {
          List<QuesbkQues> unExamLstBak = Lists.newArrayList();
          for (QuesbkQues ques : unExamLst) {
            if (ques.getId() > Long.parseLong(param.getLastQuesId())) {
              unExamLstBak.add(ques);
            }
          }
          unExamLst = unExamLstBak;
        }
        int unExamCount = getCompareCount(unExamLst.size(), rule.getQuestionNum(), 1.0);
        addAll(quesLst, getShuffleSubLst(unExamLst, unExamCount));
      } else {
        int wrongCount = getCompareCount(wrongLst.size(), rule.getQuestionNum(), 0.4);
        int unExamCount =
            getCompareCount(unExamLst.size(), rule.getQuestionNum() - wrongCount, 1.0);
        int normalCount =
            getCompareCount(normalLst.size(), rule.getQuestionNum() - wrongCount - unExamCount, 1.0);
        addAll(quesLst, getShuffleSubLst(wrongLst, wrongCount));
        addAll(quesLst, getShuffleSubLst(unExamLst, unExamCount));
        addAll(quesLst, getShuffleSubLst(normalLst, normalCount));
        Collections.shuffle(quesLst);
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
