package com.oep.dictionary;

public enum ListTypeBlock {

	NONE(0),
	/**
	 * БЛОК СОДЕРЖИТ ТОЛЬКО ВЫПАДАЮЩИЙ СПИСОК
	 */
	LIST(1),
	/**
	 * БЛОК СОДЕРЖИТ ТОЛЬКО ТАБЛИЦУ
	 */
	TABLE(2),
	/**
	 * БЛОК СОЖЕРЖИТ НЕСКОЛЬКО ОБЪЕКТОВ
	 */
	COMPLEX(3) ; 
	
	
	
	 private int value;

	 private ListTypeBlock(int value){
		 this.setValue(value);
	 }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static ListTypeBlock getValue(String value){
		
		if(value == null || value.equals(""))
		  return NONE;
		
		for(ListTypeBlock type : ListTypeBlock.values())
		  if(type.toString().equalsIgnoreCase(value))
		    return type;		
		return COMPLEX;
	}
}
