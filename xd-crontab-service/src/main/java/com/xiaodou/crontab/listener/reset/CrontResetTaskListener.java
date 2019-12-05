package com.xiaodou.crontab.listener.reset;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.springframework.util.Assert;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.crontab.engine.excutor.CrontExcutor;
import com.xiaodou.crontab.engine.model.JobEntity;
import com.xiaodou.crontab.instance.ServiceContext;
import com.xiaodou.crontab.util.NodeHelper;
import com.xiaodou.zookeeper.listener.AbstractNodeListener;

/**
 * @name @see com.xiaodou.crontab.listener.CrontTaskListener.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月17日
 * @description 重置调度任务监听器
 * @version 1.0
 */
public class CrontResetTaskListener extends AbstractNodeListener {

  @Override
  public void nodeChanged(CuratorFramework client, NodeCache cache) {
    if (null == cache || null == cache.getCurrentData() || null == cache.getCurrentData().getData())
      return;
    String sJobEntity = new String(cache.getCurrentData().getData());
    Assert.isTrue(StringUtils.isJsonNotBlank(sJobEntity), "执行任务项数据不能为空");
    JobEntity job = FastJsonUtil.fromJson(sJobEntity, JobEntity.class);
    if (null != job.getRetryContext() && job.getRetryContext().contains(ServiceContext.getId()))
      return;
    Assert.isTrue(StringUtils.isNotBlank(job.getConfigId()), "执行任务配置项ID不能为空");
    ServiceContext.pushJobData(job.getConfigId(), job);
    if (CrontExcutor.voteJob(job.getConfigId())) NodeHelper.deleteNode(client, cache);
  }

}
