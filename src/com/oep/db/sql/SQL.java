package com.oep.db.sql;

import java.util.Map;

public interface SQL {

	/**
	 * формирование SQL запроса в зависимости от параметров 
	 * из HttpServletRequest request
	 */
	public abstract String getQuery(Map<String, Object> map);
	
	/**
	 * getId
	 * 
	 * используется для получение ИД поставщика, 
	 * к которому привязан пользователь открывший сессию
	 * 
	 * 
	 */
	public abstract String getId(Object obj);
	
	
	/**
	 * getValueCheckNull
	 * 
	 * проверка значения на null и преобразование к строке
	 * 
	 */
	public abstract String getStringCheckNull(Object value);
}
