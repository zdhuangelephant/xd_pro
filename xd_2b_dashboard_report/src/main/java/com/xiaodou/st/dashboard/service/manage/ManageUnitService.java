package com.xiaodou.st.dashboard.service.manage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.domain.unit.ChiefUnitRelationDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class ManageUnitService {
  @Resource
  IStServiceFacade stServiceFacade;

  public List<UnitDO> listManageUnit(UnitDO unitDO) {
    Map<String, Object> inputs = Maps.newHashMap();
    INPUTS: {
      if (null == unitDO) break INPUTS;
      if (null != unitDO.getRole()) inputs.put("role", unitDO.getRole());
      if (StringUtils.isNotBlank(unitDO.getUnitName()))
        inputs.put("unitNameLike", unitDO.getUnitName());
    }
    Page<UnitDO> page = stServiceFacade.listManageUnit(inputs, CommUtil.getAllField(UnitDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * 
   * @description 增加单位
   * @author 李德洪
   * @Date 2017年6月26日
   * @param unitDO
   * @return
   */
  public String saveUnit(UnitDO unitDO) {
    boolean flag = stServiceFacade.saveUnit(unitDO);
    return String.valueOf(flag);
  }

  /**
   * 
   * @description 修改单位信息
   * @author 李德洪
   * @Date 2017年4月1日
   * @param classId
   * @return
   */
  public String updateUnit(UnitDO unitDO) {
    boolean flag = stServiceFacade.updateUnit(unitDO);
    return String.valueOf(flag);
  }

  /**
   * 
   * @description 查詢单位
   * @author 李德洪
   * @Date 2017年4月1日
   * @param classId
   * @return
   */
  public UnitDO getUnit(Long unitId) {
    return stServiceFacade.getUnit(unitId);
  }

  /**
   * 
   * @description 刪除单位
   * @author 李德洪
   * @Date 2017年4月1日
   * @param classId
   * @return
   */
  public String removeUnit(Long unitId) {
    boolean flag = stServiceFacade.removeUnit(unitId);
    return String.valueOf(flag);
  }

  public String saveRelate(ChiefUnitRelationDO curdo) {
    boolean flag = stServiceFacade.saveRelate(curdo);
    return String.valueOf(flag);
  }

  public String updateRelate(ChiefUnitRelationDO curdo) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("unitId", curdo.getUnitId());
    boolean flag = stServiceFacade.updateRelate(inputs, curdo);
    return String.valueOf(flag);
  }

}
