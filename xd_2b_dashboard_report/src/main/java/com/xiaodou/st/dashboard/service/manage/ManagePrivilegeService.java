package com.xiaodou.st.dashboard.service.manage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.admin.PrivilegeDO;
import com.xiaodou.st.dashboard.domain.admin.PrivilegeTree;
import com.xiaodou.st.dashboard.domain.admin.RoleDO;
import com.xiaodou.st.dashboard.domain.admin.RolePrivilegeDO;
import com.xiaodou.st.dashboard.domain.admin.TreeNode;
import com.xiaodou.st.dashboard.service.admin.AdminService;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.util.tree.TreeUtils;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class ManagePrivilegeService {

  @Resource
  private AdminService adminService;

  @Resource
  private ManageUnitService manageUnitService;

  @Resource
  IStServiceFacade stServiceFacade;

  /**
   * 获取角色的所有权限
   * 
   * @param roleId
   * @return
   */
  public List<PrivilegeDO> queryAllRolePrivilege(Integer roleId) {

    // if(Constants.MANAGE_UNIT_ROLE.equals(roleId)){
    // return this.queryAllPrivileges();
    // }

    // 获取角色的权限列表
    Map<String, Object> cond = new HashMap<>();
    cond.put("roleId", roleId);
    Page<RolePrivilegeDO> pageResult =
        stServiceFacade.listManageRolePrivilege(cond, CommUtil.getAllField(RolePrivilegeDO.class));
    List<Integer> rolePrivilegeIds = new ArrayList<>();
    for (RolePrivilegeDO rolePrivilege : pageResult.getResult()) {
      rolePrivilegeIds.add(rolePrivilege.getPrivilegeId());
    }

    List<PrivilegeDO> rolePrivileges = new ArrayList<>();
    if (null !=rolePrivilegeIds && !rolePrivilegeIds.isEmpty()) {
      rolePrivileges = this.queryPrivilegeByIds(rolePrivilegeIds);
    }

    return rolePrivileges;
  }

  /**
   * 生成多选的列表
   * 
   * @param selectIds
   * @return
   */
  public String getPrivilegeTable(List<String> selectIds, List<Integer> privilegeIds) {
    List<PrivilegeDO> privileges = this.queryAllPrivileges();
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (PrivilegeDO privilege : privileges) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(privilege.getId().toString());
      treeNode.setName(privilege.getName());
      treeNode.setParentId(privilege.getParentId().toString());
      treeNode.setLevel(privilege.getLevel());
      treeNode.setData(privilege);
      if (privilegeIds.contains(privilege.getId())) {
        treeNode.setExtData("disabled='disabled'");
      } else {
        treeNode.setExtData("");
      }
      treeNodeList.add(treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeList);
    String templateStr =
        "<tr id=\"node-${node.id}\" data-tt-id=\"${node.id}\" data-tt-parent-id=\"${node.parentId}\">"
            + "<td style=\"padding-left:30px;\">"
            + "${spacer}"
            + "<input type=\"checkbox\" preChecked=\"${checked}\" id=\"${node.id}\" name=\"menuid\" value=\"${node.id}\" level=\"${node.level}\" ${checked} ${node.extData} onclick='javascript:checknode(this);' /> "
            + "${node.name}" + "</td>" + "</tr>";
    return treeUtils.getTreeMulti("0", templateStr, selectIds, null, null);
  }

  /**
   * 根据账号获取所有权限
   * 
   * @param adminId
   * @return
   */
  private Map<Integer, List<PrivilegeDO>> queryAllPrivilegeByRoleId(Integer roleId) {

    List<PrivilegeDO> privileges = new ArrayList<>();

    Map<String, Object> cond = new HashMap<>();
    cond.put("roleId", roleId);
    Page<RolePrivilegeDO> rolePrivilegePage =
        stServiceFacade.listManageRolePrivilege(cond, CommUtil.getAllField(RolePrivilegeDO.class));
    Map<Integer, RolePrivilegeDO> privilegeMap = new HashMap<>();
    List<Integer> privilegeIds = new ArrayList<>();
    for (RolePrivilegeDO rolePrivilege : rolePrivilegePage.getResult()) {
      privilegeIds.add(rolePrivilege.getPrivilegeId());
      if (!privilegeMap.containsKey(rolePrivilege.getPrivilegeId())) {
        privilegeMap.put(rolePrivilege.getPrivilegeId(), rolePrivilege);
        privilegeIds.add(rolePrivilege.getPrivilegeId());
      }
    }
    if (null != privilegeIds && !privilegeIds.isEmpty()) {
      privileges = this.queryShowPrivilegeByIds(privilegeIds);
    }

    Map<Integer, List<PrivilegeDO>> parentMap = new HashMap<>();
    if (null != privileges && !privileges.isEmpty()) for (PrivilegeDO privilege : privileges) {
      List<PrivilegeDO> childList = null;
      if (parentMap.containsKey(privilege.getParentId())) {
        childList = parentMap.get(privilege.getParentId());
      } else {
        childList = new ArrayList<>();
      }
      childList.add(privilege);
      parentMap.put(privilege.getParentId(), childList);
    }
    return parentMap;
  }
  
  /**
   * 查询某角色的全部权限
   *
   * @param roleId
   * @return
   */
  public List<RolePrivilegeDO> queryRolePrivileges(Integer roleId) {
    Map<String, Object> con = new HashMap<String, Object>();
    con.put("roleId", roleId);
    Page<RolePrivilegeDO> page = stServiceFacade.listManageRolePrivilege(con, CommUtil.getAllField(RolePrivilegeDO.class));
    if(null != page) return page.getResult();
    return null;
  }    

  /**
   * 构建Tree
   * 
   * @param parentMap
   * @param pri
   * @return
   */
  public PrivilegeTree buildPrivilegeTree(Map<Integer, List<PrivilegeDO>> parentMap, PrivilegeDO pri) {
    PrivilegeTree privilegeTree = new PrivilegeTree(pri.getId().toString(), pri.getName(),
      pri.getIcon());
    List<PrivilegeTree> nodeList = new ArrayList<>();
    if (parentMap.containsKey(pri.getId())) {
      for (PrivilegeDO privilege : parentMap.get(pri.getId())) {
        nodeList.add(this.buildPrivilegeTree(parentMap, privilege));
      }
      privilegeTree.setChildren(nodeList);
    }else{
      privilegeTree.setUrl(pri.getUrl());
      privilegeTree.setTargetType(Constants.TARGETTYPE);
      if(StringUtils.isNotBlank(pri.getUrl()) && pri.getUrl().contains("data-room")){
        privilegeTree.setTargetType("blank");
      }
    }
    return privilegeTree;
  }

  /**
   * 菜单
   * 
   * @param adminId
   * @return
   */
  public List<PrivilegeTree> buildMenu(Integer roleId) {
    List<PrivilegeTree> result = new ArrayList<>();
    PrivilegeTree tree = new PrivilegeTree();
    tree.setId("10010");
    tree.setText("我的工作台");
    tree.setIsHeader(true);
    result.add(tree);
    Map<Integer, List<PrivilegeDO>> privilegeMap = this.queryAllPrivilegeByRoleId(roleId);
    List<PrivilegeDO> privileges = privilegeMap.get(0);
    if (null == privileges || privileges.size() == 0) return result;
    for (PrivilegeDO privilege : privileges) {
      result.add(this.buildPrivilegeTree(privilegeMap, privilege));
    }
    return result;
  }

  /**
   * 获取权限的select树结构
   * 
   * @return
   */
  public String getSelectPrivilegeTree(String selectId) {
    List<PrivilegeDO> privileges = this.queryAllPrivileges();
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (PrivilegeDO privilege : privileges) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(privilege.getId().toString());
      treeNode.setName(privilege.getName());
      treeNode.setParentId(privilege.getParentId().toString());
      treeNodeList.add(treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeList);
    String templateStr = "<option value=\"${node.id}\" ${selected}>${spacer}${node.name}</option>";
    return treeUtils.getTree("0", templateStr, selectId, null, null);
  }

  /**
   * 获取权限的table树结构
   * 
   * @param
   * @return
   */
  public String getTablePrivilegeTree() {
    List<PrivilegeDO> privileges = this.queryAllPrivileges();
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (PrivilegeDO privilege : privileges) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(privilege.getId().toString());
      treeNode.setName(privilege.getName());
      treeNode.setParentId(privilege.getParentId().toString());
      treeNode.setSortOrder(privilege.getSortOrder());
      treeNode.setDisplay(privilege.getDisplay());
      treeNode.setIcon(privilege.getIcon());
      treeNode.setUrl(privilege.getUrl());
      treeNodeList.add(treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeList);
    String templateStr =
        "<tr data-tt-id=\"${node.id}\" data-tt-parent-id=\"${node.parentId}\">"
            + "<td>"
            + "[${node.id}]${node.name}"
            + "</td>"
            + "<td>"
            + "<input name=\"sortOrder\" type=\"input\"  style=\"width:50px; text-align:center;height:25px;line-height:25px;\" id=\"${node.id}\" prevalue=\"${node.sortOrder}\" value=\"${node.sortOrder}\" />"
            + "</td>"
            + "<td>"
            + "#if($node.display==1) <span>是</span> #else <span style='color:red;'>否</span> #end"
            + "</td>"
            + "<td>"
            + "<i class=\"${node.icon}\">&nbsp;&nbsp;${node.icon}</i>"
            + "</td>"
            + "<td>"
            + "<a class=\"tablelink\" data-toggle=\"modal\" data-target=\"#saveNode\" data-sid=\"${node.id}\">添加子权限</a>"
            + "<a style='padding:5px;'>|</a>"
            + "<a class=\"tablelink\" data-toggle=\"modal\" data-target=\"#update\" data-sid=\"${node.id}\" data-display=\"${node.display}\" data-name=\"${node.name}\" data-url=\"${node.url}\" data-icon=\"${node.icon}\">修改</a>"
            + "<a style='padding:5px;'>|</a>"
            + "<a href=\"javascript:;\" class=\"tablelink\" onclick=\"removePrivilege(${node.id});\">删除</a>"
            + "</td>" + "</tr>";
    return treeUtils.getJqueryTableTree("0", templateStr);
  }

  /**
   * 生成多选的列表
   * 
   * @param selectIds
   * @return
   */
  public String getRolePrivilegeTable(List<String> selectIds) {
    List<PrivilegeDO> privileges = this.queryAllPrivileges();
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (PrivilegeDO privilege : privileges) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(privilege.getId().toString());
      treeNode.setName(privilege.getName());
      if (privilege.getParentId() != 0) {
        treeNode.setParentNode("class=\"child-of-node-" + privilege.getParentId() + "\"");
      } else {
        treeNode.setParentNode(" ");
      }
      treeNode.setParentId(privilege.getParentId().toString());
      treeNode.setLevel(privilege.getLevel());
      treeNodeList.add(treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeList);
    String templateStr =
        "<tr id=\"node-${node.id}\" data-tt-id=\"${node.id}\" data-tt-parent-id=\"${node.parentId}\" ${node.parentNode}>"
            + "<td style=\"padding-left:30px;\">"
            + "${spacer}"
            + "<input type=\"checkbox\" preChecked=\"${checked}\" id=\"${node.id}\" name=\"menuid\" value=\"${node.id}\" level=\"${node.level}\" ${checked} onclick='javascript:checknode(this);' /> "
            + "${node.name}" + "</td>" + "</tr>";
    return treeUtils.getTreeMulti("0", templateStr, selectIds, null, null);
  }

  
  public void doSetPrivilege(Integer roleId,String needDeleteId,String needAddId){
    if (StringUtils.isNotBlank(needDeleteId)) {
      String[] needDeleteIdArray = needDeleteId.split(";");
      if (needDeleteIdArray != null && needDeleteIdArray.length > 0) {
        List<Integer> needDeleteIdList = new ArrayList<>();
        for (String id : needDeleteIdArray) {
          needDeleteIdList.add(Integer.parseInt(id));
        }
        this.deleteRolePrivilege(roleId,needDeleteIdList);
      }
    }

    if (StringUtils.isNotBlank(needAddId)){
      String[] needAddIdArray = needAddId.split(";");
      if (needAddIdArray != null && needAddIdArray.length > 0) {
        List<Integer> needAddIdList = new ArrayList<>();
        for (String id : needAddIdArray) {
          needAddIdList.add(Integer.parseInt(id));
        }
        this.addRolePrivilege(roleId,needAddIdList);
      }
    }
  }
  
  /**
   * 新增权限
   * 
   * @param adminPrivilege
   * @return
   */
  public Boolean addPrivilege(PrivilegeDO privilegeDO) {
    privilegeDO.setSortOrder(0);
    boolean flag = stServiceFacade.savePrivilege(privilegeDO);
    this.cleanTree();
    return flag;
  }


  /**
   * 删除权限
   * 
   * @param privilegeId 权限号
   * @return
   */
  public Boolean deletePrivilege(Integer privilegeId) {
    // 删除本权限
    boolean result = stServiceFacade.removePrivilege(privilegeId);
    this.deletePrivilegeRole(privilegeId);
    this.cleanTree();
    return result;
  }

  /**
   * 按照权限id批量删除权限
   * 
   * @param ids
   * @return
   */
  private Boolean batchDeletePrivilegeByIds(List<Integer> privilegeIdList) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("privilegeIdList", privilegeIdList);
    stServiceFacade.removePrivilegeByCond(cond);
    Map<String, Object> deleteCond = new HashMap<>();
    deleteCond.put("idList", privilegeIdList);
    return stServiceFacade.removePrivilegeByCond(deleteCond);
  }

  /**
   * 根据idList查找权限
   * 
   * @param ids
   * @return
   */
  public List<PrivilegeDO> queryPrivilegeByIds(List<Integer> idList) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("idList", idList);
    Page<PrivilegeDO> pageResult =
        stServiceFacade.listManagePrivilege(cond, CommUtil.getAllField(PrivilegeDO.class));
    return pageResult.getResult();
  }

  /**
   * 根据idList查找可显示的权限
   * 
   * @param ids
   * @return
   */
  public List<PrivilegeDO> queryShowPrivilegeByIds(List<Integer> idList) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("idList", idList);
    cond.put("display", Constants.PRIVILEGE_SHOW);
    Page<PrivilegeDO> pageResult =
        stServiceFacade.listManagePrivilege(cond, CommUtil.getAllField(PrivilegeDO.class));
    return pageResult.getResult();
  }

  /**
   * 根据Url查询
   * 
   * @param url
   * @return
   */
  public PrivilegeDO queryPrivilegesByUrl(String url) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("url", url);
    Page<PrivilegeDO> pageResult =
        stServiceFacade.listManagePrivilege(cond, CommUtil.getAllField(PrivilegeDO.class));

    if (null == pageResult || null ==pageResult.getResult() || pageResult.getResult().isEmpty()) {
      return null;
    }
    return pageResult.getResult().get(0);
  }

  /**
   * 获取全部权限
   * 
   * @return
   */
  public List<PrivilegeDO> queryAllPrivileges() {
    Map<String, Object> cond = new HashMap<String, Object>();
    Page<PrivilegeDO> pageResult =
        stServiceFacade.listManagePrivilege(cond, CommUtil.getAllField(PrivilegeDO.class));
    return pageResult.getResult();
  }

  /**
   * 获取全部可见权限
   * 
   * @return
   */
  public List<PrivilegeDO> queryAllDisplayPrivileges() {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("display", Constants.PRIVILEGE_SHOW);
    Page<PrivilegeDO> pageResult =
        stServiceFacade.listManagePrivilege(cond, CommUtil.getAllField(PrivilegeDO.class));
    return pageResult.getResult();
  }

  /**
   * 根据权限号查询
   * 
   * @param privilegeId
   * @return
   */
  public PrivilegeDO findPrivilegeById(Integer privilegeId) {
    PrivilegeDO privilege = new PrivilegeDO();
    privilege.setId(privilegeId);
    return stServiceFacade.getPrivilegeById(privilegeId);
  }

  /**
   * 更新权限信息
   * 
   * @param privilege
   * @return
   */
  public Boolean updatePrivilegeById(PrivilegeDO privilege) {
    stServiceFacade.updatePrivilegeById(privilege);
    if (privilege.getParentId() != null) {
      return this.cleanTree();
    } else {
      return true;
    }
  }
  
  /**
   * 添加角色权限
   * 
   * @param roleId
   * @param priIds
   */
  public boolean addRolePrivilege(Integer roleId, List<Integer> priIds) {
    boolean flag = false;
    for (Integer priId : priIds) {
      RolePrivilegeDO rolePrivilege = new RolePrivilegeDO();
      rolePrivilege.setRoleId(roleId);
      rolePrivilege.setPrivilegeId(priId);
      flag = stServiceFacade.saveRolePrivilege(rolePrivilege);
      if(!flag) break;
    }
    return flag;
  }

  /**
   * 删除角色权限
   * 
   * @param roleId
   * @param priIds
   * @return
   */
  public Boolean deleteRolePrivilege(Integer roleId, List<Integer> privilegeIdList) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("roleId", roleId);
    cond.put("privilegeIdList", privilegeIdList);
    return stServiceFacade.removeRolePrivilegeByCond(cond);
  }

  /**
   * 删除掉某权限的全部关系
   * 
   * @param priId
   */
  public void deletePrivilegeRole(Integer privilegeId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("privilegeId", privilegeId);
    stServiceFacade.removeRolePrivilegeByCond(cond);
  }

  /**
   * 查询某权限的全部角色
   * 
   * @param priId
   * @return
   */
  public List<RoleDO> queryRolesByPrivilegeId(Integer privilegeId) {
    Map<String, Object> inputs = new HashMap<String, Object>();
    List<RoleDO> roles = new ArrayList<RoleDO>();
    inputs.put("privilegeId", privilegeId);
    Page<RolePrivilegeDO> rolePrivilegePage =
        stServiceFacade
            .listManageRolePrivilege(inputs, CommUtil.getAllField(RolePrivilegeDO.class));
    if (null != rolePrivilegePage && rolePrivilegePage.getResult().isEmpty())
      for (RolePrivilegeDO rolePrivilege : rolePrivilegePage.getResult()) {
        roles.add(stServiceFacade.getRoleById(rolePrivilege.getRoleId()));
      }
    return roles;
  }

  /**
   * 清洗tree
   * 
   * @return
   */
  public Boolean cleanTree() {
    // 1.查询栏目列表
    List<PrivilegeDO> privilegeList = this.queryAllPrivileges();
    // 2.栏目ID
    Map<Integer, PrivilegeDO> idMap = new HashMap<>();
    for (PrivilegeDO privilege : privilegeList) {
      idMap.put(privilege.getId(), privilege);
    }
    // 3.父栏目ID
    Map<Integer, List<PrivilegeDO>> parentIdMap = new HashMap<>();
    for (PrivilegeDO privilege : privilegeList) {
      List<PrivilegeDO> adminPrivilegeList = null;
      if (parentIdMap.containsKey(privilege.getParentId())) {
        adminPrivilegeList = parentIdMap.get(privilege.getParentId());
      } else {
        adminPrivilegeList = new ArrayList<>();
      }
      adminPrivilegeList.add(privilege);
      parentIdMap.put(privilege.getParentId(), adminPrivilegeList);
    }
    // 4.更新记录
    List<Integer> needDeleteIds = new ArrayList<>();
    for (PrivilegeDO privilege : privilegeList) {
      List<PrivilegeDO> parentList = this.parentList(idMap, privilege.getId());
      Boolean needDelete = true;
      for (PrivilegeDO adminPrivilege : parentList) {
        if (adminPrivilege.getParentId() == 0) {
          needDelete = false;
          break;
        }
      }
      if (needDelete == true) {
        needDeleteIds.add(privilege.getId());
        continue;
      }
      List<PrivilegeDO> allChildList = this.allChildId(parentIdMap, privilege.getId());
      List<PrivilegeDO> childList = parentIdMap.get(privilege.getId());

      PrivilegeDO updateEntity = new PrivilegeDO();
      updateEntity.setId(privilege.getId());
      updateEntity.setChildId(this.idString(childList));
      updateEntity.setAllChildId(this.idString(allChildList));
      updateEntity.setLevel(parentList.size() - 1);
      updateEntity.setAllParentId(this.idString(parentList));
      if (childList != null && childList.size() > 0) {
        updateEntity.setIsLeaf(0);
      } else {
        updateEntity.setIsLeaf(1);
      }
      this.updatePrivilegeById(updateEntity);
    }
    // 5.删除记录
    if (needDeleteIds.size() > 0) {
      this.batchDeletePrivilegeByIds(needDeleteIds);
    }
    return true;
  }

  /**
   * 父节点
   * 
   * @param id
   * @param idMap
   * @return
   */
  private List<PrivilegeDO> parentList(Map<Integer, PrivilegeDO> idMap, Integer id) {
    PrivilegeDO privilege = idMap.get(id);
    List<PrivilegeDO> result = new ArrayList<>();
    if (privilege != null) {
      if (privilege.getParentId() != 0) {
        result.addAll(this.parentList(idMap, privilege.getParentId()));
      }
      result.add(privilege);
      return result;
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * 所有子节点
   * 
   * @param parentIdMap
   * @param id
   * @return
   */
  private List<PrivilegeDO> allChildId(Map<Integer, List<PrivilegeDO>> parentIdMap, Integer id) {
    List<PrivilegeDO> result = new ArrayList<>();
    List<PrivilegeDO> privilegeList = parentIdMap.get(id);
    if (privilegeList != null) {
      result.addAll(privilegeList);
      for (PrivilegeDO privilege : privilegeList) {
        result.addAll(this.allChildId(parentIdMap, privilege.getId()));
      }
    }
    return result;
  }

  /**
   * ID序列化
   * 
   * @param privilegeList
   * @return
   */
  private String idString(List<PrivilegeDO> privilegeList) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (null == privilegeList || privilegeList.isEmpty()) {
      return "";
    }
    for (PrivilegeDO privilege : privilegeList) {
      stringBuilder.append(privilege.getId() + ",");
    }
    return stringBuilder.toString();
  }

}
