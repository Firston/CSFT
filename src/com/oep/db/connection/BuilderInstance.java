package com.oep.db.connection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class BuilderInstance {
	
	private ConnectionBuilder connectionBuilder;
	private  static BuilderInstance instance;
	private BuilderInstance(){};
	
	public static BuilderInstance getInstance(){
	  if(instance == null){
		  instance = new BuilderInstance();
	  }
	  return instance;
	}

    public static synchronized void init(ServletConfig config) throws ServletException{
    	ConnectionBuilder connectionBuilder = getInstance().getConnectionBuilder();
    	if( connectionBuilder == null){
    	  try{
    		ConnectionBuilder.init(config.getServletContext());  
  		    connectionBuilder = ConnectionBuilder.getInstance();
  	      }catch(Exception e){
  		      throw new ServletException(e.getMessage());	  
  	      }
        }
    	instance.setConnectionBuilder(connectionBuilder); 
    }

	public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
		this.connectionBuilder = connectionBuilder;
	}

	public ConnectionBuilder getConnectionBuilder() {
		return connectionBuilder;
	}  
    
}
