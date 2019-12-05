package com.xiaodou.control.util.netty;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;








import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.control.model.server.ProjectModel;
import com.xiaodou.control.service.netty.NettyApiService;
import com.xiaodou.summer.util.SpringWebContextHolder;
@Service("sendServerService")
public class SendServerService extends SimpleChannelHandler {
  @Resource
  NettyApiService nettyApiService;
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    Channel channel = e.getChannel();
    channel.close();
  }

  @SuppressWarnings("unchecked")
  private void packageData(MessageEvent e) {
    String oldMessage = e.getMessage().toString();
    Map<String,Object> subScribeJsonMap =JSON.parseObject(oldMessage,HashMap.class);
	Set<String> keySet = subScribeJsonMap.keySet();
	ProjectModel project =new ProjectModel();
    if(subScribeJsonMap.size()==3){
        for(String key : keySet){
          if("mac".equals(key)){
            project.setMac(subScribeJsonMap.get(key).toString());
          }else if("projectName".equals(key)){
        	  project.setProjectName(subScribeJsonMap.get(key).toString());
          }else if("state".equals(key)){
        	  project.setState(subScribeJsonMap.get(key).toString());
          }
        }
    }
    if(project!=null&&StringUtils.isNotBlank(project.getMac())&&StringUtils.isNotBlank(project.getProjectName())&&StringUtils.isNotBlank(project.getState())){
    	NettyApiService nettyApiService =
                SpringWebContextHolder.getBean("nettyApiService");
    	nettyApiService.add(project);
    }
  }

  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    this.packageData(e);
  }

  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
  }

}
