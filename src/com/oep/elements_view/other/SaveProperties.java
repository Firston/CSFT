package com.oep.elements_view.other;

import java.io.File;
import java.util.Map;

import com.oep.dictionary.ListProperties;
import com.oep.error.Error;
import com.oep.process.ProcessForeground;
import com.oep.utils.Logger;

public class SaveProperties extends AbstractProperties{
	
	private static SaveProperties instance;
	
	public static SaveProperties getiInstance(){
		if(instance == null)
			instance = new SaveProperties();
		return instance;
	}

	@Override
	public boolean save(Map<String, Object> map, ProcessForeground process) {
		super.save(map, process);
		switch (ListProperties.getValue(AbstractProperties.attrParam[0])) {
		case system_isLog :{
			Logger.addLog("Статус логироваия системы - " + AbstractProperties.attrParam[1] +
  						", изменен пользователем :" + process.getInfoWorkingPerson().get("description_user"));
			 Logger.getInstance().setCheckLog(Boolean.valueOf(AbstractProperties.attrParam[1]));
			 Error.errorInfo.put("info", "Message\n Обновлен статус логирования системы.");
			return true;			
		}
		case system_pathLog : {
			Logger.addLog("Путь расположения логов - " + AbstractProperties.attrParam[1] +
					   ", изменен пользователем :" + process.getInfoWorkingPerson().get("description_user"));
			Logger.getInstance().setPath(AbstractProperties.attrParam[1]);
			new File(AbstractProperties.attrParam[1]).mkdirs();
			Error.errorInfo.put("info", "Message\n Обновлен путь к файлам логов.");
			return true;
		}
		case system_TIME_ACTIVE_SESSION : {
			Logger.addLog("Значение параметра setMaxInactiveInterval для Сессий - " + AbstractProperties.attrParam[1] +
					   ", изменен пользователем :" + process.getInfoWorkingPerson().get("description_user"));
			Error.errorInfo.put("info", "Message\n Обновлено время активного состояния сесиий.");
			return true;			
		}
		default:
			return false;
		}
	}

}
