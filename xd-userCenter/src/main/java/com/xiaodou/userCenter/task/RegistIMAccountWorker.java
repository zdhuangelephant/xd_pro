package com.xiaodou.userCenter.task;

import java.util.List;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.im.request.RegistUserPojo;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.userCenter.model.UserModuleInfoModel;
import com.xiaodou.userCenter.service.IMManagerFactory;

@HandlerMessage("RegistIMAccount")
public class RegistIMAccountWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 681700950367469783L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    String messageBodyJson = message.getMessageBodyJson();
    if (StringUtils.isJsonBlank(messageBodyJson)) return;
    UserModuleInfoModel model = FastJsonUtil.fromJson(messageBodyJson, UserModuleInfoModel.class);
    RegistUserPojo pojo = new RegistUserPojo();
    pojo.setAccount(model.getUserName());
    pojo.setPassword(model.getUserName() + "@51xiaodou");
    pojo.setModule(model.getModule());
    IMManagerFactory imManagerFactory = SpringWebContextHolder.getBean("imManagerFactory");
    imManagerFactory.getSelfManager(model.getModule()).registUser(pojo);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    // TODO Auto-generated method stub

  }

}
