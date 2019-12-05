package com.xiaodou.userCenter.util;

import com.xiaodou.userCenter.constant.UcenterModelConstant;

public class WalletUtil {

    /**
     * 根据module格式化productLine
     * 
     * @param module
     * @return
     */
    public static String formatProductLine() {
        String module = UcenterModelConstant.WALLET_PRODUCT_LINE;
        return module.length() > 2
                ? module.substring(module.length() - 2)
                : module.length() == 1 ? String.format("0%s", module) : module;
    }

}
