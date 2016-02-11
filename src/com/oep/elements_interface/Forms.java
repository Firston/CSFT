package com.oep.elements_interface;

import java.sql.ResultSet;


public interface Forms {

	/**
	 * 
	 * @param resultSet - результирующий нароб  полей для отрисовки.
	 * @return
	 */
	public abstract void create(ResultSet resultSet);

	/**
	 * 
	 * @param field - наименование поля из представления таблицы. формат - {id}:{Рус. описание}
	 * @param type - должен передавать типы данных разрешенные в спецификации HTML
	 * @param className -  используемый css класс для визуализации
	 */
	public abstract void createElementOfForm(String field, String type, String className, String value);

}
