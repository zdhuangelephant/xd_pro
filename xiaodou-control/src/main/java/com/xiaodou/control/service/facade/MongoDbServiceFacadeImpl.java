package com.xiaodou.control.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.control.dao.server.FtpFileDao;
import com.xiaodou.control.dao.server.LogDao;
import com.xiaodou.control.dao.server.MiddleServerDao;
import com.xiaodou.control.dao.server.NginxDao;
import com.xiaodou.control.dao.server.NginxLogDao;
import com.xiaodou.control.dao.server.NginxServerGroupRelationDao;
import com.xiaodou.control.dao.server.NodeDao;
import com.xiaodou.control.dao.server.ProjectDao;
import com.xiaodou.control.dao.server.ServerDao;
import com.xiaodou.control.dao.server.ServerGroupDao;
import com.xiaodou.control.dao.server.ServerGroupTypeDao;
import com.xiaodou.control.dao.server.ServerNodeRelationDao;
import com.xiaodou.control.dao.server.base.BaseNodeDao;
import com.xiaodou.control.dao.server.base.BaseServerDao;
import com.xiaodou.control.dao.server.expand.LogMonitorDao;
import com.xiaodou.control.dao.server.expand.LogMonitorGroupRelationDao;
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
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

@Service("mongoDbServiceFacadeImpl")
public class MongoDbServiceFacadeImpl implements MongoDbServiceFacade {
	@Resource
	LogDao logDao;
	@Resource
	NodeDao nodeDao;
	@Resource
	BaseNodeDao baseNodeDao;
	@Resource
	ServerDao serverDao;
	@Resource
	MiddleServerDao middleServerDao;
	@Resource
	BaseServerDao baseServerDao;
	@Resource
	ServerGroupDao serverGroupDao;
	@Resource
	ServerNodeRelationDao serverNodeRelationDao;
	@Resource
	ServerGroupTypeDao serverGroupTypeDao;
	@Resource
	ProjectDao projectDao;
	@Resource
	NginxDao nginxDao;
	@Resource
	NginxServerGroupRelationDao nginxServerGroupRelationDao;
	@Resource
	NginxLogDao nginxLogDao;
	@Resource
	LogMonitorGroupRelationDao logMonitorGroupRelationDao;
	@Resource
	LogMonitorDao logMonitorDao;
	@Resource
	FtpFileDao ftpFileDao;
	@Override
	public List<LogModel> getLogByCond(String mac, String serverId) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInput("mac", mac);
		param.addInput("serverId", serverId);
		param.addSort("createTime", Sort.DESC);
		return logDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public LogModel getLogById(String id) {
		// TODO Auto-generated method stub
		LogModel log = new LogModel();
		log.setLogId(id);
		return logDao.findEntityById(log);
	}

	@Override
	public LogModel addLog(LogModel log) {
		return logDao.addEntity(log);
	}

	@Override
	public boolean editLog(Map<String, Object> input, LogModel entity) {
		// TODO Auto-generated method stub
		return logDao.updateEntityByCond(input, entity);
	}

	/**
	 * 根据ID获取节点
	 * 
	 * @author zhouhuan
	 */

	public NodeModel getNodeById(String nodeId) {
		// TODO Auto-generated method stub
		NodeModel node = new NodeModel();
		node.setNodeId(nodeId);
		return nodeDao.findEntityById(node);
	}

	/**
	 * 根据mac获取节点
	 * 
	 * @author zhouhuan
	 */

	public List<NodeModel> getNodeListByMac(String mac) {
		IQueryParam param = new QueryParam();
		if(StringUtils.isNotBlank(mac)){
			param.addInput("mac", mac);
		}
		List <NodeModel> list=nodeDao.findEntityListByCond(param, null).getResult();
		if(list!=null){
			return nodeDao.findEntityListByCond(param, null).getResult();
		}else{
			return Lists.newArrayList();
		}
	}

	@Override
	public boolean editNode(Map<String, Object> input, NodeModel entity) {
		// TODO Auto-generated method stub
		return nodeDao.updateEntityByCond(input, entity);
	}

	@Override
	public NodeModel addNode(NodeModel node) {
		return nodeDao.addEntity(node);
	}
	
	
	/**
	 * 根据ID获取节点
	 * 
	 * @author zhouhuan
	 */

	public BaseNodeModel getBaseNodeByMac(String mac) {
		// TODO Auto-generated method stub
		BaseNodeModel node = new BaseNodeModel();
		node.setMac(mac);
		return baseNodeDao.findEntityById(node);
	}

