package com.xiaodou.queue.aliyun.config;

import java.util.Map;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.PagingListResult;
import com.aliyun.mns.model.QueueMeta;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.queue.config.IMQServerConfig.ConfigParam;

/**
 * @name @see com.xiaodou.queue.aliyun.AliyunConfig.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 阿里云配置中心
 * @version 1.0
 */
public class AliyunConfig {
  private Map<String, CloudQueue> msQMapping = Maps.newHashMap();
  private Map<String, CloudQueue> qMapping = Maps.newHashMap();
  private Map<String, String> qsMapping = Maps.newHashMap();

  private static AliyunConfig single;

  public static AliyunConfig getInstance() {
    if (null == single) synchronized (AliyunConfig.class) {
      if (null == single) single = new AliyunConfig();
    }
    return single;
  }

  public static final CloudQueue getQueueByMesName(String messageName) {
    return getInstance().msQMapping.get(messageName);
  }

  public static final CloudQueue getQueueByName(String messageName) {
    return getInstance().qMapping.get(messageName);
  }

  public static final String getQueueByShortName(String messageName) {
    return getInstance().qsMapping.get(messageName);
  }

  public static final String getParam(ConfigParam configParam) {
    return AliyunMQServerConfig.getInstance().getParam(configParam);
  }

  public static final Integer getBatchLimit() {
    if (StringUtils.isNotBlank(AliyunMQServerConfig.getInstance().getParam(
        ConfigParam.ALIYUN_BATCHLIMIT))) {
      try {
        return Integer.parseInt(AliyunMQServerConfig.getInstance().getParam(
            ConfigParam.ALIYUN_BATCHLIMIT));
      } catch (Exception e) {}
    }
    return 1;
  }

  private AliyunConfig() {
    try {
      String accessKeyId =
          AliyunMQServerConfig.getInstance().getParam(ConfigParam.ALIYUN_ACCESSKEYID);
      String accessKeySecret =
          AliyunMQServerConfig.getInstance().getParam(ConfigParam.ALIYUN_ACCESSKEYSECRET);
      String accountEndpoint =
          AliyunMQServerConfig.getInstance().getParam(ConfigParam.ALIYUN_ACCOUNTENDPOINT);
      String prefix =
          AliyunMQServerConfig.getInstance().getParam(ConfigParam.ALIYUN_QUEUENAMEPREFIX);
      Integer parseInt =
          Integer.parseInt(AliyunMQServerConfig.getInstance().getParam(
              ConfigParam.ALIYUN_QUEUENUMBER));
      if (StringUtils.isAllBlank(accessKeyId, accessKeySecret, accountEndpoint, prefix,
          parseInt.toString())) {
        throw new RuntimeException("未找到阿里云配置文件.");
      }
      CloudAccount account = new CloudAccount(accessKeyId, accessKeySecret, accountEndpoint);
      MNSClient client = account.getMNSClient();
      PagingListResult<QueueMeta> queList = client.listQueue(prefix, StringUtils.EMPTY, parseInt);
      for (QueueMeta queueMeta : queList.getResult()) {
        qMapping.put(queueMeta.getQueueName(), client.getQueueRef(queueMeta.getQueueName()));
      }
      String[] messageQueueMapping =
          AliyunMQServerConfig.getInstance().getParam(ConfigParam.ALIYUN_QUEUEMESSAGEMAPPING)
              .split(";");
      for (String mqMapping : messageQueueMapping) {
        String[] mapping = mqMapping.split(":");
        if (mapping.length < 2 || StringUtils.isOrBlank(mapping))
          throw new IllegalArgumentException("消息名队列映射关系错误.");
        if (!qMapping.containsKey(mapping[1]))
          throw new IllegalArgumentException(mapping[1] + "队列不存在");
        msQMapping.put(mapping[0], qMapping.get(mapping[1]));
      }
      String[] queueShortNameMapping =
          AliyunMQServerConfig.getInstance().getParam(ConfigParam.ALIYUN_QUEUESHORTNAMEMAPPING)
              .split(";");
      for (String shortName : queueShortNameMapping) {
        String[] mapping = shortName.split(":");
        if (mapping.length < 2 || StringUtils.isOrBlank(mapping))
          throw new IllegalArgumentException("队列简称映射关系错误.");
        if (!qMapping.containsKey(mapping[1]))
          throw new IllegalArgumentException(mapping[1] + "队列不存在");
        qsMapping.put(mapping[0], mapping[1]);
      }
    } catch (Exception e) {
      LoggerUtil.error("初始化阿里雲配置文件失敗", e);
    }
  }
}
