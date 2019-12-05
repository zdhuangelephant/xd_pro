package com.xiaodou.ms.service.ruide;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.ruide.MajorCategoryDao;
import com.xiaodou.ms.model.ruide.ActivityModel;
import com.xiaodou.ms.model.ruide.MajorCategoryModel;
import com.xiaodou.ms.web.request.ruide.MajorCategoryAddRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.ruide.ActivityResponse;
import com.xiaodou.ms.web.response.ruide.MajorCategoryResponse;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
* @author zdh:
* @date 2017年6月13日
*
*/
@Service("majorCategoryService")
public class MajorCategoryService {
	@Resource
	MajorCategoryDao majorCategoryDao;

	public List<MajorCategoryModel> queryByCond(String majorCategory) {
		IQueryParam param = new QueryParam();
		param.addInput("majorCategoryLike", majorCategory);
		param.addOutput("id", "");
		param.addOutput("majorCategory", "");
		param.addOutput("image", "");
		param.addOutput("remark", "");
		param.addOutput("createTime", "");
		return majorCategoryDao.findEntityListByCond(param, null).getResult();
	}

	public BaseResponse doAdd(MajorCategoryAddRequest request) {
		BaseResponse response = new MajorCategoryResponse(ResultType.SUCCESS);
		MajorCategoryModel model = request.initModel();
		
	    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
	    majorCategoryDao.addEntity(model);
		return response;
	}

	public MajorCategoryModel findMajorCategoryById(Long id) {
		MajorCategoryModel entity = new MajorCategoryModel();
		entity.setId(id);
		return majorCategoryDao.findEntityById(entity);
	}

	public BaseResponse doEdit(MajorCategoryAddRequest request) {
		BaseResponse response = new MajorCategoryResponse(ResultType.SUCCESS);
		MajorCategoryModel model = request.initModel();
		majorCategoryDao.updateEntityById(model);
		return response;
	}

	public Boolean delete(Long id) {
		MajorCategoryModel entity = new MajorCategoryModel();
		entity.setId(id);
		return majorCategoryDao.deleteEntityById(entity);
	}
	
	
}
