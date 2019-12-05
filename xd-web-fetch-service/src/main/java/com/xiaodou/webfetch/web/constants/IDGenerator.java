package com.xiaodou.webfetch.web.constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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


  // mongdb业务Id
  public static String getSelfId() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  // mongdb业务Id
  public static Integer getRandoms(Integer max, Integer min) {
    if (max < 0) max = 800;
    if (min < 0) min = 0;
    Random random = new Random();
    return random.nextInt(max) % (max - min + 1) + min;
  }

}
