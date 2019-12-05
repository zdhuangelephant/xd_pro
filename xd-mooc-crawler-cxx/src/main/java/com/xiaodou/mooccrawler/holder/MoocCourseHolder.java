package com.xiaodou.mooccrawler.holder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mooccrawler.domain.MoocItem;

public class MoocCourseHolder {

  public static MoocCourseProcessor processor;

  public static synchronized MoocCourseProcessor getProcessor(String courseId) {
    if (processor == null) {
      processor = new MoocCourseProcessor();
      processor.setCourseId(courseId);
      return processor;
    }
    return processor.find(courseId);
  }

  public static synchronized MoocItem renew() {
    if (null == processor) {
      return null;
    }
    return processor.renew();
  }

  public static synchronized MoocItem check(String url) {
    if (null == processor) {
      return null;
    }
    return processor.check(url);
  }

  public static synchronized void finish(String url) {
    if (null == processor) {
      return;
    }
    processor.finish(url);
  }

  public static List<MoocCourseStatistic> getStatistic() {
    List<MoocCourseStatistic> statisticList = Lists.newArrayList();
    if (null == processor) {
      return statisticList;
    }
    processor.fetchStatistic(statisticList);
    return statisticList;
  }

  @Data
  public static class MoocCourseStatistic {
    private String courseId;
    private Integer totalCount;
    private Integer waitCount;
    private Integer processingCount;
    private Integer finishCount;

    public MoocCourseStatistic(MoocCourseProcessor processor) {
      this.courseId = processor.getCourseId();
      this.totalCount = processor.getTotalCount();
      this.waitCount = processor.getWait4ProcessCount();
      this.processingCount = processor.getProcessingCount();
      this.finishCount = processor.getFinshCount();
    }
  }

  @Data
  public static class MoocCourseProcessor {
    private String courseId;
    private Set<String> itemIdSet = Sets.newHashSet();
    private List<MoocItem> wait4Process = Lists.newArrayList();
    private Map<String, MoocItem> processing = Maps.newHashMap();
    private List<MoocItem> finish = Lists.newArrayList();
    private Integer totalCount = 0;
    private MoocCourseProcessor next;

    public void push(MoocItem item) {
      if (itemIdSet.contains(item.getItemId())) {
        return;
      }
      if (item.getDownload()) {
        finish.add(item);
      } else {
        wait4Process.add(item);
      }
      totalCount++;
      itemIdSet.add(item.getItemId());
    }

    public MoocCourseProcessor find(String courseId) {
      if (StringUtils.isNotBlank(this.courseId) && this.courseId.equals(courseId)) {
        return this;
      } else if (null == this.next) {
        next = new MoocCourseProcessor();
        next.setCourseId(courseId);
        return next;
      } else {
        return next.find(courseId);
      }
    }

    public synchronized MoocItem renew() {
      if (null != wait4Process && !wait4Process.isEmpty()) {
        MoocItem moocItem = wait4Process.get(0);
        wait4Process.remove(0);
        processing.put(moocItem.getHref(), moocItem);
        return moocItem;
      } else if (null != next) {
        return next.renew();
      } else {
        return null;
      }
    }

    public synchronized void finish(String url) {
      if (processing.containsKey(url)) {
        MoocItem item = processing.get(url);
        processing.remove(url);
        finish.add(item);
      } else if (null != next) {
        next.finish(url);
      } else {
        return;
      }
    }

    public synchronized MoocItem check(String url) {
      if (processing.containsKey(url)) {
        return processing.get(url);
      } else if (null != next) {
        return next.check(url);
      } else {
        return null;
      }
    }

    public synchronized void fetchStatistic(List<MoocCourseStatistic> statisticList) {
      if (null == statisticList) return;
      statisticList.add(new MoocCourseStatistic(this));
      if (null != next) {
        next.fetchStatistic(statisticList);
      }
    }

    public Integer getTotalCount() {
      return totalCount;
    }

    public Integer getWait4ProcessCount() {
      return wait4Process.size();
    }

    public Integer getProcessingCount() {
      return processing.size();
    }

    public Integer getFinshCount() {
      return finish.size();
    }
  }
}
