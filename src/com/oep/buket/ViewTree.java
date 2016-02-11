package com.oep.buket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import com.oep.db.sql.ResultSQL;
import com.oep.dictionary.ListTables;
import com.oep.process.ProcessForeground;
public class ViewTree{
	
	private String sessionId;
	private static ViewTree instance;
	
	public static ViewTree getInstance(){
		if(instance == null)
			instance = new ViewTree();
		return instance;
	}
	
	
	public synchronized String createTree(ResultSet resultSet, /*PrintWriter pw,*/ String sessionId){

		this.sessionId = sessionId;
		String contentTree = "";
		String table = (String)ProcessForeground.map.get("table");

		contentTree += "<form id=\"formTree_" + (table.equals("services") ? "subServices" : table) + "\" " +
		   					 "name=\"formTree_" + (table.equals("services") ? "subServices" : table) + "\" " +
		   					 "method='post' " +
		   					 "action='/CSFT/constructor'>" +
		   				"<font ><h2>" + ListTables.getDescription(ProcessForeground.map.get("table").toString()) + "</h2></font>" +
		   				"<div style=\"margin: 5% 5% auto;\" onclick=\"tree_toggle(arguments[0])\">";
		if(resultSet != null)
		  contentTree += newContainer(true, resultSet);		

		contentTree += "</div>" +
		 					"<input type=\"hidden\" id=\"typeEventInTree\" name=\"typeEvent\" value=\"add\">" +
		 					"<input type=\"hidden\" id=\"getFormInTree\" name=\"getForm\" value=\"\">" +
		 					"<input type=\"hidden\" id=\"tableInTree\" name=\"table\" value=\"\">" +
		 					"<input type=\"hidden\" id=\"elementContentInTree\" name=\"elementContent\" value=\"form\">" +
		 					"<input type=\"hidden\" id=\"idInTree\" name=\"id\" value=\"\">" +
		 			"</form>";
		return contentTree;
	}
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
	
	private String newContainer(boolean isRoot, ResultSet resultSet){
		String contentNewConteaner = "";
	  try{
		boolean b = resultSet.first();
		if(isRoot){
			contentNewConteaner += "<ul class=\"Container\">";
		  while(b){			 		  			 
		    contentNewConteaner += newTagLi(resultSet.getString(1), resultSet.getString(2), false);
			b = resultSet.next();
		  }
		  contentNewConteaner += "</ul>";
		}else{
		  contentNewConteaner += "<ul class=\"Container\">";
		  while(b){			 		  			 
		    contentNewConteaner += newTagLi(resultSet.getString(1), resultSet.getString(2), true);
			b = resultSet.next();
		  }
		  contentNewConteaner += "</ul>";
		}	
	  }catch (SQLException e) {
		e.printStackTrace();
	  }
	  return contentNewConteaner;
	}
}