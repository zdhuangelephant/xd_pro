package com.xiaodou.resources.dao.quesbk;

import java.util.List;

import com.xiaodou.resources.model.quesbk.QuesbkBase;

public interface QuesbkBaseMapper {
  int deleteByPrimaryKey(String id);

  int insert(QuesbkBase record);

  int insertSelective(QuesbkBase record);

  QuesbkBase selectByPrimaryKey(String id);

  int updateByPrimaryKeySelective(QuesbkBase record);

  int updateByPrimaryKey(QuesbkBase record);

  List<QuesbkBase> selectAll();

  List<QuesbkBase> selectAllByCategoryId(String id);
}
