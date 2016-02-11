package com.oep.elements_view.table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.oep.elements_interface.TableTools;
import com.oep.elements_view.other.AbstractEvents;
import com.oep.html.AbstractElementHTML;
import com.oep.html.AttributeHTMLElement;
import com.oep.process.ProcessForeground;
import com.oep.utils.Logger;

public abstract class AbstractTableTools extends AbstractEvents implements TableTools{
	
	@Override
	public String createFilterForTable(ResultSet resultSet){

		String filterForTable = "";
		try{
			ResultSetMetaData metaData = resultSet.getMetaData();
			int countColumn = metaData.getColumnCount();
			
			String tableName = (String) ProcessForeground.map.get("table");
			/*
			 * TDs и TR
			 */
			AttributeHTMLElement  attr = new AttributeHTMLElement("td");
			
			/*
			 * INPUT
			 */
			AttributeHTMLElement  input = new AttributeHTMLElement("input");
			input.addAttr("type", "text");
			
			/*
			 * Чекбокс строки
			 */
			attr.setClassName("c_table_ch");
			filterForTable += AbstractElementHTML.createHTMLElement(attr);
			attr.setClassName("c_table_th");

			String[] atr;
			for(int i = 1; i <= countColumn; i++){
				atr = metaData.getColumnLabel(i).split(":");
				//input.addAttr("placeholder", metaData.getColumnName(i).split(":")[1]);
				input.addAttr("id", "filter_" + atr[0]);
				input.addAttr("placeholder", atr[1]);
				input.addAttr("oninput", "hidenRow(this," + i + ",'" + tableName + "')");
				attr.setContent(AbstractElementHTML.createHTMLElement(input));
				
				filterForTable += AbstractElementHTML.createHTMLElement(attr);
			}
			attr.setTag("tr");
			attr.setClassName(null);
			attr.setContent(filterForTable);
			filterForTable = AbstractElementHTML.createHTMLElement(attr);
	
		}catch (SQLException e) {
			Logger.addLog("Ошибка создания фильтров таблицы \r\n" + e.getMessage());
		}
		return filterForTable;
	}
	
	@Override
	public String createHrefForPageOfTable() {
		String hrefForPage = "";
		AttributeHTMLElement div = new AttributeHTMLElement("div");
		div.setClassName("c_page");
		div.addAttr("onclick", "execute_pre_page('" + ProcessForeground.map.get("table") + "')");
		div.setContent("&lt;Пред. ");
		hrefForPage += AbstractElementHTML.createHTMLElement(div);
		div.clearAttr();
		
		AttributeHTMLElement input = new AttributeHTMLElement("input");
		input.setId("numberPage_" + ProcessForeground.map.get("table"));
		input.setStyle("border: none; background: #ffffff;");
		input.addAttr("size", "1");
		input.addAttr("disabled", "disabled");
		input.addAttr("value", "1");
		
		div.setContent("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Станица&nbsp;&nbsp;" + AbstractElementHTML.createHTMLElement(input));
		hrefForPage += AbstractElementHTML.createHTMLElement(div);
		
		div.clearAttr();
		div.setContent("След.&gt;");
		div.addAttr("onclick", "execute_next_page('" + ProcessForeground.map.get("table") + "')");
		hrefForPage += AbstractElementHTML.createHTMLElement(div);
		
		div.clearAttr();
		div.setClassName("c_div_page");
		div.setContent(hrefForPage);
		return AbstractElementHTML.createHTMLElement(div); 
	}

}
