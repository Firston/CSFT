package com.oep.elements_interface;

import java.sql.ResultSet;

public interface Tree {

	
	/**
	 * 
	 * @param resultSet - результирующий нароб  полей для отрисовки.
	 * @return
	 */
	public abstract void create(ResultSet resultSet, String sessionId);

	/**
	 * 
	 */
	public abstract String newContainer(boolean isRoot, ResultSet resultSet);
}
