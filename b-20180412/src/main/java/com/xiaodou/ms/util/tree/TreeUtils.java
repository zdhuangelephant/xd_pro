package com.xiaodou.ms.util.tree;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 14-9-6.
 */
public class TreeUtils {

  private Map<String,List<TreeNode>> parentListMap;

  /**
   * 构造方法
   * @param nodeList
   */
  public TreeUtils(List<TreeNode> nodeList){
    parentListMap = new HashMap<String,List<TreeNode>>();
    for (TreeNode node:nodeList){
      String key = node.getParentId();
      List<TreeNode> treeNodeList = null;
      if (parentListMap.containsKey(key)){
        treeNodeList = parentListMap.get(key);
      } else {
        treeNodeList = new ArrayList<>();
      }
      treeNodeList.add(node);
      parentListMap.put(key,treeNodeList);
    }
  }

  /**
   * 构造方法
   * @param nodeMap
   */
  public TreeUtils(Map<String, TreeNode> nodeMap) {
    parentListMap = new HashMap<String,List<TreeNode>>();
    for (String key:nodeMap.keySet()){
      TreeNode node = nodeMap.get(key);
      List<TreeNode> treeNodeList = null;
      if (parentListMap.containsKey(node.getParentId())){
        treeNodeList = parentListMap.get(node.getParentId());
      } else {
        treeNodeList = new ArrayList<>();
      }
      treeNodeList.add(node);
      parentListMap.put(node.getParentId(),treeNodeList);
    }
  }
  
  /**
   * 符号
   */
  private static String[] icons = {"│", "├", "└"};

  /**
   * 空格
   */
  private static String nbsp = "&nbsp;";

  /**
   * 得到子级数组
   *
   * @param id
   * @return
   */
  public List<TreeNode> getChild(String id) {
    List<TreeNode> nodeList = parentListMap.get(id);
    if (nodeList==null){
      return new ArrayList<>();
    } else {
      return nodeList;
    }
  }

  /**
   * 获取树状结构
   *
   * @param parentId
   * @param templateStr
   * @param selectedId
   * @param adds
   * @param templateStrGroup
   * @return
   */
  public String getTree(String parentId, String templateStr, String selectedId, String adds, String templateStrGroup) {
    if (adds == null) {
      adds = "";
    }
    //返回的结果
    String result = "";
    //当前节点在子节点的序号
    Integer number = 1;
    //获取所有子节点
    List<TreeNode> childs = this.getChild(parentId);
    if (childs.size() > 0) {
      //子节点数量
      Integer size = childs.size();
      //循环子节点
      for (TreeNode node : childs) {
        String tempId = node.getId();
        //节点前显示的符号"│","├","└"
        String sign = "";
        //节点前的空格
        String span = "";
        //如果是最后一个节点的话用└符号，其他的用├符号
        if (number.equals(size)) {
          sign = this.icons[2];
        } else {
          sign = this.icons[1];
          //span用│符号
          span = StringUtils.isNotBlank(adds) ? this.icons[0] : "";
        }
        //空格串
        String spacer = StringUtils.isNotBlank(adds) ? adds + sign : "";
        //被选中的
        String selected = tempId.equals(selectedId) ? "selected" : "";
        String nstr = "";
        Map<String, Object> replace = new HashMap<String, Object>();
        replace.put("node", node);
        replace.put("selected", selected);
        replace.put("spacer", spacer);
        //如果是父节点，并且有groupStr
        try {
          if (node.getParentId().equals(0) && templateStrGroup != null) {
            nstr = this.repalceStr(templateStrGroup, replace);
          } else {
            nstr = this.repalceStr(templateStr, replace);
          }
        } catch (Exception e) {
          e.printStackTrace();
          return "error";
        }
        result = result + nstr;
        result = result + this.getTree(tempId, templateStr, selectedId,
          adds + span + nbsp, templateStrGroup);
        number = number + 1;
      }
    }
    return result;
  }

  /**
   * 多选树状结构
   *
   * @param parentId
   * @param templateStr
   * @param selectedIds
   * @param adds
   * @param templateStrGroup
   * @return
   */
  public String getTreeMulti(String parentId, String templateStr, List<String> selectedIds, String adds, String templateStrGroup) {
    if (adds == null) {
      adds = "";
    }
    //返回的结果
    String result = "";
    //当前节点在子节点的序号
    Integer number = 1;
    //获取所有子节点
    List<TreeNode> childs = this.getChild(parentId);
    if (childs.size() > 0) {
      //子节点数量
      Integer size = childs.size();
      //循环子节点
      for (TreeNode node : childs) {
        String tempId = node.getId();
        //节点前显示的符号"│","├","└"
        String sign = "";
        //节点前的空格
        String span = "";
        //如果是最后一个节点的话用└符号，其他的用├符号
        if (number.equals(size)) {
          sign = this.icons[2];
        } else {
          sign = this.icons[1];
          //span用│符号
          span = StringUtils.isNotBlank(adds) ? this.icons[0] : "";
        }
        //空格串
        String spacer = StringUtils.isNotBlank(adds) ? adds + sign : "";
        //被选中的
        String selected = selectedIds.contains(tempId) ? "selected" : "";
        String checked = selectedIds.contains(tempId) ? "checked" : "";
        String nstr = "";
        Map<String, Object> replace = new HashMap<String, Object>();
        replace.put("node", node);
        replace.put("selected", selected);
        replace.put("spacer", spacer);
        replace.put("checked", checked);
        //如果是父节点，并且有groupStr
        try {
          if (node.getParentId().equals(0) && templateStrGroup != null) {
            nstr = this.repalceStr(templateStrGroup, replace);
          } else {
            nstr = this.repalceStr(templateStr, replace);
          }
        } catch (Exception e) {
          e.printStackTrace();
          return "error";
        }
        result = result + nstr;
        result = result + this.getTreeMulti(tempId, templateStr, selectedIds,
          adds + span + nbsp, templateStrGroup);
        number = number + 1;
      }
    }
    return result;
  }

