package com.xiaodou.im.agent.qq.util;

/**
 * u("1209440675","0f6e83a949b32cc04d832618d7049b9dca6619f7aaaafffd00bcfbdddb8728cd")
 * "0154065C0C07510F030C5301025A570404520F0603040101500300020E57085653580202010F5102505351585252565207055351565B505054540F02030A535D44554345405A4356105C46465F44"
 * <p/>
 * Date: 2014/12/12
 * Time: 17:53
 *
 * @author Tian.Dong
 */
public class GenHashUtil {
  //5757565D0701080307015050520B510755570107000752010D520354000C5303040D570403525403550252090355515253530150060B5703055207005200060144554345405A4356105C46465F44
  public static void main(String[] args) {
    String result = hash("1209440675", "fefd358504abb2e3ea6215b89f3b79b144c03dc6d0b07aaddf0b62c75d05c268");
    System.out.println(result);
    System.out.println(result.equals("5757565D0701080307015050520B510755570107000752010D520354000C5303040D570403525403550252090355515253530150060B5703055207005200060144554345405A4356105C46465F44"));
  }

  public static String hash(String uin, String ptwebqq) {
    String n = ptwebqq + "password error";
    String t = "";
    while (t.length() < n.length()) {
      t = t + uin;
    }
    t = t.substring(0, n.length());

    int[] v = new int[t.length()];
    for (int i = 0; i < t.length(); i++) {
      v[i] = t.charAt(i) ^ n.charAt(i);
    }
    char[] N = {'0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    String result = "";
    for (int i = 0; i < v.length; i++) {
      result += N[v[i] >> 4 & 15];
      result += N[v[i] & 15];
    }
    return result;
  }
}

/**
 * function (x, K) {
 for (var N = K + "password error", T = "", V = []; ;)

 if (T.length <= N.length) {
 T += x;
 if (T.length == N.length)break
 } else {
 T = T.slice(0, N.length);
 break
 }
 for (var U = 0; U < T.length; U++)V[U] = T.charCodeAt(U) ^ N.charCodeAt(U);
 N = ["0", "1", "2", "3",
 "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"];
 T = "";
 for (U = 0; U < V.length; U++) {
 T += N[V[U] >> 4 & 15];
 T += N[V[U] & 15]
 }
 return T
 }
 */