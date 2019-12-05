package com.xiaodou.mooccrawler.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.mooccrawler.domain.course.CourseResourceAudioModel;

public class AudioDetailUtil {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject getJsonString(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  public static void packageAudio(String url,CourseResourceAudioModel model) { 
    JSONObject json;
	try {
		 if(StringUtils.isBlank(url)){
			 return ;
		 }
		 json = getJsonString(url);
		 System.out.println(json.toString());
		 Double second=Double.valueOf((String) ((JSONObject) json.get("format")).get("duration"));
		 Double size=Double.valueOf((String) ((JSONObject) json.get("format")).get("size"));
		 if(second!=null){
			 Integer minutes = (int) (second/60);
	         Integer seconds = (int) (second-minutes*60);
	         model.setTimeLengthMinute(minutes);
	         model.setTimeLengthSecond(seconds);
		 }
		 if(size!=null){
			 model.setSize(size/1024/1024);
		 }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		LoggerUtil.error("获取音频信息失败", e);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		LoggerUtil.error("获取音频信息失败", e);
	}
  }
}

