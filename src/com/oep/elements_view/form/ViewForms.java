package com.oep.elements_view.form;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.oep.db.sql.ResultSQL;
import com.oep.dictionary.TypeEvents;
import com.oep.elements_interface.Forms;
import com.oep.elements_view.button.Button;
import com.oep.elements_view.button.ViewButton;
import com.oep.html.AbstractElementHTML;
import com.oep.html.AttributeHTMLElement;
import com.oep.process.ProcessForeground;
import com.oep.process.data.BufferProcess;
import com.oep.process.data.Prop;
import com.oep.servlet.Activity;
import com.oep.utils.Logger;

public class ViewForms implements Forms{

	
	private static ViewForms instance;
	private static String contentForm;
	public static ViewForms getInstance(){
		if(instance == null)
			instance = new ViewForms();
		return instance;
	}
	/*
	 * список параметров :
	 * 
	 * getForm - построение формы по сорержимому в нем значению (Пример: subServices - построение формы по процедурам)
	 * elementContent - парамтр отвечающий за то, что будет отрисовываться в контенте
	 * table - параметр устанавлвается для формирование SQL-запроса
	 * typeEvent - тип действий с данными (полный список в enum TypeEvents)
	 */
	@Override
	public void create(ResultSet resultSet) {
		contentForm = "";
		contentForm += "<div class=\"c_form\">";
			switch (TypeEvents.getValue((String)ProcessForeground.map.get("typeEvent"))) {
			case ADD : {
				formAdd(resultSet);
				break;
			}
			default:
				break;
			}
		contentForm += "</div>";		
	}
	
	public synchronized String getForm(ResultSet resultSet){
		create(resultSet);
		return contentForm;
		
	}
	public synchronized void getForm(ResultSet resultSet, PrintWriter pw){
		create(resultSet);
		pw.write(contentForm);		
	}

	@Override

	public void createElementOfForm(String field, String type, String className, String value) {
		className = className != null ? className : "";
		value = value != null ? value : "";
		String[] atr = field.split(":");		
		if(atr.length > 1 && !atr[0].equals("id")){
			contentForm += "<div class=\"c_form_blok\">" +
					   "<p>" + atr[1] + "</p>" +
					   "<input id=\"" + atr[0] + 
					   	   "\" name=\"" + atr[0] + 
					   	   "\" class=\"" + className + 
					   	   "\" type=\"" + type + 
					   	   "\" value=\"" + value + "\" " + 
					   	   (type.equals("checkbox") ? checked(value) : "") + ">" +
					 "</div>";
		}
	}
	/**
	 * Построение формы добавления записей.
	 * Реализовано для построения формы при переходе от дерева. КОСЯК!
	 * Остальное реализовано в func.js
	 * @param resultSet
	 */
	
