package com.xiaodou.common.util;

import java.util.Random;

/**
 * 随机数操作类
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-1-23
 */
public class RandomUtil {

  private static Random randGen = null;
  private static char[] numbersAndLetters = null;

  public static final String randomString(int length) {
    if (length < 1) {
      return null;
    }
    if (randGen == null) {
      randGen = new Random();
      numbersAndLetters =
          ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
              .toCharArray();
    }
    char[] randBuffer = new char[length];
    for (int i = 0; i < randBuffer.length; i++) {
      randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
    }
    return new String(randBuffer);
  }
  
  public static final String randomNumber(int length) {
    if (length < 1) {
      return null;
    }
    if (randGen == null) {
      randGen = new Random();
      numbersAndLetters =
          ("0123456789")
              .toCharArray();
    }
    char[] randBuffer = new char[length];
    for (int i = 0; i < randBuffer.length; i++) {
      randBuffer[i] = numbersAndLetters[randGen.nextInt(9)];
    }
    return new String(randBuffer);
  }

}
