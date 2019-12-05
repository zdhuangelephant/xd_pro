package com.xiaodou.userCenter.web.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
   * @throws Exception 
   */
  @ResponseBody
  @RequestMapping("add_friend")
  public String addFriend(FriendAddRequest request) throws Exception {
    return friendService.addFriend(request).toJsonString();
  }

  /**
   * 5、 删除好友。 · url /friend/del_friend
   * 
   * @param pojo
   * @throws Exception 
   */
  @ResponseBody
  @RequestMapping("del_friend")
  public String delFriend(FriendDelRequest request) throws Exception {
    return friendService.delFriend(request).toJsonString();
  }

  /**
   * 6、 好友列表。 · url /friend/list_friend
   * 
   * @param pojo
   * @throws Exception 
   */
  @ResponseBody
  @RequestMapping("list_friend")
  public String listFriend(FriendListRequest request) throws Exception {
    return friendService.listFriend(request).toJsonString();
  }

}
