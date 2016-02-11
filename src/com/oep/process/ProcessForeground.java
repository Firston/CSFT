package com.oep.process;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.oep.db.sql.ResultSQL;
import com.oep.db.sql.SQL;
import com.oep.db.sql.SQLFactory;
import com.oep.dictionary.TypeEvents;
import com.oep.elements_view.page.ViewPage;
import com.oep.utils.Logger;

public class ProcessForeground extends ResultSQL implements Foreground{	
	
	public SQL sql;
	/**
	 *  Выходной поток
	 */
	private PrintWriter pw;
	/**
	 * массив информации о пользователе открывшем сессию
	 */
	private Map<String, Object> infoWorkingPerson;
	/**
	 * массив параметров из HttpServletRequest request
	 */
	public static Map<String, Object> map = null;
	/**
	 * Контент обработанного запроса, введен для 
	 * отслеживания приема и обработки повторных запросов от клиента
	 */
	private String contentDataRequest = null;	
	/**
	 * Дата последнего изменения
	 */
	private Date lastModified;
	
	public ProcessForeground(PrintWriter output){
		pw = new PrintWriter(output);
		map = new HashMap<String, Object>();
	}
	
	
	/*public ProcessForeground(){
		map = new TreeMap<String, Object>();
	}*/

	/**
	 * формирвоание выходного потока
	 * 
	 * 
	 *  НА БУДУЩЕЕ! - убрать return.
	 *  Сделать отдельный вызов для получения выходного потока.
	 *  ТЕСТИРОВНИЕ
	 * @return
	 */
	@SuppressWarnings("finally")
	public void run(String sessionId, Object...objects){
		this.setLastModified(new Date());
		typeEvents : 
		switch(TypeEvents.getValue(map.get("typeEvent").toString())){		
		  case SELECT : {
			//Activity.logger.out("SELECT", map);
			sql = SQLFactory.getInstance().create(map.get("typeEvent").toString());			
			//new ViewPage(pw, sessionId).create();
			pw.write(ViewPage.getInstance().getPage(sessionId));
			break typeEvents;
		  }
		  case INSERT : {
			  /*
			   * Устаревшая реализация. На этапе оптимизации.
			   * Оставлена для отслеживания добавления записей по старой логике
			   */
			  Logger.out("INSERT CRITICAL", map) ;
			if(map.get("elementContent").toString().equals("tree")){
				try {
					throw new Exception("Critical ERROR INSERT- Функционал реализован по старой логике.");
				} catch (Exception e) {
					Logger.addLog("Critical ERROR INSERT - Функционал реализован по старой логике. Обратитесь к разработчику \r\n " + e.getMessage() );
					e.printStackTrace();					
				}finally{
					Logger.out("INSERT AROND FOPOST", map);
					sql = SQLFactory.getInstance().create(map.get("typeEvent").toString());
					getResultInsert(map);
					map.put("typeEvent", "select");
					sql = SQLFactory.getInstance().create(map.get("typeEvent").toString());
					if(map.get("table").toString().equals("subServices"))
			    	  map.put("table", "services");
					if(map.get("table").toString().equals("contentDictionaries"))
				    	  map.put("table", "dictionaries");
					//new ViewPage(pw, sessionId).create();
					pw.write(ViewPage.getInstance().getPage(sessionId));
					break typeEvents;		
				}
			}
		  }
		  case ADD : {
			//Logger.out("ADD", map);
		    sql = SQLFactory.getInstance().create("select");
		    //new ViewPage(pw, sessionId).create();
		    pw.write(ViewPage.getInstance().getPage(sessionId));
		    break typeEvents;
		  }
		  case EDIT : {
			//Logger.out("EDIT", map);
		    sql = SQLFactory.getInstance().create("select");
		    //new ViewPage(pw, sessionId).create();
		    pw.write(ViewPage.getInstance().getPage(sessionId));
		    break typeEvents;  
		  }
		  case SHOW : {
			//Logger.out("SHOW", map);
		    sql = SQLFactory.getInstance().create("select");
		    //new ViewPage(pw, sessionId).create();
		    pw.write(ViewPage.getInstance().getPage(sessionId));
			map.put("isPre", true);
			map.put("preId", map.get("id"));
		    break typeEvents;  
		  }
		  case UPDATE : {
			  
			  /*
			   * Устаревшая реализация. На этапе оптимизации.
			   * Оставлена для отслеживания добавления записей по старой логике
			   */
			  Logger.out("UPDATE CRITICAL", map) ;
			if(map.get("elementContent").toString().equals("tree")){
				try {
					throw new Exception("Critical ERROR UPDATE - Функционал реализован по старой логике.");
				} catch (Exception e) {
					Logger.addLog("Critical ERROR UPDATE - Функционал реализован по старой логике. Обратитесь к разработчику \r\n " + e.getMessage() );
					e.printStackTrace();					
				} finally{
					Logger.out("UPDATE AROUND DOPOST", map);
				    sql = SQLFactory.getInstance().create("update"); 
				    ResultSQL.getResultUpdate(map);
				    if(map.get("table").toString().equals("subServices"))
				      map.put("table", "services");
				    map.put("typeEvent", "select");
				    sql = SQLFactory.getInstance().create("select");
				    //new ViewPage(pw, sessionId).create();
				    pw.write(ViewPage.getInstance().getPage(sessionId));
				    break typeEvents;
				}
			}

		  }
		  case SAVE_PROPERTIES : 
		  case UNLOAD : {
			  pw.write(ViewPage.getInstance().getPage(sessionId));
			break typeEvents;
		  }
		  case NONE : break typeEvents;
		}
		//return pw;
	}

	public  Map<String, Object> getInfoWorkingPerson() {
		if(infoWorkingPerson == null)
		  infoWorkingPerson = new TreeMap<String, Object>();
		return infoWorkingPerson;
	}
	public String getContentDataRequest() {
		return contentDataRequest;
	}
	public void setContentDataRequest(String contentDataRequest) {
		this.contentDataRequest = contentDataRequest;
	}
	
	public PrintWriter getPw() {
		return pw;
	}
	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * ПРОТОТИП
	 */
	@Override
	public void run(Object object, Object... objects) {
		run((String)object);	
	}
}
