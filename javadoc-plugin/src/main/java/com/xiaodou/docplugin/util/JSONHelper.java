package com.xiaodou.docplugin.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 
 * @author bin.song
 *
 */
public class JSONHelper {

	/**
	 * 
	 * @param uglyJSONString
	 * @return
	 */
	public static String jsonFormatter(String uglyJSONString){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jsonParser = new JsonParser();
		try {
			JsonElement jsonElement = jsonParser.parse(uglyJSONString);
			String prettyJSONString = gson.toJson(jsonElement);
			return prettyJSONString;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(uglyJSONString);
			return "{}";
		}
	}
}
