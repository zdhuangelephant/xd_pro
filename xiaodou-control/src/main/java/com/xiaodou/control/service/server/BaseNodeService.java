package com.xiaodou.control.service.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.constant.Constant;
import com.xiaodou.control.enums.CommandEnum;
import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.request.server.NodeRequest;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.vo.ConfigInfoVo;
import com.xiaodou.control.vo.ConfigVo;
import com.xiaodou.control.vo.NodeCommandVo;

@Service("baseNodeService")
public class BaseNodeService {
  @Resource
  MongoDbServiceFacade mongoDbServiceFacadeImpl;

  public BaseNodeService() {}
   
  /**
   * 增加命令
   * 
   * @author zhouhuan
   */

  public boolean addCommand(NodeRequest request, String id) {
	BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(request.getMac());
    String command = baseNodeModel.getNodeCommand();
    List<NodeCommandVo> ncList = new ArrayList<NodeCommandVo>();
    if (!StringUtils.isBlank(command)) {
      ncList = FastJsonUtil.fromJsons(command, new TypeReference<List<NodeCommandVo>>() {});
    }
    NodeCommandVo nc = new NodeCommandVo();
    nc.setId(id);
    nc.setServerId(request.getServerId());
    nc.setCommandName(request.getCommandName());
    nc.setCommandId(request.getCommandId());
    nc.setState("0");
    nc.setVersion(request.getVersion());
    nc.setCommandInfo(request.getCommandInfo());
    ncList.add(nc);
    command = FastJsonUtil.toJson(ncList);
    BaseNodeModel node = new BaseNodeModel();
    node.setNodeCommand(command);
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("mac", request.getMac());
    return mongoDbServiceFacadeImpl.editBaseNode(input, node);
  }



  /**
   * 增加启动配置加载项
   * 
   * @param mac mac地址
   * @param configInfo 配置信息
   */
  public void addStartupConfig(String mac, ConfigInfoVo configInfo) {
	BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(mac);
    String startupConfig = baseNodeModel.getStartupConfig();
    List<ConfigInfoVo> ciList = new ArrayList<ConfigInfoVo>();
    if (!StringUtils.isBlank(startupConfig)) {
      ciList = FastJsonUtil.fromJsons(startupConfig, new TypeReference<List<ConfigInfoVo>>() {});
    }
    ADD_START_CONFIG: {
      for (ConfigInfoVo vo : ciList) {
        if (null == vo) continue;
        if (vo.getCommandId().equals(configInfo.getCommandId())
            && vo.getCommandInfo().equals(configInfo.getCommandInfo())) break ADD_START_CONFIG;
      }
      ciList.add(configInfo);
    }
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("mac", mac);
    BaseNodeModel node = new BaseNodeModel();
    node.setStartupConfig(FastJsonUtil.toJson(ciList));
    mongoDbServiceFacadeImpl.editBaseNode(input, node);
  }

  /**
   * 增加启动配置加载项
   * 
   * @param mac mac地址
   * @param configInfoList 配置信息列表
   */
  public void copyStartupConfig(String mac, List<ConfigInfoVo> configInfoList) {
    BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(mac);
    String startupConfig = baseNodeModel.getStartupConfig();
    List<ConfigInfoVo> ciList = new ArrayList<ConfigInfoVo>();
    if (!StringUtils.isBlank(startupConfig)) {
      ciList = FastJsonUtil.fromJsons(startupConfig, new TypeReference<List<ConfigInfoVo>>() {});
      Map<String, ConfigInfoVo> infoMap = Maps.newHashMap();
      for (ConfigInfoVo model : ciList)
        infoMap.put(model.getId(), model);
      for (ConfigInfoVo model : configInfoList) {
        if (infoMap.containsKey(model.getId())) continue;
        ciList.add(model);
      }
    } else {
      ciList = configInfoList;
    }
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("mac", mac);
    BaseNodeModel node = new BaseNodeModel();
    node.setStartupConfig(FastJsonUtil.toJson(ciList));
    mongoDbServiceFacadeImpl.editBaseNode(input, node);
  }

