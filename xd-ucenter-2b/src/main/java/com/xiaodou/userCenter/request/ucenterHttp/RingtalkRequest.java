package com.xiaodou.userCenter.request.ucenterHttp;

import java.sql.Timestamp;
import java.util.List;

import com.xiaodou.common.util.DateUtil;

import lombok.Data;

@Data
public class RingtalkRequest {
  private String msgtype = "markdown";
  private RingtalkMarkdown markdown;
  private RingtalkAT at;

  @Data
  public static class RingtalkMarkdown {
    private String title = "用户反馈信息";
    private String text;

    public void setText(Long userId, String userName, String category, String content) {
      this.text =
          String.format(
              "#### 用户反馈信息\n> 用户ID:%s\n\n> 用户姓名:%s\n\n> 反馈类型:%s\n\n> 反馈内容:%s\n\n> ###### %s反馈\n",
              userId, userName, category, content,
              DateUtil.relativeDateFormat(new Timestamp(System.currentTimeMillis())));
    }

  }

  @Data
  public static class RingtalkAT {
    private List<String> atMobiles;
    private boolean isAtAll = false;
  }

}
