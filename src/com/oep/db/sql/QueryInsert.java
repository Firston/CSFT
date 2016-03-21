package com.oep.db.sql;

import java.util.Map;

import com.oep.dictionary.ListBackgoundProcess;
import com.oep.dictionary.ListTables;
import com.oep.process.ProcessBackground;
import com.oep.process.data.BufferProcess;
import com.oep.utils.Validate;

public class QueryInsert implements SQL{

	/**
	 * with version 16.02.24
	 * Введен для уменьшения количества операций при формирвоании запросов к БД
	 */
	private StringBuffer sb;
	
	private Map<String, Object> map;
	@Override
	public synchronized String getQuery(Map<String, Object> map) {
		this.map = map;
		
		String table = map.get("table").toString().replaceAll("table_", "");
		  switch(ListTables.getValue(table)){
		    case ROLES : {
		      return  "INSERT `roles` (`code_role`, `description_role`) " +
		      		  "VALUES ('" + getStringCheckNull(map.get("code_role")) + "','" + 
		      			 		    getStringCheckNull(map.get("description_role")) +"')";  	
		    }
		    case LISTTYPES : {
		    	return "INSERT `listTypes`(`id`, `typeValue`) VALUES('" + getStringCheckNull(map.get("id")) + "', '" + 
		    															  getStringCheckNull(map.get("typeValue")) + "')";
		    }
		    case COMMISSIONS : {
		      return "INSERT `commissions` (`scriptCommission`, `descriptionCommission`) " +
		      		 "VALUES ('" + getStringCheckNull(map.get("scriptCommission")) + "','" + 
		      		 			   getStringCheckNull(map.get("descriptionCommission")) + "')";
		    }
		    case SUPPLIERS : {
		    	return "INSERT `suppliers` (`name_supplier`) " +
		    		   "VALUES ('" + getStringCheckNull(map.get("name_supplier")) +"')";
		    }
		    case USERS : {
		    	return "INSERT `users` (`login`,`description_user`) " +
		    		   "VALUES ('" + getStringCheckNull(map.get("login")) + "','" + 
		    		   				 getStringCheckNull(map.get("description_user")) +"')";
		    }
		    case SERVICES : {
		    	return "INSERT `services`(`id_supplier`, `name_service`, `code_service`, `service_access`)" +
		    		   "VALUES ('" + map.get("id_supplier") + "','" + getStringCheckNull(map.get("name_service")) + "','" 
		    		   			   + getStringCheckNull(map.get("code_service")) + "','" + Validate.isCheckBox(map.get("service_access")) + "')";
		    }
		    case SUBSERVICES : {
		    	return "INSERT `subServices`(`name_subService`, `external_code_service`, `id_service`) " +
		    		   "VALUES('" + getStringCheckNull(map.get("name_subService")) + "','"
				    		      + getStringCheckNull(map.get("external_code_service")) + "','"
				    		      + map.get("id") + "')";
		    }
		    case DICTIONARIES : {
		    	return "INSERT `dictionaries` (`name_dictionary`, `description_dictionary`, `id_supplier`) " +
	    		   "VALUES ('" + getStringCheckNull(map.get("name_dictionary")) + "','"
	    		   			   + getStringCheckNull(map.get("description_dictionary")) + "','"
	    		   			   + getId(null) +"')";
		    }
		    case TYPEOBJECT : {
		        return "INSERT `typeObject` (`id_listType`,`code`,`external_code_field`, `label`,`require`,`message`,`defaultValue`,`id_supplier`) " +
		        	   "VALUES('" + map.get("id_listType")  + "', '" + getStringCheckNull(map.get("code")) + "', '" + getStringCheckNull(map.get("external_code_field")) 
		        	   			  + "', '" + getStringCheckNull(map.get("label")) + "'," 
		        	   			  + Validate.isCheckBox(map.get("require")) + ",'" + getStringCheckNull(map.get("message")) + "','" + getStringCheckNull(map.get("defaultValue")) + "','"
		        	   			  + getId(map.get("id_supplier")) + "')";
		    }
		    case CONTENTSUBSERVICES : { 
		    	
		    	if(map.containsKey("valueDefault")){		    		
		    		return "INSERT  `contentSubServices` (`id_subService`, `id_typeObject`, `id_service`, `id_listPrioritie`,`defaultValue`) " +
		    			   "SELECT " + map.get("id_subService") + ", `contentGroupObjects`.`id_typeObject`, null, 0,'' " +
		    			   "FROM `contentGroupObjects` " +
		    			   "INNER JOIN `listGroupObjects` " +
		    			   "ON `listGroupObjects`.`id` = `contentGroupObjects`.`id_listGroupObject` " +
		    			   "WHERE `listGroupObjects`.`id` = " + map.get("id_listGroupObject");
		    	}else{
			    	return "INSERT  `contentSubServices` (`id_subService`, `id_typeObject`, `id_service`, `id_listPrioritie`,`defaultValue`) " +
		    		   "VALUES ('" + map.get("id_subService") + "', '" + map.get("id_typeObject") + "', null, 0,'')";	
		    	}		    			    	
		    }
		    case SCRIPTS : {
		    	return "INSERT `scripts` (`script`, `id_supplier`, `psevdoNameScript`)" +
		    		   "VALUES('" + getStringCheckNull(map.get("script")) + "', '" + getId(null) + "', '" + 
		    		   				getStringCheckNull(map.get("psevdoNameScript")) + "')";
		    }
		    case CONTENTDICTIONARIES : {
		    	return "INSERT `contentDictionaries` (`id_dictionarie`, `code_param`, `value_param`)" +
		    		   "VALUES('" + map.get("id") + "','" + getStringCheckNull(map.get("code_param")) + "','" + 
		    		   										getStringCheckNull(map.get("value_param")) + "')"; 
		    }
		    case SCRIPTSOBJECT : {
		    	return "INSERT `scriptsObject` (`id_script`,`id_typeObject`)" +
		    		   "VALUES('" + map.get("id_script") + "','" + map.get("id_typeObject") + "')";
		    }
		    case TERMINALS : {
		    	return "INSERT `terminals`(`code_terminal`, `location`)" +
		    		   "VALUES('" + getStringCheckNull(map.get("code_terminal")) + "', '" + 
		    		   				getStringCheckNull(map.get("location")) + "')";
		    }
		    case COMMISSIONSERVICES : {
		    			    	
		    	if(map.containsKey("valueDefault")){
		    		String valueDefault = (String) map.get("valueDefault");
		    		if(valueDefault.equals("id_service")){
			    		ProcessBackground.getInstance().run(ListBackgoundProcess.COMMISSION_SERVICES_DEFAULT, map);
			    		return  "INSERT INTO `commissionServices`(`id_commission`,`id_terminal`,`id_service`, `id_subService`)" +
				   				"SELECT '" + map.get("id_commission") + "', `terminals`.`id`, '" + map.get("id_service") + "', NULL " +
				   				"FROM `terminals`";	
		    		}else if(valueDefault.equals("id_terminal")){
			    		return  "INSERT INTO `commissionServices`(`id_commission`,`id_terminal`,`id_service`, `id_subService`) " +
		   						"SELECT `commissionServicesDefault`.`id_commission`, " + map.get("id_terminal") + ", `commissionServicesDefault`.`id_service`, NULL " +
		   						"FROM `commissionServicesDefault` " +
		   						"INNER JOIN `services` " +
		   						"ON `services`.`id`=`commissionServicesDefault`.`id_service` " +
		   						"WHERE `services`.`service_access` = 1";	
		    		}
					   		
		    	}else{
		    		return "INSERT `commissionServices`(`id_commission`,`id_terminal`,`id_service`, `id_subService`)" +
		    			   "VALUES('" + map.get("id_commission") +"','" + map.get("id_terminal") + "','" + map.get("id_service") + "', NULL)";
		    	}
		    }
		    case PROFILEACCESS : {
		    	return "INSERT `profileAccess`(`id_role`,`id_user`,`id_supplier`,`password`,`profile_access`)" +
		    		   "VALUES ('" + map.get("id_role") + "', '" + map.get("id_user") + "','" 
		    		               + map.get("id_supplier") + "','" + getStringCheckNull(map.get("password")) + "','" 
		    		               + Validate.isCheckBox(map.get("profile_access"))  + "')";		    	
		    }
		    case CONTENTGROUPOBJECTS : {
		    	return "INSERT INTO `contentGroupObjects` (`id_listGroupObject`, `id_typeObject`) " +
		    		   "VALUES ('" + map.get("id_listGroupObject") + "', '" + map.get("id_typeObject") +"')";	
		    }
		    case LISTGROUPOBJECTS : {
		    	return "INSERT INTO `listGroupObjects` (`description_group`,`id_service`) " +
		    		   "VALUES('" + map.get("description_group") +"', '" + map.get("id_service") +"')";
		    }
		    case COMMISSIONSERVICESDEFAULT : {		    	
		    	return "INSERT INTO `commissionServicesDefault` (`id_service`, `id_commission`) " +
		    		   "VALUES('" + map.get("id_service") +"', '" + map.get("id_commission") + "')"; 		    	
		    }
		    case LISTCLASSSERVICE : {

		    	return instanceQuery().append("INSERT INTO `listClassService` (`className`, `description`) VALUES('")
		    						  .append(map.get("className"))
		    						  .append("','")
		    						  .append(map.get("description"))
		    						  .append("')")
		    						  .toString();
		    }
		    case CLASSSERVICES : {
		    	
		    	return instanceQuery().append("INSERT INTO `classServices` (`id_service`,`external_code`,`id_classService`) VALUES (")
		    						  .append(map.get("id_service"))
		    						  .append(", '")
		    						  .append(getStringCheckNull(map.get("external_code")))
		    						  .append("', ")
		    						  .append(map.get("id_classService"))
		    						  .append(")")
		    						  .toString();
		    }
		  }
		
		return null;
	}
	
	public String getId(Object obj){			
		String sessionId = (String)this.map.get("sessionId");
  		return obj != null ?  obj.toString() : BufferProcess.getProcess(sessionId).getInfoWorkingPerson().get("id_supplier").toString(); 
	}

	@Override
	public String getStringCheckNull(Object value) {		
		return value != null ? value.toString() : "";
	}

	@Override
	public StringBuffer instanceQuery() {
		return sb = sb == null ? new StringBuffer() : sb.delete(0, sb.length());
	}
}
