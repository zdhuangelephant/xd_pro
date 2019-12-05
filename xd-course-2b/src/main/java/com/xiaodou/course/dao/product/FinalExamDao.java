package com.xiaodou.course.dao.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.product.FinalExamModel;

@Repository("finalExamDao")
public class FinalExamDao extends BaseProcessDao<FinalExamModel> {
	  /**
	   * @param inputArgument 查询条件
	   * @return Page<Entity>
	   * @throws
	   * @Title: findFinalExamListByCond
	   * @Description: 根据条件 查询 列表 (分页)
	   */
	  @SuppressWarnings("rawtypes")
	  public List<FinalExamModel>  findFinalExamListByCond(Map inputArgument, Map output) {
	    Map<String, Map> mapParam = new HashMap<String, Map>();
	    mapParam.put("input", inputArgument);
	    mapParam.put("output", output);
	    return this.selectPaginationList(
	        this.getEntityClass().getSimpleName() + ".findFinalExamListByCond", mapParam, null).getResult();
	  }
}
