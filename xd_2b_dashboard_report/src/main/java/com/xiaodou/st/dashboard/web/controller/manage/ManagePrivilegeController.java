package com.xiaodou.st.dashboard.web.controller.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.st.dashboard.domain.admin.PrivilegeDO;
import com.xiaodou.st.dashboard.domain.admin.RolePrivilegeDO;
import com.xiaodou.st.dashboard.service.manage.ManagePrivilegeService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("managePrivilegeController")
@RequestMapping("/manage")
public class ManagePrivilegeController extends BaseController {

  @Resource
  ManagePrivilegeService managePrivilegeService;

  @RequestMapping("/privilege_list")
  public ModelAndView privilegeList() {
    ModelAndView modelAndView = new ModelAndView("/manage/privilege/privilegeList");
    modelAndView.addObject("adminUser", super.getAdminUser());
    modelAndView.addObject("tree", managePrivilegeService.getTablePrivilegeTree());
    return modelAndView;
  }
   
  @RequestMapping("/get_select_privilege_tree")
  @ResponseBody
  public String getSelectPrivilegeTree(Integer privilegeId,Integer type){
    if(null != privilegeId){
      if(privilegeId ==0){
        String tree =
            managePrivilegeService.getSelectPrivilegeTree("0");
        return tree;
      }
      if(type == 1){
        String tree =
            managePrivilegeService.getSelectPrivilegeTree(privilegeId.toString());
        return tree;
      }
      PrivilegeDO privilege = managePrivilegeService.findPrivilegeById(privilegeId);
      String tree =
          managePrivilegeService.getSelectPrivilegeTree(privilege.getParentId().toString());
      return tree;
    }
    return null;
  }
  
  /**
   * 排序
   * 
   * @param orders
   * @return
   */
  @RequestMapping("/sort_privilege")
  @ResponseBody()
  public String sortPrivilege(String orders) {
    boolean flag = false;
    String[] orderItems = orders.split(";");
    for (String orderItem : orderItems) {
      String[] split = orderItem.split(":");
      Integer id = Integer.parseInt(split[0]);
      Integer order = Integer.parseInt(split[1]);
      PrivilegeDO privilege = new PrivilegeDO();
      privilege.setId(id);
      privilege.setSortOrder(order);
      flag = managePrivilegeService.updatePrivilegeById(privilege);
      if(!flag) break;
    }
    return String.valueOf(flag);
  }

  @RequestMapping("/save_privilege")
  @ResponseBody()
  public String savePrivilege(PrivilegeDO privilegeDO) {
    boolean flag = managePrivilegeService.addPrivilege(privilegeDO);
    return String.valueOf(flag);
  }

  @RequestMapping("/update_privilege")
  @ResponseBody()
  public String updatePrivilege(PrivilegeDO privilege) {
    boolean flag = managePrivilegeService.updatePrivilegeById(privilege);
    return String.valueOf(flag);
  }

  @RequestMapping("/remove_privilege")
  @ResponseBody()
  public String removePrivilege(Integer privilegeId) {
    boolean flag = managePrivilegeService.deletePrivilege(privilegeId);
    return String.valueOf(flag);
  }
  
  /**
   * 设置角色权限
   *
   * @param roleId
   * @return
   */
  @RequestMapping("/get_role_privilege_table")
  @ResponseBody()
  public String getRolePrivilegeTable(Integer roleId) {
      List<RolePrivilegeDO> rolePrivileges = managePrivilegeService.queryRolePrivileges(roleId);
      List<String> ids = new ArrayList<String>();
      if(null != rolePrivileges && !rolePrivileges.isEmpty())for (RolePrivilegeDO rolePrivilege : rolePrivileges) {
        ids.add(rolePrivilege.getPrivilegeId().toString());
      }
      String table = managePrivilegeService.getRolePrivilegeTable(ids);
      return table;
  }
  
  /**
   * 设置权限
   * @param roleId
   * @param needDeleteId
   * @param needAddId
   * @return
   */
  @RequestMapping("set_privilege")
  @ResponseBody()
  public String doSetPrivilege(Integer roleId,String needDeleteId,String needAddId){
    managePrivilegeService.doSetPrivilege(roleId, needDeleteId, needAddId);
    return String.valueOf(true);
  }
  
  
}