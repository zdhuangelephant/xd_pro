package com.xiaodou.oms.service.message;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.im.agent.qq.QQClient;
import com.xiaodou.im.agent.qq.response.PollResponse;
import com.xiaodou.im.agent.qq.util.IMRedisUtil;
import com.xiaodou.im.agent.qq.util.UrlRedisUtil;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.oms.util.model.IMEntity;
import com.xiaodou.oms.vo.input.message.InitQQPojo;
import com.xiaodou.oms.vo.input.message.InitQQUrlPojo;
import com.xiaodou.oms.vo.input.message.SendQQMsgPojo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * Date: 2014/12/25 Time: 16:35
 * 
 * @author Tian.Dong
 */
@Service
public class OmsMessageService {
  QQClient client = new QQClient();

  /*
   * public LoginCheckVo loginCheck(LoginCheckPojo pojo) { LoginCheckVo loginCheckVo; try {
   * loginCheckVo = client.loginCheck(pojo); return loginCheckVo; } catch (Exception e) {
   * LoggerUtil.error("OmsMessageService loginCheck",e); loginCheckVo = new LoginCheckVo();
   * loginCheckVo.setCode(-1); loginCheckVo.setData(e.getMessage()); return loginCheckVo; } }
   * 
   * public LoginCheckVo refreshImage(LoginCheckPojo pojo) { LoginCheckVo loginCheckVo; try {
   * loginCheckVo = client.freshImage(pojo); return loginCheckVo; } catch (Exception e) {
   * LoggerUtil.error("OmsMessageService loginCheck",e); loginCheckVo = new LoginCheckVo();
   * loginCheckVo.setCode(-1); loginCheckVo.setData(e.getMessage()); return loginCheckVo; } }
   * 
   * public LoginVo login(LoginPojo pojo){ LoginVo loginVo = null; try { loginVo =
   * client.login(pojo); return loginVo; } catch (Exception e) {
   * LoggerUtil.error("OmsMessageService login",e); loginVo = new LoginVo(); loginVo.setCode(-1);
   * loginVo.setDesc("系统异常 "+e.getMessage()); return loginVo; } }
   */

  public ResultInfo initQQ(InitQQPojo pojo) {
    // 写redis
    try {
      IMRedisUtil.setCookie(pojo.getCookie());
      IMRedisUtil.setPsessionid(pojo.getPsessionId());
      IMRedisUtil.setPtwebqq(pojo.getPtwebqq());
      IMRedisUtil.setVfwebqq(pojo.getVfwebqq());
      IMRedisUtil.setUin(pojo.getUin());
    } catch (Exception e) {
      LoggerUtil.error("initQQ", e);
      return new ResultInfo(ResultType.SYSFAIL);
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  public Object initQQUrl(InitQQUrlPojo pojo) {
    // 写redis
    try {
      UrlRedisUtil.setDISCUS_LIST_URL(pojo.getDiscusList());
      UrlRedisUtil.setGROUP_LIST_URL(pojo.getGroupList());
      UrlRedisUtil.setSEND_BUDDY_MSG_URL(pojo.getSendBuddyMsg());
      UrlRedisUtil.setSEND_DISCUS_MSG_URL(pojo.getSendDiscusMsg());
      UrlRedisUtil.setSEND_GROUP_MSG_URL(pojo.getSendGroupMsg());
      UrlRedisUtil.setPOLL_URL(pojo.getPoll());
      UrlRedisUtil.setREFER_URL(pojo.getRefer());
    } catch (Exception e) {
      LoggerUtil.error("initQQ", e);
      return new ResultInfo(ResultType.SYSFAIL);
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  public Object setVersion(String version) {
    try {
      // IMRedisUtil.setVersion(version);
    } catch (Exception e) {
      LoggerUtil.error("setVersion", e);
      return new ResultInfo(ResultType.SYSFAIL);
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  public ResultInfo logout() {
    // 写redis
    try {
      IMRedisUtil.removeAll();
      IMRedisUtil.setIsPoll("false");
      IMRedisUtil.setIsInit("false");
      // IMRedisUtil.setVersion(UUID.randomUUID().toString());
    } catch (Exception e) {
      LoggerUtil.error("IMRedisUtil.removeAll", e);
      return new ResultInfo(ResultType.SYSFAIL);
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  public ResultInfo sendMsg(SendQQMsgPojo pojo) {
    try {
      switch (pojo.getType()) {
        case "group":
          client.sendGroupMsg(pojo.getContent(), pojo.getName());
          break;
        case "discus":
          client.sendDiscusMsg(pojo.getContent(), pojo.getName());
          break;
        default:
          break;
      }
    } catch (Exception e) {
      LoggerUtil.error("sendMsg异常", e);
      return new ResultInfo(ResultType.SYSFAIL);
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  public ResultInfo poll() {
    if (IMRedisUtil.getIsPoll() != null && IMRedisUtil.getIsPoll().equals("true")) {
      ResultInfo resultInfo = new ResultInfo(ResultType.SYSFAIL);
      resultInfo.setRetdesc("已经开启了poll线程");
      return resultInfo;
    }
    if (IMRedisUtil.getIsPoll() == null || IMRedisUtil.getIsPoll().equals("false")) {
      IMRedisUtil.setIsPoll("true");
      OrderLoggerUtil.imInfo("poll线程启动");
      // 开线程
      new Thread() {
        public void run() {
          // 这里严格一些，不是false就一直轮询
          // 接口close_poll 会把redis中IsPoll置为false
          while (!"false".equals(getIsPollFlag())) {
            try {
              Thread.sleep(5000);
              PollResponse pollResponse = client.poll();
              IMEntity entity = new IMEntity();
              entity.setResponse(pollResponse);
              OrderLoggerUtil.imInfo(entity);
            } catch (Exception e) {
              LoggerUtil.error("qq client poll", e);
            }
          }
          OrderLoggerUtil.imInfo("poll线程终止:pollFlag=" + getIsPollFlag());
        }
      }.start();
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  /**
   * 有时候redis取出来是null，重试3次
   */
  private String getIsPollFlag() {
    String flag = null;
    int t = 3;
    while (flag == null && t-- > 0) {
      flag = IMRedisUtil.getIsPoll();
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        LoggerUtil.error("getIsPollFlag", e);
      }
    }
    return flag;
  }

  public Object closePoll() {
    // 写redis
    try {
      IMRedisUtil.setIsPoll("false");
    } catch (Exception e) {
      LoggerUtil.error("IMRedisUtil.setIsPoll", e);
      return new ResultInfo(ResultType.SYSFAIL);
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  public Object closeInit() {
    // 写redis
    try {
      IMRedisUtil.setIsInit("false");
    } catch (Exception e) {
      LoggerUtil.error("IMRedisUtil.setIsInit", e);
      return new ResultInfo(ResultType.SYSFAIL);
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  /**
   * 批处理 关闭qq
   */
  public ResultInfo taskShutdown() {
    return this.logout();
  }

  /**
   * 批处理 开启qq
   */
  // public ResultInfo taskStart(LoginCheckPojo pojo) {
  // LoginCheckVo vo = this.loginCheck(pojo);
  // if(vo.getCode() != 0) {
  // return new ResultInfo(ResultType.SYSFAIL);
  // }
  // IMRedisUtil.setVersion(UUID.randomUUID().toString());
  // return this.poll();
  // }
}
