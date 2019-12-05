package com.xiaodou.common.city.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;


/**
 * 城市信息
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-13
 */
public class City {

  private Integer id;

  private String name;

  private String uniqueId;

  private String code;

  private String fullPinyin;

  private String shortPinyin;

  private String aliasName;

  private String aliasFullPinyin;

  private String aliasShortPinyin;

  private Integer parentId;

  private Short locLevel;

  private List<City> subCity = Lists.newArrayList();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getFullPinyin() {
    return fullPinyin;
  }

  public void setFullPinyin(String fullPinyin) {
    this.fullPinyin = fullPinyin;
  }

  public String getShortPinyin() {
    return shortPinyin;
  }

  public void setShortPinyin(String shortPinyin) {
    this.shortPinyin = shortPinyin;
  }

  public String getAliasName() {
    return aliasName;
  }

  public void setAliasName(String aliasName) {
    this.aliasName = aliasName;
  }

  public String getAliasFullPinyin() {
    return aliasFullPinyin;
  }

  public void setAliasFullPinyin(String aliasFullPinyin) {
    this.aliasFullPinyin = aliasFullPinyin;
  }

  public String getAliasShortPinyin() {
    return aliasShortPinyin;
  }

  public void setAliasShortPinyin(String aliasShortPinyin) {
    this.aliasShortPinyin = aliasShortPinyin;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Short getLocLevel() {
    return locLevel;
  }

  public void setLocLevel(Short locLevel) {
    this.locLevel = locLevel;
  }

  public List<City> getSubCity() {
    return subCity;
  }

  public void setSubCity(List<City> subCity) {
    this.subCity = subCity;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

}
