package com.oep.elements_view.navigation;

import java.util.Arrays;
import java.util.List;

import com.oep.dictionary.ListTables;
import com.oep.dictionary.ItemMenu;
import com.oep.dictionary.TypeEvents;
import com.oep.html.AbstractElementHTML;
import com.oep.html.AttributeHTMLElement;
import com.oep.process.ProcessForeground;
import com.oep.process.data.BufferProcess;
import com.oep.process.data.Prop;
import com.oep.utils.Logger;

public class Navigation{

	private static Navigation instance;
	
	private static Navigation getInstance(){
		if(instance == null)
			instance = new Navigation();
		return instance;
	}
	
	private Navigation(){}
	
	/**
	 * Нассив дефалтовых действий над таблицей
	 */
	private static final List<String> defaultEvents = Arrays.asList("del", "edit", "add");
	
	/**
	 * Конфигурационный файл хранящий данные для 
	 * горизонтального и вертикального меню
	 */
	private static final String CONFIG = "config.properties";
	/**
	 * Видимость подменю
	 */
	private boolean isVisibleSubMenu;
	
	private List<String> elements;
	private ProcessForeground process;
	private int countItemSubMenu;		

	public static String getHorizontalMenu(){			
		
		instance = getInstance();
		
		String subContent = "";
		
		instance.process = BufferProcess.getProcess((String)ProcessForeground.map.get("sessionId"));

		AttributeHTMLElement div = new AttributeHTMLElement("div");
		div.setClassName("c_hm_blueline");
		subContent += AbstractElementHTML.createHTMLElement(div);

		div.setClassName(null);
		div.setStyle("float:right;");
		String value = Prop.getPropValue(CONFIG, "ELEMENTS_FOR_" + ProcessForeground.map.get("table").toString().toUpperCase() + "_" + instance.process.getInfoWorkingPerson().get("code_role").toString().toUpperCase());
		if(value != null){
		  instance.elements = Arrays.asList(value.split(","));
		  div.setContent(instance.contentHorizontalMenu(instance.elements));
		  subContent += AbstractElementHTML.createHTMLElement(div);
		}
		
		div.setStyle(null);
		div.setContent(null);
		div.setClassName("c_hm_bluemenu");
		subContent += AbstractElementHTML.createHTMLElement(div);
		
		div.setClassName(null);
		div.setId("id_HM");
		div.setContent(subContent);

		return AbstractElementHTML.createHTMLElement(div);
	}

