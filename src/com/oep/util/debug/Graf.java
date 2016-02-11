package com.oep.util.debug;

public class Graf implements Runnable{

	private Thread thread;
	
	private boolean is = true;
	
	public Graf(Thread thread){
		this.thread = thread;
	}
	@Override
	public void run() {
		
		while(is){
			
			if(thread.isAlive())
				continue;
			else{
			  System.out.println("data_name_1 : " + Data.data_name_1);
			  System.out.println("data_name_2 : " + Data.data_name_2);
			  is = false;
			}
		}
	}
}
