package com.xiaodou.userCenter.module.selfTaught.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.CourseModel;
import com.xiaodou.userCenter.model.MajorModel;

public interface IUcenterModelServiceFacade {
	List<BlankNoticeModel> queryBlackNoticeList(Map<String, Object> queryCond,
			Map<String, Object> allInfo);

	List<MajorModel> queryMajorList(Map<String, Object> queryCond,
			Map<String, Object> allInfo);

	List<CourseModel> queryCourseList(Map<String, Object> queryCond,
			Map<String, Object> allInfo);
}
