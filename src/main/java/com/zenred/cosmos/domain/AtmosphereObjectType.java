package com.zenred.cosmos.domain;
import java.util.ArrayList;
import java.util.List;

public enum AtmosphereObjectType {
	
	CHEMNAME("chem_name"){
		

		@Override
		public void storeValue(String name, String percentage) {
			dbxferObject = new DBxferObject();
			atmosphere = new Atmosphere();
			dbxferObject.setType(atmosphere);
			atmosphere = (Atmosphere)dbxferObject.getType();
			Double d_percentage = new Double(percentage);
			String chem_name = name;
			atmosphere.setChem_name(chem_name);
			atmosphere.setPercentage(d_percentage);
			dbxferList = DBxferList.instance();
			dbxferList.addDomainObject(dbxferObject);
		}

		@Override
		public String getToken() {
			return "chem_name";
		}

		@Override
		public void setPlanarType(String planarType) {
			planetoidType = planarType;
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
	private static String planetoidType;
}
