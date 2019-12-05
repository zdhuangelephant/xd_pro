package com.xiaodou.resources.dao.quesbk;

import java.util.List;

import com.xiaodou.resources.model.quesbk.QuesbkQuesType;

/**
 * @name @see com.xiaodou.dao.QuesbkQuesTypeMapper.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月8日
 * @description 题目类型数据源Mapper
 * @version 1.0
 */
public interface QuesbkQuesTypeMapper {

  /**
   * 根据主键ID获取问题类型
   * 
   * @param id 主键ID
   * @return 问题类型记录
   */
  QuesbkQuesType selectByPrimaryKey(String id);

  /**
   * 获取问题类型列表
   * 
   * @return 问题类型列表
   */
  List<QuesbkQuesType> selectQuesType();
}
