package com.zenred.cosmos.domain;

public class DBxferObject {

	public DBxferObject() {
		super();
	}
	
	Object head;
	
	public void setType(Object classType){
		head = classType;
	}
	
	public Object getType(){
		return head;
	}

}
