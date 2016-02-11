package com.oep.util.debug;

import java.util.HashMap;
import java.util.Map;

import com.oep.db.sql.QuerySelect;

public class Debug {

	
	public static void main(String[] args) {
		
		if(args.length < 1){
			System.out.println("Обязательный параметр TypeDebug не передан.");
		}
				
		switch (TypeDebug.valueOf(args[0])) {
		
			case Connection:
				
				break;
			case Help  : {
				
				System.out.println("Создание справки. Не реализовано");
				break;
			}
			case Refresh : {
				break;
			}
			case SQl : {
				String[] agr; 
				/*
				 * формат передачи данных
				 * typeEvent:select table:users
				 */
				QuerySelect querySelect = new QuerySelect();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("typeEvent", args[1]);
				map.put("table", args[2]);				
				
				System.out.println(querySelect.getQuery(map));
				break;
			}
			default:
				System.out.println("typeDebug=" + args[0] + ". Данный тип отладки не предусмотрен.");
				return;
			}
	}
}
