package com.xiaodou.resources.service.quesbk.rule.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.model.product.ProductModel.QuesRuleDetail;
import com.xiaodou.resources.model.product.ProductModel.QuesTypeDetail;
import com.xiaodou.resources.model.quesbk.QuesbkExamRule;
import com.xiaodou.resources.model.quesbk.QuesbkQues;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRuleEntity;
import com.xiaodou.resources.util.StaticInfoProp;
import com.xiaodou.summer.sceduling.concurrent.SummerTask;

/**
 * @name @see com.xiaodou.service.rule.model.RuleEntity.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
 * @date 2015年11月17日
 * @description 根据出题规则组织试题实体
 * @version 1.0
 */
public abstract class RuleEntity implements IRuleEntity<RuleEntityParam> {

  /**
   * @description 题目优先级
   * @version 1.0
   */
  public enum PriorQues {
    UNEXAM("1"), WRONG("2");
    PriorQues(String code) {
      this.code = code;
    }

    private String code;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

  }

  public RuleEntity(QuesbkExamRule examRule) {
    if (null == examRule) return;
    RuleDetail fromJson = FastJsonUtil.fromJson(examRule.getRuleDetail(), RuleDetail.class);
    this.ruleDetail = fromJson;
  }

  private RuleDetail ruleDetail;

  public RuleDetail getRuleDetail() {
    return ruleDetail;
  }

  public void setRuleDetail(RuleDetail ruleDetail) {
    this.ruleDetail = ruleDetail;
  }

  @Override
  public Integer getWeight() {
    // TODO 待实现
    return null;
  }

  @Override
  public List<Object> getQuestions(RuleEntityParam param) throws InterruptedException {
    if (ruleDetail == null) ruleDetail = new RuleDetail();
    if (ruleDetail.getItemList().size() == 0) {
      List<Rule> initDefaultRule = initDefaultRule(param);
      if (null != initDefaultRule && initDefaultRule.size() > 0)
        ruleDetail.getItemList().addAll(initDefaultRule);
    }
    final List<Object> quesIds = Lists.newArrayList();
    Set<Long> quesIdSet = Sets.newHashSet();
    ProductModel product = param.getService().queryProduct(param.getCourseId());
    if (null == product) return quesIds;
    QuesRuleDetail quesRuleDetail = product.getQuesRuleDetail();
    if (null == quesRuleDetail) return quesIds;
    final QCollection qc = new QCollection(quesRuleDetail.getTypeList());
    String uid = param.getUid();
    if (ruleDetail.getItemList().size() > 1) {
      processRuleList(param, qc._quesVector, ruleDetail.getItemList(), uid);
    } else if (ruleDetail.getItemList().size() > 0) {
      Rule rule = ruleDetail.getItemList().get(0);
      processRule(rule, param, qc._quesVector, uid);
    } else {
      processRule(null, param, qc._quesVector, uid);
    }
    qc.shuffle();
    for (final QuesTypeDetail qtype : qc._typeList) {
      List<QuesbkQues> quesLst = qc._quesMap.get(qtype.getId());
      if (null == quesLst || quesLst.size() == 0) continue;
      if (qc._count > 1) {
        quesIds.add(qtype.getTypeName()
            + QuesBaseConstant.QUES_TYPE_SPLIT
            + String.format(QuesBaseConstant.QUES_TYPE_DESC_TEMPLATE, qtype.getScore(),
                quesLst.size()));
      }
      for (QuesbkQues ques : quesLst) {
        ques.setQuestionTypeName(qtype.getTypeName());
        ques.setQuestionTypeScore(qtype.getScore());
      }
      Collections.sort(quesLst, new Comparator<QuesbkQues>() {
        @Override
        public int compare(QuesbkQues o1, QuesbkQues o2) {
          return o1.getId().intValue() - o2.getId().intValue();
        }
      });
      for (QuesbkQues ques : quesLst) {
        if (quesIdSet.contains(ques.getId())) continue;
        quesIdSet.add(ques.getId());
        quesIds.add(ques);
      }
    }
    return quesIds;
  }

