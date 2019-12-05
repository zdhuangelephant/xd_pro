package com.xiaodou.userCenter.web.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.RSAUtils;
import com.xiaodou.common.util.StringUtils;
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
  @RequestMapping("/up_token")
  @ResponseBody
  public String upToken(UpTokenPojo request) throws Exception {
    return commonService.getUpToken(request).toJsonString();
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
  @RequestMapping("/up_token_false")
  @ResponseBody
  public String upTokenFalse(UpTokenPojo request) throws Exception {
    return commonService.relaseUpToken(request).toJsonString();
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
    byte[] res = RSAUtils.decryptByPrivateKey(reqData, privateKey);
    BaseResultInfo response = new BaseResultInfo(ResultType.SUCCESS);
    response.setRetdesc(new String(res, "utf8"));
    return response.toJsonString();
  }
}
