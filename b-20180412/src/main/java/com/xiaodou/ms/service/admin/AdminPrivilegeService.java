package com.xiaodou.ms.service.admin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaodou.ms.dao.admin.AdminPrivilegeDao;
import com.xiaodou.ms.dao.admin.PrivilegeDao;
import com.xiaodou.ms.dao.admin.RolePrivilegeDao;
import com.xiaodou.ms.model.admin.AdminPrivilege;
import com.xiaodou.ms.model.admin.Privilege;
import com.xiaodou.ms.model.admin.Role;
import com.xiaodou.ms.model.admin.RolePrivilege;
import com.xiaodou.ms.util.tree.TreeNode;
import com.xiaodou.ms.util.tree.TreeUtils;
import com.xiaodou.ms.vo.PrivilegeTree;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 14-9-2.
 * <p/>
 * 权限service
 */
public class AdminPrivilegeService {

  /**
   * 权限DAO
   */
  private PrivilegeDao privilegeDao;

  /**
   * 角色权限DAO
   */
  private RolePrivilegeDao rolePrivilegeDao;

  /**
   * 管理员权限
   */
  private AdminPrivilegeDao adminPrivilegeDao;

  /**
   * 角色service
   */
  private AdminRoleService adminRoleService;

  /**
   * 管理员service
   */
  private AdminService adminService;

  public AdminPrivilegeDao getAdminPrivilegeDao() {
    return adminPrivilegeDao;
  }

  public void setAdminPrivilegeDao(AdminPrivilegeDao adminPrivilegeDao) {
    this.adminPrivilegeDao = adminPrivilegeDao;
  }

  public PrivilegeDao getPrivilegeDao() {
    return privilegeDao;
  }

  public void setPrivilegeDao(PrivilegeDao privilegeDao) {
    this.privilegeDao = privilegeDao;
  }

  public RolePrivilegeDao getRolePrivilegeDao() {
    return rolePrivilegeDao;
  }

  public void setRolePrivilegeDao(RolePrivilegeDao rolePrivilegeDao) {
    this.rolePrivilegeDao = rolePrivilegeDao;
  }

  public AdminRoleService getAdminRoleService() {
    return adminRoleService;
  }

  public void setAdminRoleService(AdminRoleService adminRoleService) {
    this.adminRoleService = adminRoleService;
  }

  public AdminService getAdminService() {
    return adminService;
  }

  public void setAdminService(AdminService adminService) {
    this.adminService = adminService;
  }

  /**
   * 获取管理员的所有权限
   * 
   * @param adminId
   * @return
   */
  public List<Privilege> queryAdminAllRolePrivilege(Integer adminId) {

	// 获取角色的权限列表
	List<Privilege> rolePrivileges = new ArrayList<>();  
	  
    // 获取所有角色
    List<Role> roles = adminService.queryAdminRoles(adminId);
    List<Integer> roleIds = new ArrayList<>();
    Boolean isSuperMan = false;
    for (Role role : roles) {
      roleIds.add(role.getId());
      if (role.getId().equals(1)) {
        isSuperMan = true;
        break;
      }
    }

    if (isSuperMan) {
      return this.queryAllPrivileges();
    }
    if (roleIds==null || roleIds.size()==0) {
		return rolePrivileges;//没有角色的时候返回null
	}
    
    Map<String, Object> cond = new HashMap<>();
    cond.put("roleIds", roleIds);
    Page<RolePrivilege> privilegeList =
        rolePrivilegeDao.queryListByCondWithOutPage(cond, new HashMap());
    List<Integer> rolePrivilegeIds = new ArrayList<>();
    for (RolePrivilege privilege : privilegeList.getResult()) {
      rolePrivilegeIds.add(privilege.getPrivilegeId());
    }
    if (rolePrivilegeIds.size() > 0) {
      rolePrivileges = this.queryPrivilegeByIds(rolePrivilegeIds);
    }

    return rolePrivileges;
  }

  /**
   * 个人权限
   * 
   * @param adminId
   * @return
   */
  public List<Privilege> queryAdminAllPersonPrivilege(Integer adminId) {
    // 获取个人权限
    List<Privilege> adminPrivileges = new ArrayList<>();
    Map<String, Object> queryCond = new HashMap<>();
    queryCond.put("adminId", adminId);
    Page<AdminPrivilege> adminPrivilegeList =
        adminPrivilegeDao.queryListByCondWithOutPage(queryCond, new HashMap());
    List<Integer> adminPrivilegeIds = new ArrayList<>();
    for (AdminPrivilege adminPrivilege : adminPrivilegeList.getResult()) {
      adminPrivilegeIds.add(adminPrivilege.getPrivilegeId());
    }
    if (adminPrivilegeIds.size() > 0) {
      adminPrivileges = this.queryPrivilegeByIds(adminPrivilegeIds);
    }
    return adminPrivileges;
  }

