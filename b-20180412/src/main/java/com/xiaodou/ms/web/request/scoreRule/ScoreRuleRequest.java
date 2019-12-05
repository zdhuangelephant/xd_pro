package com.xiaodou.ms.web.request.scoreRule;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name ScoreRuleRequest CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月16日
 * @description 接受计分规则 字段的参数
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ScoreRuleRequest extends BaseRequest {

	@Column(isMajor = true)
	private String id;

	private String ruleName;

	private String ruleDetail;

	private String ruleDesc;

	private Long ruleId;

	private Timestamp createTime;

	private Timestamp modifyTime;

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

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
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
