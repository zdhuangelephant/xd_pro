package com.xiaodou.autopractise.web.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.autopractise.dao.PaperInfoDao;
import com.xiaodou.autopractise.dao.UserCourseInfoDao;
import com.xiaodou.autopractise.dao.UserInfoDao;
import com.xiaodou.autopractise.domain.PaperInfo;
import com.xiaodou.autopractise.domain.UserCourseInfo;
import com.xiaodou.autopractise.domain.UserInfo;
import com.xiaodou.autopractise.domain.UserTestInfo;
import com.xiaodou.autopractise.engine.UserHolder;
import com.xiaodou.autopractise.enums.UserAbility;
import com.xiaodou.autopractise.enums.UserCourseStatus;
import com.xiaodou.autopractise.enums.UserStatus;
import com.xiaodou.autopractise.request.CourseInfo;
import com.xiaodou.autopractise.request.ImportUserInfo;
import com.xiaodou.autopractise.request.QuesInfo;
import com.xiaodou.autopractise.service.MainService;
import com.xiaodou.autopractise.util.ImportExcelUntil;
import com.xiaodou.autopractise.util.Util;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Controller
public class MainController extends BaseController {
  @Resource
  MainService mainService;
  @Resource
  UserInfoDao userInfoDao;
  @Resource
  PaperInfoDao paperInfoDao;
  @Resource
  UserCourseInfoDao userCourseInfoDao;
  @Resource
  UserController userController;

  @RequestMapping(value = "/improtExcel", method = {RequestMethod.POST})
  public ModelAndView improtExcel(@RequestParam(value = "uploadFile") MultipartFile file) {
    ImportUserInfo obj = new ImportUserInfo();
    try {
      List<Map<String, String>> list = ImportExcelUntil.importExcel(file, obj);
      if (null != list && !list.isEmpty()) {
        for (Map<String, String> user : list) {
          String userName = tripDouble(user.get("userName"));
          String name = tripDouble(user.get("name"));
          String passwd = tripDouble(user.get("passwd"));
          if (StringUtils.isOrBlank(name, userName, passwd)) {
            continue;
          }
          IQueryParam param = new QueryParam();
          param.addInput("userName", userName);
          param.addOutput("userId", getSuccess());
          Page<UserInfo> userPage = userInfoDao.findEntityListByCond(param, null);
          if (null != userPage && null != userPage.getResult() && userPage.getResult().size() > 0) {
            continue;
          }
          UserInfo userInfo = new UserInfo();
          userInfo.setUserId(userName);
          userInfo.setName(name);
          userInfo.setUserName(userName);
          userInfo.setPasswd(passwd);
          userInfo.setFinish(false);
          userInfoDao.addEntity(userInfo);
          UserHolder.getInstance().addUserInfo(userInfo);
        }
      }
    } catch (Exception e) {
      LoggerUtil.error("导入用户失败", e);
    }
    return userController.statistic();
  }

  private String tripDouble(String info) {
    if (StringUtils.isBlank(info)) {
      return info;
    }
    if (info.indexOf(".") <= 0) {
      return info;
    }
    return info.substring(0, info.indexOf("."));
  }

