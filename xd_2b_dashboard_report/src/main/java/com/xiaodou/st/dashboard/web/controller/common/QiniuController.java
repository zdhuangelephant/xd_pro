package com.xiaodou.st.dashboard.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.st.dashboard.domain.qiniu.QiniuPutPolicy;
import com.xiaodou.st.dashboard.util.QiniuUtil;

@Controller("qiniuController")
@RequestMapping("qiniu")
public class QiniuController {

  /**
   * 获取上传token
   * @return
   */
  @RequestMapping(value = "uptoken")
  @ResponseBody
  public String uptoken(QiniuPutPolicy putPolicy){
    String accessKey = "HkY48EzdbFQiP3iFdi0r-KxMya7mI6hsSNhFwqOi";
    String secretKey = "SEznQBN_EIax8C7ytFP_fdLOdR9kgAZ_O6LPXX1L";
    String token = QiniuUtil.getUptoken(accessKey,secretKey,putPolicy);
    String response = "{\"uptoken\":\""+token+"\"}";
    return response;
  }
}
