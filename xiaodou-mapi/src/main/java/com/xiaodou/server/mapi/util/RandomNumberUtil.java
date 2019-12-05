package com.xiaodou.server.mapi.util;

import java.util.Random;

/**
 * 
 * 随机数生成类
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public class RandomNumberUtil{
  
  private static Random randGen = null;
  private static char[] numbersAndLetters = null;

  private static Random randGen2 = null;
  private static char[] numbersAndLetters2 = null;
  
  /**
   * 
   * 生成指定位数的随机数 
   *
   * @param length
   * @return
   */
  public static final String randomString(int length) {
    if (length < 1) {
      return null;
    }
    if (randGen2 == null) {
      randGen2 = new Random();
      numbersAndLetters2 =
          ("0123456789").toCharArray();
    }
    char[] randBuffer = new char[length];
    for (int i = 0; i < randBuffer.length; i++) {
      randBuffer[i] = numbersAndLetters2[randGen2.nextInt(9)];
    }
    return new String(randBuffer);
  }
  
  /**
   * 
   * 获取指定长度随机数 
   *
   * @param length
   * @return
   */
  public static final String randomStringAll(int length) {
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

  /**
   * 
   * 获取指定范围内的随机数
   *
   * @param startLength
   * @param endLength
   * @return
   */
  public static final String getRandomString(int startLength, int endLength) {
    if(startLength < 1 || endLength < 1 || endLength < startLength)
      return null;
    Random random = new Random();
    int randomInt = random.nextInt(100);
    int result = (randomInt % (endLength - startLength + 1)) + startLength;
    return RandomNumberUtil.randomStringAll(result);
  }
  
}
