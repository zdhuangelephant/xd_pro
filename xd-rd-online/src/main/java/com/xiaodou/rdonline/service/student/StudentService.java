package com.xiaodou.rdonline.service.student;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.rdonline.domain.student.StudentModel;
import com.xiaodou.rdonline.service.facade.IRdServiceFacade;

/**
 * @author zdh:
 * @date 2017年6月8日
 */
@Service("studentService")
public class StudentService {
	@Resource
	IRdServiceFacade rdServiceFacade;

	public List<StudentModel> queryByCond(String author) {
		HashMap<String, Object> inputs = new HashMap<String, Object>();
		HashMap<String, Object> outputs = new HashMap<String, Object>();
		inputs.put("authorLike", author);

		outputs.put("id", "");
		outputs.put("majorId", "");
		outputs.put("thinkDesc", "");
		outputs.put("author", "");
		outputs.put("content", "");
		outputs.put("portrait", "");
		outputs.put("publishTime", "");
		outputs.put("createTime", "");
		outputs.put("thumbNums", "");
		outputs.put("readQuantity", "");
		outputs.put("isDel", "");
		outputs.put("contentImage", "");
		
		return rdServiceFacade.listStudent(inputs, outputs,null,null).getResult();
	}

	public List<StudentModel> findStudentList(Long startId, Integer pageNo, Integer pageSize ) {
		HashMap<String, Object> outputs = new HashMap<String, Object>();
		outputs.put("id", "");
		outputs.put("majorId", "");
		outputs.put("thinkDesc", "");
		outputs.put("author", "");
		outputs.put("content", "");
		outputs.put("portrait", "");
		outputs.put("publishTime", "");
		outputs.put("createTime", "");
		outputs.put("thumbNums", "");
		outputs.put("readQuantity", "");
		outputs.put("isDel", "");
		outputs.put("contentImage", "");
		HashMap<String, Object> inputs = new HashMap<String, Object>();
		if (startId != null) {
			inputs.put("idNotIn", startId);
		}
		return rdServiceFacade.listStudent(inputs, outputs, pageNo, pageSize).getResult();
	}

	public StudentModel findStudentById(Long stuId){
		StudentModel stu = rdServiceFacade.getStudentById(stuId);
		return stu;
	}

	public Boolean updateStudent(StudentModel student) {
		return rdServiceFacade.updateStudent(student);
	}

}
