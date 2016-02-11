package com.oep.elements_interface;

import java.sql.ResultSet;

public abstract interface Table {
	
	/**
	 * Шапка таблицы
	 * @param numberColumn
	 * @param res
	 */
	public abstract void header(int numberColumn, ResultSet res, String tableName);
	
	/**
	 * Тело таблицы
	 * @param numberColumn
	 * @param res
	 */
	
	public abstract String body(int numberColumn, ResultSet res);

	public abstract void create(ResultSet res);
}
