package com.xiaodou.oms.util;

/**
 * @author Kai.Chen
 * @version V1.0
 * @date 14/11/20 下午8:39
 */
public class ValidateUtil {
    public static boolean validateParamsNotBlank(Object... params){
        boolean flag = true;
        for(Object param:params){
            flag&=validateNotBlank(param);
        }
        return flag;
    }

    public static boolean validateNotBlank(Object param){
        if(param==null){
            return false;
        }else if(param instanceof String){
            if("".equals(param)){
                return false;
            }
        }
        return true;
    }

}
