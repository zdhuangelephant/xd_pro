package com.xiaodou.ms.vo.resources;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class ColumnSearchVO {
  private List<DataObj> data;
  
  @Data
  public static class DataObj{
    private Highlight highlight;
    private Object object;
    /** isSubscribe 默认表示可以订阅 */
    private String isSubscribe = "0";
	public Highlight getHighlight() {
		return highlight;
	}
	public void setHighlight(Highlight highlight) {
		this.highlight = highlight;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
    
  }
  @Data
  public static class Highlight{
    private String content;
    private String description;
    private String title;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    
  }
  @Data
  public static class Object{
    private String id;
    private Author author;
    @JSONField(name = "articles_count")
    private String articlesCount;
    private String followers;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public String getArticlesCount() {
		return articlesCount;
	}
	public void setArticlesCount(String articlesCount) {
		this.articlesCount = articlesCount;
	}
	public String getFollowers() {
		return followers;
	}
	public void setFollowers(String followers) {
		this.followers = followers;
	}
    
  }
  @Data
  public static class Author {
    private String name;
    private String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    
  }
public List<DataObj> getData() {
	return data;
}
public void setData(List<DataObj> data) {
	this.data = data;
}
}
