package com.oep.buket;

import com.oep.db.sql.QueryDelete;
import com.oep.db.sql.QueryInsert;
import com.oep.db.sql.QuerySelect;
import com.oep.db.sql.QueryUpdate;
import com.oep.db.sql.SQL;
import com.oep.dictionary.TypeEvents;

public class SQLFactory_before151115 {
	
	private static SQLFactory_before151115 instance;
	
	private QueryDelete queryDelete = null;
	private QueryInsert queryInsert = null;
	private QuerySelect querySelect = null;
	private QueryUpdate queryUpdate = null;
	
	
	public static SQLFactory_before151115 getInstance(){
		if(instance == null)
			instance = new SQLFactory_before151115();
		return instance;
	}
	
	public static synchronized void init(){
		getInstance();
	}
	
	/*
	 * Проверить логику работы. Сомнение!!!
	 */
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
	
}
