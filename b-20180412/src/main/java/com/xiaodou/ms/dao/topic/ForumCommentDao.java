package com.xiaodou.ms.dao.topic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.topic.ForumCommentModel;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * 
 * @author huangtao
 * 
 */
@Repository("forumCommentDao")
public class ForumCommentDao extends BaseProcessDao<ForumCommentModel> {
	/**
	 * @param inputArgument
	 *            查询条件
	 * @return Page<Entity>
	 * @throws
	 * @Title: queryListByPaging
	 * @Description: 根据条件 查询 列表 (分页)
	 */
	public Page<ForumCommentModel> cascadeQueryProduct(Map inputArgument,
			Map output) {
		Map<String, Map> mapParam = new HashMap<String, Map>();
		mapParam.put("input", inputArgument);
		mapParam.put("output", output);
		return this.selectPaginationList(this.getEntityClass().getSimpleName()
				+ ".cascadeQueryProduct", mapParam, null);
	}

}
