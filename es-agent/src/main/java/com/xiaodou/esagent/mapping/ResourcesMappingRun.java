package com.xiaodou.esagent.mapping;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.xiaodou.esagent.dao.ESInit;
import com.xiaodou.esagent.dao.ESInit2;

public class ResourcesMappingRun {
	private static Client client = ESInit2.getClient();

	/**
	 * 创建索引名称
	 * 
	 * @param indices
	 *            索引名称
	 */
	public static void createCluterName(String indices) {
		try {
			client.admin().indices().prepareCreate(indices).execute()
					.actionGet();
			System.out.print("创建索引成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建mapping(feid("indexAnalyzer","ik")该字段分词IK索引
	 * ；feid("searchAnalyzer","ik")该字段分词ik查询；具体分词插件请看IK分词插件说明)
	 * 
	 * @param indices
	 *            索引名称；
	 * @param mappingType
	 *            索引类型
	 * @throws Exception
	 */
	public static void createMapping(String indices, String mappingType)
			throws Exception {
		try {
			XContentBuilder builder = ResourcesMapping.getMapping();
			PutMappingRequest mapping = Requests.putMappingRequest(indices)
					.type(mappingType).source(builder);
			client.admin().indices().putMapping(mapping).actionGet();
			System.out.print("创建索引mapping成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		createCluterName("resources");
		createMapping("resources", "resources");
		client.close();
	}
}
