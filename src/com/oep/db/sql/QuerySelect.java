package com.oep.db.sql;


import java.util.Collections;
import java.util.Map;

import com.oep.dictionary.ListRoles;
import com.oep.dictionary.ListTables;
import com.oep.dictionary.TypeEvents;
import com.oep.process.ProcessForeground;
import com.oep.process.data.BufferProcess;
import com.oep.utils.Logger;
import com.oep.utils.Validate;

public class QuerySelect implements SQL{

	private Map<String, Object> map;
	@Override
	public synchronized String getQuery(Map<String, Object> map) {
		this.map = map;
		
		String table = map.get("table") != null ? map.get("table").toString().replace("table_", "") : null; 
		switch(TypeEvents.getValue(map.get("typeEvent").toString())){
		  case ENTRY :{
			  
			  return "SELECT `profileAccess`.`id`, `roles`.`code_role`, `roles`.`description_role`," +
			  		        "`users`.`description_user`, `profileAccess`.`id_supplier`" +
			  		 "FROM `profileAccess`" +
			  		 "INNER JOIN `roles`" +
			  		 "ON	`profileAccess`.`id_role` = `roles`.`id`" +
			  		 "INNER JOIN `users`" +
			  		 "ON  `profileAccess`.`id_user` = `users`.`id`" +
			  		 "WHERE `users`.`login` = '" + map.get("login") + "'" +
			  		 "AND `profileAccess`.`password` = '" + map.get("password") + "'" +
			  		 "AND `profileAccess`.`profile_access` = 1";			  
		  }
		  case SELECT : {
			  
			  switch(ListTables.getValue(table)){
			  
			  	case ROLES :
			  	case COMMISSIONS:
			  	case SUPPLIERS:
			  	case USERS : 
			  	case PROFILEACCESS : 
			  	case TERMINALS :
			  	  return "SELECT * FROM `view_" + table.toLowerCase() + "` " + 
			  	  								  add_system_arrayId(false) + 
			  	  	    " ORDER BY `id:№ (i)` LIMIT 10 OFFSET " + addOFFSET() ;
			    case LISTTYPES : {
			    	
			    	return "SELECT * FROM `view_listtypes` " + add_system_arrayId(false) + 
			    		  " ORDER BY `id:№ (w)` LIMIT 10 OFFSET " + addOFFSET() ;
			    }

			    case SERVICES : {
			    	
			    	ProcessForeground process = BufferProcess.getProcess(map.get("sessionId").toString());
			    	switch (ListRoles.getValue(process.getInfoWorkingPerson().get("code_role").toString()) ) {
					  case ADMIN:{
						  
						return "SELECT * FROM `view_services` " + add_system_arrayId(false) + " " +
							   "ORDER BY `id:№ (i)` LIMIT 10 OFFSET " + addOFFSET();	
					  }
					  case SUPPLIER : {
						  
						return "SELECT `view_services`.`id:№ (i)`, `view_services`.`name_service:Услуга (w)` " +
							   "FROM `view_services`" +
							   "INNER JOIN `view_suppliers`" +
							   "ON `view_services`.`id_supplier:Поставщик (l)` = `view_suppliers`.`name_supplier:Наименование (w)`" +
							   "AND `view_suppliers`.`id:№ (i)` = '" + getId(null) + "'" +
							   "AND `view_services`.`service_access:Доступ (c)` = '1' " +
							   "ORDER BY `id:№ (i)`";						
					  }
					}
			    }			    
			    case SUBSERVICES : {
			    	
			    	return "SELECT `id:№`, `name_subService:Наименование` " +
			    		   "FROM `view_subservices` " +
			    		   "WHERE `id_service` = " + map.get("id");
			    }
			    case CONTENTSUBSERVICES : {
			    	
			    	Object system_arrayId = map.get("system_arrayId"); 
			    	if(system_arrayId != null){
			    	  map.remove("system_arrayId");
			    	  
			    	  return "SELECT `id:№ (i)`, `id_typeObject:Внутренний № элемента (i)`, " +
			    	  				"`id_listType:Тип элемента (l)`, `label:Описание (w)`, " +
			    	  				"`id_listPrioritie:Приоритет построения (l)`, " +
			    	  				"`defaultValue:Знач. по умолч. индивид-е (w)` " +
			    	  		 "FROM `view_contentsubservices`" +
			    	  		 "WHERE `id:№ (i)` IN (" + system_arrayId + ") " + 
			    	  	   " ORDER BY `id:№ (i)`";
			    	}else{
			    		
				      return "SELECT `id:№ (i)`, `id_typeObject:Внутренний № элемента (i)`, " +
				      				"`id_listType:Тип элемента (l)`, `label:Описание (w)`, " +
				      				"`id_listPrioritie:Приоритет построения (l)`, " +
				      				"`defaultValue:Знач. по умолч. индивид-е (w)` " +
				      		 "FROM `view_contentsubservices`" +
				      		 "WHERE `id_subService:Процедура`= " + map.get("id") +  
				      	   " ORDER BY `id:№ (i)`"; 
			    	}
			    }
			    case COMMISSIONSERVICES : {
			    	
			    	if(map.containsKey("valueDefault")){
			    		String key = (String) map.get("valueDefault");
			    		if(Validate.checkString(key)){
				    		return "SELECT `id:№ (i)`, `id_service:Услуга (l)`, `id_terminal:Терминал (l)`, `id_commission:Скрипт коммисии (l)` " +
			    			   	   "FROM `view_commissionservices` " +
			    			   	   "INNER JOIN `services` " +
			    			   	   "ON `view_commissionservices`.`id_service:Услуга (l)` = `services`.`name_service` " +
			    			   	   "WHERE `services`.`id` = '" + key + "' " +
			    			   	   "ORDER BY `id:№ (i)`";	
			    		}else{
							return "SELECT `services`.`code_service`, `services`.`name_service`, `commissions`.`descriptionCommission` " +
							   "FROM `commissionServices` " +
							   "INNER JOIN `services` " +
							   "ON `commissionServices`.`id_service` = `services`.`id` " +
							   "INNER JOIN `commissions` " +
							   "ON `commissionServices`.`id_commission` = `commissions`.`id`" +
							   (key == null ? "" 
									   		:" WHERE `commissionServices`.`" + key + "` = " + map.get(key));	
			    		}
			    	}else{
				      return "SELECT * FROM `view_commissionservices` " + add_system_arrayId(false) + 
				             " ORDER BY `id:№ (i)` LIMIT 10 OFFSET " + addOFFSET() ;	
			    	}
			    }
			    case TYPEOBJECT : {
			    	/*
			    	 *  "`view_typeobject`.`external_code_field: Код в системе поставщика(w)`, 
			    	 */
			    	return "SELECT `view_typeobject`.`id:№ (i)`, `view_typeobject`.`id_listType:Тип (l)`, `view_typeobject`.`code:Код (w)`," +
			    				  "`view_typeobject`.`label:Описание (w)`, `view_typeobject`.`require:Обязательность (c)`," +
			    				  "`view_typeobject`.`message:Сообщение (w)`, `view_typeobject`.`defaultValue:Значение по умолчанию (w)`" +
			    			"FROM `view_typeobject`" +
			    		  " WHERE " + add_system_arrayId(true) + "`view_typeobject`.`id_supplier` = " + getId(null) +
			    		  " ORDER BY `id:№ (i)` LIMIT 10 OFFSET " + addOFFSET();
			    }
				case SCRIPTS : {
					
					  return "SELECT `id:№ (i)`, `psevdoNameScript:Описание скрипта (w)`, `script:Скрипт (w)` " +
						     "FROM `view_scripts`" +
						     "WHERE " + add_system_arrayId(true) + "`id_supplier:Код поставщика`=" + getId(null) +
						   " ORDER BY `id:№ (i)` LIMIT 10 OFFSET " + addOFFSET();					
				}
			    case SCRIPTSOBJECT : {
			    	
			    	return "SELECT `view_scriptsobject`.`id:№ (i)`, " +
			    				  "`view_scriptsobject`.`psevdoNameScript:Описание скрипта (w)`, " +
			    				  "`view_scriptsobject`.`script:Скрипт (w)`, " +
			    				  "`view_scriptsobject`.`id_typeObject:Код объекта` " +
			    		   "FROM `view_scriptsobject`" +  add_system_arrayId(false) + 
			    		   "ORDER BY `id:№ (i)` LIMIT 10 OFFSET " + addOFFSET();
			    }
			    case DICTIONARIES : {
			    	
			    	  return "SELECT `view_dictionaries`.`id:№ (i)`, " +
			    	  				"`view_dictionaries`.`name_dictionary:Наименование справочника (w)`," +
			    	  				"`view_dictionaries`.`description_dictionary:Описание (w)` " +
			    		     "FROM `view_dictionaries`" +
			    		     "WHERE " + add_system_arrayId(true) + 
			    		     "`view_dictionaries`.`id_supplier:Поставщик`='" + getId(null) + "' " +
			    		     "ORDER BY `id:№ (i)` LIMIT 10 OFFSET " + addOFFSET();
			    }
			    case CONTENTDICTIONARIES : {
			    	
			    	Object system_arrayId = map.get("system_arrayId"); 
			    	if(system_arrayId != null){
			    	  map.remove("system_arrayId");
			    	  return "SELECT `view_contentdictionaries`.`id:№ (i)` , `view_contentdictionaries`.`code_param:Код (w)` ," +
		    				 	    "`view_contentdictionaries`.`value_param:Значение (w)`, " +
		    					    "`view_contentdictionaries`.`id_dictionarie:Справочник` " +
		    			     "FROM  `view_contentdictionaries` " +
		    			     "INNER JOIN `view_dictionaries` " +
		    			     "ON `view_contentdictionaries`.`id_dictionarie:Справочник` = `view_dictionaries`.`id:№ (i)` " +
		    			     "WHERE `view_contentdictionaries`.`id:№ (i)` IN (" + system_arrayId + ") " +
		    			     "AND `view_dictionaries`.`id_supplier:Поставщик` = " + getId(null)  + 
		    			     " ORDER BY `view_contentdictionaries`.`id:№ (i)`";
			    	}else
			    	return "SELECT `view_contentdictionaries`.`id:№ (i)`, `view_contentdictionaries`.`code_param:Код (w)`, " +
	  				  "`view_contentdictionaries`.`value_param:Значение (w)`, `view_contentdictionaries`.`id_dictionarie:Справочник` " +
		    		   "FROM `view_contentdictionaries` " +
		    		   "INNER JOIN `view_dictionaries` " +
		    		   "ON `view_contentdictionaries`.`id_dictionarie:Справочник` = `view_dictionaries`.`id:№ (i)` " +
		    		   "AND `view_dictionaries`.`id_supplier:Поставщик` = " + getId(null)  + 
		    		   " ORDER BY `view_contentdictionaries`.`id:№ (i)` LIMIT 10 OFFSET " + addOFFSET();
			    }
				case CONTENTGROUPOBJECTS : {

			    	Object system_arrayId = map.get("system_arrayId"); 
			    	if(system_arrayId != null){
			    	  map.remove("system_arrayId");
			    	  return "SELECT `id:№ (i)`, `id_listType:Тип (l)`, `code:Код (w)`, " +
			    	  		        "`label:Описание (w)`, `id_listGroupObject:Код группы`" +
			    	  		 "FROM `view_contentgroupobjects` " +
			    	  		 "WHERE `id:№ (i)` IN(" + system_arrayId + ")";
			    	}else{
						return "SELECT `id:№ (i)`, `id_listType:Тип (l)`, `code:Код (w)`, " +
									  "`label:Описание (w)`, `id_listGroupObject:Код группы` " +
							   "FROM `view_contentgroupobjects`" +
							   "WHERE `id_service` IN (SELECT `services`.`id` " +
													  "FROM `services` " +
													  "WHERE `services`.`id_supplier` = '" + getId(null) + "') " +
							   "ORDER BY `id:№ (i)` LIMIT 10 OFFSET " + addOFFSET();							
			    	}
			    }
				case LISTGROUPOBJECTS : {
					
			    	Object system_arrayId = map.get("system_arrayId"); 
			    	if(system_arrayId != null){
			    	  map.remove("system_arrayId");
			    	  return "SELECT `id:№ (i)`, `description_group:Наименование группы (w)`, `id_service:Услуга (l)` " +
					   		 "FROM `view_listgroupobjects` " +
					   		 "WHERE `id:№ (i)` IN (" + system_arrayId +") " +
					   		 "ORDER BY `id:№ (i)`";
			    	}else{
						return "SELECT `id:№ (i)`, `description_group:Наименование группы (w)`, `id_service:Услуга (l)` " +
						   "FROM `view_listgroupobjects` " +
						   "WHERE `id_supplier` = " + getId(null) + 
						   " ORDER BY `id:№ (i)`";
			    	}									
				}
				case COMMISSIONSERVICESDEFAULT : {
					
				}
			  }
			  Logger.addLog("Error : Критерии запроса из SELECT не удовлетворяют ни одному результату");
			  return null; 
		  }
		  case DICTIONARY :{
			  
			switch (ListTables.getValue(table)) {
			  case SUPPLIERS:{
				  
				  return "SELECT `id`, `name_supplier` " +
				  		 "FROM `suppliers`";
			  }
			  case SUBSERVICES : {
				  
				  return "SELECT `id`, `name_subService` " +
				  		 "FROM `subServices` " +
				  		 "WHERE `id_service`='" + map.get("id") + "'"; 				  
			  }
			  case LISTTYPES : {
				  
				  return "SELECT `id`, `typeValue` FROM `listTypes`";
			  }
			  case SCRIPTS : {
				  
				  return "SELECT `id`, `psevdoNameScript` " +
				  		 "FROM `scripts`" +
				  		 "WHERE `id_supplier` = '" + getId(null) + "'";
			  }
			  case LISTPRIORITIES : {
				  
				  return "SELECT `id`, `valuePriority` FROM `listPriorities`";
			  }
			  case SERVICES : {
				  
				  ProcessForeground process = BufferProcess.getMap().get(map.get("sessionId").toString());
				  if(ListRoles.getValue(process.getInfoWorkingPerson().get("code_role").toString()).equals(ListRoles.ADMIN))
					  return "SELECT `view_services`.`id:№ (i)`, `view_services`.`name_service:Услуга (w)`" +
					  		 "FROM `view_services`";
				  return "SELECT `id`, `name_service` " +
				  		 "FROM `services` " +
				  		 "WHERE `id_supplier` = '" + getId(null) + "'";
			  }
			  case TYPEOBJECT : {
				  
				  return "SELECT `id`, `label`" +
				  		 "FROM `typeObject`" +
				  		 "WHERE `id_supplier` = '" + getId(null) + "'";
			  }
			  case COMMISSIONS : {
				  
				  return "SELECT  `id`, `descriptionCommission` FROM `commissions`";
			  }
			  case TERMINALS : {
				  
				  Object name_service = map.get("name_service");				  				  
				  if(name_service != null)
					  return "SELECT * FROM `terminals` " +
					  		"WHERE `terminals`.`id` NOT IN (SELECT `terminals`.`id` FROM `terminals` " +
					  									   "INNER JOIN `commissionServices` " +
					  									   "ON `commissionServices`.`id_terminal` = `terminals`.`id` " +
					  									   "INNER JOIN `services` " +
					  									   "ON `services`.`id` = `commissionServices`.`id_service` " +
					  									   "WHERE `services`.`name_service` = '" + name_service + "')";
				  else  return "SELECT * FROM `view_terminals`";				  
			  }
			  case ROLES : {
				  
				  return "SELECT `id`, `code_role` " +
				  		 "FROM `roles`";
			  }
			  case USERS : {
				  return "SELECT `id`, `login` " +
				  		 "FROM `users`";
			  }
			  case LISTGROUPOBJECTS : {
				  
				  return "SELECT `listGroupObjects`.`id`, `listGroupObjects`.`description_group`" +
				  		 "FROM `listGroupObjects`" +
				  		 "INNER JOIN `services`" +
				  		 "ON `services`.`id`= `listGroupObjects`.`id_service`" +
				  		 "WHERE `listGroupObjects`.`id_service` IN (SELECT `services`.`id`" +
				  		 											"FROM `services`" +
				  		 											"WHERE `services`.`id_supplier` = '" + getId(null) + "')";
			  }
			}
			Logger.addLog("Error : Критерии запроса из DICTIONARY не удовлетворяют ни одному результату");
			return null;
		  }
		  case ADD : {
			  switch (ListTables.getValue(table)) {
			  	case SUBSERVICES:{
			  		
				  return "SHOW COLUMNS FROM view_subservices";	
			  	}
			  	case TYPEOBJECT : {
			  		
			  	  return "SHOW COLUMNS FROM view_typeobject";
			  	}
			  	default:
				break;
			}
			Logger.addLog("Error : Критерии запроса из ADD не удовлетворяют ни одному результату");
			return null;
		  }
		  case SHOW :{
			  switch(ListTables.getValue(table)){
			  
				case LISTPRIORITIES : {
					
				    return "SELECT `id`, `valuePriority` FROM `listPriorities`";
				}			  
			    case CONTENTSUBSERVICES : {
			    	
			     if(map.containsKey("system_field")){
			       String system_field = (String)map.remove("system_field");
			       map.remove("system_field");
				   return "SELECT `" + system_field + "` FROM `view_subservices` WHERE `id:№` = " + map.get("id"); 
			     }else
			    	 
				      return "SELECT `id:№ (i)`, `id_typeObject:Внутренний № элемента (i)`, " +
	      				"`id_listType:Тип элемента (l)`, `label:Описание (w)`, " +
	      				"`id_listPrioritie:Приоритет построения (l)`, " +
	      				"`defaultValue:Знач. по умолч. индивид-е (w)` " +
	      		 "FROM `view_contentsubservices`" +
	      		 "WHERE `id_subService:Процедура`= " + map.get("id") +  " ORDER BY `id:№ (i)`";
			    }
				case CONTENTDICTIONARIES : {
					
			    	if(map.get("system_isLabel") != null){
			    		map.remove("system_isLabel");	
			    		return "SELECT `name_dictionary:Наименование справочника (w)` " +
			    			   "FROM `view_dictionaries` " +
			    			   "WHERE `id:№ (i)` = " + map.get("id");
			    	}else return "SELECT `id:№ (i)`,`code_param:Код (w)`, `value_param:Значение (w)`" +
						  		 "FROM `view_contentdictionaries` " +
						  		 "WHERE `id_dictionarie:Справочник`=" + map.get("id");					  
				}
				case SCRIPTSOBJECT : {
					
					return "SELECT `view_scriptsobject`.`id:№ (i)`, `view_scriptsobject`.`psevdoNameScript:Описание скрипта (w)`, " +
							      "`view_scriptsobject`.`script:Скрипт (w)`" +
						   "FROM `view_scriptsobject`" +
						   "WHERE `view_scriptsobject`.`id_typeObject:Код объекта`='" + map.get("id") + "'";
				}			
			    case TYPEOBJECT : {
			    	
			    	return "SELECT DISTINCT `view_typeobject`.`code:Код (i)`, `view_typeobject`.`label:Описание (w)` " +
			    		   "FROM `view_typeobject`" +
			    		   "WHERE `view_typeobject`.`id_supplier:Поставщик` ='"+ getId(null) + "'";
			    }
			    case COMMISSIONS : {
			    	
			    	return "SELECT * FROM `view_commissions`";
			    }
			  }
			  Logger.addLog("Error : Критерии запроса из SHOW не удовлетворяют ни одному результату");
			  return null;
		  }
		  case DEFAULT : {

			  Object valueDefault = map.get("valueDefault");
			  if(valueDefault != null){
				  return "SELECT count(*) " +
				 	 "FROM `" + table + "` " +
				 	 "WHERE `" + valueDefault + "` = " + map.get(valueDefault);				  
			  }else{
				  return "SELECT count(*) " +
				 	 "FROM `" + table + "`";
			  }
		  }
		  case FILTER : {
		    switch (ListTables.getValue(table)) {
			  case TERMINALS: {
			  	  return "SELECT * FROM `view_" + table.toLowerCase() + "` " + 
					  	 addFilter() +   
					  	" ORDER BY `id:№ (i)`";
			  }
			  case SERVICES : {
				  /*
				   * Для учетки администратора системы
				   */
				return "SELECT * FROM `view_services` " + addFilter() + 
				       "ORDER BY `id:№ (i)`";
			  }
			  case COMMISSIONSERVICES : {
				  
			    return "SELECT * FROM `view_commissionservices` " + addFilter() + 
		               "ORDER BY `id:№ (i)`";
			  }
			  case TYPEOBJECT : {
				
			    return "SELECT `view_typeobject`.`id:№ (i)`, `view_typeobject`.`id_listType:Тип (l)`, `view_typeobject`.`code:Код (w)`, " +
			    				  "`view_typeobject`.`label:Описание (w)`, `view_typeobject`.`require:Обязательность (c)`, " +
			    				  "`view_typeobject`.`message:Сообщение (w)`, `view_typeobject`.`defaultValue:Значение по умолчанию (w)` " + 
			    	   "FROM `view_typeobject` " + addFilter() + 
			    	   "AND `view_typeobject`.`id_supplier` = " + getId(null) +
			    	   " ORDER BY `id:№ (i)`";
			  }
			  case CONTENTDICTIONARIES : {
				  
			    	return "SELECT `view_contentdictionaries`.`id:№ (i)`, `view_contentdictionaries`.`code_param:Код (w)`, " +
	  				  			  "`view_contentdictionaries`.`value_param:Значение (w)`, `view_contentdictionaries`.`id_dictionarie:Справочник` " +
		    		   "FROM `view_contentdictionaries` " +
		    		    addFilter() +  
		    		  "ORDER BY `view_contentdictionaries`.`id:№ (i)`";
			  }
			  case SCRIPTSOBJECT : {
			    	return "SELECT `view_scriptsobject`.`id:№ (i)`, " +
  				  				  "`view_scriptsobject`.`psevdoNameScript:Описание скрипта (w)`, " +
  				  				  "`view_scriptsobject`.`script:Скрипт (w)`, " +
  				  				  "`view_scriptsobject`.`id_typeObject:Код объекта` " +
  				  		   "FROM `view_scriptsobject`" + 
  				  		     addFilter() + 
  				  		   "ORDER BY `id:№ (i)`";  
			  }
			  case CONTENTGROUPOBJECTS : {
		    	  return "SELECT `id:№ (i)`, `id_listType:Тип (l)`, `code:Код (w)`, " +
      	 	    				"`label:Описание (w)`, `id_listGroupObject:Код группы` " +
      	 	    		 "FROM `view_contentgroupobjects` " +
      	 	    		   addFilter() + 
      	 	    		 "ORDER BY `id:№ (i)`";
			  }
			  default:
				break;
			  }
		    Logger.addLog("Error : Для таблицы " + table + " нет запроса формирующего выполку по критериям фильтра : " + addFilter());
		  }
		}
		Logger.addLog("Error : Не обработанная ошибка формирования запроса");
		return null;
	}