	/**
	 * 获取基础NODE列表
	 * 
	 * @author zhouhuan
	 */

	public List<BaseNodeModel> getBaseNodeList(Map<String, Object> input) {
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		return baseNodeDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public boolean editBaseNode(Map<String, Object> input, BaseNodeModel entity) {
		// TODO Auto-generated method stub
		return baseNodeDao.updateEntityByCond(input, entity);
	}

	@Override
	public BaseNodeModel addBaseNode(BaseNodeModel node) {
		return baseNodeDao.addEntity(node);
	}

	@Override
	public BaseNodeModel addBaseNode(NodeHostRequest node) {
		// TODO Auto-generated method stub
		BaseNodeModel n = new BaseNodeModel();
		n.setAlias(node.getAlias());
		n.setIp(node.getIp());
		n.setMac(node.getMac());
		n.setDockerStatus(node.getDockerStatus());
		return baseNodeDao.addEntity(n);
	}

	@Override
	public boolean delServer(String serverId) {
		ServerModel server = new ServerModel();
		server.setServerId(serverId);
		return serverDao.deleteEntityById(server);
	}

	@Override
	public boolean delServer(Map<String,Object> cond) {
		return serverDao.deleteEntityByCond(cond);
	}
	@Override
	public List<ServerModel> getServerListByCond(Map<String,Object> cond) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(cond);
		return serverDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public ServerModel getServerById(String id) {
		// TODO Auto-generated method stub
		ServerModel server = new ServerModel();
		server.setServerId(id);
		return serverDao.findEntityById(server);
	}

	@Override
	public ServerModel addServer(ServerModel server) {
		// TODO Auto-generated method stub
		return serverDao.addEntity(server);
	}

	@Override
	public boolean editServer(Map<String, Object> input, ServerModel entity) {
		// TODO Auto-generated method stub
		return serverDao.updateEntityByCond(input, entity);
	}

	@Override
	public List<ServerModel> getServerByGroupId(String groupId) {
		IQueryParam param = new QueryParam();
		param.addInput("groupId", groupId);
		return serverDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public ServerGroupModel getServerGroupById(String id) {
		// TODO Auto-generated method stub
		ServerGroupModel s = new ServerGroupModel();
		s.setGroupId(id);
		return serverGroupDao.findEntityById(s);
	}

	@Override
	public List<ServerGroupModel> getServerGroupListByCond(Map<String, Object> input) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		return serverGroupDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public ServerGroupModel addServerGroup(ServerGroupModel entity) {
		// TODO Auto-generated method stub
		return serverGroupDao.addEntity(entity);
	}

	@Override
	public boolean editServerGroup(Map<String, Object> input,
			ServerGroupModel entity) {
		// TODO Auto-generated method stub
		return serverGroupDao.updateEntityByCond(input, entity);
	}

	@Override
	public boolean delServerGroup(String id) {
		// TODO Auto-generated method stub
		ServerGroupModel s = new ServerGroupModel();
		s.setGroupId(id);
		return serverGroupDao.deleteEntityById(s);
	}

	
	@Override
	public ServerGroupTypeModel getServerGroupTypeById(String serverGroupTypeId) {
		// TODO Auto-generated method stub
		ServerGroupTypeModel s = new ServerGroupTypeModel();
		s.setServerGroupTypeId(serverGroupTypeId);
		return serverGroupTypeDao.findEntityById(s);
	}

	@Override
	public List<ServerGroupTypeModel> getServerGroupTypeList() {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		return serverGroupTypeDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public ServerGroupTypeModel addServerGroupType(ServerGroupTypeModel entity) {
		// TODO Auto-generated method stub
		return serverGroupTypeDao.addEntity(entity);
	}

	@Override
	public boolean editServerGroupType(Map<String, Object> input,
			ServerGroupTypeModel entity) {
		// TODO Auto-generated method stub
		return serverGroupTypeDao.updateEntityByCond(input, entity);
	}

	@Override
	public boolean delServerGroupType(String serverGroupTypeId) {
		// TODO Auto-generated method stub
		ServerGroupTypeModel s = new ServerGroupTypeModel();
		s.setServerGroupTypeId(serverGroupTypeId);
		return serverGroupTypeDao.deleteEntityById(s);
	}
	
	
	@Override
	public NginxLogModel addNginxLog(NginxLogModel log) {
		// TODO Auto-generated method stub
		return nginxLogDao.addEntity(log);
	}

	@Override
	public BaseServerModel getBaseServerById(String id) {
		// TODO Auto-generated method stub
		BaseServerModel baseServer=new BaseServerModel();
		baseServer.setBaseServerId(id);
		return baseServerDao.findEntityById(baseServer);
	}

	@Override
	public List<BaseServerModel> getBaseServerList(Map<String, Object> input) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		return baseServerDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public BaseServerModel addBaseServer(BaseServerModel entity) {
		// TODO Auto-generated method stub
		return baseServerDao.addEntity(entity);
	}

	@Override
	public boolean editBaseServer(Map<String, Object> input,
			BaseServerModel entity) {
		// TODO Auto-generated method stub
		return baseServerDao.updateEntityByCond(input, entity);
	}

	@Override
	public boolean delBaseServer(String id) {
		// TODO Auto-generated method stub
		BaseServerModel baseServer=new BaseServerModel();
		baseServer.setBaseServerId(id);
		return baseServerDao.deleteEntityById(baseServer);
	}
	
	
	
	
	@Override
	public ProjectModel addProject(ProjectModel entity) {
		// TODO Auto-generated method stub
		return projectDao.addEntity(entity);
	}

	@Override
	public boolean delProject(String id) {
		// TODO Auto-generated method stub
		ProjectModel s = new ProjectModel();
		s.setProjectId(id);
		return projectDao.deleteEntityById(s);
	}

	@Override
	public List<ProjectModel> getProjectByMac(String mac) {
		IQueryParam param = new QueryParam();
		if(StringUtils.isNotBlank(mac)){
			param.addInput("mac", mac);
		}
		return projectDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public boolean editProject(Map<String, Object> input, ProjectModel projectModel) {
		// TODO Auto-generated method stub
		return projectDao.updateEntityByCond(input, projectModel);
	}

	@Override
	public List<ProjectModel> getProjectByCond(String mac, String projectName) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		if(StringUtils.isNotBlank(mac)){
			param.addInput("mac", mac);
		}
		if(StringUtils.isNotBlank(projectName)){
			param.addInput("projectName", projectName);
		}
		return projectDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public NginxModel getNginxById(String id) {
		// TODO Auto-generated method stub
		NginxModel s = new NginxModel();
		s.setNginxServerId(id);
		return nginxDao.findEntityById(s);
	}


	@Override
	public NginxModel addNginx(NginxModel entity) {
		// TODO Auto-generated method stub
		return nginxDao.addEntity(entity);
	}

	@Override
	public boolean editNginxModel(Map<String, Object> input,
			NginxModel entity) {
		// TODO Auto-generated method stub
		return nginxDao.updateEntityByCond(input, entity);
	}

	@Override
	public boolean delNginxModel(String id) {
		// TODO Auto-generated method stub
		ServerGroupModel s = new ServerGroupModel();
		s.setGroupId(id);
		return serverGroupDao.deleteEntityById(s);
	}

	@Override
	public NginxServerGroupRelationModel addNginxServerGroupRelation(
			NginxServerGroupRelationModel entity) {
		// TODO Auto-generated method stub
		return nginxServerGroupRelationDao.addEntity(entity);
	}

	@Override
	public List<NginxServerGroupRelationModel> getNginxServerGroupRelationByCond(
			Map<String, Object> input) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		return nginxServerGroupRelationDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public boolean delNginxServerGroupRelationModelByCond(Map<String, Object> input) {
		// TODO Auto-generated method stub
		return nginxServerGroupRelationDao.deleteEntityByCond(input);
	}

	@Override
	public List<NginxLogModel> getNginxLogByCond(String mac) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInput("mac", mac);
		param.addSort("createTime", Sort.DESC);
		return nginxLogDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public List<NodeModel> getNodeListByGroupId(String groupId) {
		IQueryParam param = new QueryParam();
		param.addInput("groupId", groupId);
		return nodeDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public boolean delNode(Map<String,Object> cond) {
		// TODO Auto-generated method stub
		return nodeDao.deleteEntityByCond(cond);
	}

	@Override
	public List<ServerNodeRelationModel> getServerNodeRelationListByCond(
			Map<String, Object> input) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		return serverNodeRelationDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public ServerNodeRelationModel addServerNodeRelation(
			ServerNodeRelationModel entity) {
		// TODO Auto-generated method stub
		return serverNodeRelationDao.addEntity(entity);
	}

	@Override
	public boolean editServerNodeRelation(Map<String, Object> input,
			ServerNodeRelationModel entity) {
		// TODO Auto-generated method stub
		return serverNodeRelationDao.updateEntityByCond(input, entity);
	}

	@Override
	public boolean delServerNodeRelationModelByCond(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return serverNodeRelationDao.deleteEntityByCond(cond);
	}

	@Override
	public LogMonitorModel getLogMonitorById(String id) {
		// TODO Auto-generated method stub
		LogMonitorModel log=new LogMonitorModel();
		log.setLogMonitorId(id);
		return logMonitorDao.findEntityById(log);
	}

	@Override
	public List<LogMonitorModel> getLogMonitorList() {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		return logMonitorDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public LogMonitorModel addLogMonitor(LogMonitorModel entity) {
		// TODO Auto-generated method stub
		return logMonitorDao.addEntity(entity);
	}

	@Override
	public boolean editLogMonitor(Map<String, Object> input,
			LogMonitorModel entity) {
		// TODO Auto-generated method stub
		return logMonitorDao.updateEntityByCond(input, entity);
	}

	@Override
	public boolean delLogMonitor(String id) {
		// TODO Auto-generated method stub
		LogMonitorModel log=new LogMonitorModel();
		log.setLogMonitorId(id);
		return logMonitorDao.deleteEntityById(log);
	}

	@Override
	public List<LogMonitorGroupRelationModel> getLogMonitorGroupRelationListByCond(
			Map<String, Object> cond) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(cond);
		return logMonitorGroupRelationDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public LogMonitorGroupRelationModel addLogMonitorGroupRelation(
			LogMonitorGroupRelationModel entity) {
		// TODO Auto-generated method stub
		return logMonitorGroupRelationDao.addEntity(entity);
	}

	@Override
	public boolean delLogMonitorGroupRelationByCond(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return logMonitorGroupRelationDao.deleteEntityByCond(cond);
	}

	@Override
	public boolean delBaseNode(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseNodeDao.deleteEntityByCond(map);
	}

	@Override
	public FtpFileModel getFtpFileById(String id) {
		// TODO Auto-generated method stub
		FtpFileModel file=new FtpFileModel();
		file.setFileId(id);
		return ftpFileDao.findEntityById(file);
	}

	@Override
	public List<FtpFileModel> getFtpFileListByCond(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(cond);
		return ftpFileDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public FtpFileModel addFtpFile(FtpFileModel entity) {
		// TODO Auto-generated method stub
		return ftpFileDao.addEntity(entity);
	}

	@Override
	public boolean editFtpFile(Map<String, Object> input, FtpFileModel entity) {
		// TODO Auto-generated method stub
		return ftpFileDao.updateEntityByCond(input, entity);
	}

	@Override
	public boolean delFtpFile(String id) {
		// TODO Auto-generated method stub
		FtpFileModel file=new FtpFileModel();
		file.setFileId(id);
		return ftpFileDao.deleteEntityById(file);
	}

	@Override
	public List<NodeModel> getNodeListByCond(Map<String, Object> input) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(input);
		return nodeDao.findEntityListByCond(param, null).getResult();
	}



	@Override
	public boolean delMiddleServer(String serverId) {
		MiddleServerModel server = new MiddleServerModel();
		server.setServerId(serverId);
		return middleServerDao.deleteEntityById(server);
	}

	@Override
	public boolean delMiddleServer(Map<String,Object> cond) {
		return middleServerDao.deleteEntityByCond(cond);
	}
	@Override
	public List<MiddleServerModel> getMiddleServerListByCond(Map<String,Object> cond) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInputs(cond);
		return middleServerDao.findEntityListByCond(param, null).getResult();
	}

	@Override
	public MiddleServerModel getMiddleServerById(String id) {
		// TODO Auto-generated method stub
		MiddleServerModel server = new MiddleServerModel();
		server.setServerId(id);
		return middleServerDao.findEntityById(server);
	}

	@Override
	public MiddleServerModel addMiddleServer(MiddleServerModel server) {
		// TODO Auto-generated method stub
		return middleServerDao.addEntity(server);
	}

	@Override
	public boolean editMiddleServer(Map<String, Object> input, MiddleServerModel entity) {
		// TODO Auto-generated method stub
		return middleServerDao.updateEntityByCond(input, entity);
	}

	@Override
	public List<MiddleServerModel> getMiddleServerByGroupId(String groupId) {
		// TODO Auto-generated method stub
		IQueryParam param = new QueryParam();
		param.addInput("groupId", groupId);
		return middleServerDao.findEntityListByCond(param, null).getResult();
	}



}
