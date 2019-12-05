package com.xiaodou.userCenter.module.jz.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.userCenter.dao.BlankNoticeDao;
import com.xiaodou.userCenter.model.BlankNoticeModel;

@Service("jzUcenterModelServiceFacade")
public class UcenterModelServiceFacadeImpl implements
		IUcenterModelServiceFacade {
	@Resource
	private BlankNoticeDao blankNoticeDao;

	@Override
	public List<BlankNoticeModel> queryBlackNoticeList(
			Map<String, Object> queryCond, Map<String, Object> allInfo) {
		return blankNoticeDao.queryList(queryCond, allInfo);
	}

}
