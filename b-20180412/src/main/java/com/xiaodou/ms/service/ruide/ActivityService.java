package com.xiaodou.ms.service.ruide;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.ruide.ActivityDao;
import com.xiaodou.ms.model.ruide.ActivityModel;
import com.xiaodou.ms.model.ruide.TutorMajorModel;
import com.xiaodou.ms.web.request.ruide.ActivityAddRequest;
import com.xiaodou.ms.web.request.ruide.TutorMajorEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.ruide.ActivityResponse;
import com.xiaodou.ms.web.response.ruide.TutorMajorResponse;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

@Service("activityService")
public class ActivityService {

	@Resource
	ActivityDao activityDao;

	public List<ActivityModel> queryByCond(String title) {
		IQueryParam param = new QueryParam();
		param.addSort("createTime", Sort.DESC);
		param.addInput("titleLike", title);
		param.addOutput("id", "");
		param.addOutput("title", "");
		param.addOutput("subtitle", "");
		param.addOutput("activityTime", "");
		param.addOutput("deadlineTime", "");
		param.addOutput("activityPlace", "");
		param.addOutput("tutorId", "");
		param.addOutput("content", "");
		param.addOutput("createTime", "");
		param.addOutput("contentImage", "");
		return activityDao.findEntityListByCond(param, null).getResult();

	}

	public BaseResponse doAdd(ActivityAddRequest request) {
		
		BaseResponse response = new ActivityResponse(ResultType.SUCCESS);
		ActivityModel model = request.initModel();
		
	    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
	    activityDao.addEntity(model);
		return response;
	}

	public Boolean delete(Long id) {
		ActivityModel model = new ActivityModel();
		model.setId(id);
		return activityDao.deleteEntityById(model);
	}

	public BaseResponse doEdit(ActivityAddRequest request) {
		BaseResponse response = new ActivityResponse(ResultType.SUCCESS);
		ActivityModel model = request.initModel();
		activityDao.updateEntityById(model);
		return response;
	}

	public ActivityModel getActivityById(Long id) {
		ActivityModel model = new ActivityModel();
		model.setId(id);
		return activityDao.findEntityById(model);
	}

	public void deleteContentImage(Long id) {
		ActivityModel model = new ActivityModel();
		model.setId(id);
		ActivityModel entity = activityDao.findEntityById(model);
		entity.setContentImage(null);
		activityDao.updateEntityById(entity);
	}
}