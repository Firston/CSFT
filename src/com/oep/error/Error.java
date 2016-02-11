package com.oep.error;

import java.util.Map;
import java.util.TreeMap;

public class Error {
	
	private static String printWrite;
	public static Map<String, Object> errorInfo = new TreeMap<String, Object>(); 
	

	/**
	 * Метод формирвоания блока ошибки
	 */
	private static String blockError(){
		
		
		if(errorInfo != null && errorInfo.size() > 0){
		  printWrite = "<head>" +
							"<link rel=\"stylesheet\" type=\"text/css\" href=\"/CSFT/style/body.css\">" +
						"</head>";
		  printWrite += "<div id='b_blockError'>" +
		  				"<div id=\"b_error_divBlock\">" +
		  				  "<input id=\"b_idErrorClose\" type=\"button\" onclick='closeError(\"b_blockError\")'/>";
		  int i = 1;
		  for(String key : errorInfo.keySet()){
			String[] value = errorInfo.get(key).toString().split("\n");			
			printWrite += "<div id='recordError" + i + "' class='b_block_error'>" +
							"<font class=\"" + TypeError.getClassStyle(value[0]) + "\">" +
						       value[0] + " : " +
						    "</font>" +
						    "<font class=\"war_info\">" +
						        value[1] +
						    "</font>" +
						  "</div>";
		    i++;
		  } 			
		  printWrite += "</div></div>";
		  return printWrite;			
		}
		return null;
	}
	
	/**
	 * Проверка наличия ошибок при работе системы
	 * @param isCreateBlock - TRUE : создает блок с имеющимися сообщениями, используется в POST запросах
	 * 						  FALSE : только проверяет наличие записей в errorInfo
	 * @return
	 */
	public static boolean isError(boolean isCreateBlock ){
		/*
		 * логический смысл переменной isCreateBlock меняется для возврата ответа
		 */
		isCreateBlock = isCreateBlock ? (blockError()== null ? false : true ) : (errorInfo.size() > 0);
		return isCreateBlock;
	}

	/**
	 * получение сгенерированного блока HTML кода, если ошибки были найдены
	 */

	public static String getPrintWrite() {
		errorInfo.clear();
		return printWrite;
	}
	
	/**
	 * Типы ошибок при работе системы
	 */
	private static enum TypeError {
		
		// знаения  классов присутствующих в файле body.css
		 Error(1){
			public String className(){
				return "war1";
			}
			
		}, Warning(2){
			public String className(){
				return "war2";
			}
		}, Message(3){
			public String className(){
				return "war3";
			}			
		};
		
		/**
		 * задает css класс для определенного типа ошибки
		 */
		public abstract String className();
		
		public static String getClassStyle(String value){
			for(TypeError typeError : TypeError.values()){
			  if(typeError.toString().equalsIgnoreCase(value))
				return typeError.className();
			}
			return "";
			
		}
		 private int value;

		 private  TypeError(int value){
			 this.setValue(value);
		 }

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
		
		public static TypeError getValue(String value){
			if(value == null || value.equals(""))
			  return null;
			for(TypeError typeError : TypeError.values())
			  if(typeError.toString().equalsIgnoreCase(value))
				return typeError;
			return null;
		}
	}
}
