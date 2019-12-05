package com.xiaodou.server.pay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.pay.constant.WeixinPayConstants;
import com.xiaodou.server.pay.response.wxH5Pay.WXAccessToken;
import com.xiaodou.server.pay.response.wxH5Pay.WXResult;
import com.xiaodou.server.pay.response.wxH5Pay.WXSnsapiUserinfo;
import com.xiaodou.server.pay.util.WXHttpUtil;

/**
 * 微信支付相關使用方法
 * 
 * @author 李德洪
 * @date 2016年12月8日
 */
@Controller("wxController")
@RequestMapping(value = "/wx_call_back")
public class WXController {
  @RequestMapping("/wx_h5_pay")
  public ModelAndView wxresources(String businessType, String resourcesId, String targetUserId,
      String giftType, String resourceTitle, String code, String state) {
    ModelAndView modelAndView = new ModelAndView("/resourceShare/wxH5Pay");
    // 通过code换取网页授权access_token
    String accessTokenDesc =
        WXHttpUtil.sendGet(
            "https://api.weixin.qq.com/sns/oauth2/access_token",
            "appid=" + WeixinPayConstants.getH5AppId(businessType) + "&secret="
                + WeixinPayConstants.getH5AppSecret(businessType) + "&code=" + code
                + "&grant_type=authorization_code");
    WXAccessToken wxAccessToken = FastJsonUtil.fromJson(accessTokenDesc, WXAccessToken.class);

    // 检验授权凭证（access_token）是否有效
    String checkaccessTokenDesc =
        WXHttpUtil.sendGet(
            "https://api.weixin.qq.com/sns/auth",
            "access_token=" + wxAccessToken.getAccess_token() + "&openid="
                + wxAccessToken.getOpenid());
    WXResult wxResult = FastJsonUtil.fromJson(checkaccessTokenDesc, WXResult.class);
    // 刷新access_token（如果需要）
    if (0 != wxResult.getErrcode()) {
      String retDesc =
          WXHttpUtil.sendGet("https://api.weixin.qq.com/sns/oauth2/refresh_token", "appid="
              + WeixinPayConstants.getH5AppId(businessType)
              + "&grant_type=refresh_token&refresh_token=" + wxAccessToken.getRefresh_token());
      wxAccessToken = FastJsonUtil.fromJson(retDesc, WXAccessToken.class);
    }
    // 拉取用户信息(需scope为 snsapi_userinfo)
    String userInfoDesc =
        WXHttpUtil.sendGet("https://api.weixin.qq.com/sns/userinfo", "access_token="
            + wxAccessToken.getAccess_token() + "&openid=" + wxAccessToken.getOpenid()
            + "&lang=zh_CN ");

    WXSnsapiUserinfo wxSnsapiUserinfo = FastJsonUtil.fromJson(userInfoDesc, WXSnsapiUserinfo.class);
    modelAndView.addObject("wxSnsapiUserinfo", wxSnsapiUserinfo);
    return modelAndView;
  }

}
