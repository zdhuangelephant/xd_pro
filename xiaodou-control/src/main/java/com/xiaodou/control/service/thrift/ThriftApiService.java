package com.xiaodou.control.service.thrift;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.constant.Constant;
import com.xiaodou.control.dao.server.ServerDao;
import com.xiaodou.control.enums.DockerStatusEnum;
import com.xiaodou.control.enums.StartupConfigCommand;
import com.xiaodou.control.model.server.LogModel;
import com.xiaodou.control.model.server.MiddleServerModel;
import com.xiaodou.control.model.server.NginxLogModel;
import com.xiaodou.control.model.server.NginxModel;
import com.xiaodou.control.model.server.NginxServerGroupRelationModel;
import com.xiaodou.control.model.server.NodeModel;
import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.model.server.ServerNodeRelationModel;
import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.model.server.base.BaseServerModel;
import com.xiaodou.control.model.server.expand.LogMonitorGroupRelationModel;
import com.xiaodou.control.model.server.expand.LogMonitorModel;
import com.xiaodou.control.prop.FtpProp;
import com.xiaodou.control.request.server.AllContainers;
import com.xiaodou.control.request.server.DockerImages;
import com.xiaodou.control.request.server.NodeHostRequest;
import com.xiaodou.control.request.server.NodeMacRequest;
import com.xiaodou.control.request.server.NodeNginxLogRequest;
import com.xiaodou.control.request.server.NodeRequest;
import com.xiaodou.control.request.server.ServerRequest;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.NodeService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.vo.ConfigInfoVo;
import com.xiaodou.control.vo.ConfigVo;
import com.xiaodou.control.vo.NginxConfVo;
import com.xiaodou.control.vo.NodeCommandVo;
import com.xiaodou.thrift.annotion.ThriftMethod;
import com.xiaodou.thrift.annotion.ThriftService;
import com.xiaodou.common.util.Base64Utils;


@Service("thriftApiService")
@ThriftService
public class ThriftApiService {
  @Resource
  BaseNodeService baseNodeService;
  @Resource
  NodeService nodeService;
  @Resource
  ServerService serverService;
  @Resource
  MongoDbServiceFacade mongoDbServiceFacadeImpl;

  public ThriftApiService() {}

  /**
   * python调用用于获取增加节点,节点存在返回命令集
   * 
   * @author zhouhuan
   */
  @ThriftMethod
  public String add(NodeHostRequest n) throws TException {
	if(n.getMac().equals("44:A8:42:18:90:18")){
		return null;
	}
    BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(n.getMac());
    List<ServerGroupModel> serverGroupList=this.getServerGroupByMac(n.getMac());
    if (baseNodeModel == null) {
      mongoDbServiceFacadeImpl.addBaseNode(n);
      return null;
    } else {
      if (!baseNodeModel.getIp().equals(n.getIp())) {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("mac", n.getMac());
        BaseNodeModel node = new BaseNodeModel();
        node.setIp(n.getIp());
        mongoDbServiceFacadeImpl.editBaseNode(input, node);
      }
      //获取并改变DOCKER状态
      if (baseNodeModel.getDockerStatus()==null||!baseNodeModel.getDockerStatus().equals(n.getDockerStatus())) {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("mac", n.getMac());
        BaseNodeModel node = new BaseNodeModel();
        if(n.getDockerStatus()==null){
        	node.setDockerStatus(DockerStatusEnum.UnknownDocker.getCode());
        }else{
        	node.setDockerStatus(n.getDockerStatus());
        }
        mongoDbServiceFacadeImpl.editBaseNode(input, node);
       }
      //获取DOCKER容器和镜像
      BaseNodeModel baseNode = new BaseNodeModel();
      if(n.getDockerStatus()!=null&&n.getDockerStatus().equals(DockerStatusEnum.DockerRunning.getCode())){
    	  List<DockerImages> imageList=Lists.newArrayList();
    	  List<AllContainers> containerList=Lists.newArrayList();
    	  if(n.getDockerImages()!=null){
        	  String images=new String(Base64Utils.decode(n.getDockerImages()));
        	  //包装镜像防止替换镜像启动命令
              imageList=FastJsonUtil.fromJsons(images, new TypeReference<List<DockerImages>>() {});
              for(DockerImages d:imageList){
            	  for(DockerImages image:baseNodeModel.getDockerImages()){
            		  if(d.getId().equals(image.getId())){
            			  d.setStartCommand(image.getStartCommand());
            		  }
            	  }
              }    	 
          }
          if(n.getContainers()!=null){
        	  String containers=new String(Base64Utils.decode(n.getContainers()));  
        	  containers = containers.replace("\"", ""); //去掉引号 
        	  String[] container = containers.split("xiaodoufenge");
        	  for(String c: container){
        		  AllContainers all=new AllContainers();
        		  all.setId(c.split(" ")[0]);
        		  all.setDetails(c);
        		  containerList.add(all);
        	  }
          }
          baseNode.setDockerImages(imageList);
          baseNode.setContainers(containerList);
      }

      //修改NODEMODEL
      Map<String, Object> input = new HashMap<String, Object>();
      input.put("mac", n.getMac());  
      baseNode.setTime(new Timestamp(System.currentTimeMillis()).toString());
      mongoDbServiceFacadeImpl.editBaseNode(input, baseNode);
      String command = baseNodeModel.getNodeCommand();
      if (command == null) {
        command = "[]";
      }
      String startConfig = this.getStartupConfig(serverGroupList);
      if (startConfig == null) {
        startConfig = "[]";
      }
      return String.format("%s#ADDNODE#%s", command, startConfig);
    }
  }

