package com.oep.elements_tree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;


import com.oep.db.sql.ResultSQL;
import com.oep.dictionary.ListTables;
import com.oep.elements_interface.Tree;
import com.oep.html.AbstractElementHTML;
import com.oep.html.AttributeHTMLElement;
import com.oep.process.ProcessForeground;

public abstract class AbstractTree extends AbstractElementHTML implements Tree{

	
	private String sessionId;
	private AttributeHTMLElement htmlObj;
	
	public static String contentTree;
	
	/**
	 * version 15.12.01 
	 * !!!!реализовано только по услугам!!!!
	 */
	@Override
	public void create(ResultSet resultSet, String sessionId) {
		
		this.sessionId = sessionId;

		String subContent = "<font><h2>" + ListTables.getDescription(ProcessForeground.map.get("table").toString()) + "</h2></font>";
		String table = (String)ProcessForeground.map.get("table");
		String formName = "formTree_" + (table.equals("services") ? "subServices" : table);
		
		htmlObj = new AttributeHTMLElement("div");
		htmlObj.setStyle("margin: 5% 5% auto;");
		htmlObj.addAttr("onclick", "tree_toggle(arguments[0])");		
		htmlObj.setContent(resultSet != null ? newContainer(true, resultSet) : "");		
		subContent += createHTMLElement(htmlObj);		
		
		htmlObj.clearAttr();
		htmlObj.setContent(null);
		htmlObj.setStyle(null);
		htmlObj.setTag("input");
		htmlObj.addAttr("type", "hidden");
		
		htmlObj.setId("typeEventInTree");
		htmlObj.addAttr("name", "typeEvent"); htmlObj.addAttr("value", "add");
		subContent += createHTMLElement(htmlObj);
		
		htmlObj.setId("elementContentInTree");
		htmlObj.addAttr("name", "elementContent"); htmlObj.addAttr("value", "form");
		subContent += createHTMLElement(htmlObj);
		
		htmlObj.removeAttr("value");
		htmlObj.setId("getFormInTree");
		htmlObj.addAttr("name", "getForm");
		subContent += createHTMLElement(htmlObj);
		
		htmlObj.setId("tableInTree");
		htmlObj.addAttr("name", "table");
		subContent += createHTMLElement(htmlObj);
		
		htmlObj.setId("idInTree");
		htmlObj.addAttr("name", "id");
		subContent += createHTMLElement(htmlObj);
		
		htmlObj = new AttributeHTMLElement("form");
		htmlObj.setId(formName);
		htmlObj.addAttr("name", formName);
		htmlObj.addAttr("method", "post");
		htmlObj.addAttr("action", "/CSFT/constructor");				
		
		htmlObj.setContent(subContent);
		
		contentTree = createHTMLElement(htmlObj);
	}
	
	@Override
	public String newContainer(boolean isRoot, ResultSet resultSet) {
		String contentNewConteaner = "";
		AttributeHTMLElement htmlObj = new AttributeHTMLElement("ul", null, "Container", null, null);
		  try{
			boolean b = resultSet.first();
			if(isRoot){
			  while(b){
				contentNewConteaner += newTagLi(resultSet.getString(1), resultSet.getString(2), false);				
				b = resultSet.next();
			  }
			}else{
			  while(b){
				  contentNewConteaner += newTagLi(resultSet.getString(1), resultSet.getString(2), true);				
				b = resultSet.next();
			  }
			}	
		  }catch (SQLException e) {
			e.printStackTrace();
		  }
		  htmlObj.setContent(contentNewConteaner);
		  return createHTMLElement(htmlObj);
	}
	
	/*
	 * version before 15.12.01
	 * рефакторинг не произведен. Причина прорабоать логику построения дерева для любых объектов
	 */
	private String newTagLi(String id, String name, boolean isLast){
		String contentNewTagLi = "";
		
		if(!isLast){
			contentNewTagLi += "<li class=\"Node IsRoot ExpandClosed isLast\">" +
					   	"<div class=\"Expand\"></div>" +
					   "<input id=\"services_" + id + "\" type=\"radio\" name=\"ch\" style=\"float: left;\" onchange=\"change(this.id, 'tree')\"/>" +
					   "<div class=\"Content\">" + name + "</div>";

						Map<String, Object> map = new TreeMap<String, Object>();
						map.put("id", id);
						map.put("table", "subServices");
						map.put("typeEvent", "dictionary");
						map.put("sessionId", sessionId);
			contentNewTagLi += newContainer(false, ResultSQL.getResultSet(map));
			contentNewTagLi += "</li>";
		}else{
			contentNewTagLi += "<li class=\"Node ExpandLeaf IsLast\">" +
		  			   "<div class=\"Expand\"></div>" +
		  			   "<input id=\"subServices_" + id + "\" name=\"ch\" type=\"radio\" style=\"float: left;\" onchange=\"change(this.id, 'tree')\"/>" +
		  			   "<div class=\"Content\">" +
		  			     "<a href=\"javascript:;\" name=\"a_subServices_" + id + "\" onclick=\"createFormShow(this, 'tree', 'subServices')\";\">" + name + "</a>" +
		  			   "</div>" +
		  			 "</li>";
		}
		return contentNewTagLi;
	}
	
}
