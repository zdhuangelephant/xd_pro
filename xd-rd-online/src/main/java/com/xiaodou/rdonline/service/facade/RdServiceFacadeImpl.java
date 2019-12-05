package com.xiaodou.rdonline.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.rdonline.dao.activity.ActivityDao;
import com.xiaodou.rdonline.dao.majorcategory.MajorCategoryDao;
import com.xiaodou.rdonline.dao.student.StudentDao;
import com.xiaodou.rdonline.dao.tutormajor.TutorMajorDao;
import com.xiaodou.rdonline.domain.activity.ActivityModel;
import com.xiaodou.rdonline.domain.majorCategory.MajorCategoryModel;
import com.xiaodou.rdonline.domain.student.StudentModel;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("rdServiceFacade")
public class RdServiceFacadeImpl implements IRdServiceFacade {
	@Resource(name = "rdActivityDao")
	ActivityDao activityDao;

	@Resource
	StudentDao studentDao;

	@Resource
	TutorMajorDao tutorMajorDao;
	
	@Resource
	MajorCategoryDao majorCategoryDao;
	
	@Override
	public StudentModel getStudentById(Long studentId) {
		StudentModel studentModel = new StudentModel();
		studentModel.setId(studentId);
		return studentDao.findEntityById(studentModel);
	}
	public Page<StudentModel> listStudent(Map<String, Object> inputs, Map<String, Object> outputs, Integer pageNo, Integer pageSize) {
		IQueryParam queryParam = new QueryParam();
		queryParam.addInputs(inputs);
		queryParam.addOutputs(outputs);
		queryParam.addSort("createTime", Sort.DESC);
		Page<StudentModel> page = new Page<StudentModel>();
		if (null != pageNo) {
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
		} else {
			page = null;
		}
		return studentDao.findEntityListByCond(queryParam, page);
	}


	@Override
	public ActivityModel getActivityById(Long activityId) {
		ActivityModel activityModel = new ActivityModel();
		activityModel.setId(activityId);
		return activityDao.findEntityById(activityModel);
	}

	@Override
	public Page<ActivityModel> listActivity(Map<String, Object> inputs, Map<String, Object> outputs, Integer pageNo, Integer pageSize) {
		IQueryParam queryParam = new QueryParam();
		queryParam.addInputs(inputs);
		queryParam.addOutputs(outputs);
		queryParam.addSort("createTime", Sort.DESC);
		Page<ActivityModel> page = new Page<ActivityModel>();
		if (null != pageNo) {
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
		} else {
			page = null;
		}
		return activityDao.findEntityListByCond(queryParam, page);
	}


	public TutorMajorModel getTutorMajorPaperById(Long tutorId) {
		TutorMajorModel model = new TutorMajorModel();
		model.setId(tutorId);
		return tutorMajorDao.findEntityById(model);
	}

	@Override
	public Page<TutorMajorModel> listTutorMajor(Map<String, Object> inputs, Map<String, Object> outputs,Integer pageNo,Integer pageSize) {
		IQueryParam queryParam = new QueryParam();
		queryParam.addInputs(inputs);
		queryParam.addOutputs(outputs);
		queryParam.addSort("createTime", Sort.DESC);
		Page<TutorMajorModel> page = new Page<TutorMajorModel>();
		if (null != pageNo && null != pageSize) {
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
		} else {
			page = null;
		}
		return tutorMajorDao.findEntityListByCond(queryParam, page);
	}

	
	
	public List<TutorMajorModel> getAllTeacherAndMajor(Short type) {
		IQueryParam param = new QueryParam();
		param.addInput("type", type);

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

	public TutorMajorModel getMajorById(Long id) {
		TutorMajorModel model = new TutorMajorModel();
		model.setId(id);
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
	
	@Override
	public Boolean updateStudent(StudentModel studentModel) {
		 return studentDao.updateEntityById(studentModel);
	}
	
	@Override
	public Boolean updateTutorMajor(TutorMajorModel tutorMajorModel) {
		 return tutorMajorDao.updateEntityById(tutorMajorModel);
	}
	@Override
	public Boolean updateActivity(ActivityModel activity) {
		return activityDao.updateEntityById(activity);
	}
	@Override
	public Page<MajorCategoryModel> getMajorCategoryList(Map<String, Object> inputs, Map<String, Object> outputs,Integer pageNo,Integer pageSize) {
		IQueryParam queryParam = new QueryParam();
		queryParam.addInputs(inputs);
		queryParam.addOutputs(outputs);
		queryParam.addSort("createTime", Sort.ASC);
		Page<MajorCategoryModel> page = new Page<MajorCategoryModel>();
		if (null != pageNo && null != pageSize) {
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
		} else {
			page = null;
		}
		return majorCategoryDao.findEntityListByCond(queryParam, page);
	}

	
}
