package com.xiaodou.ms.service.ruide;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.ruide.StudentDao;
import com.xiaodou.ms.model.ruide.ActivityModel;
import com.xiaodou.ms.model.ruide.StudentModel;
import com.xiaodou.ms.web.request.ruide.ActivityAddRequest;
import com.xiaodou.ms.web.request.ruide.StudentAddRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.ruide.ActivityResponse;
import com.xiaodou.ms.web.response.ruide.StudentResponse;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

@Service("studentService")
public class StudentService {
	@Resource
	StudentDao studentDao;
	
	public List<StudentModel> queryByCond(String author) {
		IQueryParam param = new QueryParam();
		param.addSort("createTime", Sort.DESC);
		param.addInput("authorLike", author);
		
		param.addOutput("id", "");
		param.addOutput("majorId", "");
		param.addOutput("thinkDesc", "");
		param.addOutput("author", "");
		param.addOutput("content", "");
		param.addOutput("portrait", "");
		param.addOutput("publishTime", "");
		param.addOutput("createTime", "");
		param.addOutput("contentImage", "");
		
		return studentDao.findEntityListByCond(param, null).getResult();
	}

	public BaseResponse doAdd(StudentAddRequest request) {
		
		BaseResponse response = new StudentResponse(ResultType.SUCCESS);
		StudentModel model = request.initModel();
	    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
	    studentDao.addEntity(model);
		return response;
	}

	public Boolean delete(Long id) {
		StudentModel model = new StudentModel();
		model.setId(id);
		return studentDao.deleteEntityById(model);
	}

	public StudentModel getStudentById(Long id) {
		StudentModel model = new StudentModel();
		model.setId(id);
		return studentDao.findEntityById(model);
	}

	public BaseResponse doEdit(StudentAddRequest request) {
		BaseResponse response = new StudentResponse(ResultType.SUCCESS);
		StudentModel model = request.initModel();
		studentDao.updateEntityById(model);
		return response;
	}

	public void deleteContentImage(Long id) {
		StudentModel model = new StudentModel();
		model.setId(id);
		StudentModel entity = studentDao.findEntityById(model);
		entity.setContentImage(null);
		studentDao.updateEntityById(entity);
	}
}
