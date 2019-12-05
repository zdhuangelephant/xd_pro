package com.xiaodou._2b_dashboard_report.tests4;


public interface Feature {
  public void load();
}

class FlyFeature implements Feature{

  @Override
  public void load() {
     System.out.println("fly");
  }
  
}

class DigFeature implements Feature{

  @Override
  public void load() {
    System.out.println("dig");
  }
  
}