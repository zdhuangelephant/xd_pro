package com.xiaodou.userCenter.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiaodou.summer.util.HiLoGenerator;

public class CreditLogIDGenerator {

  public static String getSeqID() {
    HiLoGenerator idGenerator = HiLoGenerator.getIDGenerator("xd_user_credit_change_sequence_id");
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

}
