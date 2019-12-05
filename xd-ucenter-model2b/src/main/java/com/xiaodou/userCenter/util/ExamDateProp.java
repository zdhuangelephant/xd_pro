package com.xiaodou.userCenter.util;
import com.xiaodou.common.util.FileUtil;
public class ExamDateProp {

  
  /**
   * 配置文件
   */
  private static FileUtil examDateFile = FileUtil.getInstance("/conf/custom/env/examDate_info.properties");
  
  /**
   * @return 获取配置文件信息
   */
  private static FileUtil getProperty(){
      if(examDateFile == null)
          synchronized (ExamDateProp.class) {
              if(examDateFile == null)
                  examDateFile = FileUtil.getInstance("/conf/custom/env/examDate_info.properties");
          }
      return examDateFile;
  }

  /**
   * 获取参数值
   * @param code 参数key
   * @return 参数值
   */
  public static String getParams(String code) {
      return getProperty().getProperties(code);
  }


}
