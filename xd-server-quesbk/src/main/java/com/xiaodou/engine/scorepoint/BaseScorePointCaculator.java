package com.xiaodou.engine.scorepoint;

import lombok.Data;

import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.manager.facade.QuesOperationFacade;

/**
 * @name @see com.xiaodou.engine.scorepoint.BaseScorePointCaculator.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月16日
 * @description 基础得分点计算器
 * @version 1.0
 */
@Data
public abstract class BaseScorePointCaculator {

	/** record 积分点记录 */
	private UserScorePointRecord record;

	/** score 得分 */
	private Double score = 0d;

	/** completePercent 完成度 */
	private Double completePercent = 0d;

	public BaseScorePointCaculator(UserScorePointRecord record) {
		this.record = record;
	}

	/**
	 * 计算成绩
	 * 
	 * @return 成绩
	 */
	public abstract void caculateScore(QuesOperationFacade facade);

	public UserScorePointRecord getRecord() {
		return record;
	}

	public void setRecord(UserScorePointRecord record) {
		this.record = record;
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

}
