package com.xiaodou.autotest.core.interfaces;

import java.io.File;
import java.util.List;

import com.xiaodou.autotest.core.model.Action;

/**
 * @name @see com.xiaodou.autotest.core.interfaces.Scheduler.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月15日
 * @description 标准行为:用例调度
 * @version 1.0
 */
public interface Scheduler {

  /**
   * 调度一个用例
   * 
   * @param action 用例
   */
  public void schedule(Action action);

  /**
   * 调度一组用例
   * 
   * @param actionList 用例组
   */
  public void schedule(List<Action> actionList);

  /**
   * 从文本中获取用例并调度
   * 
   * @param actionInfo 用例文本
   * @return 用例组
   */
  public List<Action> schedule(String actionInfo);

  /**
   * 从文件中获取用例并调度
   * 
   * @param filePath 文件路径
   * @return 用例组
   */
  public List<Action> scheduleFromFile(String filePath);

  /**
   * 从文件中获取用例并调度
   * 
   * @param file 文件
   * @return 用例组
   */
  public List<Action> scheduleFromFile(File file);

}
