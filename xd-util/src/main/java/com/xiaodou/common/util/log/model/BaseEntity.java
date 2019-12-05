package com.xiaodou.common.util.log.model;


import com.alibaba.fastjson.JSON;

/**
 * BaseEntity
 *
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-26
 */
class BaseEntity {

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

}
