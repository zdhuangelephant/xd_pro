package com.xiaodou.st.dataclean.dao.dashboard;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.dashboard.ApplyModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.st.dataclean.dao.dashboard.cateGoryUnitProductAvgScoreDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年3月31日
 * @description 报名信息
 * @version 1.0
 */
@Repository("applyDao")
public class ApplyDao extends BaseDao<ApplyModel> {

  public Integer queryStudentCountByApply(Map<String, Object> input3) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input3);
    Integer count =
        (Integer) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryStudentCountByCat", cond);
    if (count != null) {
      return count;
    } else {
      return 0;
    }
  }

  public List<Integer> queryPilotUnitIdByCatId(List<Integer> catIdList) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("catIds", catIdList);
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    return this.getSqlSession().selectList(
        getEntityClass().getSimpleName() + ".queryPilotUnitIdByCatId", cond);
  }

  public List<Integer> queryCatIdByPilotUnitId(List<Integer> pilotUnitIdList) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("pilotUnitIdList", pilotUnitIdList);
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    return this.getSqlSession().selectList(
        getEntityClass().getSimpleName() + ".queryCatIdByPilotUnitId", cond);
  }

  public Integer querySubjectsByApply(Map<String, Object> input3) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input3);
    Integer count =
        (Integer) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".querySubjectsByCat", cond);
    if (count != null) {
      return count;
    } else {
      return 0;
    }
  }

  public List<ApplyModel> querySubjectsByCond(Map<String, Object> cond, Map<String, Object> output) {
    if (null != cond && cond.size() > 0) cond.put("input", cond);
    if (null != output && output.size() > 0) cond.put("output", output);
    return this.getSqlSession().selectList(
        getEntityClass().getSimpleName() + ".querySubjectsByCond", cond);
  }

  public List<Integer> getStudentForNeverLearnCourse(Map<String, Object> cond) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("input", cond);
    List<Integer> resList =
        this.getSqlSession().selectList(
            getEntityClass().getSimpleName() + ".queryStudentForNeverLearnCourse", input);
    if (null == resList || resList.size() == 0) return Lists.newArrayList();
    return resList;
  }


}
