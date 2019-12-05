package com.xiaodou.mooccrawler.vo;

import java.util.List;

import lombok.Data;

@Data
public class SubCourseList {
  private List<SubCourse> V;

  @Data
  public static class SubCourse {
    private Integer id;
    private String name;
    private List<Video> videos;
  }

  @Data
  public static class Video {
    private Integer courseId;
    private Integer chapterId;
    private Integer id;
    private Integer subcourseId;
    private String thumbUrl;
    private String upLoadTime;
    private String videoTitle;
    private String videoUrl;
    private String qiniuUrl;
  }
}
