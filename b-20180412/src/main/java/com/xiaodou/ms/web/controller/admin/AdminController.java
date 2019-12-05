package com.xiaodou.ms.web.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.model.admin.Admin;
import com.xiaodou.ms.model.admin.Privilege;
import com.xiaodou.ms.model.admin.Role;
import com.xiaodou.ms.service.admin.AdminPrivilegeService;
import com.xiaodou.ms.service.admin.AdminRoleService;
import com.xiaodou.ms.service.admin.AdminService;
import com.xiaodou.ms.service.admin.AdminUser;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.admin.EditAdminRequest;
import com.xiaodou.ms.web.request.admin.EditInfo;
import com.xiaodou.ms.web.request.admin.EditPasswordRequest;
import com.xiaodou.ms.web.request.admin.NewAdminRequest;

/**
 * Created by zyp on 14-9-1.
 */
@Controller("adminController")
@RequestMapping("/admin")
public class AdminController extends BaseController {

  @Resource
  AdminService adminService;

  @Resource
  AdminRoleService adminRoleService;

  @Resource
  AdminPrivilegeService adminPrivilegeService;

  /**
   * 管理员登陆
   * 
   * @return
   */
  @RequestMapping("/login")
  public ModelAndView login(String fail) {
    ModelAndView modelAndView = new ModelAndView("admin/login");
    modelAndView.addObject("fail", fail);
    return modelAndView;
  }

