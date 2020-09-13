package com.zenred.cosmos.domain;
import java.util.ArrayList;
import java.util.List;

public enum AtmosphereObjectType {
	
	CHEMNAME("chem_name"){
		

		@Override
		public void storeValue(String name, String percentage) {
			Double d_percentage = new Double(percentage);
			String chemName = name;
			
		}

		@Override
		public String getToken() {
			return "chem_name";
		}

		@Override
		public void setPlanarType(String planarType) {
			String planetoidType = planarType;
		}
		
	}
	;
	
	private AtmosphereObjectType(String type){
		
	}

	public abstract String getToken(); 
	public abstract void storeValue(String name, String percentage );
	public abstract void setPlanarType(String planarType);
	private static DBxferObject dbxferObject;
	private static DBxferList dbxferList;
	private static Atmosphere atmosphere;
}
