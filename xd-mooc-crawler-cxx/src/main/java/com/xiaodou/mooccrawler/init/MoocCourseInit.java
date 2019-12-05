package com.xiaodou.mooccrawler.init;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Service;

import com.xiaodou.common.http.HttpMethodUtil;
import com.xiaodou.common.http.HttpUtil;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mooccrawler.dao.CourseModelDao;
import com.xiaodou.mooccrawler.dao.MoocItemDao;
import com.xiaodou.mooccrawler.dao.SubCourseModelDao;
import com.xiaodou.mooccrawler.domain.CourseModel;
import com.xiaodou.mooccrawler.domain.SubCourseModel;
import com.xiaodou.mooccrawler.service.FileUtilService;
import com.xiaodou.mooccrawler.vo.SubCourseList;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service
public class MoocCourseInit {

  @Resource
  MoocItemDao moocItemDao;

  @Resource
  CourseModelDao courseModelDao;

  @Resource
  SubCourseModelDao subCourseModelDao;

  @Resource
  FileUtilService fileUtilService;

  @PostConstruct
  public void init() {
    // IQueryParam param = new QueryParam();
    // param.addOutputs(CommUtil.getAllField(MoocItem.class));
    // Page<MoocItem> itemPage = moocItemDao.findEntityListByCond(param, null);
    // if (null != itemPage && null != itemPage.getResult() && !itemPage.getResult().isEmpty()) {
    // for (MoocItem item : itemPage.getResult()) {
    // MoocCourseHolder.getProcessor(item.getCourseId()).push(item);
    // }
    // }

    // 01. init course
    // for (int i = 0; i < 5000; i++) {
    // String courseId = Integer.toString(i);
    // HttpUtil util = HttpUtil.getInstance("wx.zkzj.org", 80, "http");
    // util.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
    // NameValuePair[] params = new NameValuePair[1];
    // params[0] = new NameValuePair("courseId", courseId);
    // HttpMethod method = HttpMethodUtil.getPostMethod("/tiku/app/getItemListNoLogin.do", params);
    // util.setMethod(method);
    // HttpResult result = util.getHttpResult();
    // if (null != result && StringUtils.isNotBlank(result.getContent())
    // && result.getContent().length() > 50) {
    // CourseList list = FastJsonUtil.fromJson(result.getContent(), CourseList.class);
    // if (null != list && null != list.getV() && !list.getV().isEmpty()) {
    // for (Course c : list.getV()) {
    // courseModelDao.addEntity(new CourseModel(c));
    // }
    // }
    // }
    // }
    // System.out.println("Finish!!!!!!!!!__________________________________________________________");

    // 02. init sub course
    IQueryParam param = new QueryParam();
    Page<CourseModel> coursePage = courseModelDao.findEntityListByCond(param, null);
    for (CourseModel co : coursePage.getResult()) {
      HttpUtil util = HttpUtil.getInstance("wx.zkzj.org", 80, "http");
      util.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
      NameValuePair[] params = new NameValuePair[1];
      params[0] = new NameValuePair("subcourseId", co.getSubCourseId());
      HttpMethod method =
          HttpMethodUtil.getPostMethod("/tiku/app/getVideoBySubcourseIdNoLogin.do", params);
      util.setMethod(method);
      HttpResult result = util.getHttpResult();
      if (null != result && StringUtils.isNotBlank(result.getContent())
          && result.getContent().length() > 50) {
        SubCourseList list = FastJsonUtil.fromJson(result.getContent(), SubCourseList.class);
        if (null != list && null != list.getV() && !list.getV().isEmpty()) {
          SubCourseModel scModel = new SubCourseModel(co);
          scModel.setScList(list);
          scModel.redownLoadVideo(fileUtilService);
          if (null != scModel.getChapterList() && !scModel.getChapterList().isEmpty()) {
            subCourseModelDao.addEntity(scModel);
          }
        }
      }
    }
    System.out.println("Finish!!!!!!!!!__________________________________________________________");
  }

}