  /**
   * 生成多选的列表
   * 
   * @param selectIds
   * @return
   */
  public String getAdminPrivilegeTable(List<String> selectIds, List<Integer> rolePrivilegeIds) {
    List<Privilege> privileges = this.queryAllPrivileges();
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (Privilege privilege : privileges) {
      TreeNode treeNode = new PrivilegeNode();
      treeNode.setId(privilege.getId().toString());
      treeNode.setName(privilege.getName());
      treeNode.setParentId(privilege.getParentId().toString());
      treeNode.setLevel(privilege.getLevel());
      treeNode.setData(privilege);
      if ( rolePrivilegeIds!=null && rolePrivilegeIds.contains(privilege.getId()) ) {
        treeNode.setExtData("disabled='disabled'");//加入非空判断
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
  private Map<Integer, List<Privilege>> queryAllPrivilegeByAdmin(Integer adminId) {
    List<Role> roles = adminService.queryAdminRoles(adminId);
    List<Integer> roleIds = new ArrayList<>();
    Boolean isSuperAdmin = false;
    for (Role role : roles) {
      roleIds.add(role.getId());
      if (role.getId().equals(1)) {
        isSuperAdmin = true;
        break;
      }
    }

    List<Privilege> privileges = new ArrayList<>();
    if (isSuperAdmin) {
      privileges = this.queryAllDisplayPrivileges();
    } else {
      Map<String, Object> cond = new HashMap<>();
      Page<RolePrivilege> privilegeList = new Page<RolePrivilege>();
      if(null != roleIds && !roleIds.isEmpty()){
    	  cond.put("roleIds", roleIds);
    	  privilegeList = rolePrivilegeDao.queryListByCondWithOutPage(cond, new HashMap());
      }
      Map<Integer, RolePrivilege> privilegeMap = new HashMap<>();
      List<Integer> privilegeIds = new ArrayList<>();
      for (RolePrivilege privilege : privilegeList.getResult()) {
        if (!privilegeMap.containsKey(privilege.getPrivilegeId())) {
          privilegeMap.put(privilege.getPrivilegeId(), privilege);
          privilegeIds.add(privilege.getPrivilegeId());
        }
      }

      // 获取个人权限
      Map<String, Object> queryCond = new HashMap<>();
      queryCond.put("adminId", adminId);
      Page<AdminPrivilege> adminPrivilegeList =
          adminPrivilegeDao.queryListByCondWithOutPage(queryCond, new HashMap());
      List<Integer> adminPrivilegeIds = new ArrayList<>();
      for (AdminPrivilege adminPrivilege : adminPrivilegeList.getResult()) {
        adminPrivilegeIds.add(adminPrivilege.getPrivilegeId());
      }

      privilegeIds.addAll(adminPrivilegeIds);
      if (privilegeIds.size() > 0) {
        privileges = this.queryShowPrivilegeByIds(privilegeIds);
      }
    }

    Map<Integer, List<Privilege>> parentMap = new HashMap<>();
    for (Privilege privilege : privileges) {
      List<Privilege> childList = null;
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
   * 构建Tree
   * 
   * @param parentMap
   * @param pri
   * @return
   */
  public PrivilegeTree buildPrivilegeTree(Map<Integer, List<Privilege>> parentMap, Privilege pri) {
    PrivilegeTree privilegeTree = new PrivilegeTree();
    privilegeTree.setAdminPrivilege(pri);
    List<PrivilegeTree> nodeList = new ArrayList<>();
    if (parentMap.containsKey(pri.getId())) {
      for (Privilege privilege : parentMap.get(pri.getId())) {
        nodeList.add(this.buildPrivilegeTree(parentMap, privilege));
      }
      privilegeTree.setNodeList(nodeList);
    }
    return privilegeTree;
  }

  /**
   * 菜单
   * 
   * @param adminId
   * @return
   */
  public List<PrivilegeTree> buildMenu(Integer adminId) {
    Map<Integer, List<Privilege>> privilegeMap = this.queryAllPrivilegeByAdmin(adminId);
    List<Privilege> privileges = privilegeMap.get(0);
    List<PrivilegeTree> result = new ArrayList<>();
    if (null == privileges || privileges.size() == 0) return result;
    for (Privilege privilege : privileges) {
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
    List<Privilege> privileges = this.queryAllPrivileges();
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (Privilege privilege : privileges) {
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
    List<Privilege> privileges = this.queryAllPrivileges();
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (Privilege privilege : privileges) {
      PrivilegeNode treeNode = new PrivilegeNode();
      treeNode.setId(privilege.getId().toString());
      treeNode.setName(privilege.getName());
      treeNode.setParentId(privilege.getParentId().toString());
      treeNode.setSortOrder(privilege.getSortOrder());
      treeNode.setDisplay(privilege.getDisplay());
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
            + "#if($node.display==1) 是 #else <span style='color:red;'>否</span> #end"
            + "</td>"
            + "<td>"
            + "<a href=\"/privilege/addPrivilege?parentId=${node.id}\" class=\"tablelink\">添加子权限</a>"
            + "<a style='padding:5px;'>|</a>"
            + "<a href=\"/privilege/editPrivilege?id=${node.id}\" class=\"tablelink\">修改</a>"
            + "<a style='padding:5px;'>|</a>"
            + "<a href=\"/privilege/deletePrivilege?priId=${node.id}\" class=\"tablelink\">删除</a>"
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
    List<Privilege> privileges = this.queryAllPrivileges();
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (Privilege privilege : privileges) {
      PrivilegeNode treeNode = new PrivilegeNode();
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

  /**
   * 新增权限
   * 
   * @param adminPrivilege
   * @return
   */
  public Privilege addPrivilege(Privilege adminPrivilege) {
    adminPrivilege.setSortOrder(0);
    Privilege privilege = privilegeDao.addEntity(adminPrivilege);
    this.cleanTree();
    return privilege;
  }

  /**
   * 添加管理员权限
   * 
   * @param adminId
   * @param priIds
   */
  public void addAdminPrivilege(Integer adminId, List<Integer> priIds) {
    for (Integer priId : priIds) {
      AdminPrivilege adminPrivilege = new AdminPrivilege();
      adminPrivilege.setAdminId(adminId);
      adminPrivilege.setPrivilegeId(priId);
      adminPrivilegeDao.addEntity(adminPrivilege);
    }
  }

  /**
   * 删除管理员权限
   * 
   * @param adminId
   * @param priIds
   * @return
   */
  public Boolean deleteAdminPrivilege(Integer adminId, List<Integer> priIds) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("adminId", adminId);
    cond.put("privilegeIds", priIds);
    return adminPrivilegeDao.deleteEntity(cond);
  }

  /**
   * 添加角色权限
   * 
   * @param roleId
   * @param priIds
   */
  public void addRolePrivilege(Integer roleId, List<Integer> priIds) {
    for (Integer priId : priIds) {
      RolePrivilege rolePrivilege = new RolePrivilege();
      rolePrivilege.setRoleId(roleId);
      rolePrivilege.setPrivilegeId(priId);
      rolePrivilegeDao.addEntity(rolePrivilege);
    }
  }

  /**
   * 删除角色权限
   * 
   * @param roleId
   * @param priIds
   * @return
   */
  public Boolean deleteRolePrivilege(Integer roleId, List<Integer> priIds) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("roleId", roleId);
    cond.put("privilegeIds", priIds);
    return rolePrivilegeDao.deleteEntity(cond);
  }

  /**
   * 删除权限
   * 
   * @param privilegeId 权限号
   * @return
   */
  public Boolean deletePrivilege(Integer privilegeId) {
    // 删除本权限
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", privilegeId);
    boolean result = privilegeDao.deleteEntity(cond);
    this.deletePrivilegeRole(privilegeId);
    this.cleanTree();
    return result;
  }

  /**
   * 按照权限号批量删除权限
   * 
   * @param ids
   * @return
   */
  private Boolean batchDeletePrivilegeByIds(List<Integer> ids) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("privilegeIds", ids);
    rolePrivilegeDao.deleteEntity(cond);
    Map<String, Object> deleteCond = new HashMap<>();
    deleteCond.put("ids", ids);
    return privilegeDao.deleteEntity(deleteCond);
  }

  /**
   * 根据Id查找权限
   * 
   * @param ids
   * @return
   */
  public List<Privilege> queryPrivilegeByIds(List<Integer> ids) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("ids", ids);
    Page<Privilege> adminPrivilegePage =
        privilegeDao.queryListByCondWithOutPage(cond, new HashMap());
    return adminPrivilegePage.getResult();
  }

  /**
   * 查询所有可显示
   * 
   * @param ids
   * @return
   */
  public List<Privilege> queryShowPrivilegeByIds(List<Integer> ids) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("ids", ids);
    cond.put("display", Privilege.show);
    Page<Privilege> adminPrivilegePage =
        privilegeDao.queryListByCondWithOutPage(cond, new HashMap());
    return adminPrivilegePage.getResult();
  }

  /**
   * 根据Url查询
   * 
   * @param url
   * @return
   */
  public Privilege queryPrivilegesByUrl(String url) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("url", url);
    Page<Privilege> result = privilegeDao.queryListByCondWithOutPage(cond, new HashMap());
    if (result.getResult().size() == 0) {
      return null;
    } else {
      return result.getResult().get(0);
    }
  }

  /**
   * 获取全部权限
   * 
   * @return
   */
  public List<Privilege> queryAllPrivileges() {
    Map<String, Object> cond = new HashMap<String, Object>();
    Page<Privilege> result = this.privilegeDao.queryListByCondWithOutPage(cond, new HashMap());
    return result.getResult();
  }

  /**
   * 获取全部可见权限
   * 
   * @return
   */
  public List<Privilege> queryAllDisplayPrivileges() {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("display", Privilege.show);
    Page<Privilege> result = this.privilegeDao.queryListByCondWithOutPage(cond, new HashMap());
    return result.getResult();
  }

  /**
   * 根据权限号查询
   * 
   * @param privilegeId
   * @return
   */
  public Privilege findPrivilegeById(Integer privilegeId) {
    Privilege privilege = new Privilege();
    privilege.setId(privilegeId);
    return privilegeDao.findEntityById(privilege);
  }

  /**
   * 更新权限信息
   * 
   * @param privilege
   * @return
   */
  public Boolean updatePrivilege(Privilege privilege) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", privilege.getId());
    privilegeDao.updateEntity(cond, privilege);
    if (privilege.getParentId() != null) {
      return this.cleanTree();
    } else {
      return true;
    }
  }

  /**
   * 删除掉某权限的全部关系
   * 
   * @param priId
   */
  public void deletePrivilegeRole(Integer priId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", priId);
    this.rolePrivilegeDao.deleteEntity(cond);
  }

  /**
   * 查询某权限的全部角色
   * 
   * @param priId
   * @return
   */
  public List<Role> queryRolesByPrivilege(Integer priId) {
    Map<String, Object> con = new HashMap<String, Object>();
    List<Role> roles = new ArrayList<Role>();
    con.put("privilegeId", priId);
    Page<RolePrivilege> AdminRolePrivileges =
        this.rolePrivilegeDao.queryListByCondWithOutPage(con, new HashMap());
    for (RolePrivilege rolePrivilege : AdminRolePrivileges.getResult()) {
      roles.add(adminRoleService.findRoleById(rolePrivilege.getRoleId()));
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
    List<Privilege> privilegeList = this.queryAllPrivileges();
    // 2.栏目ID
    Map<Integer, Privilege> idMap = new HashMap<>();
    for (Privilege privilege : privilegeList) {
      idMap.put(privilege.getId(), privilege);
    }
    // 3.父栏目ID
    Map<Integer, List<Privilege>> parentIdMap = new HashMap<>();
    for (Privilege privilege : privilegeList) {
      List<Privilege> adminPrivilegeList = null;
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
    for (Privilege privilege : privilegeList) {
      List<Privilege> parentList = this.parentList(idMap, privilege.getId());
      Boolean needDelete = true;
      for (Privilege adminPrivilege : parentList) {
        if (adminPrivilege.getParentId() == 0) {
          needDelete = false;
          break;
        }
      }
      if (needDelete == true) {
        needDeleteIds.add(privilege.getId());
        continue;
      }
      List<Privilege> allChildList = this.allChildId(parentIdMap, privilege.getId());
      List<Privilege> childList = parentIdMap.get(privilege.getId());

      Privilege updateEntity = new Privilege();
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
      this.updatePrivilege(updateEntity);
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
  private List<Privilege> parentList(Map<Integer, Privilege> idMap, Integer id) {
    Privilege privilege = idMap.get(id);
    List<Privilege> result = new ArrayList<>();
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
  private List<Privilege> allChildId(Map<Integer, List<Privilege>> parentIdMap, Integer id) {
    List<Privilege> result = new ArrayList<>();
    List<Privilege> privilegeList = parentIdMap.get(id);
    if (privilegeList != null) {
      result.addAll(privilegeList);
      for (Privilege privilege : privilegeList) {
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
  private String idString(List<Privilege> privilegeList) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (privilegeList == null) {
      return "";
    }
    for (Privilege privilege : privilegeList) {
      stringBuilder.append(privilege.getId() + ",");
    }
    return stringBuilder.toString();
  }

}
