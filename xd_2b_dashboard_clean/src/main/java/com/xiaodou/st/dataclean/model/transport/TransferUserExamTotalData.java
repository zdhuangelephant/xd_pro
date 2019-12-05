package com.xiaodou.st.dataclean.model.transport;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.collect.Lists;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataUserScorePointRecord;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name TransferUserExamTotalData
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 用户总得分
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferUserExamTotalData extends BaseTransferModel {
	/** userId 用户ID */
	@NotEmpty
	private String userId;
	/** typeCode 专业码值 */
	@NotEmpty
	private String typeCode;
	/** courseId 产品ID */
	@NotEmpty
	private Long courseId;
	/** totalQues 总答题数 */
	@NotEmpty
	private Integer totalQues;
	/** totalRank */
	@NotEmpty
	private Integer totalRank;
	/** rightQues */
	@NotEmpty
	private Integer rightQues;
	/** rightRank */
	@NotEmpty
	private Integer rightRank;
	/** score */
	@NotEmpty
	private Double score;
	/** rightPercent */
	@NotEmpty
	private String rightPercent;

	/* 章 */
	private List<ChapterNode> chapterNodeList;
	/* 卷子 */
	private List<FinalExamNode> finalExamNodeList;
	
	private List<RawDataUserScorePointRecord> userScorePointRecordList = Lists.newArrayList();

	@Data
	public static class ChapterNode {
		// 名称内容
		private String indexName;
		// 權重
		private Double weight;
		// 排序
		private Long order;
		// 得分
		private Double score;
		public String getIndexName() {
			return indexName;
		}
		public void setIndexName(String indexName) {
			this.indexName = indexName;
		}
		public Double getWeight() {
			return weight;
		}
		public void setWeight(Double weight) {
			this.weight = weight;
		}
		public Long getOrder() {
			return order;
		}
		public void setOrder(Long order) {
			this.order = order;
		}
		public Double getScore() {
			return score;
		}
		public void setScore(Double score) {
			this.score = score;
		}

	}

	@Data
	public static class FinalExamNode {
		// 名称内容
		private String indexName;
		// 權重
		private Double weight;
		// 排序
		private Integer order;
		// 得分
		private String score;
		public String getIndexName() {
			return indexName;
		}
		public void setIndexName(String indexName) {
			this.indexName = indexName;
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
		public String getScore() {
			return score;
		}
		public void setScore(String score) {
			this.score = score;
		}


	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Integer getTotalQues() {
		return totalQues;
	}

	public void setTotalQues(Integer totalQues) {
		this.totalQues = totalQues;
	}

	public Integer getTotalRank() {
		return totalRank;
	}

	public void setTotalRank(Integer totalRank) {
		this.totalRank = totalRank;
	}

	public Integer getRightQues() {
		return rightQues;
	}

	public void setRightQues(Integer rightQues) {
		this.rightQues = rightQues;
	}

	public Integer getRightRank() {
		return rightRank;
	}

	public void setRightRank(Integer rightRank) {
		this.rightRank = rightRank;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getRightPercent() {
		return rightPercent;
	}

	public void setRightPercent(String rightPercent) {
		this.rightPercent = rightPercent;
	}

	public List<ChapterNode> getChapterNodeList() {
		return chapterNodeList;
	}

	public void setChapterNodeList(List<ChapterNode> chapterNodeList) {
		this.chapterNodeList = chapterNodeList;
	}

	public List<FinalExamNode> getFinalExamNodeList() {
		return finalExamNodeList;
	}

	public void setFinalExamNodeList(List<FinalExamNode> finalExamNodeList) {
		this.finalExamNodeList = finalExamNodeList;
	}

	public List<RawDataUserScorePointRecord> getUserScorePointRecordList() {
		return userScorePointRecordList;
	}

	public void setUserScorePointRecordList(List<RawDataUserScorePointRecord> userScorePointRecordList) {
		this.userScorePointRecordList = userScorePointRecordList;
	}

}
