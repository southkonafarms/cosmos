package com.zenred.cosmos.domain;

public enum SystemObjectType{
	DISTANCE_TO_GALAXY_CENTRE("distance_to_galaxy_centre"){
		@Override
		public String getName() {
			return "distance_to_galaxy_centre";
		}
		public void storeValue(String value){
			
			Double distance_to_galatic_centre = new Double(value);
		}
		
	},
	UCOORDINATE ("ucoordinate"){

		@Override
		public String getName() {
			return "ucoordinate";
		}

		@Override
		public void storeValue(String value) {
			Double ucoordinate = new Double(value);
			
		}
		
	}
	,
	VCOORDINATE ("vcoordinate"){

		@Override
		public String getName() {
			return "vcoordinate";
		}

		@Override
		public void storeValue(String value) {
			Double vcoordinate = new Double(value);
			
		}
	}
	,
	SYSTEMNAME ("systemName"){

			@Override
			public String getName() {
				return "systemName";
			}

			@Override
			public void storeValue(String value) {
				String systemName = value;
				
			}
					
	}
	;
	
	private String type;
	private SystemObjectType( String type){
		
	}
	
	public abstract String getName();
	public abstract void storeValue(String value);
}
