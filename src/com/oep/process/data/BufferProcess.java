package com.oep.process.data;

import java.util.HashMap;
import java.util.Map;

import com.oep.process.ProcessForeground;

public class BufferProcess{

	/**
	 * key - ID session
	 */
	private static Map<String, ProcessForeground> map;
	
	private static BufferProcess instance;
	
	private BufferProcess (){
		if(map == null)
		  map = new HashMap<String, ProcessForeground>();
	}
	
	public static BufferProcess getInstance(){
		if(instance == null){
			instance =  new BufferProcess();			
		}
		return instance; 
	}
	public static void init(){
		getInstance();
	}
	
	public static Map<String, ProcessForeground> getMap(){
		return getInstance().map;
	}
	
	public static ProcessForeground getProcess(String sessionId){
		return getMap().get(sessionId);
	}

}
