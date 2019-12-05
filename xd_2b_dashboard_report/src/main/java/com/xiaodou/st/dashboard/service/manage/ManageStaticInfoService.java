package com.xiaodou.st.dashboard.service.manage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductCategoryDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductDO;
import com.xiaodou.st.dashboard.domain.score.ScoreDO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncAdmissionCardCodeDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncApplyDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncLogDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncStudentDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.domain.unit.ChiefUnitRelationDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.util.ExcelUtil;
import com.xiaodou.st.dashboard.util.ExceptionWrapper;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class ManageStaticInfoService extends BaseDashboardService {
  @Resource
  IStServiceFacade stServiceFacade;

  public StaticInfoDO staticInfo() {
    Page<StaticInfoDO> page =
        stServiceFacade.listStaticInfo(null, CommUtil.getAllField(StaticInfoDO.class));
    if (null == page || null == page.getResult() || page.getResult().size() == 0) {
      RuntimeException exception = new RuntimeException("无法获取当前考期,请联系系统管理员设置常量配置.");
      ExceptionWrapper.getWrapper().setValue(exception);
      throw exception;
    }
    return page.getResult().get(0);
  }

  public String updateStaticInfo(StaticInfoDO staticInfoDO) {
    return stServiceFacade.updateStaticInfo(staticInfoDO).toString();
  }

  /**
   ****************************************************
   */
  public List<SyncLogDO> listSyncLog(SyncLogDO syncLogDO) {
    Map<String, Object> input = Maps.newHashMap();
    AdminUser adminUser = super.getAdminUser();
    input.put("syncAdminId", adminUser.getId());
    INPUT: {
      if (null == syncLogDO) break INPUT;
      if (null != syncLogDO.getSyncType()) input.put("syncType", syncLogDO.getSyncType());
    }
    Page<SyncLogDO> page =
        stServiceFacade.listSyncLog(input, CommUtil.getAllField(SyncLogDO.class));
    if (null == page || null == page.getResult() || page.getResult().size() == 0) {
      return null;
    }
    return page.getResult();
  }


  public List<SyncStudentDO> listSyncStudent(String syncId) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("syncId", syncId);
    Page<SyncStudentDO> page =
        stServiceFacade.listSyncStudent(input, CommUtil.getAllField(SyncStudentDO.class));
    if (null == page || null == page.getResult() || page.getResult().size() == 0) {
      return null;
    }
    return page.getResult();
  }

  public List<SyncApplyDO> listSyncApply(String syncId) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("syncId", syncId);
    Page<SyncApplyDO> page =
        stServiceFacade.listSyncApply(input, CommUtil.getAllField(SyncApplyDO.class));
    if (null == page || null == page.getResult() || page.getResult().size() == 0) {
      return null;
    }
    return page.getResult();
  }

  public List<SyncAdmissionCardCodeDO> listSyncCard(String syncId) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("syncId", syncId);
    Page<SyncAdmissionCardCodeDO> page =
        stServiceFacade.listSyncCard(input, CommUtil.getAllField(SyncAdmissionCardCodeDO.class));
    if (null == page || null == page.getResult() || page.getResult().size() == 0) {
      return null;
    }
    return page.getResult();
  }

  public void downloadStudentExcel(String sheetName, String fileName, List<SyncStudentDO> list,
      HttpServletResponse response) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    fieldMap.put("syncId", "操作结果-记录id");
    // fieldMap.put("telephone", "手机号（11位）");
    fieldMap.put("studentId", "学生id");
    fieldMap.put("msg", "导入报错描述");
    try {
      ExcelUtil.listToExcel(list, fieldMap, sheetName, response, fileName);
    } catch (Exception e) {
      LoggerUtil.error("导出excel异常！", e);
    }
  }

  public void downloadApplyExcel(String sheetName, String fileName, List<SyncApplyDO> list,
      HttpServletResponse response) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    fieldMap.put("syncId", "操作结果-记录id");
    // fieldMap.put("telephone", "手机号（11位）");
    fieldMap.put("studentId", "学生id");
    fieldMap.put("msg", "导入报错描述");
    try {
      ExcelUtil.listToExcel(list, fieldMap, sheetName, response, fileName);
    } catch (Exception e) {
      LoggerUtil.error("导出excel异常！", e);
    }
  }

  public void downloadCardExcel(String sheetName, String fileName,
      List<SyncAdmissionCardCodeDO> list, HttpServletResponse response) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    fieldMap.put("syncId", "操作结果-记录id");
    // fieldMap.put("telephone", "手机号（11位）");
    fieldMap.put("studentId", "学生id");
    fieldMap.put("msg", "导入报错描述");
    try {
      ExcelUtil.listToExcel(list, fieldMap, sheetName, response, fileName);
    } catch (Exception e) {
      LoggerUtil.error("导出excel异常！", e);
    }
  }


  public String batchScoreByApply() {
    Map<String, ApplyDO> successApplyMap = Maps.newHashMap();
    packageSuccessApplyMap(successApplyMap);
    Map<String, ScoreDO> scoreMap = Maps.newHashMap();
    packageScoreMap(scoreMap);
    if (successApplyMap.size() < scoreMap.size()) {
      return "score表数据多于apply表符合规范数据，请更正修复！";
    }
    if (successApplyMap.size() == scoreMap.size()) return "数据正确，无需处理！";
    StringBuffer sb = new StringBuffer(200);
    if (successApplyMap.size() > scoreMap.size()) {
      for (String key : successApplyMap.keySet()) {
        if (!scoreMap.containsKey(key) && !batchSaveScore(successApplyMap.get(key))) {
          sb.append(String.format("s%|", key));
        }
      }
    }
    if (sb.length() > 0)
      return String.format("执行失败数据：s%", sb.toString());
    else
      return "true";
  }

  public void packageSuccessApplyMap(Map<String, ApplyDO> successApplyMap) {
    Assert.notNull(successApplyMap, "successApplyMap can't be null.");
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("orderStatus", Constants.ALREADYPAYMENT);
    inputs.put("applyStatus", Constants.HAS_APPLY);
    Page<ApplyDO> page =
        stServiceFacade.listApply(inputs, CommUtil.getAllField(ApplyDO.class), null);
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return;
    for (ApplyDO ado : page.getResult()) {
      if (null == ado) continue;
      String key = ado.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + ado.getProductCode();
      successApplyMap.put(key, ado);
    }
  }

  public void packageScoreMap(Map<String, ScoreDO> scoreMap) {
    Assert.notNull(scoreMap, "scoreMap can't be null.");
    Map<String, Object> inputs = Maps.newHashMap();
    Map<String, Object> outputs = CommUtil.getAllField(ScoreDO.class);
    outputs.remove("coefficient");
    Page<ScoreDO> page = stServiceFacade.findPureScoreListByCond(inputs, outputs, null);
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return;
    for (ScoreDO sdo : page.getResult()) {
      if (null == sdo) continue;
      String key = sdo.getAdmissionCardCode() + Constants.UNIQUE_SEPEATEOR + sdo.getProductCode();
      scoreMap.put(key, sdo);
    }
  }

  public boolean batchSaveScore(ApplyDO ado) {
    boolean flag = true;
    if (null == ado) return flag;
    ScoreDO scoreDO = this.getInstance(ado);
    // 第三级单位
    scoreDO.setRoleType(Constants.POILT_UNIT_ROLE);
    scoreDO.setUnitId(ado.getPilotUnitId());
    flag = stServiceFacade.saveScore(scoreDO);
    // 第二级单位
    if (null != scoreDO.getCatId()) {
      ChiefUnitRelationDO crdo = stServiceFacade.getChiefUnitByCatId(scoreDO.getCatId());
      if (null != crdo) {
        scoreDO.setRoleType(Constants.CHIEF_UNIT_ROLE);
        scoreDO.setUnitId(crdo.getUnitId());
        flag = stServiceFacade.saveScore(scoreDO) && flag;
      }
    }
    // 第一级单位
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("role", Constants.SELF_TAUGHT_UNIT_ROLE);
    Map<String, Object> outputs = Maps.newHashMap();
    outputs.put("id", 1);
    Page<UnitDO> page = stServiceFacade.listUnit(inputs, outputs);
    if (null != page && null != page.getResult() && !page.getResult().isEmpty()
        && null != page.getResult().get(0)) {
      scoreDO.setRoleType(Constants.SELF_TAUGHT_UNIT_ROLE);
      scoreDO.setUnitId(page.getResult().get(0).getId());
      flag = stServiceFacade.saveScore(scoreDO) && flag;
    }
    return flag;
  }

  public ScoreDO getInstance(ApplyDO ado) {
    ScoreDO sdo = new ScoreDO();
    if (null == ado) return sdo;
    sdo.setAdmissionCardCode(ado.getAdmissionCardCode());
    sdo.setCatId(ado.getCatId());
    sdo.setCatName(ado.getCatName());
    sdo.setClassId(ado.getClassId());
    sdo.setClassName(ado.getClassName());
    sdo.setCreateTime(new Timestamp(System.currentTimeMillis()));
    sdo.setDailyScoreOperateTime(new Timestamp(System.currentTimeMillis()));
    sdo.setDailyScoreOperator(super.getAdminUser().getUserId());
    sdo.setExamDate(ado.getExamDate());
    sdo.setPilotUnitId(ado.getPilotUnitId());
    sdo.setPilotUnitName(ado.getPilotUnitName());
    sdo.setProductCode(ado.getProductCode());
    sdo.setProductId(ado.getProductId());
    sdo.setProductName(ado.getProductName());
    sdo.setStudentId(ado.getStudentId());
    sdo.setStudentName(ado.getStudentName());
    sdo.setTelephone(ado.getTelephone());
    if (null != ado.getStudentId()) {
      StudentDO studentDO = stServiceFacade.getStudent(ado.getStudentId());
      if (null != studentDO && StringUtils.isNotBlank(studentDO.getPortrait()))
        sdo.setStudentPortrait(studentDO.getPortrait());
    }
    if (null != ado.getProductId()) {
      RawDataProductDO rdo = stServiceFacade.getRawDataProductById(ado.getProductId());
      if (null != ado && null != rdo.getBeginApplyTime() && null != rdo.getEndApplyTime())
        sdo.setBeginTime(new SimpleDateFormat("yyyy-MM-dd").format(rdo.getBeginApplyTime()));
      sdo.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(rdo.getEndApplyTime()));
    }
    if (null != ado.getCatId()) {
      RawDataProductCategoryDO rpcdo = stServiceFacade.getRawDataProductCategory(ado.getCatId());
      if (null != rpcdo && StringUtils.isNotBlank(rpcdo.getTypeCode()))
        sdo.setCatCode(rpcdo.getTypeCode());
    }
    return sdo;
  }
}