  @RequestMapping(value = "/bind_course", method = RequestMethod.POST)
  @ResponseBody
  public String bindCourse(String unique, String courseArray) {
    UserInfo info = UserHolder.getInstance().getUserInfo(unique);
    UserCourseInfo processCourse = null;
    if (null == info) {
      return getFail();
    } else {
      if (StringUtils.isJsonNotBlank(courseArray)) {
        UserCourseInfo currCourse = UserHolder.getInstance().getUserCourse(info.getUserId());
        if (null != currCourse) {
          UserCourseStatus currCourseStatus =
              mainService.notAllBlock(info.getUserId(), currCourse.getCourseId());
          if (!UserCourseStatus.UNUSE.getCode().equals(currCourse.getStatus())
              && UserCourseStatus.EXAMING.equals(currCourseStatus)) {
            processCourse = currCourse;
          }
          if (!UserCourseStatus.UNUSE.getCode().equals(currCourse.getStatus())
              && !currCourseStatus.getCode().equals(currCourse.getStatus())) {
            currCourse.setStatus(currCourseStatus.getCode());
            userCourseInfoDao.updateEntityById(currCourse);
          }
        }
        Map<String, UserCourseInfo> userCourseInfoMap =
            mainService.buildUserCourseInfoMap(info.getUserId());
        List<CourseInfo> courseInfoList =
            FastJsonUtil.fromJsons(courseArray, new TypeReference<List<CourseInfo>>() {});
        if (null != courseInfoList && !courseInfoList.isEmpty()) {
          for (CourseInfo courseInfo : courseInfoList) {
            if (userCourseInfoMap.containsKey(courseInfo.getId())) {
              UserCourseInfo userCourse = userCourseInfoMap.get(courseInfo.getId());
              if (null == userCourse) {
                continue;
              }
              UserCourseStatus userCourseStatus =
                  mainService.notAllBlock(info.getUserId(), userCourse.getCourseId());
              if (null == processCourse
                  && !UserCourseStatus.UNUSE.getCode().equals(userCourse.getStatus())
                  && UserCourseStatus.EXAMING.equals(userCourseStatus)) {
                processCourse = userCourse;
              }
              if (!UserCourseStatus.UNUSE.getCode().equals(userCourse.getStatus())
                  && !userCourseStatus.getCode().equals(userCourse.getStatus())) {
                userCourse.setStatus(userCourseStatus.getCode());
                userCourseInfoDao.updateEntityById(userCourse);
              }
            } else {
              UserCourseInfo userCourse = mainService.bindUserCourse(info, courseInfo);
              if (null == processCourse) {
                processCourse = userCourse;
              }
            }
          }
        }
      } else {
        UserHolder.getInstance().clearUserCourse(info.getUserId());
        info.setStatus(UserStatus.EMPTYCOURSE.getCode());
        info.setFinish(Boolean.TRUE);
        UserHolder.getInstance().finishUserInfo(unique);
        userInfoDao.updateEntityById(info);
        return getFinish();
      }
      if (null != processCourse) {
        UserHolder.getInstance().markUserCourse(processCourse);
        return FastJsonUtil.toJson(processCourse);
      } else {
        UserHolder.getInstance().clearUserCourse(info.getUserId());
      }
      if (mainService.hasBlock(info)) {
        info.setStatus(UserStatus.BLOCKING.getCode());
        info.setFinish(Boolean.FALSE);
      } else {
        info.setStatus(UserStatus.FINISH.getCode());
        info.setFinish(Boolean.TRUE);
      }
      UserHolder.getInstance().finishUserInfo(unique);
      userInfoDao.updateEntityById(info);
    }
    return getFinish();
  }

  @RequestMapping(value = "/mark_finish_course", method = RequestMethod.POST)
  @ResponseBody
  public String markFinishCourse(String unique) {
    UserInfo info = UserHolder.getInstance().getUserInfo(unique);
    if (null == info) {
      return getFail();
    } else {
      UserCourseInfo userCourse = UserHolder.getInstance().getUserCourse(info.getUserId());
      if (null == userCourse) {
        return getFinish();
      }
      userCourse.setStatus(UserCourseStatus.UNUSE.getCode());
      userCourseInfoDao.updateEntityById(userCourse);
      UserHolder.getInstance().refreshUserInfoStatus(unique);
      return getSuccess();
    }
  }

  @RequestMapping(value = "/get_test_index", method = RequestMethod.POST)
  @ResponseBody
  public String getTestIndex(String unique, String testInfo) {
    UserInfo info = UserHolder.getInstance().getUserInfo(unique);
    if (null == info) {
      return getFail();
    } else {
      UserCourseInfo userCourse = UserHolder.getInstance().getUserCourse(info.getUserId());
      if (null == userCourse) {
        return getFinish();
      }
      if (StringUtils.isJsonNotBlank(testInfo)) {
        if (!mainService.refreshCourseTest(info, userCourse, testInfo)) {
          return getFail();
        }
      }
      UserTestInfo currTest = mainService.getTestIndex(userCourse);
      if (null == currTest) {
        userCourse.setStatus(UserCourseStatus.FINISH.getCode());
        userCourseInfoDao.updateEntityById(userCourse);
        return getFinish();
      }
      UserHolder.getInstance().refreshUserInfoStatus(unique);
      return FastJsonUtil.toJson(currTest);
    }
  }

