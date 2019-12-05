package com.xiaodou.autotest.web.prpo;

import com.xiaodou.common.util.FileUtil;


/**
 * @name @see com.xiaodou.autotest.web.prpo.MailProp.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年09月25日
 * @description 邮件配置信息获取
 * @version 1.0
 */
public class MailProp {
  /**
   * 配置文件
   */
  private static FileUtil errFile = FileUtil
      .getInstance("/conf/custom/env/mail.properties");

  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty() {
    if (errFile == null){
      synchronized (MailProp.class) {
        if (errFile == null){
          errFile = FileUtil.getInstance("/conf/custom/env/mail.properties");
        }
      }
    }
    return errFile;
  }


	/**
	 * 获取参数值
	 * 
	 * @param code
	 *            参数key
	 * @return 参数值
	 */
	public static String getParams(String code) {
		return getProperty().getProperties(code);
	}

}
