package com.xiaodou.ms.service.sms;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.local.DynamicLocalCache;
import com.xiaodou.ms.dao.sms.SmsTemplateTypeDao;
import com.xiaodou.ms.model.sms.SmsChannelModel;
import com.xiaodou.ms.model.sms.SmsTemplateTypeModel;
import com.xiaodou.ms.web.request.sms.TemplateTypeCreateRequest;
import com.xiaodou.ms.web.request.sms.TemplateTypeEditRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.ResourceCourseCreateResponse;
import com.xiaodou.summer.dao.pagination.Page;

@Service("smsTemplateTypeService")
public class SmsTemplateTypeService {
	
	/**
	 * 模板类型DAO
	 */
	@Resource
	SmsTemplateTypeDao smsTemplateTypeDao;
	
	
	/**
	   * 查询模板类型
	   * @param id
	   * @return
	   */
	  public SmsTemplateTypeModel findTemplateTypeById(Integer id){
		  SmsTemplateTypeModel cond = new SmsTemplateTypeModel();
	    cond.setId(id);
	    return smsTemplateTypeDao.findEntityById(cond);
	  }

	  /**
	   * 新增模板类型
	   * @param entity
	   * @return
	   */
	  public SmsTemplateTypeModel addTemplateType(SmsTemplateTypeModel entity){
	    return smsTemplateTypeDao.addEntity(entity);
	  }

	  /**
	   * 更新模板类型
	   * @param smsTemplateTypeModel
	   * @return
	   */
	  public Boolean editTemplateType(SmsTemplateTypeModel smsTemplateTypeModel){
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("id",smsTemplateTypeModel.getId());
	    return smsTemplateTypeDao.updateEntity(cond, smsTemplateTypeModel);
	  }

	  /**
	   * 更新模板类型
	   * @param request
	   * @return
	   */
	  public Boolean editTemplateType(TemplateTypeEditRequest request){
		  SmsTemplateTypeModel smsTemplateTypeModel = new SmsTemplateTypeModel();
		 smsTemplateTypeModel.setId(request.getId());
		 smsTemplateTypeModel.setName(request.getName());
		 smsTemplateTypeModel.setDescription(request.getDescription());
	    return this.editTemplateType(smsTemplateTypeModel);
	  }

	  /**
	   * 查询所有的模板类型
	   * @return
	   */
	  public Page<SmsTemplateTypeModel> queryAllTemplateType(){
	    Map<String,Object> cond = new HashMap<>();
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("name","");
	    output.put("description","");
	    output.put("channelIds","");
	    output.put("createTime","");
	    output.put("retryTime","");
	    output.put("cacheTime","");
	    return smsTemplateTypeDao.queryListByCondWithOutPage(cond, output);
	  }

	 
	  /**
	   * 根据栏目级联查询模板类型
	   * @param catId
	   * @return
	   */
	  public Page<SmsTemplateTypeModel> cascadeQueryTemplateTypeByCatId(String channelIds){	
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("channelIds", channelIds);
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("name","");
	    output.put("description","");
	    output.put("channelIds","");
	    output.put("createTime","");
	    output.put("retryTime","");
	    output.put("cacheTime","");
	    return smsTemplateTypeDao.cascadeQueryProduct(cond, output);
	  }
	 

	  /**
	   * 删除模板类型
	   * @param id
	   * @return
	   */
	  public Boolean deleteTemplateType(Integer id){
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("id",id);
	    return smsTemplateTypeDao.deleteEntity(cond);
	  }


	 /**
	   * 新增模板类型
	   * @param request
	   * @return
	   */
	  public ResourceCourseCreateResponse addTemplateType(TemplateTypeCreateRequest request){
		  SmsTemplateTypeModel templateTypeModel = new SmsTemplateTypeModel();
		  templateTypeModel.setRetryTime(request.getRetryTime());
		  templateTypeModel.setCacheTime(request.getCacheTime());
		  templateTypeModel.setName(request.getName());
		  templateTypeModel.setDescription(request.getDescription());
		  templateTypeModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
		  SmsTemplateTypeModel resultModel = this.addTemplateType(templateTypeModel);

	    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
	    return response;
	  }
	  /**
	   * 获取所有栏目Id,map
	   * @return
	   */
	 
	  public Map<String,Object> queryAllTemplateTypeMap() {
	    List<SmsTemplateTypeModel> templateTypeModelList = this.queryTemplateTypeList();
	    //Map<Integer,Object> resultMap = new HashMap<>();
	    Map<String,Object> resultMap=DynamicLocalCache.getLocalCacheMap();
	    if(resultMap !=null){
	    	return resultMap;
	    }else{
	    	for (SmsTemplateTypeModel templateTypeMode:templateTypeModelList){
	  	      resultMap.put(String.valueOf(templateTypeMode.getId()),templateTypeMode);
	  	    }
	  	    return resultMap;
	    }
	    
	  }
	  
	  /**
	   * 获取所有栏目
	   * @return
	   */
	  public List<SmsTemplateTypeModel> queryTemplateTypeList() {
	    Map<String, Object> cond = new HashMap<String, Object>();
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("name","");
	    output.put("description","");
	    output.put("channelIds","");
	    output.put("createTime","");
	    output.put("retryTime","");
	    output.put("cacheTime","");
	    Page<SmsTemplateTypeModel> templateTypeList = smsTemplateTypeDao.queryListByCondWithOutPage(cond, output);
	    return templateTypeList.getResult();
	  }
	  
	  /**
	   * 下拉框目录列表
	   * @return
	   */
	  public List<SmsTemplateTypeModel> TemplateTypeSelect(String selectedId){
	    List<SmsTemplateTypeModel> templateTypeList = this.queryTemplateTypeList();
	    return templateTypeList;
	  }

}
