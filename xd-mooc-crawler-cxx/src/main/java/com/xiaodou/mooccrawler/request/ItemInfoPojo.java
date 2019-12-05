package com.xiaodou.mooccrawler.request;

import java.util.List;

import lombok.Data;

import com.google.common.collect.Lists;
import com.xiaodou.mooccrawler.request.CourseInfoPojo.ItemInfo;


/**
 * @name @see com.xiaodou.mooccrawler.request.CourseInfoPojo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月25日
 * @description 节信息参数类
 * @version 1.0
 */
@Data
public class ItemInfoPojo {
  private String href;
  private List<ItemInfo> itemArray = Lists.newArrayList();
}
