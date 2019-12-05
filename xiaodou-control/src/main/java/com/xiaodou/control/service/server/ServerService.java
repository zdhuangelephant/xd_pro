package com.xiaodou.control.service.server;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.model.server.ServerNodeRelationModel;
import com.xiaodou.control.model.server.base.BaseServerModel;
import com.xiaodou.control.prop.FtpProp;
import com.xiaodou.control.request.server.NodeRequest;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;

@Service("serverService")
public class ServerService {
  @Resource
  MongoDbServiceFacade mongoDbServiceFacadeImpl;
  @Resource
  NodeService nodeService;
  @Resource
  BaseNodeService baseNodeService;
  public ServerService() {}


  public  ServerModel addServer(String groupId, String baseServerId) {
		// TODO Auto-generated method stub
	  ServerModel s=new ServerModel();
	  s.setServerId(RandomUtil.randomNumber(11).toString());
	  s.setBaseServerId(baseServerId);
	  s.setGroupId(groupId);
	  return mongoDbServiceFacadeImpl.addServer(s);
	}
  
  public void deleteServer(String groupId, String baseServerId) throws TException {
		// TODO Auto-generated method stub
	  Map<String,Object> cond=Maps.newConcurrentMap();
	  cond.put("groupId", groupId);
	  cond.put("baseServerId", baseServerId);
	  List<ServerModel> serverList=mongoDbServiceFacadeImpl.getServerListByCond(cond);  
	  if(serverList!=null&&serverList.get(0)!=null){
		    this.addCommand(serverList.get(0).getServerId(),baseServerId, null);
			this.removeServerNodeRelation(serverList.get(0).getServerId(),null);
	  }
	  mongoDbServiceFacadeImpl.delServer(cond);  
	}
  
  public void deleteServer(String groupId) {
		// TODO Auto-generated method stub
	  Map<String,Object> cond=Maps.newConcurrentMap();
	  cond.put("groupId", groupId);
	  mongoDbServiceFacadeImpl.delServer(cond);  
	}
  
  public List<ServerModel> packageServerList(List<ServerModel> serverList){
	  for(ServerModel server:serverList){
		  this.packageServer(server);
	  }
	  return serverList;
  }
  
  public ServerModel packageServer(ServerModel server){
	  if(server==null){
		  return null;
	  }
	  BaseServerModel baseServer=mongoDbServiceFacadeImpl.getBaseServerById(server.getBaseServerId());
	  if(baseServer!=null){
		  server.setBaseDir(baseServer.getBaseDir());
		  server.setTomcatPort(baseServer.getTomcatPort());
		  server.setUser(baseServer.getUser());
		  server.setWarAdress(baseServer.getWarAdress());
		  server.setServerName(baseServer.getServerName());
	  }
	  return server;
  }
  
  
  
  /**
   * 根据ID获取服务并且包装FTP信息
   * 
   * @author zhouhuan
   */
  public ServerModel getById(String serverId) {
    // TODO Auto-generated method stub
    if (serverId == null) {
      return null;
    }
    ServerModel server = mongoDbServiceFacadeImpl.getServerById(serverId);
    if(server!=null){
	    this.packageServer(server);
	    server.setUserName(FtpProp.getParams("username"));
	    server.setPassWord(FtpProp.getParams("password"));
	    server.setHost(FtpProp.getParams("host"));
	    server.setPort(FtpProp.getParams("port"));
    }
    return server;
  }


  /**
   * 服务增加节点关联
   * 
   * @author zhouhuan
   */

  public ServerNodeRelationModel addServerNodeRelation(String serverId, String nodeId,String mac) throws TException {
	ServerNodeRelationModel serverNodeRelationModel=new ServerNodeRelationModel();
	serverNodeRelationModel.setServerNodeId(RandomUtil.randomNumber(11).toString());
	serverNodeRelationModel.setServerId(serverId);
	serverNodeRelationModel.setNodeId(nodeId);
	serverNodeRelationModel.setMac(mac);
    return mongoDbServiceFacadeImpl.addServerNodeRelation(serverNodeRelationModel);
  }
  
  /**
   * 改变服务关联的节点的状态
   * 此处属性可传null
   * @author zhouhuan
   */

  public boolean removeServerNodeRelation(String serverId, String mac) throws TException {
	Map<String, Object> cond=Maps.newConcurrentMap();
	if(StringUtils.isNotBlank(serverId)){
		cond.put("serverId", serverId);
	}
	if(StringUtils.isNotBlank(mac)){
		cond.put("mac", mac);
	}
	return mongoDbServiceFacadeImpl.delServerNodeRelationModelByCond(cond);
  }
  
  /**
   * 批量增加命令
   * 此处属性可传null
   * @author zhouhuan
   */

  public void addCommand(String serverId,String baseServerId, String mac) throws TException {
	Map<String, Object> cond=Maps.newConcurrentMap();
	if(StringUtils.isNotBlank(serverId)){
		cond.put("serverId", serverId);
	}
	if(StringUtils.isNotBlank(mac)){
		cond.put("mac", mac);
	}
	List<ServerNodeRelationModel> list= mongoDbServiceFacadeImpl.getServerNodeRelationListByCond(cond);
	for(ServerNodeRelationModel s :list ){
		String id = UUID.randomUUID().toString();
		NodeRequest request=new NodeRequest();
		request.setCommandId("4");
		request.setCommandName("批量删除");
		request.setMac(s.getMac());
		request.setServerId(baseServerId);
		baseNodeService.addCommand(request,id);
	}
  }

  /**
   * ServerNodeRelation版本号修改
   * 
   * @author zhouhuan
   */

  public boolean editVersion(String serverId, String mac, String version) throws TException {
    Map<String, Object> cond=Maps.newConcurrentMap();
    cond.put("serverId", serverId);
    cond.put("mac", mac);
    ServerNodeRelationModel r=new ServerNodeRelationModel();
    r.setVersion(version);
    return mongoDbServiceFacadeImpl.editServerNodeRelation(cond, r);
  }
  
  /**
   * ServerNodeRelation关联Nginx权重修改
   * 
   * @author zhouhuan
   */

  public boolean editStrategy(String serverId, String mac, String strategy,String weight) {
	  Map<String, Object> cond=Maps.newConcurrentMap();
	  cond.put("serverId", serverId);
	  cond.put("mac", mac);
	  ServerNodeRelationModel r=new ServerNodeRelationModel();
	  r.setStrategy(strategy);
	  r.setWeight(weight);
	  return mongoDbServiceFacadeImpl.editServerNodeRelation(cond, r);
  }

  /**
   * server后置url修改
   * 
   * @author zhouhuan
   */

  public boolean editServerRequestUrl(String serverId,String requestUrl){
	  Map<String, Object> cond=Maps.newConcurrentMap();
	  cond.put("serverId", serverId);
	  ServerModel s=new ServerModel();
	  s.setRequestUrl(requestUrl);
	  return mongoDbServiceFacadeImpl.editServer(cond, s);
  }
 
}