  protected void processRuleList(final RuleEntityParam param, final List<QuesbkQues> quesLst,
      List<Rule> ruleList, final String uid) throws InterruptedException {
    final CountDownLatch reachGate = new CountDownLatch(ruleDetail.getItemList().size());
    for (final Rule rule : ruleDetail.getItemList()) {
      param.getExcutor().execute(new SummerTask() {
        @Override
        public void run() {
          try {
            processRule(rule, param, quesLst, uid);
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
    reachGate.await();
  }

  protected void processRule(Rule rule, RuleEntityParam param, List<QuesbkQues> quesLst, String uid) {
    if (null != rule && null != rule.getProductChapterId()) {
      List<ProductItemModel> itemList =
          param.getService().queryItemList(param.getCourseId(),
              rule.getProductChapterId().toString());
      if (null != itemList && itemList.size() > 0) {
        List<String> sItemList = Lists.newArrayList();
        for (ProductItemModel item : itemList) {
          if (null == item.getId()) continue;
          sItemList.add(item.getId().toString());
        }
        if (sItemList.size() > 0) {
          rule.setItemList(sItemList);
          rule.setProductChapterId(null);
        }
      }
    }
    _processRule(rule, param, quesLst);
  }

  protected void _processRule(Rule rule, RuleEntityParam param, List<QuesbkQues> quesLst) {
    List<QuesbkQues> wrongLst = Lists.newArrayList();
    List<QuesbkQues> unExamLst = Lists.newArrayList();
    List<QuesbkQues> normalLst = Lists.newArrayList();
    List<QuesbkQues> quesList = param.getService().queryQuesList(rule);
    for (QuesbkQues ques : quesList) {
      if (ques.getMyRightTimes() < StaticInfoProp.getWrongLimit()
          && ques.getStatistic().getMyWrongTimes() > 0) {
        wrongLst.add(ques);
      } else if (ques.getStatistic().getMyExamTimes() == 0) {
        unExamLst.add(ques);
      } else {
        normalLst.add(ques);
      }
    }
    int unExamCount = getCompareCount(unExamLst.size(), rule.getQuestionNum(), 1.0);
    int wrongCount = getCompareCount(wrongLst.size(), rule.getQuestionNum() - unExamCount, 1.0);
    int normalCount =
        getCompareCount(normalLst.size(), rule.getQuestionNum() - wrongCount - unExamCount, 1.0);
    addAll(quesLst, getShuffleSubLst(wrongLst, wrongCount));
    addAll(quesLst, getShuffleSubLst(unExamLst, unExamCount));
    addAll(quesLst, getShuffleSubLst(normalLst, normalCount));
    Collections.shuffle(quesLst);
  }

  protected int getCompareCount(int counta, int countb, double ratio) {
    return (int) (counta > (countb * ratio) ? (countb * ratio) : counta);
  }

  protected abstract List<Rule> initDefaultRule(RuleEntityParam param);

  protected <T> List<T> getShuffleSubLst(List<T> lst, Integer count) {
    if (lst.size() > 0) {
      if (lst.size() > count)
        Collections.shuffle(lst);
      else
        count = lst.size();
      return lst.subList(0, count);
    }
    return null;
  }

  /**
   * @name QCollection CopyRright (c) 2015 by <a
   *       href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年7月8日
   * @description 本类专用集合类
   * @version 1.0
   */
  public static class QCollection {
    private List<QuesbkQues> _quesVector = new Vector<QuesbkQues>();
    private List<QuesTypeDetail> _typeList = Lists.newArrayList();
    private Map<Integer, List<QuesbkQues>> _quesMap = Maps.newHashMap();
    private Integer _count = 0;

    private void shuffle() {
      if (null != _quesVector && _quesVector.size() > 0) {
        for (QuesbkQues ques : _quesVector) {
          if (ques != null && ques.getId() != null && ques.getQuestionType() != null) {
            List<QuesbkQues> list = _quesMap.get(ques.getQuestionType().intValue());
            if (list.size() == 0) _count++;
            list.add(ques);
          }
        }
      }
    }

    public QCollection(List<QuesTypeDetail> quesTypeList) {
      if (null != quesTypeList && quesTypeList.size() > 0) {
        _typeList.addAll(quesTypeList);
        Collections.sort(_typeList, new Comparator<QuesTypeDetail>() {
          @Override
          public int compare(QuesTypeDetail o1, QuesTypeDetail o2) {
            if (o1 == null || null == o1.getListOrder() || o1.getListOrder() == -1)
              return 1;
            else if (o2 == null || null == o2.getListOrder() || o2.getListOrder() == -1)
              return -1;
            else
              return o1.getListOrder().compareTo(o2.getListOrder());
          }
        });
        for (QuesTypeDetail qt : _typeList) {
          _quesMap.put(qt.getId(), new Vector<QuesbkQues>());
        }
      }
    }
  }

  protected <T> void addAll(List<T> dstLst, List<T> srcLst) {
    if (null != srcLst && srcLst.size() > 0) {
      dstLst.addAll(srcLst);
    }
  }

}
