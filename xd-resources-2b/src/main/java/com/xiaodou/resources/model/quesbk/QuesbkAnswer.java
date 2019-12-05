package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;


public class QuesbkAnswer extends BaseEntity implements Comparable<QuesbkAnswer> {
  private String id;

  private String optionType;

  private String selection;

  private String imgUrl;

  public String getOptionType() {
    return optionType;
  }

  public void setOptionType(String optionType) {
    this.optionType = optionType;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getId() {
    return id;
  }

  public String getSelection() {
    return selection;
  }

  public void setSelection(String selection) {
    this.selection = selection;
  }

  @Override
  public int compareTo(QuesbkAnswer o) {
    return this.getId().compareTo(o.getId());
  }

}
