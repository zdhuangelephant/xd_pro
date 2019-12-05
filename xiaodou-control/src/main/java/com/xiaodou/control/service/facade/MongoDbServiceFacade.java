package com.xiaodou.control.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.control.model.server.FtpFileModel;
import com.xiaodou.control.model.server.MiddleServerModel;
import com.xiaodou.control.model.server.NodeModel;
import com.xiaodou.control.model.server.LogModel;
import com.xiaodou.control.model.server.NginxLogModel;
import com.xiaodou.control.model.server.NginxModel;
import com.xiaodou.control.model.server.NginxServerGroupRelationModel;
import com.xiaodou.control.model.server.ProjectModel;
import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.control.model.server.ServerGroupTypeModel;
import com.xiaodou.control.model.server.ServerModel;
import com.xiaodou.control.model.server.ServerNodeRelationModel;
import com.xiaodou.control.model.server.base.BaseNodeModel;
import com.xiaodou.control.model.server.base.BaseServerModel;
import com.xiaodou.control.model.server.expand.LogMonitorGroupRelationModel;
import com.xiaodou.control.model.server.expand.LogMonitorModel;
import com.xiaodou.control.request.server.NodeHostRequest;

public interface MongoDbServiceFacade {
	
	//命令执行日志
	List<LogModel> getLogByCond(String mac, String serverId);

	LogModel getLogById(String id);

	LogModel addLog(LogModel log);

	boolean editLog(Map<String, Object> input, LogModel entity);
	
	
	//Nginx部署日志
	List<NginxLogModel> getNginxLogByCond(String mac);

	NginxLogModel addNginxLog(NginxLogModel log);

	
	//NodeModel
	NodeModel addNode(NodeModel node);
	
	boolean delNode(Map<String,Object> map);

	NodeModel getNodeById(String nodeId);

	List<NodeModel> getNodeListByMac(String mac);
	
	List<NodeModel> getNodeListByGroupId(String groupId);
	
	List<NodeModel> getNodeListByCond(Map<String, Object> input);

	boolean editNode(Map<String, Object> input, NodeModel entity);
	
	//BaseNode
	BaseNodeModel addBaseNode(BaseNodeModel node);

	BaseNodeModel addBaseNode(NodeHostRequest node);

	BaseNodeModel getBaseNodeByMac(String mac);
	
	boolean delBaseNode(Map<String,Object> map);
	
	List<BaseNodeModel> getBaseNodeList(Map<String,Object> input);

	boolean editBaseNode(Map<String, Object> input, BaseNodeModel entity);


	//BaseServer
	BaseServerModel getBaseServerById(String id);

	List<BaseServerModel> getBaseServerList(Map<String, Object> input);

	BaseServerModel addBaseServer(BaseServerModel entity);

	boolean editBaseServer(Map<String, Object> input, BaseServerModel entity);

	boolean delBaseServer(String id);
	
	
	//Server
	ServerModel getServerById(String id);

	List<ServerModel> getServerListByCond(Map<String, Object> input);

	ServerModel addServer(ServerModel entity);

	boolean editServer(Map<String, Object> input, ServerModel entity);

	boolean delServer(Map<String, Object> cond);

	List<ServerModel> getServerByGroupId(String groupId);

	//MiddleServer
	MiddleServerModel getMiddleServerById(String id);

	List<MiddleServerModel> getMiddleServerListByCond(Map<String, Object> input);

	MiddleServerModel addMiddleServer(MiddleServerModel entity);

	boolean editMiddleServer(Map<String, Object> input, MiddleServerModel entity);

	boolean delMiddleServer(Map<String, Object> cond);

	List<MiddleServerModel> getMiddleServerByGroupId(String groupId);
	
	boolean delMiddleServer(String serverId);
	
	//ServerNodeRelation

	List<ServerNodeRelationModel> getServerNodeRelationListByCond(Map<String, Object> input);

	ServerNodeRelationModel addServerNodeRelation(ServerNodeRelationModel entity);

	boolean editServerNodeRelation(Map<String, Object> input, ServerNodeRelationModel entity);

	boolean delServerNodeRelationModelByCond(Map<String, Object> input);
	
	//ServerGroup
	ServerGroupModel getServerGroupById(String id);

	List<ServerGroupModel> getServerGroupListByCond(Map<String, Object> input);

	ServerGroupModel addServerGroup(ServerGroupModel entity);

	boolean editServerGroup(Map<String, Object> input, ServerGroupModel entity);

	boolean delServerGroup(String id);
	
	//ServerGroupType
	ServerGroupTypeModel getServerGroupTypeById(String id);

	List<ServerGroupTypeModel> getServerGroupTypeList();

	ServerGroupTypeModel addServerGroupType(ServerGroupTypeModel entity);

	boolean editServerGroupType(Map<String, Object> input, ServerGroupTypeModel entity);

	boolean delServerGroupType(String id);
	
	
	//Project
	ProjectModel addProject(ProjectModel entity);
	
	boolean delProject(String id);
	
	List<ProjectModel> getProjectByMac(String mac);

	List<ProjectModel> getProjectByCond(String mac,String projectName);
	
	boolean editProject(Map<String, Object> input, ProjectModel projectModel);
	
	
	//Nginx
	NginxModel getNginxById(String id);

	NginxModel addNginx(NginxModel entity);

	boolean editNginxModel(Map<String, Object> input, NginxModel entity);

	boolean delNginxModel(String id);
	
	NginxServerGroupRelationModel addNginxServerGroupRelation(NginxServerGroupRelationModel entity);
	
	List<NginxServerGroupRelationModel> getNginxServerGroupRelationByCond(Map<String, Object> input);
	
	boolean delNginxServerGroupRelationModelByCond(Map<String, Object> input);

	boolean delServer(String serverId);
	
	//LogMonitor
	
	LogMonitorModel getLogMonitorById(String id);

	List<LogMonitorModel> getLogMonitorList();

	LogMonitorModel addLogMonitor(LogMonitorModel entity);

	boolean editLogMonitor(Map<String, Object> input, LogMonitorModel entity);

	boolean delLogMonitor(String id);
	
	//LogMonitor
	
	List<LogMonitorGroupRelationModel> getLogMonitorGroupRelationListByCond(Map<String, Object> cond);

	LogMonitorGroupRelationModel addLogMonitorGroupRelation(LogMonitorGroupRelationModel entity);

	boolean delLogMonitorGroupRelationByCond(Map<String, Object> cond);
	
	
	//FtpFile
	FtpFileModel getFtpFileById(String id);

	List<FtpFileModel> getFtpFileListByCond(Map<String, Object> cond);

	FtpFileModel addFtpFile(FtpFileModel entity);

	boolean editFtpFile(Map<String, Object> input, FtpFileModel entity);

	boolean delFtpFile(String id);



	
}
