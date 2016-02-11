package com.oep.dictionary;

public enum ListDictionaries {
	
	NONE(0), SERVICES(1), TERMINALS(2), LISTGROUPOBJECTS(3), CONTENTSUBSERVICES(4);
	
	
	 private int value;

	 private ListDictionaries(int value){
		 this.setValue(value);
	 }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static ListDictionaries getValue(String value){
		
		if(value == null || value.equals(""))
		  return NONE;
		
		for(ListDictionaries dictionary : ListDictionaries.values())
		  if(dictionary.toString().equalsIgnoreCase(value))
		    return dictionary;
		return NONE;
	}

	
}
