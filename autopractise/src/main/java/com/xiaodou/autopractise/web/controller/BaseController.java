package com.xiaodou.autopractise.web.controller;

import lombok.Data;

/**
 * @name @see com.xiaodou.autopractise.web.controller.BaseController.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年5月4日
 * @description 基础控制器
 * @version 1.0
 */
@Data
public class BaseController {
  private String Success = "{\"status\":\"Success!\"}";
  private String Same = "{\"status\":\"Same!\"}";
  private String Fail = "{\"status\":\"Fail!\"}";
  private String Finish = "{\"status\":\"Finish!\"}";
  private String Block = "{\"status\":\"Block!\"}";
}
