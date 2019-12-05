package com.xiaodou.ms.dao.knowledge;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.ms.model.knowledge.ForumModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name ForumDao CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月7日
 * @description 知识分享Dao
 * @version 1.0
 */
@Repository("forumDao")
public class ForumDao extends BaseDao<ForumModel> {


  public ForumModel cascadeFindById(ForumModel entity) {
    return (ForumModel) this.getSqlSession().selectOne("CascadeForumModel.cascadeFindById", entity);
  }

  public Page<ForumModel> cascadeFindListByCond(IQueryParam param, Page<ForumModel> page) {
    Map<String, Object> cond = Maps.newHashMap();
    if (null != param && null != param.getInput() && param.getInput().size() > 0) {
    	cond.put("input", param.getInput());
    }
      
    if (null != param && null != param.getOutput() && param.getOutput().size() > 0) {
    	 cond.put("output", param.getOutput());
    }
     
    if (null != param && null != param.getSort() && param.getSort().size() > 0) {
    	cond.put("sort", param.getSort());
    }
    if (null != param && null != param.getLimit() && param.getLimit().size() > 0) {
    	cond.put("limit", param.getLimit());
    }
    return this.selectPaginationList("CascadeForumModel.cascadeFindListByCond", cond, page);
  }
  
  
 
  
}
