package com.xiaodou.ms.web.controller.admin;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.model.admin.*;
import com.xiaodou.ms.service.admin.AdminPrivilegeService;
import com.xiaodou.ms.service.admin.AdminRoleService;
import com.xiaodou.ms.service.admin.AdminService;
import com.xiaodou.ms.service.admin.PrivilegeNode;
import com.xiaodou.ms.util.tree.TreeNode;
import com.xiaodou.ms.util.tree.TreeUtils;
import com.xiaodou.ms.web.request.admin.EditRoleRequest;
import com.xiaodou.ms.web.request.admin.NewRoleRequest;
import com.xiaodou.ms.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 14-9-1.
 */
@Controller("roleController")
@RequestMapping("/role")
public class RoleController extends BaseController {

  @Resource
  AdminRoleService adminRoleService;

  @Resource
  AdminPrivilegeService adminPrivilegeService;

  @Resource
  AdminService adminService;

  /**
   * 角色列表
   *
   * @return
   */
  @RequestMapping("list")
  public ModelAndView list() {
    Map<String, Object> cond = new HashMap<String, Object>();
    List<Role> roles = adminRoleService.queryRolesByCond(cond);
    ModelAndView modelAndView = new ModelAndView("admin/roleList");
    modelAndView.addObject("roles", roles);
    return modelAndView;
  }

  /**
   * 添加角色
   *
   * @return
   */
  @RequestMapping("/addRole")
  public ModelAndView addRole(@ModelAttribute("newRoleRequest") NewRoleRequest newRoleRequest, Errors errors, HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      newRoleRequest.validate(newRoleRequest, errors);
      if (errors.hasErrors()) {
        return this.showMessage("添加角色失败", this.getErrMsg(errors), "/role/list", false);
      }
      Role role = new Role();
      role.setDisabled(newRoleRequest.getDisabled());
      role.setRoleDescription(newRoleRequest.getRoleDescription());
      role.setRoleName(newRoleRequest.getRoleName());
      role.setSortOrder(0);
      adminRoleService.addRole(role);
      return this.showMessage("成功", "添加角色成功", "/role/list", false);
    } else {
      ModelAndView modelAndView = new ModelAndView("/admin/roleAdd");
      return modelAndView;
    }
  }

  /**
   * 删除角色
   *
   * @param roleId
   * @return
   */
  @RequestMapping("/deleteRole")
  @ResponseBody
  public String deleteRole(Integer roleId) {
    if (roleId == null) {
      return "roleId不能为空";
    } else {
      adminRoleService.deleteRole(roleId);
      return "删除成功";
    }
  }

  /**
   * 编辑角色
   *
   * @return
   */
  @RequestMapping("/editRole")
  public ModelAndView editRole(@ModelAttribute("editRoleRequest") EditRoleRequest editRoleRequest, Errors errors, HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      editRoleRequest.validate(editRoleRequest, errors);
      if (errors.hasErrors()) {
        return this.showMessage("编辑角色失败", this.getErrMsg(errors), "/role/list", false);
      }
      Role role = new Role();
      role.setDisabled(editRoleRequest.getDisabled());
      role.setRoleDescription(editRoleRequest.getRoleDescription());
      role.setId(editRoleRequest.getRoleId());
      role.setRoleName(editRoleRequest.getRoleName());
      role.setSortOrder(0);
      adminRoleService.updateRole(role);
      return this.showMessage("成功", "编辑角色成功", "/role/list", false);
    } else {
      ModelAndView modelAndView = new ModelAndView("/admin/roleEdit");
      Role role = adminRoleService.findRoleById(editRoleRequest.getRoleId());
      modelAndView.addObject("role", role);
      return modelAndView;
    }
  }

  /**
   * 设置角色权限
   *
   * @param roleId
   * @param httpServletRequest
   * @return
   */
  @RequestMapping("setPrivilege")
  public ModelAndView setPrivilege(Integer roleId, HttpServletRequest httpServletRequest) {
      ModelAndView modelAndView = new ModelAndView("admin/rolePrivilege");
      List<RolePrivilege> rolePrivileges = adminRoleService.queryRolePrivileges(roleId);
      List<String> ids = new ArrayList<String>();
      for (RolePrivilege rolePrivilege : rolePrivileges) {
        ids.add(rolePrivilege.getPrivilegeId().toString());
      }
      String table = adminPrivilegeService.getRolePrivilegeTable(ids);
      modelAndView.addObject("table", table);
      modelAndView.addObject("roleId",roleId);
      return modelAndView;
  }

  /**
   * 设置权限
   * @param roleId
   * @param needDeleteId
   * @param needAddId
   * @return
   */
  @RequestMapping("doSetPrivilege")
  public ModelAndView doSetPrivilege(Integer roleId,String needDeleteId,String needAddId){

    if (StringUtils.isNotBlank(needDeleteId)) {
      String[] needDeleteIdArray = needDeleteId.split(";");
      if (needDeleteIdArray != null && needDeleteIdArray.length > 0) {
        List<Integer> needDeleteIdList = new ArrayList<>();
        for (String id : needDeleteIdArray) {
          needDeleteIdList.add(Integer.parseInt(id));
        }
        adminPrivilegeService.deleteRolePrivilege(roleId,needDeleteIdList);
      }
    }

    if (StringUtils.isNotBlank(needAddId)){
      String[] needAddIdArray = needAddId.split(";");
      if (needAddIdArray != null && needAddIdArray.length > 0) {
        List<Integer> needAddIdList = new ArrayList<>();
        for (String id : needAddIdArray) {
          needAddIdList.add(Integer.parseInt(id));
        }
        adminPrivilegeService.addRolePrivilege(roleId,needAddIdList);
      }
    }

    return this.showMessage("成功",null,"/role/setPrivilege?roleId="+roleId,true);
  }



  @RequestMapping("/admins")
  public ModelAndView roleAdminList(Integer roleId) {
    ModelAndView modelAndView = new ModelAndView("admin/roleAdmin");
    List<AdminRole> adminRoles = adminRoleService.queryRoleAdmins(roleId);
    List<Admin> admins = new ArrayList<Admin>();
    for (AdminRole adminRole : adminRoles) {
      admins.add(adminService.findAdminById(adminRole.getAdminId()));
    }
    modelAndView.addObject("admins", admins);
    return modelAndView;
  }
}
