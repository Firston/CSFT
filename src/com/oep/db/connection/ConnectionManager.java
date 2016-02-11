package com.oep.db.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.oep.utils.Logger;

public class ConnectionManager {


	private static Connection connection;
	
    public static Connection getConnection(){
    	
      try{
	      if(connection == null){
		    BuilderInstance builderInstance = BuilderInstance.getInstance();
	    	Logger.addLog("connection is null. Create new connection");
	    	ConnectionBuilder connectionBuilder = builderInstance.getConnectionBuilder();    	
	  		connection = connectionBuilder.getConnection();
	  		Logger.addLog("connection : " + connection); 
	  	  }
      }catch(Exception e){
    	  e.printStackTrace();
      }
  	  return connection;
    }
    
    @Deprecated
	public static Statement getStatement_old(){
					
		 try {			 
			 if(connection != null)
			   return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			 connection = getConnection();
			 return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
			   connection.close();
			   connection = null;
			   return getStatement_old();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * @version 15.11.17
     * @return
     */
	public static Statement getStatement(){
					
		Statement stmt = null;
		 try {			 
			 if(connection != null){
			   stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			 }else{
				 connection = getConnection();
				 stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);				 
			 }
		} catch (SQLException e) {
			e.printStackTrace();
			try {
			   connection.close();
			   connection = null;
			   return getStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stmt;
	}
    
	public static void setConnection(Connection connection) {
		ConnectionManager.connection = connection;
	}
}
