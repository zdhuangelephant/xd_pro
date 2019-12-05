package com.xiaodou.ms.service.ruide;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.ms.dao.ruide.TutorMajorDao;
import com.xiaodou.ms.model.knowledge.AuthorModel;
import com.xiaodou.ms.model.ruide.ActivityModel;
import com.xiaodou.ms.model.ruide.TutorMajorModel;
import com.xiaodou.ms.web.request.ruide.TutorMajorAddRequest;
import com.xiaodou.ms.web.request.ruide.TutorMajorEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.ruide.TutorMajorResponse;
import com.xiaodou.ms.web.response.selftaught.ShowResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

@Service("tutorMajorService")
public class TutorMajorService {

	@Resource
	TutorMajorDao tutorMajorDao;

	public TutorMajorModel getTeacherById(Long tutorId) {
		
		TutorMajorModel model = new TutorMajorModel();
		model.setId(tutorId);
		return tutorMajorDao.findEntityById(model);
	}

	public List<TutorMajorModel> getAllTeacherAndMajor(Short type) {
		IQueryParam param = new QueryParam();
		param.addInput("type", type);
		param.addSort("createTime", Sort.DESC);
		
		param.addOutput("id", "");
		param.addOutput("title", "");
		param.addOutput("type", "");
		param.addOutput("subtitle", "");
		param.addOutput("publishTime", "");
		param.addOutput("image", "");
		param.addOutput("author", "");
		param.addOutput("content", "");
		param.addOutput("contentImage", "");
		List<TutorMajorModel> result = tutorMajorDao.findEntityListByCond(param, null).getResult();
		return result;
	}

	public TutorMajorModel getMajorById(Long tutorId) {
		TutorMajorModel model = new TutorMajorModel();
		model.setId(tutorId);
		return tutorMajorDao.findEntityById(model);
	}


	public List<TutorMajorModel> search(String authorName, Short type) {
		IQueryParam param = new QueryParam();
		if (type != (short) 3) {
			param.addInput("authorLike", authorName);
		} else {
			param.addInput("titleLike", authorName);
		}
		param.addSort("createTime", Sort.DESC);
		
		param.addInput("type", type);
		param.addOutputs(CommUtil.getAllField(TutorMajorModel.class));
		Page<TutorMajorModel> authorPage = tutorMajorDao.findEntityListByCond(param, null);
		if (null == authorPage)
			return null;
		return authorPage.getResult();
	}

	public BaseResponse doAdd(TutorMajorAddRequest request, Short type) {
		BaseResponse response = new TutorMajorResponse(ResultType.SUCCESS);
		TutorMajorModel model = request.initModel();
		model.setPublishTime(new Timestamp(System.currentTimeMillis()));
		model.setCreateTime(new Timestamp(System.currentTimeMillis()));
		switch (type) {
		case (short) 1:
			model.setType((short) 1);
			break;
		case (short) 2:
			model.setType((short) 2);
			break;
		case (short) 3:
			model.setType((short) 3);
			break;
		}
		tutorMajorDao.addEntity(model);
		return response;
	}

	public BaseResponse doEdit(TutorMajorEditRequest request) {
		BaseResponse response = new TutorMajorResponse(ResultType.SUCCESS);
		TutorMajorModel model = request.initModel();
		tutorMajorDao.updateEntityById(model);
		return response;
	}

	public Boolean delete(Long id) {
		TutorMajorModel model = new TutorMajorModel();
		model.setId(id);
		return tutorMajorDao.deleteEntityById(model);
	}

	public void deleteContentImage(Long id) {
		TutorMajorModel model = new TutorMajorModel();
		model.setId(id);
		TutorMajorModel entity = tutorMajorDao.findEntityById(model);
		entity.setContentImage(null);
		tutorMajorDao.updateEntityById(entity);
	}

}