	private void formAdd(ResultSet resultSet){
		contentForm += "<p> Добавление записи в услугу " + ProcessForeground.map.get("id") + "</p>" +
				"<form id=\"formAdd_" + ProcessForeground.map.get("table") + "\" " +
					  "name=\"formAdd_" + ProcessForeground.map.get("table") + "\" " +
					  "method='POST' " +
					  "action='/CSFT/constructor' " +
					  "accept-charset=\"UTF-8\">";
		
				boolean b;
				try {
					b = resultSet.first();
					while(b){		
					 createElementOfForm(resultSet.getString("Field"), "text", "c_input", null);
					 b = resultSet.next();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String elementContent ="";
				if(ProcessForeground.map.get("getForm").equals("services") || 
				   ProcessForeground.map.get("getForm").equals("subServices")) elementContent = "tree";
				else elementContent ="table";
				contentForm += "<img id = \"trSave\" src=\"/CSFT/image/knopka.png\" onclick=\"formAdd_" + ProcessForeground.map.get("table") + ".submit()\" " +
																				  "onmouseover=\"mouseOver(this.id, null)\" " +
																				  "onmouseout=\"mouseOut(this.id)\"/>" +
						"<input type=\"hidden\" id=\"tableFormaAdd\" name=\"table\" value=\"" +  ProcessForeground.map.get("getForm") + "\">" +
						"<input type=\"hidden\" id=\"elementContentFormaAdd\" name=\"elementContent\" value=\"" + elementContent + "\">" +
						"<input type=\"hidden\" id=\"typeEventFormaAdd\" name=\"typeEvent\" value=\"insert\">";
				if(ProcessForeground.map.get("getForm").equals("subServices"))
					contentForm += "<input type=\"hidden\" id=\"id_service\" name=\"id\" value=\"" + ProcessForeground.map.get("id") + "\">"; 
		
		contentForm += "</form>";
	}
	/**
	 * Форма для блока логирования.
	 * Временное решение . Переделать  через единичную отправку GET звпроса
	 * @return
	 */
	public static String formLog(){
		AttributeHTMLElement attr = new AttributeHTMLElement("nonTag");
		String result = "", buff = "";
		AttributeHTMLElement attrDiv = new AttributeHTMLElement("div", null, "c_form_blok", null, null);
		/*
		 * Путь к файлам логов
		 */
		attr.setClassName("c_label_element");
		attr.setContent("Путь к файлам логов");
		buff = AbstractElementHTML.createHTMLElement(attr);
		
		attr.setTag("input");
		String idElement = "system_pathLog";
		attr.setId(idElement);
		attr.setClassName(null);
		attr.addAttr("name", idElement);
		attr.addAttr("type", "text");
		attr.addAttr("value", Prop.getPropValue(Activity.SYSTEM_CONFIG, idElement));
		attr.setContent(null);
		buff += AbstractElementHTML.createHTMLElement(attr);

		/*
		 * Отрисовка кнопки сохранить
		 */
		attr.setId(null);
		attr.clearAttr();
		attr.setContent("Сохранить");
		attr.setStyle("width:90px;height:100%");
		attr.addAttr("onClick", "saveTools('" + idElement + "'), removeMessage()");
		buff += Button.getInstance().getHTMLObject(attr);
		
		attrDiv.setContent(buff);
		result += AbstractElementHTML.createHTMLElement(attrDiv);
		
		/*
		 * Логирование  
		 */
		attr.setTag("span");
		attr.setClassName("c_label_element");
		attr.setContent("Включить логирование");
		buff = AbstractElementHTML.createHTMLElement(attr);
		
		attr.setTag("input");
		attr.setId("system_isLog");
		attr.setClassName(null);
		attr.addAttr("name", "system_isLog");
		attr.addAttr("type", "checkbox");
		String value = Prop.getPropValue(Activity.SYSTEM_CONFIG, "system_isLog");
		attr.addAttr("value", value);
		if(Boolean.valueOf(value))
		  attr.addAttr("checked", checked(value));
		attr.addAttr("onchange", "change(this,'properties')");	
		attr.addAttr("onClick", "saveTools(this.id)");
		attr.setContent(null);
		buff += AbstractElementHTML.createHTMLElement(attr);		
		attrDiv.setContent(buff);
		result += AbstractElementHTML.createHTMLElement(attrDiv);
		
		return result;
	}

	public static String formSystemConfig(){
	
		AttributeHTMLElement attr = new AttributeHTMLElement("nonTag");
		String result = "", buff = "";
		AttributeHTMLElement attrDiv = new AttributeHTMLElement("div", null, "c_form_blok", null, null);
		/*
		 * путь к системному файлу настроек
		 */
		attr.setTag("span");
		attr.setClassName("c_label_element");
		attr.setContent("Расположение системного файла настроек : ");	
		buff = AbstractElementHTML.createHTMLElement(attr);
		
		attr.setClassName(null);
		attr.setContent(new File(Activity.SYSTEM_CONFIG).getAbsolutePath());
		buff += AbstractElementHTML.createHTMLElement(attr);
		attrDiv.setContent(buff);
		result += AbstractElementHTML.createHTMLElement(attrDiv);
		
		/*
		 * размер конфигурационного файла
		 */
		attr.setTag("span");
		attr.setClassName("c_label_element");
		attr.setContent("Размер файла : ");	
		buff = AbstractElementHTML.createHTMLElement(attr);
		
		attr.setClassName(null);
		attr.setContent(new File(Activity.SYSTEM_CONFIG).getAbsoluteFile().length() + " байт");
		buff += AbstractElementHTML.createHTMLElement(attr);
		attrDiv.setContent(buff);
		result += AbstractElementHTML.createHTMLElement(attrDiv);
		
		return result;
		
	}
	
	public static String formSession(){	
	
		AttributeHTMLElement attr = new AttributeHTMLElement("nonTag");
		String result = "", buff = "";
		AttributeHTMLElement attrDiv = new AttributeHTMLElement("div", null, "c_form_blok", null, null);
		/*
		 * Время последнего обновления списка сессий
		 */
		attr.setTag("span");
		attr.setClassName("c_label_element");
		attr.setContent("Последнее обновление сессий : ");	
		buff = AbstractElementHTML.createHTMLElement(attr);
		
		attr.setClassName(null);
		attr.setContent(new Date(Long.valueOf(Prop.getPropValue(Activity.SYSTEM_CONFIG, "system_TIME_LAST_REFRESH"))).toLocaleString());
		buff += AbstractElementHTML.createHTMLElement(attr);
		attrDiv.setContent(buff);
		result += AbstractElementHTML.createHTMLElement(attrDiv);
		
		/*
		 * Время активного состояния сессии
		 */
		attr.setClassName("c_label_element");
		attr.setContent("Время активного состояния сессии  в секундах ");
		buff = AbstractElementHTML.createHTMLElement(attr);
		
		attr.setTag("input");
		String idElement = "system_TIME_ACTIVE_SESSION";
		attr.setId(idElement);
		attr.setClassName(null);
		attr.addAttr("name", idElement);
		attr.addAttr("type", "text");
		attr.addAttr("size", "5");
		attr.addAttr("value", Prop.getPropValue(Activity.SYSTEM_CONFIG, idElement));
		attr.setContent(null);
		buff += AbstractElementHTML.createHTMLElement(attr);

		/*
		 * Отрисовка кнопки сохранить
		 */
		attr.setId(null);		
		attr.clearAttr();
		attr.addAttr("onClick", "saveTools('" + idElement + "'), removeMessage()");
		attr.setContent("Сохранить");
		attr.setStyle("width:90px;height:100%");
		buff += Button.getInstance().getHTMLObject(attr);
		
		attrDiv.setContent(buff);
		result += AbstractElementHTML.createHTMLElement(attrDiv);
		
		/*
		 * Количество открытых сессий
		 */
		try{
			attr.setTag("span");
			attr.setClassName("c_label_element");
			attr.setContent("Количество открытых сессий : ");
			buff = AbstractElementHTML.createHTMLElement(attr);
			buff += BufferProcess.getMap().size();
			attrDiv.setContent(buff);
		}catch(NullPointerException ex){
			Logger.addLog("Error. Не удается определить количество открытых сессий.");
			attr.setContent("Не удается определить количество открытых сессий.");
		}
		result += AbstractElementHTML.createHTMLElement(attrDiv);
		
		return result;
	}
	public static String formInfoUsers(){
	
		AttributeHTMLElement attr = new AttributeHTMLElement("nonTag");
		String result = "", buff = "";
		AttributeHTMLElement attrDiv = new AttributeHTMLElement("div", null, "c_form_blok", null, null);
		
		attr.setTag("span");
		attr.setClassName("c_label_element");
		ProcessForeground.map.put("table", "users");
		ProcessForeground.map.put("typeEvent", TypeEvents.DEFAULT);
		try {
			ResultSet resultSet = ResultSQL.getResultSet(ProcessForeground.map);
			resultSet.first();			
			attr.setContent("Количество пользователей системы : ");
			buff += AbstractElementHTML.createHTMLElement(attr);
			buff += + resultSet.getInt(1);
			attrDiv.setContent(buff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += AbstractElementHTML.createHTMLElement(attrDiv); 
		/*
		 * Количество пользователей онлайн
		 */
		try{
			attr.setContent("Количество пользователей online : ");
			buff = AbstractElementHTML.createHTMLElement(attr);
			buff += BufferProcess.getMap().size();
			attrDiv.setContent(buff);
		}catch(NullPointerException ex){
			Logger.addLog("Error. Не удается определить количество пользоватеей системы.");
			attr.setContent("Не удается определить количество пользоватеей системы.");
		}
		result += AbstractElementHTML.createHTMLElement(attrDiv);
		
		try {
			ProcessForeground.map.put("table", "profileAccess");
			ProcessForeground.map.put("valueDefault", "profile_access");
			ProcessForeground.map.put("profile_access", "1");
			ResultSet resultSet = ResultSQL.getResultSet(ProcessForeground.map);
			ProcessForeground.map.remove("valueDefault");
			resultSet.first();			
			attr.setContent("Количество пользователей имеющих доступ : ");
			buff = AbstractElementHTML.createHTMLElement(attr);
			buff += resultSet.getInt(1);
			attrDiv.setContent(buff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result += AbstractElementHTML.createHTMLElement(attrDiv);
		
		return result;
	}
	
	/**
	 * Выгрузка услуг из Рапиды
	 * @return
	 */
	public static String formUnloadFromRapida(){
		
		String buff = "";		
		AttributeHTMLElement attr = new AttributeHTMLElement("nonTag");
		buff += ViewButton.button_unLoadRapida();
		buff += ViewButton.button_refreshUnLoadRapida();

		attr.setTag("p");
		attr.setStyle("  position: absolute; margin-left: 25%;");
		attr.setContent("Лог процесса");
		buff += AbstractElementHTML.createHTMLElement(attr);
		
		attr.setTag("textarea");
		attr.setId("id_unloadLog");
		attr.setStyle("position: absolute; width: 22%; height: 250px; margin: 1.5% 0.5%;");
		attr.setContent(null);
		buff += AbstractElementHTML.createHTMLElement(attr);
		
		attr.setTag("div");
		attr.setId(null);
		attr.setStyle(null);
		attr.setContent(buff);

		return AbstractElementHTML.createHTMLElement(attr);
		
	}
	
	private static String checked(String value){
		return value.equals("true") ? "checked" : "";
	}
	
	public static void main(String[] args) {
		System.out.println(ViewForms.getInstance().formInfoUsers());
	}
	public static String toolsTerminals() {
		
		AttributeHTMLElement attr = new AttributeHTMLElement();
		/*
		 * Вызов функции реализованной на JavaScript
		 */
		attr.setId("addBondForTerminal"); 
		attr.addAttr("onClick", "addBondForTerminal(), removeMessage()");
		attr.setContent("Добаввить на терминал все активные услуги");
		return Button.getInstance().getHTMLObject(attr);		
	}
	public static String log() {
		
		AttributeHTMLElement attr = new AttributeHTMLElement("textarea");
		
		attr.setId("id_Log");
		attr.setStyle("position: inherit; width: 98%; height: 110px;");
		attr.setContent(null);
		
		return AbstractElementHTML.createHTMLElement(attr);
	}
	
	
}
