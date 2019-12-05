package com.xiaodou.st.dashboard.service.unit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.unit.ChiefUnitRelationDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class UnitService {

  @Resource
  IStServiceFacade stServiceFacade;

  /**
   * 查询所有第三级单位列表
   * 
   * @return
   */
  public List<UnitDO> listPilotUnit() {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("role", Constants.POILT_UNIT_ROLE);
    Page<UnitDO> page = stServiceFacade.listUnit(inputs, CommUtil.getAllField(UnitDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * 
   * @description 通过单位id查询单位
   * @author 李德洪
   * @Date 2017年5月17日
   * @param unitId
   * @return
   */
  public UnitDO getUnit(Long unitId) {
    return stServiceFacade.getUnit(unitId);
  }

  /**
   * 
   * @description 根据第二级单位id查寻关联关系
   * @author 李德洪
   * @Date 2017年6月26日
   * @param unitId
   * @return
   */
  public ChiefUnitRelationDO getChiefUnitByUnitId(Long unitId) {
    return stServiceFacade.getChiefUnitByUnitId(unitId);
  }

  /**
   * 
   * @description 所有被关联的专业的id列表
   * @author 李德洪
   * @Date 2017年11月2日
   * @return
   */
  public List<String> listRelationCatId() {
    Map<String, Object> inputs = Maps.newHashMap();
    Page<ChiefUnitRelationDO> page =
        stServiceFacade.listChiefUnitRelation(inputs,
            CommUtil.getAllField(ChiefUnitRelationDO.class));
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return null;
    List<String> list =
        Lists.transform(page.getResult(), new Function<ChiefUnitRelationDO, String>() {
          @Override
          public String apply(ChiefUnitRelationDO input) {
            if (null == input) return null;
            return input.getCatId().toString();
          }
        });
    return list;
  }
}
