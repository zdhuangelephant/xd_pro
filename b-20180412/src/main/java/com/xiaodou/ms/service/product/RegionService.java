package com.xiaodou.ms.service.product;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.dao.product.RegionDao;
import com.xiaodou.ms.dao.scoreRule.ScoreRuleDao;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.service.mission.MissionService;
import com.xiaodou.ms.vo.mq.AddScoreRuleEvent.TransferScoreRuleData;
import com.xiaodou.ms.vo.mq.DeleteScoreRuleEvent;
import com.xiaodou.ms.web.request.product.RegionCreateRequest;
import com.xiaodou.ms.web.request.product.RegionEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name ProductRegionService CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月12日
 * @description TODO
 * @version 1.0
 */
@Service("regionService")
public class RegionService {

  /**
   * 资源分类DAO
   */
  @Resource
  RegionDao regionDao;

  @Resource
  ScoreRuleDao scoreRuleDao;

  @Resource
  MissionService missionService;


  /**
   * 新增目录
   * 
   * @param entity
   * @return
   */
  public RegionModel addRegion(RegionModel entity) {
    RegionModel RegionModel = regionDao.addEntity(entity);
    return RegionModel;
  }

  /**
   * 新增目录
   * 
   * @param request
   * @return
   */
  public RegionModel addRegion(RegionCreateRequest request) {
    RegionModel regionModel = new RegionModel();
    regionModel.setRuleId(request.getRuleId());
    regionModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    regionModel.setDetail(request.getDetail());
    regionModel.setModuleName(request.getModuleName());
    regionModel.setModule(request.getModule());
    regionModel.setListOrder(request.getListOrder());
    regionModel.setFirstChoice(request.getFirstChoice());
    RegionModel addRegion = this.addRegion(regionModel);

    missionService.queryMissionByModule(regionModel.getModule());// 查到所有的mission
    return addRegion;
  }

  /**
   * 删除目录
   * 
   * @param id
   * @return
   */
  public Boolean deleteRegion(Long id) {
    RegionModel entity = new RegionModel();
    entity.setId(id);
    RegionModel dbRegion = regionDao.findEntityById(entity);
    HashMap<String, Object> cond = Maps.newHashMap();
    cond.put("id", dbRegion.getRuleId());
    scoreRuleDao.deleteEntityByCond(cond);

    TransferScoreRuleData transfer =
        new TransferScoreRuleData(String.valueOf(dbRegion.getRuleId()), dbRegion.getModule());
    DeleteScoreRuleEvent event = new DeleteScoreRuleEvent();
    event.setModule(dbRegion.getModule());
    event.setDataModel(transfer);
    event.send();
    return regionDao.deleteEntityById(entity);
  }

  /**
   * 更新目录
   * 
   * @param entity
   * @return
   */
  public Boolean editRegion(RegionModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    regionDao.updateEntity(cond, entity);
    return true;
  }

  /**
   * 目录编辑
   * 
   * @param request
   * @return
   */
  public BaseResponse editRegion(RegionEditRequest request) {
    RegionModel regionModel = new RegionModel();

    regionModel.setDetail(request.getDetail());
    regionModel.setModuleName(request.getModuleName());
    regionModel.setModule(request.getModule());
    regionModel.setId(request.getId());
    regionModel.setListOrder(request.getListOrder());
    regionModel.setFirstChoice(request.getFirstChoice());
    regionModel.setShowStatus(request.getShowStatus());

    Boolean aBoolean = this.editRegion(regionModel);
    BaseResponse response = null;
    if (aBoolean) {
      response = new BaseResponse(ResultType.SUCCESS);
    } else {
      response = new BaseResponse(ResultType.SYS_FAIL);
    }
    return response;
  }

  /**
   * 目录编辑
   * 
   * @param request
   * @return
   */
  public void editRegionState(String id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("NId", id);
    RegionModel regionModel = new RegionModel();
    regionModel.setFirstChoice(0);
    regionDao.updateEntityByCond(cond, regionModel);
  }

  /**
   * 根据主键查询
   * 
   * @param catId
   * @return
   */
  public RegionModel findRegionById(Long catId) {
    RegionModel entity = new RegionModel();
    entity.setId(catId);
    return regionDao.findEntityById(entity);
  }

  /**
   * 获取所有栏目
   * 
   * @return
   */
  public List<RegionModel> queryAllRegion() {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("module", "");
    output.put("moduleName", "");
    output.put("ruleId", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("listOrder", "");
    output.put("firstChoice", "");
    output.put("showStatus", "");

    Page<RegionModel> regionList = regionDao.queryListWithSort(cond, output, null);
    return regionList.getResult();
  }

  /**
   * 按照module查找地域
   * 
   * @return
   */
  public List<RegionModel> queryRegionWithModule(String module) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("module", module);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("module", "");
    output.put("moduleName", "");
    output.put("ruleId", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("listOrder", "");
    output.put("firstChoice", "");
    output.put("showStatus", "");
    Page<RegionModel> regionList = regionDao.queryListWithSort(cond, output, null);
    return regionList.getResult();
  }

  /**
   * 根据region获取regionCode
   * 
   * @param regionName
   * @return
   */
  public RegionModel findCodeByRegion(String moduleName) {
    if (StringUtils.isEmpty(moduleName)) return null;
    IQueryParam param = new QueryParam();
    if (StringUtils.isNotEmpty(moduleName)) param.addInput("moduleNameLike", moduleName);
    param.addOutput("module", "");
    param.addOutput("moduleName", "");
    Page<RegionModel> page = regionDao.findEntityListByCond(param, null);
    if (null != page && page.getResult().size() > 0) return page.getResult().get(0);
    return null;
  }

  public RegionModel findModuleNameByModuleCode(String moduleCode) {
    if (StringUtils.isEmpty(moduleCode)) return null;
    IQueryParam param = new QueryParam();
    param.addInput("module", moduleCode);
    Map<String, Object> allField = CommUtil.getAllField(RegionModel.class);
    allField.remove("hasBeginnerCourse");
    allField.remove("courseId");
    param.addOutputs(allField);
    Page<RegionModel> page = regionDao.findEntityListByCond(param, null);
    if (null != page && page.getResult().size() > 0) return page.getResult().get(0);
    return null;
  }


}
