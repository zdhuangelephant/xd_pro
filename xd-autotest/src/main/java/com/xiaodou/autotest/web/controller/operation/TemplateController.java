package com.xiaodou.autotest.web.controller.operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.web.constants.Cons;
import com.xiaodou.autotest.web.controller.BaseController;
import com.xiaodou.autotest.web.enums.GroupTmp;
import com.xiaodou.autotest.web.model.template.AfterSetDetail;
import com.xiaodou.autotest.web.model.template.HeaderDetail;
import com.xiaodou.autotest.web.model.template.ParamDetail;
import com.xiaodou.autotest.web.model.template.PreCondDetail;
import com.xiaodou.autotest.web.model.template.PreSetDetail;
import com.xiaodou.autotest.web.model.template.TemplateGroup;
import com.xiaodou.autotest.web.model.template.TestCaseDetail;
import com.xiaodou.autotest.web.service.operation.TemplateService;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * 模版Dispatcher
 * 
 * @name TemplateController CopyRright (c) 2018 by
 *       <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 *
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2018年1月31日
 * @description TODO
 * @version 1.0
 */
@Controller("TemplateController")
@RequestMapping("/template")
public class TemplateController extends BaseController {

  @Resource
  TemplateService templateService;

  /**
   * 查看某一个Header
   */
 /* @RequestMapping("/header_query")
  @ResponseBody
  public String queryOne(Integer id) {
    if (id == null) {
      List<HeaderGroup> group = headerGroupService.findAllOrOne(id);
      if (group == null) {
        return null;
      }
      return FastJsonUtil.toJson(group);
    } else {
      List<HeaderGroup> groupList = headerGroupService.findAllOrOne(id);
      if (groupList == null || groupList.size() == 0) {
        return StringUtils.EMPTY;
      }
      return FastJsonUtil.toJson(groupList.get(0));
    }
  }

  *//**
   * 新增或者修改
   * 
   * @author zdh
   * @return
   *//*
  @RequestMapping("/header_saveOrUpdate")
  @ResponseBody
  public String saveOrUpdate(Integer groupId, String headerParams, String headerName) {

    Map<String, Object> msgMaps = new HashMap<>();
    try {
      TemplateGroup group = null;
      if (groupId == null) {
        HeaderGroupReq groupReq = new HeaderGroupReq();
        groupReq.setHeaderGroupName(headerName);
        group = headerGroupService.insertHeaderGroup(groupReq);
      }

      List<Map<String, String>> paramMaps =
          FastJsonUtil.fromJsons(headerParams, new TypeReference<List<Map<String, String>>>() {});
      // 修改
      if (groupId != null) {
        // 删除原有的detail
        headerDetailService.deleteByGroupId(groupId);
      }

      if (!CollectionUtils.isEmpty(paramMaps)) {
        for (Map<String, String> map : paramMaps) {
          if (CollectionUtils.isEmpty(map)) {
            continue;
          }
          HeaderDetail detail = new HeaderDetail();
          detail.setCreateTime(new Timestamp(System.currentTimeMillis()));
          if (groupId == null) {
            detail.setGroupId(group);
          } else {
            detail.setGroupId(groupId);
          }
          detail.setId(getEightNums());
          detail.setKey(map.get("key"));
          detail.setValue(map.get("value"));
          headerDetailService.save(detail);
        }
      }
      msgMaps.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      msgMaps.put(Cons.MSG, Cons.SUCCESS);
      return FastJsonUtil.toJson(msgMaps);
    } catch (RuntimeException e) {
      if (e.getMessage().equals("0")) {
        msgMaps.put(Cons.STATUS, Cons.STATUS_FAILURE);
        msgMaps.put(Cons.MSG, "重复Header模版的命名");
      } else {
        msgMaps.put(Cons.STATUS, Cons.STATUS_FAILURE);
        msgMaps.put(Cons.MSG, e.toString());
      }
    } catch (Exception e) {
      msgMaps.put(Cons.STATUS, Cons.STATUS_FAILURE);
      msgMaps.put(Cons.MSG, e.toString());
      LoggerUtil.error(Cons.FAILURE, e);
    }
    return FastJsonUtil.toJson(msgMaps);
  }



  *//**
   * 删除
   * 
   * @author zdh
   * @return
   *//*
  @RequestMapping("/header_delete")
  @ResponseBody
  public String delete(HeaderGroupReq req) {
    Map<String, Object> map = new HashMap<>();
    try {
      headerGroupService.removeHeaderGroup(req.getId());
      map.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      map.put(Cons.MSG, Cons.SUCCESS);
      return FastJsonUtil.toJson(map);
    } catch (Exception e) {
      map.put(Cons.STATUS, Cons.STATUS_FAILURE);
      map.put(Cons.MSG, e.toString());
      LoggerUtil.error("删除失败", e);
    }
    return FastJsonUtil.toJson(map);
  }

  *//**
   * 获取getHeaderGroup;
   *//*
  @SuppressWarnings("finally")
  @RequestMapping("/header_getGroup")
  @ResponseBody
  public String getHeaderGroup(Integer id) {
    Map<String, Object> map = Maps.newHashMap();
    try {
      List<HeaderGroup> oneOrAll = headerGroupService.findAllOrOne(id);
      map.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      map.put(Cons.MSG, oneOrAll);
      map.put("asdf", FastJsonUtil.toJson(oneOrAll));
    } catch (Exception e) {
      map.put(Cons.STATUS, Cons.STATUS_FAILURE);
      map.put(Cons.MSG, e.toString());
      LoggerUtil.error("获取HeaderGroup失败", e);
    } finally {
      return FastJsonUtil.toJson(map);
    }
  }

  *//**
   * 获取getHeaderGroup下的所有headerDetail;
   *//*
  @RequestMapping("/header_getDetail")
  @ResponseBody
  public String getDetailOfHeaderGroup(Integer id) {
    Map<String, Object> map = Maps.newHashMap();
    try {
      List<HeaderDetail> details = headerDetailService.findDetailByGroup(id);
      map.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      map.put(Cons.MSG, details);
      map.put("groupInfo", headerGroupService.findOneById(id));
    } catch (Exception e) {
      map.put(Cons.STATUS, Cons.STATUS_FAILURE);
      map.put(Cons.MSG, e.toString());
      LoggerUtil.error("获取HeaderDetail失败", e);
    }
    return FastJsonUtil.toJson(map);
  }*/


