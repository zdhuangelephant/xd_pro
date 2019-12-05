package com.xiaodou.domain.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 测验规则
 * </p>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @version 1.0
 * @date 2015年6月29日
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuesbkExamRule extends BaseEntity {
	/**
	 * 主键ID
	 */
	@Column(isMajor = true, betweenScope = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
	private Long id;

	/** name 规则名称 */
	private String name;

	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 测验类型ID
	 */
	private Integer examTypeId;

	/**
	 * 规则细节
	 */
	private String ruleDetail;

	/**
	 * 规则状态
	 */
	private Short status;

	public void setRuleDetail(String ruleDetail) {
		this.ruleDetail = ruleDetail == null ? null : ruleDetail.trim();
	}

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						QuesbkExamRule.class,
						"xd_quesbk_exam_rule",
						"F:/xdworks/xd-server-quesbk-b20180102/src/main/resources/conf/mybatis/product/")
				.buildXml();
		;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Integer getExamTypeId() {
		return examTypeId;
	}

	public void setExamTypeId(Integer examTypeId) {
		this.examTypeId = examTypeId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getRuleDetail() {
		return ruleDetail;
	}

  public void setProductId(Long productId) {
    this.productId = productId;
  }

}
