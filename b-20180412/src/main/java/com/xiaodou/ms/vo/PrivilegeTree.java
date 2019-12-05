package com.xiaodou.ms.vo;

import com.xiaodou.ms.model.admin.Privilege;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyp on 15/8/25.
 */
public class PrivilegeTree {

  private Privilege adminPrivilege;

  private List<PrivilegeTree> nodeList = new ArrayList<>();

  public Privilege getAdminPrivilege() {
    return adminPrivilege;
  }

  public void setAdminPrivilege(Privilege adminPrivilege) {
    this.adminPrivilege = adminPrivilege;
  }

  public List<PrivilegeTree> getNodeList() {
    return nodeList;
  }

  public void setNodeList(List<PrivilegeTree> nodeList) {
    this.nodeList = nodeList;
  }
}
