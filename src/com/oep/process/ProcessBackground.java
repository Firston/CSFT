package com.oep.process;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oep.db.sql.ResultSQL;
import com.oep.dictionary.ListBackgoundProcess;
import com.oep.process.background.Authorization;
import com.oep.process.background.RefreshSession;
import com.oep.utils.Logger;

public class ProcessBackground extends Timer implements Background{
	
	
	private static ProcessBackground instance;
	
	public static ProcessBackground getInstance(){
		if(instance == null)
			instance = new ProcessBackground();
		return instance;
	}
	
	
	/**
	 * event - Тпп действия
	 * objects - объекты необходимые для его выполнения
	 */
	public synchronized void run(ListBackgoundProcess event, Object...objects){
		
		switch(event){		
			/*
			 * Инициализация объектов для работы системы
			 */
			case INITIALIZATION : {
				
				com.oep.process.data.Prop.init();
				com.oep.utils.Logger.init();
				Logger.addLog("Инициализация объекта работы с файлами properties");
				Logger.addLog("Инициализация логирования системы");

				try {
					com.oep.db.connection.BuilderInstance.init((ServletConfig)objects[0]);
					Logger.addLog("Инициализация соединения с БД");
				} catch (ServletException e) {
					Logger.addLog("Cоединения с БД не становлено : " + e.getMessage());
				}

				com.oep.process.data.BufferProcess.init();
				Logger.addLog("Инициализация массива рабочих сессий");
				
				com.oep.db.sql.SQLFactory.init();
				Logger.addLog("Инициализация построителя sql - запросов");
								
				run(ListBackgoundProcess.REFRESHSESSION);
				break;
			}
			case REFRESHSESSION : {
				/*
				 * Время обновления списка сессий 60 секунд
				 * 
				 * !!!Время вынести в раздел настроек
				 */
				schedule(RefreshSession.getInstance().getTask(), new Date(), 60000);
				break;
			}
			case COMMISSION_SERVICES_DEFAULT : {
				/*
				 * Комиссия по услуге использующаяся на всех терминалах по УМОЛЧАНИЮ 
				 */
				HashMap<String, Object> map = new HashMap<String, Object>((HashMap<String, Object>) objects[0]);
				map.put("table", "commissionServicesDefault");
				map.remove("valueDefault");
				ResultSQL.getResultInsert(map);
				map = null;
				break;
			}			
			case AUTHORIZATION :{
				/*
				 * Авторизация пользователя в системе
				 */
				Authorization.checkAuth((HttpServletRequest)objects[0], 
										(PrintWriter)objects[1], 
										(Map<String, Object>)objects[2]);
					
				break;
			}
		}
	}

    /**
     * ПРОТОТИП ФУНКЦИОНАЛА
     */
	@Override
	public void run(Object object, Object... objects) {
		run((ListBackgoundProcess)object, objects);		
	}
}