	/**
	 * version 15.11.26
	 * @return
	 */
	public  static synchronized String getVerticalMenu(){
		instance.isVisibleSubMenu = false;
		/**
		 * при наведении проставляет параметр выбора таблицы
		 */		
		instance = getInstance();
		instance.process = BufferProcess.getProcess((String)ProcessForeground.map.get("sessionId"));
		
		AttributeHTMLElement htmlObj = new AttributeHTMLElement();
		AttributeHTMLElement input = new AttributeHTMLElement("input");			
		
		String data = Prop.getPropValue(CONFIG,
	    		"LIST_MENU_FOR_" + instance.process.getInfoWorkingPerson().get("code_role").toString().toUpperCase()
        );
	    String subContent = "", subMenu;
		String[] attrMenu;
		if(data != null){
			List<String> LIST_MENU = java.util.Arrays.asList(
		    	 data.split(",")
		    );
			for(String itemMenu : LIST_MENU){				
				attrMenu = itemMenu.split(":");			
				subMenu = instance.getSubVerticalMenu(attrMenu[0]);
				
				input.setId("menu_" + attrMenu[0]);
				input.addAttr("type", "image");
				//input.addAttr("src", "/CSFT/image/" + attrMenu[0] + ".png");
				//input.addAttr("src", "/CSFT/image/" + ListTables.getImagepath(attrMenu[0]));
				input.addAttr("src", "/CSFT/image/" + ItemMenu.getImagePath(attrMenu[0]));
				
				htmlObj.setTag("div");
				htmlObj.setClassName("collapsible");
				htmlObj.setStyle("height:48px");
				htmlObj.addAttr("onmouseover", "mouseOver(this,'verticalMenu')");
				htmlObj.addAttr("onmouseout", "mouseOut(this,'verticalMenu')");
				htmlObj.addAttr("onmousedown", "slidedown('subMenu_" + attrMenu[0] + "','menu_" + attrMenu[0] + "')");
				htmlObj.setContent(AbstractElementHTML.createHTMLElement(input) + "<p>" + (attrMenu.length == 1 ? ItemMenu.getDescription(attrMenu[0]) :attrMenu[1])+ "</p>");
				
				subContent += AbstractElementHTML.createHTMLElement(htmlObj);
				
				htmlObj.clearAttr();
				htmlObj.setClassName(null);
				htmlObj.setId("subMenu_" + attrMenu[0]);
				htmlObj.setStyle("display: " + instance.getAttrDisplay() + ";overflow: hidden;height:" + 48 * instance.countItemSubMenu + "px; background-color: gray;");
				htmlObj.setContent(subMenu);
				
				subContent += AbstractElementHTML.createHTMLElement(htmlObj); 
			}			
		}else{
			subContent += instance.getSubVerticalMenu(null); 
		}
		/*
		 * formVerticalMenu - активная форма,используется для 
		 * 					  ноого обращения к серверу
		 * 
		 * formRefreshVerticalMenu - форма хрянящая последнее состояние
		 * 							 отправленное на сервер. 
		 * 							 Служит для обновления отображения.
		 */
		
		String hiddenForms = "", subHiddenForms = "";
		
		String[] hiddenInputs = new String[3];
			
		input.addAttr("type", "hidden");
		
		input.setId("table"); input.addAttr("name", "table"); input.addAttr("value", "");
		hiddenInputs[0] = AbstractElementHTML.createHTMLElement(input); 
		input.setId("elementContentVerticalMenu"); input.addAttr("name", "elementContent"); input.addAttr("value", "");
		hiddenInputs[1] = AbstractElementHTML.createHTMLElement(input);
		input.setId("typeEvent"); input.addAttr("name", "typeEvent"); input.addAttr("value", "select");
		hiddenInputs[2] = AbstractElementHTML.createHTMLElement(input); 
		
		htmlObj.clearAttr(); htmlObj.setClassName(null);htmlObj.setStyle(null);		
		htmlObj.setTag("div");
		htmlObj.setId(null);
		htmlObj.setContent(hiddenInputs[0] + hiddenInputs[1] + hiddenInputs[2]);
		subHiddenForms = AbstractElementHTML.createHTMLElement(htmlObj);
		
		htmlObj.setTag("form");
		htmlObj.setId("formVerticalMenu");
		htmlObj.addAttr("name", "formVerticalMenu"); 
		htmlObj.addAttr("method", "post");
		htmlObj.addAttr("action", "/CSFT/constructor");
		htmlObj.setContent(subHiddenForms);
		hiddenForms = AbstractElementHTML.createHTMLElement(htmlObj);
		
		input.setId("tableRefresh"); input.addAttr("name", "table"); input.addAttr("value", (String) instance.process.map.get("table"));
		hiddenInputs[0] = AbstractElementHTML.createHTMLElement(input);		
		input.setId("elementContentVMRefresh"); input.addAttr("name", "elementContent"); input.addAttr("value", (String) instance.process.map.get("elementContent"));
		hiddenInputs[1] = AbstractElementHTML.createHTMLElement(input);		
		input.setId("typeEventRefresh"); input.addAttr("name", "typeEvent"); input.addAttr("value", "select");
		hiddenInputs[2] = AbstractElementHTML.createHTMLElement(input); 
		
		htmlObj.clearAttr();		
		htmlObj.setTag("div");
		htmlObj.setId(null);
		htmlObj.setContent(hiddenInputs[0] + hiddenInputs[1] + hiddenInputs[2]);
		subHiddenForms = AbstractElementHTML.createHTMLElement(htmlObj);
				
		htmlObj.setTag("form");
		htmlObj.setId("formRefreshVerticalMenu");
		htmlObj.addAttr("name", "formRefreshVerticalMenu"); 
		htmlObj.addAttr("method", "post");
		htmlObj.addAttr("action", "/CSFT/constructor");
		htmlObj.setContent(subHiddenForms);
		hiddenForms += AbstractElementHTML.createHTMLElement(htmlObj);		

		htmlObj.clearAttr();
		htmlObj.setTag("div");
		htmlObj.setId("id_VM");
		htmlObj.setClassName("c_vertical_menu");
		htmlObj.setContent(subContent + hiddenForms);
		
		return AbstractElementHTML.createHTMLElement(htmlObj);
	}	
	
