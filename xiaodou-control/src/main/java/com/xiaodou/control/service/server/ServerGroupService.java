package com.xiaodou.control.service.server;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.control.model.server.ServerGroupModel;
import com.xiaodou.control.request.server.ServerGroupRequest;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.thrift.annotion.ThriftService;

@Service("serverGroupService")
@ThriftService
public class ServerGroupService {

  @Resource
  MongoDbServiceFacade mongoDbServiceFacadeImpl;


  public ServerGroupService() {}

  /**
   * 增加服务
   * 
   * @author zhouhuan
   */
  public void add(ServerGroupRequest s) {
    ServerGroupModel group = new ServerGroupModel();
    group.setGroupId(s.getGroupId());
    group.setGroupName(s.getGroupName());
    group.setGroupServiceType(s.getGroupServiceType());
    group.setGroupType(s.getGroupType());
    group.setType(s.getType());
    mongoDbServiceFacadeImpl.addServerGroup(group);
  }


  /**
   * 修改服务
   * 
   * @author zhouhuan
   */
  public boolean edit(ServerGroupRequest server) {
    // TODO Auto-generated method stub
    ServerGroupModel group = new ServerGroupModel();
    group.setGroupName(server.getGroupName());
    group.setGroupType(server.getGroupType());
    group.setGroupServiceType(server.getGroupServiceType());
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("groupId", server.getGroupId());
    return mongoDbServiceFacadeImpl.editServerGroup(input, group);
  }


  public void addServerRelation(String baseServerId,String serverGroupId){
	  
	  
  }
}
