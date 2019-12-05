package com.xiaodou.ms.service.topic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.local.DynamicLocalCache;
import com.xiaodou.ms.dao.topic.ForumCategoryDao;
import com.xiaodou.ms.dao.topic.ForumListDao;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.topic.ForumCategoryModel;
import com.xiaodou.ms.model.topic.ForumListModel;
import com.xiaodou.ms.web.request.product.ProductCreateRequest;
import com.xiaodou.ms.web.request.product.ProductEditRequest;
import com.xiaodou.ms.web.request.topic.ForumCreateRequest;
import com.xiaodou.ms.web.request.topic.ForumEditRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.ResourceCourseCreateResponse;
import com.xiaodou.summer.dao.pagination.Page;

@Service("topicService")
public class TopicService {
	
	@Resource
	ForumListDao forumListDao;
	
	/**
	   * 查询话题
	   * @param id
	   * @return
	   */
	  public ForumListModel findSubjectById(Integer id){
		  ForumListModel cond = new ForumListModel();
	    cond.setId(id);
	    return forumListDao.findEntityById(cond);
	  }

	  /**
	   * 新增话题
	   * @param entity
	   * @return
	   */
	  public ForumListModel addSubject(ForumListModel entity){
	    return forumListDao.addEntity(entity);
	  }

	  /**
	   * 更新话题
	   * @param productModel
	   * @return
	   */
	  public Boolean editSubject(ForumListModel forumListModel){
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("id",forumListModel.getId());
	    return forumListDao.updateEntity(cond, forumListModel);
	  }

	  /**
	   * 更新话题
	   * @param request
	   * @return
	   */
	  public Boolean editSubject(ForumEditRequest request){
		  ForumListModel forumListModel = new ForumListModel();
		  forumListModel.setStatus(request.getStatus());
		  forumListModel.setDigest(request.getDigest());
		  forumListModel.setTop(request.getTop());
		  forumListModel.setRecommend(request.getRecommend());
	      forumListModel.setId(request.getId());
	    return this.editSubject(forumListModel);
	  }

	  /**
	   * 查询所有的话题
	   * @return
	   */
	  public Page<ForumListModel> queryAllCourse(){
	    Map<String,Object> cond = new HashMap<>();
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("categoryId","");
	    output.put("title","");
	    output.put("outline","");
	    output.put("createTime","");
	    return forumListDao.queryListByCond0(cond, output, null);
	  }

	  /**
	   * 根据标题查询
	   * @param catId
	   * @return
	   */
	  public Page<ForumListModel> queryCourseByCatId(Integer catId){
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("categoryId",catId);
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("categoryId","");
	    output.put("title","");
	    output.put("outline","");
	    output.put("createTime","");
	    return forumListDao.queryListByCond0(cond, output, null);
	  }

	  /**
	   * 根据栏目级联查询话题
	   * @param catId
	   * @return
	   */
	  public Page<ForumListModel> cascadeQueryCourseByCatId(Integer categoryId){	
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("categoryId", categoryId);
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","1");
	    output.put("status","1");
	    output.put("title","1");
	    output.put("outline","1");
	    output.put("createTime","1");
	    output.put("content","1");
	    output.put("digest","1");
	    output.put("top","1");
	    output.put("recommend","1");
	    output.put("images","1");
	    output.put("categoryId","1");
	    output.put("publisherId","1");
	    output.put("repliesNumber","1");
	    output.put("praiseNumber","1");
	    output.put("assign","1");
	    output.put("tag","1");
	    output.put("operator","1");
	    output.put("operatorip","1");
	    output.put("updateTime","1");
	    return forumListDao.cascadeQueryProduct(cond, output);
	  }
	  /**
	   * 根据status=0查询关闭话题
	   * @param Status
	   * @return
	   */
	  public Page<ForumListModel> cascadeQueryCourseByStatus(Integer categoryId,Integer status){	
		    Map<String,Object> cond = new HashMap<>();
		    cond.put("status", status);
		    cond.put("categoryId", categoryId);
		    Map<String,Object> output = new HashMap<>();
		    output.put("id","1");
		    output.put("title","1");
		    output.put("status", "1");
		    output.put("outline","1");
		    output.put("createTime","1");
		    output.put("content","1");
		    output.put("digest","1");
		    output.put("top","1");
		    output.put("recommend","1");
		    output.put("images","1");
		    output.put("categoryId","1");
		    output.put("publisherId","1");
		    output.put("repliesNumber","1");
		    output.put("praiseNumber","1");
		    output.put("assign","1");
		    output.put("tag","1");
		    output.put("operator","1");
		    output.put("operatorip","1");
		    output.put("updateTime","1");
		    return forumListDao.cascadeQueryProduct(cond, output);
		  }

	  /**
	   * 删除话题
	   * @param id
	   * @return
	   */
	  public Boolean deleteSubject(Integer id){
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("id",id);
	    return forumListDao.deleteEntity(cond);
	  }


	 /**
	   * 新增话题
	   * @param request
	   * @return
	   */
	  public ResourceCourseCreateResponse addSubject(ForumCreateRequest request){
	    ForumListModel subjectModel = new ForumListModel();
	    subjectModel.setCategoryId(request.getCategoryId());
	    subjectModel.setTitle(request.getTitle());
	    subjectModel.setOutline(request.getOutline());
	    subjectModel.setContent(request.getContent());
	    subjectModel.setImages(request.getImages());
	    subjectModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
	    ForumListModel resultModel = this.addSubject(subjectModel);

	    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
	    return response;
	  }
	  /**
	   * 获取所有栏目Id,map
	   * @return
	   */
	 
	  public Map<String,Object> queryAllForumMap() {
	    List<ForumListModel> forumModelList = this.queryAllForum();
	    //Map<Integer,Object> resultMap = new HashMap<>();
	    Map<String,Object> resultMap=DynamicLocalCache.getLocalCacheMap();
	    if(resultMap !=null){
	    	return resultMap;
	    }else{
	    	for (ForumListModel forumModel:forumModelList){
	  	      resultMap.put(String.valueOf(forumModel.getId()),forumModel);
	  	    }
	  	    return resultMap;
	    }
	    
	  }
	  
	  /**
	   * 获取所有栏目
	   * @return
	   */
	  public List<ForumListModel> queryAllForum() {
	    Map<String, Object> cond = new HashMap<String, Object>();
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("title","");
	    output.put("outline","");
	    output.put("content","");
	    output.put("images","");
	    output.put("digest","");
	    output.put("top","");
	    output.put("recommend","");
	    output.put("praiseNumber","");
	    output.put("tag","");
	    output.put("createTime","");
	    Page<ForumListModel> categoryList = forumListDao.queryListByCond0(cond, output, null);
	    return categoryList.getResult();
	  }

}