	private String getAttrDisplay(){
		
		if(this.isVisibleSubMenu){
			this.isVisibleSubMenu = false;
			return "block";
		}else{
			return "none";			
		}
	}
	/**
	 * modifited 15.12.11
	 * @param item
	 * @return
	 */
	private String getSubVerticalMenu(String item){
		
		try{
			item = (item != null ? item : "TABLES");
			List<String> LIST_SUBMENU = java.util.Arrays.asList(
			    	Prop.getPropValue(CONFIG,
				    		("MENU_" + item  + "_FOR_" + process.getInfoWorkingPerson().get("code_role").toString()).toUpperCase()
				        ).split(",")
				    ); 		
			return contentVerticalMenu(LIST_SUBMENU);			
		}catch(NullPointerException npe){
			Logger.addLog("Раздел " + item + " не содержит внутренних подразделов");
		}
		return "";
	}
	
	private String contentVerticalMenu(List<String> elements){
		String[] atr; 
		String divColor, textColor, subContent = "";
		countItemSubMenu = 0;
		
		AttributeHTMLElement div = new AttributeHTMLElement("div");
		AttributeHTMLElement input = new AttributeHTMLElement("input");
		for(String element : elements){
		  ++countItemSubMenu;
		  atr = element.split(":");
		  divColor = atr[0].equals(ProcessForeground.map.get("table").toString()) ? "#619be6" : "#B7C8FC";
		  textColor = atr[0].equals(ProcessForeground.map.get("table").toString()) ? "#33363f" : "";
		  isVisibleSubMenu = (!isVisibleSubMenu) ? (atr[0].equals(ProcessForeground.map.get("table").toString()) ? true : false) : true;

		  input.setId(atr[0]);
		  input.addAttr("name", atr[0]);
		  input.addAttr("type", "image");
		  //input.addAttr("src", "/CSFT/image/" + ListTables.getImagepath(atr[0]));
		  input.addAttr("src", "/CSFT/image/" + ItemMenu.getImagePath(atr[0]));

		  div.setStyle("background-color:" + divColor + "; color:" + textColor + "; height:48px;");
		  div.addAttr("onclick", "formVerticalMenu.submit()");
		  div.addAttr("onMouseOver", "mouseOver(this,'verticalMenu')");
		  div.addAttr("onMouseOut", "mouseOut(this,'verticalMenu')");		  
		  div.setContent(AbstractElementHTML.createHTMLElement(input) + "<p>" + (atr.length == 1 ? ItemMenu.getDescription(atr[0]) : atr[1]) + "</p>");
		  		  
		  subContent += AbstractElementHTML.createHTMLElement(div);		  		 
		}
		return subContent;
	}
	
