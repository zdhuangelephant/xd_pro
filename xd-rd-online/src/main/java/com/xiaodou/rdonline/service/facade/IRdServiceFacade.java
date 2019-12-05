package com.xiaodou.rdonline.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.rdonline.domain.activity.ActivityModel;
import com.xiaodou.rdonline.domain.majorCategory.MajorCategoryModel;
import com.xiaodou.rdonline.domain.student.StudentModel;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.summer.dao.pagination.Page;

public interface IRdServiceFacade {


	ActivityModel getActivityById(Long activityId);
	Page<ActivityModel> listActivity(Map<String, Object> inputs, Map<String, Object> outputs, Integer pageNo, Integer pageSize);

	
	StudentModel getStudentById(Long studentId);
	Page<StudentModel> listStudent(Map<String, Object> inputs, Map<String, Object> outputs,Integer pageNo,Integer pageSize);

	
	Page<TutorMajorModel> listTutorMajor(Map<String, Object> inputs, Map<String, Object> outputs,Integer pageNo,Integer pageSize);
	
	public TutorMajorModel getTutorMajorPaperById(Long tutorId);
	
	public List<TutorMajorModel> getAllTeacherAndMajor(Short type);
	
	
	public List<TutorMajorModel> search(String authorName, Short type);
	
	
	Boolean updateStudent(StudentModel studentModel);
	
	Boolean updateTutorMajor(TutorMajorModel tutorMajorModel);
	Boolean updateActivity(ActivityModel activity);
	Page<MajorCategoryModel> getMajorCategoryList(Map<String, Object> inputs, Map<String, Object> outputs,Integer pageNo,Integer pageSize);
}
