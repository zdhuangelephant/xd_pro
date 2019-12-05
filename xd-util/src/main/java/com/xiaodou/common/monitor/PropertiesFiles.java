package com.xiaodou.common.monitor;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.common.util.FileUtil;

/**
 * Date: 2014/9/26
 * Time: 18:21
 *
 * @author Tian.Dong
 */
public class PropertiesFiles {
  public static Map<String,FileUtil> map = new HashMap<String,FileUtil>();
  public static void put(String fileName,FileUtil fileUtil){
    map.put(fileName,fileUtil);
  }
  public static FileUtil get(String fileName){
    return map.get(fileName);
  }

  public static Map<String, FileUtil> getMap() {
    return map;
  }

  public static void setMap(Map<String, FileUtil> map) {
    PropertiesFiles.map = map;
  }
}
