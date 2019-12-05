package com.xiaodou.dashboard.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.cache.redis.model.param.JedisKeyValuePair;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.dashboard.model.log.ProjectModel;
import com.xiaodou.dashboard.vo.log.ActionModelVo;
import com.xiaodou.dashboard.vo.log.response.ProjectModelVO;

/**
 * @name @see com.xiaodou.logmonitor.statistic.OutInStatisticInfo.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月26日
 * @description OutIn日志记录情况
 * @version 1.0
 */
public class ActionStatisticInfo {
	private static final DateFormat SDF_DATE_HOUR_MIN = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private static final DateFormat SDF_DATE_HOUR = new SimpleDateFormat(
			"yyyy-MM-dd HH");
	private static final DateFormat SDF_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final String PROJECT_NAME_KEY = "logmonitor:outin:projectName";
	private static final String EXCUTE_POINT_KEY = "logmonitor:outin:%s:excutePoint";
	private static final String TOTAL_KEY_SERVER = "logmonitor:outin:server:%s";
	private static final String TOTAL_KEY_IP = "logmonitor:outin:ip:%s";
	private static final String TOTAL_KEY_MAC = "logmonitor:outin:mac:%s";
	private static final String TOTAL_KEY_ALL = "logmonitor:outin:total:all:%s";
	private static final String TOTAL_KEY_FAIL = "logmonitor:outin:total:fail:%s";
	private static final String PART_KEY_ALL = "logmonitor:outin:part:all:%s";
	private static final String PART_KEY_FAIL = "logmonitor:outin:part:fail:%s";
	private static final String TOTAL_KEY_OVERTIME = "logmonitor:outin:total:overtime:%s";
	private static final String PART_KEY_OVERTIME = "logmonitor:outin:part:overtime:%s";

	private ActionModelVo model;
	private String timeMin;
	private String timeHour;
	private String timeDate;

	public static Map<String, String> getProjectList() {
		return JedisUtil.getAllMapValueFromJedis(PROJECT_NAME_KEY);
	}

	public static Map<String, String> getExcutePointList(String projectName) {
		return JedisUtil.getAllMapValueFromJedis(String.format(
				EXCUTE_POINT_KEY, projectName));
	}

	public String getTimeMin() {
		return timeMin;
	}

	public void setTimeMin(String timeMin) {
		this.timeMin = timeMin;
	}

	public String getTimeHour() {
		return timeHour;
	}

	public void setTimeHour(String timeHour) {
		this.timeHour = timeHour;
	}

	public String getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(String timeDate) {
		this.timeDate = timeDate;
	}

	private String totalSurfixMin;
	private String partSurfixMin;
	private String totalSurfixHour;
	private String partSurfixHour;
	private String totalSurfixDate;
	private String partSurfixDate;
	private String serverSurfix;
	// server key
	private String totalServerKey;
	private String totalIpKey;
	private String totalMacKey;
	// log key
	private String partFailTimeMinKey;
	private String totalFailTimeMinKey;
	private String partFailTimeHourKey;
	private String totalFailTimeHourKey;
	private String partFailTimeDateKey;
	private String totalFailTimeDateKey;
	private String partAllTimeMinKey;
	private String totalAllTimeMinKey;
	private String partAllTimeHourKey;
	private String totalAllTimeHourKey;
	private String partAllTimeDateKey;
	private String totalAllTimeDateKey;

	public String getTotalSurfixMin() {
		return totalSurfixMin;
	}

	public void setTotalSurfixMin(String totalSurfixMin) {
		this.totalSurfixMin = totalSurfixMin;
	}

	public String getPartSurfixMin() {
		return partSurfixMin;
	}

	public void setPartSurfixMin(String partSurfixMin) {
		this.partSurfixMin = partSurfixMin;
	}

	public String getTotalSurfixHour() {
		return totalSurfixHour;
	}

	public void setTotalSurfixHour(String totalSurfixHour) {
		this.totalSurfixHour = totalSurfixHour;
	}

