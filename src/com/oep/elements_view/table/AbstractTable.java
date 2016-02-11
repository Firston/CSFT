package com.oep.elements_view.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import com.oep.dictionary.ListTables;
import com.oep.dictionary.ListTypeSQLData;
import com.oep.elements_interface.Table;
//import com.oep.elements_view.other.AbstractEvents;
import com.oep.html.AbstractElementHTML;
import com.oep.html.AttributeHTMLElement;
import com.oep.process.ProcessForeground;
import com.oep.utils.Logger;
import com.oep.utils.Validate;

public abstract class AbstractTable extends AbstractTableTools implements Table{

	private int count;
	public static String contenttable;
	private Map<String, String> buffForTable = null;	
	private String buffData = "";;
	
	@Override
	public void create(ResultSet resultSet) {
		contenttable = "";
		buffForTable = new TreeMap<String, String>();
		if(resultSet != null){
		  try {
			count  = resultSet.getMetaData().getColumnCount();
			String tableName = ProcessForeground.map.get("table").toString();
			switch(ListTables.getValue(tableName)) {

			  case CONTENTSUBSERVICES :{  
			    ProcessForeground.map.put("system_field", "name_subService:Наименование");
			    break;
			  }
			  default : break;	  
			}				
			/*
			 * Вынести условие  в скрипты
			 */
			if(!ProcessForeground.map.get("elementContent").equals("blocks"))
			  contenttable += "<form id=\"formTable_" + tableName + "\" class=\"c_table_form\" name=\"formTable_" + tableName + "\" method='post' action='/CSFT/constructor'>" + 
				  "<div id='labelTable'>" +
					     		"<font><h2>" + ListTables.getDescription(tableName) + getOneValue() + "</h2></font>" +
					     			/*
					     			 * input содержит значение отображаемой таблицы и ИД родительской. Используется в function SAVE()
					     			 */
					     		"<input type=\'hidden\' value=\'" + tableName + "_" + ProcessForeground.map.get("id") + "\'>" +
				  "</div>";
			
			/*
			 * Временное решение
			 */
			//if(tableName.equals("contentSubServices")){
			//  ProcessForeground.map.put("system_field", "external_code_service:Код услуги у поставщика (w)");
			//  contenttable += "<font><h2>Код услуги у поставщика - " + getOneValue() + "</h2></font>";	
			//}
			//else
			contenttable += "<form id=\"formTable_" + tableName + "\" name=\"formTable_" + tableName + "\" method='post' action='/CSFT/constructor'>";
		
			/*
			 * формирование шапки таблицы 
			 */
		   contenttable += createHrefForPageOfTable();
		   contenttable += "<table id=\"table_" + tableName + "\" border=1  class=\"c_table c_table_header\">";
		   header(count, resultSet, tableName);
		   contenttable += createFilterForTable(resultSet);
		   writeBuffForTable(resultSet);
		   contenttable += "</table>";

		   /*
		    * формироавние тела и контента таблицы
		    */		    
		    AttributeHTMLElement attr = new AttributeHTMLElement();

		    attr.setTag("table");
		    attr.setId("table_body_" + tableName);
		    attr.setClassName("c_table");
		    attr.setStyle("margin: auto;");
		    attr.setContent(body(count, resultSet)); 
		    
		    /*
		     * Формирвоание оберточного тега
		     */
		    attr.setContent(AbstractElementHTML.createHTMLElement(attr));
		    attr.setTag("div");
		    attr.setClassName("c_table_body");
		    attr.setId(null);
		    attr.setStyle(null);
		    
		    contenttable += AbstractElementHTML.createHTMLElement(attr);
		    /*
		     * формирвоание скрытых полей	  
		     */
		    hiddenBlok();
		    contenttable += "</form>";
		  } catch (SQLException e) {
			e.printStackTrace();
		  }
		}else Logger.addLog("Error : resultSet is null");				
	}

