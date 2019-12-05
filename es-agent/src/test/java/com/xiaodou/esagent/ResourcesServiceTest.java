package com.xiaodou.esagent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import com.xiaodou.esagent.enums.CommonEnum.FilterType;
import com.xiaodou.esagent.enums.CommonEnum.SortType;
import com.xiaodou.esagent.model.Filter;

public class ResourcesServiceTest {
	/*@Test
	public void testAddORUpdata() {
		ResourcesDao dao = new ResourcesDao();
		ResourcesForEs f=new ResourcesForEs();
		f.setId("444");
		f.setContent("中国军人在法国");
	    f.setOutline(null);
	    f.setTitle("爱学习");
	    Timestamp nowTime = new Timestamp(new Date().getTime());
	    f.setCreateTime(nowTime);
	    dao.addORUpdata("444",f);
	}*/
	/*@Test
	public void fileRead() {
    	List<ResourcesForEs> list=ResourcesFileUtil.findFile("resources_file.json");
    	System.out.println(list);
    	for(ResourcesForEs resources:list){
    		if(resources.getTitle()!=null){
    			
    		}
    	}
	}
	@Test
	public void delR() {
		del("123");
	}
	@Test
	public void getByIdR() {
		getById("123");
	}*/
/*	@Test
	public void searchR() {
		QueryBuilder qb = QueryBuilders.termQuery("title", "爱学习");
		try {
			ResourcesDao dao = new ResourcesDao();
			ResourcesForEs r=new ResourcesForEs();
			List<FieldSortBuilder> sortBuilders = new ArrayList<FieldSortBuilder>();
			sortBuilders.add(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
			QueryBuilder query = new BoolQueryBuilder()  
	        .must(QueryBuilders.termQuery("title", "爱学习"));
			QueryBuilder postFilter=QueryBuilders.rangeQuery("createTime").to("1482471814677");
			List<ResourcesForEs> h=dao.search(query,postFilter,sortBuilders,0,12,r);
			System.out.println(h);
		} catch (NoNodeAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	@Test
	public void searchT() {
		try {
			ResourcesDao dao = new ResourcesDao();
			ResourcesForEs r=new ResourcesForEs();
			Map <String, String>query =new HashMap <String, String>();
			query.put("title", "爱学习");
			Map <String, SortType>sort =new HashMap <String, SortType>();
			sort.put("createTime", SortType.DESC);
			List<Filter> filterList =new ArrayList<Filter> ();
			Filter f=new Filter();
			f.setColumn("createTime");
			f.setData("1482471814677");
			f.setType(FilterType.TO);
			filterList.add(f);
			List<ResourcesForEs> h=dao.search(query,filterList,sort,0,12);
			System.out.println(h);
		} catch (NoNodeAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
