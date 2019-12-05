package com.xiaodou.ms.model.score;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.util.StringUtils;

import lombok.Data;

/**
 * @name RegionModel CopyRright (c) 2018 by
 *       <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月12日
 * @description 计分点规则
 * @version 1.0
 */
@Data
public class ScoreRuleModel {

  @Column(isMajor = true)
  private String id;

  private String ruleName;

  private String ruleDetail;

  private String ruleDesc;
  
  private Integer scope;

  private Map<String,RuleInfo> ruleInfoMap = new TreeMap<String,RuleInfo>();

  private Timestamp createTime;

  private Timestamp modifyTime;

  public void initModuleInfo(String ruleDetail) {
    if (StringUtils.isJsonNotBlank(ruleDetail)) {
      JSONArray jsonArray = JSONArray.parseArray(ruleDetail);
      System.out.println("jsonArray"+jsonArray);
      
      List<RuleInfo> mInfo = JSON.parseArray(ruleDetail, RuleInfo.class);
      for (int i = 0; i < mInfo.size(); i++) {
        ruleInfoMap.put(String.valueOf(i),mInfo.get(i) );
      }
    }
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(ScoreRuleModel.class, "xd_course_product_score_point_rule",
        "F:\\workspace\\b-20180412\\src\\main\\resources\\conf\\mybatis\\scoreRule").buildXml();
  }

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getRuleName() {
	return ruleName;
}

public void setRuleName(String ruleName) {
	this.ruleName = ruleName;
}

public String getRuleDetail() {
	return ruleDetail;
}

public void setRuleDetail(String ruleDetail) {
	this.ruleDetail = ruleDetail;
}

public String getRuleDesc() {
	return ruleDesc;
}

public void setRuleDesc(String ruleDesc) {
	this.ruleDesc = ruleDesc;
}

public Integer getScope() {
	return scope;
}

public void setScope(Integer scope) {
	this.scope = scope;
}

public Map<String, RuleInfo> getRuleInfoMap() {
	return ruleInfoMap;
}

public void setRuleInfoMap(Map<String, RuleInfo> ruleInfoMap) {
	this.ruleInfoMap = ruleInfoMap;
}

public Timestamp getCreateTime() {
	return createTime;
}

public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}

public Timestamp getModifyTime() {
	return modifyTime;
}

public void setModifyTime(Timestamp modifyTime) {
	this.modifyTime = modifyTime;
}

}
