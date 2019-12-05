package com.xiaodou.rdonline.service.majorcategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.rdonline.domain.majorCategory.MajorCategoryModel;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.rdonline.service.facade.IRdServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

/**
* @author zdh:
* @date 2017年6月13日
*
*/
@Service("majorCategoryService")
public class MajorCategoryService {
	@Resource
	IRdServiceFacade rdServiceFacade;

	public List<MajorCategoryModel> findMajorCategoryList(Integer pageNo, Integer pageSize) {
		Map<String,Object> inputs = new HashMap<>();
		Map<String, Object> outputs = new HashMap<>();
		outputs.put("id", "");
		outputs.put("image", "");
		outputs.put("majorCategory", "");
		outputs.put("remark", "");
		outputs.put("createTime", "");
		Page<MajorCategoryModel> result = rdServiceFacade.getMajorCategoryList(inputs, outputs, pageNo, pageSize);
		if (result == null) {
			return null;
		}
		List<MajorCategoryModel> list = result.getResult();
		for(MajorCategoryModel major : list){
			Map<String,Object> input = Maps.newHashMap();
			input.put("majorCategoryId", major.getId());
			Map<String, Object> output = new HashMap<>();
			output.put("id", "");
			output.put("image", "");
			output.put("majorName", "");
			Page<TutorMajorModel> page = rdServiceFacade.listTutorMajor(input, output, 1, 3);
			if(null != page) major.setListTutorMajor(page.getResult());
		}
		return list;
	}
	
}