  /**
   * 管理员列表
   * 
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView list() {
    Map<String, Object> cond = new HashMap<String, Object>();
    List<Admin> admins = adminService.queryAdmin(cond);
    for (Admin admin : admins) {
      admin.setRoleList(adminService.queryAdminRoles(admin.getId()));
    }
    ModelAndView modelAndView = new ModelAndView("/admin/adminList");
    modelAndView.addObject("admins", admins);
    return modelAndView;
  }

  /**
   * 修改密码
   * 
   * @return
   */
  @RequestMapping("/changePassword")
  public ModelAndView resetPassword(
      @ModelAttribute("editAdminRequest") EditPasswordRequest editPasswordRequest, Errors errors,
      HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      AdminUser adminUser = this.getUser();
      editPasswordRequest.validate(editPasswordRequest, errors);
      if (errors.hasErrors()) {
        return this.showMessage("修改密码失败", this.getErrMsg(errors), "/admin/changePassword", false);
      }
      // 验证老密码
      if (!adminService.validatePassword(adminUser.getUserId(),
          editPasswordRequest.getOldPassword())) {
        return this.showMessage("修改密码失败", "老密码不正确", "/admin/changePassword", false);
      } else {
        Admin admin = new Admin();
        admin.setId(adminUser.getUserId());
        admin.setPassword(editPasswordRequest.getNewPassword());
        adminService.updateAdmin(admin);
        return this.showMessage("成功", "修改密码", "/admin/changePassword", false);
      }
    } else {
      ModelAndView modelAndView = new ModelAndView("/admin/changePassword");
      return modelAndView;
    }
  }

  /**
   * 编辑管理员信息
   * 
   * @return
   */
  @RequestMapping("/editAdmin")
  public ModelAndView editAdminInfo(
      @ModelAttribute("editAdminRequest") EditAdminRequest editAdminRequest, Errors errors,
      HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      editAdminRequest.validate(editAdminRequest, errors);
      if (errors.hasErrors()) {
        return this.showMessage("修改管理员失败", this.getErrMsg(errors), "/admin/editAdmin?adminId="
            + editAdminRequest.getAdminId(), false);
      }
      Admin admin = new Admin();
      admin.setId(editAdminRequest.getAdminId());
      admin.setUserName(editAdminRequest.getUserName());
      admin.setEmail(editAdminRequest.getEmail());
      admin.setTelphone(editAdminRequest.getTelphone());
      if (StringUtils.isNotBlank(editAdminRequest.getPassword()))
        admin.setPassword(editAdminRequest.getPassword());
      admin.setRealName(editAdminRequest.getRealName());
      admin.setMerchant(editAdminRequest.getMerchant());
      adminService.updateAdmin(admin);
      return this.showMessage("成功", "编辑管理员成功", "/admin/list", false);
    } else {
      ModelAndView modelAndView = new ModelAndView("/admin/adminEdit");
      Integer adminId = editAdminRequest.getAdminId();
      Admin admin = adminService.findAdminById(adminId);
      modelAndView.addObject("admin", admin);
      return modelAndView;
    }
  }

  /**
   * 添加管理员
   * 
   * @return
   */
  @RequestMapping("/addAdmin")
  public ModelAndView addAdmin(@ModelAttribute("newAdminRequest") NewAdminRequest newAdminRequest,
      Errors errors, HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      newAdminRequest.validate(newAdminRequest, errors);
      if (errors.hasErrors()) {
        return this.showMessage("添加管理员失败", this.getErrMsg(errors), "/admin/addAdmin", false);
      }
      
      // 判断当前用户名是否可用
      Admin db_user = adminService.findAdminByUserName(newAdminRequest.getUserName());
      if (db_user != null) {
    	  return this.showMessage("添加管理员失败", "用户名已经存在", "/admin/addAdmin", false);
      }
      
      Admin admin = new Admin();
      admin.setEmail(newAdminRequest.getEmail());
      admin.setUserName(newAdminRequest.getUserName());
      admin.setPassword(newAdminRequest.getPassword());
      admin.setRealName(newAdminRequest.getRealName());
      admin.setTelphone(newAdminRequest.getTelphone());
      admin.setMerchant(newAdminRequest.getMerchant());
      adminService.addAdmin(admin);
      return this.showMessage("成功", "添加管理员成功", "/admin/list", false);
    } else {
      ModelAndView modelAndView = new ModelAndView("/admin/adminAdd");
      return modelAndView;
    }
  }

  @RequestMapping("/deleteAdmin")
  public ModelAndView deleteAdmin(Integer adminId) {
    if (adminId == null) {
      return this.showMessage("失败", "管理员id不能为空", "/admin/list", false);
    } else {
      adminService.deleteAdmin(adminId);
      return this.showMessage("成功", "管理员删除成功", "/admin/list", false);
    }
  }

  @RequestMapping("/showInfo")
  public ModelAndView showInfo(EditInfo editInfo, Errors errors,
      HttpServletRequest httpServletRequest) {
    ModelAndView modelAndView = new ModelAndView("/admin/infoShow");
    AdminUser adminUser = this.getUser();
    if (httpServletRequest.getMethod().equals("POST")) {
      editInfo.validate(editInfo, errors);
      if (errors.hasErrors()) {
        modelAndView.addObject("showInfo", editInfo);
        return this.showMessage("失败", "信息编辑失败", "/admin/editInfo", false);
      }
      Admin admin = new Admin();
      admin.setId(adminUser.getUserId());
      admin.setTelphone(editInfo.getTelphone());
      admin.setEmail(editInfo.getEmail());
      admin.setRealName(editInfo.getRealName());
      admin.setMerchant(editInfo.getMerchant());
      adminService.updateAdmin(admin);
      return this.showMessage("成功", "信息编辑成功", "/admin/infoShow", false);
    } else {
      Admin admin = adminService.findAdminById(adminUser.getUserId());
      modelAndView.addObject("admin", admin);
      return modelAndView;
    }
  }

  @RequestMapping("/editInfo")
  public ModelAndView editInfo(EditInfo editInfo, Errors errors,
      HttpServletRequest httpServletRequest) {
    ModelAndView modelAndView = new ModelAndView("/admin/infoEdit");
    AdminUser adminUser = this.getUser();
    if (httpServletRequest.getMethod().equals("POST")) {
      editInfo.validate(editInfo, errors);
      if (errors.hasErrors()) {
        modelAndView.addObject("editInfo", editInfo);
        return this.showMessage("失败", "信息编辑失败", "/admin/editInfo", false);
      }
      Admin admin = new Admin();
      admin.setId(adminUser.getUserId());
      admin.setTelphone(editInfo.getTelphone());
      admin.setEmail(editInfo.getEmail());
      admin.setRealName(editInfo.getRealName());
      admin.setMerchant(editInfo.getMerchant());
      adminService.updateAdmin(admin);
      return this.showMessage("成功", "信息编辑成功", "/admin/editInfo", false);
    } else {
      Admin admin = adminService.findAdminById(adminUser.getUserId());
      modelAndView.addObject("admin", admin);
      return modelAndView;
    }
  }

  /**
   * 为管理员分类角色
   * 
   * @return
   */
  @RequestMapping("/assigenRole")
  public ModelAndView assignRole(Integer adminId, HttpServletRequest httpServletRequest) {
    ModelAndView modelAndView = new ModelAndView("admin/adminRole");
    List<Role> roles = adminRoleService.queryRolesByCond(new HashMap<String, Object>());
    List<Role> adminRoles = adminService.queryAdminRoles(adminId);
    modelAndView.addObject("adminRoles", adminRoles);
    modelAndView.addObject("roles", roles);
    modelAndView.addObject("adminId", adminId);
    return modelAndView;
  }

  /**
   * 设置管理员角色
   * 
   * @param adminId
   * @param needDeleteId
   * @param needAddId
   * @return
   */
  @RequestMapping("/doAssigenRole")
  public ModelAndView doAssigenRole(Integer adminId, String needDeleteId, String needAddId) {

    if (StringUtils.isNotBlank(needDeleteId)) {
      String[] needDeleteIdArray = needDeleteId.split(";");
      List<Integer> needDeleteIdList = new ArrayList<>();
      for (String id : needDeleteIdArray) {
        needDeleteIdList.add(Integer.parseInt(id));
      }
      adminRoleService.deleteAdminRole(adminId, needDeleteIdList);
    }

    if (StringUtils.isNotBlank(needAddId)) {
      String[] needAddIdArray = needAddId.split(";");
      List<Integer> needAddIdList = new ArrayList<>();
      for (String id : needAddIdArray) {
        needAddIdList.add(Integer.parseInt(id));
      }
      adminRoleService.addAdminRole(adminId, needAddIdList);
    }

    return this.showMessage("成功", null, "/admin/assigenRole?adminId=" + adminId, true);
  }

  /**
   * 管理员权限
   * 
   * @param adminId
   * @return
   */
  @RequestMapping("/adminPrivileges")
  public ModelAndView adminPrivileges(Integer adminId) {
    ModelAndView modelAndView = new ModelAndView("admin/adminPrivilege");
    List<Privilege> personPrivileges = adminPrivilegeService.queryAdminAllPersonPrivilege(adminId);
    List<Privilege> rolePrivileges = adminPrivilegeService.queryAdminAllRolePrivilege(adminId);
    List<String> allIds = new ArrayList<>();
    List<Integer> rolePrivilegeIds = new ArrayList<>();
    for (Privilege privilege : personPrivileges) {
      allIds.add(privilege.getId().toString());
    }
    for (Privilege privilege : rolePrivileges) {
      allIds.add(privilege.getId().toString());
      rolePrivilegeIds.add(privilege.getId());
    }
    String table = adminPrivilegeService.getAdminPrivilegeTable(allIds, rolePrivilegeIds);
    modelAndView.addObject("table", table);
    modelAndView.addObject("adminId", adminId);
    return modelAndView;
  }

  /**
   * 管理员权限
   * 
   * @param adminId
   * @param needDeleteId
   * @param needAddId
   * @return
   */
  @RequestMapping("/doAdminPrivileges")
  public ModelAndView doAdminPrivileges(Integer adminId, String needDeleteId, String needAddId) {
    if (StringUtils.isNotBlank(needDeleteId)) {
      String[] needDeleteIdArray = needDeleteId.split(";");
      List<Integer> needDeleteIdList = new ArrayList<>();
      for (String id : needDeleteIdArray) {
        needDeleteIdList.add(Integer.parseInt(id));
      }
      adminPrivilegeService.deleteAdminPrivilege(adminId, needDeleteIdList);
    }

    if (StringUtils.isNotBlank(needAddId)) {
      String[] needAddIdArray = needAddId.split(";");
      List<Integer> needAddIdList = new ArrayList<>();
      for (String id : needAddIdArray) {
        needAddIdList.add(Integer.parseInt(id));
      }
      adminPrivilegeService.addAdminPrivilege(adminId, needAddIdList);
    }

    return this.showMessage("成功", null, "/admin/adminPrivileges?adminId=" + adminId, true);
  }

}
