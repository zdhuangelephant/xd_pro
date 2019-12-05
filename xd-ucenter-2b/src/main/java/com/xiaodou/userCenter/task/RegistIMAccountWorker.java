//package com.xiaodou.userCenter.task;
//
//import java.util.List;
//
//import com.xiaodou.common.util.StringUtils;
//import com.xiaodou.common.util.log.LoggerUtil;
//import com.xiaodou.common.util.warp.FastJsonUtil;
//import com.xiaodou.im.request.RegistUserPojo;
//import com.xiaodou.im.response.IMResponse;
//import com.xiaodou.queue.annotation.HandlerMessage;
//import com.xiaodou.queue.callback.IMQCallBacker;
//import com.xiaodou.queue.message.DefaultMessage;
//import com.xiaodou.queue.worker.AbstractDefaultWorker;
//import com.xiaodou.summer.util.SpringWebContextHolder;
//import com.xiaodou.userCenter.constant.UcenterModelConstant;
//import com.xiaodou.userCenter.model.UserModel;
//import com.xiaodou.userCenter.service.IMManagerFactory;
//
//@HandlerMessage("RegistIMAccount")
//public class RegistIMAccountWorker extends AbstractDefaultWorker {
//
//  /** serialVersionUID */
//  private static final long serialVersionUID = 681700950367469783L;
//
//  private Integer registTimeLimit = 3;
//
//  @Override
//  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
//    String messageBodyJson = message.getMessageBodyJson();
//    if (StringUtils.isJsonBlank(messageBodyJson)) return;
//    UserModel model = FastJsonUtil.fromJson(messageBodyJson, UserModel.class);
//    RegistUserPojo pojo = new RegistUserPojo();
//    pojo.setAccount(model.getXdUniqueId().toString());
//    pojo.setPassword(model.getXdUniqueId() + "@51xiaodou");
//    pojo.setModule(UcenterModelConstant.MODULE);
//    IMManagerFactory imManagerFactory = SpringWebContextHolder.getBean("imManagerFactory");
//    for (int i = 0; i < registTimeLimit; i++) {
//      IMResponse response = registMember(imManagerFactory, UcenterModelConstant.MODULE, pojo);
//      if (response.isRetOK()) return;
//    }
//    // TODO alarm
//    LoggerUtil.error("注册IM用户信息失败", new RuntimeException());
//  }
//
//  public IMResponse registMember(IMManagerFactory imManagerFactory, String module,
//      RegistUserPojo pojo) {
//    return imManagerFactory.getSelfManager(module).registUser(pojo);
//  }
//
//  @Override
//  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
//    // TODO Auto-generated method stub
//
//  }
//
//  @Override
//  public void onException(Throwable t, List<DefaultMessage> message,
//      IMQCallBacker<List<DefaultMessage>> callback) {
//    // TODO Auto-generated method stub
//
//  }
//
//  @Override
//  public void onException(Throwable t, DefaultMessage message,
//      IMQCallBacker<DefaultMessage> callback) {
//    // TODO Auto-generated method stub
//
//  }
//
//}
