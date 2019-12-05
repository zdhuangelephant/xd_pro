package com.xiaodou.st.dashboard.domain.admin;

import java.util.List;

import lombok.Data;

@Data
public class TreeNode {
  /**
   * id
   */
  private String id;

  /**
   * 父id
   */
  private String parentId;

  /**
   * 名称
   */
  private String name;

  /**
   * 数据
   */
  private Object data;

  /**
   * 是否为叶子结点
   */
  private Boolean isLeaf;

  /**
   * 层次
   */
  private Integer level;

  /**
   * 排序
   */
  private Integer listOrder;

  /**
   * 子节点
   */
  private List<TreeNode> childNodes;

  /**
   * 额外信息
   */
  private String extData;
  
  private Integer sortOrder;

  private String url;

  private Integer display;

  private String params;

  private String icon;

  private String parentNode;

}
