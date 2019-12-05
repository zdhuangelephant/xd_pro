package com.xiaodou.ucenter.util;


import com.xiaodou.summer.util.HiLoGenerator;

public class IDGenerator {

  public static String getSeqID(String module) {
    HiLoGenerator idGenerator = HiLoGenerator.getIDGenerator("xd_user_sequence_id");
    long subID = idGenerator.getNextID((byte) 0);
    String trainOrderID = String.format("%s%s", module, subID);
    return trainOrderID;
  }

}
