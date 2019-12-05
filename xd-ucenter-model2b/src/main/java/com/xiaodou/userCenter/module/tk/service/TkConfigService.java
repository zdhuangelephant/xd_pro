package com.xiaodou.userCenter.module.tk.service;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.info.CommonInfoUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.property.BlankNoticeModelProperty;
import com.xiaodou.userCenter.module.tk.request.TkConfigRequest;
import com.xiaodou.userCenter.module.tk.response.TkConfigResponse;
import com.xiaodou.userCenter.module.tk.response.TkConfigResponse.ConfigData;
import com.xiaodou.userCenter.module.tk.response.TkConfigVersionResponse;
import com.xiaodou.userCenter.module.tk.response.TkConfigVersionResponse.ConfigVersionData;
import com.xiaodou.userCenter.module.tk.service.facade.IUcenterModelServiceFacade;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.service.IConfigService;
import com.xiaodou.userCenter.util.ThirdPlateformProp;

public class TkConfigService
		implements
		IConfigService<TkConfigRequest, TkConfigVersionResponse, TkConfigResponse> {
	IUcenterModelServiceFacade ucenterModelServiceFacade;
	private String iosversion_code = "003";
	private String androidversion_code = "004";
	private String iosdownloadurl_code = "005";
	private String androiddownloadurl_code = "006";
	private String thirdloginurl_code = "007";
	private String shareplateformurl_code = "008";
	private String blankNotice_code = "009";

	@Override
	public TkConfigVersionResponse configVersion(BaseRequest pojo) {
		String module = pojo.getModule();
		TkConfigVersionResponse response = new TkConfigVersionResponse(
				ResultType.SUCCESS);
		ConfigVersionData data = new ConfigVersionData();
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
	public TkConfigResponse config(TkConfigRequest request) {
		TkConfigResponse response = new TkConfigResponse(ResultType.SUCCESS);
		ConfigData data = new ConfigData();
		if ("1".equals(request.getThirdlogin()))
			data.setThirdlogin(ThirdPlateformProp.getThirdLogin());
		if ("1".equals(request.getShareplateform()))
			data.setShareplateform(ThirdPlateformProp.getSharePlateform());
		if ("1".equals(request.getBlankNotice())){
			BlankNoticeResponse blankNoticeResponse = initBlankNotice(request.getModule());
			data.setBlankNotice(blankNoticeResponse);
		}
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
			if(null == ucenterModelServiceFacade){
				ucenterModelServiceFacade = SpringWebContextHolder.getBean("tkUcenterModelServiceFacade");
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

