package com.xiaodou.autotest.web.service.operation;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.web.dao.operation.template.AfterSetDetialDao;
import com.xiaodou.autotest.web.dao.operation.template.HeaderDetailDao;
import com.xiaodou.autotest.web.dao.operation.template.ParamDetailDao;
import com.xiaodou.autotest.web.dao.operation.template.PreCondDetialDao;
import com.xiaodou.autotest.web.dao.operation.template.PreSetDetailDao;
import com.xiaodou.autotest.web.dao.operation.template.TemplateGroupDao;
import com.xiaodou.autotest.web.dao.operation.template.TestCaseDetailDao;
import com.xiaodou.autotest.web.enums.GroupTmp;
import com.xiaodou.autotest.web.model.template.AfterSetDetail;
import com.xiaodou.autotest.web.model.template.HeaderDetail;
import com.xiaodou.autotest.web.model.template.ParamDetail;
import com.xiaodou.autotest.web.model.template.PreCondDetail;
import com.xiaodou.autotest.web.model.template.PreSetDetail;
import com.xiaodou.autotest.web.model.template.TemplateGroup;
import com.xiaodou.autotest.web.model.template.TestCaseDetail;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

@Service("templateService")
public class TemplateService {
  @Resource
  TemplateGroupDao templateGroupDao;
  @Resource
  HeaderDetailDao headerDetailDao;
  @Resource
  ParamDetailDao paramDetailDao;
  @Resource
  AfterSetDetialDao afterSetDetialDao;
  @Resource
  PreCondDetialDao preCondDetialDao;
  @Resource
  PreSetDetailDao preSetDetailDao;
  @Resource
  TestCaseDetailDao testCaseDetailDao;



  public List<TemplateGroup> findAllOrOne(Long id, Short typeId) {
    IQueryParam param = new QueryParam();
    if (null != id) {
      param.addInput("id", id);
    }
    if (typeId != null && typeId != GroupTmp.UNKNOWN.getCode()) {
      param.addInput("typeId", typeId);
    }
    param.addOutputs(CommUtil.getAllField(TemplateGroup.class));
    Page<TemplateGroup> page = templateGroupDao.findEntityListByCond(param, null);
    return (null != page && page.getResult().size() != 0) ? page.getResult() : null;
  }

  public TemplateGroup addEntity(TemplateGroup group) {
    return templateGroupDao.addEntity(group);
  }

  public void addEntityHeaderList(List<HeaderDetail> headerArgs) {
    for (HeaderDetail headerDetail : headerArgs) {
      headerDetailDao.addEntity(headerDetail);
    }
  }

  public void addEntityAfterSetList(List<AfterSetDetail> aftersetArgs) {
    for (AfterSetDetail afterSetDetail : aftersetArgs) {
      afterSetDetialDao.addEntity(afterSetDetail);
    }
  }

  public void addEntityTestCaseList(List<TestCaseDetail> testcaseArgs) {
    for (TestCaseDetail testCaseDetail : testcaseArgs) {
      testCaseDetailDao.addEntity(testCaseDetail);
    }
  }

  public void addEntityPreSetList(List<PreSetDetail> presetArgs) {
    for (PreSetDetail preSetDetail : presetArgs) {
      preSetDetailDao.addEntity(preSetDetail);
    }
  }

  public void addEntityPreCondList(List<PreCondDetail> precondArgs) {
    for (PreCondDetail preCondDetail : precondArgs) {
      preCondDetialDao.addEntity(preCondDetail);
    }
  }

  public void addEntityParamList(List<ParamDetail> paramArgs) {
    for (ParamDetail paramDetail : paramArgs) {
      paramDetailDao.addEntity(paramDetail);
    }

  }

  public List<TemplateGroup> checkGroupExists(String headerName, Short typeId) {
   IQueryParam param = new QueryParam();
   param.addInput("groupName", headerName);
   param.addInput("typeId", typeId);
   param.addOutput("id", 1);
   Page<TemplateGroup> page = templateGroupDao.findEntityListByCond(param, null);
   return (null == page || page.getResult().size() == 0) ? null : page.getResult();
  }

  public void delPreCondTemplateByGroupId(Long groupId) {
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("groupId", groupId);
    preCondDetialDao.deleteEntityByCond(input);
  }

