package com.xiaodou.autotest.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.util.Assert;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.autotest.core.deserialize.TransferHelper;
import com.xiaodou.autotest.core.deserialize.dmodel.MidAction;
import com.xiaodou.autotest.core.interfaces.Scheduler;
import com.xiaodou.autotest.core.loader.DefaultDocumentLoader;
import com.xiaodou.autotest.core.loader.DocumentLoader;
import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * @name @see com.xiaodou.autotest.core.ActionScheduler.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月15日
 * @description 用例调度器
 * @version 1.0
 */
public class ActionScheduler implements Scheduler {

  /** left_bracket 左中括号 */
  private static String left_bracket = "[";

  /** right_bracket 右中括号 */
  private static String right_bracket = "]";

  /** loader 文本读取器 */
  private static DocumentLoader loader = new DefaultDocumentLoader();

  /** scheduler 调度器单例 */
  private static ActionScheduler scheduler = new ActionScheduler();

  /** 获取单例方法 */
  public static ActionScheduler getInstance() {
    return scheduler;
  }

  /**
   * 入口方法
   * 
   * @param args
   */
  public static void main(String[] args) {
    ActionScheduler scheduler = ActionScheduler.getInstance();
    List<Action> actionList = scheduler.scheduleFromFile(args[0]);
    if (null != actionList && !actionList.isEmpty()) {
      FileWriter writer = null;
      try {
        writer = new FileWriter(new File("out.json"));
        String json = FastJsonUtil.toJson(actionList);
        writer.write(json);
      } catch (IOException e) {
        LoggerUtil.error("写入文件失败.", e);
      } finally {
        if (null != writer) {
          try {
            writer.flush();
            writer.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  @Override
  public void schedule(Action action) {
    Assert.notNull(action, "Scedule Action can't be null");
    Assert.hasText(action.getName(), "Scedule Action's name can't be null");
    Assert.hasText(action.getBusinessLine(), "Scedule Action's businessLine can't be null");
    Assert.hasText(action.getVersion(), "Scedule Action's version can't be null");
    Assert.notNull(action.getApiList(), "Scedule Action's apiList can't be null");
    action.excute();
  }

  @Override
  public void schedule(List<Action> actionList) {
    if (null == actionList || actionList.size() == 0) {
      return;
    }
    for (Action action : actionList) {
      schedule(action);
    }
  }

  @Override
  public List<Action> schedule(String actionInfo) {
    if (StringUtils.isJsonBlank(actionInfo)) {
      return null;
    }
    if (!actionInfo.startsWith(left_bracket)) {
      actionInfo = left_bracket + actionInfo;
    }
    if (!actionInfo.endsWith(right_bracket)) {
      actionInfo = actionInfo + right_bracket;
    }
    List<MidAction> dActionList =
        FastJsonUtil.fromJsons(actionInfo, new TypeReference<List<MidAction>>() {});
    List<Action> actionList = TransferHelper.transferList(dActionList);
    schedule(actionList);
    return actionList;
  }

  @Override
  public List<Action> scheduleFromFile(String filePath) {
    try {
      String actionInfo = loader.loadDocument(filePath);
      return schedule(actionInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Action> scheduleFromFile(File file) {
    try {
      String actionInfo = loader.loadDocument(file);
      return schedule(actionInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


}
