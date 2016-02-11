package com.oep.elements_view.table;

import java.io.PrintWriter;
import java.sql.ResultSet;

public class ViewTable extends AbstractTable{

	private static ViewTable instance;
	
	public static ViewTable getInstance(){
		if(instance == null)
			instance = new ViewTable();
		return instance;
	}
	
	
	public synchronized String getTable(ResultSet resultSet){
		create(resultSet);
		return AbstractTable.contenttable;
	}
	public synchronized void getTable(ResultSet resultSet, PrintWriter pw){
		create(resultSet);
		pw.write(AbstractTable.contenttable);
	}
}


