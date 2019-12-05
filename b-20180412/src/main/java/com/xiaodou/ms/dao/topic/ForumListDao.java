package com.xiaodou.ms.dao.topic;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.ms.dao.BaseProcessDao;
import com.xiaodou.ms.model.topic.ForumCommentModel;
import com.xiaodou.ms.model.topic.ForumListModel;
import com.xiaodou.summer.dao.pagination.Page;

import org.springframework.stereotype.Repository;

/**
 * Created by zyp on 15/6/26.
 */
@Repository("forumListDao")
public class ForumListDao extends BaseProcessDao<ForumListModel> {
	/**
	 * @param inputArgument
	 *            查询条件
	 * @return Page<Entity>
	 * @throws
	 * @Title: queryListByPaging
	 * @Description: 根据条件 查询 列表 (分页)
	 */
	public Page<ForumListModel> cascadeQueryProduct(Map inputArgument,
			Map output) {
		Map<String, Map> mapParam = new HashMap<String, Map>();
		mapParam.put("input", inputArgument);
		mapParam.put("output", output);
		return this.selectPaginationList(this.getEntityClass().getSimpleName()
				+ ".cascadeQueryProduct", mapParam, null);
	}
}
