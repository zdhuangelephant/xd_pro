package com.xiaodou.mysqladmin.system.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.mysqladmin.system.dao.AdminDao;
import com.xiaodou.mysqladmin.system.dao.RoleDao;
import com.xiaodou.mysqladmin.system.entity.Admin;
import com.xiaodou.mysqladmin.system.entity.Role;
import com.xiaodou.mysqladmin.system.utils.Constants;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Controller
@RequestMapping({"work-platform"})
public class LoginController {

  @Resource
  AdminDao adminDao;
  @Resource
  RoleDao roleDao;

  @RequestMapping(value = {"login"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String login() {
    return "system/login";
  }

  @RequestMapping(value = {"loginVaildate"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  public String loginVaildate(HttpServletRequest request, HttpServletResponse response) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    // String captcha = request.getParameter("captcha").toLowerCase();
    username = StringEscapeUtils.escapeHtml4(username.trim());
    HttpSession session = request.getSession(true);
    // String cap = (String) session.getAttribute("KAPTCHA_SESSION_KEY");
    String message = "";
    HashMap<String, String> map = Maps.newHashMap();
    if ((username == "") || (username == null)) {
      message = "请输入帐号！";
      map.put("error", message);
      request.setAttribute("message", message);
      return "system/login";
    }

    // if (!captcha.equals(cap)) {
    // message = "验证码错误！";
    // map.put("error", message);
    // request.setAttribute("message", message);
    // return "system/login";
    // }

    IQueryParam param = new QueryParam();
    param.addInput("userName", username);
    Page<Admin> adminPage = adminDao.findEntityListByCond(param, null);

    if (null == adminPage || null == adminPage.getResult() || adminPage.getResult().isEmpty()) {
      return loginVaildateLocalAccount(request, response);
    }

    Admin admin = adminPage.getResult().get(0);

    if (!admin.checkPassword(password)) {
      message = "您输入的帐号或密码有误！";
      map.put("error", message);
      request.setAttribute("message", message);
      return "system/login";
    }

    message = "登录成功！";
    param = new QueryParam();
    param.addInput("userId", admin.getId().toString());
    param.addOutputs(CommUtil.getAllField(Role.class));
    Page<Role> rolePage = roleDao.findEntityListByCond(param, null);
    Role role = null;
    if (null == rolePage || null == rolePage.getResult() || rolePage.getResult().isEmpty()) {
      role = new Role();
      role.setRid(UUID.randomUUID().toString());
      role.setToken(UUID.randomUUID().toString());
      role.setUserId(admin.getId().toString());
      role.setUserName(admin.getUserName());
      role.setName(admin.getRealName());
      role.setType(Constants.ROLE_TYPE_COMMON);
      roleDao.addEntity(role);
    } else {
      role = rolePage.getResult().get(0);
      role.setToken(UUID.randomUUID().toString());
      roleDao.updateEntityById(role);
    }
    session.setAttribute("TOKEN", role.getToken());
    session.setAttribute("USER_NAME", username);
    session.setAttribute("REAL_NAME", role.getName());
    request.setAttribute("username", username);
    return "redirect:/work-platform";
  }

  private String loginVaildateLocalAccount(HttpServletRequest request, HttpServletResponse response) {

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    // String captcha = request.getParameter("captcha").toLowerCase();
    username = StringEscapeUtils.escapeHtml4(username.trim());
    HttpSession session = request.getSession(true);
    String message = "";
    HashMap<String, String> map = Maps.newHashMap();

    IQueryParam param = new QueryParam();
    param.addInput("userName", username);
    param.addOutputs(CommUtil.getAllField(Role.class));
    Page<Role> rolePage = roleDao.findEntityListByCond(param, null);

    if (null == rolePage || null == rolePage.getResult() || rolePage.getResult().isEmpty()) {
      message = "您输入的帐号或密码有误！";
      map.put("error", message);
      request.setAttribute("message", message);
      return "system/login";
    }
    Role role = rolePage.getResult().get(0);

    if (!role.checkPassword(password)) {
      message = "您输入的帐号或密码有误！";
      map.put("error", message);
      request.setAttribute("message", message);
      return "system/login";
    }

    role.setToken(UUID.randomUUID().toString());
    roleDao.updateEntityById(role);
    message = "登录成功！";
    session.setAttribute("TOKEN", role.getToken());
    session.setAttribute("USER_NAME", username);
    session.setAttribute("REAL_NAME", role.getName());
    request.setAttribute("username", username);
    return "redirect:/work-platform";
  }

  @RequestMapping({"logout"})
  public String logout(HttpServletRequest request) {
    Enumeration<?> em = request.getSession().getAttributeNames();
    while (em.hasMoreElements()) {
      request.getSession().removeAttribute(em.nextElement().toString());
    }
    request.getSession().invalidate();
    return "system/login";
  }
}
