package nettyDemo.chat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.alibaba.fastjson.JSON;


@SuppressWarnings("unchecked")
public class JsonUtil {

  /**
   * Json类型转换为Map类型
   * 
   * @param jsonStr
   * @return jsonStr对应的map对象
   */
  public static HashMap<String, Object> jsonToMap(String jsonStr) {
    return JSON.parseObject(jsonStr, HashMap.class);
  }


  /**
   * Json类型转换为Map<String, objectClass>类型
   * 
   * @param jsonStr
   * @param objectClass
   * @return jsonStr对应的Map数据
   */
  public static Map<String, Object> jsonToMap(String jsonStr, Class<? extends Map> objectClass) {
    return JSON.parseObject(jsonStr, objectClass);
  }

  /**
   * Map类型转换为Json类型
   * 
   * @param map
   * @return map对应的json数据
   */
  public static String mapToJson(Map map) {
    return JSON.toJSONString(map);
  }

  /**
   * Json类型转换为Bean类型
   * 
   * @param jsonStr
   * @param objectClass
   * @return jsonStr对应的Bean对象
   */
  public static Object jsonToBean(String jsonStr, Class objectClass) {
    return JSON.parseObject(jsonStr, objectClass);
  }

  /**
   * Bean类型转换为Json类型
   * 
   * @param object
   * @return bean对应的json串
   */
  public static String BeanToJson(Object object) {
    return JSON.toJSONString(object);
  }

  /**
   * List类型转换为Json类型
   * 
   * @param list
   * @return list对应的json串
   */
  public static String ListToJson(List list) {

    JSONArray jsonArray = JSONArray.fromObject(list);
    return jsonArray.toString();
  }

  /**
   * Json类型转换为List<objectClass>类型
   * 
   * @param jsonStr
   * @param listClass
   * @param objectClass
   * @return jsonStr对应的list数据
   */
  public static List jsonToList(String jsonStr, Class listClass, Class objectClass) {

    JsonConfig config = new JsonConfig();
    config.setCollectionType(listClass);
    config.setRootClass(objectClass);
    JSONArray jsonArray = JSONArray.fromObject(jsonStr);
    return (List) JSONArray.toCollection(jsonArray, config);
  }

  /**
   * Json类型转换为List<objectClass>类型
   * 
   * @param jsonStr
   * @param objectClass
   * @return jsonStr对应的List数据
   */
  public static List jsonToList(String jsonStr, Class objectClass) {

    JSONArray jsonArray = JSONArray.fromObject(jsonStr);
    return (List) JSONArray.toCollection(jsonArray, objectClass);
  }

  /**
   * Json类型转换为List<Map>类型
   * 
   * @param jsonStr
   * @return jsonStr对应的List
   */
  public static List jsonToList(String jsonStr) {

    JSONArray jsonArray = JSONArray.fromObject(jsonStr);
    return (List) JsonUtil.parseJSONObj(jsonArray);
  }

  /**
   * Json类型转换为Array<objectClass>类型
   * 
   * @param jsonStr
   * @param objectClass
   * @return jsonStr对应的array对象
   */
  public static Object jsonToArray(String jsonStr, Class objectClass) {

    JSONArray jsonArray = JSONArray.fromObject(jsonStr);
    return (Object) JSONArray.toArray(jsonArray, objectClass);
  }

  /**
   * Json类型转换为Array类型
   * 
   * @param jsonStr
   * @return json对应的array类型
   */
  public static Object jsonToArray(String jsonStr) {

    JSONArray jsonArray = JSONArray.fromObject(jsonStr);
    List list = (List) JsonUtil.parseJSONObj(jsonArray);
    return list.toArray();
  }

  /**
   * Array类型转换为Json类型
   * 
   * @param array
   * @return array对应的json串
   */
  public static String ArrayToJson(Object array) {

    JSONArray jsonArray = JSONArray.fromObject(array);
    return jsonArray.toString();
  }

  /**
   * 递归地将JSONArray转换为List对象，将JSONObject转换为Map对象
   * 
   * @param obj
   * @return
   */
  private static Object parseJSONObj(Object obj) {

    Object result = null;
    if (obj == null) {
      // error
      return null;
    } else {
      if (obj instanceof JSONArray) {
        JSONArray arrayObj = (JSONArray) obj;
        List<Object> list = new ArrayList<Object>();
        for (Object element : arrayObj.toArray()) {
          list.add(JsonUtil.parseJSONObj(element));
        }
        result = list;
      } else if (obj instanceof JSONObject) {
        JSONObject jsonObj = (JSONObject) obj;
        Map<String, Object> map = new HashMap<String, Object>();
        for (Object key : jsonObj.keySet()) {
          map.put(key.toString(), JsonUtil.parseJSONObj(jsonObj.get(key.toString())));
        }
        return map;
      } else {
        result = obj;
      }
    }
    return result;
  }

  /**
   * 根据Object生成json字符串
   * 
   * @param object
   * @return
   */
  public static String getJsonStrFromObj(Object object) {
    JSONObject jsonObj = JSONObject.fromObject(object);
    return jsonObj.toString();
  }

}
