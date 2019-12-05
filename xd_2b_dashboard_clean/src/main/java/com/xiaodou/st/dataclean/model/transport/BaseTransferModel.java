package com.xiaodou.st.dataclean.model.transport;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.source.jdbc.helper.RealSqlSessionKeyHolder;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * @name @see com.xiaodou.st.dataclean.model.transport.BaseTransferModel.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年7月23日
 * @description 业务基础传输模型
 * @version 1.0
 */
public class BaseTransferModel extends BaseValidatorPojo {
  /** module 模块ID */
  @NotEmpty
  private String module;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
    if (StringUtils.isNotBlank(module)) {
      setRealSqlSessionKey(module);
    }
  }

  /**
   * @description 设置多数据源key
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年6月20日
   * @param module
   */
  public void setRealSqlSessionKey(String module) {
    RealSqlSessionKeyHolder.getHolder().setIsUsekey(true);
    RealSqlSessionKeyHolder.getHolder().setRealSqlSessionKey(module);
  }
}
