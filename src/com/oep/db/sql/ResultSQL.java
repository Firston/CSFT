package com.oep.db.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.oep.db.connection.ConnectionManager;
import com.oep.dictionary.TypeEvents;
import com.oep.error.Error;
import com.oep.process.ProcessForeground;
import com.oep.process.data.BufferProcess;
import com.oep.utils.Logger;


public class ResultSQL{
	
	private static ProcessForeground process;
	
	private static String currentSessionId;

	/*
	 * НЕ ЗАКРЫВАЕТИСЯ объект Statement нужна доработка
	 */
	public static ResultSet getResultSet(Map<String, Object> map){


		chechSession((String)map.get("sessionId"));
		try {
			String query = process.sql.getQuery(map);
			Logger.addLog(query);
			return ConnectionManager.getStatement().executeQuery(query);							
		} catch (SQLException e) {
			Error.errorInfo.put("Warning SQL_Select", "Warning\n" + e.getMessage());
			Logger.addLog("Error getResultSet : " + e.getMessage());
		}
		return null;
	}
	public static boolean getResultDelete(Map<String, Object> map){
		
		chechSession((String)map.get("sessionId"));
		try {
			String query = process.sql.getQuery(map);
			Logger.addLog(query);
			Statement stmt = ConnectionManager.getStatement(); 
			stmt.execute(query);
			Logger.addLog("Удалена запись № " + map.get("id") + " из таблицы - " + map.get("table")  + 
		  			            " пользователем : " + process.getInfoWorkingPerson().get("description_user"));
			stmt.close();
			return true;
		} catch (SQLException e) {
			Error.errorInfo.put("Warning SQL_Delete", "Warning\n" + e.getMessage());
			Logger.addLog("Error getResultDelete : " + e.getMessage());
			return false;
		}
	}
	public static List<Integer> getResultUpdate(Map<String, Object> map){
		
		chechSession((String)map.get("sessionId"));
		int idUpdate = -1;
		try {
			Statement stmt = ConnectionManager.getStatement();
			String query = process.sql.getQuery(map);
			Logger.addLog(query);
			stmt.executeUpdate(query);
			Object id = map.get("id");
			if(id != null){
			  idUpdate = Integer.valueOf(id.toString());
			  Logger.addLog("Обновлена запись № " + idUpdate + " в таблице - " + map.get("table") + 
			 	  " пользователем : " + process.getInfoWorkingPerson().get("description_user"));				
			}else{			  
			  idUpdate = stmt.getUpdateCount();
			  Logger.addLog("Обновлено : " + idUpdate + " записи//ей" + " в таблице - " + map.get("table") + 
		 	  " пользователем : " + process.getInfoWorkingPerson().get("description_user"));
			}
			map.put("resultQueryType", "update");
			stmt.close();
			return Arrays.asList(idUpdate);
		} catch (SQLException e) {
			Error.errorInfo.put("Warning SQL_Update", "Warning\n" + e.getMessage());
			Logger.addLog("Error getResultUpdate : " + e.getMessage());
			return Arrays.asList(idUpdate);
		}		
	}
	public static List<Integer> getResultInsert(Map<String, Object> map){		
		chechSession((String)map.get("sessionId"));
		ResultSet resultSet = null; String query = "";
		List<Integer> listInsert = new ArrayList<Integer>();
		int idInsert = -1;
		try {
			Statement stmt = ConnectionManager.getStatement();
			Object valueDefault = map.get("valueDefault");
			if(valueDefault != null){
			  map.put("typeEvent", "default");
			  //process.sql = SQLFactory.getInstance().create("select");
			  process.sql = SQLFactory.getQueryObject(TypeEvents.SELECT);
			  
			  resultSet = getResultSet(map);
			  resultSet.first();

			  if(Integer.valueOf(resultSet.getString(1)) != 0){
				  /*
				   * UPDATE нескольких записей
				   */
				  //process.sql = SQLFactory.getInstance().create("update");
				  process.sql = SQLFactory.getQueryObject(TypeEvents.UPDATE);
				  
				  listInsert.addAll(getResultUpdate(map));
				  return listInsert;
				  
			  }else{
				  /*
				   * INSERT нескольких записей
				   */
				  //process.sql = SQLFactory.getInstance().create("insert");
				  process.sql = SQLFactory.getQueryObject(TypeEvents.INSERT);
				  query = process.sql.getQuery(map);
				  Logger.addLog(query);
				  stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
				  resultSet = stmt.getGeneratedKeys();
				  boolean b = resultSet.first();
				  while(b) {
					listInsert.add(resultSet.getInt(1)); 
					Logger.addLog("Добавлена запись № " + resultSet.getInt(1) + " в таблице - " + map.get("table") + 
				  			  " пользователем : " + process.getInfoWorkingPerson().get("description_user"));
					b = resultSet.next();
					
				  }					
				}
			}else{
			  query = process.sql.getQuery(map);
			  Logger.addLog(query);
			  stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
			  resultSet = stmt.getGeneratedKeys();
			  resultSet.first(); 
			  idInsert = resultSet.getInt(1);
			  listInsert.add(idInsert);
			  if(idInsert != -1)
				Logger.addLog("Добавлена запись № " + idInsert + " в таблице - " + map.get("table") + 
						  			  " пользователем : " + process.getInfoWorkingPerson().get("description_user"));

			}
			Logger.addLog("listInsert.get(0) : " + listInsert.get(0));
			map.put("resultQueryType", "insert");
			stmt.close();
		} catch (MySQLSyntaxErrorException e1) {
		  Error.errorInfo.put("Warning SQL_Insert1", "Warning\n" + e1.getMessage());
		  Logger.addLog("MySQLSyntaxErrorException getResultInsert : " + e1.getMessage());
		  listInsert.add(-1);
		} catch (SQLException e) {
		  Error.errorInfo.put("Warning SQL_Insert2", "Warning\n" + e.getMessage());	
		  Logger.addLog("Error getResultInsert : " + e.getMessage());
		  listInsert.add(-1);
		}
		return listInsert;
	}
	
	
	private static void chechSession(String sessionId){
		if(currentSessionId != null){
		  if(!currentSessionId.equals(sessionId))
			process = BufferProcess.getProcess(sessionId);
		}else{
			currentSessionId = sessionId;
			process = BufferProcess.getProcess(sessionId);			
		}
	}
}
