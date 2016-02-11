package com.oep.html;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractElementHTML {

	/**
	 * Универсальный построитель елемента со свойствами
	 * применим для следующих тегов : span, p
	 * @param attr
	 * @return
	 */
	public static String createHTMLElement(AttributeHTMLElement attr){
		if(attr.getMapAttr() == null)
		  return createHTMLElemen_1(attr.getTag(), attr.getId(), attr.getClassName(), attr.getStyle(), attr.getContent());
		else
		  return createHTMLElemen_1(attr.getTag(), attr.getId(), attr.getClassName(), attr.getStyle(), attr.getContent(), attr.getMapAttr());
	}
	
	/**
	 * 
	 * @param tag 
	 * @param id
	 * @param className
	 * @param style
	 * @param content
	 * @return
	 */
	public static String createHTMLElemen(String tag, String id, String className, String style, Object content){
		return "<" + tag + " " + id +" " + className + " " +  style + ">" + content + "</" + tag + ">";	
	}
	
	/**
	 * 
	 * @param tag
	 * @param id
	 * @param className
	 * @param style
	 * @param content
	 * @param other
	 * @return
	 */
	public static String createHTMLElemen(String tag, String id, String className, String style, Object content, Map<String, String> other){
		return "<" + tag + " " + id +" " + className + " " +  style + getOttehrAttibute(other) + ">" + content + "</" + tag + ">";	
	}
	
	private static String getOttehrAttibute(Map<String, String> map){
		String otherAttributes = "";
		for(String key : map.keySet())
			otherAttributes +=  " " + key + "=\"" + map.get(key) + "\"";
		return otherAttributes;
	}

	/**
	 * version 15.12.01 - add
	 * @param tag
	 * @param id
	 * @param className
	 * @param style
	 * @param content
	 * @param other
	 * @return
	 */
	private static String createHTMLElemen_1(String tag, String id, String className, String style, Object content, Map<String, String> other){
		return "<" + tag + ((id == "" ? "" : " " + id)) +
						   ((className == "" ? "" : " " + className)) + 
						   ((style == "" ? "" : " " + style)) +   
						   getOttehrAttibute(other) + ">" + 
						   ((content == "" ? "" : " " + content)) +  
			   "</" + tag + ">";	
	}
	
	/**
	 * version 15.12.01 - add
	 * @param tag 
	 * @param id
	 * @param className
	 * @param style
	 * @param content
	 * @return
	 */
	private static String createHTMLElemen_1(String tag, String id, String className, String style, Object content){
		return "<" + tag + ((id == "" ? "" : " " + id)) + 
						   ((className == "" ? "" : " " + className)) + 
						   ((style == "" ? "" : " " + style)) + ">" + 
						   ((content == "" ? "" : " " + content)) + 
			   "</" + tag + ">";	
	}
}
