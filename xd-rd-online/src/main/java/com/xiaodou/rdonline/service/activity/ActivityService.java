package com.xiaodou.rdonline.service.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.rdonline.domain.activity.ActivityModel;
import com.xiaodou.rdonline.service.facade.IRdServiceFacade;

/**
 * @author zdh:
 * @date 2017年6月8日
 *
 */
@Service("activityService")
public class ActivityService {
	
	@Resource
	IRdServiceFacade rdServiceFacade;

	
	
	public List<ActivityModel> findActivityList(Long startId, Integer pageNo, Integer pageSize) {

		HashMap<String, Object> output = new HashMap<String, Object>();
		output.put("id", "");
		output.put("title", "");
		output.put("subtitle", "");
		output.put("activityTime", "");
		output.put("deadlineTime", "");
		output.put("activityPlace", "");
		output.put("tutorId", "");
		output.put("content", "");
		output.put("createTime", "");
		output.put("contentImage", "");
		HashMap<String, Object> inputs = new HashMap<String, Object>();
		if (startId != null) {
			inputs.put("idLower", startId);
		}
		return rdServiceFacade.listActivity(inputs, output, pageNo, pageSize).getResult();
	}

	public ActivityModel findActivityById(Long stuId) {
		ActivityModel act = rdServiceFacade.getActivityById(stuId);
		return act;
	}

	public Boolean updateActivity(ActivityModel activity) {
		return rdServiceFacade.updateActivity(activity);
	}

}
