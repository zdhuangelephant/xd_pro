package com.xiaodou.course.service.order;

import lombok.Data;

@Data
public class Context {

  public static interface Strategy {
    public Boolean useStrategy(Long productId);
  }

  private Strategy strategy;

  public Context(Strategy strategy) {
    this.strategy = strategy;
  }

  public Boolean strategyMethod(Long productId) {
    return strategy.useStrategy(productId);
  }

}
