package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;

public class ChapterListResponse extends BaseResponse {

	public ChapterListResponse(ResultType type) {
		super(type);
	}

	private String courseName;

	private List<Chapter> chapterList = Lists.newArrayList();

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public List<Chapter> getChapterList() {
		return chapterList;
	}

	public void setChapterList(List<Chapter> chapterList) {
		this.chapterList = chapterList;
	}

	public static class Chapter {
		private String chapterId;
		private String chapterName;
		private String chapterIndex;
		private String starLevel; // 星级
		private String status; // 状态 0 未解锁 1 已解锁 2 已完成

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

		public String getStarLevel() {
			return starLevel;
		}

		public void setStarLevel(String starLevel) {
			this.starLevel = starLevel;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	}

}