  public void delParamTemplateByGroupId(Long groupId) {
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("groupId", groupId);
    paramDetailDao.deleteEntityByCond(input);
    
  }

  public void delPreSetTemplateByGroupId(Long groupId) {
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("groupId", groupId);
    preSetDetailDao.deleteEntityByCond(input);
  }

  public void delTestCaseTemplateByGroupId(Long groupId) {
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("groupId", groupId);
    testCaseDetailDao.deleteEntityByCond(input);
  }

  public void delAfterSetTemplateByGroupId(Long groupId) {
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("groupId", groupId);
    afterSetDetialDao.deleteEntityByCond(input);
    
  }

  public void delHeaderTemplateByGroupId(Long groupId) {
    HashMap<String, Object> input = Maps.newHashMap();
    input.put("groupId", groupId);
    headerDetailDao.deleteEntityByCond(input);
    
  }

  
  public String saveGroup(Long groupId, Short typeId, String headerName, String headerParams) {
    List<TemplateGroup> isExists = checkGroupExists(headerName, typeId);
    if (groupId == null && isExists != null && isExists.size() != 0) {
      ResultInfo info = new ResultInfo();
      info.setRetcode("-1");
      info.setRetdesc("操作失败、该类型下的模版命名出现重复");
      return info.toString0();
    }
    if(groupId == null) {
      TemplateGroup group = new TemplateGroup();
      group.setCreateTime(new Timestamp(System.currentTimeMillis()));
      group.setGroupName(headerName);
      group.setTypeId(typeId);
      TemplateGroup entity = templateGroupDao.addEntity(group);
      groupId = entity.getId();
    }

    if (null != typeId && GroupTmp.HEADER.getCode() == typeId) {
      List<HeaderDetail> headerArgs =
          FastJsonUtil.fromJsons(headerParams, new TypeReference<List<HeaderDetail>>() {});
      if (!CollectionUtils.isEmpty(headerArgs)) 
      {
        for (HeaderDetail details : headerArgs) {
          details.setGroupId(groupId);
        }
        addEntityHeaderList(headerArgs);
      }
    }
    if (null != typeId && GroupTmp.PARAM.getCode() == typeId) {
      List<ParamDetail> paramArgs =
          FastJsonUtil.fromJsons(headerParams, new TypeReference<List<ParamDetail>>() {});
      if (!CollectionUtils.isEmpty(paramArgs)) 
      {
        for (ParamDetail details : paramArgs) {
          details.setGroupId(groupId);
        }
        addEntityParamList(paramArgs);
      }
    }
    if (null != typeId && GroupTmp.PRECOND.getCode() == typeId) {
      List<PreCondDetail> precondArgs =
          FastJsonUtil.fromJsons(headerParams, new TypeReference<List<PreCondDetail>>() {});
      if (!CollectionUtils.isEmpty(precondArgs)) 
      {
        for (PreCondDetail details : precondArgs) {
          details.setGroupId(groupId);
        }
        addEntityPreCondList(precondArgs);
      }
    }
    if (null != typeId && GroupTmp.PRESET.getCode() == typeId) {
      List<PreSetDetail> presetArgs =
          FastJsonUtil.fromJsons(headerParams, new TypeReference<List<PreSetDetail>>() {});
      if (!CollectionUtils.isEmpty(presetArgs)) 
      {
        for (PreSetDetail details : presetArgs) {
          details.setGroupId(groupId);
        }
        addEntityPreSetList(presetArgs);
      }
    }
    if (null != typeId && GroupTmp.TESTCASE.getCode() == typeId) {
      List<TestCaseDetail> testcaseArgs =
          FastJsonUtil.fromJsons(headerParams, new TypeReference<List<TestCaseDetail>>() {});
      if (!CollectionUtils.isEmpty(testcaseArgs)) 
      {
        for (TestCaseDetail details : testcaseArgs) {
          details.setGroupId(groupId);
        }
        addEntityTestCaseList(testcaseArgs);
      }
    }
    if (null != typeId && GroupTmp.AFTERSET.getCode() == typeId) {
      List<AfterSetDetail> aftersetArgs =
          FastJsonUtil.fromJsons(headerParams, new TypeReference<List<AfterSetDetail>>() {});
      if (!CollectionUtils.isEmpty(aftersetArgs)) 
      {
        for (AfterSetDetail details : aftersetArgs) {
          details.setGroupId(groupId);
        }
        addEntityAfterSetList(aftersetArgs);
      }
    }
    
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

  public void delTemplateGroupById(Long id) {
    TemplateGroup group = new TemplateGroup();
    group.setId(id);
    templateGroupDao.deleteEntityById(group);
  }

  public List<PreCondDetail> findPreCondsByGroupId(Long groupId) {
    IQueryParam param = new QueryParam();
    param.addInput("groupId", groupId);
    param.addOutputs(CommUtil.getAllField(PreCondDetail.class));
    Page<PreCondDetail> page = preCondDetialDao.findEntityListByCond(param, null);
    return (null == page || page.getResult().size() == 0) ? null : page.getResult();
  }

  public List<ParamDetail> findParamByGroupId(Long groupId) {
    IQueryParam param = new QueryParam();
    param.addInput("groupId", groupId);
    param.addOutputs(CommUtil.getAllField(ParamDetail.class));
    Page<ParamDetail> page = paramDetailDao.findEntityListByCond(param, null);
    return (null == page || page.getResult().size() == 0) ? null : page.getResult();
  }

  public List<PreSetDetail> findPreSetByGroupId(Long groupId) {
    IQueryParam param = new QueryParam();
    param.addInput("groupId", groupId);
    param.addOutputs(CommUtil.getAllField(PreSetDetail.class));
    Page<PreSetDetail> page = preSetDetailDao.findEntityListByCond(param, null);
    return (null == page || page.getResult().size() == 0) ? null : page.getResult();
  }

  public List<TestCaseDetail> findTestCaseByGroupId(Long groupId) {
    IQueryParam param = new QueryParam();
    param.addInput("groupId", groupId);
    param.addOutputs(CommUtil.getAllField(TestCaseDetail.class));
    Page<TestCaseDetail> page = testCaseDetailDao.findEntityListByCond(param, null);
    return (null == page || page.getResult().size() == 0) ? null : page.getResult();
  }

  public List<AfterSetDetail> findAfterSetByGroupId(Long groupId) {
    IQueryParam param = new QueryParam();
    param.addInput("groupId", groupId);
    param.addOutputs(CommUtil.getAllField(AfterSetDetail.class));
    Page<AfterSetDetail> page = afterSetDetialDao.findEntityListByCond(param, null);
    return (null == page || page.getResult().size() == 0) ? null : page.getResult();
  }

  public List<HeaderDetail> findHeaderByGroupId(Long groupId) {
    IQueryParam param = new QueryParam();
    param.addInput("groupId", groupId);
    param.addOutputs(CommUtil.getAllField(HeaderDetail.class));
    Page<HeaderDetail> page = headerDetailDao.findEntityListByCond(param, null);
    return (null == page || page.getResult().size() == 0) ? null : page.getResult();
  }
  
  
  /**
   *    @Resource
    HeaderDetailDao headerDetailDao;

    public HeaderDetail save(HeaderDetail detail) {
        return headerDetailDao.addEntity(detail);
    }

    public List<HeaderDetail> findAllOrOne(Integer id) {
        IQueryParam param = new QueryParam();
        if(id == null) {
            param.addOutputs(CommUtil.getGeneralField(HeaderDetail.class));
            Page<HeaderDetail> page = headerDetailDao.findEntityListByCond(param, null);
            if(page == null || page.getResult().size() == 0) return null;
            return page.getResult();
        }else {
            HeaderDetail entity = new HeaderDetail();
            entity.setId(id);
            List<HeaderDetail> res = Lists.newArrayList();
            res.add(headerDetailDao.findEntityById(entity));
            return res;
        }
    }

    public List<HeaderDetail> findDetailByGroup(Integer id) {
        if(null == id)
            throw new NullPointerException("目标ID为空， 参数有误");
        IQueryParam param = new QueryParam();
        param.addInput("headerGroupId", id);
        param.addOutputs(CommUtil.getAllField(HeaderDetail.class));
        Page<HeaderDetail> details = headerDetailDao.findEntityListByCond(param, null);
        return null != details ? details.getResult() : null;
    }

    public Boolean deleteByGroupId(Integer id) {
        Map<String,Object> conds = Maps.newHashMap();
        conds.put("headerGroupId", id);
        return headerDetailDao.deleteEntityByCond(conds);
    }
   */


}
