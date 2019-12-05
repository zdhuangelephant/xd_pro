package com.xiaodou.autopractise.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.autopractise.dao.UserCourseInfoDao;
import com.xiaodou.autopractise.dao.UserInfoDao;
import com.xiaodou.autopractise.dao.UserTestInfoDao;
import com.xiaodou.autopractise.domain.UserCourseInfo;
import com.xiaodou.autopractise.domain.UserInfo;
import com.xiaodou.autopractise.domain.UserTestInfo;
import com.xiaodou.autopractise.engine.UserHolder;
import com.xiaodou.autopractise.enums.UserAbility;
import com.xiaodou.autopractise.enums.UserCourseStatus;
import com.xiaodou.autopractise.enums.UserStatus;
import com.xiaodou.autopractise.enums.UserTestStatus;
import com.xiaodou.autopractise.service.MainService;
import com.xiaodou.autopractise.util.CacheUtil;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Controller
public class UserController extends BaseController {

  @Resource
  UserInfoDao userInfoDao;
  @Resource
  UserCourseInfoDao userCourseInfoDao;
  @Resource
  UserTestInfoDao userTestInfoDao;
  @Resource
  MainService mainService;

  @RequestMapping(value = "/get_user", method = RequestMethod.POST)
  @ResponseBody
  public String getUser(String unique) {
    UserInfo info = UserHolder.getInstance().getUserInfo(unique);
    if (null == info) {
      return getFinish();
    } else {
      if (CacheUtil.isWrong(unique, info.getUserId())) {
        info.setStatus(UserStatus.WRONG.getCode());
        userInfoDao.updateEntityById(info);
        UserHolder.getInstance().refreshUserInfo(info);
        UserHolder.getInstance().finishUserInfo(unique);
        return getUser(unique);
      }
      return FastJsonUtil.toJson(info);
    }
  }

  @RequestMapping(value = "/logout_user", method = RequestMethod.POST)
  @ResponseBody
  public String logoutUser(String unique) {
    UserHolder.getInstance().releaseUserInfo(unique);
    return getFinish();
  }

  @RequestMapping(value = "/bind_user", method = RequestMethod.POST)
  @ResponseBody
  public String bindUser(String unique, String name) {
    UserInfo info = UserHolder.getInstance().getUserInfo(unique);
    if (null == info || StringUtils.isBlank(name)) {
      return getFail();
    } else {
      name = name.trim();
      if (!info.getName().equals(name)) {
        info.setName(name);
        userInfoDao.updateEntityById(info);
        UserHolder.getInstance().refreshUserInfo(info);
      }
      return FastJsonUtil.toJson(info);
    }
  }

  @RequestMapping(value = "/import_user", method = RequestMethod.GET)
  public ModelAndView importUser() {
    return new ModelAndView("importUser");
  }

  @RequestMapping(value = "/add_user", method = RequestMethod.GET)
  public ModelAndView addUser() {
    ModelAndView user = new ModelAndView("userInfo");
    user.addObject("action", "/do_add_user");
    return user;
  }

  @RequestMapping(value = "/change_all_ability/{abilityCode}", method = RequestMethod.GET)
  @ResponseBody
  public String changeAllAbility(@PathVariable Integer abilityCode) {
    UserAbility ability = UserAbility.getStatusByCode(abilityCode);
    List<UserInfo> userInfoList = UserHolder.getInstance().getAllUser();
    if (null == userInfoList || userInfoList.isEmpty()) {
      return getSuccess();
    }
    for (UserInfo userInfo : userInfoList) {
      if (userInfo == null) continue;
      userInfo.setAbility(ability.getCode());
      userInfoDao.updateEntityById(userInfo);
      UserHolder.getInstance().refreshUserInfo(userInfo);
    }
    return getSuccess();
  }

  @RequestMapping(value = "/reset/{userId}", method = RequestMethod.GET)
  @ResponseBody
  public String reset(@PathVariable String userId) {
    UserInfo userInfo = UserHolder.getInstance().getUserInfoByUserId(userId);
    if (null == userInfo) {
      return getSuccess();
    }
    userInfo.setFinish(Boolean.FALSE);
    userInfo.setStatus(UserStatus.EXAMING.getCode());
    userInfoDao.updateEntityById(userInfo);
    UserHolder.getInstance().reAddUserInfo(userInfo.getUserId());
    resetAllCourse(userInfo.getUserId());
    return getSuccess();
  }

