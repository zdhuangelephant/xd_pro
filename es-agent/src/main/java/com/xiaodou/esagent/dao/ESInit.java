package com.xiaodou.esagent.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.xiaodou.esagent.util.EsProp;
public class ESInit {
    public static final String CLUSTER_NAME = EsProp.getParams("cluster_name");
    private static final String HOST_NAME = EsProp.getParams("host");  
    private static final int PORT = Integer.parseInt(EsProp.getParams("port"));  //端口
    //1.设置集群名称：默认是elasticsearch，并设置client.transport.sniff为true，使客户端嗅探整个集群状态，把集群中的其他机器IP加入到客户端中  
    private static Settings settings = Settings  
            .settingsBuilder()  
            .put("cluster.name",CLUSTER_NAME)  
            .put("client.transport.sniff", true)  
            .build();  
    //创建私有对象  
    private static TransportClient client;   
    static {  
/*        try {  */
        	client = TransportClient.builder().settings(settings).build();
        	String hostname = HOST_NAME;
    		String hostnames[] = hostname.split(",");	
    		if(hostnames!=null){
    			for(String host: hostnames) {
    				try {
    					client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), PORT));
    				} catch (UnknownHostException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} 
    			}
    		}
            System.out.print("集群连接成功");
    }  
    //取得实例  
    public static synchronized TransportClient getTransportClient(){ 
        return client;  
    }  
  
    //为集群添加新的节点  
    public static synchronized void addNode(String name){  
        try {  
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(name),9300));  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        }  
    }  
    
    //删除集群中的某个节点  
    public static synchronized void removeNode(String name){  
        try {  
            client.removeTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(name),9300));  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        }  
    }  
}