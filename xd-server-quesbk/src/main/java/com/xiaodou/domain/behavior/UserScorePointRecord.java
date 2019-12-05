package com.xiaodou.domain.behavior;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.domain.BaseEntity;
import com.xiaodou.domain.product.ProductScorePointRule.RuleInfo;

/**
 * @name @see com.xiaodou.domain.behavior.UserScorePointRecord.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月13日
 * @description 慕享用户积分点条目明细表
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserScorePointRecord extends BaseEntity {

	/** id 主键 */
	@Column(isMajor = true, sortBy = true, listValue = true)
	private String id;
	/** ruleType 规则类型 */
	@Column(listValue = true)
	private Short ruleType;
	/** module 地域模块 */
	private String module;
	/** typeCode 专业码值(非持久化字段) */
	@Column(persistent = false)
	private String typeCode;
	/** productId 产品ID */
	private Long productId;
	/** weight 所占权重(非持久化字段) */
	@Column(persistent = false)
	private RuleInfo ruleInfo;
	/** userId 用户ID */
	private Long userId;
	/** score 计分点成绩 */
	private Double score = 0d;
	/** completePercent 计分点完成度 */
	private Double completePercent = 0d;
	/** createTime 创建时间 */
	@Column(betweenScope = true, sortBy = true)
	private Timestamp createTime;
	/** modifyTime 修改时间 */
	@Column(betweenScope = true, sortBy = true)
	private Timestamp modifyTime;

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						UserScorePointRecord.class,
						"xd_user_score_point_record",
						"D:/MyWorkSpace/MyEclipse8.5/xd-server-quesbk/src/main/resources/conf/mybatis/behavior")
				.buildXml();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Short getRuleType() {
		return ruleType;
	}

	public void setRuleType(Short ruleType) {
		this.ruleType = ruleType;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public RuleInfo getRuleInfo() {
		return ruleInfo;
	}

	public void setRuleInfo(RuleInfo ruleInfo) {
		this.ruleInfo = ruleInfo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getCompletePercent() {
		return completePercent;
	}

	public void setCompletePercent(Double completePercent) {
		this.completePercent = completePercent;
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
