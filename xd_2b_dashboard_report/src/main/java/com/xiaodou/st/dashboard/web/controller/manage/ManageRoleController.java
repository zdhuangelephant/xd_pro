package com.xiaodou.st.dashboard.web.controller.manage;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.st.dashboard.domain.admin.RoleDO;
import com.xiaodou.st.dashboard.service.manage.ManageRoleService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("manageRoleController")
@RequestMapping("/manage")
public class ManageRoleController extends BaseController {
  @Resource
  ManageRoleService manageRoleService;

  @RequestMapping("/role_list")
  public ModelAndView roleList() {
    ModelAndView modelAndView = new ModelAndView("/manage/role/roleList");
    modelAndView.addObject("adminUser", super.getAdminUser());
    modelAndView.addObject("listRole", manageRoleService.listRole(null));
    return modelAndView;
  }

  @RequestMapping("/save_role")
  @ResponseBody()
  public String saveUnit(RoleDO roleDO) {
    //RoleDO roleDO = new RoleDO().instance(roleDTO);
    roleDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return manageRoleService.saveRole(roleDO);
  }

  @RequestMapping("/update_role")
  @ResponseBody()
  public String updateUnit(RoleDO roleDO) {
    return manageRoleService.updateRole(roleDO);
  }

  @RequestMapping("/remove_role")
  @ResponseBody()
  public String removeUnit(Integer roleId) {
    return manageRoleService.removeRole(roleId);
  }

}
