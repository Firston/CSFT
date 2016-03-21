package com.oep.db.sql;

import java.util.Map;
import com.oep.dictionary.ListTables;


public class QueryDelete implements SQL{


	@Override
	public synchronized String getQuery(Map<String, Object> map) {

		  /*
		   *  ВСЕ РАБОТАЕТ ПО DEFAULT
		   *  
		   *  РАЗРАБОТАТЬ ЗАПРОСЫ КАСКАДНОГО УДАЛЕНИЯ
		   */
		  String table = map.get("table").toString().replaceAll("table_", "");
		  switch(ListTables.getValue(null)){
		    default : {
		    	return "DELETE FROM `" + table + "` WHERE `id`=" + map.get("id"); 
		    }
		  }
	}

	@Override
	public String getId(Object obj) {
		return null;
	}

	@Override
	public String getStringCheckNull(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer instanceQuery() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
