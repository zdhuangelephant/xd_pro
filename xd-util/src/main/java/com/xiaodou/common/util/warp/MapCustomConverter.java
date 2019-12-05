package com.xiaodou.common.util.warp;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 自定义Map xml 转换
 * <p/>
 * Date: 2014-5-7 Time: 下午2:12:30
 * 
 * @author tian.dong@corp.elong.com
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MapCustomConverter extends AbstractCollectionConverter {

  /**
   * @param mapper
   */
  public MapCustomConverter(Mapper mapper) {
    super(mapper);
  }

  public boolean canConvert(Class type) {
    return type.equals(HashMap.class) || type.equals(Hashtable.class)
        || type.getName().equals("java.util.LinkedHashMap")
        || type.getName().equals("sun.font.AttributeMap") // Used by java.awt.Font in JDK 6
    ;
  }

  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    Map<String, Object> map = (Map<String, Object>) source;
    for (Entry<String, Object> entry : map.entrySet()) {
      writer.startNode(entry.getKey());
      Object value = entry.getValue();
      if (value instanceof Map) {
        marshal(value, writer, context);
      } else {
        writer.setValue(entry.getValue().toString());
      }

      writer.endNode();
    }
  }



  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    CustomMapList<String, Object> map = new CustomMapList<String, Object>();
    populateMap(reader, map);
    return map;
  }

  protected void populateMap(HierarchicalStreamReader reader, CustomMapList<String, Object> root) {

    CustomMapList<String, Object> child = new CustomMapList<String, Object>();
    root.putMapList(reader.getNodeName(), child);
    for (int i = 0; i < reader.getAttributeCount(); i++) {
      System.out.println(reader.getNodeName() + " " + reader.getAttributeName(i) + ":"
          + reader.getAttribute(i));
      root.putMapList(reader.getAttributeName(i), reader.getAttribute(i));
    }
    while (reader.hasMoreChildren()) {
      reader.moveDown();


      if (reader.hasMoreChildren()) {
        populateMap(reader, child);
      } else {
        child.putMapList(reader.getNodeName(), reader.getValue());
      }


      reader.moveUp();
    }

  }

  // private void populateList(HierarchicalStreamReader reader, CustomArrayList<Map> root) {
  // System.out.println("****list root:"+reader.getNodeName());
  // System.out.println("#####list"+reader.toString());
  //
  // while (reader.hasMoreChildren()) {
  // Map<String,Object> child = new HashMap<String, Object>();
  // root.add(child);
  // reader.moveDown();
  // for(int i=0;i<reader.getAttributeCount();i++){
  // String attrName = reader.getAttributeName(i);
  // String value = reader.getAttribute(i);
  // root.addAttribute(attrName, value);
  // System.err.println("****listmapput"+attrName+" "+value);
  // }
  // System.out.println(reader.getNodeName());
  // if(reader.hasMoreChildren()){
  // populateMap(reader,child);
  // } else {
  // child.put(reader.getNodeName(),reader.getValue());
  // }
  // reader.moveUp();
  // }
  // }
}