  /**
   * python调用，用于增加日志，修改任务状态
   * 
   * @author zhouhuan
   */
  @ThriftMethod
  public String editState(NodeRequest n) throws TException {
    System.out.print(n.getMac());
    BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(n.getMac());
    if (baseNodeModel != null) {
      String command = baseNodeModel.getNodeCommand();
      List<NodeCommandVo> ncList = getNodeCommandList(command);
      for (int i = 0; i < ncList.size(); i++) {
        if (ncList.get(i).getId().equals(n.getId())) {
          ncList.remove(i);
        }
      }
      LogModel log = mongoDbServiceFacadeImpl.getLogById(n.getId());
      String msg = "";
      if (log != null && log.getMsg() != null) {
        msg = log.getMsg() + n.getMsg().toString();
      } else {
        msg = n.getMsg();
      }
      log.setMsg(msg);
      log.setState(n.getState());
      Map<String, Object> input = new HashMap<String, Object>();
      input.put("logId", n.getId());
      mongoDbServiceFacadeImpl.editLog(input, log);
      Map<String, Object> input2 = new HashMap<String, Object>();
      input2.put("mac", n.getMac());
      BaseNodeModel node = new BaseNodeModel();
      node.setNodeCommand(FastJsonUtil.toJson(ncList));
      if (mongoDbServiceFacadeImpl.editBaseNode(input2, node)) {
        return "success";
      } else {
        return "false";
      }
    }
    return "false";
  }

  /**
   * 根据ID获取服务
   * 
   * @author zhouhuan
   */
  @ThriftMethod
  public List<BaseServerModel> getBaseServerById(ServerRequest server) throws TException {
    List<BaseServerModel> serverList=Lists.newArrayList();
	if (server == null) {
      return serverList;
    }
	BaseServerModel baseServer=mongoDbServiceFacadeImpl.getBaseServerById(server.getServerId());    
    serverList.add(baseServer);
    return serverList;
  }
  
  /**
   * 根据ID获取服务
   * 
   * @author zhouhuan
   */
  @ThriftMethod
  public List<ServerModel> getById(ServerRequest server) throws TException {
    List<ServerModel> serverList=Lists.newArrayList();
	if (server == null) {
      return serverList;
    }
    ServerModel serverModel = mongoDbServiceFacadeImpl.getServerById(server.getServerId());
    if (serverModel != null) {
    	serverModel.setUserName(FtpProp.getParams("username"));
    	serverModel.setPassWord(FtpProp.getParams("password"));
    	serverModel.setHost(FtpProp.getParams("host"));
    	serverModel.setPort(FtpProp.getParams("port"));
    }
    serverService.packageServer(serverModel);
    serverList.add(serverModel);
    return serverList;
  }
  
  /**
   * 根据ID获取中间件服务
   * 
   * @author zhouhuan
   */
  @ThriftMethod
  public List<MiddleServerModel> getMiddleServerById(ServerRequest server) throws TException {
	  List<MiddleServerModel> middleServerList=Lists.newArrayList();
	  middleServerList.add(mongoDbServiceFacadeImpl.getMiddleServerById(server.getServerId()));
	  return middleServerList;
  }


