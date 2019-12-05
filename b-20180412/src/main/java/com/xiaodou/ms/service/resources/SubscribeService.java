package com.xiaodou.ms.service.resources;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.knowledge.ForumAccountDao;
import com.xiaodou.ms.enums.Platform;
import com.xiaodou.ms.model.knowledge.ForumAccount;
import com.xiaodou.ms.vo.resources.ColumnSearchVO;
import com.xiaodou.ms.vo.resources.ColumnSearchVO.DataObj;
import com.xiaodou.ms.vo.resources.WechatOfficialAccountVO;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

@Service("subscribeService")
public class SubscribeService {
  @Resource
  ForumAccountDao forumAccountDao;

  /**
   * 调用知乎接口
   * 
   * @param keyword
   * @return updateTime: 2018年1月23日 下午1:14:45
   */
  public List<DataObj> searchColumn(String keyword) {
    try {
      String encode = URLEncoder.encode(keyword, "utf-8");
      HttpUtil http = HttpUtil.getInstance("www.zhihu.com", 443, "https");
      http.getClient().getParams().setContentCharset("UTF-8");
      HttpMethod method = HttpMethodUtil.getGetMethod("/api/v4/search_v3?t=column&q=" + encode
          + "&correction=1&search_hash_id=3ca8d5b404066527b412739dd56762f4&offset=0&limit=200");
      method.setRequestHeader("accept", "application/json, text/plain, */*");
      method.setRequestHeader("Referer",
          "https://www.zhihu.com/search?q=" + encode + "&type=column");
      method.setRequestHeader("authorization", "oauth c3cef7c66a1843f8b3a9e6a1e3160e20");
      method.setRequestHeader("X-UDID", "AHDrh_AzBw2PTmf3KoqCS26A_dcjW6ITbPE=");
      method.setRequestHeader("X-API-Version", "3.0.91");
      method.setRequestHeader("X-App-Za", "OS=Web");
      http.setMethod(method);
      return hlKeyword(http.getHttpResult());
    } catch (Exception e) {
      LoggerUtil.error("请求知乎Search接口出错", e);
    }
    return null;
  }

  /**
   * 高亮关键字
   * 
   * @param httpResult
   * @return updateTime: 2018年1月23日 下午6:35:22
   */
  private List<DataObj> hlKeyword(HttpResult httpResult) {
    String content = httpResult.getContent();
    if (StringUtils.isNotBlank(content)) {
      ColumnSearchVO vo = FastJsonUtil.fromJsons(content, new TypeReference<ColumnSearchVO>() {});
      if (null != vo && null != vo.getData() && vo.getData().size() != 0) {
        for (DataObj data : vo.getData()) {
          data.getHighlight().setTitle(
              data.getHighlight().getTitle().replaceAll("<em>", "<font style='color:red'>"));
          data.getHighlight()
              .setTitle(data.getHighlight().getTitle().replaceAll("</em>", "</font>"));
          // TODO
          
          // isSubsc:true 可以订阅
          Boolean isSubsc = checkIsSubscribe(Platform.COLUMN_ZHIHU.getCode().toString(), data.getObject().getId());
          if(!isSubsc) {
            data.setIsSubscribe("1");
          }
        }
        return vo.getData();
      }
    }
    return null;
  }

  private Boolean checkIsSubscribe(String platformType, String signature) {
    IQueryParam param = new QueryParam();
    param.addInput("platformType", platformType);
    param.addInput("signature", signature);
    param.addOutputs(CommUtil.getAllField(ForumAccount.class));
    Page<ForumAccount> page = forumAccountDao.findEntityListByCond(param, null);
    return (page == null || page.getResult().size() == 0) ? true : false;
  }

