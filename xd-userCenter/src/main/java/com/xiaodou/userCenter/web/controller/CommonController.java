package com.xiaodou.userCenter.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.RSAUtils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.request.UpTokenPojo;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.service.CommonService;

@Controller("commonController")
@RequestMapping("/common")
public class CommonController extends BaseController {

  @Resource
  CommonService commonService;

  /**
   * 获取UpToken接口
   * 
   * @param pojo
   * @param errors
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/upToken")
  @ResponseBody
  public String upToken(UpTokenPojo pojo, Errors errors, HttpServletRequest request)
      throws Exception {
    try {
      ResultInfo response = null;
      pojo = this.getParamsValue(request, UpTokenPojo.class);
      this.getParamsValueFromHeader(request, pojo);
      if (null == pojo) {
        return JSON.toJSONString((new ResultInfo(ResultType.VALFAIL)));
      }
      errors = pojo.validate();
      if (errors.hasErrors()) {
        response = new ResultInfo(ResultType.VALFAIL);
        response.setRetdesc(this.getErrMsg(errors));
        return JSON.toJSONString(response);
      } else {
        response = commonService.getUpToken(pojo);
        return JSON.toJSONString(response);
      }
    } catch (Exception e) {
      LoggerUtil.error("Controller层:信息反馈异常,详细信息请查看日志: ", e);
      throw e;
    }
  }

  /**
   * 获取UpToken接口
   * 
   * @param pojo
   * @param errors
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/upTokenFalse")
  @ResponseBody
  public String upTokenFalse(UpTokenPojo pojo, Errors errors, HttpServletRequest request)
      throws Exception {
    try {
      ResultInfo response = null;
      pojo = this.getParamsValue(request, UpTokenPojo.class);
      this.getParamsValueFromHeader(request, pojo);
      if (null == pojo) {
        return JSON.toJSONString((new ResultInfo(ResultType.VALFAIL)));
      }
      errors = pojo.validate();
      if (errors.hasErrors()) {
        response = new ResultInfo(ResultType.VALFAIL);
        response.setRetdesc(this.getErrMsg(errors));
        return JSON.toJSONString(response);
      } else {
        response = commonService.relaseUpToken(pojo);
        return JSON.toJSONString(response);
      }
    } catch (Exception e) {
      LoggerUtil.error("Controller层:信息反馈异常,详细信息请查看日志: ", e);
      throw e;
    }
  }

  @RequestMapping("/check_data")
  @ResponseBody
  public String checkData(HttpServletRequest request) throws Exception {
    String json = request.getParameter("req");
    if (StringUtils.isBlank(json)) {
      return "null data";
    }
    byte[] reqData = Base64Utils.decode(json);
    String privateKey = ConfigProp.getParams("req.data.private.key");
    // if (RSAUtils.verify(reqData, publicKey, sign)) {
    byte[] res = RSAUtils.decryptByPrivateKey(reqData, privateKey);
    BaseResultInfo response = new BaseResultInfo(ResultType.SUCCESS);
    response.setRetdesc(new String(res, "utf8"));
    return FastJsonUtil.toJson(response);
    // } else {
    // return "check data fail";
    // }
  }
}
