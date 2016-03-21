package com.oep.elements_view.page;

import java.io.PrintWriter;
import java.sql.ResultSet;

import com.oep.db.sql.ResultSQL;
import com.oep.dictionary.ListElements;
import com.oep.elements_interface.Page;
import com.oep.elements_tree.ViewTree;
import com.oep.elements_tree.ViewTree;
import com.oep.elements_view.block.ViewBlocks;
import com.oep.elements_view.form.ViewForms;
import com.oep.elements_view.navigation.Navigation;
import com.oep.elements_view.other.VisibleTools;
import com.oep.elements_view.table.ViewTable;
import com.oep.html.AbstractElementHTML;
import com.oep.html.AttributeHTMLElement;
import com.oep.process.ProcessForeground;
import com.oep.process.data.BufferProcess;
import com.oep.servlet.Activity;

public class ViewPage implements Page{
 
	private String sessionId;
	private static String contentPage;
	private AttributeHTMLElement attrHTML = null;

	private static ViewPage instance;
	
	private ViewPage(){}
	
	public static ViewPage getInstance(){
		if(instance == null)
			instance = new ViewPage();
		return instance;
	}
	
	public void getPage(PrintWriter pw, String sessionId){		
		this.sessionId = sessionId;
		create();
		pw.write(contentPage);		
	}
	
	public String getPage(String sessionId){
		this.sessionId = sessionId;
		create();
		return contentPage;
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.oep.elements_interface.Page#header()
	 * шапка страницы
	 */
	@Override
	public String header() {
	  String contentHeader = "";
      contentHeader += "<div class=\"b_header\">";
      /*
       * логотип
       */
      contentHeader += "<div id='h_logo'>" +
      		     			"<img id='logo' src=\"/CSFT/image/logo.png\"/ mouseover=\"mouseOver(this.id, 'logo')\" >" +
      		     	   "</div>";
      /*
       * информация о пользователе
       */      
      ProcessForeground process = BufferProcess.getProcess(sessionId);
      contentHeader += "<div id=\"h_entryPerson\">" +
	       				  "<h3>Вы работате под учеткой: " + 
	       					  process.getInfoWorkingPerson().get("description_role") + " - " + 
	       					  process.getInfoWorkingPerson().get("description_user") +
	       				  "</h3>" +
	       			  "</div>";      
      /*
       * форма выхода
       */
      contentHeader += "<form id=\"FormaLogOut\" name=\"FormaLogOut\" method='post' action='/CSFT/constructor'>" +
	     			   		"<div id='h_exit'>" +
	     			   			"<input id='exit' type='submit' mouseover=\"mouseOver(this.id, 'exit')\"  value=\"Выход\">" + //onclick=FormaLogOut.submit()\"
	     			   		"</div>" +
	     			   		"<input type=\"hidden\" id=\"typeEventLogOut\" name=\"typeEvent\" value=\"logout\">" +
	     			   	"</form>";
      /*
       * информационный блок
       */
      contentHeader += "<div id='h_info'>" +
      						"<a href=\"javascript:;\" onclick=\"openInfo()\">Справка</a>" +
      				  "</div>" +
      				"</div>"; //class=\"b_header\">
      return contentHeader;
	}

	/**
	 * (non-Javadoc)
	 * @see com.oep.elements_interface.Page#content()
	 * контент страницы
	 */
	@Override
	public String content() {
		  String contentContent = ""; 
	      Object elementContent = ProcessForeground.map.get("elementContent");
	      
	      contentContent += "<div id=\"id_content\" class=\"b_content\">";
	      contentContent += "<input id=\"id_process\" type=\"image\" " +
	      						   "src=\"/CSFT/image/progress.gif\"" +
	      						   "style=\"visibility: hidden; position: fixed; margin: 10% 35% auto;\"/>";
	      
	      contentContent += Navigation.getHorizontalMenu();
		  contentContent += Navigation.getVerticalMenu();  
	      
	      contentContent += "<div id=\"c_context\"class=\"c_context\">";

	      ResultSet resultSet = null;
	      
	      if(!ProcessForeground.map.get("elementContent").equals("blocks"))
	    	  resultSet =  ResultSQL.getResultSet(ProcessForeground.map);
	      /*
	       *  формирвоание объекта отображения
	       */
	      if(elementContent != null)
	      switch (ListElements.getValue(elementContent.toString())) {
	        case TREE:{
	          contentContent += ViewTree.getTree(resultSet, sessionId);
			  break;
	        }
	        case TABLE :{
		      contentContent += ViewTable.getInstance().getTable(resultSet);
	          break;
	        }
	        case FORM : {
	        	contentContent += ViewForms.getInstance().getForm(resultSet);
	            break;
	        }
	        case BLOCKS : {
	        	contentContent += ViewBlocks.getInstance().getBlocks(sessionId);
	        	break;
	        }
	        default:
			break;
		}
	      contentContent += "</div>" + //  c_context
    					"</div>"; // id_content
	      return contentContent;
	}

	/**
	 * (non-Javadoc)
	 * @see com.oep.elements_interface.Page#footer()
	 * футтер
	 */
	@Override
	public String footer() {

		attrHTML = new AttributeHTMLElement("div");
		attrHTML.setId("f_info");
		attrHTML.setContent("Copyright  2014-2016 © SКИУТ \r\n version " + Activity.VERSION);
		
		attrHTML.setContent(AbstractElementHTML.createHTMLElement(attrHTML));
		attrHTML.setId(null);
		attrHTML.setClassName("b_footer");
		
		return AbstractElementHTML.createHTMLElement(attrHTML); 		
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.oep.elements_interface.Page#create()
	 * главный метод класса
	 */
	@Override
	public void create(){		
		
		ProcessForeground.map.put("sessionId", sessionId);		
		contentPage = "<!DOCTYPE html>";
		contentPage += "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
		contentPage += "<head>";
		/*
		 * МЕТА Данные
		 */
		attrHTML = new AttributeHTMLElement("meta");
		
		attrHTML.addAttr("http-equiv", "Content-Type");
		attrHTML.addAttr("content", "text/html; charset=utf-8");
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);
		
		attrHTML.addAttr("http-equiv", "Content-Style-Type");
		attrHTML.addAttr("content", "text/css");
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);
		
		attrHTML.clearAttr();
		
		/*
		 * СТИЛИ
		 */
		attrHTML.setTag("link");
		attrHTML.addAttr("rel", "stylesheet");
		attrHTML.addAttr("type", "text/css");
		
		attrHTML.addAttr("href", "/CSFT/style/body.css");
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);
		
