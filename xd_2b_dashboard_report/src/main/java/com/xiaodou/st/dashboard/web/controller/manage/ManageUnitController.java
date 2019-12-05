package com.xiaodou.st.dashboard.web.controller.manage;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.unit.ChiefUnitRelationDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.manage.ManageRoleService;
import com.xiaodou.st.dashboard.service.manage.ManageUnitService;
import com.xiaodou.st.dashboard.service.product.ProductService;
import com.xiaodou.st.dashboard.service.task.StudentTask;
import com.xiaodou.st.dashboard.service.unit.UnitService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("manageUnitController")
@RequestMapping("/manage")
public class ManageUnitController extends BaseController {
  @Resource
  ManageUnitService manageUnitService;
  @Resource
  StudentTask studentTask;
  @Resource
  UnitService unitService;
  @Resource
  ManageRoleService manageRoleService;
  @Resource
  ProductService productService;

  @RequestMapping("/unit_list")
  public ModelAndView unitList(UnitDO unitDO) {
    ModelAndView mv = new ModelAndView("/manage/unit/unitList");
    mv.addObject("unitDO", unitDO);
    mv.addObject("adminUser", super.getAdminUser());
    List<UnitDO> listUnit = manageUnitService.listManageUnit(unitDO);
    // for (UnitDO unit : listUnit) {
    // Integer catId = null;
    // Long pilotUnitId = null;
    // Long studentCount = 0l;
    // if (Constants.CHIEF_UNIT_ROLE == unit.getRole()) {
    // CATID:{
    // ChiefUnitRelationDO cudo = unitService.getChiefUnitByUnitId(unit.getId());
    // if(null == cudo) break CATID;
    // catId = cudo.getCatId();
    // studentCount = studentTask.countStudent(catId, null);
    // }
    // } else if (Constants.POILT_UNIT_ROLE == unit.getRole()) {
    // pilotUnitId = unit.getId();
    // studentCount = studentTask.countStudent(null, pilotUnitId);
    // }
    // unit.setStudentCount(studentCount);
    // }
    mv.addObject("listUnit", listUnit);
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("validStatus", Constants.ROLE_ON);
    mv.addObject("listRole", manageRoleService.listRole(inputs));
    mv.addObject("listCategory", productService.listCategory());
    return mv;
  }

  @RequestMapping("/save_unit")
  @ResponseBody()
  public String saveUnit(UnitDO unitDO) {
    unitDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return manageUnitService.saveUnit(unitDO);
  }

  @RequestMapping("/update_unit")
  @ResponseBody()
  public String updateUnit(UnitDO unitDO) {
    return manageUnitService.updateUnit(unitDO);
  }

  @RequestMapping("/remove_unit")
  @ResponseBody()
  public String removeUnit(Long unitId) {
    return manageUnitService.removeUnit(unitId);
  }

  @RequestMapping("/get_relate")
  @ResponseBody()
  public String getChiefUnitByUnitId(Long unitId) {
    ChiefUnitRelationDO curdo = unitService.getChiefUnitByUnitId(unitId);
    String catId = StringUtils.EMPTY;
    if(null != curdo) catId = curdo.getCatId().toString();
    List<String> catIdList = unitService.listRelationCatId();
    catIdList.remove(catId);
    Map<String,Object> map = Maps.newHashMap();
    map.put("oneself", catId);
    map.put("other", catIdList);
    return FastJsonUtil.toJson(map);
  }
  
  
  @RequestMapping("/save_relate")
  @ResponseBody()
  public String saveRelate(ChiefUnitRelationDO curdo) {
    if(null != curdo.getUnitId()){
      ChiefUnitRelationDO cur = unitService.getChiefUnitByUnitId(curdo.getUnitId());
      if(null != cur){
        return manageUnitService.updateRelate(curdo);
      }else{
        curdo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return manageUnitService.saveRelate(curdo);
      }
    }
    return String.valueOf(false);
  }
}
