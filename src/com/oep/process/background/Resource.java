package com.oep.process.background;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.oep.utils.Encoding;

public class Resource {

	private static Resource instance;
	
	private Resource(){}
	
	
	private static Resource getInstance(){
		
		if(instance == null)
			instance = new Resource();
		
		return instance;
	}
	
	/**
	 * Загрузка файла ресурса
	 * @param path
	 * @return
	 */
	public static synchronized String loadResource (String path){
		
		try {
		  InputStream in = getInstance().getClass().getClassLoader().getResourceAsStream(path);
		  InputStreamReader inR = new InputStreamReader(in, Encoding.UTF8);
		  int c = 0;
		  String result = "";
		  while((c=inR.read()) > -1)
			result +=(char)c;
		  return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