  @RequestMapping(value = "/reset_all", method = RequestMethod.GET)
  @ResponseBody
  public String resetAll() {
    List<UserInfo> userInfoList = UserHolder.getInstance().getAllUser();
    if (null == userInfoList || userInfoList.isEmpty()) {
      return getSuccess();
    }
    for (UserInfo userInfo : userInfoList) {
      if (userInfo == null) continue;
      if (userInfo.getFinish() && UserStatus.FINISH.getCode().equals(userInfo.getStatus())) {
        userInfo.setFinish(Boolean.FALSE);
        userInfo.setStatus(UserStatus.EXAMING.getCode());
        userInfoDao.updateEntityById(userInfo);
        UserHolder.getInstance().reAddUserInfo(userInfo.getUserId());
        resetAllCourse(userInfo.getUserId());
      }
    }
    return getSuccess();
  }

  private void resetAllCourse(String userId) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addOutputs(CommUtil.getAllField(UserCourseInfo.class));
    Page<UserCourseInfo> userCoursePage = userCourseInfoDao.findEntityListByCond(param, null);
    if (null != userCoursePage && null != userCoursePage.getResult()
        && !userCoursePage.getResult().isEmpty()) {
      for (UserCourseInfo userCourse : userCoursePage.getResult()) {
        if (null == userCourse) {
          continue;
        }
        userCourse.setStatus(UserCourseStatus.EXAMING.getCode());
        userCourseInfoDao.updateEntityById(userCourse);
      }
    }
  }

  @RequestMapping(value = "/debug_reset_all", method = RequestMethod.GET)
  @ResponseBody
  public String debugResetAll() {
    List<UserInfo> userInfoList = UserHolder.getInstance().getAllUser();
    if (null == userInfoList || userInfoList.isEmpty()) {
      return getSuccess();
    }
    for (UserInfo userInfo : userInfoList) {
      if (userInfo == null) continue;
      if (!UserStatus.EXAMING.getCode().equals(userInfo.getStatus())) {
        userInfo.setFinish(Boolean.FALSE);
        userInfo.setStatus(UserStatus.EXAMING.getCode());
        userInfoDao.updateEntityById(userInfo);
        UserHolder.getInstance().reAddUserInfo(userInfo.getUserId());
      }
    }
    return getSuccess();
  }

  @RequestMapping(value = "/modify_user/{userId}", method = RequestMethod.GET)
  public ModelAndView modifyUser(@PathVariable String userId) {
    ModelAndView user = new ModelAndView("userInfo");
    user.addObject("action", "/do_modify_user");
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(userId);
    userInfo = userInfoDao.findEntityById(userInfo);
    user.addObject("user", userInfo);
    return user;
  }

  @RequestMapping(value = "/report/{userId}/{courseId}", method = RequestMethod.GET)
  public ModelAndView report(@PathVariable String userId, @PathVariable String courseId) {
    ModelAndView report = new ModelAndView("report");
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addOutputs(CommUtil.getAllField(UserCourseInfo.class));
    Page<UserCourseInfo> userCoursePage = userCourseInfoDao.findEntityListByCond(param, null);
    if (null != userCoursePage) {
      report.addObject("userCourseList", userCoursePage.getResult());
    }
    UserInfo info = new UserInfo();
    info.setUserId(userId);
    report.addObject("userInfo", userInfoDao.findEntityById(info));
    report.addObject("courseId", courseId);
    return report;
  }

  @RequestMapping(value = "/report_detail/{userId}/{courseId}", method = RequestMethod.GET)
  @ResponseBody
  public String reportDetail(@PathVariable String userId, @PathVariable String courseId) {
    StringBuilder sb = new StringBuilder(2000);
    Map<String, UserCourseInfo> userCourseMap = buildUserCourseMap(userId, courseId);
    sb.append("<table>").append(System.getProperty("line.separator"));
    sb.append(
        "<tr><td>序号</td><td>课程ID</td><td>课程名称</td><td>练习阶段</td><td>练习次数</td><td>练习状态</td><td>试卷ID</td><td>成绩</td><td>完成时间</td></tr>")
        .append(System.getProperty("line.separator"));
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    if (StringUtils.isNotBlank(courseId) && !"-1".equals(courseId)) {
      param.addInput("courseId", courseId);
    }
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    Page<UserTestInfo> testPage = userTestInfoDao.findEntityListByCond(param, null);
    if (null != testPage && null != testPage.getResult() && !testPage.getResult().isEmpty()) {
      Integer index = 1;
      for (UserTestInfo testInfo : testPage.getResult()) {
        UserTestStatus status = UserTestStatus.getStatusByCode(testInfo.getStatus());
        UserCourseInfo userCourse = userCourseMap.get(testInfo.getCourseId());
        sb.append("<tr><td>").append(index++).append("</td><td>").append(userCourse.getCourseId())
            .append("</td><td>").append(userCourse.getCourseName()).append("</td><td>").append("第")
            .append(testInfo.getIndex()).append("阶段").append("</td><td>").append("第")
            .append(testInfo.getTime()).append("次").append("</td><td>").append(status.getDesc())
            .append("</td><td>");
        if (StringUtils.isNotBlank(testInfo.getPaperId())) {
          sb.append("<a href=\"javascript:doTest('").append(testInfo.getPaperId()).append("')\">")
              .append(testInfo.getPaperId()).append("</a>");
        } else {
          sb.append("无");
        }
        sb.append("</td><td>").append(testInfo.getScore()).append("</td><td>")
            .append(testInfo.getDate()).append("</td></tr>")
            .append(System.getProperty("line.separator"));
      }
    }
    sb.append("</table>").append(System.getProperty("line.separator"));
    return sb.toString();
  }

  @RequestMapping(value = "/detail/{userId}", method = RequestMethod.GET)
  public ModelAndView detail(@PathVariable String userId) {
    ModelAndView detail = new ModelAndView("detail");
    UserInfo info = new UserInfo();
    info.setUserId(userId);
    detail.addObject("userInfo", userInfoDao.findEntityById(info));
    detail.addObject("userCourseStatus", Lists.newArrayList(UserCourseStatus.values()));
    return detail;
  }

  @RequestMapping(value = "/detail_detail/{userId}/{userCourseStatus}", method = RequestMethod.GET)
  @ResponseBody
  public String detailDetail(@PathVariable String userId, @PathVariable Integer userCourseStatus) {
    StringBuilder sb = new StringBuilder(2000);
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addOutputs(CommUtil.getAllField(UserCourseInfo.class));
    Page<UserCourseInfo> userCoursePage = userCourseInfoDao.findEntityListByCond(param, null);
    sb.append("<table>").append(System.getProperty("line.separator"));
    sb.append("<tr><td>序号</td><td>课程ID</td><td>课程名称</td><td>课程类型</td><td>课程状态</td><td>操作</td></tr>")
        .append(System.getProperty("line.separator"));
    if (null != userCoursePage && null != userCoursePage.getResult()
        && !userCoursePage.getResult().isEmpty()) {
      Integer index = 1;
      for (UserCourseInfo userCourse : userCoursePage.getResult()) {
        UserCourseStatus status = UserCourseStatus.getStatusByCode(userCourse.getStatus());
        if (null != userCourseStatus && userCourseStatus != -99
            && userCourseStatus != status.getCode()) {
          continue;
        }
        sb.append("<tr><td>").append(index++).append("</td><td>").append(userCourse.getCourseId())
            .append("</td><td>").append(userCourse.getCourseName()).append("</td><td>")
            .append(userCourse.getCourseType()).append("</td><td>").append(status.getDesc())
            .append("</td><td>").append("<a href=\"javascript:report('")
            .append(userCourse.getUserId()).append("','").append(userCourse.getCourseId())
            .append("')\">成绩单</a></td></tr>").append(System.getProperty("line.separator"));
      }
    }
    sb.append("</table>").append(System.getProperty("line.separator"));
    return sb.toString();
  }

  @RequestMapping(value = "/paper_report/{paperId}", method = RequestMethod.GET)
  public ModelAndView paperReport(@PathVariable String paperId) {
    ModelAndView report = new ModelAndView("paperReport");
    report.addObject("paperId", paperId);
    return report;
  }

  @RequestMapping(value = "/paper_report_detail/{paperId}", method = RequestMethod.GET)
  @ResponseBody
  public String paperReportDetail(@PathVariable String paperId) {
    StringBuilder sb = new StringBuilder(2000);
    sb.append("<table>").append(System.getProperty("line.separator"));
    sb.append(
        "<tr><td>序号</td><td>用户</td><td>课程名称</td><td>练习阶段</td><td>练习次数</td><td>练习状态</td><td>试卷ID</td><td>成绩</td><td>完成时间</td></tr>")
        .append(System.getProperty("line.separator"));
    IQueryParam param = new QueryParam();
    param.addInput("paperId", paperId);
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    Page<UserTestInfo> testPage = userTestInfoDao.findEntityListByCond(param, null);
    if (null != testPage && null != testPage.getResult() && !testPage.getResult().isEmpty()) {
      Integer index = 1;
      for (UserTestInfo testInfo : testPage.getResult()) {
        UserTestStatus status = UserTestStatus.getStatusByCode(testInfo.getStatus());
        UserInfo info = UserHolder.getInstance().getUserInfoByUserId(testInfo.getUserId());
        if (null == info) {
          continue;
        }
        param = new QueryParam();
        param.addInput("courseId", testInfo.getCourseId());
        param.addInput("userId", testInfo.getUserId());
        param.addOutputs(CommUtil.getAllField(UserCourseInfo.class));
        Page<UserCourseInfo> userCoursePage = userCourseInfoDao.findEntityListByCond(param, null);
        sb.append("<tr><td>").append(index++).append("</td><td>").append(info.getName())
            .append("</td><td>");
        if (null != userCoursePage && null != userCoursePage.getResult()
            && userCoursePage.getResult().size() > 0) {
          sb.append(userCoursePage.getResult().get(0).getCourseName());
        } else {
          sb.append("------");
        }
        sb.append("</td><td>").append("第").append(testInfo.getIndex()).append("阶段")
            .append("</td><td>").append("第").append(testInfo.getTime()).append("次")
            .append("</td><td>").append(status.getDesc()).append("</td><td>")
            .append(testInfo.getPaperId()).append("</td><td>").append(testInfo.getScore())
            .append("</td><td>").append(testInfo.getDate()).append("</td></tr>")
            .append(System.getProperty("line.separator"));
      }
    }
    sb.append("</table>").append(System.getProperty("line.separator"));
    return sb.toString();
  }

  private Map<String, UserCourseInfo> buildUserCourseMap(String userId, String courseId) {
    Map<String, UserCourseInfo> map = Maps.newHashMap();
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    if (StringUtils.isNotBlank(courseId) && !"-1".equals(courseId)) {
      param.addInput("courseId", courseId);
    }
    param.addOutputs(CommUtil.getAllField(UserCourseInfo.class));
    Page<UserCourseInfo> userCoursePage = userCourseInfoDao.findEntityListByCond(param, null);
    if (null != userCoursePage && null != userCoursePage.getResult()
        && !userCoursePage.getResult().isEmpty()) {
      for (UserCourseInfo userCourse : userCoursePage.getResult()) {
        map.put(userCourse.getCourseId(), userCourse);
      }
    }
    return map;
  }

  @RequestMapping(value = "/do_add_user", method = RequestMethod.POST)
  @ResponseBody
  public String doAddUser(UserInfo userInfo) {
    if (StringUtils.isOrBlank(userInfo.getName(), userInfo.getUserName(), userInfo.getPasswd())) {
      return getFail();
    }
    IQueryParam param = new QueryParam();
    param.addInput("userName", userInfo.getUserName());
    param.addOutput("userId", getSuccess());
    Page<UserInfo> userPage = userInfoDao.findEntityListByCond(param, null);
    if (null != userPage && null != userPage.getResult() && userPage.getResult().size() > 0) {
      return getSame();
    }
    userInfo.setUserId(userInfo.getUserName());
    userInfo.setFinish(false);
    userInfoDao.addEntity(userInfo);
    UserHolder.getInstance().addUserInfo(userInfo);
    return getSuccess();
  }

  @RequestMapping(value = "/do_modify_user", method = RequestMethod.POST)
  @ResponseBody
  public String doModifyUser(UserInfo userInfo) {
    if (StringUtils.isOrBlank(userInfo.getUserId())) {
      return getFail();
    }
    IQueryParam param = new QueryParam();
    param.addInput("userId", userInfo.getUserId());
    param.addOutputs(CommUtil.getAllField(UserInfo.class));
    Page<UserInfo> userInfoPage = userInfoDao.findEntityListByCond(param, null);
    if (null == userInfoPage || null == userInfoPage.getResult()
        || userInfoPage.getResult().isEmpty()) {
      return getFail();
    }
    UserInfo _userInfo = userInfoPage.getResult().get(0);
    if (!_userInfo.getUserId().equals(userInfo.getUserId())) {
      return getFail();
    }
    if (StringUtils.isNotBlank(userInfo.getName())) {
      _userInfo.setName(userInfo.getName());
    }
    if (null != userInfo.getAbility()) {
      _userInfo.setAbility(userInfo.getAbility());
    }
    if (StringUtils.isNotBlank(userInfo.getUserName())) {
      _userInfo.setUserName(userInfo.getUserName());
    }
    if (StringUtils.isNotBlank(userInfo.getPasswd())) {
      if (!userInfo.getPasswd().equals(_userInfo.getPasswd())
          && UserStatus.WRONG.getCode().equals(_userInfo.getStatus())) {
        _userInfo.setStatus(UserStatus.EXAMING.getCode());
      }
      _userInfo.setPasswd(userInfo.getPasswd());
    }
    userInfoDao.updateEntityById(_userInfo);
    UserHolder.getInstance().refreshUserInfo(_userInfo);
    return getSuccess();
  }

  @RequestMapping(value = "/delete_user/{userId}", method = RequestMethod.GET)
  @ResponseBody
  public String deleteUser(@PathVariable String userId) {
    if (StringUtils.isOrBlank(userId)) {
      return getFail();
    }
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(userId);
    userInfo = userInfoDao.findEntityById(userInfo);
    if (null == userInfo) {
      return getFail();
    }
    userInfoDao.deleteEntityById(userInfo);
    UserHolder.getInstance().deleteUserInfo(userInfo);
    mainService.deleteUserInfo(userId);
    return getSuccess();
  }

  @RequestMapping(value = "/statistic", method = RequestMethod.GET)
  public ModelAndView statistic() {
    ModelAndView statistic = new ModelAndView("statistic");
    statistic.addObject("userStatus", Lists.newArrayList(UserStatus.values()));
    return statistic;
  }

  @RequestMapping(value = "/statistic_detail/{statusCode}", method = RequestMethod.GET)
  @ResponseBody
  public String statisticDetail(@PathVariable Integer statusCode) {
    StringBuilder sb = new StringBuilder(2000);
    sb.append("<table>").append(System.getProperty("line.separator"));
    sb.append(
        "<tr><td>序号</td><td>学生ID</td><td>姓名</td><td>学号</td><td>重复次数</td><td>能力值</td><td>学习状态</td><td>当前课程</td><td>操作</td></tr>")
        .append(System.getProperty("line.separator"));
    List<UserInfo> allUserList = UserHolder.getInstance().getAllUser();
    if (null != allUserList && !allUserList.isEmpty()) {
      Integer index = 1;
      Map<String, Integer> userNameCount = Maps.newHashMap();
      for (UserInfo user : allUserList) {
        Integer count = userNameCount.get(user.getUserName());
        if (null == count) {
          count = 1;
        } else {
          count++;
        }
        userNameCount.put(user.getUserName(), count);
      }
      for (UserInfo user : allUserList) {
        if (null != statusCode && -99 != statusCode && statusCode != user.getStatus()) {
          continue;
        }
        UserStatus status = UserStatus.getStatusByCode(user.getStatus());
        UserAbility ability = UserAbility.getStatusByCode(user.getAbility());
        UserCourseInfo userCourse = UserHolder.getInstance().getUserCourse(user.getUserId());
        Integer sameCount = userNameCount.get(user.getUserName());
        sb.append("<tr><td>").append(index++).append("</td><td>").append(user.getUserId())
            .append("</td><td>").append(user.getName()).append("</td><td>")
            .append(user.getUserName()).append("</td><td>")
            .append(sameCount == null ? 1 : sameCount).append("</td><td>")
            .append(ability.getDesc()).append("</td><td>").append(status.getDesc())
            .append("</td><td>").append(userCourse == null ? "无" : userCourse.getCourseName())
            .append("</td><td><a href=\"javascript:modify('").append(user.getUserId())
            .append("')\">修改</a>&nbsp;&nbsp<a href=\"javascript:deleteUser('")
            .append(user.getUserId()).append("','").append(user.getName())
            .append("')\">删除</a>&nbsp;&nbsp<a href=\"javascript:detail('").append(user.getUserId())
            .append("')\">详情</a>&nbsp;&nbsp<a href=\"javascript:reset('").append(user.getUserId())
            .append("')\">重置</a>&nbsp;&nbsp<a href=\"javascript:report('").append(user.getUserId())
            .append("','-1')\">成绩单</a>").append("</td></tr>")
            .append(System.getProperty("line.separator"));
      }
    }
    sb.append("</table>").append(System.getProperty("line.separator"));
    return sb.toString();
  }
}
