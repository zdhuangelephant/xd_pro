package com.xiaodou.oms.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiaodou.summer.util.HiLoGenerator;

public class IDGenerator {

  public static String getSeqID() {
    HiLoGenerator idGenerator = HiLoGenerator.getIDGenerator("xd_order_sequence_id");
    long subID = idGenerator.getNextID((byte) 0);
    String trainOrderID = getPrefixOrderID() + subID;
    return trainOrderID;
  }

  public static String getSeqID(String tableName) {
    HiLoGenerator idGenerator = HiLoGenerator.getIDGenerator(tableName);
    return String.valueOf(idGenerator.getIdFromDB(tableName));
  }

  private static String getPrefixOrderID() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    Date today = new Date();
    String prefixStr = format.format(today);
    return prefixStr;
  }
}
