package com.xiaodou.st.dashboard.service.task;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductCategoryDO;
import com.xiaodou.st.dashboard.domain.session.CategorySessionPercentDO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionPercentDO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class StudentTask {
  @Resource
  IStServiceFacade stServiceFacade;
  @Resource
  ManageStaticInfoService manageStaticInfoService;

  /**
   * 更新学生数
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月21日
   */
  public void updateStudentCount() {
    // 查看所有专业
    Page<RawDataProductCategoryDO> catPage =
        stServiceFacade.listRawDataProductCategory(null,
            CommUtil.getAllField(RawDataProductCategoryDO.class));
    if (null == catPage || catPage.getResult().isEmpty()) return;
    // 查看所有单位
    Page<UnitDO> unitpage = stServiceFacade.listUnit(null, CommUtil.getAllField(UnitDO.class));
    if (null == unitpage || unitpage.getResult().isEmpty()) return;
    // 查看所有第三级单位
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("role", Constants.POILT_UNIT_ROLE);
    Page<UnitDO> pilotUtitpage =
        stServiceFacade.listUnit(inputs, CommUtil.getAllField(UnitDO.class));
    if (null == pilotUtitpage || pilotUtitpage.getResult().isEmpty()) return;
    // 1、更新专业下的学生数
    for (RawDataProductCategoryDO rdpc : catPage.getResult()) {
      for (UnitDO unit : unitpage.getResult()) {
        Long studentCount = 0l;
        if (Constants.POILT_UNIT_ROLE == unit.getRole()) {
          studentCount = this.countStudent(rdpc.getId(), unit.getId());
        } else {
          studentCount = this.countStudent(rdpc.getId(), null);
        }
        CategorySessionPercentDO csp = new CategorySessionPercentDO();
        csp.setStudentCount(studentCount);
        Map<String, Object> input = Maps.newHashMap();
        input.put("catId", rdpc.getId());
        input.put("role", unit.getRole());
        input.put("unitId", unit.getId());
        stServiceFacade.updateCategorySessionPercent(input, csp);
      }
    }

    // 2、更新专业下的，第三级单位下面学生数
    for (RawDataProductCategoryDO rdpc : catPage.getResult()) {
      for (UnitDO pilotUtit : pilotUtitpage.getResult()) {
        Long studentCount = 0l;
        studentCount = this.countStudent(rdpc.getId(), pilotUtit.getId());
        CategoryUnitSessionPercentDO cusp = new CategoryUnitSessionPercentDO();
        cusp.setStudentCount(studentCount);
        Map<String, Object> input = Maps.newHashMap();
        input.put("catId", rdpc.getId());
        input.put("pilotUnitId", pilotUtit.getId());
        stServiceFacade.updateCategoryUnitSessionPercent(input, cusp);
      }
    }

  }

  /**
   * 
   * @description 获取当期学生数目
   * @author 李德洪
   * @Date 2017年4月21日
   * @return
   */
  public Long countStudent(Integer catId, Long pilotUnitId) {
    Long defaultCount = 0l;
    Map<String, Object> inputs = Maps.newHashMap();
    StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
    inputs.put("examDate", staticInfo.getExamDate());
    inputs.put("group", "student_id");
    inputs.put("orderStatus", Constants.ALREADYPAYMENT);
    inputs.put("applyStatus", Constants.BUSINESS_APPLY_SUCCESS);
    if (null != catId) inputs.put("catId", catId);
    if (null != pilotUnitId) inputs.put("pilotUnitId", pilotUnitId);
    Page<ApplyDO> applyPage =
        stServiceFacade.listApply(inputs, CommUtil.getAllField(ApplyDO.class),null);
    if (null == applyPage) return defaultCount;
    return applyPage.getTotalCount();
  }

}
