package com.oep.utils;

public abstract  class SpecialSumbol {

	public static String breakdownLine = "&";
	
	public static String breakdownSubLine = ";";
	
	public static String breakdownElement = ""; 
	
	public static String breakdownNone = "";
	
	
	public static  String breakdownLine(boolean isLast){
		
		return isLast ? breakdownNone : breakdownLine; 
	}
	
	public static  String breakdownSubLine(boolean isLast){
		
		return isLast ? breakdownNone : breakdownSubLine; 
	}
	
}
