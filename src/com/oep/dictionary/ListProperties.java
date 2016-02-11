package com.oep.dictionary;

public enum ListProperties {
	system_isLog(1), system_pathLog(2), system_TIME_LAST_REFRESH(3), system_TIME_ACTIVE_SESSION(4);
	
	private int value;		
	
	 private  ListProperties(int value){
		 this.setValue(value);
	 }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static ListProperties getValue(String value){
		if(value == null || value.equals(""))
		  return null;
		for(ListProperties list : ListProperties.values())
		  if(list.toString().equals(value))
			return list;
		return null;
	};
}
