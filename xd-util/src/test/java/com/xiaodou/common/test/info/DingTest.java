package com.xiaodou.common.test.info;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.xiaodou.common.info.ding.DingSender;
import com.xiaodou.common.info.ding.req.LinkDingReq;
import com.xiaodou.common.info.ding.req.MarkdownDingReq;
import com.xiaodou.common.info.ding.req.SingleActionCardDingReq;
import com.xiaodou.common.info.ding.req.TextDingReq;
import com.xiaodou.common.util.DateUtil;

public class DingTest {

  List<String> urls =
      Lists
          .newArrayList("https://oapi.dingtalk.com/robot/send?access_token=53e39dc49ca6b15a084e50fedad32a20397ac5fccc3bcf280a1949181fb2c7b6");

  @Test
  public void testDingText() {
    TextDingReq req = new TextDingReq(urls);
    TextDingReq.Text text = new TextDingReq.Text();
    text.setContent("我就是我, 颜色不一样的焰火!");
    req.setText(text);
    DingSender.send(req);
  }

  @Test
  public void testDingLink() {
    LinkDingReq req = new LinkDingReq(urls);
    LinkDingReq.Link link = new LinkDingReq.Link();
    link.setTitle("测试link消息");
    link.setText("测试link消息");
    link.setMessageUrl("http://www.baidu.com");
    req.setLink(link);
    DingSender.send(req);
  }

  @Test
  public void testDingMarkDown() {
    MarkdownDingReq req = new MarkdownDingReq(urls);
    MarkdownDingReq.Markdown markdown = new MarkdownDingReq.Markdown();
    markdown.setTitle("测试MarkDown消息");
    markdown.setText(String.format(
        "#### 测试MarkDown消息\n> 测试MarkDown消息\n\n> 测试MarkDown消息\n\n> ###### %s反馈\n",
        DateUtil.relativeDateFormat(new Timestamp(System.currentTimeMillis()))));
    req.setMarkdown(markdown);
    DingSender.send(req);
  }

  @Test
  public void testDingSingleActionCard() {
    SingleActionCardDingReq req = new SingleActionCardDingReq(urls);
    SingleActionCardDingReq.ActionCard actionCard = new SingleActionCardDingReq.ActionCard();
    actionCard.setTitle("测试SingleActionCard");
    actionCard.setText("测试SingleActionCard");
    actionCard.setSingleTitle("测试SingleActionCard");
    actionCard.setSingleURL("http://www.baidu.com");
    req.setActionCard(actionCard);
    DingSender.send(req);
  }

}
