package com.xiaodou.ucerCenter.agent.model;

import java.sql.Timestamp;

public class FeedBackModel {
    private String id;

    private String token;

    private String content;
    
    private Timestamp createTime;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public Timestamp getCreateTime() {
      return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
      this.createTime = createTime;
    }

    @Override
    public String toString() {
      return "FeedBackModel [id=" + id + ", token=" + token + ", content=" + content
          + ", createTime=" + createTime + "]";
    }
    
}