  @RequestMapping(value = "/get_answer", method = RequestMethod.POST)
  @ResponseBody
  public String getAnswer(String unique, String ids, String quesArray, String paperDetail) {
    UserInfo info = UserHolder.getInstance().getUserInfo(unique);
    if (null == info) {
      return getFail();
    } else {
      UserCourseInfo userCourse = UserHolder.getInstance().getUserCourse(info.getUserId());
      if (null == userCourse) {
        return getFail();
      }
      try {
        String paperId = CommUtil.HEXAndMd5(ids);
        mainService.buildUserPaper(info.getUserId(), paperId);
        PaperInfo paper = new PaperInfo();
        paper.setPaperId(paperId);
        paper = paperInfoDao.findEntityById(paper);
        if (null == paper) {
          paper = new PaperInfo();
          paper.setPaperId(paperId);
          paper.setCourseId(userCourse.getCourseId());
          paper.setCourseName(userCourse.getCourseName());
          paper.setQuesInfoArray(quesArray);
          paper.setPaperDetail(URLDecoder.decode(paperDetail, "utf8"));
          paperInfoDao.addEntity(paper);
          mainService.blockTest(userCourse, paperId);
          userCourseInfoDao.updateEntityById(userCourse);
          UserHolder.getInstance().refreshUserInfoStatus(unique);
          return getBlock();
        } else if (!paper.getHasAnswer()) {
          mainService.blockTest(userCourse, paperId);
          userCourseInfoDao.updateEntityById(userCourse);
          UserHolder.getInstance().refreshUserInfoStatus(unique);
          return getBlock();
        } else {
          UserAbility ability = UserAbility.getStatusByCode(info.getAbility());
          Integer wrongQues = ability.getWrongQuesNum();
          Map<String, Object> quesMap = Maps.newHashMap();
          String quesInfoArr = paper.getQuesInfoArray();
          List<QuesInfo> quesInfoList =
              FastJsonUtil.fromJsons(quesInfoArr, new TypeReference<List<QuesInfo>>() {});
          Collections.shuffle(quesInfoList);
          for (int i = 0; i < wrongQues; i++) {
            quesInfoList.get(i).setQuesValue(StringUtils.EMPTY);
          }
          for (QuesInfo quesInfo : quesInfoList) {
            quesMap.put(quesInfo.getQuesId(), quesInfo);
          }
          UserHolder.getInstance().refreshUserInfoStatus(unique);
          return JSON.toJSONString(quesMap);
        }
      } catch (Exception e) {
        e.printStackTrace();
        return getFail();
      }
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView index() {
    return new ModelAndView("index");
  }

  @RequestMapping(value = "/paper_list", method = RequestMethod.GET)
  public ModelAndView addUser() {
    ModelAndView paperListView = new ModelAndView("paperList");
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(PaperInfo.class));
    Page<PaperInfo> paperPage = paperInfoDao.findEntityListByCond(param, null);
    if (null != paperPage && null != paperPage.getResult() && !paperPage.getResult().isEmpty()) {
      Set<String> courseIdSet = Sets.newHashSet();
      List<PaperInfo> paperList = Lists.newArrayList();
      for (PaperInfo paper : paperPage.getResult()) {
        if (courseIdSet.contains(paper.getCourseId())) {
          continue;
        }
        courseIdSet.add(paper.getCourseId());
        paperList.add(paper);
      }
      paperListView.addObject("paperList", paperList);
    }
    return paperListView;
  }

  @RequestMapping(value = "/paper_list_detail/{courseId}/{paperStatus}", method = RequestMethod.GET)
  @ResponseBody
  public String statisticDetail(@PathVariable String courseId, @PathVariable String paperStatus) {
    StringBuilder sb = new StringBuilder(2000);
    sb.append("<table>").append(System.getProperty("line.separator"));
    sb.append("<tr><td>序号</td><td>试卷ID</td><td>课程</td><td>状态</td><td>操作</td></tr>").append(
        System.getProperty("line.separator"));
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(PaperInfo.class));
    Page<PaperInfo> paperPage = paperInfoDao.findEntityListByCond(param, null);
    if (null != paperPage && null != paperPage.getResult() && !paperPage.getResult().isEmpty()) {
      Integer index = 1;
      for (PaperInfo paper : paperPage.getResult()) {
        if (StringUtils.isNotBlank(courseId) && !"-1".equals(courseId)
            && !paper.getCourseId().equals(courseId)) {
          continue;
        }
        if (StringUtils.isNotBlank(paperStatus) && !"-99".equals(paperStatus)) {
          if ("0".equals(paperStatus) && paper.getHasAnswer()) {
            continue;
          } else if ("1".equals(paperStatus) && !paper.getHasAnswer()) {
            continue;
          }
        }
        if (StringUtils.isJsonBlank(paper.getQuesInfoArray())) {
          sb.append("<tr><td>").append(index++).append("</td><td>").append(paper.getPaperId())
              .append("</td><td>").append(paper.getCourseName()).append("</td><td>").append("异常试卷")
              .append("</td><td><a href=\"javascript:deletePaper('").append(paper.getPaperId())
              .append("')\">删除</a>&nbsp;&nbsp;");
        } else {
          sb.append("<tr><td>").append(index++).append("</td><td>").append(paper.getPaperId())
              .append("</td><td>").append(paper.getCourseName()).append("</td><td>")
              .append(paper.getHasAnswer() ? "已完成" : "未完成")
              .append("</td><td><a href=\"javascript:doTest('").append(paper.getPaperId())
              .append("')\">作答</a>&nbsp;&nbsp;");
        }
        sb.append("<a href=\"javascript:userList('").append(paper.getPaperId())
            .append("')\">相关用户</a>").append("</td></tr>")
            .append(System.getProperty("line.separator"));
      }
    }
    sb.append("</table>").append(System.getProperty("line.separator"));
    return sb.toString();
  }

