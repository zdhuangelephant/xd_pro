package com.xiaodou.ms.service.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.common.XDcityDao;
import com.xiaodou.ms.model.common.XDcity;
@Service("XDcityService")
public class XDcityService {
  @Resource
  XDcityDao XDcityDao;
  /**
   * 查询 城市或者省份
   * 
   * @param name
   */
  public XDcity findCityByOne(String name){
    XDcity xdcity = new XDcity();
    xdcity.setName(name);
    return XDcityDao.findEntityByCondOne(xdcity);
  }
  public XDcity findCityByTwo(String name1,String name2){
    XDcity xdcity = new XDcity();
    xdcity.setName1(name1);
    xdcity.setName2(name2);
    return XDcityDao.findEntityByCondTwo(xdcity);
  }
}