	private String contentHorizontalMenu(List<String> elements){
		
		String subContent = "";

		String [] attr;
		contentHM: for(String element : elements){
		  if(element == null || element.equals("") ) break contentHM;
		  
		  attr = element.split(":");
		  if(attr[0].contains("_")){
			  /*
			   * елемент содержит единичное действие
			   */
			  subContent += onClick(Arrays.asList(attr[0].split("_")[0]), attr[0].split("_")[1], attr[1]);
		  }else{
			  /*
			   * элемент содержит набор действий по умолчанию.
			   * Перечень возможных - add, edit,del
			   */			 
			  subContent += onClick(Arrays.asList(attr[1].split("_")), attr[0], null);
		  }
		}
		return subContent;
	}
	
	private String onClick(List<String> events, String tableName, String rusDescription){
		
		String subContent = "";
		AttributeHTMLElement htmlObj = new AttributeHTMLElement();
		
		boolean isImg = defaultEvents.containsAll(events) ? true : false;
		for(String event : events){
			
			htmlObj.setId(event + "_" + tableName);
			htmlObj.addAttr("onClick", getOnClick(tableName , TypeEvents.getValue(event)));
			if(isImg){
				htmlObj.setTag("img");				
				htmlObj.addAttr("src", "/CSFT/image/" + event + ".png");				
			}else{
				htmlObj.setTag("div");
				htmlObj.setClassName("c_hm_blok");
				htmlObj.setContent(rusDescription != null ? rusDescription : "");
			}					
			subContent += AbstractElementHTML.createHTMLElement(htmlObj); 
		}		
		htmlObj.clearAttr();
		htmlObj.setTag("div");
		htmlObj.setId(null);
		htmlObj.setClassName("c_hm_blok");
		if(isImg)htmlObj.setContent("<p>" + ItemMenu.getDescription(tableName) + " </p>" + subContent);
		else return subContent;
		
		return AbstractElementHTML.createHTMLElement(htmlObj);
	}	

	private String getOnClick(String value, TypeEvents typeEvents){
	
		switch (typeEvents) {
		  case ADD:{
			  switch(ListTables.getValue(value)){		  
			    //case SERVICES : 
			    case SUBSERVICES : {
			      return "createFormAdd('tree', 'table_" + value + "'), removeMessage()"; 
			    }
			    default : {
			      return "createFormAdd('table', 'table_" + value + "'), removeMessage()";
			    }
			  }	
		  }		
		  case DEL : {
			  switch(ListTables.getValue(value)){		  
			    //case SERVICES : 
			    case SUBSERVICES : {
			      return "del(this,'tree'), removeMessage()";
			    }
			    default : {
			    	return "del(this,'table'), removeMessage()";
			    }
			  }			  
		  }
		  case EDIT : {
			  switch(ListTables.getValue(value)){		  
			    //case SERVICES : 
			    case SUBSERVICES : {
			     return "createFormEdit('tree', 'table_" + value + "'), removeMessage()";
			    }
			    case CONTENTSUBSERVICES : {
			    	return "createFormShowOrEdit('table_contentSubServices', 'defaultValue,id_listPrioritie'), removeMessage()";	
			    }
			    default : {
			     return "createFormEdit('table', 'table_" + value + "'), removeMessage()";
			    }
			  }			  
		  }
		  case SHOW : {
			  switch(ListTables.getValue(value)){		  
			    //case SERVICES : 
			    case SUBSERVICES : {
			      return ""; 
			    }
			    case SCRIPTS :
			    case COMMISSIONS : 
			    case TYPEOBJECT :
			    case LISTGROUPOBJECTS :	
			    case LISTPRIORITIES :
			    case LISTCLASSSERVICE : {
			      return "createFormShow(this.id, null, '" + ProcessForeground.map.get("table") + "'), removeMessage()";
			    }
			    default : {
			      return "createFormShow(this, 'table', '" + ProcessForeground.map.get("table") + "'), removeMessage()";
			    }
			  }			  
		  }
		  case REFRESH : {
			  return "formRefreshVerticalMenu.submit()";
		  }
		  case FILTER : {
			  
			  return "execute_filter('" + value + "')";
		  }
		  
		  default:
			return "";
		}		  
	}
}