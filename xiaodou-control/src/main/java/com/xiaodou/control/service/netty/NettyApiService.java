package com.xiaodou.control.service.netty;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.xiaodou.control.model.server.ProjectModel;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;
@Service("nettyApiService")
public class NettyApiService {
  public NettyApiService() {}

  /**
   * python调用用于获取增加节点,节点存在返回命令集
   * 
   * @author zhouhuan
   */
  public void add(ProjectModel project){
	  MongoDbServiceFacade mongoDbServiceFacadeImpl =
            SpringWebContextHolder.getBean("mongoDbServiceFacadeImpl");
	  List<ProjectModel> list = mongoDbServiceFacadeImpl.getProjectByCond(project.getMac(),project.getProjectName());
	    if (list == null || list.size() == 0) {
	    	project.setCreateTime(new Timestamp(System.currentTimeMillis()).toString());
	    	project.setProjectId(UUID.randomUUID().toString());
	    	project.setUpdateTime(new Timestamp(System.currentTimeMillis()).toString());
	        mongoDbServiceFacadeImpl.addProject(project);
	    } else if (list != null && list.size() == 1) {
	    	ProjectModel projectModel=list.get(0);   	
	      Map<String, Object> input = new HashMap<String, Object>();
	      input.put("mac", project.getMac());  
	      input.put("projectName", project.getProjectName());  
	      projectModel.setState(project.getState());
	      projectModel.setUpdateTime((new Timestamp(System.currentTimeMillis()).toString()));
	      mongoDbServiceFacadeImpl.editProject(input, projectModel);
	    }    
  }
}
