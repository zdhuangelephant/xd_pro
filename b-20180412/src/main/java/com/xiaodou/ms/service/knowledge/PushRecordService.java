package com.xiaodou.ms.service.knowledge;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.knowledge.PushRecordDao;
import com.xiaodou.ms.model.knowledge.PushRecordModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @author zdh:
 * @date 2017年7月13日
 *
 */
@Service("pushRecordService")
public class PushRecordService {
	
	@Resource
	PushRecordDao pushRecordDao;

	public List<PushRecordModel> findListByCond(Map<String, Object> inputs) {
		IQueryParam param = new QueryParam();
		param.addOutput("id", "");
		param.addOutput("typeId", "");
		param.addOutput("remark", "");
		param.addOutput("isPush", "");
		param.addOutput("createTime", "");
		param.addInputs(inputs);
		param.addSort("createTime", Sort.DESC);
//		param.addOutput("currentTime", sdf.format(new Date()));
//		param.addInput("createTime", new Timestamp(System.currentTimeMillis()) );
		Page<PushRecordModel> results = pushRecordDao.findEntityListByCond(param, null);
		if (results != null) {
			return results.getResult();
		}
		return null;
	}
	
	
	public PushRecordModel addEntity(PushRecordModel pushRecordModel) {
		return pushRecordDao.addEntity(pushRecordModel);
	}

	
}
