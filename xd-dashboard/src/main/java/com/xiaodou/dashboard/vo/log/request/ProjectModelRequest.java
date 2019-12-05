package com.xiaodou.dashboard.vo.log.request;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.dashboard.model.alarm.domain.AlarmEventDo;
import com.xiaodou.dashboard.model.log.ProjectModel;

import lombok.Data;

/**
 * @name @see com.xiaodou.dashboard.vo.log.request.ProjectModelRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年6月12日
 * @description 项目数据请求模型
 * @version 1.0
 */
@Data
public class ProjectModelRequest {
	/** id 主键ID */
	private String projectId;
	/** alarmEventId 报警事件ID */
	private String alarmEventId;
	/** projectName 项目名称 */
	private String projectName;
	/** excutePoint 执行点 */
	private String excutePoint;
	/** rate 频率 */
	private Integer rate;
	/** threshold 阈值 */
	private Integer threshold;
	/** alarmPolicy 所属报警策略 */
	private String alarmPolicyId;
	/** page 页码 */
	private Integer page = 1;
	/**0错误数据1正常数据*/
	private String errorData="1";

	public ProjectModel getProjectModel() {
		ProjectModel project = new ProjectModel();
		project.setProjectId(projectId);
		project.setProjectName(projectName);
		project.setExcutePoint(excutePoint);
		return project;
	}

	public AlarmEventDo getEventDo() {
		AlarmEventDo event = new AlarmEventDo();
		event.setModule(projectName);
		event.setName(excutePoint);
		event.setRate(rate);
		event.setThreshold(threshold);
		event.setAlarmPolicyId(alarmPolicyId);
		return event;
	}

	public String getUniqueId() {
		if (StringUtils.isOrBlank(projectName, excutePoint))
			return StringUtils.EMPTY;
		try {
			return CommUtil.HEXAndMd5(projectName + excutePoint);
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getAlarmEventId() {
		return alarmEventId;
	}

	public void setAlarmEventId(String alarmEventId) {
		this.alarmEventId = alarmEventId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getExcutePoint() {
		return excutePoint;
	}

	public void setExcutePoint(String excutePoint) {
		this.excutePoint = excutePoint;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public String getAlarmPolicyId() {
		return alarmPolicyId;
	}

	public void setAlarmPolicyId(String alarmPolicyId) {
		this.alarmPolicyId = alarmPolicyId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}


}
