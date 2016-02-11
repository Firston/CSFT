package com.oep.elements_view.other;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class VisibleTools {

	private static VisibleTools instance;
	
	public static VisibleTools getInstance(){
		if(instance  == null)
			instance = new VisibleTools();
		return instance;
	}
	
	public void getTools(ResultSet resultSet, PrintWriter pw){
		
		pw.write("<div id=\"checkT\" style=\"display:inline\">" +
				   "<p style=\"display:inline;\">Видимость столбцов</p>" +
				   "<img id=\"idT\" src=\"/CSFT/image/arrow2.png\" href=\"javascript:;\" onmousedown=\"slidedown('mydiv2',this.id);\"/>" +
				 "</div>" +
				 "<div id='mydiv2' style=\"display:none; overflow:hidden; height:95px;\" class=\"c_option_memu\">");
				
		if(resultSet != null){
			try{			
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();
				for(int i = 1; i <= columnCount; i++){
				
					pw.write("<div class='c_tools_div'>" +
							   "<input id='" + metaData.getColumnName(i) + "' " +
							   		  "class='c_tools_input' " +
							   		  "type='checkbox'" +
							   		  "onchange=change(this,'tools') " +
							   		  "value='true'" +
							   		  "checked='checked' >" +
							   		  metaData.getColumnName(i).split(":")[1] + 
							   "</input>" +
							 "</div>");						
				}
			}catch(SQLException e){
			  e.printStackTrace();
			}
		}
		pw.write("</div>");// close id='mydiv2' 
	}  
	
	public String getTools(ResultSet resultSet){
		String contentTools = "";
		contentTools += "<div id=\"checkT\" style=\"display:inline\">" +
				   			"<p style=\"display:inline;\">Видимость столбцов</p>" +
				   			"<img id=\"idT\" src=\"/CSFT/image/arrow2.png\" href=\"javascript:;\" onmousedown=\"slidedown('mydiv2',this.id);\"/>" +
				   		"</div>" +
				   		"<div id='mydiv2' style=\"display:none; overflow:hidden; height:95px;\" class=\"c_option_memu\">";
				
		if(resultSet != null){
			try{			
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();
				for(int i = 1; i <= columnCount; i++){
				
					contentTools += "<div class='c_tools_div'>" +
							   			"<input id='" + metaData.getColumnName(i) + "' " +
							   				"class='c_tools_input' " +
							   				"type='checkbox'" +
							   				"onchange=change(this,'tools') " +
							   				"value='true'" +
							   				"checked='checked' >" +
							   				metaData.getColumnName(i).split(":")[1] + 
							   			"</input>" +
							   		"</div>";						
				}
			}catch(SQLException e){
			  e.printStackTrace();
			}
		}
		contentTools += "</div>";// close id='mydiv2'
		return contentTools;
	}
}
