package com.xiaodou.webfetch.web.domain;

import java.util.Date;

import com.xiaodou.summer.dao.mongo.annotion.CollectionName;
import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知乎的专栏数据模型
 * 
 * @name Column CopyRright (c) 2017 by <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 *
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2017年12月28日
 * @description TODO
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@CollectionName("zhihu_column")
public class Column extends MongoBaseModel {
  /** businessId 逻辑/业务主键 */
  @Pk
  private String seflId;
  /** signature 知乎搜索接口Unique标识 */
  private String signature;
  /** updateTime 更新时间 */
  private Date updateTime = new Date();
  /** isTitleImageFullScreen 标题配图是否全屏 */
  private Boolean isTitleImageFullScreen;
  /** rating 等级 */
  private String rating;
  /** sourceUrl 资源路径 */
  private String sourceUrl;
  /** publishedTime 发布时间 */
  private String publishedTime;
  /** links 文章评论api */
  private Link links;
  /** author 作者信息 */
  private Author author;
  /** url 文章访问url */
  private String url;
  /** title 文章标题 */
  private String title;
  /** titleImage 文章配图url */
  private String titleImage;
  /** summary 文章概要 */
  private String summary;
  /** content 文章正文 */
  private String content;
  /** state 文章状态 */
  private String state;
  /** href 该文章的最近topN的评论、打赏的用户信息 */
  private String href;
  private Meta meta;
  /** commentPermission 评论开发群体(默认面向所有人) */
  private String commentPermission;
  /** snapshotUrl 快照url */
  private String snapshotUrl;
  /** canComment 是否可以评论(登陆后可评) */
  private Boolean canComment;
  /** slug 文章ID */
  private Long slug;
  /** commentsCount 已评论次数 */
  private Long commentsCount;
  /** likesCount 点赞数 */
  private Long likesCount;
  
  private String isSub = "0"; // 0表示还没有被抓取，抓取完毕后紧接着修改其状态


  @Data
  public static class Link {
    /** comments 改文章的所有评论 */
    private String comments;
  }
  @Data
  public static class Author {
    /** bio 昵称 */
    private String bio;
    /** isFollowing 是否关注(未登录是false) */
    private Boolean isFollowing;
    private String hash;
    /** uid 用户ID */
    private Long uid;
    private Boolean isOrg;
    /** slug 伪静态地址 */
    private String slug;
    private Boolean isFollowed;
    private String description;
    private String name;
    private String profileUrl;
    private Avatar avatar;
    private Boolean isOrgWhiteList;
    private Boolean isBanned;
    private String selfId;
  }

  @Data
  public static class Avatar {
    private String id;
    private String template;
  }
  @Data
  public static class Meta {
    private String previous;
    private String next;
  }


}
