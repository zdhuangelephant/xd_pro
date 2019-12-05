package com.xiaodou.resources.service.product;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.request.product.CourseShareRequest;
import com.xiaodou.share.response.ShareResponse;
import com.xiaodou.share.service.AbstractShareService;
import com.xiaodou.ucenter.agent.toC.util.UserTokenWrapper;

@Service("courseShareService")
public class CourseShareService extends AbstractShareService<CourseShareRequest> {

  @Override
  public ShareResponse processResponse(CourseShareRequest request, ShareResponse response) {
    String resultType = request.getShareType();
    String nickName = UserTokenWrapper.getWrapper().getUserModel().getNickName();
    if (StringUtils.isNotBlank(nickName)) {
      try {
        nickName = Base64Utils.encode(URLEncoder.encode(nickName, "utf8").getBytes("utf8"));
      } catch (UnsupportedEncodingException e) {
        LoggerUtil.error("", e);
      }
    } else {
      nickName = StringUtils.EMPTY;
    }
    if (("1").equals(resultType)) {// 学习成功
      // 学习成功展示接口 -> url
      String url = String.format(response.getUrl(), resultType);
      response.setUrl(url);
    }
    if (("2").equals(resultType)) {// 学习失败
      // 学习失败展示接口 -> url
      String url = String.format(response.getUrl(), resultType);
      response.setUrl(url);
    }
    if (("5").equals(resultType)) {// 摘花瓣游戏
      // 摘花瓣游戏展示接口 -> url
    }
    return response;
  }

}
