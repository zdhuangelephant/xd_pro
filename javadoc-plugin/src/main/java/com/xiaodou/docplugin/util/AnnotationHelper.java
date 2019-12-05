package com.xiaodou.docplugin.util;

import java.util.List;

/**
 * 
 * @author bin.song
 *
 */
public class AnnotationHelper {

	/**
	 * 
	 * @param target
	 * @return
	 */
	public static boolean isResponseBody(String target){
		String responseBody = "@ResponseBody";
		return target.equals(responseBody);
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	public static boolean isController(String target){
		String controller = "@Controller";
		return target.equals(controller);
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	public static boolean isRequestMapping(String target){
		String controller = "@RequestMapping";
		if(target.startsWith(controller)){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param annotations
	 * @return
	 */
	public static boolean isHttpApi(List<String> annotations){
		boolean httpApi = false;
		if(annotations != null){
			if(annotations.contains("@Controller")){
				httpApi = true;
			}
		}
		return httpApi;
	}
}
