package com.xiaodou.st.dashboard.web.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.domain.admin.PrivilegeTree;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.alarm.AlarmService;
import com.xiaodou.st.dashboard.service.manage.ManagePrivilegeService;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("indexController")
@RequestMapping("/")
public class IndexController extends BaseController {

  @Resource
  AlarmService alarmService;
  @Resource
  ManagePrivilegeService managePrivilegeService;

  /**
   * @description 首頁
   * @author 李德洪
   * @Date 2017年3月30日
   * @return
   */
  @RequestMapping("/")
  public ModelAndView index() {
    ModelAndView mv = new ModelAndView("/index");
    AdminUser adminUser = super.getAdminUser();
    mv.addObject("adminUser", adminUser);
    if (adminUser.getRole() != 4) {
      mv.addObject("listUnreadAlarmRecord", alarmService.listUnreadAlarmRecord());
    }
    List<PrivilegeTree> listTree =
        managePrivilegeService.buildMenu(Integer.valueOf(adminUser.getRole().toString()));
    mv.addObject("menuTree", FastJsonUtil.toJson(listTree));
    mv.addObject("goEasySubscribekey", StaticInfoProp.goEasySubscribekey());
    return mv;
  }

  
  @RequestMapping("/data-room/main")
  public ModelAndView dataRoom() {
    ModelAndView mv = new ModelAndView("/data-room/index");
    return mv;
  }
}
