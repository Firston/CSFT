package com.oep.process.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import com.oep.servlet.Activity;

public class Prop {

	private static Properties properties;
	private static Prop instance;	
	private static String path;
	
	private static Prop getInstance(){
		  if(instance == null){
			  instance = new Prop();
		  }
	      if(!new File(Activity.SYSTEM_CONFIG).exists()){
	    	try {
	    	  properties = new Properties();
	    	  /*
	    	   * Обязательные параметры при старте системы
	    	   */
	    	  properties.setProperty("system_isLog", String.valueOf(false));
	    	  properties.setProperty("system_TIME_ACTIVE_SESSION", String.valueOf(300));
	    	  properties.setProperty("system_TIME_LAST_REFRESH", String.valueOf(new Date().getTime()));
			  properties.store(new FileOutputStream(Activity.SYSTEM_CONFIG), null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	      }
		  return instance;
		}
	
    public static synchronized void init(){    	
    	getInstance();
    }
    
	private void load(String path){
		if(this.path == null || !this.path.equals(path)){
			this.path = path;
			properties = new Properties();
			try {			
				InputStream in = this.path.contains("system") 
								 ? new FileInputStream(new File(this.path).getAbsolutePath()) 
								 : getClass().getClassLoader().getResourceAsStream(this.path);
				InputStreamReader inR = new InputStreamReader(in, "UTF-8");
				properties.load(inR);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static String getPropValue(String path, String value){
		getInstance().load(path);
		return properties.getProperty(value);
	}
	
	public static boolean setPropValue(String path, String key, String value){	
  
	  getInstance().load(path);
	  if(properties.contains(key))properties.remove(key);
	  properties.setProperty(key, value);	  
	  try {
		properties.store(new FileOutputStream(new File(path).getAbsolutePath(), true), null);		
		return true;
	  } catch (FileNotFoundException e) {
		e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
	  }
	  return false;
	}	
}
