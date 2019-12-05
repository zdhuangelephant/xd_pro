package com.xiaodou.autopractise.engine;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xiaodou.autopractise.dao.UserInfoDao;
import com.xiaodou.autopractise.domain.UserInfo;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.sceduling.common.SummerCommonScheduledExecutor;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

@Component
public class UserInit {

  @Resource
  UserInfoDao userInfoDao;

  /** initialDelay 初始调度任务时机 */
  private Integer initialDelay = 0;

  /** productListDelay 产品列表刷新延迟 */
  private Integer productListDelay = 60;

  @PostConstruct
  public void init() {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(UserInfo.class));
    Page<UserInfo> userInfoPage = userInfoDao.findEntityListByCond(param, null);
    if (null == userInfoPage || null == userInfoPage.getResult()
        || userInfoPage.getResult().isEmpty()) {
      return;
    }
    for (UserInfo userInfo : userInfoPage.getResult()) {
      UserHolder.getInstance().addUserInfo(userInfo);
    }
    SummerCommonScheduledExecutor.getExecutor().scheduleWithFixedDelay(new SummerScheduledTask() {

      @Override
      public void onException(Throwable t) {
        LoggerUtil.error("校验用户状态失败", t);
      }

      @Override
      public void doMain() {
        UserHolder.getInstance().checkUserStatus();
      }
    }, initialDelay, productListDelay, TimeUnit.SECONDS);
  }

}
