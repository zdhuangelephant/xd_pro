package com.xiaodou.summer.dao.mongo.enums;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.query.Criteria;

import com.google.common.collect.Lists;


/**
 * 值范围 LT:小于 LE:小于等于 EQ:等于 GT:大于 GE:大于等于
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月11日
 */
public enum Scope {
  // 小于
  LT {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      return c.and(key).lt(value);
    }
  },
  // 小于等于
  LE {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      return c.and(key).lte(value);
    }
  },
  // 等于
  EQ {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      return c.and(key).is(value);
    }
  },
  // 大于
  GT {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      return c.and(key).gt(value);
    }
  },
  // 大于等于
  GE {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      return c.and(key).gte(value);
    }
  },
  // 包含
  IN {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      if (value instanceof List)
        return c.and(key).in((List<?>) value);
      else if (value instanceof Set)
        return c.and(key).in((Set<?>) value);
      else
        return c.and(key).in(Lists.newArrayList(value));
    }
  },
  // 不包含
  NIN {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      if (value instanceof List)
        return c.and(key).nin((List<?>) value);
      else if (value instanceof Set)
        return c.and(key).nin((Set<?>) value);
      else
        return c.and(key).nin(Lists.newArrayList(value));
    }
  },
  // 模糊匹配
  LIKE {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      return c.and(key).regex(value.toString());
    }

  },
  // 是否存在
  EXIST {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      if (!(value instanceof Boolean)) {
        throw new IllegalArgumentException("the value only can be boolean");
      }
      return c.and(key).exists((boolean) value);
    }
  },
  // 不等于
  NE {
    @Override
    public Criteria append(Criteria c, String key, Object value) {
      return c.and(key).ne(value);
    }
  };
  public abstract Criteria append(Criteria c, String key, Object value);

}