	public String getPartSurfixHour() {
		return partSurfixHour;
	}

	public void setPartSurfixHour(String partSurfixHour) {
		this.partSurfixHour = partSurfixHour;
	}

	public String getTotalSurfixDate() {
		return totalSurfixDate;
	}

	public void setTotalSurfixDate(String totalSurfixDate) {
		this.totalSurfixDate = totalSurfixDate;
	}

	public String getPartSurfixDate() {
		return partSurfixDate;
	}

	public void setPartSurfixDate(String partSurfixDate) {
		this.partSurfixDate = partSurfixDate;
	}

	public String getServerSurfix() {
		return serverSurfix;
	}

	public void setServerSurfix(String serverSurfix) {
		this.serverSurfix = serverSurfix;
	}

	public String getTotalServerKey() {
		return totalServerKey;
	}

	public void setTotalServerKey(String totalServerKey) {
		this.totalServerKey = totalServerKey;
	}

	public String getTotalIpKey() {
		return totalIpKey;
	}

	public void setTotalIpKey(String totalIpKey) {
		this.totalIpKey = totalIpKey;
	}

	public String getTotalMacKey() {
		return totalMacKey;
	}

	public void setTotalMacKey(String totalMacKey) {
		this.totalMacKey = totalMacKey;
	}

	public String getPartFailTimeMinKey() {
		return partFailTimeMinKey;
	}

	public void setPartFailTimeMinKey(String partFailTimeMinKey) {
		this.partFailTimeMinKey = partFailTimeMinKey;
	}

	public String getTotalFailTimeMinKey() {
		return totalFailTimeMinKey;
	}

	public void setTotalFailTimeMinKey(String totalFailTimeMinKey) {
		this.totalFailTimeMinKey = totalFailTimeMinKey;
	}

	public String getPartFailTimeHourKey() {
		return partFailTimeHourKey;
	}

	public void setPartFailTimeHourKey(String partFailTimeHourKey) {
		this.partFailTimeHourKey = partFailTimeHourKey;
	}

	public String getTotalFailTimeHourKey() {
		return totalFailTimeHourKey;
	}

	public void setTotalFailTimeHourKey(String totalFailTimeHourKey) {
		this.totalFailTimeHourKey = totalFailTimeHourKey;
	}

	public String getPartFailTimeDateKey() {
		return partFailTimeDateKey;
	}

	public void setPartFailTimeDateKey(String partFailTimeDateKey) {
		this.partFailTimeDateKey = partFailTimeDateKey;
	}

	public String getTotalFailTimeDateKey() {
		return totalFailTimeDateKey;
	}

	public void setTotalFailTimeDateKey(String totalFailTimeDateKey) {
		this.totalFailTimeDateKey = totalFailTimeDateKey;
	}

	public String getPartAllTimeMinKey() {
		return partAllTimeMinKey;
	}

	public void setPartAllTimeMinKey(String partAllTimeMinKey) {
		this.partAllTimeMinKey = partAllTimeMinKey;
	}

	public String getTotalAllTimeMinKey() {
		return totalAllTimeMinKey;
	}

	public void setTotalAllTimeMinKey(String totalAllTimeMinKey) {
		this.totalAllTimeMinKey = totalAllTimeMinKey;
	}

	public String getPartAllTimeHourKey() {
		return partAllTimeHourKey;
	}

	public void setPartAllTimeHourKey(String partAllTimeHourKey) {
		this.partAllTimeHourKey = partAllTimeHourKey;
	}

	public String getTotalAllTimeHourKey() {
		return totalAllTimeHourKey;
	}

	public void setTotalAllTimeHourKey(String totalAllTimeHourKey) {
		this.totalAllTimeHourKey = totalAllTimeHourKey;
	}

	public String getPartAllTimeDateKey() {
		return partAllTimeDateKey;
	}

	public void setPartAllTimeDateKey(String partAllTimeDateKey) {
		this.partAllTimeDateKey = partAllTimeDateKey;
	}

