package com.xiaodou.queue.processer;

import java.io.Serializable;

import com.xiaodou.queue.annotation.ProcesserHandler;

public interface IProcesser extends Serializable, Cloneable {

  public void initialize(ProcesserHandler annotation);
  
  public void process();

  public void processList();

}
