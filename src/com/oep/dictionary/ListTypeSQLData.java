package com.oep.dictionary;

/*
 * Типы данных, возвращаемые в ResultSet,
 *  сформированных системой SQL запросов
 */
public enum ListTypeSQLData {

	NONE(0), INT(1),VARCHAR(2), TINYINT(3);
	
	
	 private int value;

	 private  ListTypeSQLData(int value){
		 this.setValue(value);
	 }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static ListTypeSQLData getValue(String value){
		if(value == null || value.equals(""))
		  return NONE;
		for(ListTypeSQLData listTypeSQLData : ListTypeSQLData.values())
		  if(listTypeSQLData.toString().equalsIgnoreCase(value))
			return listTypeSQLData;
		return NONE;
	}
}