  /**
   * 调用搜狗搜索
   * 
   * @param keyword
   * @return updateTime: 2018年1月23日 下午1:13:29
   */
  public List<WechatOfficialAccountVO> searchWechatOfficialAccount(String keyword) {
    ArrayList<WechatOfficialAccountVO> container = Lists.newArrayList();
    ArrayList<String> top100 = Lists.newArrayList();
    try {
      int index = 1;
      int blockCounts = 0;
      while (top100.size() < 10 && blockCounts < 10) {
        String encode = URLEncoder.encode(keyword, "utf-8");
        HttpUtil http = HttpUtil.getInstance("weixin.sogou.com", 80, "http");
        http.getClient().getParams().setContentCharset("UTF-8");

        HttpMethod method = HttpMethodUtil.getGetMethod(
            "/weixin?type=1&s_from=input&query=" + encode + "&ie=utf8&_sug_=n&_sug_type_=");
        if (index > 1) {
          method = HttpMethodUtil.getGetMethod("/weixin?type=1&s_from=input&query=" + encode
              + "&ie=utf8&_sug_=n&_sug_type_=&page=" + index);
        }

        method.setRequestHeader("Accept",
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        method.setRequestHeader("Upgrade-Insecure-Requests", "1");
        method.setRequestHeader("Connection", "keep-alive");
        method.setRequestHeader("Host", "weixin.sogou.com");
        method.setRequestHeader("Cache-Control", "max-age=0");
        method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        method.setRequestHeader("Accept-Encoding", "gzip, deflate");


        method.setRequestHeader("User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0");
        method.setRequestHeader("Referer",
            "http://weixin.sogou.com/weixin?type=1&s_from=input&query=" + encode
                + "&ie=utf8&_sug_=n&_sug_type_=");
        if (index > 1) {
          method.setRequestHeader("Referer", "http://weixin.sogou.com/weixin?query=" + encode
              + "&_sug_type_=&s_from=input&_sug_=n&type=1&page=" + index + "&ie=utf8");
        }
        http.setMethod(method);
        HttpResult httpResult = http.getHttpResult();
        if (!httpResult.getContent().contains("您的访问出错了")) {
          top100.add(httpResult.getContent());
        } else {
          blockCounts++;
          // 访问出错

          List<String> fromJedis = JedisUtil.getListFromJedis("sougou-cookie-warehouse");
          if (null != fromJedis && fromJedis.size() > 0) {
            for (String cookieString : fromJedis) {
              httpResult.setCookies(deserializeCookies(cookieString));
              break;
            }
          }
        }
        index += 1;
      }
      if (!CollectionUtils.isEmpty(top100)) {
        for (String content : top100) {
          cleanOfficialData(container, content);
        }
      }
      return container;
    } catch (Exception e) {
      LoggerUtil.error("请求搜狗Search接口出错", e);
    }
    return null;
  }



  /**
   * 解析搜索结果
   * 
   * @param container
   * @param httpResult updateTime: 2018年1月23日 下午1:14:09
   */
  private void cleanOfficialData(ArrayList<WechatOfficialAccountVO> container, String content) {
    if (StringUtils.isNotBlank(content)) {
      // 解析HTML
      Document doc = Jsoup.parse(content);
      Elements eles = doc.select("div.news-box").select("ul.news-list2").select("li");
      for (Element element : eles) {
        WechatOfficialAccountVO vo = new WechatOfficialAccountVO();
        // 模糊查询结果
        String html = element.select("a").html();
        vo.setOfficialAccount(html);
        // 跳转地址
        String hrefOfficialAccount = element.select("a").attr("href");
        vo.setLinkAddr(hrefOfficialAccount);
        // 微信号
        String account = element.select("txt-box").select("p.info").select("label").html();
        Elements select = element.select("txt-box").select("p.info").select("span");
        if (null != select && select.size() != 0) {
          // 月转发量
          vo.setRepostsOfMonths(select.html());
        }
        vo.setOfficialAccountNO(account);
        // 功能介绍
        Elements dls = element.select("dl");
        for (int index = 0; index <= dls.size(); index++) {
          if (dls.size() > 2) {
            // 功能介绍
            if (index == 0) {
              String profile = dls.get(index).select("dd").html();
              vo.setProfile(profile);
            }
            if (index == 1) {
              // 微信认证
              String authenticate = dls.get(index).select("dd").html();
              vo.setAuthenticate(authenticate);
            }
            if (index == 2) {
              buildRecentArticle(vo, dls, index);
            }
          } else {
            // 功能介绍
            if (index == 0) {
              String profile = dls.get(index).select("dd").html();
              vo.setProfile(profile);
            }
            if (dls.size() > 1 && index == 1) {
              buildRecentArticle(vo, dls, index);
            }
          }
        }
        container.add(vo);
      }
    }
  }

  /**
   * 格式化公众号
   * 
   * @param dataObjList
   * @param keyword updateTime: 2018年1月23日 下午1:13:50
   */
  public void formatDataObj(List<WechatOfficialAccountVO> dataObjList, String keyword) {
    if (CollectionUtils.isEmpty(dataObjList)) return;
    for (WechatOfficialAccountVO vo : dataObjList) {
      String account = vo.getOfficialAccount();
      Document doc = Jsoup.parse(account);
      if (doc.html().contains("img")) {
        doc.select("img").remove();
      }
      if (doc.html().contains("span")) {
        doc.select("span").remove();
      }
      if (doc.html().contains("em")) {
        doc.select("em").addClass("ht");
      }
      vo.setOfficialAccount(doc.html());
    }
  }


  private void buildRecentArticle(WechatOfficialAccountVO vo, Elements dls, int index) {
    // 最近文章
    String recentArticle = dls.get(index).select("dd").select("a").html();
    String href = dls.get(index).select("dd").select("a").attr("href");
    vo.setRecentArticleLinkAddr(href);
    vo.setRecentArticle(recentArticle);
    // 发布时间
    String publishTime = dls.get(index).select("dd").select("dd").select("span").html();
    if (StringUtils.isNotBlank(publishTime)) {
      Pattern p = Pattern.compile("(\\d+)");
      Matcher m = p.matcher(publishTime);
      while (m.find()) {
        vo.setPublishTime(stampToDate(m.group(1).toString()));
        vo.setPublishTime(publishTime);
      }
    }
  }

  /**
   * 时间戳转Date
   * 
   * @param s
   * @return updateTime: 2018年1月23日 下午1:15:01
   */
  public static String stampToDate(String s) {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long lt = new Long(s);
    Date date = new Date(lt);
    res = simpleDateFormat.format(date);
    return res;
  }


  static {
    InitCookiePools();
  }


  private static void InitCookiePools() {
    /** initialDelay 初始调度任务时机 */
    Integer initialDelay = 0;
    /** refreshDelay 请求缓存刷新延迟 */
    Integer refreshDelay = 30;
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void doMain() {
        reqSoGouIndex();
      }

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("保存搜狗Cookie异常.", t);
      }
    }, initialDelay, refreshDelay, TimeUnit.SECONDS);
  }


  private static void reqSoGouIndex() {
    HttpUtil http = HttpUtil.getInstance("weixin.sogou.com", 443, "http");
    http.getClient().getParams().setContentCharset("UTF-8");

    HttpMethod reMethod = HttpMethodUtil.getGetMethod("/");

    reMethod.setRequestHeader("Accept",
        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
    reMethod.setRequestHeader("Accept-Encoding", "gzip, deflate, br");
    reMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
    reMethod.setRequestHeader("Cache-Control", "max-age=0");
    reMethod.setRequestHeader("Connection", "keep-alive");
    // reMethod.setRequestHeader("Cookie",
    // "IPLOC=CN1100; ABTEST=8|1515135990|v17; SUID=72834F792313940A000000005A4F23F6;
    // SUV=00241754794F83725A4F23F7DB116317; usid=VzxMEf-77iUGrOXA;
    // wuid=AAEPRvbCHQAAAAqUJic1XgcAkwA=; browerV=3; osV=1; SNUID=CE38F4C2BBBED8141554278FBC1B8158;
    // sct=1");
    reMethod.setRequestHeader("Host", "weixin.sogou.com");
    reMethod.setRequestHeader("Upgrade-Insecure-Requests", "1");
    reMethod.setRequestHeader("User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

    http.setMethod(reMethod);
    HttpResult httpResult = http.getHttpResult();
    Cookie[] cookies = httpResult.getCookies();
    List<String> fromJedis = JedisUtil.getListFromJedis("sougou-cookie-warehouse");
    if (null != fromJedis && fromJedis.size() != 0) {
      fromJedis.add(serializeCookies(cookies));
    } else {
      fromJedis = Lists.newArrayList(serializeCookies(cookies));
    }
    // 缓存cookie对
    if (null != fromJedis && fromJedis.size() > 0) {
      JedisUtil.addListToJedis("sougou-cookie-warehouse", fromJedis, XdmsConstant.SURVIVAL_TIME);
    }
  }

  private static String serializeCookies(Cookie[] cookies) {
    if (cookies == null || cookies.length <= 0) {
      return StringUtils.EMPTY;
    }
    ArrayList<com.xiaodou.ms.vo.resources.Cookie> lists = Lists.newArrayList();
    for (Cookie cookie : cookies) {
      com.xiaodou.ms.vo.resources.Cookie c = new com.xiaodou.ms.vo.resources.Cookie();
      c.setKey(cookie.getName());
      c.setValue(cookie.getValue());
      lists.add(c);
    }
    return FastJsonUtil.toJson(lists);
  }

  private Cookie[] deserializeCookies(String cookieString) {
    if (StringUtils.isBlank(cookieString)) {
      return null;
    }
    Cookie[] cookies = new Cookie[20];
    ArrayList<Cookie> lists = Lists.newArrayList();
    List<com.xiaodou.ms.vo.resources.Cookie> fromJsons = FastJsonUtil.fromJsons(cookieString,
        new TypeReference<List<com.xiaodou.ms.vo.resources.Cookie>>() {});
    for (com.xiaodou.ms.vo.resources.Cookie c : fromJsons) {
      Cookie cookie = new Cookie();
      cookie.setName(c.getKey());
      cookie.setValue(c.getValue());
      lists.add(cookie);
    }
    return lists.toArray(cookies);
  }

  /**调用web-fetch-service的接口
   * @param signature
   * updateTime: 2018年1月29日 下午1:30:13
   */
  public Boolean doSubscribe(String signature, String platformType) {
//    String encode = "";
//    try {
//      encode = URLEncoder.encode(signature, "utf-8");
//    } catch (UnsupportedEncodingException e) {      
//    }
//    
//    HttpUtil http = HttpUtil.getInstance("localhost", 8080, "http");
//    http.getClient().getParams().setContentCharset("UTF-8");
//    HttpMethod method = HttpMethodUtil.getGetMethod("/zhiHu/execute?signature=" + encode);
//    
//    http.setMethod(method);
//    HttpResult result = http.getHttpResult();
//    return "200".equals(result.getStatusCode()+"");
    ForumAccount entity = new ForumAccount();
    entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
    entity.setPlatform(Short.parseShort(platformType));
    entity.setSignature(signature);
    forumAccountDao.addEntity(entity);
    return true;
  }

  public Boolean cancelSubscribe(ForumAccount account) {
    return forumAccountDao.deleteEntityById(account);
  }


}

