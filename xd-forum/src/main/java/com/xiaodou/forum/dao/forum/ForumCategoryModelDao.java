package com.xiaodou.forum.dao.forum;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.forum.dao.BaseProcessDao;
import com.xiaodou.forum.model.forum.ForumCategoryModel;

/**
 * 话题分类基础dao
 * 
 * @author bing.cheng
 * 
 */

@Repository("forumCategoryModelDao")
public class ForumCategoryModelDao extends BaseProcessDao<ForumCategoryModel> {

  public void updatePeopleNumber(Long id, Integer peopleNumber) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("id", id);
    input.put("peopleNumber", peopleNumber);
    getSqlSession().update(getEntityClass().getSimpleName() + ".updatePeopleNumber", input);
  }

  public ForumCategoryModel queryOneById(String catagoryId, Map<String, Object> outputField) {
    Map<String, Map<String, Object>> mapParam = Maps.newHashMap();
    Map<String, Object> inputArgument = Maps.newHashMap();
    inputArgument.put("id", catagoryId);
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    return (ForumCategoryModel) this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".queryList", mapParam);
  }

}
