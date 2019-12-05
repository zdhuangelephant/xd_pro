package com.xiaodou.moofficial.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.moofficial.dao.ForumDao;
import com.xiaodou.moofficial.entity.ForumModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("forumService")
public class ForumService {
	
	@Resource
	ForumDao forumDao;
	
	public List<ForumModel> findForumList(Long startId, Integer pageNo, Integer pageSize) {
		HashMap<String, Object> output = new HashMap<String, Object>();
		output.put("forumId", "");
		output.put("authorId", "");
		output.put("forumTitle", "");
		output.put("forumContent", "");
		output.put("forumType", "");
		output.put("forumPraiserNum", "");
		output.put("forumCover", "");
		output.put("createTime", "");
		HashMap<String, Object> inputs = new HashMap<String, Object>();
		inputs.put("status", 1);
		ArrayList<Integer> params = Lists.newArrayList();
		params.add(1);
		params.add(2);
		inputs.put("forumTypeList", params);
		if (startId != null) {
			inputs.put("idLower", startId);
		}
		List<ForumModel> result = listForum(inputs, output, pageNo, pageSize).getResult();
		return result;
	}
	
	private Page<ForumModel> listForum(Map<String, Object> inputs, Map<String, Object> outputs, Integer pageNo, Integer pageSize) {
		IQueryParam queryParam = new QueryParam();
		queryParam.addInputs(inputs);
		queryParam.addOutputs(outputs);
		queryParam.addSort("createTime", Sort.DESC);
		Page<ForumModel> page = new Page<ForumModel>();
		if (null != pageNo) {
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
		} else {
			page = null;
		}
		return forumDao.findEntityListByCond(queryParam, page);
	}

	public ForumModel findForumById(String forumId) {
		ForumModel forum = new ForumModel();
		forum.setForumId(forumId);
		return forumDao.findEntityById(forum);
	}

	public Boolean updateForum(ForumModel forumModel) {
		
		return forumDao.updateEntityById(forumModel);
	}

}