  /**
   * 移除启动配置加载项
   * 
   * @param mac mac地址
   * @param configInfoList 配置信息列表
   */
  public boolean removeStartupConfig(ConfigVo pojo) {
	BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(pojo.getMac());
    String startupConfig = baseNodeModel.getStartupConfig();
    List<ConfigInfoVo> ciList = new ArrayList<ConfigInfoVo>();
    if (!StringUtils.isBlank(startupConfig)) {
      List<ConfigInfoVo> ociList =
          FastJsonUtil.fromJsons(startupConfig, new TypeReference<List<ConfigInfoVo>>() {});
      for (ConfigInfoVo model : ociList) {
        if (pojo.getConfigId().equals(model.getId())) continue;
        ciList.add(model);
      }
    }
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("mac", pojo.getMac());
    BaseNodeModel node = new BaseNodeModel();
    node.setStartupConfig(FastJsonUtil.toJson(ciList));
    return mongoDbServiceFacadeImpl.editBaseNode(input, node);
  }

  public void markRemoveStartupConfig(String mac, String configId) {
	BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(mac);
    String startupConfig = baseNodeModel.getStartupConfig();
    List<ConfigInfoVo> ciList = new ArrayList<ConfigInfoVo>();
    if (!StringUtils.isBlank(startupConfig)) {
      List<ConfigInfoVo> ociList =
          FastJsonUtil.fromJsons(startupConfig, new TypeReference<List<ConfigInfoVo>>() {});
      for (ConfigInfoVo model : ociList) {
        if (configId.equals(model.getId())) {
          model.setState(Constant.MARK_DELET);
        };
        ciList.add(model);
      }
    }
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("mac", mac);
    BaseNodeModel node = new BaseNodeModel();
    node.setStartupConfig(FastJsonUtil.toJson(ciList));
    mongoDbServiceFacadeImpl.editBaseNode(input, node);
  }

  /**
   * 移除启动配置加载项
   * 
   * @param mac mac地址
   * @param configInfoList 配置信息列表
   */
  public void markRemoveStartupConfig(String mac, List<ConfigInfoVo> configInfoList) {
	BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(mac);
    String startupConfig = baseNodeModel.getStartupConfig();
    List<ConfigInfoVo> ciList = new ArrayList<ConfigInfoVo>();
    if (!StringUtils.isBlank(startupConfig)) {
      List<ConfigInfoVo> ociList =
          FastJsonUtil.fromJsons(startupConfig, new TypeReference<List<ConfigInfoVo>>() {});
      Map<String, ConfigInfoVo> infoMap = Maps.newHashMap();
      for (ConfigInfoVo model : configInfoList)
        infoMap.put(model.getId(), model);
      for (ConfigInfoVo model : ociList) {
        if (infoMap.containsKey(model.getId())) {
          model.setState(Constant.MARK_DELET);
        };
        ciList.add(model);
      }
    }
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("mac", mac);
    BaseNodeModel node = new BaseNodeModel();
    node.setStartupConfig(FastJsonUtil.toJson(ciList));
    mongoDbServiceFacadeImpl.editBaseNode(input, node);
  }

  /**
   * 修改节点别名根据MAC地址
   * 
   * @author zhouhuan
   */

  public boolean editAlias(String mac, String alias) {
    // TODO Auto-generated method stub
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("mac", mac);
    BaseNodeModel node = new BaseNodeModel();
    node.setAlias(alias);
    return mongoDbServiceFacadeImpl.editBaseNode(input, node);
  }

  public boolean editNginxConf(String mac, String nginxConf) {
    // TODO Auto-generated method stub
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("mac", mac);
    BaseNodeModel node = new BaseNodeModel();
    node.setNginxConf(nginxConf);
    return mongoDbServiceFacadeImpl.editBaseNode(input, node);
  }
}
