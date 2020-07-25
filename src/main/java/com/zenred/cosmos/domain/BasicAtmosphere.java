package com.zenred.cosmos.domain;

public class BasicAtmosphere extends Atmosphere {

	public BasicAtmosphere() {
		super();
	}

	public BasicAtmosphere( String chem_name, Double percentage) {
		super.setChem_name(chem_name);
		super.setPercentage(percentage);
	}
	
	public String getChem_name(){
		return super.getChem_name();
	}
	
	public void setChem_name(String chem_name){
		super.setChem_name(chem_name);
	}

}
