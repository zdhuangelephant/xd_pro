package com.xiaodou.ms.service.scoreRule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.dao.scoreRule.ScoreRuleDao;
import com.xiaodou.ms.enums.ScoreRuleType;
import com.xiaodou.ms.enums.DefaultScoreRuleType;
import com.xiaodou.ms.model.score.RuleInfo;
import com.xiaodou.ms.model.score.ScoreRuleModel;
import com.xiaodou.ms.vo.mq.AddScoreRuleEvent;
import com.xiaodou.ms.vo.mq.AddScoreRuleEvent.TransferScoreRuleData;
import com.xiaodou.ms.vo.mq.ModifyScoreRuleEvent;
import com.xiaodou.ms.web.request.product.RegionCreateRequest;
import com.xiaodou.ms.web.request.product.RegionEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @name ScoreRuleService CopyRright (c) 2018 by
 *       <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月16日
 * @description TODO
 * @version 1.0
 */
@Service("scoreRuleService")
public class ScoreRuleService {


  @Resource
  ScoreRuleDao scoreRuleDao;

  /**
   * 新增目录
   * 
   * @param entity
   * @return
   */
  public ScoreRuleModel addRule(ScoreRuleModel entity) {
    ScoreRuleModel scoreRuleModel= scoreRuleDao.addEntity(entity);
    return scoreRuleModel;
  }
  
  /**
   * 新增目录
   * 
   * @param entity
   * @return
   */
  public ScoreRuleModel addDefaultRule(Integer scope,String ruleName,String ruleDesc) {
    ScoreRuleModel entity= new ScoreRuleModel();
    ArrayList<RuleInfo> scoreRuleTypeLists = Lists.newArrayList();
    for (ScoreRuleType ele : ScoreRuleType.values()) {
      RuleInfo info = new RuleInfo();
      info.setAbtractInfo(ele.getAbtractInfo());
      info.setCode(ele.getCode());
      info.setMoreInfo(ele.getMoreInfo());
      info.setOrder(ele.getOrder());
      info.setWeight(ele.getWeight());
      scoreRuleTypeLists.add(info);
    }
    entity.setRuleDetail(FastJsonUtil.toJson(scoreRuleTypeLists));
    entity.setScope(scope);
    entity.setRuleName(ruleName);
    entity.setRuleDesc(ruleDesc);
    entity.setId(UUID.randomUUID().toString());
    entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
    ScoreRuleModel scoreRuleModel= scoreRuleDao.addEntity(entity);
 /*   AddScoreRuleEvent event = new AddScoreRuleEvent();
    event.setDataModel(new TransferScoreRuleData(entity));
    event.send();*/
    return entity;
  }
  
  /**
   * 新增目录
   * 
   * @param entity
   * @return
   */
  public ScoreRuleModel addRule(Integer scope,String ruleName,String ruleDesc) {
    ScoreRuleModel entity= new ScoreRuleModel();
    ArrayList<RuleInfo> scoreRuleTypeLists = Lists.newArrayList();
    for (DefaultScoreRuleType ele : DefaultScoreRuleType.values()) {
        RuleInfo info = new RuleInfo();
        info.setAbtractInfo(ele.getAbtractInfo());
        info.setCode(ele.getCode());
        info.setMoreInfo(ele.getMoreInfo());
        info.setOrder(ele.getOrder());
        info.setWeight(ele.getWeight());
        scoreRuleTypeLists.add(info);
      }
    entity.setRuleDetail(FastJsonUtil.toJson(scoreRuleTypeLists));
    entity.setScope(scope);
    entity.setRuleName(ruleName);
    entity.setRuleDesc(ruleDesc);
    entity.setId(UUID.randomUUID().toString());
    entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
    ScoreRuleModel scoreRuleModel= scoreRuleDao.addEntity(entity);
 /*   AddScoreRuleEvent event = new AddScoreRuleEvent();
    event.setDataModel(new TransferScoreRuleData(entity));
    event.send();*/
    return entity;
  }

  /**
   * 新增目录
   * 
   * @param request
   * @return
   */
  public BaseResponse addRegion(RegionCreateRequest request) {
    ScoreRuleModel scoreRuleModel = new ScoreRuleModel();
    scoreRuleModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    //TODO
    
    this.addRule(scoreRuleModel);
    return new BaseResponse(ResultType.SUCCESS);
  }

  /**
   * 删除目录
   * 
   * @param id
   * @return
   */
  public Boolean deleteRule(Integer id) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    
    // 发送删除异步消息到report
    
    return scoreRuleDao.deleteEntity(cond);
  }

  /**
   * 更新目录
   * 
   * @param entity
   * @return
   */
  public Boolean editRule(ScoreRuleModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    scoreRuleDao.updateEntity(cond, entity);
/*    ModifyScoreRuleEvent event = new ModifyScoreRuleEvent();
    event.setDataModel(new TransferScoreRuleData(entity));
    event.send();*/
    
    return true;
  }
  
  /**
   * 目录编辑
   * 
   * @param request
   * @return
   */
  public BaseResponse editRule(RegionEditRequest request) {
    ScoreRuleModel ruleModel = new ScoreRuleModel();
    //TODO
 

    Boolean aBoolean = this.editRule(ruleModel);
    BaseResponse response = null;
    if (aBoolean) {
      response = new BaseResponse(ResultType.SUCCESS);
    } else {
      response = new BaseResponse(ResultType.SYS_FAIL);
    }
    return response;
  }

  /**
   * 根据主键查询
   * 
   * @param catId
   * @return
   */
  public ScoreRuleModel findRuleById(String catId) {
    ScoreRuleModel entity = new ScoreRuleModel();
    entity.setId(catId);
    return scoreRuleDao.findEntityById(entity);
  }
  
  /**
   * @return
   */
  public Page<ScoreRuleModel> findRuleList(Integer page){
    
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("module", "");
    output.put("moduleName", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("listOrder", "");
    output.put("scope", "");
    Page<ScoreRuleModel> ruleList = scoreRuleDao.queryListWithSort(cond, output, null);
    return ruleList;
  }

}
