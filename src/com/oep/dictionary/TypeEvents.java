package com.oep.dictionary;

public enum TypeEvents {

	/*
	 * ENTRY (1) - проставляется при входе в систему
	 * 
	 * SELECT(2), INSERT(3), UPDATE(4), DELETE(5) - типы действий  по SQL запросам
	 * 
	 * LOGOUT(6) - проставляется при выходе из системы
	 * 
	 *  DICTIONARY(7) - проставляется при формировании запроса на подгрузку справочника
	 *  
	 *  ADD(8), EDIT(9), DEL(10), SHOW(11) - типы действий для отображения контента
	 *  
	 *  NONE(12) - возвращаемое значение в случае не корректной передачи параметров в класс
	 *  
	 *  DEFAULT(13) - проставляется  для формироания SQL забросов без учета идентификаторов
	 *  
	 *  INFO - проставляется для вызова справки 
	 *  
	 *  SAVE_PROPERTIES (15)- сохранение системных настроек в файл *.properties
	 *  
	 *  REFRESH(16) - обновление страницы
	 *  
	 *  UNLOAD (17) - Выгрузка
	 */
	ENTRY (1), SELECT(2), INSERT(3), UPDATE(4), DELETE(5), 
	LOGOUT(6), DICTIONARY(7), ADD(8), EDIT(9), DEL(10), 
	SHOW(11), NONE(12), DEFAULT(13), INFO(14), SAVE_PROPERTIES(15),
	REFRESH(16), UNLOAD(17), FILTER(18);  

	 private int value;

	 private TypeEvents(int value){
		 this.setValue(value);
	 }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static TypeEvents getValue(String value){
		
		if(value == null || value.equals(""))
		  return NONE;
		for(TypeEvents typeEvent : TypeEvents.values())
			if(typeEvent.toString().equalsIgnoreCase(value))
				return typeEvent;
		return NONE;
	}

}


