package com.xiaodou.server.pay.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @name @see com.xiaodou.server.pay.enums.BusinessStatus.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月27日
 * @description 业务状态
 * @version 1.0
 */
public enum BusinessStatus {

  INIT_TOKEN(0), FIRST_PAY(1), FIRST_REFUND(-1), TWICE_PAY(10), TWICE_REFUND(-10);

  BusinessStatus(int code) {
    this.code = code;
  }

  private int code;

  public int getCode() {
    return code;
  }

  private static final Map<Integer, BusinessStatus> statusMap =
      new HashMap<Integer, BusinessStatus>() {
        /** serialVersionUID */
        private static final long serialVersionUID = -4008154721832076699L;

        public HashMap<Integer, BusinessStatus> initThis() {
          for (BusinessStatus status : BusinessStatus.values())
            this.put(status.code, status);
          return this;
        }
      }.initThis();

  public static BusinessStatus getByCode(int code) {
    BusinessStatus status = statusMap.get(code);
    if (null == status) return INIT_TOKEN;
    return status;
  }

}
