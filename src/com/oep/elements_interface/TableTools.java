package com.oep.elements_interface;

import java.sql.ResultSet;

public interface TableTools {

	/**
	 * Постраничная навигация в таблице
	 * @return
	 */
	public abstract String createHrefForPageOfTable();
	
	/**
	 * Фильтры по столбцами таблицы
	 * @param resultSet
	 * @return
	 */
	public abstract String createFilterForTable(ResultSet resultSet);
}
