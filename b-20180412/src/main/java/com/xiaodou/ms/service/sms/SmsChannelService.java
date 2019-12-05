package com.xiaodou.ms.service.sms;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.local.DynamicLocalCache;
import com.xiaodou.ms.dao.sms.SmsChannelDao;
import com.xiaodou.ms.dao.sms.SmsMerchantDao;
import com.xiaodou.ms.model.sms.SmsChannelModel;
import com.xiaodou.ms.web.request.sms.ChannelCreateRequest;
import com.xiaodou.ms.web.request.sms.ChannelEditRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.ResourceCourseCreateResponse;
import com.xiaodou.summer.dao.pagination.Page;

@Service("smsChannelService")
public class SmsChannelService {
	/**
	 * 通道DAO
	 */
	@Resource
	SmsChannelDao smsChannelDao;
	/**
	 * 供应商DAO
	 */
	@Resource
	SmsMerchantDao smsMerchantDao;
	
	
	/**
	   * 查询通道
	   * @param id
	   * @return
	   */
	  public SmsChannelModel findChannelById(Integer id){
		  SmsChannelModel cond = new SmsChannelModel();
	    cond.setId(id);
	    return smsChannelDao.findEntityById(cond);
	  }

	  /**
	   * 新增通道
	   * @param entity
	   * @return
	   */
	  public SmsChannelModel addChannel(SmsChannelModel entity){
	    return smsChannelDao.addEntity(entity);
	  }

	  /**
	   * 更新通道
	   * @param smsChannelModel
	   * @return
	   */
	  public Boolean editChannel(SmsChannelModel smsChannelModel){
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("id",smsChannelModel.getId());
	    return smsChannelDao.updateEntity(cond, smsChannelModel);
	  }

	  /**
	   * 更新通道
	   * @param request
	   * @return
	   */
	  public Boolean editChannel(ChannelEditRequest request){
		  SmsChannelModel smsChannelModel = new SmsChannelModel();
		 smsChannelModel.setName(request.getName());
		 smsChannelModel.setStatus(request.getStatus());
		 smsChannelModel.setPort(request.getPriority());
		 smsChannelModel.setUserName(request.getUserName());
		 smsChannelModel.setSecretKey(request.getSecretKey());
		 smsChannelModel.setChannelURL(request.getChannelURL());
		 smsChannelModel.setPort(request.getPort());
		  smsChannelModel.setId(request.getId());
	    return this.editChannel(smsChannelModel);
	  }

	  /**
	   * 查询所有的通道
	   * @return
	   */
	  public Page<SmsChannelModel> queryAllChannel(){
	    Map<String,Object> cond = new HashMap<>();
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("merchantId","");
	    output.put("name","");
	    output.put("status","");
	    output.put("priority","");
	    output.put("userName","");
	    output.put("secretKey","");
	    output.put("channelURL","");
	    output.put("port","");
	    output.put("timeOut","");
	    output.put("controlMaxCount","");
	    output.put("balance","");
	    output.put("createTime", "");
	    return smsChannelDao.queryListByCondWithOutPage(cond, output);
	  }

	 
	  /**
	   * 根据栏目级联查询通道
	   * @param catId
	   * @return
	   */
	  public Page<SmsChannelModel> cascadeQueryChannelByCatId(Integer merchantId){	
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("merchantId", merchantId);
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("merchantId","");
	    output.put("name","");
	    output.put("status","");
	    output.put("priority","");
	    output.put("userName","");
	    output.put("secretKey","");
	    output.put("channelURL","");
	    output.put("port","");
	    output.put("timeOut","");
	    output.put("controlMaxCount","");
	    output.put("balance","");
	    output.put("createTime", "");
	    return smsChannelDao.cascadeQueryProduct(cond, output);
	  }
	  /**
	   * 根据status=0查询关闭通道
	   * @param status
	   * @return
	   */
	  public Page<SmsChannelModel> cascadeQueryChannelByStatus(Integer status){	
		    Map<String,Object> cond = new HashMap<>();
		    cond.put("status", status);
		    Map<String,Object> output = new HashMap<>();
		    output.put("id","1");
		    output.put("merchantId","1");
		    output.put("name","1");
		    output.put("status","1");
		    output.put("priority","1");
		    output.put("userName","1");
		    output.put("secretKey","1");
		    output.put("channelURL","1");
		    output.put("port","1");
		    output.put("timeOut","1");
		    output.put("controlMaxCount","1");
		    output.put("balance","1");
		    output.put("createTime", "1");
		    return smsChannelDao.cascadeQueryProduct(cond, output);
		  }

	  /**
	   * 删除通道
	   * @param id
	   * @return
	   */
	  public Boolean deleteChannel(Integer id){
	    Map<String,Object> cond = new HashMap<>();
	    cond.put("id",id);
	    return smsChannelDao.deleteEntity(cond);
	  }


	 /**
	   * 新增通道
	   * @param request
	   * @return
	   */
	  public ResourceCourseCreateResponse addChannel(ChannelCreateRequest request){
		  SmsChannelModel channelModel = new SmsChannelModel();
		  channelModel.setId(request.getId());
		  channelModel.setMerchantId(request.getMerchantId());
		  channelModel.setName(request.getName());
		  channelModel.setUserName(request.getUserName());
		  channelModel.setSecretKey(request.getSecretKey());
		  channelModel.setChannelURL(request.getChannelURL());
		  channelModel.setPort(request.getPort());
		  channelModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
	    SmsChannelModel resultModel = this.addChannel(channelModel);

	    ResourceCourseCreateResponse response = new ResourceCourseCreateResponse(ResultType.SUCCESS);
	    return response;
	  }
	  /**
	   * 获取所有栏目Id,map
	   * @return
	   */
	 
	  public Map<String,Object> queryAllChannelMap() {
	    List<SmsChannelModel> channelModelList = this.queryChannelList();
	    //Map<Integer,Object> resultMap = new HashMap<>();
	    Map<String,Object> resultMap=DynamicLocalCache.getLocalCacheMap();
	    if(resultMap !=null){
	    	return resultMap;
	    }else{
	    	for (SmsChannelModel channelModel:channelModelList){
	  	      resultMap.put(String.valueOf(channelModel.getId()),channelModel);
	  	    }
	  	    return resultMap;
	    }
	    
	  }
	  
	  /**
	   * 获取所有栏目
	   * @return
	   */
	  public List<SmsChannelModel> queryChannelList() {
	    Map<String, Object> cond = new HashMap<String, Object>();
	    Map<String,Object> output = new HashMap<>();
	    output.put("id","");
	    output.put("merchantId","");
	    output.put("name","");
	    output.put("status","");
	    output.put("priority","");
	    output.put("userName","");
	    output.put("secretKey","");
	    output.put("channelURL","");
	    output.put("port","");
	    output.put("timeOut","");
	    output.put("controlMaxCount","");
	    output.put("balance","");
	    output.put("createTime", "");
	    Page<SmsChannelModel> channelList = smsChannelDao.queryListByCondWithOutPage(cond, output);
	    return channelList.getResult();
	  }
	
	  /**
	   * 下拉框目录列表
	   * @return
	   */
	  public List<SmsChannelModel> channelSelect(String selectedId){
	    List<SmsChannelModel> channelList = this.queryChannelList();
	    return channelList;
	  }

}