		attrHTML.addAttr("href", "/CSFT/style/header.css");
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);
		
		attrHTML.addAttr("href", "/CSFT/style/content.css");
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);

		attrHTML.addAttr("href", "/CSFT/style/footer.css");
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);
		
		attrHTML.addAttr("href", "/CSFT/style/style.css");
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);
		
		attrHTML.addAttr("href", "/CSFT/style/styleTree.css");
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);
		
		attrHTML.addAttr("href", "/CSFT/style/verticalMenu.css");
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);
		
		attrHTML.clearAttr();
		
		/*
		 * СКРИПТЫ
		 * Остальный скрипты подгружаются через js.js
		 */
		attrHTML.setTag("script");
		attrHTML.addAttr("type", "text/javascript");
		attrHTML.addAttr("charset", "UTF-8");
		attrHTML.addAttr("src", "/CSFT/script/js.js");		
		contentPage += AbstractElementHTML.createHTMLElement(attrHTML);
		
		
		contentPage += "</head>"; 

		contentPage += "<body class=\"b_body\" onload='onLoad()'>" +
				        "<div id=\"id_main_div\" class=\"b_main_div\">" +
							header() +
							content() +
							footer() +						
						"</div>" + // id=\"id_main_div\"
				   "</body>" + // class=\"b_body\"
				 "</html>";
	}

}
