package com.xiaodou.ms.web.controller.admin;


import com.xiaodou.ms.model.admin.Privilege;
import com.xiaodou.ms.service.admin.AdminPrivilegeService;
import com.xiaodou.ms.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zyp on 14-9-1.
 */
@Controller("privilegeController")
@RequestMapping("/privilege")
public class PrivilegeController extends BaseController {

  @Resource
  AdminPrivilegeService adminPrivilegeService;

  /**
   * @param privilege
   * @param httpServletRequest
   * @return
   */
  @RequestMapping(value = "/addPrivilege")
  public ModelAndView addPrivilegeShow(Privilege privilege, HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      adminPrivilegeService.addPrivilege(privilege);
      return this.showMessage("添加成功", "添加权限成功", "/privilege/list", false, null);
    } else {
      String tree = "";
      if (privilege.getParentId() != null) {
        tree = adminPrivilegeService.getSelectPrivilegeTree(privilege.getParentId().toString());
      } else {
        tree = adminPrivilegeService.getSelectPrivilegeTree(null);
      }
      ModelAndView modelAndView = new ModelAndView("admin/privilegeAdd");
      modelAndView.addObject("tree", tree);
      return modelAndView;
    }
  }

  /**
   * @param httpServletRequest
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView queryPrivilege(HttpServletRequest httpServletRequest) {
    String tree = adminPrivilegeService.getTablePrivilegeTree();
    ModelAndView modelAndView = new ModelAndView("admin/privilegeList");
    modelAndView.addObject("tree", tree);
    return modelAndView;
  }

  /**
   * @param adminPrivilege
   * @param httpServletRequest
   * @return
   */
  @RequestMapping("/editPrivilege")
  public ModelAndView editPrivilege(Privilege adminPrivilege, HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getMethod().equals("POST")) {
      Integer id = adminPrivilege.getId();
      if (null != id) {
        adminPrivilegeService.updatePrivilege(adminPrivilege);
        return this.showMessage("成功", "权限更新成功", "/privilege/list", false);
      } else {
        return this.showMessage("失败", "权限更新成功", "/privilege/list", false);
      }
    } else {
      if (adminPrivilege.getId() == null) {
        this.showMessage("失败", "权限号不能为空", "/privilege/list", false);
      }
      Privilege privilege = adminPrivilegeService.findPrivilegeById(adminPrivilege.getId());
      String tree =
          adminPrivilegeService.getSelectPrivilegeTree(privilege.getParentId().toString());

      ModelAndView modelAndView = new ModelAndView("admin/privilegeEdit");
      modelAndView.addObject("tree", tree);
      modelAndView.addObject("privilege", privilege);
      return modelAndView;
    }
  }

  /**
   * @param priId
   * @return
   */
  @RequestMapping("/deletePrivilege")
  public ModelAndView deletePrivilege(String priId) {
    if (priId == null) {
      return this.showMessage("删除失败", "权限号不能为空", "/privilege/list", false);
    } else {
      adminPrivilegeService.deletePrivilege(Integer.parseInt(priId));
      return this.showMessage("成功", "权限删除成功", "/privilege/list", false);
    }
  }

  /**
   * 排序
   * 
   * @param orders
   * @return
   */
  @RequestMapping("/sortPrivilege")
  public String sortPrivilege(String orders) {
    String[] orderItems = orders.split(";");
    for (String orderItem : orderItems) {
      String[] split = orderItem.split(":");
      Integer id = Integer.parseInt(split[0]);
      Integer order = Integer.parseInt(split[1]);
      Privilege privilege = new Privilege();
      privilege.setId(id);
      privilege.setSortOrder(order);
      adminPrivilegeService.updatePrivilege(privilege);
    }
    return "true";
  }



}
