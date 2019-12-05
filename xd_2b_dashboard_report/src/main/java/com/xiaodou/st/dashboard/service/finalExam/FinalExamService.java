package com.xiaodou.st.dashboard.service.finalExam;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.domain.finalExam.DashboardFinalExamDO;
import com.xiaodou.st.dashboard.domain.finalExam.RawDataFinalExamDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;
@Service
public class FinalExamService extends BaseDashboardService {
  @Resource
  IStServiceFacade stServiceFacade;

  /**
   * 查询成绩详情汇总表
   * 
   * @return
   */
  public List<DashboardFinalExamDO> listDbFinalExam(Long studentId,Long productId) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("studentId", studentId);
    inputs.put("productId", productId);
    Page<DashboardFinalExamDO> page = stServiceFacade.listDbFinalExam(inputs, CommUtil.getAllField(DashboardFinalExamDO.class));
    if (null == page) return null;
    return page.getResult();
  }
  
  /**
   * 查询成绩详情洗数据表
   * 
   * @return
   */
  public List<RawDataFinalExamDO> listRdFinalExam(Long studentId,Long productId,int type) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("studentId", studentId);
    inputs.put("productId", productId);
    inputs.put("type", type);
    Page<RawDataFinalExamDO> page = stServiceFacade.listRdFinalExam(inputs, CommUtil.getAllField(RawDataFinalExamDO.class));
    if (null == page) return null;
    return page.getResult();
  }
  
  
}
