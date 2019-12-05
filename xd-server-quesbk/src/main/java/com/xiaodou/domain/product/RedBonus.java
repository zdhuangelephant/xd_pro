package com.xiaodou.domain.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.domain.BaseEntity;
import com.xiaodou.vo.response.CourseScore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.domain.RedBonus.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 红包
 * @version 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class RedBonus extends BaseEntity {
	/** id 主键 */
	@Column(isMajor = true, betweenScope = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
	private String id;
	/** redBonusType 红包类型 */
	private String redBonusType;
	/** module 所属模块 */
	private String module;
	/** typeCode 专业码值 */
	private String typeCode;
	/** userId 用户ID */
	private String userId;
	/** missionId 任务ID */
	private String missionId;
	/** courseId 课程ID */
	private String courseId;
	/** chapterId 章ID */
	private String chapterId;
	/** itemId 节ID */
	private String itemId;
	/** bonusDetail 红包详情 */
	private String bonusDetail;
	/** statue 状态 1 已领取 0 未领取 */
	private Short statue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRedBonusType() {
		return redBonusType;
	}

	public void setRedBonusType(String redBonusType) {
		this.redBonusType = redBonusType;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getBonusDetail() {
		return bonusDetail;
	}

	public void setBonusDetail(String bonusDetail) {
		this.bonusDetail = bonusDetail;
	}

	public Short getStatue() {
		return statue;
	}

	public void setStatue(Short statue) {
		this.statue = statue;
	}

	@Data
	public static class CourseBonus {
		/** originalScore 原始分 */
		private Long originalScore;
		/** bonusScore 红包分 */
		private Long bonusScore;
		/** other1 干扰分 */
		private Long other1;
		/** other2 干扰分 */
		private Long other2;
		/** courseScore 课程加分详情 */
		private CourseScore courseScore;

		public CourseBonus() {
		}

		public Long getOriginalScore() {
			return originalScore;
		}

		public void setOriginalScore(Long originalScore) {
			this.originalScore = originalScore;
		}

		public Long getBonusScore() {
			return bonusScore;
		}

		public void setBonusScore(Long bonusScore) {
			this.bonusScore = bonusScore;
		}

		public Long getOther1() {
			return other1;
		}

		public void setOther1(Long other1) {
			this.other1 = other1;
		}

		public Long getOther2() {
			return other2;
		}

		public void setOther2(Long other2) {
			this.other2 = other2;
		}

		public CourseScore getCourseScore() {
			return courseScore;
		}

		public void setCourseScore(CourseScore courseScore) {
			this.courseScore = courseScore;
		}

	}

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						RedBonus.class,
						"xd_quesbk_red_bonus",
						"F:/xdworks/xd-server-quesbk-b20180102/src/main/resources/conf/mybatis/product/")
				.buildXml();
		;
	}
}
