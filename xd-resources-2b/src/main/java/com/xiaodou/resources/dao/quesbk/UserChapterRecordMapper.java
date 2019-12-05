package com.xiaodou.resources.dao.quesbk;

import java.util.List;
import java.util.Map;

import com.xiaodou.resources.model.quesbk.UserChapterRecord;

public interface UserChapterRecordMapper {

  int deleteByPrimaryKey(String id);

  int insertSelective(UserChapterRecord record);

  UserChapterRecord selectByPrimaryKey(String id);

  List<UserChapterRecord> selectByCond(Map<String, Object> cond);

  int updateByPrimaryKey(UserChapterRecord record);
}
