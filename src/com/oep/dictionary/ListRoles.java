package com.oep.dictionary;

/*
 * Разрешенные роли работы в системе.
 * 
 * Под учеткой администраторы можно создавать 
 * дополнительные роли но их обработка ядром 
 * системы будет проходить по default
 * 
 */
public enum ListRoles {

	NONE(0), ADMIN(1), SUPPLIER(2), TESTER(3);
	
	 private int value;

	 private  ListRoles(int value){
		 this.setValue(value);
	 }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static ListRoles getValue(String value){
		if(value == null || value.equals(""))
		  return NONE;
		for(ListRoles listRole : ListRoles.values())
		  if(listRole.toString().equalsIgnoreCase(value))
			return listRole;
		return NONE;
	}
}
