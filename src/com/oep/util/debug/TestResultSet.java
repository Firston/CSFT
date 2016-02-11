package com.oep.util.debug;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class TestResultSet {

	
	public static void main(String[] args) {
		
	      try { 
	          Class.forName("com.mysql.jdbc.Driver");
	          
	 	      Properties info = new Properties();
	 	      info.put("user", "root");
	 	      info.put("password", "root");
	 		  Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/terminal", info);
	 		  Statement stmt = connection.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_INSENSITIVE);
	 		  
	 		  ResultSet resultSet = stmt.executeQuery("SELECT * FROM `view_users`  ORDER BY `id:№ (i)`;");
	 		  ResultSetMetaData metaData = resultSet.getMetaData();
	 		  int columnCount = metaData.getColumnCount();
	 		  boolean b = resultSet.first();
	 		  while(b){
	 			  for(int i=1; i<=columnCount;i++){
	 				  System.out.println();
	 				  System.out.print(metaData.getColumnName(i));
	 				  System.out.println();
	 				 System.out.print(metaData.getColumnLabel(i));
	 			  }
	 			  b = resultSet.next();
	 		  }
	      }catch (ClassNotFoundException cnfe){ 
	          System.out.println("ERROR : Драйвер com.mysql.jdbc.Driver отсутствует");
	      } catch (SQLException e) {			
			e.printStackTrace();
		}	      
	      
		

	}
	
	
}
