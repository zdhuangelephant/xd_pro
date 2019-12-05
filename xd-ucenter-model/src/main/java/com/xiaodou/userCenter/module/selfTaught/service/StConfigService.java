package com.xiaodou.userCenter.module.selfTaught.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.info.CommonInfoUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.enums.Latest;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.CourseModel;
import com.xiaodou.userCenter.model.MajorModel;
import com.xiaodou.userCenter.model.property.BlankNoticeModelProperty;
import com.xiaodou.userCenter.model.property.CourseModelProperty;
import com.xiaodou.userCenter.model.property.MajorModelProperty;
import com.xiaodou.userCenter.module.selfTaught.request.StConfigRequest;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse.ConfigData;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse.Course;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigResponse.Major;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigVersionResponse;
import com.xiaodou.userCenter.module.selfTaught.response.StConfigVersionResponse.ConfigVersionData;
import com.xiaodou.userCenter.module.selfTaught.service.facade.IUcenterModelServiceFacade;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.service.IConfigService;
import com.xiaodou.userCenter.util.ThirdPlateformProp;

public class StConfigService
		implements
		IConfigService<StConfigRequest, StConfigVersionResponse, StConfigResponse> {
	IUcenterModelServiceFacade ucenterModelServiceFacade;

	private String thirdloginurl_code = "011";
	private String shareplateformurl_code = "012";
	private String blankNotice_code = "013";
	private String majorList_code = "010";

	@Override
	public StConfigVersionResponse configVersion(BaseRequest pojo) {
		String module = pojo.getModule();
		StConfigVersionResponse response = new StConfigVersionResponse(
				ResultType.SUCCESS);
		ConfigVersionData data = new ConfigVersionData();
		data.setThirdlogin(CommonInfoUtil.getCommonInfoInfoByCode(
				thirdloginurl_code, module).getInfoVersion());
		data.setShareplateform(CommonInfoUtil.getCommonInfoInfoByCode(
				shareplateformurl_code, module).getInfoVersion());
		data.setBlankNotice(CommonInfoUtil.getCommonInfoInfoByCode(
				blankNotice_code, module).getInfoVersion());
		data.setMajor(CommonInfoUtil.getCommonInfoInfoByCode(majorList_code,
				module).getInfoVersion());
		response.setData(data);
		return response;
	}

	@Override
	public StConfigResponse config(StConfigRequest request) {
		StConfigResponse response = new StConfigResponse(ResultType.SUCCESS);
		ConfigData data = new ConfigData();
		if ("1".equals(request.getThirdlogin()))
			data.setThirdlogin(ThirdPlateformProp.getThirdLogin());
		if ("1".equals(request.getShareplateform()))
			data.setShareplateform(ThirdPlateformProp.getSharePlateform());
		if ("1".equals(request.getBlankNotice())) {
			BlankNoticeResponse blankNoticeResponse = initBlankNotice(request
					.getModule());
			data.setBlankNotice(blankNoticeResponse);
		}
		if ("1".equals(request.getMajorList()))
			data.setMajorList(initMajorList(request.getModule()));
		response.setData(data);
		return response;
	}

	private BlankNoticeResponse initBlankNotice(String module) {
		BlankNoticeResponse response = new BlankNoticeResponse(
				ResultType.SUCCESS);
		response.setType((short) 2);
		try {
			Map<String, Object> queryCond = Maps.newHashMap();
			queryCond.put("module", module);
			if (null == ucenterModelServiceFacade) {
				ucenterModelServiceFacade = SpringWebContextHolder
						.getBean("stUcenterModelServiceFacade");
			}
			List<BlankNoticeModel> blackNoticeList = ucenterModelServiceFacade
					.queryBlackNoticeList(queryCond,
							BlankNoticeModelProperty.getAllInfo());
			if (null == blackNoticeList || blackNoticeList.size() <= 0) {
				// return new BlankNoticeResponse(UcenterResType.NoFoundNotice);
				return response;
			}
			if (blackNoticeList.size() > 1)
				return response;
			// return new BlankNoticeResponse(UcenterResType.MoreThanOneData);
			return response.setBlankNoticeResponse(blackNoticeList.get(0));
		} catch (Exception e) {
			LoggerUtil.error("查询首页弹出页数据异常", e);
			return new BlankNoticeResponse(ResultType.SYSFAIL);
		}
	}

	private List<Major> initMajorList(String module) {
		List<Major> majorList = Lists.newArrayList();
		try {
			Map<String, Object> queryCond = Maps.newHashMap();
			queryCond.put("module", module);
			if (null == ucenterModelServiceFacade)
				ucenterModelServiceFacade = SpringWebContextHolder
						.getBean("stUcenterModelServiceFacade");
			// 1、查询模块所有专业
			List<MajorModel> list = ucenterModelServiceFacade.queryMajorList(
					queryCond, MajorModelProperty.getAllInfo());
			if (null == list || list.size() <= 0 )
				return null;
			for (MajorModel majorModel : list) {
				// 2、根据专业id查询课程
				// 近期
				List<Course> latestCourseList = queryCourseListByMajor(
						majorModel.getMajorId(), Latest.ISLATEST.getCode());
				// 其它
				List<Course> courseList = queryCourseListByMajor(
						majorModel.getMajorId(), Latest.NOTLATEST.getCode());

				Major major = new Major();
				major.setMajorId(majorModel.getMajorId());
				major.setMajorImage(majorModel.getMajorImage());
				major.setDegree(majorModel.getDegree());
				major.setMajorName(majorModel.getMajorName());
				major.setLatestCourseList(latestCourseList);
				major.setCourseList(courseList);

				majorList.add(major);

			}

			return majorList;
		} catch (Exception e) {
			LoggerUtil.error("查询专业与课程时数据异常", e);
			return null;
		}
	}

	private List<Course> queryCourseListByMajor(String majorId, int isLatest) {
		List<Course> courseList = Lists.newArrayList();
		Map<String, Object> cond = Maps.newHashMap();
		cond.put("majorId", majorId);
		cond.put("isLatest", isLatest);
		List<CourseModel> courseModelList = ucenterModelServiceFacade
				.queryCourseList(cond, CourseModelProperty.getAllInfo());
		for (CourseModel courseModel : courseModelList) {
			Course course = new Course();
			course = course.getCourse(courseModel);
			courseList.add(course);
		}
		return courseList;
	}
}
