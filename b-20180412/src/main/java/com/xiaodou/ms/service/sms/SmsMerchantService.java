package com.xiaodou.ms.service.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.local.DynamicLocalCache;
import com.xiaodou.ms.dao.sms.SmsMerchantDao;
import com.xiaodou.ms.model.sms.SmsMerchantModel;
import com.xiaodou.ms.web.request.sms.MerchantCreateRequest;
import com.xiaodou.ms.web.request.sms.MerchantEditRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.CategoryCreateResponse;
import com.xiaodou.ms.web.response.course.CategoryEditResponse;
import com.xiaodou.summer.dao.pagination.Page;

@Service("smsMerchantService")
public class SmsMerchantService {

  /**
   * 供应商分类DAO
   */
  @Resource
  SmsMerchantDao smsMerchantDao;

  /**
   * 新增供应商
   * 
   * @param entity
   * @return
   */
  public SmsMerchantModel addMerchant(SmsMerchantModel entity) {
    return smsMerchantDao.addEntity(entity);
  }

  /**
   * 新增供应商
   * 
   * @param request
   * @return
   */
  public CategoryCreateResponse addMerchant(MerchantCreateRequest request) {
    SmsMerchantModel smsMerchantModel = new SmsMerchantModel();
    smsMerchantModel.setId(request.getId());
    smsMerchantModel.setName(request.getName());
    smsMerchantModel.setShortName(request.getShortName());
    smsMerchantModel.setAddress(request.getAddress());
    smsMerchantModel.setTelephone(request.getTelephone());
    smsMerchantModel.setContactPerson(request.getContactPerson());

    SmsMerchantModel resultModel = this.addMerchant(smsMerchantModel);
    CategoryCreateResponse response = new CategoryCreateResponse(ResultType.SUCCESS);
    response.setCatId(resultModel.getId());
    return response;
  }

  /**
   * 删除供应商
   * 
   * @param catId
   * @return
   */
  public Boolean deleteMerchant(Integer catId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", catId);
    return smsMerchantDao.deleteEntity(cond);
  }

  /**
   * 更新供应商
   * 
   * @param entity
   * @return
   */
  public Boolean editMerchant(SmsMerchantModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    return smsMerchantDao.updateEntity(cond, entity);
  }

  /**
   * 供应商编辑
   * 
   * @param request
   * @return
   */
  public CategoryEditResponse editMerchant(MerchantEditRequest request) {
    SmsMerchantModel smsMerchantModel = new SmsMerchantModel();
    smsMerchantModel.setName(request.getName());
    smsMerchantModel.setAddress(request.getAddress());
    smsMerchantModel.setShortName(request.getShortName());
    smsMerchantModel.setTelephone(request.getTelephone());
    smsMerchantModel.setContactPerson(request.getContactPerson());
    smsMerchantModel.setId(request.getId());
    Boolean aBoolean = this.editMerchant(smsMerchantModel);
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
  public SmsMerchantModel findMerchantById(Long id) {
    SmsMerchantModel entity = new SmsMerchantModel();
    entity.setId(id);
    return smsMerchantDao.findEntityById(entity);
  }



  /**
   * 根据id查询
   * 
   * @param id
   * @return
   */
  public Page<SmsMerchantModel> queryMerchantByCatId(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("name", "");
    output.put("shortName", "");
    output.put("address", "");
    output.put("telephone", "");
    output.put("contactPerson", "");
    return smsMerchantDao.queryListByCondWithOutPage(cond, output);
  }


  /**
   * 获取所有栏目
   * 
   * @return
   */
  public List<SmsMerchantModel> queryAllMerchant() {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("name", "");
    output.put("shortName", "");
    output.put("address", "");
    output.put("telephone", "");
    output.put("contactPerson", "");
    Page<SmsMerchantModel> merchantList = smsMerchantDao.queryListByCondWithOutPage(cond, output);
    return merchantList.getResult();
  }

  /**
   * 
   * 根据id获取
   * 
   * @param id
   * @return
   */
  public List<SmsMerchantModel> queryAllMerchantById(Long id) {
    List<SmsMerchantModel> result = new ArrayList<SmsMerchantModel>();
    List<SmsMerchantModel> list = this.queryAllMerchant();
    result.addAll(list);
    for (SmsMerchantModel model : list) {
      result.addAll(this.queryAllMerchantById(model.getId()));
    }
    return result;
  }

  /**
   * 获取所有栏目Id,map
   * 
   * @return
   */

  public Map<String, Object> queryAllMerchantMap() {
    List<SmsMerchantModel> smsMerchantModelList = this.queryAllMerchant();
    // Map<Integer,Object> resultMap = new HashMap<>();
    Map<String, Object> resultMap = DynamicLocalCache.getLocalCacheMap();
    if (resultMap != null) {
      return resultMap;
    } else {
      for (SmsMerchantModel smsMerchantModel : smsMerchantModelList) {
        resultMap.put(String.valueOf(smsMerchantModel.getId()), smsMerchantModel);
      }
      return resultMap;
    }

  }

  /**
   * 下拉框目录列表
   * 
   * @return
   */
  public List<SmsMerchantModel> merchantSelect(String selectedId) {
    List<SmsMerchantModel> merchantList = this.queryAllMerchant();
    return merchantList;
  }
}
