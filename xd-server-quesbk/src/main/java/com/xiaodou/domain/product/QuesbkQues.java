package com.xiaodou.domain.product;

import java.util.Date;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.domain.BaseEntity;
import com.xiaodou.domain.CourseKeyword;
import com.xiaodou.util.QuesbkUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuesbkQues extends BaseEntity {

	@Column(isMajor = true, betweenScope = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
	private Long id;

	private Long questionType;

	private String questionTypeName;

	private Double questionTypeScore;

	private String questionSrc;

	private Long chapterId;

	private String parentChapter;

	private Long courseId;

	private String quesImgUrl;

	private String keyPoint;

	/**
	 * 认知程度
	 */
	private Double cognitionLevel;

	/**
	 * 难易程度
	 */
	private Double diffcultLevel;

	private Integer myRightTimes = 0;

	private String myAnswerIds;

	private String answerIds;

	private String mdesc;

	private String manalyze;

	private String status;

	private String misc;

	private String answerList;

	private Date quesTime;

	private Short zhenti;

	private String storeId;

	private String wrongId;

	/**
	 * 用户收藏状态 0 未收藏 1 好题(默认) 2 我不会的  3 需要记忆  4 取消收藏(未收藏)
	 */
	private String storeStatus = "0";
	/**
	 * 错题状态 0 未练习 1 未掌握 2 待强化 3 已消灭 4 已移除(默认)
	 */
	private String wrongStatus = "0";

	private Statistic statistic = new Statistic();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Long questionType) {
		this.questionType = questionType;
	}

	public String getQuestionTypeName() {
		return questionTypeName;
	}

	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}

	public Double getQuestionTypeScore() {
		return questionTypeScore;
	}

	public void setQuestionTypeScore(Double questionTypeScore) {
		this.questionTypeScore = questionTypeScore;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public String getParentChapter() {
		return parentChapter;
	}

	public void setParentChapter(String parentChapter) {
		this.parentChapter = parentChapter;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Double getCognitionLevel() {
		return cognitionLevel;
	}

	public void setCognitionLevel(Double cognitionLevel) {
		this.cognitionLevel = cognitionLevel;
	}

	public Double getDiffcultLevel() {
		return diffcultLevel;
	}

	public void setDiffcultLevel(Double diffcultLevel) {
		this.diffcultLevel = diffcultLevel;
	}

	public Integer getMyRightTimes() {
		return myRightTimes;
	}

	public void setMyRightTimes(Integer myRightTimes) {
		this.myRightTimes = myRightTimes;
	}

	public String getMyAnswerIds() {
		return myAnswerIds;
	}

	public void setMyAnswerIds(String myAnswerIds) {
		this.myAnswerIds = myAnswerIds;
	}

	public Date getQuesTime() {
		return quesTime;
	}

	public void setQuesTime(Date quesTime) {
		this.quesTime = quesTime;
	}

	public Short getZhenti() {
		return zhenti;
	}

	public void setZhenti(Short zhenti) {
		this.zhenti = zhenti;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getWrongId() {
		return wrongId;
	}

	public void setWrongId(String wrongId) {
		this.wrongId = wrongId;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	public String getWrongStatus() {
		return wrongStatus;
	}

	public void setWrongStatus(String wrongStatus) {
		this.wrongStatus = wrongStatus;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	public String getQuestionSrc() {
		return questionSrc;
	}

	public String getQuesImgUrl() {
		return quesImgUrl;
	}

	public String getKeyPoint() {
		return keyPoint;
	}

	public String getAnswerIds() {
		return answerIds;
	}

	public String getMdesc() {
		return mdesc;
	}

	public String getManalyze() {
		return manalyze;
	}

	public String getStatus() {
		return status;
	}

	public String getMisc() {
		return misc;
	}

	public String getAnswerList() {
		return answerList;
	}

	public void setQuestionSrc(String questionSrc) {
		this.questionSrc = QuesbkUtil.trim(questionSrc);
	}

	public void setQuesImgUrl(String quesImgUrl) {
		this.quesImgUrl = QuesbkUtil.trim(quesImgUrl);
	}

	public void setAnswerIds(String answerIds) {
		this.answerIds = QuesbkUtil.trim(answerIds);
	}

	public void setKeyPoint(String keyPoint) {
		this.keyPoint = keyPoint == null ? null : keyPoint.trim();
	}

	public void setMdesc(String mdesc) {
		this.mdesc = mdesc == null ? null : mdesc.trim();
	}

	public void setManalyze(String manalyze) {
		this.manalyze = manalyze == null ? null : manalyze.trim();
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public void setMisc(String misc) {
		this.misc = misc == null ? null : misc.trim();
	}

	public void setAnswerList(String answerList) {
		this.answerList = answerList == null ? null : answerList.trim();
	}

	/**
	 * 考点
	 * 
	 * @author Administrator
	 */
	@Data
	public static class KeyPoint {

		public KeyPoint() {
		}

		public KeyPoint(CourseKeyword keyword) {
			setName(keyword.getName());
			setPoindId(keyword.getId().toString());
		}

		/**
		 * 考点ID
		 */
		private String poindId;
		/**
		 * 考点名称
		 */
		private String name;

		public String getPoindId() {
			return poindId;
		}

		public void setPoindId(String poindId) {
			this.poindId = poindId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	/**
	 * @Location xd-quesbase/com.xiaodou.domain.QuesbkQues.java
	 * @Description 答题统计情况
	 * @Author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
	 * @Date 下午7:30:59
	 */
	@Data
	public static class Statistic {
		/**
		 * 我的作答次数
		 */
		private Integer myExamTimes = 0;
		/**
		 * 我的做错次数
		 */
		private Integer myWrongTimes = 0;
		/**
		 * 全站作答次数
		 */
		private Integer totalExamTimes = 0;
		/**
		 * 全站做错次数
		 */
		private Integer totalWrongTimes = 0;
		/**
		 * 全站正确率
		 */
		private String totalRightPercent = "-";

		public Integer getMyExamTimes() {
			return myExamTimes;
		}

		public void setMyExamTimes(Integer myExamTimes) {
			this.myExamTimes = myExamTimes;
		}

		public Integer getMyWrongTimes() {
			return myWrongTimes;
		}

		public void setMyWrongTimes(Integer myWrongTimes) {
			this.myWrongTimes = myWrongTimes;
		}

		public Integer getTotalExamTimes() {
			return totalExamTimes;
		}

		public void setTotalExamTimes(Integer totalExamTimes) {
			this.totalExamTimes = totalExamTimes;
		}

		public Integer getTotalWrongTimes() {
			return totalWrongTimes;
		}

		public void setTotalWrongTimes(Integer totalWrongTimes) {
			this.totalWrongTimes = totalWrongTimes;
		}

		public String getTotalRightPercent() {
			return totalRightPercent;
		}

		public void setTotalRightPercent(String totalRightPercent) {
			this.totalRightPercent = totalRightPercent;
		}

	}

	@Override
	public String toString() {
		return FastJsonUtil.toJson(this);
	}

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						QuesbkQues.class,
						"xd_quesbk_ques",
						"F:/xdworks/xd-server-quesbk-b20180102/src/main/resources/conf/mybatis/product/")
				.buildXml();
		;
	}

}
