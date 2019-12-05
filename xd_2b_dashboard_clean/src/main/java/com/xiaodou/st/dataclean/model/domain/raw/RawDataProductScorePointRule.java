package com.xiaodou.st.dataclean.model.domain.raw;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @name @see
 *       com.xiaodou.st.dataclean.model.domain.raw.RawDataProductScorePointRule
 *       .java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">huangzedong</a>
 * @date 2018年4月20日
 * @description 慕享产品计分点规则表
 * @version 1.0
 */
@Data
public class RawDataProductScorePointRule {

	/** id 主键ID */
	@Column(isMajor = true, sortBy = true, listValue = true)
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
	/** scope 1.地域2.课程 */
	private Integer scope;

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

	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(RawDataProductScorePointRule.class,
				"xd_raw_data_product_score_point_rule",
				"src/main/resources/conf/mybatis/raw").buildXml();
	}

	/**
	 * @name @see
	 *       com.xiaodou.st.dataclean.model.domain.raw.RawDataProductScorePointRule
	 *       .RuleInfo.java
	 * @CopyRright (c) 2018 by Corp.XiaodouTech
	 * 
	 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">huangzedong</a>
	 * @date 2018年4月20日
	 * @description 规则条目
	 * @version 1.0
	 */
	@Data
	public static class RuleInfo {
		/** code 类型码 */
		private Short code;
		/** abtractInfo 概要描述 */
		private String abtractInfo;
		/** moreInfo 详细描述 */
		private String moreInfo;
		/** weight 权重 */
		private Double weight;
		/** order 排序 */
		private Integer order;

		public Short getCode() {
			return code;
		}

		public void setCode(Short code) {
			this.code = code;
		}

		public String getAbtractInfo() {
			return abtractInfo;
		}

		public void setAbtractInfo(String abtractInfo) {
			this.abtractInfo = abtractInfo;
		}

		public String getMoreInfo() {
			return moreInfo;
		}

		public void setMoreInfo(String moreInfo) {
			this.moreInfo = moreInfo;
		}

		public Double getWeight() {
			return weight;
		}

		public void setWeight(Double weight) {
			this.weight = weight;
		}

		public Integer getOrder() {
			return order;
		}

		public void setOrder(Integer order) {
			this.order = order;
		}

	}
}
