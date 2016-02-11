package com.oep.elements_tree;

import java.io.PrintWriter;
import java.sql.ResultSet;

public class ViewTree extends AbstractTree{
	
	private static ViewTree instance;
	
	public static ViewTree getInstance(){
		if(instance == null)
			instance = new ViewTree();
		return instance;
	}
	
	public static synchronized String getTree(ResultSet resultSet, String sessionId){
		getInstance().create(resultSet, sessionId);
		return AbstractTree.contentTree;
	}
	public synchronized void getTable(ResultSet resultSet, PrintWriter pw, String sessionId){
		getInstance().create(resultSet, sessionId);
		pw.write(AbstractTree.contentTree);
	}
}


