package com.xiaodou.ucenter.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiaodou.summer.util.HiLoGenerator;

public class IDGenerator {

  public static String getSeqID() {
    HiLoGenerator idGenerator = HiLoGenerator.getIDGenerator("xd_base_user_sequence_id");
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
