package com.oep.dictionary;

/*
 * Список  возможных для построения объектов страницы
 */
public enum ListElements {

	NONE(0), TREE(1), TABLE(2), FORM(3), TOOLS(4), BLOCKS(5);
	
	 private int value;

	 private ListElements(int value){
		 this.setValue(value);
	 }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static ListElements getValue(String value){
		if(value == null || value.equals(""))
		  return NONE;
		for(ListElements listElement : ListElements.values())
		  if(listElement.toString().equalsIgnoreCase(value))
		    return listElement;
		return NONE;
	}
}
