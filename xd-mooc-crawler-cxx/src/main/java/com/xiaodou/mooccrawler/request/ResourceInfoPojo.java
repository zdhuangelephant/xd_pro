package com.xiaodou.mooccrawler.request;

import java.util.List;

import lombok.Data;

import com.google.common.collect.Lists;


@Data
public class ResourceInfoPojo {

  private String url;
  private List<ResourceInfo> resourceInfoList = Lists.newArrayList();

  /**
   * @name @see com.xiaodou.mooccrawler.request.ResourceInfoPojo.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年4月25日
   * @description 资源信息
   * @version 1.0
   */
  @Data
  public static class ResourceInfo {
    private String name;
    private String url;
  }
}
