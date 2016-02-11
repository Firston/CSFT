package com.oep.process.background;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.Map.Entry;

import com.oep.process.ProcessForeground;
import com.oep.process.data.BufferProcess;
import com.oep.process.data.Prop;
import com.oep.servlet.Activity;
import com.oep.utils.Logger;

public class RefreshSession implements Task{

	private static RefreshSession instance;
	
	public static RefreshSession getInstance(){
		if(instance == null)
			instance = new RefreshSession();
		return instance;
	}
	
	@Override
	public TimerTask getTask() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				refreshActiveSession();
			}
		};
		return task;
	}

	private void refreshActiveSession(){


		Map<String, ProcessForeground> buffProcess = BufferProcess.getMap();
		Logger.addLog("Message: Количество открытых сессий " + buffProcess.size());
		
		if(buffProcess.size() != 0){
			  List<String> list = new ArrayList<String>();
	
			  long TIME_ACTIVE_SESSION = Long.valueOf(Prop.getPropValue(Activity.SYSTEM_CONFIG, "system_TIME_ACTIVE_SESSION"));
				for(Entry<String, ProcessForeground> map : buffProcess.entrySet())
				  if((new Date().getTime() - map.getValue().getLastModified().getTime()) > TIME_ACTIVE_SESSION * 1000)
					  list.add(map.getKey());
				
				for(String key : list){
				  buffProcess.remove(key);
				  Logger.addLog("Session : " + key + " is close");
				}
				Prop.setPropValue(Activity.SYSTEM_CONFIG, "system_TIME_LAST_REFRESH", String.valueOf(new Date().getTime()));
				Logger.addLog("Message: Обновлен список сессий " + new Date().toLocaleString());	
		}
    }
	
	
}