  /**
   * 根据ID获取中间件服务
   * 
   * @author zhouhuan
   */
  @ThriftMethod
  public List<MiddleServerModel> getMonitoringData(NodeHostRequest n) throws TException {
	      BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(n.getMac());
	      if(baseNodeModel==null){
	    	  return null;
	      }
	      List<ServerGroupModel> serverGroupList=this.getServerGroupByMac(n.getMac());
	      List<MiddleServerModel> middleServerList=this.getMiddleServerList(serverGroupList);
	      return middleServerList;
  }

  
  
  
  /**
   * 通用方法，根据command转换成泛型List
   * 
   * @author zhouhuan
   */
  @ThriftMethod
  public List<NodeCommandVo> getNodeCommandList(String command) throws TException {
    List<NodeCommandVo> ncList = new ArrayList<NodeCommandVo>();
    if (!StringUtils.isBlank(command)) {
      ncList = FastJsonUtil.fromJsons(command, new TypeReference<List<NodeCommandVo>>() {});
    }
    return ncList;
  }

  /**
   * 移除启动配置加载项
   * 
   * @param mac mac地址
   * @param configInfoList 配置信息列表
   */
  @ThriftMethod
  public boolean removeStartupConfig(ConfigVo pojo) {
    return baseNodeService.removeStartupConfig(pojo);
  }
  
  /**
   * 启动当前服务器下的所有容器
   * 
   * @param mac mac地址
   */
  @ThriftMethod
  public void startAll(NodeHostRequest n) {
	if(StringUtils.isNotBlank(n.getMac())){
		BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(n.getMac());
		if (baseNodeModel != null) {
			for(AllContainers c:baseNodeModel.getContainers()){
				NodeRequest request=new NodeRequest();
				request.setCommandId(StartupConfigCommand.StartContainer.getCode());
				request.setCommandInfo("");
				request.setCommandName(StartupConfigCommand.StartContainer.getDesc());
				request.setMac(n.getMac());
				request.setServerId(c.getId());
				String id = UUID.randomUUID().toString();
				baseNodeService.addCommand(request, id);
				nodeService.addLog(request, id, "0000000","auto");
			}
		}
	}
  }
  
  
  
  /**
   * python定时调用用于获取nginx配置信息是否改变并拉取数据
   * 
   * @author zhouhuan
   */
  @ThriftMethod
  public String getNginxConf(NodeMacRequest n) throws TException {
    if(n.getMac()==null){
	    return null; 
    }
    BaseNodeModel baseNode = mongoDbServiceFacadeImpl.getBaseNodeByMac(n.getMac());
    //1.根据MAC反查服务组
    List<ServerGroupModel> serverGroupList=this.getServerGroupByMac(n.getMac());
    //2.根据服务组反查NginxServerId
    Map<String,Object> nginxMaps=Maps.newConcurrentMap();
    for(ServerGroupModel s:serverGroupList){
    	Map<String, Object> input = new HashMap<String, Object>();
        input.put("serverGroupId", s.getGroupId());
        List<NginxServerGroupRelationModel> relationlist=mongoDbServiceFacadeImpl.getNginxServerGroupRelationByCond(input);
        for(NginxServerGroupRelationModel relation:relationlist){
        	nginxMaps.put(relation.getNginxServerId(), "");
        }
    }
    //3.根据NginxServerId查询Nginx配置信息并且包装
    List <NginxModel> nginxList=Lists.newArrayList();
 
    for (Map.Entry<String, Object> entry : nginxMaps.entrySet()) {   
    	NginxModel nginx=mongoDbServiceFacadeImpl.getNginxById(entry.getKey());
    	if(nginx!=null){
    		nginxList.add(nginx);   
    	}
    }     
    List <NginxConfVo> finalList=Lists.newArrayList();
    for(NginxModel nginxModel:nginxList){
    	NginxConfVo nginx=new NginxConfVo(nginxModel);
    	ServerModel server = mongoDbServiceFacadeImpl.getServerById(nginx.getNginxServerId());
        serverService.packageServer(server);
    	if(server!=null){
        	Map<String, Object> input = new HashMap<String, Object>();
            input.put("serverId", server.getServerId());
        	List<ServerNodeRelationModel> sList = mongoDbServiceFacadeImpl.getServerNodeRelationListByCond(input);
            List<NginxConfVo.UpstreamNode> upstreamNodeList=Lists.newArrayList();
            for(ServerNodeRelationModel s:sList){
	            	NginxConfVo.UpstreamNode upsteam=new NginxConfVo.UpstreamNode();
	            	List<NodeModel> nodeList = mongoDbServiceFacadeImpl.getNodeListByMac(s.getMac());
	            	nodeService.packageNodeList(nodeList);
	            	if(nodeList!=null&&nodeList.size()>0){
	            		upsteam.setIpAdress(baseNode.getIp()+":"+server.getTomcatPort());
	                	if(s.getStrategy()!=null){
	                		upsteam.setStrategy(s.getStrategy());
	                	}
	                	upstreamNodeList.add(upsteam);
	            	}
	            	nginx.setUpstreamNode(FastJsonUtil.toJson(upstreamNodeList));
            }
        }
        nginx.setCommandId("2111");
        nginx.setCommandName("nginx重新配置");
        finalList.add(nginx);       
    }
    String nginxConf=FastJsonUtil.toJson(finalList);
    if(StringUtils.isBlank(nginxConf)){
    	nginxConf="[]";
    }
    if(baseNode!=null){
    	if(nginxConf.equals(baseNode.getNginxConf())){
    		 return null;
    	}else{
    		baseNodeService.editNginxConf(n.getMac(),nginxConf);
    	}
    } 
    return String.format("%s#EDIT#",nginxConf);
  }

