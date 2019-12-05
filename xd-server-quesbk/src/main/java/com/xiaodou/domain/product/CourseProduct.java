package com.xiaodou.domain.product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.domain.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
public class CourseProduct extends BaseEntity {

	/** id 主键 */
	@Column(isMajor = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
	private Long id;

	// 专业ID
	private Integer categoryId;

	// 产品名
	private String name;

	// 简介
	private String briefInfo;

	// 详情
	private String detail;

	// 图片地址
	private String imageUrl;

	// 当前报名人数
	private Integer currApplyCount;

	// 报名人数上线
	private Integer totalApplyCount;

	// 开始申请时间
	private Timestamp beginApplyTime;

	// 申请结束时间
	private Timestamp endApplyTime;

	// 原价
	private BigDecimal originalAmount;

	// 优惠价
	private BigDecimal payAmount;

	// 创建时间
	private Timestamp createTime;

	// 更新时间
	private Timestamp updateTime;

	// misc 杂项
	private String misc;

	// 是否显示
	private Integer showStatus;

	// 点赞数
	private Integer praiseCount;

	// 栏目名称
	private String categoryName;

	// 产品类型
	private Integer type;

	/** moduleCourse 是否新手课程 */
	private String moduleCourse;

	// 题库设置
	private String questionBankSetting;

	// 资源产品ID
	private Integer resourceSubject;

	private String typeCode;

	/** ruleId 计分点规则ID */
	private String ruleId;

	/**
	 * 获取组卷规则框架 总分数/题型构成/比重信息
	 * 
	 * @return
	 */
	public QuesRuleDetail getQuesRuleDetail() {
		try {
			if (StringUtils.isJsonNotBlank(questionBankSetting)) {
				return FastJsonUtil.fromJson(questionBankSetting,
						QuesRuleDetail.class);
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @name @see com.xiaodou.domain.QuesRuleDetail.java
	 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
	 * 
	 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * @date 2015年8月9日
	 * @description 组卷规则框架
	 * @version 1.0
	 */
	@Data
	public static class QuesRuleDetail {
		/** totalScore 总分数 */
		private Integer totalScore;
		/**
		 * typeList 组卷问题类型细则 问题类型/问题分值/问题数量/排列顺序
		 */
		private List<QuesTypeDetail> typeList;

		public Integer getTotalScore() {
			return totalScore;
		}

		public void setTotalScore(Integer totalScore) {
			this.totalScore = totalScore;
		}

		public List<QuesTypeDetail> getTypeList() {
			return typeList;
		}

		public void setTypeList(List<QuesTypeDetail> typeList) {
			this.typeList = typeList;
		}

	}

	/**
	 * @name @see com.xiaodou.domain.QuesTypeDetail.java
	 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
	 * 
	 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * @date 2015年8月9日
	 * @description 问题类型细节
	 * @version 1.0
	 */
	/** answerType 问题类型码 */
	@Data
	public static class QuesTypeDetail {
		private Short answerType;
		/** id 问题类型ID */
		private Integer id;
		/** listOrder 排序字段 */
		private Integer listOrder;
		/** questionNum 问题数量 */
		private Integer questionNum;
		/** score 问题分值 */
		private Double score;
		/** typeName 问题类型名称 */
		private String typeName;
		public Short getAnswerType() {
			return answerType;
		}
		public void setAnswerType(Short answerType) {
			this.answerType = answerType;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getListOrder() {
			return listOrder;
		}
		public void setListOrder(Integer listOrder) {
			this.listOrder = listOrder;
		}
		public Integer getQuestionNum() {
			return questionNum;
		}
		public void setQuestionNum(Integer questionNum) {
			this.questionNum = questionNum;
		}
		public Double getScore() {
			return score;
		}
		public void setScore(Double score) {
			this.score = score;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBriefInfo() {
		return briefInfo;
	}

	public void setBriefInfo(String briefInfo) {
		this.briefInfo = briefInfo;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getCurrApplyCount() {
		return currApplyCount;
	}

	public void setCurrApplyCount(Integer currApplyCount) {
		this.currApplyCount = currApplyCount;
	}

	public Integer getTotalApplyCount() {
		return totalApplyCount;
	}

	public void setTotalApplyCount(Integer totalApplyCount) {
		this.totalApplyCount = totalApplyCount;
	}

	public Timestamp getBeginApplyTime() {
		return beginApplyTime;
	}

	public void setBeginApplyTime(Timestamp beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}

	public Timestamp getEndApplyTime() {
		return endApplyTime;
	}

	public void setEndApplyTime(Timestamp endApplyTime) {
		this.endApplyTime = endApplyTime;
	}

	public BigDecimal getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(BigDecimal originalAmount) {
		this.originalAmount = originalAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getModuleCourse() {
		return moduleCourse;
	}

	public void setModuleCourse(String moduleCourse) {
		this.moduleCourse = moduleCourse;
	}

	public String getQuestionBankSetting() {
		return questionBankSetting;
	}

	public void setQuestionBankSetting(String questionBankSetting) {
		this.questionBankSetting = questionBankSetting;
	}

	public Integer getResourceSubject() {
		return resourceSubject;
	}

	public void setResourceSubject(Integer resourceSubject) {
		this.resourceSubject = resourceSubject;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						CourseProduct.class,
						"xd_course_product",
						"D:/work/workspace_xd/xd-server-quesbk/src/main/resources/conf/mybatis/product/")
				.buildXml();
	}
}