	public String getTotalAllTimeDateKey() {
		return totalAllTimeDateKey;
	}

	public void setTotalAllTimeDateKey(String totalAllTimeDateKey) {
		this.totalAllTimeDateKey = totalAllTimeDateKey;
	}

	public String getPartOverTimeMinKey() {
		return partOverTimeMinKey;
	}

	public void setPartOverTimeMinKey(String partOverTimeMinKey) {
		this.partOverTimeMinKey = partOverTimeMinKey;
	}

	public String getTotalOverTimeMinKey() {
		return totalOverTimeMinKey;
	}

	public void setTotalOverTimeMinKey(String totalOverTimeMinKey) {
		this.totalOverTimeMinKey = totalOverTimeMinKey;
	}

	public String getPartOverTimeHourKey() {
		return partOverTimeHourKey;
	}

	public void setPartOverTimeHourKey(String partOverTimeHourKey) {
		this.partOverTimeHourKey = partOverTimeHourKey;
	}

	public String getTotalOverTimeHourKey() {
		return totalOverTimeHourKey;
	}

	public void setTotalOverTimeHourKey(String totalOverTimeHourKey) {
		this.totalOverTimeHourKey = totalOverTimeHourKey;
	}

	public String getPartOverTimeDateKey() {
		return partOverTimeDateKey;
	}

	public void setPartOverTimeDateKey(String partOverTimeDateKey) {
		this.partOverTimeDateKey = partOverTimeDateKey;
	}

	public String getTotalOverTimeDateKey() {
		return totalOverTimeDateKey;
	}

	public void setTotalOverTimeDateKey(String totalOverTimeDateKey) {
		this.totalOverTimeDateKey = totalOverTimeDateKey;
	}

	public static DateFormat getSdfDateHourMin() {
		return SDF_DATE_HOUR_MIN;
	}

	public static DateFormat getSdfDateHour() {
		return SDF_DATE_HOUR;
	}

	public static DateFormat getSdfDate() {
		return SDF_DATE;
	}

	public static String getProjectNameKey() {
		return PROJECT_NAME_KEY;
	}

	public static String getExcutePointKey() {
		return EXCUTE_POINT_KEY;
	}

	public static String getTotalKeyServer() {
		return TOTAL_KEY_SERVER;
	}

	public static String getTotalKeyIp() {
		return TOTAL_KEY_IP;
	}

	public static String getTotalKeyMac() {
		return TOTAL_KEY_MAC;
	}

	public static String getTotalKeyAll() {
		return TOTAL_KEY_ALL;
	}

	public static String getTotalKeyFail() {
		return TOTAL_KEY_FAIL;
	}

	public static String getPartKeyAll() {
		return PART_KEY_ALL;
	}

	public static String getPartKeyFail() {
		return PART_KEY_FAIL;
	}

	public static String getTotalKeyOvertime() {
		return TOTAL_KEY_OVERTIME;
	}

	public static String getPartKeyOvertime() {
		return PART_KEY_OVERTIME;
	}

	public void setModel(ActionModelVo model) {
		this.model = model;
	}

	private String partOverTimeMinKey;
	private String totalOverTimeMinKey;
	private String partOverTimeHourKey;
	private String totalOverTimeHourKey;
	private String partOverTimeDateKey;
	private String totalOverTimeDateKey;