  /**
   * Nginx日志
   * 
   * @author zhouhuan
   */
  @ThriftMethod
  public void nginxLog(NodeNginxLogRequest n) throws TException {
    if(n.getMac()==null){
    	return;
    }
    NginxLogModel nginxLog=new NginxLogModel();
    nginxLog.setLogId(UUID.randomUUID().toString());
    nginxLog.setMac(n.getMac());
    nginxLog.setMsg(n.getMsg());
    nginxLog.setState(n.getState());
    nginxLog.setCreateTime(new Timestamp(System.currentTimeMillis()).toString());
    mongoDbServiceFacadeImpl.addNginxLog(nginxLog);
  }
  
  
  /**
   * 根据mac地址反查所在的服务组
   * 
   * @author zhouhuan
   */
  public List<ServerGroupModel> getServerGroupByMac(String mac) {
	List<ServerGroupModel> serverGroupList =Lists.newArrayList();
	Map<String, Object> input = new HashMap<String, Object>();
    input.put("mac", mac);
    List<NodeModel> nodeList=mongoDbServiceFacadeImpl.getNodeListByCond(input);
    for(NodeModel node:nodeList){
    	serverGroupList.add(mongoDbServiceFacadeImpl.getServerGroupById(node.getGroupId()));
    }
	return serverGroupList;
  }
  
  /**
   * 获取中间件服务
   * 
   * @author zhouhuan
   */
  public List<MiddleServerModel> getMiddleServerList(List<ServerGroupModel> list){
	  List<MiddleServerModel> middleServerList=Lists.newArrayList();
	  for(ServerGroupModel s:list){
		  Map<String,Object> input =Maps.newConcurrentMap();
		  input.put("groupId", s.getGroupId());
		  input.put("timingMonitoring", Constant.open);
		  middleServerList.addAll(mongoDbServiceFacadeImpl.getMiddleServerByGroupId(s.getGroupId()));
	  }
	  return middleServerList;
	  
  }
  
  
  public String getStartupConfig(List<ServerGroupModel> list){
	  List<ConfigInfoVo> configList=Lists.newArrayList();
	  Map <String,String>logM=Maps.newConcurrentMap();
	  for(ServerGroupModel s:list){
		  Map<String,Object> input =Maps.newConcurrentMap();
		  input.put("groupId", s.getGroupId());
		  List<LogMonitorGroupRelationModel> relationList=mongoDbServiceFacadeImpl.getLogMonitorGroupRelationListByCond(input);
		  for(LogMonitorGroupRelationModel l:relationList){
			  logM.put(l.getLogMonitorId(),l.getLogMonitorId());
		  }
	  }
	  for(String value: logM.values()){
		  LogMonitorModel log= mongoDbServiceFacadeImpl.getLogMonitorById(value);
		  log.getLogPath();
		  ConfigInfoVo configModel = log.transferModel();
		  configList.add(configModel);
	  }
	  return FastJsonUtil.toJson(configList);
	  
  }

}
