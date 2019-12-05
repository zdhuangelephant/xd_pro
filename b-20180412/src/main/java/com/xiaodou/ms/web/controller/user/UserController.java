package com.xiaodou.ms.web.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.ms.service.user.UserService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("userController")
@RequestMapping("/user")
public class UserController extends BaseController {
  @Resource
  UserService userService;
//  @Resource
//  ProductModuleService productModuleService;

  /**
   * 供应商目录
   */
  @RequestMapping("/list") 
  public ModelAndView resourceUser(Integer moduleId,Integer page) {  
    try {
      if (page == null || page == 0) {
        page = 1;
      }
      if(moduleId==null){
    	  moduleId=0;
      }
      ModelAndView modelAndView = new ModelAndView("/user/userList");
      Page<UserModel> userLists = userService.queryAllUser(moduleId,page);
//      List<ProductModuleModel> selectModule=productModuleService.queryAllModule();
//      modelAndView.addObject("selectModule", selectModule);
      modelAndView.addObject("pageList", userLists);
      modelAndView.addObject("moduleId", moduleId);
      return modelAndView;
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      throw e;
    }
  }

}
