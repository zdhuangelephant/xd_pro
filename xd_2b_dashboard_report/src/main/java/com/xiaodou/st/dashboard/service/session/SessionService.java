package com.xiaodou.st.dashboard.service.session;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.Data;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyCountDO;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductCategoryDO;
import com.xiaodou.st.dashboard.domain.session.CategorySessionPercentDO;
import com.xiaodou.st.dashboard.domain.session.CategorySessionPercentVO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionLearnPercentDO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionLearnPercentDTO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionPercentDO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionPercentVO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionProductAvgScoreDO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionProductAvgScoreDTO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.domain.unit.ChiefUnitRelationDO;
import com.xiaodou.st.dashboard.domain.unit.UnitDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.alarm.AlarmService;
import com.xiaodou.st.dashboard.service.apply.ApplyService;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.st.dashboard.util.StDateUtil;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class SessionService extends BaseDashboardService {

  @Resource
  IStServiceFacade stServiceFacade;
  @Resource
  ApplyService applyService;
  @Resource
  AlarmService alarmService;
  @Resource
  ManageStaticInfoService manageStaticInfoService;

  public List<CategorySessionPercentDO> listCategorySessionPercent() {
    Page<CategorySessionPercentDO> page =
        stServiceFacade.listCategorySessionPercent(null,
            CommUtil.getAllField(CategorySessionPercentDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  public Long studentCountByCond(Integer catId, Long pilotUnitId) {
    ApplyCountDO applyDo = new ApplyCountDO();
    applyDo.setCatId(catId);
    applyDo.setPilotUnitId(pilotUnitId);
    applyDo.setApplyStatus((short) 1);
    applyDo.setOrderStatus((short) 1);
    List<ApplyCountDO> applyList = applyService.groupCatApply(applyDo);
    if (null == applyList || applyList.isEmpty() || null == applyList.get(0).getStudentNum())
      return 0l;
    return applyList.get(0).getStudentNum().longValue();
  }

  /**
   * 
   * @description 获取 专业列表 页面list
   * @author 李德洪
   * @Date 2017年4月10日
   * @return
   */
  public List<CategorySessionPercentVO> listCategorySessionPercentVO() {
    List<CategorySessionPercentVO> listVO = Lists.newArrayList();
    AdminUser adminUser = super.getAdminUser();
    List<Integer> hasStudyCatIds = Lists.newArrayList();
    List<CategorySessionPercentDO> listDO = this.listCategorySessionPercent();
    if (null != listDO && !listDO.isEmpty()) {
      for (CategorySessionPercentDO cdo : listDO) {
        Long pilotUnitId = null;
        if (Constants.POILT_UNIT_ROLE.equals(adminUser.getRole())) {
          pilotUnitId = adminUser.getUnitId();
        }
        CategorySessionPercentVO cvo = new CategorySessionPercentVO();
        Long studentCount = this.studentCountByCond(cdo.getCatId(), pilotUnitId);
        if (0 == studentCount) continue;
        cvo.setStudentCount(studentCount);
        cvo.setId(cdo.getId());
        cvo.setCatId(cdo.getCatId());
        hasStudyCatIds.add(cdo.getCatId());
        cvo.setLearnPercent(Constants.DECIMAL_FORMAT.format(cdo.getLearnPercent() * 100));
        cvo.setMissionPercent(Constants.DECIMAL_FORMAT.format(cdo.getMissionPercent() * 100));
        cvo.setRightPercent(Constants.DECIMAL_FORMAT.format(cdo.getRightPercent() * 100));
        Long learnTimePercent = StDateUtil.getCeilLong((cdo.getLearnTimePercent() / 60d));
        if (learnTimePercent > 10080) learnTimePercent = 10080l;
        cvo.setLearnTimePercent(learnTimePercent);
        cvo.setCatName(cdo.getCatName());
        cvo.setChiefUnitName(cdo.getChiefUnitName());
        cvo.setAlarmCount(alarmService.countAlarmRecordByCond(adminUser.getRole(),
            adminUser.getUnitId(), cdo.getCatId()));
        listVO.add(cvo);
      }
    }
    ApplyCountDO applyCountDO = new ApplyCountDO();
    applyCountDO.setOrderStatus(Short.valueOf("1"));
    applyCountDO.setApplyStatus(Short.valueOf("1"));
    if (Constants.CHIEF_UNIT_ROLE.equals(adminUser.getRole())) {
      ChiefUnitRelationDO crdo = stServiceFacade.getChiefUnitByUnitId(adminUser.getUnitId());
      applyCountDO.setCatId(crdo.getCatId());
    } else if (Constants.POILT_UNIT_ROLE.equals(adminUser.getRole())) {
      applyCountDO.setPilotUnitId(adminUser.getUnitId());
    }
    List<ApplyCountDO> applyCountList = applyService.groupCatApply(applyCountDO);
    List<Integer> catIds = Lists.newArrayList();
    for (ApplyCountDO applyCount : applyCountList) {
      if (applyCount.getCatId() == null || catIds.contains(applyCount.getCatId())) continue;

      CategorySessionPercentVO cvo = new CategorySessionPercentVO();
      if (0 == applyCount.getStudentNum()) continue;
      cvo.setStudentCount(applyCount.getStudentNum().longValue());
      cvo.setId(applyCount.getCatId().longValue());
      cvo.setCatId(applyCount.getCatId());
      RawDataProductCategoryDO cat =
          stServiceFacade.getRawDataProductCategory(applyCount.getCatId());
      if (null != cat) {
        cvo.setCatName(cat.getName());
      }
      ChiefUnitRelationDO crdo = stServiceFacade.getChiefUnitByCatId(applyCount.getCatId());
      if (null != crdo) {
        UnitDO unit = stServiceFacade.getUnit(crdo.getUnitId());
        if (null != unit) cvo.setChiefUnitName(unit.getUnitName());
      }
      if (hasStudyCatIds.isEmpty() || !hasStudyCatIds.contains(applyCount.getCatId())) {
        listVO.add(cvo);
      }
      catIds.add(applyCount.getCatId());
    }
    return listVO;
  }

  @SuppressWarnings("unused")
  private Map<Integer, Temp> getStudentCountMap() {
    List<ApplyDO> applyList = applyService.listApply(null);
    Map<Integer, Temp> map = Maps.newHashMap();
    for (ApplyDO apply : applyList) {
      if (!map.containsKey(apply.getCatId())) {
        Temp temp = new Temp();
        temp.setRdo(stServiceFacade.getRawDataProductCategory(apply.getCatId()));
        temp.setStudentCount(1l);
        temp.setStudentIdList(Lists.newArrayList(apply.getStudentId()));
        map.put(apply.getCatId(), temp);
      } else {
        Temp temp = map.get(apply.getCatId());
        if (null != temp) {
          if (!temp.getStudentIdList().contains(apply.getStudentId()))
            temp.getStudentIdList().add(apply.getStudentId());
          temp.setStudentCount(Long.valueOf(temp.getStudentIdList().size() + ""));
          map.put(apply.getCatId(), temp);
        }
      }
    }
    return map;
  }

  @Data
  class Temp {
    private RawDataProductCategoryDO rdo;
    private Long studentCount;
    private List<Integer> studentIdList;
  }

  /**
   * 
   * @description 专业下的第三级单位列表
   * @author 李德洪
   * @Date 2017年4月10日
   * @param catId
   * @return
   */
  public List<CategoryUnitSessionPercentDO> listCategoryUnitSessionPercent(Integer catId) {
    Map<String, Object> inputs = Maps.newHashMap();
    if (null != catId) inputs.put("catId", catId);
    inputs.put("orderStatus", Constants.ALREADYPAYMENT);
    inputs.put("applyStatus", Constants.BUSINESS_APPLY_SUCCESS);
    Page<CategoryUnitSessionPercentDO> page =
        stServiceFacade.listCategoryUnitSessionPercent(inputs,
            CommUtil.getAllField(CategoryUnitSessionPercentDO.class));
    if (null == page) return null;
    if (null != page.getResult() && !page.getResult().isEmpty())
      for (CategoryUnitSessionPercentDO cudo : page.getResult()) {
        cudo.setStudentCount(this.studentCountByCond(catId, cudo.getPilotUnitId()));
      }
    return page.getResult();
  }

  /**
   * @deprecated
   * @description TODO
   * @author 李德洪
   * @Date 2017年8月4日
   * @param catId
   * @return
   */
  public Integer getAlarmCount(Integer catId) {
    Integer alarmCount = 0;
    List<CategoryUnitSessionPercentDO> listDO = this.listCategoryUnitSessionPercent(catId);
    for (CategoryUnitSessionPercentDO vo : listDO) {
      if (null != vo) alarmCount += null != vo.getAlarmCount() ? vo.getAlarmCount() : 0;
    }
    return alarmCount;
  }

  /**
   * 
   * @description 专业下的第三级单位列表 頁面
   * @author 李德洪
   * @Date 2017年4月10日
   * @param catId
   * @return
   */
  public List<CategoryUnitSessionPercentVO> listCategoryUnitSessionPercentVO(Integer catId) {
    List<CategoryUnitSessionPercentVO> listVO = Lists.newArrayList();
    List<CategoryUnitSessionPercentDO> listDO = this.listCategoryUnitSessionPercent(catId);
    AdminUser adminUser = super.getAdminUser();
    if (null != listDO) {
      Map<Long, Integer> alarmCountMap = Maps.newHashMap();
      alarmService.packageAlarmRecordCountMapByCatId(alarmCountMap, catId);
      for (CategoryUnitSessionPercentDO cdo : listDO) {
        CategoryUnitSessionPercentVO cvo = new CategoryUnitSessionPercentVO();
        if (null != cdo) {
          cvo.setCatId(cdo.getCatId());
          cvo.setPilotUnitId(cdo.getPilotUnitId());
          cvo.setPilotUnitName(cdo.getPilotUnitName());
          cvo.setStudentCount(cdo.getStudentCount());
          if (null != cdo.getRightPercent())
            cvo.setRightPercent(Constants.PERCENT_FORMAT.format(cdo.getRightPercent()));
          if (null != cdo.getLearnPercent())
            cvo.setLearnPercent(Constants.PERCENT_FORMAT.format(cdo.getLearnPercent()));
          if (null != cdo.getMissionPercent())
            cvo.setMissionPercent(Constants.PERCENT_FORMAT.format(cdo.getMissionPercent()));
          if (null != cdo.getLearnTimePercent())
            cvo.setLearnTimePercent(StDateUtil.getCeilLong((cdo.getLearnTimePercent() / 60d)));
          Integer alarmCount = alarmCountMap.get(cdo.getPilotUnitId());
          cvo.setAlarmCount(null != alarmCount ? alarmCount : 0);
        }
        if (adminUser.getRole().equals(Constants.POILT_UNIT_ROLE)) {
          if (null != cvo.getPilotUnitId() && cvo.getPilotUnitId().equals(adminUser.getUnitId()))
            listVO.add(cvo);
        } else {
          listVO.add(cvo);
        }
      }
    }
    return listVO;
  }

  public List<CategoryUnitSessionLearnPercentDO> listCategoryUnitSessionLearnPercent(Integer catId,
      Long pilotUnitId) {
    Map<String, Object> inputs = Maps.newHashMap();
    if (null != catId) inputs.put("catId", catId);
    if (null != pilotUnitId) inputs.put("pilotUnitId", pilotUnitId);
    Page<CategoryUnitSessionLearnPercentDO> page =
        stServiceFacade.listCategoryUnitSessionLearnPercent(inputs,
            CommUtil.getAllField(CategoryUnitSessionLearnPercentDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  public List<CategoryUnitSessionProductAvgScoreDO> listCategoryUnitSessionProductAvgScore(
      Integer catId, Long pilotUnitId) {
    Map<String, Object> inputs = Maps.newHashMap();
    if (null != catId) inputs.put("catId", catId);
    if (null != pilotUnitId) inputs.put("pilotUnitId", pilotUnitId);
    Page<CategoryUnitSessionProductAvgScoreDO> page =
        stServiceFacade.listCategoryUnitSessionProductAvgScore(inputs,
            CommUtil.getAllField(CategoryUnitSessionProductAvgScoreDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * 
   * @description 分数转换
   * @author 李德洪
   * @Date 2017年4月7日
   * @param catId
   * @param pilotUnitId
   * @return
   */
  public List<CategoryUnitSessionProductAvgScoreDTO> getCategoryUnitSessionProductAvgScoreDTO(
      Integer catId, Long pilotUnitId) {
    List<CategoryUnitSessionProductAvgScoreDTO> cdtoList = Lists.newArrayList();
    List<CategoryUnitSessionProductAvgScoreDO> list =
        this.listCategoryUnitSessionProductAvgScore(catId, pilotUnitId);
    if (null == list || list.size() < 1) return null;
    for (CategoryUnitSessionProductAvgScoreDO cdo : list) {
      CategoryUnitSessionProductAvgScoreDTO cdto = new CategoryUnitSessionProductAvgScoreDTO();
      cdto.setAvgScore(Constants.DECIMAL_FORMAT.format(cdo.getAvgScore()));
      cdto.setProductName(cdo.getProductName());
      cdtoList.add(cdto);
    }
    return cdtoList;
  }

  /**
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月7日
   * @param days
   * @param catId
   * @param pilotUnitId
   * @return
   * @throws ParseException
   */
  public List<CategoryUnitSessionLearnPercentDTO> getCategoryUnitSessionLearnPercentDTO(int days,
      Integer catId, Long pilotUnitId) throws ParseException {
    DateFormat SDF_MD = new SimpleDateFormat("MM-dd");
    List<CategoryUnitSessionLearnPercentDTO> adtoList = Lists.newArrayList();
    List<String> dateTimeList = super.getBeforeDateStrList(days);
    List<CategoryUnitSessionLearnPercentDO> list =
        this.listCategoryUnitSessionLearnPercent(catId, pilotUnitId);
    Map<String, String> learnPercentMap = Maps.newHashMap();
    Map<String, String> missionPercentMap = Maps.newHashMap();
    Map<String, String> rightPercentMap = Maps.newHashMap();
    Map<String, Long> learnTimePercentMap = Maps.newHashMap();
    if (null != list && list.size() > 0) {
      StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
      Date productBeginApplyTime =
          new SimpleDateFormat("yyyy-MM-dd").parse(staticInfo.getBeginApplyTime());
      Date productEndApplyTime =
          new SimpleDateFormat("yyyy-MM-dd").parse(staticInfo.getEndApplyTime());
      for (CategoryUnitSessionLearnPercentDO sdo : list) {
        // Date productBeginApplyTime = new Date(117, 3, 1);
        // Date productEndApplyTime = new Date(117, 9, 20);
        Date dateTime = sdo.getDateTime();
        if (dateTime.after(productBeginApplyTime) && dateTime.before(productEndApplyTime)
            || dateTime.equals(productBeginApplyTime) || dateTime.equals(productEndApplyTime)) {
          learnPercentMap.put(SDF_MD.format(sdo.getDateTime()),
              Constants.DECIMAL_FORMAT.format(sdo.getLearnPercent() * 100));
          missionPercentMap.put(SDF_MD.format(sdo.getDateTime()),
              Constants.DECIMAL_FORMAT.format(sdo.getMissionPercent() * 100));
          rightPercentMap.put(SDF_MD.format(sdo.getDateTime()),
              Constants.DECIMAL_FORMAT.format(sdo.getRightPercent() * 100));
          learnTimePercentMap.put(SDF_MD.format(sdo.getDateTime()),
              StDateUtil.getCeilLong((sdo.getLearnTimePercent() / 60d)));
        }
      }
    }
    for (String dateTime : dateTimeList) {
      CategoryUnitSessionLearnPercentDTO adto = new CategoryUnitSessionLearnPercentDTO();
      adto.setDateTime(dateTime);
      if (null != learnPercentMap && learnPercentMap.containsKey(dateTime))
        adto.setLearnPercent(learnPercentMap.get(dateTime));
      if (null != missionPercentMap && missionPercentMap.containsKey(dateTime))
        adto.setMissionPercent(missionPercentMap.get(dateTime));
      if (null != learnTimePercentMap && learnTimePercentMap.containsKey(dateTime)) {
        Long learnTimePercent = 1440l;
        if (learnTimePercentMap.get(dateTime) < 1440l)
          learnTimePercent = learnTimePercentMap.get(dateTime);
        adto.setLearnTimePercent(learnTimePercent);
      }
      if (null != rightPercentMap && rightPercentMap.containsKey(dateTime))
        adto.setRightPercent(rightPercentMap.get(dateTime));
      adtoList.add(adto);
    }
    // 重試
    if (!super.isSuccessOrderData(adtoList)) {
      getCategoryUnitSessionLearnPercentDTO(days, catId, pilotUnitId);
    }
    return adtoList;
  }
}
