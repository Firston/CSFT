package com.oep.db.connection;


import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;

import com.oep.utils.Logger;

public class ConnectionBuilder {
	
	private static ConnectionBuilder instance;
	private String url;
	private String login;
	private String password;

	private ConnectionBuilder(){
		
      try { 
        Class.forName("com.mysql.jdbc.Driver"); 
      }catch (ClassNotFoundException cnfe){ 
        Logger.addLog("ERROR : Драйвер com.mysql.jdbc.Driver отсутствует");
      }
	}
	
	public static synchronized void init(ServletContext context) throws Exception{
		
		ConnectionBuilder object = getInstance();	
		if(object.url!=null &&
		   object.login!=null && 
		   object.password!=null){
		  return;	
		}		
		object.url = context.getInitParameter("url");
		if(object.url ==null){
			throw new Exception("Не заполнено соединение с БД");
		}
		object.login = context.getInitParameter("login");
		if(object.login ==null ){
			throw new Exception("Не заполнен логин для БД");
		}
		object.password = context.getInitParameter("password");
        if(object.password == null){
        	throw new Exception("Не заполнен пароль");
        }
	}
	
	public static ConnectionBuilder getInstance() throws Exception {
		
	  if(instance == null){
		instance = new ConnectionBuilder();
	  }
	  return instance;
	}
	
	public Connection getConnection() throws Exception{ 		
		
	  Connection result = DriverManager.getConnection(url, login, password);
	  if (result == null) {
	    throw new Exception("Не удалось установить соединение с БД!");
      }
	  return result;
	}
}
