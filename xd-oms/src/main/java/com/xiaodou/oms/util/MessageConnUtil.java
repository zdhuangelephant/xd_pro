
package com.xiaodou.oms.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;




import com.google.common.collect.Maps;
import com.xiaodou.oms.util.model.BizMessageInfo;

/**
 * @ClassName: MessageConnectionUtil

 * @Description: 读取业务线连接配置工具 
 * <p> 配置文件命名格式 message_(productLine)格式</p>

 * @author Guanguo.Gao

 * @date 2014年8月28日 上午10:04:10

 * @version V1.0
 */
public class MessageConnUtil implements Serializable{
    
    /**
     * Default SID
     */
    private static final long serialVersionUID = 1L;

    //key=productType, value=properties
    private static ConcurrentMap<String, Properties> propMap = Maps.newConcurrentMap();

    public static final String MESSAGE_BASEDIR = "/conf/custom/notenv/messageDBConnection";

    
    /**
     * 构造函数
     * 
     * */
    private MessageConnUtil() {
    }
    
    /**
     * @Description: 从文件中获得
     * @param fileName
     * @return
     * @throws
     */
    public static  Properties loadPropertyFromFile(String productLine){
        Properties prop = new Properties();
        String realFileName = MESSAGE_BASEDIR + File.separator + "message_"
                                + productLine + ".properties";
        try {
            InputStream is = MessageConnUtil.class.getResourceAsStream(realFileName);
            prop.load(is);
            if (is != null)
                is.close();
        } catch (IOException e) {
         return null;
        }
        return prop;
    }

    /**
     * @Description: 根据产品线获取属性
     * @param productLine
     * @param propertyName
     * @return
     * @throws
     */
    public static String getProperty(String productLine, String propertyName) {
        Properties prop = propMap.get(productLine);
        if(prop == null){
            prop = loadPropertyFromFile(productLine);
            if(prop == null)
                return "";
            propMap.put(productLine, prop);
        }
        return prop.getProperty(propertyName);
    }
    
    /**
     * @Description: 根据产品线获得连接信息
     * @param productLine
     * @return
     * @throws
     */
    public static BizMessageInfo queryInfoByProduct(String productLine){
        BizMessageInfo info = new BizMessageInfo();
        info.setDriverClassName(getProperty(productLine, "driverClassName"));
        info.setUrl(getProperty(productLine, "url"));
        info.setUsername(getProperty(productLine, "username"));
        info.setPassword(getProperty(productLine, "password"));
        info.setTableName(getProperty(productLine, "tableName"));
        info.setTagColumnName(getProperty(productLine, "tagColumnName"));
        return info;
    }

    
    public static void main(String[] args) {
        //util.getProperty("05", "url");
        for(int i = 0; i < 3; i++){
            new Thread(new Runnable() {
                
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    System.out.println(MessageConnUtil.queryInfoByProduct("05"));
                }
            }).start();
        }
    }
}
