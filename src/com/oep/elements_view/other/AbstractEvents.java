package com.oep.elements_view.other;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.oep.db.sql.ResultSQL;
import com.oep.process.ProcessForeground;
import com.oep.utils.Logger;

public abstract class AbstractEvents {

	/**
	 * 
	 * ДОЛЖЕН ВОЗВРАЩАТЬ ОДНО НАИМЕНОВАНИЕ по id записи в БД
	 *  map должен содержать `system_field`
	 * @return
	 */
	public static String getOneValue(){
		String value = "";
		if(ProcessForeground.map.get("system_field") != null){
			try {				
			    ResultSet res = ResultSQL.getResultSet(ProcessForeground.map);
			    if(res != null){
					boolean b = res.first();
					if(b){
						value = res.getString(1);
						value = value != null ? " \"" + value + "\"" : "";					
					}	
			    }
			} catch (SQLException e) {
				Logger.addLog(e.getMessage());
			}			
		}
		return value;		
	}

}
