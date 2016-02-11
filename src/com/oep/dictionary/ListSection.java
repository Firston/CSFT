package com.oep.dictionary;

/*
 * Использование для формирования ответа
 * Обобщение формирования ответа не произведено
 */
public enum ListSection {
	
	data(1), errors(2), info(3);
	
	private int value;		
	
	 private  ListSection (int value){
		 this.setValue(value);
	 }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
