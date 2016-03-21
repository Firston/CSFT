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
 * @version 0.4
 * Версия от 16.03.21
 * @author Anthony
 *
 */
public class Logger {
	
	private static Logger instance;	

	/**
	 * Директория расположения логов
	 */
	private static String directory;
	/**
	 * Текущий файл записи логов
	 */
	private final static String fileName = "currentLog_CSFT";	
	/**
	 * Тип файла логов
	 */
	private final static String txt = ".txt";
	
	/**
	 * Путь до файла
	 */
	private String path;

	/**
	 * Статус логирования. 
	 * По умолчанию true (включено) 
	 */
	private static boolean checkLog = true;

	private File file = null;
	private FileWriter fw = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM EEE dd HH:mm:ss", Locale.ENGLISH);
	
	private Logger(){
		if(new File(Activity.SYSTEM_CONFIG).exists()){

			Object directory = Prop.getPropValue(Activity.SYSTEM_CONFIG, "system_pathLog");			
			if(directory != null){				
			  this.directory = directory.toString();
			  checkLog = Boolean.valueOf(Prop.getPropValue("systemConfig.properties", "system_isLog"));
			  path  = getPath();
			}else checkLog = false;
		}		
	}
	
	public static Logger getInstance(){
		
		if(instance == null){
			synchronized (Logger.class) {
				if(instance == null){
					instance = new Logger();
				}
			}
		}			
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
		
	  file = new File(path); 
	  if(file.exists()){
		if(file.length() >= 1000000){
		  String oldPath = new StringBuffer().append(directory)
			   								 .append(fileName)
			   								 .append(new Date().getTime())
			   								 .append(txt)
			   								 .toString();
		  System.out.println("Сохранение старого лога : " + file.renameTo(new File(oldPath)));
		  file = new File(path);
		}
	  }else{
		try {
			new FileOutputStream(file).close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		file = new File(getPath());
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
		return path = new StringBuffer().append(directory)
								 		.append(fileName)
								 		.append(txt)
								 		.toString(); 
	}
	
	
	public void setPath(String directory) {
		this.path = directory + "currentLog_CSFT.txt";
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
