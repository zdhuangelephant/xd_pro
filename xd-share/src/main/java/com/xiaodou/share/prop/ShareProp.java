package com.xiaodou.share.prop;

import com.xiaodou.common.util.FileUtil;
import com.xiaodou.share.response.ShareResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.userCenter.prop.ShareInfoProp.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 分享信息配置文件
 * @version 1.0
 */
public class ShareProp {

  public enum ShareField {
    content, title, imageUrl, url
  }

  private static String template = "shareinfo.%s.%s.%s";

  private static FileUtil shareinfo = FileUtil.getInstance("/conf/custom/env/shareinfo.properties");

  public static ShareResponse getResponse(String module, String shareType) {
    ShareResponse res = new ShareResponse(ResultType.SUCCESS);
    res.setContent(shareinfo.getProperties(String.format(template, module,
        ShareField.content.toString(), shareType)));
    res.setTitle(shareinfo.getProperties(String.format(template, module,
        ShareField.title.toString(), shareType)));
    res.setImageUrl(shareinfo.getProperties(String.format(template, module,
        ShareField.imageUrl.toString(), shareType)));
    res.setUrl(shareinfo.getProperties(String.format(template, module, ShareField.url.toString(),
        shareType)));
    return res;
  }

}
