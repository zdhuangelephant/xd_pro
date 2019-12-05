package com.xiaodou.webfetch.model;

import java.util.List;

import com.xiaodou.common.util.warp.FastJsonUtil;

import lombok.Data;

/**
 * @name @see com.xiaodou.webfetch.model.FetchTaskModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月8日
 * @description 抓取任务数据库存储模型
 * @version 1.0
 */
@Data
public class FetchTacticModel {
	/** id 主键ID */
	private String id;
	/** crontExpression 定时任务表达式 */
	private String crontExpression;
	/** apiType api类型 */
	private String apiType;
	/** headerList header数组 */
	private String headerList;
	/** cookieList cookie数组 */
	private String cookieList;
	/** baseInfo 基础信息 */
	private String baseInfo;
	/** taskList 任务列表 */
	private String taskList;
	/** startActionId 开始动作ID */
	private String startActionId;

	public void setTaskList(List<FetchTaskModel> modelList) {
		this.taskList = FastJsonUtil.toJson(modelList);
	}
}
