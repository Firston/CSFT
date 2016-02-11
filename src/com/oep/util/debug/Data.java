package com.oep.util.debug;

import java.util.ArrayList;

public class Data implements Runnable{

	/**
	 * Наименование объекта
	 */
	private String name;
	
	/**
	 * Сквозной идентификатор события
	 */
	private static int id;
	/**
	 * количество итераций
	 */
	private static int count = 100;
	
	private int persentOnw;
	
	/*
	 * Данные 1
	 */
	public static java.util.List<Integer> data_name_1;

	/*
	 * Данные 2
	 */
	public static java.util.List<Integer> data_name_2;
	
	public Data(String name){
		
		if(data_name_1 == null)
			data_name_1 = new ArrayList<Integer>();
		if(data_name_2 == null)
			data_name_2 = new ArrayList<Integer>();
		this.name=name;
	}
	
	@Override
	public void run() {
		
		System.out.println("СТАРТ " + this.name );
		
		id=0;
		while(id<count){
		  try {
		    //Thread.sleep(1000);
			System.out.println(this.name + " : " + (++id));
			persentOnw++;
		  } catch (Exception e) {
			e.printStackTrace();
		  } 	
		}		  		
		System.out.println(this.name + " завершено. " + Float.valueOf(String.valueOf(persentOnw))  + " %");
		if(this.name.equals("NAME_1")){
			data_name_1.add(persentOnw);
			System.out.println("Раземр data_name_1 " + data_name_1.size());
		}else if(this.name.equals("NAME_2")){
			data_name_2.add(persentOnw);
			System.out.println("Раземр data_name_2 " + data_name_2.size());
		}
	}
}
