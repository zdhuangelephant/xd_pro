package com.xiaodou.userCenter.module.jz.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.city.CityUtil;
import com.xiaodou.common.info.CommonInfoUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.property.BlankNoticeModelProperty;
import com.xiaodou.userCenter.module.jz.request.JzConfigRequest;
import com.xiaodou.userCenter.module.jz.response.JzConfigResponse;
import com.xiaodou.userCenter.module.jz.response.JzConfigResponse.Advertisement;
import com.xiaodou.userCenter.module.jz.response.JzConfigResponse.City;
import com.xiaodou.userCenter.module.jz.response.JzConfigResponse.ConfigData;
import com.xiaodou.userCenter.module.jz.response.JzConfigResponse.ExamDate;
import com.xiaodou.userCenter.module.jz.response.JzConfigVersionResponse;
import com.xiaodou.userCenter.module.jz.response.JzConfigVersionResponse.ConfigVersionData;
import com.xiaodou.userCenter.module.jz.service.facade.IUcenterModelServiceFacade;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.service.IConfigService;
import com.xiaodou.userCenter.util.AdvertProp;
import com.xiaodou.userCenter.util.ExamDateProp;
import com.xiaodou.userCenter.util.ThirdPlateformProp;
import com.xiaodou.userCenter.util.TimeUtil;

