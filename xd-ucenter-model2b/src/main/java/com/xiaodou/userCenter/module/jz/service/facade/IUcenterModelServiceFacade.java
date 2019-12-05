package com.xiaodou.userCenter.module.jz.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.userCenter.model.BlankNoticeModel;

public interface IUcenterModelServiceFacade {
	List<BlankNoticeModel> queryBlackNoticeList(Map<String, Object> queryCond,
			Map<String, Object> allInfo);
}
