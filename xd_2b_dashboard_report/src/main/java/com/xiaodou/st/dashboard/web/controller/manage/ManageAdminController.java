package com.xiaodou.st.dashboard.web.controller.manage;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.admin.AdminDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.admin.AdminService;
import com.xiaodou.st.dashboard.service.manage.ManageAdminService;
import com.xiaodou.st.dashboard.service.manage.ManageRoleService;
import com.xiaodou.st.dashboard.service.manage.ManageUnitService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("manageAdminController")
@RequestMapping("/manage")
public class ManageAdminController extends BaseController {
  @Resource
  ManageAdminService manageAdminService;
  @Resource
  AdminService adminService;
  @Resource
  ManageUnitService manageUnitService;
  @Resource
  ManageRoleService manageRoleService;

  @RequestMapping("/admin_list")
  public ModelAndView adminList(AdminDO adminDO) {
    ModelAndView mv = new ModelAndView("/manage/admin/adminList");
    mv.addObject("adminDO", adminDO);
    mv.addObject("adminUser", super.getAdminUser());
    mv.addObject("listAdmin", manageAdminService.listManageAdmin(adminDO));
    List<UnitDO> listUnit = manageUnitService.listManageUnit(null);
    mv.addObject("listUnit", listUnit);
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("validStatus", Constants.ROLE_ON);
    mv.addObject("listRole", manageRoleService.listRole(inputs));
    return mv;
  }


  @RequestMapping("/save_admin")
  @ResponseBody()
  public String saveAdmin(AdminDO adminDO) {
    adminDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userName", adminDO.getUserName());
    if(!CollectionUtils.isEmpty(adminService.queryAdmin(cond))){
      return "该用户名已经存在，请使用其它用户名！";
    }
    return String.valueOf(adminService.addAdmin(adminDO));
  }

  @RequestMapping("/update_admin")
  @ResponseBody()
  public String updateAdmin(AdminDO adminDO) {
    return String.valueOf(adminService.updateAdmin(adminDO));
  }

  @RequestMapping("/remove_admin")
  @ResponseBody()
  public String removeAdmin(Integer adminId) {
    return String.valueOf(adminService.deleteAdmin(adminId));
  }
}
