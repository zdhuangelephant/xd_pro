package com.xiaodou.autobuild;

import com.xiaodou.autobuild.tool.MybatisXmlTool;

public class Main {
  public static void main(String[] args) {
    MybatisXmlTool.getInstance(RaiseWrongQues.class).buildXml();
  }
}
