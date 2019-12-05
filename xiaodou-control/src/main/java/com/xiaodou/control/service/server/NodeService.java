package com.xiaodou.control.service.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.control.model.server.LogModel;
import com.xiaodou.control.model.server.NodeModel;
import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.model.server.ServerNodeRelationModel;
import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.request.server.NodeRequest;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;

@Service("nodeService")
public class NodeService {
  @Resource
  MongoDbServiceFacade mongoDbServiceFacadeImpl;
  @Resource
  ServerService serverService;
  public NodeService() {}
  
  public NodeModel addNode(String groupId,String mac){
	// TODO Auto-generated method stub
	  NodeModel node=new NodeModel();
	  node.setNodeId(RandomUtil.randomNumber(11).toString());
	  node.setMac(mac);
	  node.setGroupId(groupId);
	  return mongoDbServiceFacadeImpl.addNode(node);
  }
  public void deleteNode(String groupId,String mac) throws TException{
	// TODO Auto-generated method stub
	  Map<String,Object> cond=Maps.newConcurrentMap();
	  if(StringUtils.isNotBlank(groupId)){
		  cond.put("groupId", groupId);
	  }
	  if(StringUtils.isNotBlank(mac)){
		  cond.put("mac", mac);
		  if(StringUtils.isNotBlank(groupId)){
			  List<ServerModel> serverList=mongoDbServiceFacadeImpl.getServerByGroupId(groupId);
			  for(ServerModel server:serverList){
				 serverService.addCommand(server.getServerId(), server.getBaseServerId(), mac);
				 serverService.removeServerNodeRelation(server.getServerId(), mac);		
			  }
		  }else{
			  serverService.removeServerNodeRelation(null, mac);
		  }
		  
	  }
	  mongoDbServiceFacadeImpl.delNode(cond);  
  }
  

  
  public List<NodeModel> packageNodeList(List<NodeModel> nodeList){
	  for(NodeModel node:nodeList){
		  this.packageNode(node);
	  }
	  return nodeList;
  }
  
  public NodeModel packageNode(NodeModel node){
	  BaseNodeModel baseNode=mongoDbServiceFacadeImpl.getBaseNodeByMac(node.getMac());
	  node.setIp(baseNode.getIp());
	  node.setTime(baseNode.getTime());
	  node.setAlias(baseNode.getAlias());
	  return node;
  }
  
  
  
  
  /**
   * 获取组合后的node列表
   * 
   * @author zhouhuan
   */

  public List<NodeModel> getNodeModelListByServerId(String serverId) {
	Map<String, Object> cond=Maps.newConcurrentMap();
	cond.put("serverId", serverId);
	List<ServerNodeRelationModel> serverNodeRelationList = mongoDbServiceFacadeImpl.getServerNodeRelationListByCond(cond);
    List<NodeModel> nodeList = new ArrayList<NodeModel>();
      for (ServerNodeRelationModel s : serverNodeRelationList) {
          List<NodeModel> nodesList = mongoDbServiceFacadeImpl.getNodeListByMac(s.getMac());
          if(nodesList.size()>0&&nodesList.get(0)!=null){
        	  this.packageNode(nodesList.get(0));
        	  nodesList.get(0).setCurVersion(s.getVersion());
        	  nodesList.get(0).setCurWeight(s.getWeight());     
          }
          nodeList.add(nodesList.get(0));     
      }
    return nodeList;
  }

 
  /**
   * 增加日志
   * 
   * @author zhouhuan
   */
  public LogModel addLog(NodeRequest request, String id, String userId,String userName) {
    LogModel log = new LogModel();
    log.setLogId(id);
    log.setServerId(request.getServerId());
    log.setState("0");
    log.setCommandName(request.getCommandName());
    log.setCommandId(request.getCommandId());
    log.setMac(request.getMac());
    log.setVersion(request.getVersion());
    log.setCreateTime(new Timestamp(System.currentTimeMillis()).toString());
    log.setUserId(userId);
    log.setUserName(userName);
    BaseNodeModel baseNodeModel = mongoDbServiceFacadeImpl.getBaseNodeByMac(request.getMac());
    if(baseNodeModel!=null){
    	log.setAlias(baseNodeModel.getIp()+"("+baseNodeModel.getAlias()+")");
    }
    return mongoDbServiceFacadeImpl.addLog(log);
  }
  

}
 