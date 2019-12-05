package com.xiaodou.common.cache.redis;

import com.xiaodou.common.cache.redis.model.proxy.pipeline.IPipeLineProxy;

/**
 * @name @see com.xiaodou.common.cache.redis.IJedisPipeLineOperation.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年7月5日
 * @description PipeLine批量操作拓展类
 * @version 1.0
 */
public interface IJedisPipeLineOperation {

  public void operationWithPipeLine(IPipeLineProxy<?> pipeLine);

}
