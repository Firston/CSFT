package com.oep.db.sql;

import com.oep.dictionary.TypeEvents;

public class SQLFactory {
	
	private static SQLFactory instance;
	
	private QueryDelete queryDelete = null;
	private QueryInsert queryInsert = null;
	private QuerySelect querySelect = null;
	private QueryUpdate queryUpdate = null;
	
	private SQLFactory(){}
	
	public static SQLFactory getInstance(){
		if(instance == null)
			instance = new SQLFactory();
		return instance;
	}
	
	public static synchronized void init(){
		getInstance();
	}
	
	public static SQL getQueryObject(TypeEvents action){
		
		return getInstance().create(action);
	}
	
	@Deprecated
	public SQL create(String action){
				
		if(action == null) return null;
		
		  switch(TypeEvents.getValue(action)){
			case SELECT :
				return querySelect = querySelect != null ? querySelect : new QuerySelect();
			case INSERT :
				return queryInsert = queryInsert != null ? queryInsert : new QueryInsert();
			case UPDATE : 
				return queryUpdate = queryUpdate != null ? queryUpdate : new QueryUpdate();
			case DELETE :
				return queryDelete = queryDelete != null ? queryDelete : new QueryDelete();
			default : return null;
		  }			
	}
	
	/**
	 * @version 15.11.15
	 * @param action
	 * @return
	 */
	private SQL create(TypeEvents action){
		
		if(action == null) return null;
		
		  switch(action){
			case SELECT :
				return querySelect = querySelect != null ? querySelect : new QuerySelect();
			case INSERT :
				return queryInsert = queryInsert != null ? queryInsert : new QueryInsert();
			case UPDATE : 
				return queryUpdate = queryUpdate != null ? queryUpdate : new QueryUpdate();
			case DELETE :
				return queryDelete = queryDelete != null ? queryDelete : new QueryDelete();
			default : return null;
		  }			
	}
	
}