  @RequestMapping(value = "/delete_paper/{paperId}", method = RequestMethod.GET)
  @ResponseBody
  public String deletePaper(@PathVariable String paperId) {
    if (mainService.deletePaper(paperId)) {
      return getSuccess();
    } else {
      return getFail();
    }
  }

  @RequestMapping(value = "/do_test/{paperId}", method = RequestMethod.GET)
  public ModelAndView doTest(@PathVariable String paperId) {
    ModelAndView paper = new ModelAndView("paperDetail");
    PaperInfo paperInfo = new PaperInfo();
    paperInfo.setPaperId(paperId);
    paperInfo = paperInfoDao.findEntityById(paperInfo);
    paper.addObject("paperId", paperId);
    String pageInfo = paperInfo.getPaperDetail();
    pageInfo = pageInfo.replaceAll("src=\"", "src=\"http://exonline.bitsde.com");
    paper.addObject("pageInfo", pageInfo);
    return paper;
  }

  @RequestMapping(value = "/fetch_ques/{paperId}", method = RequestMethod.GET)
  @ResponseBody
  public String fetchQues(@PathVariable String paperId) {
    PaperInfo paperInfo = new PaperInfo();
    paperInfo.setPaperId(paperId);
    paperInfo = paperInfoDao.findEntityById(paperInfo);
    if (null != paperInfo) {
      Map<String, Object> quesMap = Maps.newHashMap();
      String quesInfoArr = paperInfo.getQuesInfoArray();
      List<QuesInfo> quesInfoList =
          FastJsonUtil.fromJsons(quesInfoArr, new TypeReference<List<QuesInfo>>() {});
      for (QuesInfo info : quesInfoList) {
        quesMap.put(info.getQuesId(), info);
      }
      return JSON.toJSONString(quesMap);
    } else {
      return getFail();
    }
  }

  @RequestMapping(value = "/submit/{paperId}", method = RequestMethod.POST)
  public void submit(@PathVariable String paperId, HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    ModelAndView paper = new ModelAndView("paperDetail");
    PaperInfo paperInfo = new PaperInfo();
    paperInfo.setPaperId(paperId);
    paperInfo = paperInfoDao.findEntityById(paperInfo);
    if (null != paperInfo) {
      String quesInfoArr = paperInfo.getQuesInfoArray();
      List<QuesInfo> quesInfoList =
          FastJsonUtil.fromJsons(quesInfoArr, new TypeReference<List<QuesInfo>>() {});
      paperInfo.setHasAnswer(Boolean.TRUE);
      for (QuesInfo quesInfo : quesInfoList) {
        String value = Util.fetchValue(request.getParameterValues(quesInfo.getQuesId()));
        if (StringUtils.isBlank(value)) {
          paperInfo.setHasAnswer(Boolean.FALSE);
        } else {
          quesInfo.setQuesValue(value);
        }
      }
      paperInfo.setQuesInfoArray(JSON.toJSONString(quesInfoList));
      paperInfoDao.updateEntityById(paperInfo);
      mainService.finishPaper(paperInfo);
    }
    paper.addObject("paperId", paperId);
    paper.addObject("pageInfo", paperInfo.getPaperDetail());
    response.sendRedirect("/paper_list");
  }
}
