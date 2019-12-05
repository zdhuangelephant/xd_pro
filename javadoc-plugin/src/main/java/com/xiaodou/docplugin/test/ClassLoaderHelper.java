package com.xiaodou.docplugin.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;

public class ClassLoaderHelper {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub

		String basePath = 
				"D:/code/Java/java-standard-project/elongorder/giftcardms/Trunk/target/giftcardms.jar";
		
		URL url1 = new URL("file:" + basePath);  
        URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 }, Thread.currentThread()  
                .getContextClassLoader());  
        Class<?> myClass1 = myClassLoader1.loadClass("com.elong.giftmis.vo.OrderStatus");
		
		Object object = myClass1.newInstance();
		String jsonStr = JSON.toJSONString(object);
		
	}

}
