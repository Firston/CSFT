package com.oep.elements_view.other;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.oep.html.AttributeHTMLElement;
import com.oep.process.ProcessForeground;

public class ViewSelect {

	private static String contentSelect;
	private static ViewSelect instance;
	private AttributeHTMLElement attributeHTMLElement = null;
		
	public static ViewSelect getInstance(){
		if(instance == null)
		  instance = new ViewSelect();
		return instance;
	}
	
	private void create(ResultSet resultSet, boolean isValue){
		contentSelect = "";
		attributeHTMLElement = new AttributeHTMLElement("option", 
				   null, 
				   null, 
				   null, 
				   null);
		contentSelect += com.oep.html.AbstractElementHTML.createHTMLElement(attributeHTMLElement);
		try {		
		  boolean b = resultSet.first();
		  while(b){
			if(isValue)attributeHTMLElement.addAttr("value", resultSet.getString(2)); 
			  else attributeHTMLElement.addAttr("value", resultSet.getString(1));
			attributeHTMLElement.addAttr("label", resultSet.getString(2));
			contentSelect += com.oep.html.AbstractElementHTML.createHTMLElement(attributeHTMLElement);
			attributeHTMLElement.clearAttr();
			b = resultSet.next();
		  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		attributeHTMLElement.setTag("select");
		attributeHTMLElement.setClassName("c_selectBlock");
		attributeHTMLElement.setId("id_" + ProcessForeground.map.get("table"));
		attributeHTMLElement.addAttr("name", "id_" + ProcessForeground.map.get("table")); //УБРАТЬ?
		/*
		 * Динамически просталять второй параметр метода change
		 */
		attributeHTMLElement.addAttr("onchange", "change(this.id, 'filter')");
		attributeHTMLElement.setContent(contentSelect);
		contentSelect = com.oep.html.AbstractElementHTML.createHTMLElement(attributeHTMLElement);
	}
	
	/**
	 * 
	 * @param resultSet - результирующийй набор данных
	 * @param isValue - определяет что передавать в качестве значения :
	 * 				true - значение контента value равно textContent 
	 * 				false -  значение идентификатора value не равно textContent
	 * @return
	 */
	public String getSelect(ResultSet resultSet, boolean isValue){
		create(resultSet, isValue);
		return contentSelect;
	}
}
