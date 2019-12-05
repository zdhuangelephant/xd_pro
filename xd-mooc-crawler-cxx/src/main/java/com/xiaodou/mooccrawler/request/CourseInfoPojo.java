package com.xiaodou.mooccrawler.request;

import java.util.List;
import lombok.Data;
import com.google.common.collect.Lists;


/**
 * @name @see com.xiaodou.mooccrawler.request.CourseInfoPojo.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月25日
 * @description 课程信息参数类
 * @version 1.0
 */
@Data
public class CourseInfoPojo {
  private String id;
  private String name;
  private List<ChapterInfo> chapterArray = Lists.newArrayList();

  /**
   * @name @see com.xiaodou.mooccrawler.request.CourseInfoPojo.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年4月25日
   * @description 章信息参数类
   * @version 1.0
   */
  @Data
  public static class ChapterInfo {
    private String index;
    private String text;
    private List<ItemInfo> itemArray = Lists.newArrayList();
  }

  /**
   * @name @see com.xiaodou.mooccrawler.request.CourseInfoPojo.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年4月25日
   * @description 节信息参数类
   * @version 1.0
   */
  @Data
  public static class ItemInfo {
    private String index;
    private String text;
    private String href;
  }
}
