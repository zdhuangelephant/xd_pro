package com.xiaodou.oms.util;

import com.xiaodou.oms.constant.order.GorderTagsType;

/**
 * @author Kai.Chen
 * @version V1.0
 * @date 15/3/16 上午10:42
 */
public class GorderTagsUtil {
    /**
     * 判断是黄牛模式还是占座模式  true 黄牛 false 占座
     * @param tags
     * @return
     */
//    public static boolean isPurchasingAgencyOrHoldingToPay(Integer tags){
//        if(tags == null){
//            return true;
//        }
//        if(GorderTagsType.PurchasingAgency.getCode().equals(GorderTagsType.getPurchasingModeStat(tags))){
//            return true;
//        }else if(GorderTagsType.HoldingToPay.getCode().equals(GorderTagsType.getPurchasingModeStat(tags))){
//            return false;
//        }else{
//            return true;
//        }
//    }


    /**
     * 是否可以延时关单
     * @param tags
     * @return
     */
    public static boolean canDelayOrder(Integer tags){
        if (tags ==null){
            return true;
        }
        if((tags & Integer.valueOf(GorderTagsType.DelayOrderDisable.getCode()))==Integer.valueOf(GorderTagsType.DelayOrderDisable.getCode()) ){
            return false;
        }else{
            return true;
        }
    }

}
