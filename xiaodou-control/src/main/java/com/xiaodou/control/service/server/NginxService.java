package com.xiaodou.control.service.server;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.control.model.server.NginxModel;
import com.xiaodou.control.model.server.NginxServerGroupRelationModel;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.thrift.annotion.ThriftService;

@Service("NginxService")
@ThriftService
public class NginxService {
  @Resource
  MongoDbServiceFacade mongoDbServiceFacadeImpl;

  public NginxService() {}

  /**
   * 增加Nginx配置
   * 
   * @author zhouhuan
   */

  public boolean addOrUpdate(NginxModel nginx) {
	  try{
		if(nginx.getNginxServerId()==null){
			return false;
		}
		NginxModel nginxModel=mongoDbServiceFacadeImpl.getNginxById(nginx.getNginxServerId());
		if(nginxModel!=null){
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("nginxServerId", nginx.getNginxServerId());
			return mongoDbServiceFacadeImpl.editNginxModel(input,nginx);
		}else{
			mongoDbServiceFacadeImpl.addNginx(nginx);
		}
		return true;
	  }catch(Exception e){
		  return false;
	  }
  }



  /**
   * 根据ID获取服务并且包装FTP信息
   * 
   * @author zhouhuan
   */
  public NginxModel getNginx(String serverId) {
    // TODO Auto-generated method stub
    if (serverId == null) {
      return null;
    }
    NginxModel nginxModel=mongoDbServiceFacadeImpl.getNginxById(serverId);
    return nginxModel;
  }


	/**
	 * 设置Nginx应用的服务组
	 * 
	 * @return
	 */
	public void editNginxServerGroup(String nginxServerGroupId, String needDeleteId,String needAddId){
		if (StringUtils.isNotBlank(needDeleteId)) {
			String[] needDeleteIdArray = needDeleteId.split(";");
			for (String id : needDeleteIdArray) {			
				Map<String,Object> input =Maps.newConcurrentMap();
				input.put("nginxServerId", nginxServerGroupId);
				input.put("serverGroupId", id);
				mongoDbServiceFacadeImpl.delNginxServerGroupRelationModelByCond(input);
			}
		}

		if (StringUtils.isNotBlank(needAddId)) {
			String[] needAddIdArray = needAddId.split(";");
			for (String id : needAddIdArray) {
				NginxServerGroupRelationModel model=new NginxServerGroupRelationModel();
				model.setNginxServerId(nginxServerGroupId);
				model.setServerGroupId(id);
				mongoDbServiceFacadeImpl.addNginxServerGroupRelation(model);
			}
		}
   }
}
