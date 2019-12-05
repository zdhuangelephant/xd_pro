package com.xiaodou.resources.model.forum;

import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * 资源分类关联表
 * @author zhouhuan
 *
 */
public class CategoryRelationModel {
	
   private  String categoryId;
   private  String resourcesId;
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getResourcesId() {
		return resourcesId;
	}
	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}
	 public static void main(String[] args) {
		    MybatisXmlTool.getInstance(CategoryRelationModel.class, "xd_category_relation",
		        "F:/apache-tomcat-7.0.57/webapps/xd-resources/WEB-INF/classes/conf/mybatis/forum").buildXml();
		 }
}
