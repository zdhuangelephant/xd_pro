package com.xiaodou.ms.service.sms;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.local.DynamicLocalCache;
import com.xiaodou.ms.dao.sms.SmsTemplateDao;
import com.xiaodou.ms.model.sms.SmsTemplateModel;
import com.xiaodou.ms.model.sms.SmsTemplateTypeModel;
import com.xiaodou.ms.web.request.sms.TemplateCreateRequest;
import com.xiaodou.ms.web.request.sms.TemplateEditRequest;
import com.xiaodou.ms.web.request.sms.TemplateTypeCreateRequest;
import com.xiaodou.ms.web.request.sms.TemplateTypeEditRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.ResourceCourseCreateResponse;
import com.xiaodou.summer.dao.pagination.Page;

@Service("smsTemplateService")
public class SmsTemplateService {
	

	/**
	 * 短信模板DAO
	 */
	@Resource
	SmsTemplateDao smsTemplateDao;
	
	
	/**
	   * 查询短信模板
	   * @param id
	   * @return
	   */
	  public SmsTemplateModel findTemplateById(Integer id){
		  SmsTemplateModel cond = new SmsTemplateModel();
	    cond.setId(id);
	    return smsTemplateDao.findEntityById(cond);
	  }

	  /**
	   * 新增短信模板
	   * @param entity
	   * @return
	   */
	  public SmsTemplateModel addTemplate(SmsTemplateModel entity){
	    return smsTemplateDao.addEntity(entity);
	  }

	  /**
	   * 更新短信模板
	   * @param smsTemplateModel
	   * @return
	   */
	  public Boolean editTemplate(SmsTemplateModel smsTemplateModel){
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("id",smsTemplateModel.getId());
	    return smsTemplateDao.updateEntity(cond, smsTemplateModel);
	  }

	  /**
	   * 更新短信模板
	   * @param request
	   * @return
	   */
	  public Boolean editTemplate(TemplateEditRequest request){
		  SmsTemplateModel smsTemplateModel = new SmsTemplateModel();
		 smsTemplateModel.setMessageContent(request.getMessageContent());
		 smsTemplateModel.setDescription(request.getDescription());
		 smsTemplateModel.setStatus(request.getStatus());
	    return this.editTemplate(smsTemplateModel);
	  }

	  /**
	   * 查询所有的短信模板
	   * @return
	   */
	  public Page<SmsTemplateModel> queryAllTemplate(){
	    Map<String,Object> cond = new HashMap<>();
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("messageContent","");
	    output.put("description","");
	    output.put("status","");
	    output.put("createTime","");
	    output.put("typeId","");
	    return smsTemplateDao.queryListByCondWithOutPage(cond, output);
	  }
	  /**
       * 根据状态查询短信模板
       * @return
       */
      public Page<SmsTemplateModel> queryTemplateByStatus(Integer status){
        Map<String,Object> cond = new HashMap<>();
        cond.put("status", status);
        Map<String,Object> output = new HashMap<>();
        output.put("id","");
        output.put("messageContent","");
        output.put("description","");
        output.put("status","");
        output.put("createTime","");
        output.put("typeId","");
        return smsTemplateDao.queryListByCondWithOutPage(cond, output);
      }
	 
	  /**
	   * 根据栏目级联查询短信模板
	   * @param catId
	   * @return
	   */
	  public Page<SmsTemplateModel> cascadeQueryTemplateByCatId(Integer typeId){	
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("typeId", typeId);
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("messageContent","");
	    output.put("description","");
	    output.put("status","");
	    output.put("createTime","");
	    output.put("typeId","");
	    return smsTemplateDao.cascadeQueryProduct(cond, output);
	  }
	 

	  /**
	   * 删除短信模板
	   * @param id
	   * @return
	   */
	  public Boolean deleteTemplate(Integer id){
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("id",id);
	    return smsTemplateDao.deleteEntity(cond);
	  }


	 /**
	   * 新增短信模板
	   * @param request
	   * @return
	   */
	  public ResourceCourseCreateResponse addTemplate(TemplateCreateRequest request){
		  SmsTemplateModel templateModel = new SmsTemplateModel();
		  templateModel.setTypeId(request.getTypeId());
		  templateModel.setMessageContent(request.getMessageContent());
		  templateModel.setDescription(request.getDescription());
		  templateModel.setId(request.getId());
		  templateModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
		  SmsTemplateModel resultModel = this.addTemplate(templateModel);

	    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
	    return response;
	  }
	  /**
	   * 获取所有栏目Id,map
	   * @return
	   */
	 
	  public Map<String,Object> queryAllTemplateMap() {
	    List<SmsTemplateModel> templateModelList = this.queryTemplateList();
	    //Map<Integer,Object> resultMap = new HashMap<>();
	    Map<String,Object> resultMap=DynamicLocalCache.getLocalCacheMap();
	    if(resultMap !=null){
	    	return resultMap;
	    }else{
	    	for (SmsTemplateModel templateMode:templateModelList){
	  	      resultMap.put(String.valueOf(templateMode.getId()),templateMode);
	  	    }
	  	    return resultMap;
	    }
	    
	  }
	  
	  /**
	   * 获取所有栏目
	   * @return
	   */
	  public List<SmsTemplateModel> queryTemplateList() {
	    Map<String, Object> cond = new HashMap<String, Object>();
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("messageContent","");
	    output.put("description","");
	    output.put("status","");
	    output.put("createTime","");
	    output.put("typeId","");
	    Page<SmsTemplateModel> templateList = smsTemplateDao.queryListByCondWithOutPage(cond, output);
	    return templateList.getResult();
	  }



}
