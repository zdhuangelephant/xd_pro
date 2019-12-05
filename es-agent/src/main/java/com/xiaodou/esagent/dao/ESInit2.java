package com.xiaodou.esagent.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.esagent.util.EsProp;

/**
 * ES工具类
 * @author zhouhuan
 *
 */
public class ESInit2 {
    public static final String CLUSTER_NAME = EsProp.getParams("cluster_name"); //实例名称
	private static final String IP = EsProp.getParams("host");  
	private static final int PORT = Integer.parseInt(EsProp.getParams("port"));  //端口
	private static Client clients = null;
	private ESInit2() {
	}
	/**
	 * 关闭对应client
	 * @param client
	 */
    public static void close(Client client) {
        if (client != null) {
            try {
             client.close();
            } catch (Exception e) {
            }
            client = null;
        }
    }

    public static void flush(Client client, String indexName, String indexType) {
		try{
			client.admin().indices().flush(new FlushRequest(indexName.toLowerCase(), indexType)).actionGet();
		}catch(Exception e){};
	}
	
	/**
	 * 根据默认系统默认配置初始化库,如果已经有连接则使用该连接
	 * @return
	 */
	public static Client getClient() {
		
		if(clients!=null) {
			return clients;
		}
		clients = newClient();
		
		return clients;
		
	}
	
	/**
	 * 初始化并连接elasticsearch集群，返回连接后的client
	 * @return 返回连接的集群的client
	 */
	public static Client newClient() {
		String clusterName =CLUSTER_NAME;
		int port = PORT;
		String hostname = IP;
		String hostnames[] = hostname.split(",");	
		boolean clientTransportSniff = true;
		return newClient(clusterName, clientTransportSniff, port, hostnames);
		
	}
	
	/**
	 * 初始化并连接elasticsearch集群，返回连接后的client
	 * @param clusterName 中心节点名称
	 * @param clientTransportSniff 是否自动发现新加入的节点
	 * @param port 节点端口
	 * @param hostname 集群节点所在服务器IP，支持多个
	 * @return 返回连接的集群的client
	 */
	public static Client newClient(String clusterName, boolean clientTransportSniff, int port, String... hostname) {
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", clusterName)
				.put("client.transport.sniff", clientTransportSniff)
				.build();
		
		TransportClient transportClient = TransportClient.builder().settings(settings).build();
		if(hostname!=null){
			for(String host: hostname) {
				try {
					transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		return transportClient;
	}
	
	public static boolean indicesExists(Client client, String indexName){
		IndicesExistsRequest ier = new IndicesExistsRequest();
		ier.indices(new String[]{indexName.toLowerCase()});
		
		return client.admin().indices().exists(ier).actionGet().isExists();
	}
	
	public static boolean typesExists(Client client, String indexName, String indexType){
		if(indicesExists(client, indexName)) {
			TypesExistsRequest ter = new TypesExistsRequest(new String[]{indexName.toLowerCase()}, indexType);
			return client.admin().indices().typesExists(ter).actionGet().isExists();
		}
		return false;
	}
		
	
	public static SearchHits search(String indexName, List<String> indexTypes, QueryBuilder query, List<FieldSortBuilder> sortBuilders, int from, int size) throws NoNodeAvailableException, Exception {
		if(getClient() == null ) {
			return null;
		}
		indexName = indexName.toLowerCase();
		
		// 去掉不存在的索引
		IndicesExistsRequest ier = new IndicesExistsRequest();
		ier.indices(new String[]{indexName});
		boolean exists = getClient().admin().indices().exists(ier).actionGet().isExists();
		if(exists){
			getClient().admin().indices().open(new OpenIndexRequest(indexName)).actionGet();
		}else{
			Index index = new Index(indexName);
			//throw new IndexMissingException(index);
			return null;
		}
		
		try {
			getClient().admin().indices().refresh(new RefreshRequest(indexName)).actionGet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(indexName);
		
		if(indexTypes != null && indexTypes.size() > 0) {
			String[] types = new String[indexTypes.size()];
			for(int i=0; i<indexTypes.size(); i++) {
				types[i] = indexTypes.get(i).toLowerCase();
			}
			searchRequestBuilder.setTypes(types);
		}
		
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		searchRequestBuilder.setFrom(from);
		searchRequestBuilder.setSize(size);
		searchRequestBuilder.setExplain(false);
		searchRequestBuilder.setQuery(query);
		if(sortBuilders!=null && sortBuilders.size()>0){
			for(FieldSortBuilder sortBuilder: sortBuilders){
				searchRequestBuilder.addSort(sortBuilder);
			}
		}
		
		return searchRequestBuilder.execute().actionGet().getHits();
	}
	
	/**
	 * 查询数据
	 * @param indexName 索引名称
	 * @param indexType 索引类型
	 * @param id 数据id
	 * @return 如果不存在，返回<code>null</code>
	 */
	public static Map<String, Object> query(String indexName, String indexType, String id) {
		if(getClient() == null ) {
			return null;
		}
		if( StringUtils.isEmpty(indexName) || StringUtils.isEmpty(indexType) ||  StringUtils.isEmpty(id)) {
			return null;
		}
		indexName = indexName.toLowerCase();
		indexType = indexType.toLowerCase();
		
		IndicesExistsRequest ier = new IndicesExistsRequest();
		ier.indices(new String[]{indexName});
		boolean exists = getClient().admin().indices().exists(ier).actionGet().isExists();
		if(!exists){
			// 索引不存在
			return null; 
		}
		
		getClient().admin().indices().open(new OpenIndexRequest(indexName)).actionGet();
		getClient().admin().indices().refresh(new RefreshRequest(indexName)).actionGet();
		
		GetRequest gr = new GetRequest(indexName, indexType, id);
		
		ActionFuture<GetResponse> future = getClient().get(gr);
		GetResponse response = future.actionGet();
		return swapResult(response);
	}
	
	
	public static List<Map<String, Object>> swapResult(SearchHits hits) {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		
		if(hits == null || hits.getTotalHits() <= 0) {
			return datas;
		}
		
		for(int i=0; i<hits.hits().length; i++) {
			SearchHit hit = hits.getAt(i);
			
			Map<String, Object> rowData = hit.sourceAsMap(); 
			rowData.put("_index", hit.getIndex());
			rowData.put("_type", hit.getType());
			rowData.put("_id", hit.getId());
			
			datas.add(rowData);
		}
		
		return datas;
	}
	
	public static Map<String, Object> swapResult(GetResponse response) {
		if(response == null || !response.isExists()) {
			return null;
		}
			
		Map<String, Object> rowData = response.getSourceAsMap();
		rowData.put("_index", response.getIndex());
		rowData.put("_type", response.getType());
		rowData.put("_id", response.getId());
		
		return rowData;
	}
	
	
}
