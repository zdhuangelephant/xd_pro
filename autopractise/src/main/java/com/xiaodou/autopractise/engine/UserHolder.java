package com.xiaodou.autopractise.engine;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.xiaodou.autopractise.domain.UserCourseInfo;
import com.xiaodou.autopractise.domain.UserInfo;
import com.xiaodou.autopractise.enums.UserStatus;
import com.xiaodou.common.util.StringUtils;

public class UserHolder {

  private static UserHolder instance = new UserHolder();

  private final Set<String> userIdSet = Sets.newHashSet();
  private final Map<String, UserInfo> allUserMap = Maps.newHashMap();
  private final Queue<UserInfo> userInfoQ = Queues.newArrayBlockingQueue(10000);
  private final Map<String, UserInfo> userInfoMap = Maps.newConcurrentMap();
  private final Map<String, Long> userStatusMap = Maps.newConcurrentMap();
  private final Map<String, UserCourseInfo> userCourseMap = Maps.newConcurrentMap();

  public synchronized static UserHolder getInstance() {
    if (null == instance) {
      instance = new UserHolder();
    }
    return instance;
  }

  public synchronized void addUserInfo(UserInfo info) {
    if (null == info) {
      return;
    }
    if (!userIdSet.contains(info.getUserId())) {
      userIdSet.add(info.getUserId());
      if (UserStatus.INIT.getCode().equals(info.getStatus())
          || UserStatus.EXAMING.getCode().equals(info.getStatus())) {
        userInfoQ.add(info);
      }
      allUserMap.put(info.getUserId(), info);
    }
  }

  public synchronized void reAddUserInfo(String userId) {
    if (StringUtils.isBlank(userId)) {
      return;
    }
    if (userIdSet.contains(userId)) {
      UserInfo info = allUserMap.get(userId);
      if (!info.getFinish()) {
        userInfoQ.add(info);
      }
    }
  }

  public synchronized void markUserCourse(UserCourseInfo courseInfo) {
    userCourseMap.put(courseInfo.getUserId(), courseInfo);
  }

  public synchronized void clearUserCourse(String userId) {
    userCourseMap.remove(userId);
  }

  public synchronized UserCourseInfo getUserCourse(String userId) {
    return userCourseMap.get(userId);
  }

  public synchronized void refreshUserInfo(UserInfo info) {
    if (null == info || StringUtils.isBlank(info.getUserId())) {
      return;
    }
    UserInfo _userInfo = allUserMap.get(info.getUserId());
    _userInfo.setName(info.getName());
    _userInfo.setAbility(info.getAbility());
    _userInfo.setUserName(info.getUserName());
    _userInfo.setPasswd(info.getPasswd());
    _userInfo.setStatus(info.getStatus());
  }

  public synchronized UserInfo getUserInfo(String unique) {
    if (userInfoMap.containsKey(unique)) {
      return userInfoMap.get(unique);
    } else {
      UserInfo info = userInfoQ.poll();
      if (null != info) {
        userInfoMap.put(unique, info);
        userStatusMap.put(unique, System.currentTimeMillis());
      }
      return info;
    }
  }

  public synchronized void refreshUserInfoStatus(String unique) {
    if (userInfoMap.containsKey(unique)) {
      userStatusMap.put(unique, System.currentTimeMillis());
    }
  }

  public synchronized void finishUserInfo(String unique) {
    userInfoMap.remove(unique);
    userStatusMap.remove(unique);
  }

  public synchronized void releaseUserInfo(String unique) {
    if (!userInfoMap.containsKey(unique)) {
      return;
    } else {
      UserInfo info = userInfoMap.get(unique);
      if (null != info) {
        if (UserStatus.INIT.getCode().equals(info.getStatus())
            || UserStatus.EXAMING.getCode().equals(info.getStatus())) {
          userInfoQ.add(info);
        }
      }
      userInfoMap.remove(unique);
      userStatusMap.remove(unique);
    }
  }

  public List<UserInfo> getAllUser() {
    return Lists.newArrayList(allUserMap.values());
  }

  public UserInfo getUserInfoByUserId(String userId) {
    return allUserMap.get(userId);
  }

  public void deleteUserInfo(UserInfo userInfo) {
    allUserMap.remove(userInfo.getUserId());
    userIdSet.remove(userInfo.getUserId());
  }

  public void checkUserStatus() {
    Set<String> releaseSet = Sets.newHashSet();
    Long now = System.currentTimeMillis();
    for (Map.Entry<String, Long> userStatus : userStatusMap.entrySet()) {
      if (null == userStatus) {
        continue;
      }
      if (StringUtils.isBlank(userStatus.getKey())) {
        continue;
      }
      if (null == userStatus.getValue()) {
        userStatus.setValue(now);
        continue;
      }
      if (null != userStatus.getValue() && userStatus.getValue() + 3 * 60 * 1000 < now) {
        releaseSet.add(userStatus.getKey());
      }
    }
    if (null != releaseSet && releaseSet.size() > 0) {
      for (String key : releaseSet) {
        releaseUserInfo(key);
      }
    }

  }
}
