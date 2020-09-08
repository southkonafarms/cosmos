package com.zenred.cosmos.domain;

class PlanarType{
	String pType;
	public PlanarType (){
		
	}
	public void  setType(String p_type){
		pType = p_type;
	}
}


public enum PlanetoidObjectType {
	

	PLANETOIDNAME("planetoidName"){

		@Override
		public String getName() {
			return "planetoidName";
		}

		@Override
		public void storeValue(String value) {
			String [] tinyArray = value.split(":");
			String planetoidName = tinyArray[0];
			setType(tinyArray[1]);  // a moon or a planet
		}
		
	},
	RADIUS("radius"){

		@Override
		public String getName() {
			return "radius";
		}

		@Override
		public void storeValue(String value) {
			Double radius = new Double(value);
			
		}
		
	},
	DISTANCETOPRIMARY("distanceToPrimary"){

		@Override
		public String getName() {
			return "distanceToPrimary";
		}

		@Override
		public void storeValue(String value) {
			Double distanceToPrimary = new Double(value);
			
		}
		
	},
	DEGREE("degree"){

		@Override
		public String getName() {
			return "degree";
		}

		@Override
		public void storeValue(String value) {
			Double degree = new Double(value);
			
		}
		
	},
	TEMPERATURE("temperature"){

		@Override
		public String getName() {
			return "temperature";
		}

		@Override
		public void storeValue(String value) {
			Double temperature = new Double(value);
			
		}
		
	},
	PERCENTWATER("percentWater"){

		@Override
		public String getName() {
			return "percentWater";
		}

		@Override
		public void storeValue(String value) {
			Double percentWater = new Double(value);
			
		}
		
	}

	;
	private  PlanetoidObjectType(String type) {
	}

	public abstract String getName();
	public abstract void storeValue(String value);
	
	public PlanarType planarType = new PlanarType();
	public void setType(String p_type){
		planarType.setType(p_type);
	}

}
