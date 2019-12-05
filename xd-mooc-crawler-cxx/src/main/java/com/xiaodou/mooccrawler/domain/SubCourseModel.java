package com.xiaodou.mooccrawler.domain;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.collect.Lists;
import com.xiaodou.mooccrawler.service.FileUtilService;
import com.xiaodou.mooccrawler.service.QiniuMediaUtilService;
import com.xiaodou.mooccrawler.vo.QiNiuMedia;
import com.xiaodou.mooccrawler.vo.SubCourseList;
import com.xiaodou.mooccrawler.vo.SubCourseList.SubCourse;
import com.xiaodou.mooccrawler.vo.SubCourseList.Video;
import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@Data
@EqualsAndHashCode(callSuper = true)
@CollectionName("qiniuSubCourse")
public class SubCourseModel extends MongoBaseModel {
  @Pk
  private String subCourseId;
  private Integer banxingType;
  private String banxingTypeName;
  private String bigPicUrl;
  private Integer courseId;
  private String description;
  private String itemContent;
  private String kcname;
  private Integer keshi;
  private String picUrl;
  private String teacher;
  private List<Chapter> chapterList = Lists.newArrayList();

  public SubCourseModel(CourseModel course) {
    this.banxingType = course.getBanxingType();
    this.banxingTypeName = course.getBanxingTypeName();
    this.bigPicUrl = course.getBigPicUrl();
    this.subCourseId = course.getSubCourseId();
    this.courseId = course.getCourseId();
    this.description = course.getDescription();
    this.itemContent = course.getItemContent();
    this.kcname = course.getKcname();
    this.keshi = course.getKeshi();
    this.picUrl = course.getPicUrl();
    this.teacher = course.getTeacher();
  }

  public void setScList(SubCourseList scList) {
    for (SubCourse sc : scList.getV()) {
      this.chapterList.add(new Chapter(sc));
    }
  }

  @Data
  public static class Chapter {
    private String id;
    private String name;
    private List<Video> videos;

    public Chapter(SubCourse sc) {
      this.id = sc.getId().toString();
      this.name = sc.getName();
      this.videos = sc.getVideos();
    }
  }

  public void redownLoadVideo(FileUtilService fileUtilService) {
    if (null == this.chapterList || this.chapterList.isEmpty()) {
      return;
    }
    List<Chapter> newCList = Lists.newArrayList();
    for (Chapter c : chapterList) {
      if (null == c.videos || c.videos.isEmpty()) {
        return;
      }
      List<Video> newVList = Lists.newArrayList();
      for (Video v : c.videos) {
        String oldUrl = v.getVideoUrl();
        String newUrl = oldUrl.replaceAll("tk.360xkw.com", "s1.v.360xkw.com");
        v.setVideoUrl(newUrl);
        String name = UUID.randomUUID().toString() + QiniuMediaUtilService.MP4;
        if (fileUtilService.downLoadByUrl(v.getVideoUrl(), name)) {
          v.setQiniuUrl(QiNiuMedia.DOMAIN + name);
          newVList.add(v);
        }
      }
      if (null != newVList && !newVList.isEmpty()) {
        c.videos = newVList;
        newCList.add(c);
      }
    }
    this.chapterList = newCList;
  }
}
