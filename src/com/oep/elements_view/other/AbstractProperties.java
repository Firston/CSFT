package com.oep.elements_view.other;

import java.util.Map;

import com.oep.elements_interface.Properties;
import com.oep.process.ProcessForeground;
import com.oep.process.data.Prop;
import com.oep.servlet.Activity;

public abstract class AbstractProperties implements  Properties{

	/**
	 * Параметр в котором приходят измененые настройки
	 */
	private static final String SYSTEM_PROPERTY = "system_property";
	
	public static String[] attrParam;

	public boolean save(Map<String, Object> map, ProcessForeground process) {
		System.out.println(map.get(SYSTEM_PROPERTY));
		attrParam = map.get(SYSTEM_PROPERTY).toString().split("::");
		if(attrParam[0] != null && attrParam[1] != null){
		  if(!attrParam[1].equals(Prop.getPropValue(Activity.SYSTEM_CONFIG, attrParam[0]))){
				Prop.setPropValue(Activity.SYSTEM_CONFIG, attrParam[0], attrParam[1]);
				map.remove(SYSTEM_PROPERTY);
				return true;
		  }			
		}else map.remove(SYSTEM_PROPERTY);	
		return false; 
	}

	
}