	public void header(int numberColumn, ResultSet res, String tableName) {

		try {			
			contenttable += "<tr id='table_header_" + tableName /*ProcessForeground.map.get("table")*/ + "' botder=1 style='border-color: #000000'>";
			for(int i = 0; i <= numberColumn; i++){
				if(i == 0){
				 contenttable += "<td id='ch' style=\"width: 21px;\">" +
				 		  "</td>";		      	
				}else{
				  //String value = res.getMetaData().getColumnName(i).toString();
				  String value = res.getMetaData().getColumnLabel(i).toString();
				  if(value.contains(":")){
					String[] atr = value.split(":");				  
					contenttable += "<td id='" + atr[0] + "' class='c_table_th'>" +
							   atr[1] +
							 "</td>";			  
				  }else{
				    buffForTable.put(value, null);
				    count--;
				  }
				}
			} 
		    contenttable += "</tr>";
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String body(int numberColumn, ResultSet res) {
	
		String contenttable = "";
		String indexTRName = "tr",
			   indexTDName = "td",
		       classTDName = "",
		       classTRName = "";	
		int indTr = 0;
		try { 
		  boolean b = res.first();
		  String id;
		  //int testCount = 0;
		  int limitRow = 0;
		  while(b){
			  /*
			   * Отображение 10 записей
			   */
			if(limitRow < 10){
				try{
				  id = res.getString("id:№ (i)");
				}catch (SQLException e) {
				  id = res.getString("id:№ (w)");
				}
					indTr++;
					if(indTr % 2 != 0){
						classTDName = "c_table_td";
						classTRName = "c_table_tr";
					}else{
						classTDName = "c_table_td2";
						classTRName = "c_table_tr2";
					}
					contenttable += "<tr id='" + indexTRName + id + "' class='" + classTRName + "' onmouseover=\"mouseOver(this.id, 'tr')\" onmouseout=\"mouseOut(this.id, 'tr')\">";
					String table = ProcessForeground.map.get("table").toString();  
				    for(int i = 0; i <= numberColumn; i++){
				      if(i == 0){
						  contenttable += "<td id='ch_td_" + indTr + "'  class=\"c_table_ch\" style=\"width: 21px;\">" +  
						  		     "<input id=\"" + table + "_" + id + "\"  name=\"ch\" type=\"radio\" style=\"float: left;\" onchange=\"change(this.id, 'table')\"/>" +
						  		   "</td>";		      	
				      }else{
				    	contenttable += "<td id='" + indexTDName + "_" + indTr + "_" + i + "' class='" + classTDName  + "'>"; 
				    	String typeData = res.getMetaData().getColumnTypeName(i).toString();
				    	switch(ListTypeSQLData.getValue(typeData)){
				    	  case INT : {
				    		  contenttable += "<span>" +
				    		  		     res.getString(i) +
				    		  		   "</span>";		    		  
				    		  break;
				    	  }
				    	  case VARCHAR : {
						      contenttable += contentData(indexTDName + indTr + i, res.getString(i));	  
				    		  break;
				    	  }
				    	  case TINYINT : {
				    		  String status = "";
				    		  String value = res.getString(i);
				    		  if(value.equals("1")) status = "checked='checked'";
				    		  contenttable +="<span>" +
				    		  		     "<input type=\"checkbox\" " + status + " disabled value='" + value + "'/>" +
				    		  		   "</span>";
				    		  break;
				    	  }
				    	  default : {
				    		  Logger.addLog("Тип : " + typeData + " не определен в системе");
				    		  break;
				    	  }
				    	}
				    	contenttable += "</td>"; 
				      }
				    }
				    contenttable += "</tr>";
				    limitRow++;
			}else{
			  for(int i = 1; i <= numberColumn; i++)
				buffData += (i == numberColumn) ? (res.isLast() ? res.getString(i) : (res.getString(i) + "&" )): res.getString(i) + "|";				
			}
			//testCount++;
		    b = res.next();
		  }
		} catch (SQLException e) {
		  e.printStackTrace();
		}	
		return contenttable;
	}
	
	private void writeBuffForTable(ResultSet res){
		
	    try {
		  res.first();
		  if(buffForTable != null){
			for(String key : buffForTable.keySet()){
			   buffForTable.put(key, res.getString(key));}  
		  }
		  
		  buffForTable.put("typeEventTable", "add");
		  buffForTable.put("getFormTable", ProcessForeground.map.get("table").toString());
		  buffForTable.put("tableTable", ProcessForeground.map.get("table").toString());
		  buffForTable.put("elementContentTable", "form");
		  buffForTable.put("idTable", "");		 
		  
		} catch (SQLException e) {
		  e.printStackTrace();
		}
	}

	private void hiddenBlok(){
		
		for(String key : buffForTable.keySet())
		  contenttable += "<input type=\"hidden\" id=\"" + key + "\" name=\"" + key.replace("Table", "") + "\" value=\"" + buffForTable.get(key) + "\">";
	    contenttable += "<textarea id=\"buffData_" + ProcessForeground.map.get("table") + "\" style=\"visibility: hidden;\">" +  buffData + "</textarea>";
		buffData = "";
	}

	private String contentData(String name, String content){
		String contenttable = "";
		if(content != null){
			
			if((content.length() >= 20 && content.contains(" ")) || (content.length() > 10 && !content.contains(" "))){
			  contenttable += "<span>" +			
					"<textarea name='" + name + "' readonly class=\"c_table_textarea\" ondblclick=\"mouseDoubleClick(this)\">" 
						+ Validate.formatData(content) + 
					"</textarea>" +
				  "</span>";
			}else{
				contenttable += "<span>" + 
						  Validate.formatData(content) + 
					    "</span>";  
			}
		}
		return contenttable;
	}
}
