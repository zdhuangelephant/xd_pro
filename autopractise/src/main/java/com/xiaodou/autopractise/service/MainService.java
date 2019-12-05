package com.xiaodou.autopractise.service;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.autopractise.dao.PaperInfoDao;
import com.xiaodou.autopractise.dao.UserCourseInfoDao;
import com.xiaodou.autopractise.dao.UserInfoDao;
import com.xiaodou.autopractise.dao.UserPaperRecordDao;
import com.xiaodou.autopractise.dao.UserTestInfoDao;
import com.xiaodou.autopractise.domain.PaperInfo;
import com.xiaodou.autopractise.domain.UserCourseInfo;
import com.xiaodou.autopractise.domain.UserInfo;
import com.xiaodou.autopractise.domain.UserPaperRecord;
import com.xiaodou.autopractise.domain.UserTestInfo;
import com.xiaodou.autopractise.engine.UserHolder;
import com.xiaodou.autopractise.enums.UserCourseStatus;
import com.xiaodou.autopractise.enums.UserStatus;
import com.xiaodou.autopractise.enums.UserTestStatus;
import com.xiaodou.autopractise.request.CourseInfo;
import com.xiaodou.autopractise.request.CourseTestInfo;
import com.xiaodou.autopractise.request.CourseTestInfo.TestInfo;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.dao.mongo.enums.Scope;
import com.xiaodou.summer.dao.mongo.param.MongoFieldParam;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service
public class MainService {
  @Resource
  UserInfoDao userInfoDao;
  @Resource
  PaperInfoDao paperInfoDao;
  @Resource
  UserPaperRecordDao userPaperRecordDao;
  @Resource
  UserCourseInfoDao userCourseInfoDao;
  @Resource
  UserTestInfoDao userTestInfoDao;

  public Map<String, UserCourseInfo> buildUserCourseInfoMap(String userId) {
    Map<String, UserCourseInfo> userCourseInfoMap = Maps.newHashMap();
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addOutputs(CommUtil.getAllField(UserCourseInfo.class));
    Page<UserCourseInfo> userCourseInfoPage = userCourseInfoDao.findEntityListByCond(param, null);
    if (null != userCourseInfoPage && null != userCourseInfoPage.getResult()
        && !userCourseInfoPage.getResult().isEmpty()) {
      for (UserCourseInfo courseInfo : userCourseInfoPage.getResult()) {
        userCourseInfoMap.put(courseInfo.getCourseId(), courseInfo);
      }
    }
    return userCourseInfoMap;
  }

  public UserCourseInfo bindUserCourse(UserInfo info, CourseInfo courseInfo) {
    UserCourseInfo userCourseInfo = new UserCourseInfo();
    userCourseInfo.setRecordId(RandomUtil.randomString(10));
    userCourseInfo.setUserId(info.getUserId());
    userCourseInfo.setCourseId(courseInfo.getId());
    userCourseInfo.setCourseName(courseInfo.getName());
    userCourseInfo.setCourseType(courseInfo.getType());
    userCourseInfo.setStatus(UserCourseStatus.EXAMING.getCode());
    userCourseInfoDao.addEntity(userCourseInfo);
    return userCourseInfo;
  }

  public void buildUserPaper(String userId, String paperId) {
    if (StringUtils.isOrBlank(userId, paperId)) {
      return;
    }
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("paperId", paperId);
    param.addOutputs(CommUtil.getAllField(UserPaperRecord.class));
    Page<UserPaperRecord> recordPage = userPaperRecordDao.findEntityListByCond(param, null);
    if (null != recordPage && null != recordPage.getResult() && !recordPage.getResult().isEmpty()) {
      return;
    }
    UserPaperRecord record = new UserPaperRecord();
    record.setRecordId(RandomUtil.randomString(10));
    record.setUserId(userId);
    record.setPaperId(paperId);
    userPaperRecordDao.addEntity(record);
  }

  public void finishPaper(PaperInfo paperInfo) {
    IQueryParam param = new QueryParam();
    param.addInput("paperId", paperInfo.getPaperId());
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    Page<UserTestInfo> userTestPage = userTestInfoDao.findEntityListByCond(param, null);
    if (null == userTestPage || null == userTestPage.getResult()
        || userTestPage.getResult().isEmpty()) {
      return;
    }
    for (UserTestInfo userTestInfo : userTestPage.getResult()) {
      userTestInfo.setStatus(UserTestStatus.EXAMING.getCode());
      userTestInfoDao.updateEntityById(userTestInfo);
      UserInfo info = UserHolder.getInstance().getUserInfoByUserId(userTestInfo.getUserId());
      if (null != info && UserStatus.BLOCKING.getCode().equals(info.getStatus())) {
        info.setStatus(UserStatus.EXAMING.getCode());
        info.setFinish(Boolean.FALSE);
        userInfoDao.updateEntityById(info);
        UserHolder.getInstance().reAddUserInfo(userTestInfo.getUserId());
      }
    }
  }

