package com.oep.html;

import java.util.HashMap;
import java.util.Map;

import com.oep.utils.Logger;

public class AttributeHTMLElement {
	
	/**
	 * Добавлено 15.03.11
	 */
	public AttributeHTMLElement(){
		this.tag = "noTag";		
	}
	/**
	 * Обновлено в версии 15.03.11 
	 * @param tag
	 */
	public AttributeHTMLElement(String tag){
		if(tag != null) this.tag = tag;
		else {
 		  Logger.addLog("Error: создан объек класса AttributeHTMLElement без параметра `tag`");
		  this.tag = "noTag";
		}
	}
	
	/**
	 * Обновлено в версии 15.03.11 
	 * @param tag
	 * @param id
	 * @param className
	 * @param style
	 * @param content
	 */
	public AttributeHTMLElement(String tag, String id, String className,
								String style, Object content){
		if(tag != null){
		  this.tag = tag;
		  this.id = id;
		  this.className = className;
		  this.style = style;
		  this.content = content;
		}else{
		  Logger.addLog("Error: создан объек класса AttributeHTMLElement без параметра `tag`. Остальные параметры не сохранены!");
		  this.tag = "noTag";
		}
	}

	private String tag;

	private String id;
	
	private String className;
	
	private String style;
	
	private Object content;
	
	private Map<String, String> other;

	public String getId() {
		//return id != null ? "id=\"" + id +"\" " : "";
		return id != null ? "id=\"" + id +"\"" : "";
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		//return className != null ? "class=\"" + className + "\" " : "";
		return className != null ? "class=\"" + className + "\"" : "";
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStyle() {
		//return style != null ? "style=\"" + style + "\" " : "";
		return style != null ? "style=\"" + style + "\"" : "";
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Map<String, String> getMapAttr() {
		if(other == null)
			other = new HashMap<String, String>();
		return other;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Object getContent() {
		return content != null ? content : "";
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
	
	/**
	 * @version 15.02.20
	 * @author Anthony	
	 * @param name
	 * @param value
	 */
	public void addAttr(String name, String value){
		try{
		  getMapAttr().put(name, value);	
		}catch(NullPointerException e){
			Logger.addLog("Warning - " + e  + " name: " + name + " value: " + value + 
						  "\r\nMessage - свойство не было добавлено");
		}
	}
	
	public void removeAttr(String name){
		
		getMapAttr().remove(name);
	}
	/**
	 * @version 15.02.20
	 * @author Anthony
	 * @param Очищает список дополнительных аттрибутов. 
	 * НЕ ОБНУЛЯЕТ : tag, id, className, style, content;	
	 * 
	 */
	public void  clearAttr() {
		getMapAttr().clear();
	}
}
