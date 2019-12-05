package com.xiaodou.common.test.util.reflect;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.enums.ClassType;
import com.xiaodou.common.util.warp.FastJsonUtil;

public class test {

  public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
    UserBean userBean = new UserBean();
    userBean.setAddress("adsf");
    System.out.println(FastJsonUtil.toJson(userBean));
    Map<String, String> strMAP = Maps.newHashMap();
    strMAP.put("errorInfo", new RuntimeException().toString());
    System.out.println(FastJsonUtil.toJson(strMAP));
  }

  static class UserBean {
    private String address;
    private ClassType classType;
    private Short short1;
    private Double double1;
    private Float float1;
    private Integer id;
    private Long age;
    private String name;
    private List<String> userNameList0;
    private Map<String, Object> map0;
    private ArrayList<String> userNameList1;
    private HashMap<String, Object> map1;
    private Timestamp tt;

    public ClassType getClassType() {
      return classType;
    }

    public void setClassType(ClassType classType) {
      this.classType = classType;
    }

    public List<String> getUserNameList0() {
      return userNameList0;
    }

    public void setUserNameList0(List<String> userNameList0) {
      this.userNameList0 = userNameList0;
    }

    public Map<String, Object> getMap0() {
      return map0;
    }

    public void setMap0(Map<String, Object> map0) {
      this.map0 = map0;
    }

    public ArrayList<String> getUserNameList1() {
      return userNameList1;
    }

    public void setUserNameList1(ArrayList<String> userNameList1) {
      this.userNameList1 = userNameList1;
    }

    public HashMap<String, Object> getMap1() {
      return map1;
    }

    public void setMap1(HashMap<String, Object> map1) {
      this.map1 = map1;
    }

    public Timestamp getTt() {
      return tt;
    }

    public void setTt(Timestamp tt) {
      this.tt = tt;
    }

    public Short getShort1() {
      return short1;
    }

    public void setShort1(Short short1) {
      this.short1 = short1;
    }

    public Double getDouble1() {
      return double1;
    }

    public void setDouble1(Double double1) {
      this.double1 = double1;
    }

    public Float getFloat1() {
      return float1;
    }

    public void setFloat1(Float float1) {
      this.float1 = float1;
    }

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public Long getAge() {
      return age;
    }

    public void setAge(Long age) {
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }
  }
}
