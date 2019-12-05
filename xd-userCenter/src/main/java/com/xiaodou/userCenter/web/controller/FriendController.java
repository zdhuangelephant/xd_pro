package com.xiaodou.userCenter.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.userCenter.request.FriendAddRequest;
import com.xiaodou.userCenter.request.FriendDelRequest;
import com.xiaodou.userCenter.request.FriendListRequest;
import com.xiaodou.userCenter.service.FriendService;

/**
 * @name @see com.xiaodou.userCenter.web.controller.FriendController.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月1日
 * @description 好友关系controller
 * @version 1.0
 */
@Controller
@RequestMapping("friend")
public class FriendController extends BaseController {

  @Resource
  FriendService friendService;

  /**
   * 发起好友申请。 · url /friend/add_friend
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("add_friend")
  public String addFriend(HttpServletRequest request) {
    return doMain(request, new IController<FriendAddRequest>() {
      @Override
      public ResultInfo doService(FriendAddRequest pojo, HttpServletRequest request)
          throws Exception {
        return friendService.addFriend(pojo);
      }

      @Override
      public FriendAddRequest buildRequest(HttpServletRequest request) throws Exception {
        return getParamsValue(request, FriendAddRequest.class);
      }

    });
  }

  /**
   * 5、 删除好友。 · url /friend/del_friend
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("del_friend")
  public String delFriend(HttpServletRequest request) {
    return doMain(request, new IController<FriendDelRequest>() {
      @Override
      public ResultInfo doService(FriendDelRequest pojo, HttpServletRequest request)
          throws Exception {
        return friendService.delFriend(pojo);
      }

      @Override
      public FriendDelRequest buildRequest(HttpServletRequest request) throws Exception {
        return getParamsValue(request, FriendDelRequest.class);
      }

    });
  }

  /**
   * 6、 好友列表。 · url /friend/list_friend
   * 
   * @param pojo
   */
  @ResponseBody
  @RequestMapping("list_friend")
  public String listFriend(HttpServletRequest request) {
    return doMain(request, new IController<FriendListRequest>() {
      @Override
      public ResultInfo doService(FriendListRequest pojo, HttpServletRequest request)
          throws Exception {
        return friendService.listFriend(pojo);
      }

      @Override
      public FriendListRequest buildRequest(HttpServletRequest request) throws Exception {
        return new FriendListRequest();
      }

    });
  }

}
