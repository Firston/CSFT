package com.oep.process.background;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oep.dictionary.ListRoles;
import com.oep.error.Error;
import com.oep.process.ProcessForeground;
import com.oep.process.data.BufferProcess;
import com.oep.utils.Logger;
import com.oep.utils.Validate;

public class Authorization {

		
	public static synchronized void checkAuth(HttpServletRequest request, PrintWriter pw, Map<String, Object> inputData){
	
		if(Validate.checkEntry(request, inputData)){
			ProcessForeground process = new ProcessForeground(pw);
			  
			  /*
			   * Инфорация о пользователе открывшем сессию
			   */					  
			  /*
			   * обязательные пааметры для хранения в отдельном массиве
			   */
			  process.getInfoWorkingPerson().put("code_role", inputData.get("code_role"));
			  process.getInfoWorkingPerson().put("id_supplier", inputData.get("id_supplier"));
			  /*
			   * обязательный параметр. пследний запрос сделанный пользователем
			   */
			  //process.setContentDataRequest(contentDataRequest);
			  /*
			   *  не обязательные пааметры для хранения в отдельном массиве
			   */					  
			  process.getInfoWorkingPerson().put("description_role", inputData.get("description_role"));
			  process.getInfoWorkingPerson().put("description_user", inputData.get("description_user"));
			  
			  //buffMap.clear();
			  inputData = null;
			  
			  /*
			   * общие значения для обоих учеток
			   */
			  process.map.put("typeEvent", "select");
			  process.map.put("table", "services");
			  process.map.put("isPre", false); //? надобность, проверить
      	  
	      	  /*
	      	   * индивидуальные значения 
	      	   * реализовано с учетом введения новых типов ролей 
	      	   */
	          switch(ListRoles.getValue(process.getInfoWorkingPerson().get("code_role").toString())){		    	
	        	case ADMIN :{
	        	  process.map.put("elementContent", "table");
	        	  break;
	        	}
	        	case SUPPLIER : {
	        	  process.map.put("elementContent", "tree");
	        	  break;
	        	}
	        	case TESTER : {
	        	  process.map.put("elementContent", "table");
	        	  break;
	        	}
	          }		    	
      	  		        	  
			  HttpSession session = request.getSession(true);		        	  
			  
			  BufferProcess.getMap().put(session.getId(), process);
			  Logger.addLog("Session : " + session.getId() + " is open");
			  process.run(session.getId());
		}else{
			  Error.errorInfo.put("Entry failure", "Error\n Ошибка входа. Не правильно введены пара Login & Password.");
			  pw.write(Resource.loadResource("entry.html"));
		}		
	}
}
