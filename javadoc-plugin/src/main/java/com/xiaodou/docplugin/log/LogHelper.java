package com.xiaodou.docplugin.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日志记录
 * @author bin.song
 *
 */
public class LogHelper {
	private static final String _LOGPATH = System.getProperty("user.dir") + "/log/";
	private static final String _BASELOGNAME = "javadoc";
	private static final String _LOGFORMAT = "_yy_MM_dd_HH";
	private static final String _LOGTIMEFORMAT = "yyyy-MM-dd HH:mm:ss SSS";
	private static Object _lockPad = new Object();
	private static Object _lockPad4Write = new Object();
	private static File _Logger ;
	
	/**
	 * ��¼��־����
	 * @param msg
	 * ��־
	 */
	public static void Log(String msg){
		try{
			CreateFile();
			RealLog(msg);
			DelExpiredLog();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
		
	/**
	 * ������־�ļ�
	 * @throws IOException
	 */
	private static void CreateFile(){
		Date date = new Date();
		File dir = new File(_LOGPATH);
		if(!dir.exists()){
			dir.mkdirs();
		}
		SimpleDateFormat dFormat = new SimpleDateFormat(_LOGFORMAT);
		String logName = _LOGPATH + _BASELOGNAME + dFormat.format(date) + ".log";
		_Logger = new File(logName);
		if(!_Logger.exists()){
			synchronized (_lockPad) {
				try {
					_Logger.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ��¼��Ϣ
	 * @param msg
	 */
	private static void RealLog(String msg){
		try {
			synchronized (_lockPad4Write) {
				FileWriter fWriter = new FileWriter(_Logger, true);
				Date date = new Date();
				SimpleDateFormat dFormat = new SimpleDateFormat(_LOGTIMEFORMAT);
				fWriter.write("LogTime : " + dFormat.format(date) + "\r\n");
				String splitMakr = "================================================================\r\n";
				String logs = msg + "\r\n";
				logs += splitMakr;
				fWriter.write(logs);
				fWriter.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * ɾ�������־�ļ�
	 * @throws IOException
	 */
	private static void DelExpiredLog(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -5);
		Date date = calendar.getTime();
		SimpleDateFormat dFormat = new SimpleDateFormat(_LOGFORMAT);
		String logName = _LOGPATH + _BASELOGNAME + dFormat.format(date) + ".log";
		_Logger = new File(logName);
		if(_Logger.exists()){
			synchronized (_lockPad) {
				_Logger.delete();
			}
		}
	}
}
