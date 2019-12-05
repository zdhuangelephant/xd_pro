package com.xiaodou.webfetch.param;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.webfetch.unique.IUnique;
import com.xiaodou.webfetch.unique.Target;

/**
 * @name @see com.xiaodou.webfetch.param.DependTaskGroup.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月13日
 * @description 依赖任务组
 * @version 1.0
 */
public class DependTaskGroup implements IUnique {

  public DependTaskGroup(Target depend) {
    this.target = depend;
  }

  private List<FetchTask> taskList = Lists.newArrayList();

  public List<FetchTask> getTaskList() {
    return taskList;
  }

  public void pushTask(FetchTask task) {
    this.taskList.add(task);
  }

  private Target target;

  @Override
  public String unique() {
    return target.unique();
  }

}
