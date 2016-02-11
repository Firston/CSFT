package com.oep.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.oep.process.data.Prop;
import com.oep.servlet.Activity;

/**
 * @version 0.3
 * Версия от 15.03.11
 * @author Anthony
 *
 */
public class Logger {
	
	private static Logger instance;
	
	private File file = null;
	private FileWriter fw = null;

	private static String prePath = null; 
	private String path = null;
	private String oldPath = "oldLog_CSFT_";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM EEE dd HH:mm:ss", Locale.ENGLISH);
	private static boolean checkLog = true;
		
	private Logger(){
		if(new File(Activity.SYSTEM_CONFIG).exists()){
			Object prePath = Prop.getPropValue(Activity.SYSTEM_CONFIG, "system_pathLog");			
			if(prePath != null){				
			  this.prePath = prePath.toString();
			  checkLog = Boolean.valueOf(Prop.getPropValue("systemConfig.properties", "system_isLog"));
			  path = getPath();
			}else checkLog = false;	
		}		
	}
	
	public static Logger getInstance(){
		if(instance == null)
			instance = new Logger();
		return instance;
	}
	public static synchronized void init(){
		getInstance();		
	}
	
	
	public static boolean addLog(Object value){
		if(value != null)
		  return getInstance().add(value.toString());
		return false;
	}
	
	private  void newFile(){
		
	  if(new File(path).exists()){
		file = new File(path);
		if(file.length() >= 1000000){
		  int i = 1;
		  while(new File(prePath + oldPath + i +".txt").exists())
		    i++;			
		  file.renameTo(new File(oldPath + i + ".txt"));
		  file = new File(path);
		}
	  }else{
		try {
			new FileOutputStream(path).close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		file = new File(path);
	  }
	  file.setWritable(true);
	}

	private  void newFileWrite(){
		
      try {
      if(file != null)
		fw = new FileWriter(file, true);
      else
    	fw = new FileWriter(file);
	  } catch (IOException e) {
		e.printStackTrace();
	  }		
	}
	
	private  void write(String value){
				 
		try {
		  if(value != null)
			fw.write("[INFO " + sdf.format(new Date()) +"] : " + value + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private  boolean add(String value){
	  
	  if (checkLog){
		try{
		  newFile();
		  newFileWrite();
		  write(value);
		  close();
		  return true;
		}catch(Throwable t){
		 	t.printStackTrace();	
		  return false;
		}
	  }
	  return true;
	}
	private  void close(){

		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isCheckLog() {
		return checkLog;
	}

	public void setCheckLog(boolean checkLog) {
		this.checkLog = checkLog;
	}
	
	public String getPath() {
		return path == null ? prePath + "currentLog_CSFT.txt" : path; 
	}
	
	
	public void setPath(String prePath) {
		this.path = prePath + "currentLog_CSFT.txt";
	}
	
	/*
	 * ДЛЯ ОТЛАДКИ
	 */
	public static void out(String name, Map<String, Object> map){
		
		if(checkLog){
			getInstance().addLog(name + "_out+");
			for(String key : map.keySet())
				getInstance().addLog(key + " : " + map.get(key));
			getInstance().addLog(name + "_out-");			
		}
	}
	
}