	public String getId(Object obj){
		ProcessForeground process = BufferProcess.getProcess(this.map.get("sessionId").toString());
		if(ListRoles.getValue(process.getInfoWorkingPerson().get("code_role").toString()).equals(ListRoles.ADMIN))
			return "";
		return (String) (obj = obj != null ? obj.toString() : process.getInfoWorkingPerson().get("id_supplier").toString()); 
	}

	/**
	 * 
	 * @param isAND : true - добавляет операнд AND в конец части запроса
	 * 				 false - считает, что условие является первым в списке добавляет в начало WHERE
	 * @return
	 */
	private String add_system_arrayId(boolean isAND){
    	Object system_arrayId = map.get("system_arrayId");
    	if(system_arrayId != null){
    		map.remove("system_arrayId");
    		return (isAND ? "" : " WHERE " ) + "  `id:№ (i)` IN (" + system_arrayId + ")" + (isAND ? " AND " : "");
    	} return "";
	}

	private String addFilter(){
		
		String where = "";
		for(String key : map.keySet()){
			if(key.startsWith("filter_")){
			  where += where.equals("") ? "WHERE " : "AND ";
			  where += key.replace("filter_", "`") + "` LIKE '%" + map.get(key) + "%' "; 
			}
		}
		
		return where;
	}
	
	private int addOFFSET(){
  	  Object system_numberPage = map.get("system_numberPage");
  	  if(system_numberPage != null){
  		  return 10 * Integer.valueOf(system_numberPage.toString()) - 10;
  	  }else return 0;		
	}
	
	@Override
	public String getStringCheckNull(Object value) {
		return null;
	}
	
}
