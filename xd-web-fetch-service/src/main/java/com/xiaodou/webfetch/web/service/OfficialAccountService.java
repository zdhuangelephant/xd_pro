package com.xiaodou.webfetch.web.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.webfetch.util.FileUtilService;
import com.xiaodou.webfetch.web.constants.IDGenerator;
import com.xiaodou.webfetch.web.dao.AuthorModelDao;
import com.xiaodou.webfetch.web.dao.ForumDao;
import com.xiaodou.webfetch.web.dao.OfficialAccountDao;
import com.xiaodou.webfetch.web.domain.AuthorModel;
import com.xiaodou.webfetch.web.domain.ForumModel;
import com.xiaodou.webfetch.web.domain.OfficialAccount;

/**
 * 微信公众号Service层
 * 
 * @name OfficialService CopyRright (c) 2018 by
 *       <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 *
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2018年1月17日
 * @description TODO
 * @version 1.0
 */
@Service("officialService")
public class OfficialAccountService {
  
  @Resource
  FileUtilService fileUtilService;
  
  @Resource
  OfficialAccountDao officialAccountDao;

  @Resource
  AuthorModelDao authorModelDao;

  @Resource
  ForumDao forumDao;

  public List<OfficialAccount> getSuitableArticle() {
    ArrayList<OfficialAccount> linkLists = Lists.newArrayList();
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(OfficialAccount.class));
    Page<OfficialAccount> allArticles = officialAccountDao.findEntityListByCond(param, null);
    if (null != allArticles && !CollectionUtils.isEmpty(allArticles.getResult())) {
      for (OfficialAccount oa : allArticles.getResult()) {
        linkLists.add(oa);
      }
    }
    return (linkLists.size() != 0) ? linkLists : null;
  }

  public void analyizeArticle(List<OfficialAccount> linkLists) throws IOException {
    if (linkLists == null || linkLists.size() == 0) {
      return;
    }
    for (OfficialAccount oa : linkLists) {
      Connection connect = Jsoup.connect(oa.getLink());
      analyizeArticle0(connect.execute(), oa);
    }

  }


  public void analyizeArticle0(Response response, OfficialAccount oa) throws IOException {
    if (response.statusCode() == 200) {
      String body = response.body();
      Element mainBody = Jsoup.parse(body).getElementById("page-content");
      String title = mainBody.getElementById("activity-name").html().trim();
      String postDate = mainBody.getElementById("post-date").html().trim();
      String postUser = mainBody.getElementById("post-user").html().trim();
      Element jsContent = mainBody.getElementById("js_content");
      if (!jsContent.select("img[data-ratio]").isEmpty()) {
        jsContent.select("img[data-ratio]").remove();
      }

      AuthorModel dbAuthor = saveOrUpdateAuthor(postUser);

      ForumModel vo = new ForumModel();
      vo.setAuthorId(dbAuthor.getId() + "");
      vo.setCreateTime(Timestamp.valueOf(postDate + " 00:00:00"));
      vo.setForumType("2");
      vo.setForumClassify("1");
      
      String fileName = UUID.randomUUID().toString() + ".jpg";
      fileUtilService.downLoadByUrl(oa.getCover(), fileName);
      vo.setForumCover("http://resource.51xiaodou.com/" + fileName);
      vo.setForumTitle(title);
      vo.setForumContent(jsContent.html());
      vo.setForumMedia("");
      vo.setForumPraiserNum(100);
      vo.setForumReaderNum(100);
      vo.setPlatform("1");
      vo.setSelfId(oa.getAppmsgid());
      vo.setStatus((short) 0);
      vo.setUpdateTime(stampToDate(oa.getUpdate_time()));
      ForumModel exists = exists(oa.getAppmsgid());
      if (null != exists) {
        vo.setForumId(exists.getForumId());
        forumDao.updateEntityById(vo);
      } else {
        forumDao.addEntity(vo);
      }
    }
  }

  private ForumModel exists(String appmsgid) {
    IQueryParam param = new QueryParam();
    param.addInput("selfId", appmsgid);
    param.addOutput("forumId", 1);
    Page<ForumModel> dbForum = forumDao.findEntityListByCond(param, null);
    return (null == dbForum || dbForum.getResult().size() == 0) ? null : dbForum.getResult().get(0);
  }

  private AuthorModel saveOrUpdateAuthor(String postUser) {
    if (StringUtils.isBlank(postUser)) {
      return null;
    }
    IQueryParam param = new QueryParam();
    param.addInput("name", postUser.trim());
    param.addOutputs(CommUtil.getAllField(AuthorModel.class));
    Page<AuthorModel> page = authorModelDao.findEntityListByCond(param, null);
    if (null == page || page.getResult().size() == 0) {
      AuthorModel model = new AuthorModel();
      model.setId(Long.parseLong(IDGenerator.getSeqID()));
      model.setName(postUser);
      model.setGender((short) 1);
      model.setPortrait("");
      model.setInfo("wechat-official-account");
      model.setSelfId(IDGenerator.getSelfId());
      model.setCreateTime(new Timestamp(System.currentTimeMillis()));
      model.setWorksNum(10);
      return authorModelDao.addEntity(model);
    } else
      return page.getResult().get(0);
  }


  public static void main(String[] args) throws Exception {
    // new一个URL对象
    URL url =
        new URL("http://v.qq.com/iframe/player.html?vid=z0020tqgk10&width=670&height=502.5&auto=0");
    // 打开链接
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setConnectTimeout(5 * 1000);
    InputStream inStream = conn.getInputStream();
    byte[] data = readInputStream(inStream);
    File imageFile = new File("G:\\test.mp4");
    FileOutputStream outStream = new FileOutputStream(imageFile);
    outStream.write(data);
    outStream.close();
  }

  public static byte[] readInputStream(InputStream in) throws Exception {
    if (in == null) {
      return new byte[0];
    }
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len;
    while ((len = in.read(buffer)) > -1) {
      output.write(buffer, 0, len);
    }
    output.flush();
    return output.toByteArray();
  }
  
  public static Timestamp stampToDate(String s) {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    res = simpleDateFormat.format(new Date(Integer.parseInt(s) * 1000L));
    return Timestamp.valueOf(res);
  }
 
}
