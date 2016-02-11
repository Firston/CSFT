package com.oep.util.debug;

public class AppMain {

	
	
	public static void main(String[] args) throws InterruptedException {
		
		String name;
		
		Thread thread1 = null;
		Thread thread2 = null;
		int i=0;
		while(i<5){

			if(thread1 == null && thread2 == null){

				name = "NAME_1";
				thread1 = new Thread(new Data(name), name);
				thread1.setPriority(10);

				name = "NAME_2";
				
				thread2=  new Thread(new Data(name), name);
				thread2.setPriority(1);
				
				thread1.start();
				thread2.start();

				System.out.println("Итерация : " + (++i));
			}else if(!thread1.isAlive() && !thread2.isAlive()){

				name = "NAME_1";
				thread1 = new Thread(new Data(name), name);
				thread1.setPriority(10);

				name = "NAME_2";
				
				thread2=  new Thread(new Data(name), name);
				thread2.setPriority(1);
				
				thread1.start();
				thread2.start();

				System.out.println("Итерация : " + (++i));
			}
			
		}
		
		Thread thread3 = new Thread(new Graf(thread2), "GRAF_3");
		thread3.start();
	}
}
