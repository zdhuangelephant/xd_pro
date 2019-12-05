package com.xiaodou.dao.behavior;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.dao.ProcessBaseDao;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.summer.dao.pagination.Page;

@Repository
public class UserChapterRecordDao extends ProcessBaseDao<UserChapterRecord> {


  /**
   * @param id
   * @return 删除成功返回1
   */
  public int deleteByPrimaryKey(String id) {
    return this.getSqlSession().delete(
        this.getEntityClass().getSimpleName() + ".deleteByPrimaryKey", id);
  }

  public int insertSelective(UserChapterRecord record) {
    return this.getSqlSession().insert(this.getEntityClass().getSimpleName() + ".insertSelective",
        record);
  }

  public UserChapterRecord selectByPrimaryKey(String id) {
    return this.getSqlSession().selectOne(
        this.getEntityClass().getSimpleName() + ".selectByPrimaryKey", id);
  }

  public List<UserChapterRecord> selectByCond(Map<String, Object> cond) {
    return this.selectPaginationList(this.getEntityClass().getSimpleName() + ".selectByCond", cond,
        new Page<UserChapterRecord>()).getResult();
  }

  public int updateByPrimaryKey(UserChapterRecord record) {

    int result =
        this.getSqlSession().update(this.getEntityClass().getSimpleName() + ".updateByPrimaryKey",
            record);
    return result;

  }

}