  /**
   * 获取jqueryTree结构
   *
   * @param parentId
   * @param treeId
   * @param parentTemplateStr
   * @param childTemplateStr
   * @param isTop
   * @return
   */
  private String getJqueryTree(String parentId, String treeId, String parentTemplateStr, String childTemplateStr, String treeClass, Boolean isTop) {
    String result = "";
    //获取所有子节点
    List<TreeNode> childs = this.getChild(parentId);
    //看是否为top节点
    String treeEffectedId = "";
    String classNode = "";
    if (isTop) {
      treeEffectedId = "id='" + treeId + "'";
    }
    if (isTop) {
      classNode = "class='" + treeClass + "'";
    }
    result = result + "<ul " + treeEffectedId + " " + classNode + " >";
    for (TreeNode treeNode : childs) {
      String key = treeNode.getId();
      List<TreeNode> nodes = this.getChild(treeNode.getId());
      Map<String, Object> replace = new HashMap<String, Object>();
      replace.put("node", treeNode);
      result = result + "<li>";
      try {
        if (nodes.size() <= 0) {
          result = result + this.repalceStr(childTemplateStr, replace);
        } else {
          result = result + this.repalceStr(parentTemplateStr, replace);
          result = result
            + this.getJqueryTree(treeNode.getId(), treeId, parentTemplateStr, childTemplateStr, treeClass, false);
        }
      } catch (Exception e) {
        e.printStackTrace();
        return "error";
      }
      result = result + "</li>";
    }
    result = result + "</ul>";
    return result;
  }

  /**
   * 获取jueryTree 结构
   *
   * @param parentId
   * @param treeId
   * @param parentTemplateStr
   * @param childTemplateStr
   * @return
   */
  public String getJqueryTree(String parentId, String treeId, String parentTemplateStr, String childTemplateStr, String treeClass) {
    return this.getJqueryTree(parentId, treeId, parentTemplateStr, childTemplateStr, treeClass, true);
  }

  /**
   * 获取jqueryTableTree
   * @param parentId
   * @param templateStr
   * @return
   */
  public String getJqueryTableTree(String parentId,String templateStr){
    StringBuilder result = new StringBuilder("");

    List<TreeNode> allChild = this.getChild(parentId);
    for (TreeNode node:allChild){
      Map<String, Object> replace = new HashMap<String, Object>();
      replace.put("node", node);
      List<TreeNode> nodes = this.getChild(node.getId());
      try {
        if (nodes.size()>0){
          result.append(this.repalceStr(templateStr,replace));
          result.append(this.getJqueryTableTree(node.getId(),templateStr));
        } else {
          result.append(this.repalceStr(templateStr,replace));
        }
      } catch (Exception e){
        return "error";
      }
    }
    return result.toString();
  }

  /**
   * 获取jqueryTableTree
   * @param parentId
   * @param parentTemplateStr
   * @param childTemplateStr
   * @return
   */
  public String getJqueryTableTree(String parentId,String parentTemplateStr, String childTemplateStr){
    StringBuilder result = new StringBuilder("");

    List<TreeNode> allChild = this.getChild(parentId);
    for (TreeNode node:allChild){
      Map<String, Object> replace = new HashMap<String, Object>();
      replace.put("node", node);
      List<TreeNode> nodes = this.getChild(node.getId());
      try {
        if (nodes.size()>0){
          result.append(this.repalceStr(parentTemplateStr,replace));
          result.append(this.getJqueryTableTree(node.getId(),parentTemplateStr,childTemplateStr));
        } else {
          result.append(this.repalceStr(childTemplateStr,replace));
        }
      } catch (Exception e){
        return "error";
      }
    }
    return result.toString();
  }


  /**
   * 字符串变量替换
   *
   * @param content
   * @return
   */
  public String repalceStr(String content, Map<String, Object> replace) throws Exception {
    VelocityEngine ve = new VelocityEngine();
    ve.init();
    // 取得velocity的上下文context
    VelocityContext context = new VelocityContext();
    // 把数据填入上下文
    for (String key : replace.keySet()) {
      context.put(key, replace.get(key));
    }
    // 输出流
    StringWriter writer = new StringWriter();
    // 转换输出
    ve.evaluate(context, writer, "", content);
    return writer.toString();
  }
}
