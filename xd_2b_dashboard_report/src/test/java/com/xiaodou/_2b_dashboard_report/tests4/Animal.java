package com.xiaodou._2b_dashboard_report.tests4;

public interface Animal {
  public void doStuff();
}


class Rat implements Animal {
  @Override
  public void doStuff() {
    System.err.println("play with Tom");
  }

}