	public ActionStatisticInfo(ActionModelVo actionModel) {
		model = actionModel;
		timeMin = SDF_DATE_HOUR_MIN.format(model.getLogTime());
		timeHour = SDF_DATE_HOUR.format(model.getLogTime());
		timeDate = SDF_DATE.format(model.getLogTime());

		serverSurfix = String.format("%s:%s", model.getProjectName(),
				model.getExcutePoint());
		totalSurfixMin = String.format("%s:%s:%s", timeMin,
				model.getProjectName(), model.getExcutePoint());
		partSurfixMin = String.format("%s:%s:%s:%s", timeMin,
				model.getProjectName(), model.getExcutePoint(),
				model.getServerName());
		totalSurfixHour = String.format("%s:%s:%s", timeHour,
				model.getProjectName(), model.getExcutePoint());
		partSurfixHour = String.format("%s:%s:%s:%s", timeHour,
				model.getProjectName(), model.getExcutePoint(),
				model.getServerName());
		totalSurfixDate = String.format("%s:%s:%s", timeDate,
				model.getProjectName(), model.getExcutePoint());
		partSurfixDate = String.format("%s:%s:%s:%s", timeDate,
				model.getProjectName(), model.getExcutePoint(),
				model.getServerName());
		// server key
		totalServerKey = String.format(TOTAL_KEY_SERVER, serverSurfix);
		totalIpKey = String.format(TOTAL_KEY_IP, serverSurfix);
		totalMacKey = String.format(TOTAL_KEY_MAC, serverSurfix);
		// log key
		partFailTimeMinKey = String.format(PART_KEY_FAIL, partSurfixMin);
		totalFailTimeMinKey = String.format(TOTAL_KEY_FAIL, totalSurfixMin);
		partAllTimeMinKey = String.format(PART_KEY_ALL, partSurfixMin);
		totalAllTimeMinKey = String.format(TOTAL_KEY_ALL, totalSurfixMin);
		partFailTimeHourKey = String.format(PART_KEY_FAIL, partSurfixHour);
		totalFailTimeHourKey = String.format(TOTAL_KEY_FAIL, totalSurfixHour);
		partAllTimeHourKey = String.format(PART_KEY_ALL, partSurfixHour);
		totalAllTimeHourKey = String.format(TOTAL_KEY_ALL, totalSurfixHour);
		partFailTimeDateKey = String.format(PART_KEY_FAIL, partSurfixDate);
		totalFailTimeDateKey = String.format(TOTAL_KEY_FAIL, totalSurfixDate);
		partAllTimeDateKey = String.format(PART_KEY_ALL, partSurfixDate);
		totalAllTimeDateKey = String.format(TOTAL_KEY_ALL, totalSurfixDate);

		partOverTimeMinKey = String.format(PART_KEY_OVERTIME, partSurfixMin);
		totalOverTimeMinKey = String.format(TOTAL_KEY_OVERTIME, totalSurfixMin);
		partOverTimeHourKey = String.format(PART_KEY_OVERTIME, partSurfixHour);
		totalOverTimeHourKey = String.format(TOTAL_KEY_OVERTIME,
				totalSurfixHour);
		partOverTimeDateKey = String.format(PART_KEY_OVERTIME, partSurfixDate);
		totalOverTimeDateKey = String.format(TOTAL_KEY_OVERTIME,
				totalSurfixDate);
	}