public class JzConfigService
		implements
		IConfigService<JzConfigRequest, JzConfigVersionResponse, JzConfigResponse> {
	IUcenterModelServiceFacade ucenterModelServiceFacade ;
	private String city_code = "001";
	private String advertisement_code = "002";
	private String iosversion_code = "003";
	private String androidversion_code = "004";
	private String iosdownloadurl_code = "005";
	private String androiddownloadurl_code = "006";
	private String thirdloginurl_code = "007";
	private String shareplateformurl_code = "008";
	private String blankNotice_code = "009";
	private static int month1 = Integer.valueOf(ExamDateProp
			.getParams("examDate.mar"));
	private static int weekCount1 = Integer.valueOf(ExamDateProp
			.getParams("examDate.mar.weekCount"));
	private static int month2 = Integer.valueOf(ExamDateProp
			.getParams("examDate.ele"));
	private static int weekCount2 = Integer.valueOf(ExamDateProp
			.getParams("examDate.ele.weekCount"));

	@Override
	public JzConfigVersionResponse configVersion(BaseRequest pojo) {
		String module = pojo.getModule();
		JzConfigVersionResponse response = new JzConfigVersionResponse(
				ResultType.SUCCESS);
		ConfigVersionData data = new ConfigVersionData();
		data.setCity(CommonInfoUtil.getCommonInfoInfoByCode(city_code,module)
				.getInfoVersion());
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int start = 1;
		Long calTime1 = TimeUtil.getDateBySun(year, month1, weekCount1);
		Long calTime2 = TimeUtil.getDateBySun(year, month2, weekCount2);
		Long nowTime = cal.getTime().getTime();
		/**
		 * 旧代码的逻辑判断 int month = cal.get(Calendar.MONTH); if (month >= 6) {
		 * start++; }
		 */
		if (nowTime >= calTime1 && nowTime < calTime2)
			start++;// 下半年
		if (nowTime >= calTime2)
			year++;
		data.setExamDate(String.valueOf(year * 10 + start));
		data.setAdvertisement(CommonInfoUtil.getCommonInfoInfoByCode(
				advertisement_code,module).getInfoVersion());
		data.setIosVersion(CommonInfoUtil.getCommonInfoInfoByCode(
				iosversion_code,module).getInfoVersion());
		data.setAndroidVersion(CommonInfoUtil.getCommonInfoInfoByCode(
				androidversion_code,module).getInfoVersion());
		data.setIosDownloadUrl(CommonInfoUtil.getCommonInfoInfoByCode(
				iosdownloadurl_code,module).getInfoVersion());
		data.setAndroidDownloadUrl(CommonInfoUtil.getCommonInfoInfoByCode(
				androiddownloadurl_code,module).getInfoVersion());
		data.setThirdlogin(CommonInfoUtil.getCommonInfoInfoByCode(
				thirdloginurl_code,module).getInfoVersion());
		data.setShareplateform(CommonInfoUtil.getCommonInfoInfoByCode(
				shareplateformurl_code,module).getInfoVersion());
		data.setBlankNotice(CommonInfoUtil.getCommonInfoInfoByCode(
				blankNotice_code,module).getInfoVersion());
		response.setData(data);
		return response;
	}

	@Override
	public JzConfigResponse config(JzConfigRequest request) {
		JzConfigResponse response = new JzConfigResponse(ResultType.SUCCESS);
		ConfigData data = new ConfigData();
		if ("1".equals(request.getCity())) {
			data.setCity(transferCityList(CityUtil.getCityList()));
		}
		if ("1".equals(request.getExamDate()))
			data.setExamDate(initExamDate());
		if ("1".equals(request.getAdvertisement()))
			data.setAdvertisement(initAdvertisement());
		if ("1".equals(request.getThirdlogin()))
			data.setThirdlogin(ThirdPlateformProp.getThirdLogin());
		if ("1".equals(request.getShareplateform()))
			data.setShareplateform(ThirdPlateformProp.getSharePlateform());
		if ("1".equals(request.getBlankNotice())){
			BlankNoticeResponse blankNoticeResponse = initBlankNotice(request.getModule());
//			if((blankNoticeResponse.getRetcode()).equals(UcenterResType.NoFoundNotice.getCode()))
//				return new JzConfigResponse(UcenterResType.NoFoundNotice);
//			if((blankNoticeResponse.getRetcode()).equals(UcenterResType.MoreThanOneData.getCode()))
//				return new JzConfigResponse(UcenterResType.MoreThanOneData);
			data.setBlankNotice(blankNoticeResponse);
		}
		response.setData(data);
		return response;
	}

	private static String[] _prefix = { "%d年上半年", "%d年下半年" };

	private static List<ExamDate> initExamDate() {
		List<ExamDate> list = Lists.newArrayList();
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int start = 0;
		Long calTime1 = TimeUtil.getDateBySun(year, month1, weekCount1);
		Long calTime2 = TimeUtil.getDateBySun(year, month2, weekCount2);
		Long nowTime = cal.getTime().getTime();
		if (nowTime >= calTime1 && nowTime < calTime2)
			start--;// 下半年
		if (nowTime >= calTime2)
			year++;
		/**
		 * 旧代码的逻辑判断 int month = cal.get(Calendar.MONTH); if (month >= 6) {
		 * start--; }
		 */
		int limit = 5 + start;
		for (int i = 0; i < limit; i++, start++) {
			ExamDate examDate = new ExamDate();
			examDate.setName(String.format(_prefix[start & 0x01], year));
			examDate.setOrder(String.valueOf(i + 1));
			if ((start & 0x01) == 0x01)
				year++;
			list.add(examDate);
		}
		{
			ExamDate examDate = new ExamDate();
			examDate.setName("其它");
			examDate.setOrder(String.valueOf(Short.MAX_VALUE));
			list.add(examDate);
		}
		return list;
	}

	private City transferCity(com.xiaodou.common.city.model.City city) {
		if (null == city || city.getLocLevel() > 2)
			return null;
		City _city = new City();
		_city.setId(city.getId().toString());
		_city.setName(city.getName());
		if (city.getLocLevel() == 1) {
			_city.setSubCity(transferCityList(city.getSubCity()));
		}
		return _city;
	}

	private List<City> transferCityList(
			List<com.xiaodou.common.city.model.City> cityList) {
		List<City> _cityList = Lists.newArrayList();
		for (com.xiaodou.common.city.model.City city : cityList) {
			City _city = transferCity(city);
			if (null != _city)
				_cityList.add(_city);
		}
		return _cityList;
	}

	private List<Advertisement> initAdvertisement() {
		return AdvertProp.getAds();
	}

	private BlankNoticeResponse initBlankNotice(String module) {
		BlankNoticeResponse response = new BlankNoticeResponse(
				ResultType.SUCCESS);
		response.setType((short) 2);
		try {
			Map<String, Object> queryCond = Maps.newHashMap();
			queryCond.put("module", module);
			if(null== ucenterModelServiceFacade){
				ucenterModelServiceFacade = SpringWebContextHolder.getBean("jzUcenterModelServiceFacade");
			}
			List<BlankNoticeModel> blackNoticeList = ucenterModelServiceFacade
					.queryBlackNoticeList(queryCond,
							BlankNoticeModelProperty.getAllInfo());
			if (null == blackNoticeList || blackNoticeList.size() <= 0) {
				//return new BlankNoticeResponse(UcenterResType.NoFoundNotice);
				return response;
			}
			if (blackNoticeList.size() > 1) 	return response;
				//return new BlankNoticeResponse(UcenterResType.MoreThanOneData);
			return response.setBlankNoticeResponse(blackNoticeList.get(0));
		} catch (Exception e) {
			LoggerUtil.error("查询首页弹出页数据异常", e);
			return new BlankNoticeResponse(ResultType.SYSFAIL);
		}
	}
}
