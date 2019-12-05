package com.xiaodou.userCenter.prop;
import com.xiaodou.common.util.FileUtil;


public class OpenCourseProp {
    
    /**
     * 配置文件
     */
    private static FileUtil prop = FileUtil.getInstance("/conf/custom/env/open_course.properties");
    
    private static FileUtil getProperty(){
        if(prop == null)
            synchronized (SevenCowProp.class) {
                if(prop == null)
                    prop = FileUtil.getInstance("/conf/custom/env/open_course.properties");
            }
        return prop;
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

