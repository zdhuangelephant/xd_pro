package com.xiaodou.resources.model.forum;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

public class SharePagePvModel {

  @Column(isMajor = true, canUpdate = false)
  private Long id;
  @Column(canUpdate = false)
  private Long resourceId;
  @Column(canUpdate = true)
  private Long pv;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getResourceId() {
    return resourceId;
  }

  public void setResourceId(Long resourceId) {
    this.resourceId = resourceId;
  }

  public Long getPv() {
    return pv;
  }

  public void setPv(Long pv) {
    this.pv = pv;
  }

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(SharePagePvModel.class, "xd_share_page_pv",
        "D:/work/workspace_xd/xd-resources/src/main/resources/conf/mybatis/forum/").buildXml();
  }
}
