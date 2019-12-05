package com.xiaodou.resources.service.quesbk.rule.base;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.xiaodou.common.util.consistentHash.model.NodeMap;
import com.xiaodou.common.util.consistentHash.pool.ConsistentHashPool;
import com.xiaodou.resources.service.quesbk.rule.iterface.IRuleEntity;

/**
 * @name WeightChooser 
 * CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月7日
 * @description 权重选择器
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class WeightChooser<T extends IRuleEntity> {

  public T choose(List<T> ruleList) {
    ConsistentHashPool<T> ruleHashPool =
        new ConsistentHashPool<>(Hashing.md5(), 10, "utf-8", Lists.transform(ruleList,
            new Function<T, NodeMap<T>>() {
              @Override
              public NodeMap<T> apply(T input) {
                return new NodeMap<T>(Integer.toString(input.hashCode()), input, input
                    .getWeight());
              }
            }));
    return ruleHashPool.get(UUID.randomUUID().toString());
  }

}