	public final Integer partFailTimeMin() {
		String object = JedisUtil.getStringFromJedis(partFailTimeMinKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}
	
	public final Integer partOverTimeMin() {
		String object = JedisUtil.getStringFromJedis(partOverTimeMinKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}


	public final Integer partFailTimeHour() {
		String object = JedisUtil.getStringFromJedis(partFailTimeHourKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}
	
	public final Integer partOverTimeHour() {
		String object = JedisUtil.getStringFromJedis(partOverTimeHourKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}


	public final Integer partFailTimeDate() {
		String object = JedisUtil.getStringFromJedis(partFailTimeDateKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}
	
	public final Integer partOverTimeDate() {
		String object = JedisUtil.getStringFromJedis(partOverTimeDateKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}


	public final Integer partAllTimeMin() {
		String object = JedisUtil.getStringFromJedis(partAllTimeMinKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}

	public final Integer partAllTimeHour() {
		String object = JedisUtil.getStringFromJedis(partAllTimeHourKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}

	public final Integer partAllTimeDate() {
		String object = JedisUtil.getStringFromJedis(partAllTimeDateKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}

	public final Integer totalFailTimeMin() {
		String object = JedisUtil.getStringFromJedis(totalFailTimeMinKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}

	public final Integer totalOverTimeMin() {
		String object = JedisUtil.getStringFromJedis(totalOverTimeMinKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}
	public final Integer totalFailTimeHour() {
		String object = JedisUtil.getStringFromJedis(totalFailTimeHourKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}
	public final Integer totalOverTimeHour() {
		String object = JedisUtil.getStringFromJedis(totalOverTimeHourKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}

	public final Integer totalFailTimeDate() {
		String object = JedisUtil.getStringFromJedis(totalFailTimeDateKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}
	
	public final Integer totalOverTimeDate() {
		String object = JedisUtil.getStringFromJedis(totalOverTimeDateKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}


	public final Integer totalAllTimeMin() {
		String object = JedisUtil.getStringFromJedis(totalAllTimeMinKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}

	public final Integer totalAllTimeHour() {
		String object = JedisUtil.getStringFromJedis(totalAllTimeHourKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}

	public final Integer totalAllTimeDate() {
		String object = JedisUtil.getStringFromJedis(totalAllTimeDateKey);
		if (StringUtils.isEmpty(object))
			return 0;
		return Integer.valueOf(object);
	}

	public final List<String> getServerList() {
		Map<String, String> keyMap = JedisUtil
				.getAllMapValueFromJedis(totalServerKey);
		if (null == keyMap)
			return Lists.newArrayList();
		return Lists.newArrayList(keyMap.keySet());
	}

	public final List<String> getIpList() {
		Map<String, String> keyMap = JedisUtil
				.getAllMapValueFromJedis(totalIpKey);
		if (null == keyMap)
			return Lists.newArrayList();
		return Lists.newArrayList(keyMap.keySet());
	}

	public final List<String> getMacList() {
		Map<String, String> keyMap = JedisUtil
				.getAllMapValueFromJedis(totalMacKey);
		if (null == keyMap)
			return Lists.newArrayList();
		return Lists.newArrayList(keyMap.keySet());
	}

	public final ActionModelVo getModel() {
		return model;
	}

	public static List<ProjectModelVO> getProjectModelVOList(
			List<ProjectModel> projectModelList) {
		if (null == projectModelList || projectModelList.size() == 0)
			return null;
		List<ProjectModelVO> projectModelVOList = Lists.newArrayList();
		List<ProjectMapper> mapperList = Lists.newArrayList();
		List<JedisKeyValuePair> pairList = Lists.newArrayList();
		for (ProjectModel projectModel : projectModelList) {
			ProjectModelVO projectModelVO = new ProjectModelVO(projectModel);
			projectModelVOList.add(projectModelVO);
			ProjectMapper mapper = new ProjectMapper(projectModelVO);
			pairList.add(mapper.currHourFailCount);
			pairList.add(mapper.currHourOverTimeCount);
			pairList.add(mapper.currHourTotalCount);
			pairList.add(mapper.oneHourAgoFailCount);
			pairList.add(mapper.oneHourAgoOverTimeCount);
			pairList.add(mapper.oneHourAgoTotalCount);
			pairList.add(mapper.twoHourAgoFailCount);
			pairList.add(mapper.twoHourAgoOverTimeCount);
			pairList.add(mapper.twoHourAgoTotalCount);
			mapperList.add(mapper);
		}
		JedisUtil.getGroupStringFromJedis(pairList);
		for (ProjectMapper mapper : mapperList)
			mapper.fillAllValue();
		return projectModelVOList;
	}

	private static class ProjectMapper {
		ProjectMapper(ProjectModelVO project) {
			this.project = project;
			ActionModelVo model = new ActionModelVo();
			model.setProjectName(project.getProjectName());
			model.setExcutePoint(project.getExcutePoint());
			long currentTimeMillis = System.currentTimeMillis();
			{
				model.setLogTime(new Timestamp(currentTimeMillis));
				ActionStatisticInfo info = new ActionStatisticInfo(model);
				currHourFailCount = new JedisKeyValuePair(
						info.totalFailTimeHourKey);
				currHourOverTimeCount =new JedisKeyValuePair(
						info.totalOverTimeHourKey);
				currHourTotalCount = new JedisKeyValuePair(
						info.totalAllTimeHourKey);
			}
			{
				model.setLogTime(new Timestamp(currentTimeMillis - 3600 * 1000));
				ActionStatisticInfo info = new ActionStatisticInfo(model);
				oneHourAgoFailCount = new JedisKeyValuePair(
						info.totalFailTimeHourKey);
				oneHourAgoOverTimeCount = new JedisKeyValuePair(
						info.totalOverTimeHourKey);
				oneHourAgoTotalCount = new JedisKeyValuePair(
						info.totalAllTimeHourKey);
			}
			{
				model.setLogTime(new Timestamp(
						currentTimeMillis - 3600 * 1000 * 2));
				ActionStatisticInfo info = new ActionStatisticInfo(model);
				twoHourAgoFailCount = new JedisKeyValuePair(
						info.totalFailTimeHourKey);
				twoHourAgoOverTimeCount = new JedisKeyValuePair(
						info.totalOverTimeHourKey);
				twoHourAgoTotalCount = new JedisKeyValuePair(
						info.totalAllTimeHourKey);
			}
		}

		private ProjectModelVO project;
		private JedisKeyValuePair currHourFailCount;
		private JedisKeyValuePair oneHourAgoFailCount;
		private JedisKeyValuePair twoHourAgoFailCount;
		private JedisKeyValuePair currHourOverTimeCount;
		private JedisKeyValuePair oneHourAgoOverTimeCount;
		private JedisKeyValuePair twoHourAgoOverTimeCount;
		private JedisKeyValuePair currHourTotalCount;
		private JedisKeyValuePair oneHourAgoTotalCount;
		private JedisKeyValuePair twoHourAgoTotalCount;

		private void fillAllValue() {
			if (null == project)
				return;
			if (StringUtils.isNotBlank(currHourTotalCount.getValue())) {
				project.setCurrHourTotalCount(new Integer(currHourTotalCount
						.getValue()));
				if (StringUtils.isNotBlank(currHourFailCount.getValue())) {
					project.setCurrHourErrorPercent(new Double(
							currHourFailCount.getValue())
							/ new Double(currHourTotalCount.getValue()));
				}
				if (StringUtils.isNotBlank(currHourOverTimeCount.getValue())) {
					project.setCurrHourOverTimePercent(new Double(
							currHourOverTimeCount.getValue())
							/ new Double(currHourTotalCount.getValue()));
				}
			}
			if (StringUtils.isNotBlank(oneHourAgoTotalCount.getValue())) {
				project.setOneHourAgoTotalCount(new Integer(
						oneHourAgoTotalCount.getValue()));
				if (StringUtils.isNotBlank(oneHourAgoFailCount.getValue())) {
					project.setOneHourAgoErrorPercent(new Double(
							oneHourAgoFailCount.getValue())
							/ new Double(oneHourAgoTotalCount.getValue()));
				}
				if (StringUtils.isNotBlank(oneHourAgoOverTimeCount.getValue())) {
					project.setOneHourAgoOverTimePercent(new Double(
							oneHourAgoOverTimeCount.getValue())
							/ new Double(oneHourAgoTotalCount.getValue()));
				}
			}
			if (StringUtils.isNotBlank(twoHourAgoTotalCount.getValue())) {
				project.setTwoHourAgoTotalCount(new Integer(
						twoHourAgoTotalCount.getValue()));
				if (StringUtils.isNotBlank(twoHourAgoFailCount.getValue())) {
					project.setTwoHourAgoErrorPercent(new Double(
							twoHourAgoFailCount.getValue())
							/ new Double(twoHourAgoTotalCount.getValue()));
				}
				if (StringUtils.isNotBlank(twoHourAgoOverTimeCount.getValue())) {
					project.setTwoHourAgoOverTimePercent(new Double(
							twoHourAgoOverTimeCount.getValue())
							/ new Double(twoHourAgoTotalCount.getValue()));
				}
			}
		}
	}

}
