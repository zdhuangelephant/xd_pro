package com.xiaodou.server.mapi.response.quesbk;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see
 *       com.xiaodou.server.mapi.response.quesbk.ScorePointRuleResponse.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月24日
 * @description 计分点规则响应类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScorePointRuleResponse extends BaseResponse {

	public ScorePointRuleResponse() {
	}

	public ScorePointRuleResponse(ResultType resultType) {
		super(resultType);
	}

	public ScorePointRule getScorePointRule() {
		return scorePointRule;
	}

	public void setScorePointRule(ScorePointRule scorePointRule) {
		this.scorePointRule = scorePointRule;
	}

	/** scorePointRule 计分点规则类 */
	private ScorePointRule scorePointRule;

	/**
	 * @name @see
	 *       com.xiaodou.server.mapi.response.quesbk.ScorePointRuleResponse.java
	 * @CopyRright (c) 2018 by Corp.XiaodouTech
	 * 
	 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * @date 2018年4月24日
	 * @description 计分点规则类
	 * @version 1.0
	 */
	@Data
	public class ScorePointRule {
		/** id 主键ID */
		private String id;
		/** ruleName 规则名称 */
		private String ruleName;
		/** ruleDetail 规则明细 */
		private String ruleDetail;
		/** ruleDesc 规则描述 */
		private String ruleDesc;
		/** createTime 创建时间 */
		@Column(betweenScope = true, sortBy = true)
		private Timestamp createTime;
		/** modifyTime 修改时间 */
		@Column(betweenScope = true, sortBy = true)
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
}
