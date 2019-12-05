package com.xiaodou.server.mapi.response.ucenter.vo;

import java.util.List;

import com.xiaodou.common.util.StringUtils;

import lombok.Data;

@Data
public class RegionVO {

  private String region;
  private String regionName;
  private List<Major> majorList;


  @Data
  public static class Major {
    private String majorId = StringUtils.EMPTY;;
    private String typeCode = StringUtils.EMPTY;;
    private String majorName = StringUtils.EMPTY;;
    private String courseCount = StringUtils.EMPTY;;
    private String pictureUrl= StringUtils.EMPTY;;
    private String chiefAcademy = StringUtils.EMPTY;
    
    private String showCover = StringUtils.EMPTY;

  }
}
