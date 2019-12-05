package com.xiaodou.engine.scorepoint;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.manager.facade.QuesOperationFacade;

/**
 * @name @see com.xiaodou.engine.scorepoint.ChapterScorePointCaculator.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月16日
 * @description 章总结得分点计算器
 * @version 1.0
 */
public class ChapterScorePointCaculator extends BaseScorePointCaculator {

	public ChapterScorePointCaculator(UserScorePointRecord record) {
		super(record);
	}

	/** totalChapterCount 节总数 */
	private Integer totalChapterCount = 0;

	/** completeChapterCount 节完成数 */
	private Integer completeChapterCount = 0;

	/** totalChapterScore 节总分 */
	private Double totalChapterScore = 0d;

	/** caculateChapterCount 节计算节点数 */
	private Integer caculateChapterCount = 0;

	/** totalChapterWeight 节总计算权重 */
	private Double totalChapterWeight = 1.0d;

	@Override
	public void caculateScore(QuesOperationFacade facade) {
		Map<String, Object> cond = Maps.newHashMap();
		cond.put("userId", getRecord().getUserId());
		cond.put("courseId", getRecord().getProductId());
		List<UserChapterRecord> chapterRecordList = facade
				.queryUserChapterRecord(cond);
		Map<Long, UserChapterRecord> chapterRecordMap = buildUserChapterRecordMap(chapterRecordList);
		List<CourseProductItem> chapterList = facade
				.queryChapterList(getRecord().getProductId().toString());
		if (null != chapterList && chapterList.size() > 0) {
			this.totalChapterCount = chapterList.size();
			for (CourseProductItem chapter : chapterList) {
				if (null == chapter || null == chapter.getParentId()
						|| 0 != chapter.getParentId())
					continue;
				UserChapterRecord chapterScore = chapterRecordMap.get(chapter
						.getId());
				this.caculateChapterCount++;
				if (null == chapterScore)
					continue;
				if (chapterScore.getScore() >= 60) {
					this.completeChapterCount++;
				
				}
				// 判断章是否有单独权重, 若有, 移除平均计算逻辑, 单独按其权重计算入总成绩, 同时移除其权重
				if (null != chapter.getWeight() && chapter.getWeight() > 0) {
					setScore(getScore() + chapterScore.getScore()
							* chapter.getWeight());
					this.caculateChapterCount--;
					this.totalChapterWeight -= chapter.getWeight();
				} else {
					this.totalChapterScore += chapterScore.getScore();
				}
			}
		}
		setScore(getScore() + caculateChapterAvgScore());
		setCompletePercent(caculateChapterCompletePercent());
	}

	/**
	 * <p>
	 * 计算章平均成绩分
	 * </p>
	 * 章计算节点总分 / 章计算节点个数 * 章计算节点权重
	 * 
	 * @return itemCaculateScore
	 */
	private Double caculateChapterAvgScore() {
		if (caculateChapterCount == 0) {
			return 0d;
		}
		return totalChapterScore / caculateChapterCount * totalChapterWeight;
	}

	/**
	 * 计算节完成度
	 * 
	 * @return
	 */
	private Double caculateChapterCompletePercent() {
		if (totalChapterCount == 0) {
			return 0d;
		}
		return completeChapterCount / totalChapterCount * 1d;
	}

	private Map<Long, UserChapterRecord> buildUserChapterRecordMap(
			List<UserChapterRecord> userChapterRecordList) {
		if (null == userChapterRecordList || userChapterRecordList.size() == 0) {
			return null;
		}
		Map<Long, UserChapterRecord> userChapterRecordMap = Maps.newHashMap();
		for (UserChapterRecord record : userChapterRecordList) {
			if(record.getItemId()!=null&&record.getChapterId()!=null&&record.getItemId().toString().equals(record.getChapterId().toString())){
				userChapterRecordMap.put(record.getChapterId(), record);
			}
		}
		return userChapterRecordMap;
	}

	public Integer getTotalChapterCount() {
		return totalChapterCount;
	}

	public void setTotalChapterCount(Integer totalChapterCount) {
		this.totalChapterCount = totalChapterCount;
	}

	public Integer getCompleteChapterCount() {
		return completeChapterCount;
	}

	public void setCompleteChapterCount(Integer completeChapterCount) {
		this.completeChapterCount = completeChapterCount;
	}

	public Double getTotalChapterScore() {
		return totalChapterScore;
	}

	public void setTotalChapterScore(Double totalChapterScore) {
		this.totalChapterScore = totalChapterScore;
	}

	public Integer getCaculateChapterCount() {
		return caculateChapterCount;
	}

	public void setCaculateChapterCount(Integer caculateChapterCount) {
		this.caculateChapterCount = caculateChapterCount;
	}

	public Double getTotalChapterWeight() {
		return totalChapterWeight;
	}

	public void setTotalChapterWeight(Double totalChapterWeight) {
		this.totalChapterWeight = totalChapterWeight;
	}

}
