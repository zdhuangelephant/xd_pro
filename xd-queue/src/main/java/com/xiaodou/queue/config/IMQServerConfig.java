package com.xiaodou.queue.config;



/**
 * @name @see com.xiaodou.queue.client.config.IMQServerConfig.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月22日
 * @description MQ服务端配置文件
 * @version 1.0
 */
public interface IMQServerConfig {
  public enum ConfigParam {
    /** ALIYUN_ACCESSKEYID */
    ALIYUN_ACCESSKEYID,
    /** ALIYUN_ACCESSKEYSECRET */
    ALIYUN_ACCESSKEYSECRET,
    /** ALIYUN_ACCOUNTENDPOINT */
    ALIYUN_ACCOUNTENDPOINT,
    /** ALIYUN_QUEUEMARKET */
    ALIYUN_QUEUEMARKET,
    /** ALIYUN_QUEUENUMBER */
    ALIYUN_QUEUENUMBER,
    /** ALIYUN_QUEUENAMEPREFIX 队列名称前缀 */
    ALIYUN_QUEUENAMEPREFIX,
    /** ALIYUN_QUEUEMESSAGEMAPPING 消息队列映射关系 */
    ALIYUN_QUEUEMESSAGEMAPPING,
    /** ALIYUN_QUEUESHORTNAMEMAPPING 消息队列简称映射关系 */
    ALIYUN_QUEUESHORTNAMEMAPPING,
    /** ALIYUN_BATCHLIMIT 批处理数量级 */
    ALIYUN_BATCHLIMIT
  }

  public String getParam(ConfigParam param);

}
