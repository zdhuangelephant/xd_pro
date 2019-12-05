package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;

public class LearnRecordResponse extends BaseResponse {

	public LearnRecordResponse(ResultType type) {
		super(type);
	}

	private String totalScore = "99";// 总成绩
	private List<Chapter> chapterList = Lists.newArrayList(new Chapter(),
			new Chapter());

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public List<Chapter> getChapterList() {
		return chapterList;
	}

	public void setChapterList(List<Chapter> chapterList) {
		this.chapterList = chapterList;
	}

	public static class Chapter {
		private String chapterId = "1";
		private String chapterName = "aa";
		private String chapterIndex = "3";
		private String score = "99"; // 章节成绩
		private String practiseAmount = "33"; // 练习量
		private String rightPercent = "0.88"; // 正确率
		private String trend = "1"; // 趋势

		public String getChapterId() {
			return chapterId;
		}

		public void setChapterId(String chapterId) {
			this.chapterId = chapterId;
		}

		public String getChapterName() {
			return chapterName;
		}

		public void setChapterName(String chapterName) {
			this.chapterName = chapterName;
		}

		public String getChapterIndex() {
			return chapterIndex;
		}

		public void setChapterIndex(String chapterIndex) {
			this.chapterIndex = chapterIndex;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getPractiseAmount() {
			return practiseAmount;
		}

		public void setPractiseAmount(String practiseAmount) {
			this.practiseAmount = practiseAmount;
		}

		public String getRightPercent() {
			return rightPercent;
		}

		public void setRightPercent(String rightPercent) {
			this.rightPercent = rightPercent;
		}

		public String getTrend() {
			return trend;
		}

		public void setTrend(String trend) {
			this.trend = trend;
		}
	}
}
