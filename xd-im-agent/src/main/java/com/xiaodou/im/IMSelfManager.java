package com.xiaodou.im;

import com.xiaodou.im.request.DeleteUserPojo;
import com.xiaodou.im.request.LoginPojo;
import com.xiaodou.im.request.LogoffPojo;
import com.xiaodou.im.request.ModifyInfoPojo;
import com.xiaodou.im.request.ModifyPasswdPojo;
import com.xiaodou.im.request.RegistUserPojo;
import com.xiaodou.im.response.IMResponse;

/**
 * @name @see com.xiaodou.im.IMRigister.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月2日
 * @description 个人账号管理interface
 * @version 1.0
 */
public interface IMSelfManager {

  /**
   * 注册用户
   */
  public IMResponse registUser(RegistUserPojo pojo);

  /**
   * 修改信息
   */
  public IMResponse modifyInfo(ModifyInfoPojo pojo);

  /**
   * 修改密码
   */
  public IMResponse modifyPasswd(ModifyPasswdPojo pojo);

  /**
   * 删除用户
   */
  public IMResponse deleteUser(DeleteUserPojo pojo);

  /**
   * 登录
   */
  public IMResponse login(LoginPojo pojo);

  /**
   * 登出
   */
  public IMResponse logoff(LogoffPojo pojo);

}
