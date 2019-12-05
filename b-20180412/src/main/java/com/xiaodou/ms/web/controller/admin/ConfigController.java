package com.xiaodou.ms.web.controller.admin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.ms.model.admin.AdminConfig;
import com.xiaodou.ms.service.admin.AdminConfigService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.ms.web.request.admin.AttachmentConfigRequest;
import com.xiaodou.ms.web.request.admin.BaseConfigRequest;
import com.xiaodou.ms.web.request.admin.EmailConfigRequest;
import com.xiaodou.ms.web.request.admin.SecurityConfigRequest;
import com.xiaodou.ms.web.request.admin.ShopConfigRequest;

/**
 * Created by zyp on 14-9-1.
 */

@Controller("configController")
@RequestMapping("/config")
public class ConfigController extends BaseController {

  @Resource
  AdminConfigService adminConfigService;

  @RequestMapping("/base")
  public ModelAndView base(@ModelAttribute("configRequest") BaseConfigRequest configRequest, Errors errors, HttpServletRequest httpServletRequest) {
    return this.doConfig("admin", "/config/base", "admin/baseConfig", configRequest, errors, httpServletRequest);
  }

  @RequestMapping("/email")
  public ModelAndView email(@ModelAttribute("configRequest") EmailConfigRequest configRequest, Errors errors, HttpServletRequest httpServletRequest) {
    return this.doConfig("email", "/config/email", "admin/emailConfig", configRequest, errors, httpServletRequest);
  }

  @RequestMapping("/attachment")
  public ModelAndView attachment(@ModelAttribute("configRequest") AttachmentConfigRequest configRequest, Errors errors, HttpServletRequest httpServletRequest) {
    return this.doConfig("admin", "/config/attachment", "admin/attachmentConfig", configRequest, errors, httpServletRequest);
  }

  @RequestMapping("/security")
  public ModelAndView security(@ModelAttribute("configRequest") SecurityConfigRequest configRequest, Errors errors, HttpServletRequest httpServletRequest) {
    return this.doConfig("admin", "/config/security", "admin/securityConfig", configRequest, errors, httpServletRequest);
  }

  @RequestMapping("/shop")
  public ModelAndView shop(@ModelAttribute("configRequest") ShopConfigRequest configRequest, Errors errors, HttpServletRequest httpServletRequest) {
    return this.doConfig("shop", "/config/shop", "admin/shopConfig", configRequest, errors, httpServletRequest);
  }

  /**
   * 获取配置属性值
   *
   * @param module
   * @param request
   * @return
   */
  public Map<String, String> getConfig(String module, Object request) {
    Map<String, String> config = new HashMap<String, String>();
    Field[] fields = request.getClass().getDeclaredFields();
    for (Field field : fields) {
      AdminConfig adminConfig = adminConfigService.findConfig(module, field.getName());
      if (adminConfig == null) {
        config.put(field.getName(), "");
      } else {
        config.put(field.getName(), adminConfig.getConfigValue());
      }
    }
    return config;
  }

  /**
   * 更新属性值
   */
  public void updateConfig(String module, Object request) throws IllegalAccessException {
    Field[] fields = request.getClass().getDeclaredFields();
    for (Field field : fields) {
      AdminConfig adminConfig = adminConfigService.findConfig(module, field.getName());
      field.setAccessible(true);
      if (adminConfig == null) {
        AdminConfig newAdminConfig = new AdminConfig();
        newAdminConfig.setSystemModule(module);
        newAdminConfig.setConfigKey(field.getName());
        newAdminConfig.setConfigValue((String) field.get(request));
        adminConfigService.addConfig(newAdminConfig);
      } else {
        adminConfig.setConfigValue((String) field.get(request));
        adminConfigService.editConfig(adminConfig);
      }
    }
  }

  /**
   * 执行配置
   *
   * @param module
   * @param url
   * @param templatePath
   * @param configRequest
   * @param errors
   * @param httpServletRequest
   * @return
   */
  public ModelAndView doConfig(String module, String url, String templatePath, BaseRequest configRequest, Errors errors, HttpServletRequest httpServletRequest) {
    ModelAndView modelAndView = new ModelAndView(templatePath);
    if (httpServletRequest.getMethod().equals("POST")) {
      configRequest.validate(configRequest, errors);
      if (errors.hasErrors()) {
        return this.showMessage("失败", "修改配置失败", url, false);
      }
      try {
        this.updateConfig(module, configRequest);
      } catch (IllegalAccessException e) {
        return this.showMessage("失败", "修改配置失败", url, false);
      }
      return this.showMessage("成功", "修改配置成功", url, false);
    } else {
      Map<String, String> config = this.getConfig(module, configRequest);
      modelAndView.addObject("config", config);
    }
    return modelAndView;
  }

}
