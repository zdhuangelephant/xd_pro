package com.xiaodou.esagent.service;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.esagent.dao.ESInit2;
import com.xiaodou.esagent.enums.CommonEnum.DumpMethod;
import com.xiaodou.esagent.enums.CommonEnum.FilterType;
import com.xiaodou.esagent.enums.CommonEnum.SortType;
import com.xiaodou.esagent.model.DumpType;
import com.xiaodou.esagent.model.Filter;
import com.xiaodou.esagent.util.FileUtil;

public class BaseDao<Entity>  {	
	public String index =null;
	public String type = null;
	private static Client client = ESInit2.getClient();
    private Class<Entity> entityClass = null;
    private Entity entity = null;
	private IndicesAdminClient adminClient;
	@SuppressWarnings("unchecked")
	public BaseDao() {
	    this.entityClass = null;
	    this.entity=null;
	    Class<?> c = getClass();
	    Type type = c.getGenericSuperclass();
	    if (type instanceof ParameterizedType) {
	      Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
	      this.entityClass = (Class<Entity>) parameterizedType[0];
	    }
	    this.index =this.getEntityClass().getSimpleName().toLowerCase();
	    this.type = this.getEntityClass().getSimpleName().toLowerCase();
	  }
	public Class<Entity> getEntityClass() {
	    return entityClass;
	 }
	public Entity getEntity(){
		return entity;
	}
	public boolean indexExists() {
		adminClient = client.admin().indices();
		IndicesExistsRequest request = new IndicesExistsRequest(index);
		IndicesExistsResponse response = adminClient.exists(request)
				.actionGet();
		if (response.isExists()) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("hiding")
	public <Entity> IndexResponse addORUpdata(String id,DumpType t) {
		try {
		    t.setMethod(DumpMethod.ADD);
		    t.setId(id);
			IndexResponse res = client.prepareIndex(index, type, id.toString())
					.setSource(t.toString()).execute().actionGet();
			return res;
		} catch (Exception e) {
			FileUtil<Entity> util = new FileUtil<Entity>(this.getEntityClass().getSimpleName());
			util.saveFile(FastJsonUtil.toJson(t));
		}
		return null;
	}

	@SuppressWarnings("hiding")
	public <Entity> Boolean del(String id) {
		DumpType t=new DumpType();
		try {
			t.setMethod(DumpMethod.DEL);
			t.setId(id);
			DeleteResponse del = client.prepareDelete().setIndex(index)
					.setType(type).setId(id).execute().actionGet();
			return del.isFound();
		} catch (Exception e) {
			FileUtil<Entity> util = new FileUtil<Entity>(this.getEntityClass().getSimpleName());
			util.saveFile(FastJsonUtil.toJson(t));
		}
		return null;
	}

	public GetResponse getById(String id) {
		GetResponse res = client.prepareGet().setIndex(index).setType(type)
				.setId(id).execute().actionGet();
		return res;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <Entity extends DumpType> List<Entity> search(QueryBuilder query,QueryBuilder postFilter,
			List<FieldSortBuilder> sortBuilders, int from, int size,Entity t){
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
		searchRequestBuilder.setTypes(type);
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		searchRequestBuilder.setFrom(from);
		searchRequestBuilder.setSize(size);
		searchRequestBuilder.setExplain(false);
		searchRequestBuilder.setQuery(query);
		if(postFilter!=null){
			searchRequestBuilder.setPostFilter(postFilter);
		}
		if (sortBuilders != null && sortBuilders.size() > 0) {
			for (FieldSortBuilder sortBuilder : sortBuilders) {
				searchRequestBuilder.addSort(sortBuilder);
			}
		}
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		SearchHit[] hits = response.getHits().getHits();  
	    List<Entity> list = new ArrayList<Entity>();  
        for (SearchHit hit : hits) {  
            t = (Entity) FastJsonUtil.fromJson(hit.getSourceAsString(),
            		t.getClass());
            list.add(t);
        }  
		return list;
	}
	@SuppressWarnings({ "unchecked", "hiding" })
	public <Entity extends DumpType> List<Entity> search(Map <String, String>query,List<Filter> filter,Map<String,SortType> sort,int from, int size){
		Entity t = null;
		try {
			t = (Entity) this.getEntityClass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
		QueryBuilder queryBuilder =new BoolQueryBuilder(); 
		if(query!=null){
			BoolQueryBuilder b=new BoolQueryBuilder();
			 for (String key : query.keySet()) {		
				   QueryBuilder  builder= QueryBuilders.termQuery(key,query.get(key));
				   b.must(builder);
			}	
			 queryBuilder=b;
		}	
		if(sort!=null){
			 for (String key : sort.keySet()) {
				   if(SortType.ASC.equals(sort.get(key))){
					   FieldSortBuilder f= SortBuilders.fieldSort(key.toString()).order(SortOrder.ASC);
					   searchRequestBuilder.addSort(f);
				   }else if(SortType.DESC.equals(sort.get(key))){
					   FieldSortBuilder f=(SortBuilders.fieldSort(key.toString()).order(SortOrder.DESC));
					   searchRequestBuilder.addSort(f);
				   }
			}	
		}	
		QueryBuilder postFilter =null;
		if(filter!=null){
			BoolQueryBuilder b=new BoolQueryBuilder();
			for(Filter f:filter){
				if(f.getType()==FilterType.FROM){
					QueryBuilder  builder=QueryBuilders.rangeQuery(f.getColumn()).from(f.getData());
					b.must(builder);
				}else if(f.getType()==FilterType.TO){
					QueryBuilder  builder=QueryBuilders.rangeQuery(f.getColumn()).to(f.getData());
					b.must(builder);
				}
			}	
			postFilter =b;
		}	
		
		searchRequestBuilder.setTypes(type);
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		searchRequestBuilder.setFrom(from);
		searchRequestBuilder.setSize(size);
		searchRequestBuilder.setExplain(false);
		searchRequestBuilder.setQuery(queryBuilder);
		if(postFilter!=null){
			searchRequestBuilder.setPostFilter(postFilter);
		}
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		SearchHit[] hits = response.getHits().getHits();  
	    List<Entity> list = new ArrayList<Entity>();  
        for (SearchHit hit : hits) {  
            t = (Entity) FastJsonUtil.fromJson(hit.getSourceAsString(),
            		t.getClass());
            list.add(t);
        }  
		return list;
	}
	/**
	 * 通用方法
	 * 
	 * @author zhouhuan
	 */
	public  List<Entity> getObject(String sets) {
		List<Entity> reList = new ArrayList<Entity>();
		if (!StringUtils.isBlank(sets)) {
			reList = FastJsonUtil.fromJsons(sets,
					new TypeReference<List<Entity>>() {
					});
		}
		return reList;
	}

}
