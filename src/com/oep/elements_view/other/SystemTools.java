package com.oep.elements_view.other;

import java.io.PrintWriter;

import com.oep.elements_interface.Tools;

/*
 * УСТАРЕВШАЯ РЕАЛИЗАЦИЯ СИСТЕМНЫХ НАСТРОЕК
 * ВАЖНО УДАЛИТЬ КЛАСС 15.04.05
 */
public class SystemTools implements Tools{

	private PrintWriter pw;
	
	public SystemTools(PrintWriter pw){
		
		if(this.pw == null)
		  this.pw = new PrintWriter(pw);
	}
	
	/*
	 * Устаревшая реализация настроек
	 */
	public PrintWriter getSystemTools(){
		return null;
	}

	@Override
	public void createContextTools() {
		pw.write("<div id=\"checkSystemTools\" style=\"display:inline\">" +
				   "<p style=\"display:inline;\">Системные настройки</p>" +
				   "<img id=\"idSystemTools\" " +
				   		"src=\"/CSFT/image/arrow2.png\" " +
				   		"href=\"javascript:;\" " +
				   		"onmousedown=\"slidedown('mydiv3',this.id);\"/>" +
				 "</div>" +
				 "<div id='mydiv3' style=\"display:none; overflow:hidden; height:95px;\" class=\"c_option_memu\">" +
				   "Путь к файлам логов : <input id=\"system_pathLog\" type=\"text\" size=\"25\"></input></br>" +
				   "Включить логирование <input id=\"system_isLog\" type=\"checkbox\">" +
				   "<input id=\"typeEvent\" type=\"hidden\" value=\"save_properties\">" +
				   "<img id=\"trSave\" " +
				   		"src=\"/CSFT/image/knopka.png\" " +
				   		"onclick=\"save()\" " +
				   		"onmouseover=\"mouseOver(this.id, null)\" " +
				   		"onmouseout=\"mouseOut(this.id)\"" +
				   		"style=\"float:right\">" +
				 "</div>");		
	}  

}
