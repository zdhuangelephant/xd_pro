package com.xiaodou.ms.service.selftaught;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.selftaught.CourseDao;
import com.xiaodou.ms.model.selftaught.CourseModel;
import com.xiaodou.summer.dao.pagination.Page;
@Service("courseService")
public class CourseService {
	@Resource(name="courseDao")
	CourseDao courseDao;

	/**
	 * 获取所有某专业下的所有课程
	 * 
	 * @return
	 */
	public List<CourseModel> queryCourseByMajorId(String majorId) {
		Map<String, Object> inputArgument = new HashMap<String, Object>();
		inputArgument.put("majorId", majorId);
		Map<String, Object> outputField = new HashMap<>();
		outputField.put("courseId", "");
		outputField.put("courseName", "");
		outputField.put("courseImage", "");
		outputField.put("examDate", "");
		outputField.put("amount", "");
		outputField.put("isLatest", "");
		outputField.put("majorId", "");
		Page<CourseModel> CourseList = courseDao.queryListByCond0(inputArgument, outputField, null);
		return CourseList.getResult();
	}

	/**
	 * 查看课程详情
	 * 
	 * @param courseId
	 */
	public CourseModel findCourseId(String courseId) {
		CourseModel courseModel = new CourseModel();
		courseModel.setCourseId(courseId);
		return courseDao.findEntityById(courseModel);
	}

	/**
	 * 更新课程详情
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean editCourse(CourseModel entity) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("courseId", entity.getCourseId());
		return courseDao.updateEntity(cond, entity);
	}

	/**
	 * 增加课程
	 * 
	 * @param courseModel
	 */
	public void addCourse(CourseModel courseModel) {
		this.courseDao.addEntity(courseModel);
	}

	/**
	 * 删除课程
	 * 
	 * @param courseId
	 */
	public Boolean deleteCourse(String courseId) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("courseId", courseId);
		Boolean result = courseDao.deleteEntity(cond);
		return result;
	}


}