  public void blockTest(UserCourseInfo userCourse, String paperId) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userCourse.getUserId());
    param.addInput("courseId", userCourse.getCourseId());
    param.addInput("status", UserTestStatus.BLOCKING.getCode());
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    Page<UserTestInfo> testPage = userTestInfoDao.findEntityListByCond(param, null);
    Set<Integer> blockSet = Sets.newHashSet();
    if (null != testPage && null != testPage.getResult() && testPage.getResult().size() > 0) {
      for (UserTestInfo info : testPage.getResult()) {
        if (null == info) continue;
        blockSet.add(info.getIndex());
      }
    }
    param = new QueryParam();
    param.addInput("userId", userCourse.getUserId());
    param.addInput("courseId", userCourse.getCourseId());
    if (null != blockSet && !blockSet.isEmpty()) {
      MongoFieldParam indexSet = new MongoFieldParam(blockSet, Scope.NIN);
      param.addInput("index", indexSet);
    }
    param.addInput("status", UserTestStatus.EXAMING.getCode());
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    testPage = userTestInfoDao.findEntityListByCond(param, null);
    if (null == testPage || null == testPage.getResult() || testPage.getResult().size() == 0) {
      return;
    }
    UserTestInfo info = testPage.getResult().get(0);
    info.setPaperId(paperId);
    info.setStatus(UserTestStatus.BLOCKING.getCode());
    userTestInfoDao.updateEntityById(info);
  }

  public UserTestInfo getTestIndex(UserCourseInfo userCourse) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userCourse.getUserId());
    param.addInput("courseId", userCourse.getCourseId());
    param.addInput("status", UserTestStatus.BLOCKING.getCode());
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    Page<UserTestInfo> testPage = userTestInfoDao.findEntityListByCond(param, null);
    Set<Integer> blockSet = Sets.newHashSet();
    if (null != testPage && null != testPage.getResult() && testPage.getResult().size() > 0) {
      for (UserTestInfo info : testPage.getResult()) {
        if (null == info) continue;
        blockSet.add(info.getIndex());
      }
    }
    param = new QueryParam();
    param.addInput("userId", userCourse.getUserId());
    param.addInput("courseId", userCourse.getCourseId());
    if (null != blockSet && !blockSet.isEmpty()) {
      MongoFieldParam indexSet = new MongoFieldParam(blockSet, Scope.NIN);
      param.addInput("index", indexSet);
    }
    param.addInput("status", UserTestStatus.EXAMING.getCode());
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    testPage = userTestInfoDao.findEntityListByCond(param, null);
    if (null == testPage || null == testPage.getResult() || testPage.getResult().size() == 0) {
      return null;
    }
    return testPage.getResult().get(0);
  }

  public UserCourseStatus notAllBlock(String userId, String courseId) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("courseId", courseId);
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    Page<UserTestInfo> testPage = userTestInfoDao.findEntityListByCond(param, null);
    Set<Integer> blockSet = Sets.newHashSet();
    if (null == testPage || null == testPage.getResult() || testPage.getResult().size() == 0) {
      return UserCourseStatus.EXAMING;
    }
    for (UserTestInfo info : testPage.getResult()) {
      if (null == info) continue;
      if (UserTestStatus.BLOCKING.getCode().equals(info.getStatus())) {
        blockSet.add(info.getIndex());
      }
    }
    for (UserTestInfo info : testPage.getResult()) {
      if (null == info) continue;
      if (UserTestStatus.EXAMING.getCode().equals(info.getStatus())
          && !blockSet.contains(info.getIndex())) {
        return UserCourseStatus.EXAMING;
      }
    }
    if (blockSet.isEmpty()) {
      return UserCourseStatus.FINISH;
    } else {
      return UserCourseStatus.ALLBLOCK;
    }
  }

  public boolean refreshCourseTest(UserInfo info, UserCourseInfo userCourse, String sTestInfo) {
    CourseTestInfo courseTestInfo = FastJsonUtil.fromJson(sTestInfo, CourseTestInfo.class);
    if (StringUtils.isOrBlank(courseTestInfo.getName(), courseTestInfo.getCourseName())) {
      return true;
    }
    if (!info.getName().equals(courseTestInfo.getName())
        || !userCourse.getCourseName().equals(courseTestInfo.getCourseName())) {
      return false;
    }
    if (null == courseTestInfo.getGrageArr() || courseTestInfo.getGrageArr().isEmpty()) {
      return true;
    }
    for (TestInfo testInfo : courseTestInfo.getGrageArr()) {
      if (null == testInfo.getIndex() || null == testInfo.getTime()) {
        continue;
      }
      if (0 == testInfo.getIndex() || 0 == testInfo.getTime()) {
        continue;
      }
      Boolean insert = Boolean.TRUE;
      UserTestInfo userTestInfo = new UserTestInfo();
      userTestInfo.setTestId(RandomUtil.randomString(10));
      userTestInfo.setUserId(info.getUserId());
      userTestInfo.setCourseId(userCourse.getCourseId());
      userTestInfo.setIndex(testInfo.getIndex());
      userTestInfo.setTime(testInfo.getTime());
      IQueryParam param = new QueryParam();
      param.addInput("userId", info.getUserId());
      param.addInput("courseId", userCourse.getCourseId());
      param.addInput("index", testInfo.getIndex());
      param.addInput("time", testInfo.getTime());
      param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
      Page<UserTestInfo> testPage = userTestInfoDao.findEntityListByCond(param, null);
      if (null != testPage && null != testPage.getResult() && testPage.getResult().size() > 0) {
        insert = Boolean.FALSE;
        userTestInfo = testPage.getResult().get(0);
      }
      if (StringUtils.isBlank(testInfo.getScore()) || "未提交".equals(testInfo.getScore())) {
        userTestInfo.setScore(0d);
      } else {
        Double score =
            Double.parseDouble(testInfo.getScore().substring(0, testInfo.getScore().length() - 1));
        userTestInfo.setScore(score);
        userTestInfo.setStatus(UserTestStatus.FINISH.getCode());
        userTestInfo.setDate(testInfo.getDate());
      }
      if (insert) {
        userTestInfoDao.addEntity(userTestInfo);
      } else {
        userTestInfoDao.updateEntityById(userTestInfo);
      }
    }
    return true;
  }

  public boolean hasBlock(UserInfo info) {
    IQueryParam param = new QueryParam();
    param.addInput("userId", info.getUserId());
    param.addInput("status", UserTestStatus.BLOCKING.getCode());
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    Page<UserTestInfo> testPage = userTestInfoDao.findEntityListByCond(param, null);
    if (null == testPage || null == testPage.getResult() || testPage.getResult().size() == 0) {
      return false;
    }
    return true;
  }

  public boolean deletePaper(String paperId) {
    IQueryParam param = new QueryParam();
    param.addInput("paperId", paperId);
    param.addOutputs(CommUtil.getAllField(UserTestInfo.class));
    Map input = param.getInput();
    paperInfoDao.deleteEntityByCond(input);
    userPaperRecordDao.deleteEntityByCond(input);
    Page<UserTestInfo> userTestPage = userTestInfoDao.findEntityListByCond(param, null);
    if (null != userTestPage && null != userTestPage.getResult()
        && !userTestPage.getResult().isEmpty()) {
      for (UserTestInfo testInfo : userTestPage.getResult()) {
        if (paperId.equals(testInfo.getPaperId())
            && UserTestStatus.BLOCKING.getCode().equals(testInfo.getStatus())) {
          testInfo.setPaperId(StringUtils.EMPTY);
          testInfo.setStatus(UserTestStatus.EXAMING.getCode());
          userTestInfoDao.updateEntityById(testInfo);
          UserInfo info = UserHolder.getInstance().getUserInfoByUserId(testInfo.getUserId());
          if (null == info) {
            continue;
          }
          if (UserStatus.BLOCKING.getCode() == info.getStatus()) {
            info.setFinish(Boolean.FALSE);
            info.setStatus(UserStatus.EXAMING.getCode());
            userInfoDao.updateEntityById(info);
            UserHolder.getInstance().refreshUserInfo(info);
          }
        }
      }
    }
    return true;
  }

  public void deleteUserInfo(String userId) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", userId);
    userCourseInfoDao.deleteEntityByCond(cond);
    userPaperRecordDao.deleteEntityByCond(cond);
    userTestInfoDao.deleteEntityByCond(cond);
  }
}
