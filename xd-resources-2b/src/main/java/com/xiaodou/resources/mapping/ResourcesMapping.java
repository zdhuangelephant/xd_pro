package com.xiaodou.resources.mapping;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

//创建mapping
public class ResourcesMapping {
	static XContentBuilder getMapping() throws Exception{
		XContentBuilder builder=XContentFactory
				.jsonBuilder()
				.startObject()
				    .startObject("resourcesfores")
					.startObject("properties")
						.startObject("title")
							.field("type", "string")
							.field("store", "yes")
							.field("analyzer","ik")
							.field("index","analyzed")
						.endObject()
						.startObject("outline")
							.field("type", "string")
							.field("store", "yes")
							.field("analyzer","ik")
							.field("index","analyzed")
						.endObject()
						.startObject("content")
							.field("type", "string")
							.field("store", "no")
							.field("analyzer","ik")
							.field("index","analyzed")
						.endObject()
						.startObject("digest")
							.field("type", "string")
							.field("store", "no")
							.field("index","not_analyzed")
						.endObject()
						.startObject("publisherId")
							.field("type", "string")
							.field("store", "no")
							.field("index","not_analyzed")
						.endObject()			
						.startObject("createTime")
							.field("type", "string")
							.field("store", "no")
							.field("index","not_analyzed")
/*							.field("format","YY-MM-DD HH:MM:SS")*/
						.endObject()				 
					.endObject()
				.endObject()
			.endObject();
		return builder;
}
}
