package com.xiaodou.wallet.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiaodou.summer.util.HiLoGenerator;

public class IDGenerator {

  public static String getSeqID() {
    HiLoGenerator idGenerator = HiLoGenerator.getIDGenerator("xd_account_wallet_bill_sequence_id");
    long subID = idGenerator.getNextID((byte) 0);
    String trainOrderID = getPrefixID() + subID;
    return trainOrderID;
  }

  private static String getPrefixID() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    Date today = new Date();
    String prefixStr = format.format(today);
    return prefixStr;
  }
  
  public static String getSeqTradeNo(int businessType) {
    HiLoGenerator idGenerator =
        HiLoGenerator.getIDGenerator("xd_account_wallet_bill_sequence_token");
    long subID = idGenerator.getNextID((byte) 0);
    String trainOrderID = String.format("%s%s", businessType, subID);
    return trainOrderID;
  }

}
