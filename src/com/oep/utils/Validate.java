package com.oep.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.oep.db.connection.ConnectionManager;
import com.oep.db.sql.ResultSQL;
import com.oep.db.sql.SQLFactory;
import com.oep.error.Error;
import com.oep.process.ProcessForeground;
import com.oep.process.data.Prop;
import com.oep.servlet.Activity;

public class Validate extends ResultSQL {
	
	private static ResultSet resultSet;

	
	/**
	 * преобразование спецсимолов. используется при формироании ответа от сервера
	 */
	public static String formatData(String content){
		
		return 	content.replaceAll("<", "&lt;").
				   		replaceAll(">", "&gt;").
				   		replaceAll("\"", "&quot;").
				   		replaceAll("\'", "&apos;");

	}
	
	/**
	 * Определяет можли ли преобразовать строку в число
	 * @param string
	 * @return
	 */
	public static boolean checkString(String string) {
		
        if (string == null)
          return false;
        return string.matches("^-?\\d+$");
    }
	
	/**
	 * Проверка корректности учетной записи для открытия новой сесии
	 */
	public synchronized static boolean checkEntry(HttpServletRequest request, Map<String, Object> map){
		
		
		/**
		 * формирование запроса аутентификации пользователя системы
		 */
		String query = SQLFactory.getInstance().create("select").getQuery(map);
		HttpSession session = request.getSession();
			try {
				try{
					resultSet = ConnectionManager.getStatement().executeQuery(query);	
				}catch(CommunicationsException e){
					Logger.addLog("Error connection with DB : " + e);
					ConnectionManager.setConnection(null);
					resultSet = ConnectionManager.getStatement().executeQuery(query);
				}
				resultSet.last();
				if(resultSet.getRow() == 1){				
					
				  session.setAttribute("login", request.getParameter("login"));
				  session.setMaxInactiveInterval(Integer.valueOf(Prop.getPropValue(Activity.SYSTEM_CONFIG, "system_TIME_ACTIVE_SESSION"))); 
				  map.put("code_role", resultSet.getString("code_role")); 
				  map.put("description_role", resultSet.getString("description_role"));
				  map.put("description_user", resultSet.getString("description_user"));
				  Object id_supplier = resultSet.getString("id_supplier");
				  if(id_supplier != null)
				  	  map.put("id_supplier", id_supplier);
				  return true;
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		return false;
	}
	/**
	 * Преобразование флага для занесения информации в БД
	 */
	public static String isCheckBox(Object is){
		/*
		 * Временное решение
		 *  for ON
		 */
		is = is.toString().equals("true") ? true : is.toString().equals("on") ? true :  false;
		return is != null ? (Boolean.valueOf(is.toString()) ? "1" : "0") : "0"; 		
	}

	/**
	 * Проверка предыдущего и текущего запросов в рамках одной сессии
	 * 
	 * isvalidate - параметр задающий значение словия ; Проверять? (true - да, false - нет)
	 * 
	 * Используется для doPost
	 */
	public static String statusCompliteRequest(InputStream currentData, ProcessForeground process, boolean isValidate){			
		
		String currentContentData = "";
			try{
				BufferedReader buff = new BufferedReader(new InputStreamReader(currentData));
				StringBuffer sb = new StringBuffer();
				sb.append(buff.readLine());
				currentContentData = URLDecoder.decode(sb.toString(), "UTF-8");
				if(process != null && isValidate 
				  && !currentContentData.contains("typeEvent=select")
				  && !currentContentData.contains("typeEvent=show")
				  && !currentContentData.contains("typeEvent=save_properties") ){
				  if(!currentContentData.equals(process.getContentDataRequest())){
					  process.setContentDataRequest(currentContentData);					  
				  }else{
					  Error.errorInfo.put("duplicate request", "Message\n Операция уже была выполнена ранее");
					  currentContentData = "";
				  }
				}					
			} catch (IOException e) {
				e.printStackTrace();
			}
		return currentContentData;
	}
	/**
	 * Проверка предыдущего и текущего запросов в рамках одной сессии
	 * 
	 * isvalidate - параметр задающий значение условия ; Проверять? (true - да, false - нет)
	 * 
	 * Используется для doGet
	 */
	public static String statusCompliteRequest(String currentContentData, ProcessForeground process, boolean isValidate){


	  if(process != null && isValidate 
		  && !currentContentData.contains("typeEvent=select") 
		  && !currentContentData.contains("typeEvent=show")){
		if(!currentContentData.equals(process.getContentDataRequest())){
			  process.setContentDataRequest(currentContentData);					  
		}else{
			Error.errorInfo.put("duplicate request", "Message\n Операция уже была выполнена ранее");
			currentContentData = "";
		}
	  }					
	  return currentContentData;
	  
	}	
}
