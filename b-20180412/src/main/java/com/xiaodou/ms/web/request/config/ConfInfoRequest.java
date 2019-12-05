package com.xiaodou.ms.web.request.config;

import com.xiaodou.common.info.model.CommonInfo;
import com.xiaodou.ms.web.request.BaseRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConfInfoRequest extends BaseRequest {
	private Integer id;
	private String infoType;
	private String infoCode;
	private String infoVersion;
	//private String module;

	public CommonInfo initModel() {
		CommonInfo model = new CommonInfo();
		model.setId(id);
		model.setInfoCode(infoCode);
		model.setInfoType(infoType);
		model.setInfoVersion(infoVersion);
	//	model.setModule(module);
		return model;
	}
}