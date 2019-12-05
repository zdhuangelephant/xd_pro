package com.xiaodou.oms.web.controller;

import org.junit.Test;

import com.xiaodou.common.util.CommUtil;

/**
 * Date: 2014/11/19 Time: 14:09
 * 
 * @author Tian.Dong
 */
public class SignTest {
  @Test
  public void test() throws Exception {
    String gorderId = "38904";

    String valiSrc = "%s%s%s%s";
    valiSrc =
        String.format(valiSrc, "CB0FAD9081F98DD992E6E49DBD70FE44", gorderId, "05", "190010007");
    String str = CommUtil.HEXAndMd5(valiSrc);
    System.out.println(str);
  }
}
