package com.xiaodou.st.dataclean.dao.raw;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.st.dataclean.dao.raw.RawDataLearnRecordDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 原始数据:用户学习记录
 * @version 1.0
 */
@Repository("rawDataLearnRecordDao")
public class RawDataLearnRecordDao extends BaseDao<RawDataLearnRecordModel> {

  public Integer queryLearnTimeCountByCond(Map<String, Object> input) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    Integer count =
        (Integer) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryLearnTimeCountByCond", cond);
    if (count != null) {
      return count;
    } else {
      return 0;
    }
  }

  public Integer queryStudentCountByCond(Map<String, Object> input) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    Integer count =
        (Integer) this.getSqlSession().selectOne(
            getEntityClass().getSimpleName() + ".queryStudentCountByCond", cond);
    if (count != null) {
      return count;
    } else {
      return 0;
    }
  }

  public List<RawDataLearnRecordModel> getLaskWeekLearnTimeRank(Map<String, Object> input) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    return this.getSqlSession().selectList(
        getEntityClass().getSimpleName() + ".getLaskWeekLearnTimeRank", cond);

  }

  public List<RawDataLearnRecordModel> querySubjectsByCond(Map<String, Object> input) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("input", input);
    return this.getSqlSession().selectList(
        getEntityClass().getSimpleName() + ".querySubjectsByCond", cond);
  }
}
