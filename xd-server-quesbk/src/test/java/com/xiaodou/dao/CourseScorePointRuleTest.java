package com.xiaodou.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.ProductConstants;
import com.xiaodou.dao.product.ProductScorePointRuleDao;
import com.xiaodou.domain.product.ProductScorePointRule;
import com.xiaodou.domain.product.ProductScorePointRule.RuleInfo;

public class CourseScorePointRuleTest extends BaseUnitils {

  @SpringBean("productScorePointRuleDao")
  ProductScorePointRuleDao productScorePointRuleDao;

  @Test
  public void testInsertModule2Rule() {
    ProductScorePointRule rule = new ProductScorePointRule();
    rule.setId(UUID.randomUUID().toString());
    rule.setRuleName("北京默认规则");
    List<RuleInfo> ruleInfoList = Lists.newArrayList();
    {
      RuleInfo ruleInfo = new RuleInfo();
      ruleInfo.setCode(ProductConstants.RULE_TYPE_ITEM_PRACTICE);
      ruleInfo.setAbtractInfo("节练习");
      ruleInfo.setMoreInfo("节练习成绩");
      ruleInfo.setWeight(0.00d);
      ruleInfo.setOrder(1);
      ruleInfoList.add(ruleInfo);
    }
    {
      RuleInfo ruleInfo = new RuleInfo();
      ruleInfo.setCode(ProductConstants.RULE_TYPE_CHAPTER_PRACTICE);
      ruleInfo.setAbtractInfo("章总结");
      ruleInfo.setMoreInfo("章总结成绩");
      ruleInfo.setWeight(0.49d);
      ruleInfo.setOrder(1);
      ruleInfoList.add(ruleInfo);
    }
    {
      RuleInfo ruleInfo = new RuleInfo();
      ruleInfo.setCode(ProductConstants.RULE_TYPE_FINAL_EXAM);
      ruleInfo.setAbtractInfo("期末测试");
      ruleInfo.setWeight(0.21d);
      ruleInfo.setOrder(1);
      ruleInfoList.add(ruleInfo);
    }
    {
      RuleInfo ruleInfo = new RuleInfo();
      ruleInfo.setCode(ProductConstants.RULE_TYPE_STANDARD_MISSION);
      ruleInfo.setAbtractInfo("学习过程");
      ruleInfo.setMoreInfo("学习任务完成度");
      ruleInfo.setWeight(0.30d);
      ruleInfo.setOrder(1);
      ruleInfoList.add(ruleInfo);
    }
    {
      RuleInfo ruleInfo = new RuleInfo();
      ruleInfo.setCode(ProductConstants.RULE_TYPE_LEAK_FILLING);
      ruleInfo.setAbtractInfo("查漏补缺");
      ruleInfo.setMoreInfo("查漏补缺成绩");
      ruleInfo.setWeight(1.00d);
      ruleInfo.setOrder(1);
      ruleInfoList.add(ruleInfo);
    }
    {
      RuleInfo ruleInfo = new RuleInfo();
      ruleInfo.setCode(ProductConstants.RULE_TYPE_CAT);
      ruleInfo.setAbtractInfo("机考");
      ruleInfo.setMoreInfo("上机考试成绩");
      ruleInfo.setWeight(0.00d);
      ruleInfo.setOrder(1);
      ruleInfoList.add(ruleInfo);
    }
    rule.setRuleDetail(FastJsonUtil.toJson(ruleInfoList));
    rule.setRuleDesc("北京地区的规则");
    rule.setCreateTime(new Timestamp(System.currentTimeMillis()));
    rule.setModifyTime(new Timestamp(System.currentTimeMillis()));
    productScorePointRuleDao.addEntity(rule);
  }
}
