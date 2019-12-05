package com.xiaodou.userCenter.module.domain.vo;

import java.util.List;

import lombok.Data;

@Data
public class RegionVO {

  private String region;
  private String regionName;
  private List<Major> majorList;
  public RegionVO() {}

  public RegionVO(String region, String regionName, List<Major> majorList) {
    super();
    this.region = region;
    this.regionName = regionName;
    this.majorList = majorList;
  }

  public String getMajorCount() {
    return String.valueOf(majorList.size());
  }

  @Data
  public static class Major {
    private String majorId;
    private String typeCode;
    private String majorName;
    private String courseCount;
    private String pictureUrl;
    private String chiefAcademy;

    public Major() {}

    public Major(String majorId,String typeCode, String majorName, String courseCount, String pictureUrl,String chiefAcademy) {
      super();
      this.majorId = majorId;
      this.typeCode = typeCode;
      this.majorName = majorName;
      this.courseCount = courseCount;
      this.pictureUrl = pictureUrl;
      this.chiefAcademy = chiefAcademy;
    }

  }
}
