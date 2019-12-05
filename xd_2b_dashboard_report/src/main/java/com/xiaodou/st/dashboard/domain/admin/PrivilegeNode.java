package com.xiaodou.st.dashboard.domain.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PrivilegeNode extends TreeNode {

  private Integer sortOrder;

  private String url;

  private Integer display;

  private String params;

  private String icon;

  private Integer level;

  private String parentNode;

}
