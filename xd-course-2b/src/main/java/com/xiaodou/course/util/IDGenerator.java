package com.xiaodou.course.util;


import com.xiaodou.summer.util.HiLoGenerator;

public class IDGenerator {

  public static String getSeqID() {
    HiLoGenerator idGenerator = HiLoGenerator.getIDGenerator("xd_resources_sequence_id");
    return Long.toString(idGenerator.getNextID((byte) 0));
  }

}
