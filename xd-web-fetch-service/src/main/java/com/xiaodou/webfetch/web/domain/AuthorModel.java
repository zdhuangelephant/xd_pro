package com.xiaodou.webfetch.web.domain;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @name AuthorModel CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月6日
 * @description 作者模型
 * @version 1.0
 */
@Data
public class AuthorModel {
  /** id 主键ID */
  @Column(isMajor = true, betweenScope = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
  private Long id;
  /** name 姓名 */
  private String name;
  /** gender 性别 */
  private Short gender;
  /** portrait 头像 */
  private String portrait;
  /** cover 封面 */
  private String cover;
  /** info 描述 */
  private String info;
  /** createTime 创建时间 */
  @Column(betweenScope = true)
  private Timestamp createTime;
  /** worksNum 发布作品数 */
  private Integer worksNum;
  private String selfId;
  
  /********************知乎专栏作者********************/

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(AuthorModel.class, "xd_resource_author",
        "src/main/resources/conf/mybatis").buildXml();
  }
}
