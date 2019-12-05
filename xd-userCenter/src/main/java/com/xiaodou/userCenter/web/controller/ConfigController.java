package com.xiaodou.userCenter.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.request.ConfigRequest;
import com.xiaodou.userCenter.util.ModuleMappingWrapper;

@Controller("configController")
@RequestMapping("/config")
public class ConfigController extends BaseController {

  @RequestMapping("/version")
  @ResponseBody
  public String configVersion(HttpServletRequest request) {
    return doMain(request, new IController<BaseRequest>() {
      @Override
      public ResultInfo doService(BaseRequest pojo, HttpServletRequest request) throws Exception {
        return ModuleMappingWrapper.getWrapper().getModule().getConfigService().configVersion(pojo);
      }

      @Override
      public BaseRequest buildRequest(HttpServletRequest request) throws Exception {
        return new BaseRequest();
      }
    });
  }
  @RequestMapping("/config")
  @ResponseBody
  public String config(HttpServletRequest request) {
    return doMain(request, new IController<ConfigRequest>() {

      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(ConfigRequest pojo, HttpServletRequest request) throws Exception {
        return ModuleMappingWrapper.getWrapper().getModule().getConfigService().config(pojo);
      }

      @Override
      public ConfigRequest buildRequest(HttpServletRequest request) throws Exception {
        ConfigRequest pojo = null;
        if ((pojo =
            getParamsValue(request, ModuleMappingWrapper.getWrapper().getModule().getConfInClass())) == null)
          pojo = ModuleMappingWrapper.getWrapper().getModule().getConfInClass().newInstance();
        return pojo;
      }
    });
  }

}
