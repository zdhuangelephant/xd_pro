package com.xiaodou.course.vo.product;

import java.sql.Timestamp;
import java.util.Date;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.user.UserSignRecordModel;
import com.xiaodou.course.model.user.UserSignStatistic;
import com.xiaodou.course.util.SignUtil;

import lombok.Data;

/**
 * @name SignCard CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月9日
 * @description 打卡记录
 * @version 1.0
 */
@Data
public class SignCard {
	/** signId 打卡记录ID */
	private Long signId;
	/** learnTime 学习时长 */
	private Long learnTime;
	/** targetTime 目标时长 */
	private Long targetTime;
	/** desc 描述文字 */
	private String desc = SignUtil.getDesc(0);
	/** totalDay 总天数 */
	private Integer totalDay = 0;
	/** continDay 连续天数 */
	private Integer continDay = 0;
	/** state 状态 0 不能打卡 1 未打卡 2 已打卡 */
	private Integer state = CourseConstant.CANT_SING;

	public void setStatistic(UserSignStatistic statistic) {
		if (null == statistic)
			return;
		this.totalDay = statistic.getTotalSign();
		String currentDate = DateUtil.SDF_YMD.format(new Date());
		String lastDate = DateUtil.SDF_YMD.format(new Timestamp(DateUtil
				.getTimesmorning(-1)));
		if (currentDate.equals(statistic.getUpdateTime())
				|| lastDate.equals(statistic.getUpdateTime())) {
			this.continDay = statistic.getContinSign();
			this.desc = SignUtil.getDesc(statistic.getContinSign());
		}
	}

	public void setRecord(UserSignRecordModel signRecord) {
		if (null == signRecord)
			return;
		this.learnTime = signRecord.getLearnTime();
		this.targetTime = signRecord.getTargetTime();
		this.state = CourseConstant.HAS_SING;
	}

	public Long getSignId() {
		return signId;
	}

	public void setSignId(Long signId) {
		this.signId = signId;
	}

	public Long getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(Long learnTime) {
		this.learnTime = learnTime;
	}

	public Long getTargetTime() {
		return targetTime;
	}

	public void setTargetTime(Long targetTime) {
		this.targetTime = targetTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(Integer totalDay) {
		this.totalDay = totalDay;
	}

	public Integer getContinDay() {
		return continDay;
	}

	public void setContinDay(Integer continDay) {
		this.continDay = continDay;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