  /***************************** Param-Template ****************************/
  /**
   * 获取参数组;
   */
  @RequestMapping("/getGroup")
  @ResponseBody
  public String getParamGroup(Long id, Short typeId) {
    Map<String, Object> map = Maps.newHashMap();
    try {
      List<TemplateGroup> oneOrAll = templateService.findAllOrOne(id, typeId);
      map.put(Cons.STATUS, Cons.STATUS_SUCCESS);
      map.put(Cons.MSG, oneOrAll);
      // map.put("asdf", FastJsonUtil.toJson(oneOrAll));
      return FastJsonUtil.toJson(map);
    } catch (Exception e) {
      map.put(Cons.STATUS, Cons.STATUS_FAILURE);
      map.put(Cons.MSG, e.toString());
      LoggerUtil.error("获取ParamGroup失败", e);
      return FastJsonUtil.toJson(map);
    } 
  }



  /**
   * 新增
   * 
   * @author zdh
   * @return
   */
  @RequestMapping("/saveOrUpdate")
  @ResponseBody
  public String saveOrUpdate(Long id, Short typeId, String groupParams, String groupName) {
    // 修改
    if (id != null && null != typeId && GroupTmp.PRECOND.getCode() == typeId) {
      templateService.delPreCondTemplateByGroupId(id);
    }
    if (id != null && null != typeId && GroupTmp.PARAM.getCode() == typeId) {
      templateService.delParamTemplateByGroupId(id);
    }
    if (id != null && null != typeId && GroupTmp.PRESET.getCode() == typeId) {
      templateService.delPreSetTemplateByGroupId(id);
    }
    if (id != null && null != typeId && GroupTmp.TESTCASE.getCode() == typeId) {
      templateService.delTestCaseTemplateByGroupId(id);
    }
    if (id != null && null != typeId && GroupTmp.AFTERSET.getCode() == typeId) {
      templateService.delAfterSetTemplateByGroupId(id);
    }
    if (id != null && null != typeId && GroupTmp.HEADER.getCode() == typeId) {
      templateService.delHeaderTemplateByGroupId(id);
    }
    /*if (id != null) {
      templateService.delTemplateGroupById(id);
      return new ResultInfo(ResultType.SUCCESS).toString0();
    }*/

    // 新增
    return templateService.saveGroup(id, typeId, groupName, groupParams);
  }
  
  
  
  
  /**
   * 获取Detail
   * 
   * @author zdh
   * @return
   */
  @RequestMapping("/getDetail")
  @ResponseBody
  public String saveOrUpdate(Long groupId, Short typeId) {
    List<TemplateGroup> allGroups = templateService.findAllOrOne(groupId, typeId);
    HashMap<String, Object> info = Maps.newHashMap();
    info.put("groupInfo", allGroups.get(0));
    // 修改
    if (groupId != null && null != typeId && GroupTmp.PRECOND.getCode() == typeId) {
      List<PreCondDetail> preConds = templateService.findPreCondsByGroupId(groupId);
      info.put("detail", preConds);
      return FastJsonUtil.toJson(info);
    }
    if (groupId != null && null != typeId && GroupTmp.PARAM.getCode() == typeId) {
      List<ParamDetail> params = templateService.findParamByGroupId(groupId);
      info.put("detail", params);
      return FastJsonUtil.toJson(info);
    }
    if (groupId != null && null != typeId && GroupTmp.PRESET.getCode() == typeId) {
      List<PreSetDetail> preSets = templateService.findPreSetByGroupId(groupId);
      info.put("detail", preSets);
      return FastJsonUtil.toJson(info);
    }
    if (groupId != null && null != typeId && GroupTmp.TESTCASE.getCode() == typeId) {
      List<TestCaseDetail> testCases = templateService.findTestCaseByGroupId(groupId);
      info.put("detail", testCases);
      return FastJsonUtil.toJson(info);
    }
    if (groupId != null && null != typeId && GroupTmp.AFTERSET.getCode() == typeId) {
      List<AfterSetDetail> afterSets = templateService.findAfterSetByGroupId(groupId);
      info.put("detail", afterSets);
      return FastJsonUtil.toJson(info);
    }
    if (groupId != null && null != typeId && GroupTmp.HEADER.getCode() == typeId) {
      List<HeaderDetail> headers = templateService.findHeaderByGroupId(groupId);
      info.put("detail", headers);
      return FastJsonUtil.toJson(info);
    }
    return null;
  }
  
  
  
  
  
  
}


