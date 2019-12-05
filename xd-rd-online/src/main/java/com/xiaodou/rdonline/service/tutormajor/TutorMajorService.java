package com.xiaodou.rdonline.service.tutormajor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.rdonline.service.facade.IRdServiceFacade;

/**
 * @author zdh:
 * @date 2017年6月8日
 *
 */
@Service("tutorMajorService")
public class TutorMajorService {
	@Resource
	IRdServiceFacade rdServiceFacade;

	public List<TutorMajorModel> search(String authorName, Short type) {
		return rdServiceFacade.search(authorName, type);
	}

	public TutorMajorModel getTutorMajorPaperById(Long id) {
		TutorMajorModel tutor = rdServiceFacade.getTutorMajorPaperById(id);
		return tutor;
	}

	public List<TutorMajorModel> findTutorMajorList(Short type, Integer pageNo, Integer pageSize) {
		Map<String, Object> inputs = new HashMap<>();
		inputs.put("type", type);
		Map<String, Object> outputs = new HashMap<>();
		outputs.put("id", "");
		outputs.put("title", "");
		outputs.put("type", "");
		outputs.put("subtitle", "");
		outputs.put("publishTime", "");
		outputs.put("image", "");
		outputs.put("author", "");
		outputs.put("content", "");
		outputs.put("thumbNums", "");
		outputs.put("readQuantity", "");
		outputs.put("majorCategoryId", "");
		outputs.put("majorName", "");
		outputs.put("contentImage", "");
		return rdServiceFacade.listTutorMajor(inputs, outputs, pageNo, pageSize).getResult();
	}

	public List<TutorMajorModel> findPaperList(Integer pageNo, Integer pageSize) {
		Map<String, Object> inputs = new HashMap<>();
		inputs.put("type", 3);
		Map<String, Object> outputs = new HashMap<>();
		outputs.put("id", "");
		outputs.put("title", "");
		outputs.put("type", "");
		outputs.put("publishTime", "");
		outputs.put("author", "");
		outputs.put("content", "");
		outputs.put("thumbNums", "");
		outputs.put("readQuantity", "");
		outputs.put("contentImage", "");
		return rdServiceFacade.listTutorMajor(inputs, outputs, pageNo, pageSize).getResult();
	}

	public Boolean updateThumbsReadQuality(TutorMajorModel tutorMajorModel) {
		return rdServiceFacade.updateTutorMajor(tutorMajorModel);
	}

	public Boolean updateTutorMajorModel(TutorMajorModel tutorMajorModel) {
		return rdServiceFacade.updateTutorMajor(tutorMajorModel);
	}

}
