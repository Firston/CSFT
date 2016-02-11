package com.oep.db.sql;


import java.util.Map;

import com.oep.dictionary.ListTables;
import com.oep.error.Error;
import com.oep.utils.Validate;

public class QueryUpdate implements SQL{

	@Override
	public synchronized String getQuery(Map<String, Object> map) {

		  String table = map.get("table").toString().replaceAll("table_", "");
		  switch(ListTables.getValue(table)){
		    case ROLES : {
		    	
		      return "UPDATE `roles` SET `code_role`='" + getStringCheckNull(map.get("code_role")) + "', " +
		      							"`description_role`='" + getStringCheckNull(map.get("description_role")) + "' " +
		      		 "WHERE `id`='" + map.get("id") + "'";
		    }
		    case LISTTYPES : {
		    	
		    	return "UPDATE `listTypes` SET `typeValue` = '" + getStringCheckNull(map.get("typeValue")) + "'" +
		    		   "WHERE `id`='" + map.get("id") + "'";
		    }
		    case COMMISSIONS : {
		    	
		      return "UPDATE `commissions` SET `scriptCommission`='" + getStringCheckNull(map.get("scriptCommission")) + "', " +
		      								  "`descriptionCommission`='" + getStringCheckNull(map.get("descriptionCommission")) + "' " +
		      		"WHERE `id`='" + map.get("id") + "'";
		    }
		    case SUPPLIERS : {
		    	
		    	return "UPDATE `suppliers` SET `name_supplier`='" + getStringCheckNull(map.get("name_supplier"))  + 
		    		   "' WHERE `id`='" + map.get("id") + "'";
		    }
		    case USERS : {
		    	
		    	return "UPDATE `users` SET `login`='" + getStringCheckNull(map.get("login")) + "'," + 
		    							  "`description_user`='" + getStringCheckNull(map.get("description_user")) + "' " + 
		    		   "WHERE `id`='" + map.get("id") + "'";
		    }
		    case SERVICES : {
		    	
		    	return "UPDATE `services` SET `id_supplier` = '" + map.get("id_supplier") + "', " +
		    								 "`name_service` = '" + getStringCheckNull(map.get("name_service")) + "', " +
		    								 "`code_service` = '" + getStringCheckNull(map.get("code_service")) + "', " +
		    								 "`service_access` = '" + Validate.isCheckBox(map.get("service_access")) + "' " +
		    		   "WHERE `id`='" + map.get("id") + "'";
		    }
		    case SUBSERVICES : {		
		    	
		    	return "UPDATE `subServices` SET `name_subService` = '" + getStringCheckNull(map.get("name_subService")) + "', " +
		    									"`external_code_service` = '" + map.get("external_code_service")  + "' " +
		    		   "WHERE `id`='" + map.get("id") + "'";
		    }
		    case DICTIONARIES : {
		    	
		    	return "UPDATE `dictionaries` SET `name_dictionary`='" + getStringCheckNull(map.get("name_dictionary")) + "', " +
		    									 "`description_dictionary`='" + getStringCheckNull(map.get("description_dictionary")) + "'" +
		    		   " WHERE `id`='" + map.get("id") +"'";
		    }
		    case TYPEOBJECT : {
 
		    	return "UPDATE `typeObject` SET `id_listType`='" + map.get("id_listType") + "',`code`='" + getStringCheckNull(map.get("code")) + "'," +
		    								  " `external_code_field` = '" + getStringCheckNull(map.get("external_code_field")) + "'," +
				  							  " `label`='" + getStringCheckNull(map.get("label")) + "', `require`='" + Validate.isCheckBox(map.get("require")) + "'," +
				  							  " `message`='" + getStringCheckNull(map.get("message")) + "', `defaultValue`='" + getStringCheckNull(map.get("defaultValue")) + "' " +
				  		"WHERE `id`='" + map.get("id") + "'";
		    }
		    case CONTENTDICTIONARIES : {
		    	
		    	return "UPDATE `contentDictionaries` SET `code_param` = '" + getStringCheckNull(map.get("code_param")) + "', " +
		    											"`value_param`='" + getStringCheckNull(map.get("value_param")) + "'" +
		    		   "WHERE `id`='" + map.get("id") + "'";
		    }
		    case SCRIPTS : {
		    	
		    	return "UPDATE `scripts` SET `script` = '" + getStringCheckNull(map.get("script")) + "', " +
		    								"`psevdoNameScript`='" + getStringCheckNull(map.get("psevdoNameScript")) + "' " +
		    		   "WHERE `id` = '" + map.get("id") + "'"; 
		    }
		    case TERMINALS : {
		    	
		    	return "UPDATE `terminals` SET `code_terminal` = '" + getStringCheckNull(map.get("code_terminal")) + "', " +
		    								  "`location` = '" + getStringCheckNull(map.get("location")) + "' " +
	    		       "WHERE `id` = '" + map.get("id") + "'";
		    }
		    case COMMISSIONSERVICES : {
		    	
		    	if(map.containsKey("valueDefault")){
		    		String valueDefault = (String) map.get("valueDefault");
			    	return "UPDATE `commissionServices` SET `id_commission` = '" + map.get("id_commission") + "' " +
 		 			   	   "WHERE `" + valueDefault + "` = " + map.get(valueDefault); 
		    	}else{
		    		return "UPDATE `commissionServices` SET `id_commission` = '" + map.get("id_commission") + "', " +
		    												"`id_terminal` = '" + map.get("id_terminal")  + "', " +
		    												"`id_service` = '" + map.get("id_service") + "' " +
		    			   "WHERE `id` = " + map.get("id");
		    	}
		    }
		    case PROFILEACCESS : {
		    	
		    	return "UPDATE `profileAccess` SET `id_role` = '" + map.get("id_role") + "', `id_user` = '" + map.get("id_user") + "', " +
		    									  "`id_supplier` = '" + map.get("id_supplier") + "'," +
		    									  "`password` = '" + getStringCheckNull(map.get("password")) + "',`profile_access` ='" + Validate.isCheckBox(map.get("profile_access")) + "'" +
		    		   "WHERE `id` = " + map.get("id");
		    }
		    case CONTENTSUBSERVICES : {
		    	
		    	if(map.containsKey("valueDefault")){
		    		Error.errorInfo.put("Error Update - 0001","Error\n - Обновление содержимого подуслуги не допускается. Удалите привязянные объекты.");
		    	}else{
			    	return "UPDATE `contentSubServices` SET `id_listPrioritie` = '" + map.get("id_listPrioritie") + "', " +
															"`defaultValue` = '" + getStringCheckNull(map.get("defaultValue")) + "'" +
						 " WHERE `id`= " + map.get("id");	
		    	}
		    }
		    case LISTGROUPOBJECTS : {		    	
		    	
		    	return "UPDATE `listGroupObjects` SET `description_group` = '" + map.get("description_group") + "'," +
		    										 "`id_service` = '" + map.get("id_service") + "' " +
		    		   "WHERE `id` =" + map.get("id");
		    }
		    default : {
		    	return ""; 
		    }
		  }
	}

	@Override
	public String getId(Object obj) {
		return null;
	}

	@Override
	public String getStringCheckNull(Object value) {
		return value != null ? value.toString() : "";
	}
	
}
