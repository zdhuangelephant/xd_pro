package com.xiaodou.resources.cache.quesbk;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

public class QuesExamCache {

  private static final Map<String, Record> recordMap = Maps.newConcurrentMap();

  private static class Record {
    private Long total;
    private AtomicLong dealed = new AtomicLong(0);
    private AtomicInteger looper = new AtomicInteger(0);

    Record(Long total) {
      this.total = total;
    }
  }

  public static void incrExamRecord(String tag) {
    Record record = recordMap.get(tag);
    if (null != record) {
      record.dealed.incrementAndGet();
    }
  }

  public static void initTotalExamRecord(String tag) {
    if (StringUtils.isBlank(tag)) return;
    if (recordMap.containsKey(tag)) {
      recordMap.remove(tag);
    } else {
      recordMap.put(tag, new Record(0l));
    }
  }

  public static void setTotalExamRecord(String tag, Long count) {
    Record record = recordMap.get(tag);
    if (null == record || record.total != 0) return;
    record.total = count;
  }

  public static boolean isAllDealed(String tag) {
    Record record = recordMap.get(tag);
    if (null != record) {
      if (record.looper.incrementAndGet() > 10) throw new RuntimeException("处理错题统计异常.");
      return record.dealed.get() >= record.total;
    }
    return false;
  }

  public static boolean isValidExamRecord(String tag) {
    return recordMap.containsKey(tag);
  }

  public static void delExamRecord(String tag) {
    recordMap.remove(tag);
  }

}
