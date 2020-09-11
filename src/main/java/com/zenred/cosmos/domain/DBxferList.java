package com.zenred.cosmos.domain;

import java.util.LinkedList;

class Head{
	static String head = "Head";
}


public class DBxferList {
	
	private LinkedList<DBxferObject>  xferList = new LinkedList<DBxferObject>();
	private static DBxferList DBxferListthis = new DBxferList();

	public DBxferList() {
	}
	
	public void initList(){
		DBxferObject dbxferObject = new DBxferObject();
		Head head = new Head();
		dbxferObject.setType(head);
		xferList.add(dbxferObject);
	}
	
	public void addDomainObject(DBxferObject domainObject){
		xferList.add(domainObject);
	}
	
	public static DBxferList instance(){
		return DBxferListthis;
	}
}
