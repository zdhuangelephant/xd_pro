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
import com.xiaodou.ms.model.topic.ForumCategoryModel;
import com.xiaodou.ms.web.request.topic.ForumCategoryCreateRequest;
import com.xiaodou.ms.web.request.topic.ForumCategoryEditRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.CategoryCreateResponse;
import com.xiaodou.ms.web.response.course.CategoryEditResponse;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/7/21.
 * 
 * @author Administrator
 *
 */
@Service("topicCategoryService")
public class TopicCategoryService {
  /**
   * 资源分类DAO
   */
  @Resource
  ForumCategoryDao forumCategoryDao;

  /**
   * 新增目录
   * 
   * @param entity
   * @return
   */
  public ForumCategoryModel addCategory(ForumCategoryModel entity) {
    return forumCategoryDao.addEntity(entity);
  }

  /**
   * 新增目录
   * 
   * @param request
   * @return
   */
  public CategoryCreateResponse addCategory(ForumCategoryCreateRequest request) {
    ForumCategoryModel categoryModel = new ForumCategoryModel();
    categoryModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    categoryModel.setTitle(request.getTitle());
    categoryModel.setOutline(request.getOutline());
    categoryModel.setContent(request.getContent());
    categoryModel.setImages(request.getImages());
    categoryModel.setShortName(request.getShortName());
    categoryModel.setMoudle(request.getMoudle());
    ForumCategoryModel resultModel = this.addCategory(categoryModel);
    CategoryCreateResponse response = new CategoryCreateResponse(ResultType.SUCCESS);
    response.setCatId(resultModel.getId());
    return response;
  }

  /**
   * 删除目录
   * 
   * @param catId
   * @return
   */
  public Boolean deleteCategory(Integer catId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", catId);
    return forumCategoryDao.deleteEntity(cond);
  }

  /**
   * 更新目录
   * 
   * @param entity
   * @return
   */
  public Boolean editCategory(ForumCategoryModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    return forumCategoryDao.updateEntity(cond, entity);
  }

  /**
   * 目录编辑
   * 
   * @param request
   * @return
   */
  public CategoryEditResponse editCategory(ForumCategoryEditRequest request) {
    ForumCategoryModel categoryModel = new ForumCategoryModel();
    categoryModel.setTitle(request.getTitle());
    categoryModel.setContent(request.getContent());
    categoryModel.setMoudle(request.getMoudle());
    categoryModel.setImages(request.getImages());
    categoryModel.setShortName(request.getShortName());
    categoryModel.setOutline(request.getOutline());
    categoryModel.setId(request.getId());
    Boolean aBoolean = this.editCategory(categoryModel);
    CategoryEditResponse response = null;
    if (aBoolean) {
      response = new CategoryEditResponse(ResultType.SUCCESS);
    } else {
      response = new CategoryEditResponse(ResultType.SYS_FAIL);
    }
    return response;
  }

  /**
   * 根据主键查询
   * 
   * @param catId
   * @return
   */
  public ForumCategoryModel findCategoryById(Long id) {
    ForumCategoryModel entity = new ForumCategoryModel();
    entity.setId(id);
    return forumCategoryDao.findEntityById(entity);
  }



  /**
   * 根据id查询
   * 
   * @param id
   * @return
   */
  public Page<ForumCategoryModel> queryCourseByCatId(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("moudle", "");
    output.put("shortName", "");
    output.put("title", "");
    output.put("content", "");
    output.put("outline", "");
    output.put("images", "");
    output.put("createTime", "");
    return forumCategoryDao.queryListByCond0(cond, output, null);
  }


  /**
   * 获取所有栏目
   * 
   * @return
   */
  public List<ForumCategoryModel> queryAllCategory() {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("moudle", "");
    output.put("shortName", "");
    output.put("title", "");
    output.put("content", "");
    output.put("outline", "");
    output.put("images", "");
    output.put("createTime", "");
    Page<ForumCategoryModel> categoryList =
        forumCategoryDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 
   * 根据id获取话题分类列表
   * 
   * @param id
   * @return
   */
  public List<ForumCategoryModel> queryAllCategory(Long id) {
    List<ForumCategoryModel> result = new ArrayList<ForumCategoryModel>();
    List<ForumCategoryModel> list = this.queryCategoryByMoudle(id);
    result.addAll(list);
    for (ForumCategoryModel model : list) {
      result.addAll(this.queryAllCategory(model.getId()));
    }
    return result;
  }

  /**
   * 根据moudle获取话题分类数据
   * 
   * @param moudle
   * @return
   */
  public List<ForumCategoryModel> queryCategoryByMoudle(Long moudle) {
    Map<String, Object> cond = new HashMap();
    cond.put("moudle", moudle);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "1");
    output.put("moudle", "1");
    output.put("title", "1");
    output.put("outline", "1");
    output.put("content", "1");
    output.put("images", "1");
    output.put("shortName", "1");
    output.put("createTime", "1");
    Page<ForumCategoryModel> categoryList =
        forumCategoryDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 获取所有栏目Id,map
   * 
   * @return
   */

  public Map<String, Object> queryAllCategoryMap() {
    List<ForumCategoryModel> ForumCategoryModelList = this.queryAllCategory();
    // Map<Integer,Object> resultMap = new HashMap<>();
    Map<String, Object> resultMap = DynamicLocalCache.getLocalCacheMap();
    if (resultMap != null) {
      return resultMap;
    } else {
      for (ForumCategoryModel categoryModel : ForumCategoryModelList) {
        resultMap.put(String.valueOf(categoryModel.getId()), categoryModel);
      }
      return resultMap;
    }

  }

}
