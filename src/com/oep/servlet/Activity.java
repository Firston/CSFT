package com.oep.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oep.db.sql.SQLFactory;
import com.oep.dictionary.ListBackgoundProcess;
import com.oep.dictionary.TypeEvents;
import com.oep.elements_view.other.SaveProperties;
import com.oep.error.Error;
import com.oep.process.ProcessBackground;
import com.oep.process.ProcessForeground;
import com.oep.process.background.Resource;
import com.oep.process.data.BufferProcess;
import com.oep.utils.Encoding;
import com.oep.utils.Logger;
import com.oep.utils.SpecialSumbol;
import com.oep.utils.Validate;

/**
 * Servlet implementation class Activity
 */
public class Activity extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Версия - дата последних изменений в формете yy.MM.dd
	 * 
	 * ВАЖНО! Проект очищен от закомментированного кода старых реализаций
	 */
	public static final String VERSION ="16.02.11";
	/**
	 * Системный конфигурационный файл, создается автоматически при старте сервлдета, если отсутствует
	 */
	public static final String SYSTEM_CONFIG = "systemConfig.properties";	
	
	/**
	 * Замена buffMap используется в doPost и doGet для приема запросов
	 */
	private Map<String, Object> inputData = null;

	/**
	 * Массив процессов. Наполняется в зависимости от количества открытых сессий
	 */
	public static BufferProcess bufferProcess = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Activity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		ProcessBackground.getInstance().run(ListBackgoundProcess.INITIALIZATION, config);
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		return null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		/*
		 * стартовое время обраотки запроса
		 */
		long start = new Date().getTime();
		
		request.setCharacterEncoding(Encoding.UTF8);
		response.setCharacterEncoding(Encoding.UTF8);
		response.setContentType("text/html; charset=" + Encoding.UTF8);
		
		String result = SpecialSumbol.breakdownNone, 
			   contentDataRequest = request.getQueryString();
		
		if(contentDataRequest != null && BufferProcess.getMap().containsKey(request.getSession().getId())){
			HttpSession	session = request.getSession(false);
			ProcessForeground process = BufferProcess.getProcess(session.getId());
			if(!Validate.statusCompliteRequest(contentDataRequest, process, true).equals(SpecialSumbol.breakdownNone)){
				inputData = new HashMap<String, Object>(); 
				inputData.put("sessionId", session.getId());
				inputData.putAll(getMapParamsOfRequest(contentDataRequest));
				String ch = SpecialSumbol.breakdownNone;				
				if(inputData != null && inputData.containsKey("typeEvent")){
					switch (TypeEvents.getValue(inputData.get("typeEvent").toString())) {
					case SAVE_PROPERTIES : {
						SaveProperties.getiInstance().save(inputData, process);
						break;
					}
					case DICTIONARY:{
						process.sql = SQLFactory.getQueryObject(TypeEvents.SELECT);
						ResultSet resultSet   = ProcessForeground.getResultSet(inputData);
						try{
						  boolean b = resultSet.first();
						  /*
						   * Релизовать позже
						   * result += "data:";
						   */
 						  while(b){
						    result += ch + resultSet.getString(1) + "=" + resultSet.getString(2);						  
						    ch = SpecialSumbol.breakdownLine(resultSet.isLast());
						    b = resultSet.next();			  			  
						  }
						}catch(SQLException e){
						  e.printStackTrace();
						}
						break;
					}
					case DELETE : {
					  /*
					   * Реализовать позже
					   * result +="info:";
					   */
						process.sql = SQLFactory.getQueryObject(TypeEvents.DELETE);
						  result = "isDelete=" + ProcessForeground.getResultDelete(inputData);			 
						break;
					}
					case UPDATE : {
						process.sql = SQLFactory.getQueryObject(TypeEvents.UPDATE);
						List<Integer> listUpdate = ProcessForeground.getResultUpdate(inputData);
						system_arrayId(inputData, listUpdate);
						if(listUpdate.size() > 0){
							inputData.put("typeEvent", "select");
							process.sql = SQLFactory.getQueryObject(TypeEvents.SELECT);
							ResultSet resultSet   = ProcessForeground.getResultSet(inputData);
							try{
								boolean b = resultSet.first();
								/*
								 * Реализовать ПОЗЖЕ
								 * result += "data:";
								 */
								ResultSetMetaData metaData = resultSet.getMetaData();
								int count = metaData.getColumnCount();
								while(b){				
								  for(int i = 1; i <= count; i++)									  
									result += (i ==1 ? SpecialSumbol.breakdownNone : SpecialSumbol.breakdownSubLine) + 
											  metaData.getColumnLabel(i) + "=" + resultSet.getString(i);										  
								  result += SpecialSumbol.breakdownLine(resultSet.isLast());
								  b = resultSet.next();			  			  
								}
							}catch(SQLException e){
								e.printStackTrace();
							}
							result += "&resultQueryType=" + inputData.get("resultQueryType");
						}						
						break;
					}
					case SELECT : {
						  process.sql = SQLFactory.getQueryObject(TypeEvents.SELECT);
						  ResultSet resultSet   = ProcessForeground.getResultSet(inputData);
						  try{
							boolean b = resultSet.first();
							ResultSetMetaData metaData = resultSet.getMetaData();
							int count = metaData.getColumnCount();
							while(b){				
							  for(int i = 1; i <= count; i++)
								result += (i ==1 ? SpecialSumbol.breakdownNone : SpecialSumbol.breakdownSubLine) + 
										  metaData.getColumnLabel(i) + "=" + resultSet.getString(i);
							  result += SpecialSumbol.breakdownLine(resultSet.isLast());
							  b = resultSet.next();			  			  
							}
						  }catch(SQLException e){
							  e.printStackTrace();
						  }
						  break;
					}
					case SHOW : {
					  process.sql = SQLFactory.getQueryObject(TypeEvents.SELECT);
					  ResultSet resultSet = ProcessForeground.getResultSet(inputData);
					  try{
						boolean b = resultSet.first();
						while(b){
						  result += ch + resultSet.getString(1) + "=" + resultSet.getString(2);
						  ch = SpecialSumbol.breakdownLine(resultSet.isLast());
						  b = resultSet.next();			  			  
						}
					  }catch(SQLException e){
						  e.printStackTrace();
					  }
					  break;			
					}
					case INSERT : {
						process.sql = SQLFactory.getQueryObject(TypeEvents.INSERT);
						List<Integer> listInsert = ProcessForeground.getResultInsert(inputData);			
						system_arrayId(inputData, listInsert);
						if(listInsert.get(0) != -1){
							inputData.put("typeEvent", "select");
							process.sql = SQLFactory.getQueryObject(TypeEvents.SELECT);
							ResultSet resultSet   = ProcessForeground.getResultSet(inputData);
							try{
								boolean b = resultSet.first();
								ResultSetMetaData metaData = resultSet.getMetaData();
								int count = metaData.getColumnCount();
								while(b){				
									for(int i = 1; i <= count; i++)
										result += (i == 1 ? SpecialSumbol.breakdownNone : SpecialSumbol.breakdownSubLine) + 
												metaData.getColumnLabel(i) + "=" + resultSet.getString(i);
									result += SpecialSumbol.breakdownLine(resultSet.isLast());
									b = resultSet.next();			  			  
								}
								result += "&resultQueryType=" + inputData.get("resultQueryType");
							}catch(SQLException e){
								e.printStackTrace();
							}				
						}
						break;
					}
					case FILTER : {
					  process.sql = SQLFactory.getQueryObject(TypeEvents.SELECT);
  					  ResultSet resultSet   = ProcessForeground.getResultSet(inputData);
					  try{
						boolean b = resultSet.first();
						ResultSetMetaData metaData = resultSet.getMetaData();
						int count = metaData.getColumnCount();
						while(b){				
						  for(int i = 1; i <= count; i++)
							result += (i ==1 ? SpecialSumbol.breakdownNone : SpecialSumbol.breakdownSubLine) + 
									  metaData.getColumnLabel(i) + "=" + resultSet.getString(i);
						  result += SpecialSumbol.breakdownLine(resultSet.isLast());
						  b = resultSet.next();			  			  
						}
					  }catch(SQLException e){
						  e.printStackTrace();
					  }
					  break;
					}
					case INFO : {
						result = Resource.loadResource("info.html");
						break;
					}
					
					default: {
						process.sql = SQLFactory.getQueryObject(TypeEvents.SELECT);
						ResultSet resultSet   = ProcessForeground.getResultSet(inputData);
						try {
							boolean b = resultSet.first();
							result = b ? resultSet.getString(1) : "0";
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;	
					}
					}
				}else{
					session = request.getSession(false);
					if(session != null){
					  Logger.addLog("Session : " + session.getId() + " is close");
					  BufferProcess.getMap().remove(session.getId());
					  session.invalidate();				 
					}
					getPageEntry(response.getWriter());
				}			
			}
			if(Error.isError(false)){				
			    result += "&errors:";
			    for(Entry<String, Object> entry : Error.errorInfo.entrySet())
			      result += entry.getKey() + "=" + entry.getValue() + SpecialSumbol.breakdownSubLine;			  
			    Error.errorInfo.clear();	
			}
			response.getOutputStream().write(result.getBytes(Encoding.UTF8));				
		}else getPageEntry(response.getWriter());
		
		Logger.addLog("Query processing time : " + (new Date().getTime() - start) + " ms");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 /*
		 * Стартовое время обраотки запроса
		 */
		  long startDate = new Date().getTime();
		
		  ProcessForeground process = bufferProcess.getProcess(request.getSession().getId());
		  try{
			request.setCharacterEncoding(Encoding.UTF8);
			
			response.setCharacterEncoding(Encoding.UTF8);
			response.setContentType("text/html; charset=" + Encoding.UTF8);
			PrintWriter pw = response.getWriter();

			HttpSession session = null;
			String contentDataRequest = Validate.statusCompliteRequest(request.getInputStream(), process, true);
			if(contentDataRequest != null && 
			  !contentDataRequest.equals("")){
				inputData = new HashMap<String, Object>();
				inputData.putAll(getMapParamsOfRequest(contentDataRequest));
				
				switch(TypeEvents.getValue((String)inputData.get("typeEvent"))){
				  case ENTRY : {
					ProcessBackground.getInstance().run(ListBackgoundProcess.AUTHORIZATION, request, pw, inputData);
					break;
				  }
				  case LOGOUT : {
					if(request != null){
					  session = request.getSession(false);
					  if(ProcessForeground.map != null) ProcessForeground.map.clear();
					  if(session != null){
						  Logger.addLog("Session : " + session.getId() + " is close");
						  BufferProcess.getMap().remove(session.getId());
						  session.invalidate();	
					  }
					}
					getPageEntry(pw);
					break;
				  }			
				default : {				
						session = request.getSession(false);
						if(session != null && BufferProcess.getMap().containsKey(request.getSession().getId())){
							Logger.addLog("Session : " + session.getId() + " is work");
							 process = BufferProcess.getProcess(session.getId());		
							if(process != null){
							  process.setContentDataRequest(contentDataRequest);
							  process.map.putAll(inputData);	  
							  inputData = null;
							  process.setPw(pw);
							  process.run(session.getId());					
							}					
						}else{
						  getPageEntry(pw);	
						}	
					break;
				}
			  }		
		    }else{
		    	Error.errorInfo.put("Error System", "Error\n При работе в системе наложены ограничения на 1.Некоторые системыне клавишы. 2. Повторную отправку запроса.");
		    	Error.errorInfo.put("Error System2", "Message\n Для продолжения работы вернитесь в систему. Нажмите стрелку 'Назад' в браузере");
		    }
			
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Max-Age", "86400");
			if(Error.isError(true))
				pw.write(Error.getPrintWrite());
			pw.close();
			Logger.addLog("Query processing time : " + (new Date().getTime() - startDate) + " ms");
		}catch(Throwable e){
			e.printStackTrace();
		}
	}

	public static Map<String, Object> getMapParamsOfRequest(String contentDataRequest){

		Map<String, Object> buffMap = new HashMap<String, Object>();
		String[] lines = contentDataRequest.split(SpecialSumbol.breakdownLine);
		String[] attr;
		for(String line : lines){
		  attr = line.split("=");
		  /*
		   * key параметра от клиента не длжен содержать "."
		   */
		  if(!attr[0].contains(".") && attr.length == 2){			  
			  try {
				buffMap.put(URLDecoder.decode(attr[0], Encoding.UTF8), URLDecoder.decode(attr[1], Encoding.UTF8));				
			} catch (UnsupportedEncodingException e) {
				Logger.addLog(e.getMessage());
			}
		  }	
		}
		for(String key : buffMap.keySet())
			Logger.addLog(key + " : " + buffMap.get(key));
		Logger.out("getMapParamsOfRequest", buffMap);
		return buffMap;
	}	

	
	private void getPageEntry(PrintWriter pw){
		pw.write(Resource.loadResource("entry.html"));
	}
	

	/**
	 * Сохранение идентификаторов кортежей при INSERT & UPDATE
	 * @param inputData
	 * @param list
	 */
	private void system_arrayId(Map<String, Object> inputData, List<Integer> list){		
		String system_arrayId = "";
		for(int i = 0, count = list.size(); i < count;)
		  system_arrayId += count == (++i) ? list.get(i - 1) : list.get(i - 1) + ",";
		inputData.put("system_arrayId", system_arrayId);
	}
}
