package com.xiaodou.oms.util;

import com.xiaodou.common.util.FileUtil;


/**
 * @author Kai.Chen
 * @version V1.0
 * @date 14/11/11 上午11:13
 */
public final class FileUtils {

  public final static FileUtil PAYMENT_PROPERTIES = FileUtil
      .getInstance("/conf/custom/env/query_payment.properties");

  public final static FileUtil RABBITMQ_PROPERTIES = FileUtil
      .getInstance("/conf/custom/env/rabbitmq.properties");

  public final static FileUtil FRAUD_FILE_PROPERTIES = FileUtil
      .getInstance("/conf/custom/env/fraud.properties");
}
