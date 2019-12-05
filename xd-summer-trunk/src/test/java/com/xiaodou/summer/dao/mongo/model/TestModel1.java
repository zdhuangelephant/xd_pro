package com.xiaodou.summer.dao.mongo.model;

import com.xiaodou.summer.dao.mongo.annotion.Pk;

public class TestModel1 {
  @Pk
  private String tid;
  private String name;
  private Integer age;

  public String getTid() {
    return tid;
  }

  public void setTid(String tid) {
    this.tid = tid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
}
