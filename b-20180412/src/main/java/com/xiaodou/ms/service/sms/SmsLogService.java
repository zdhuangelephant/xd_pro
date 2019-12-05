package com.xiaodou.ms.service.sms;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.dao.sms.SmsLogDao;
import com.xiaodou.ms.model.sms.SmsLogModel;
import com.xiaodou.ms.web.request.sms.SmsLogRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("smsLogService")
public class SmsLogService {
	@Resource
	SmsLogDao smsLogDao;

	public Page<SmsLogModel> querySmsLogs(Integer pageNo, SmsLogRequest request) {
		Page<SmsLogModel> page = new Page<SmsLogModel>();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		
		IQueryParam param = new QueryParam();
		INPUTS:{
			if(request == null) break INPUTS;
			if(StringUtils.isNotBlank(request.getMobile())) {
				param.addInput("mobileLike", request.getMobile());
			}
			if(StringUtils.isNotBlank(request.getBeginDate())) {
				param.addInput("beginDate", request.getBeginDate());
			}
			if(StringUtils.isNotBlank(request.getEndDate())) {
				param.addInput("endDate", request.getEndDate());
			}
		}
		
		param.addOutputs(CommUtil.getAllField(SmsLogModel.class));
		return smsLogDao.findEntityListByCond(param, page);
	}

}
