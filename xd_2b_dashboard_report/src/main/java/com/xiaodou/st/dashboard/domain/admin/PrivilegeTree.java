package com.xiaodou.st.dashboard.domain.admin;

import java.util.List;

import lombok.Data;

@Data
public class PrivilegeTree {

  private String id;
  private String text;
  private String icon;
  
  private String url;
  private String targetType;
  private List<PrivilegeTree> children = null;
  
  private Boolean isHeader;
  
  public PrivilegeTree(String id, String text, String icon) {
    super();
    this.id = id;
    this.text = text;
    this.icon = icon;
  }

  public PrivilegeTree() {
  }

}
