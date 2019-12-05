package com.xiaodou.ms.web.controller.admin;

import com.xiaodou.ms.service.admin.AdminPrivilegeService;
import com.xiaodou.ms.service.admin.AdminSecurityMetadataSource;
import com.xiaodou.ms.service.admin.AdminUser;
import com.xiaodou.ms.vo.PrivilegeTree;
import com.xiaodou.ms.web.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by zyp on 15/7/29.
 */
@Controller("indexController")
@RequestMapping("/")
public class IndexController extends BaseController {

  @Resource
  AdminPrivilegeService adminPrivilegeService;
  @Resource
  AdminSecurityMetadataSource mySecurityMetadataSource;
  /**
   * 首页
   * @return
   */
  @RequestMapping("/")
  public ModelAndView index(){
    AdminUser adminUser = this.getUser();
    List<PrivilegeTree> privilegeMapNodes =
      adminPrivilegeService.buildMenu(adminUser.getUserId());
    ModelAndView modelAndView = new ModelAndView("admin/index");
    modelAndView.addObject("admin", adminUser);
    //每次刷新首页，重新更新url-role关系
    mySecurityMetadataSource.loadPrivilegeDefine();
    modelAndView.addObject("privileges",privilegeMapNodes);
    return modelAndView;
  }
}
