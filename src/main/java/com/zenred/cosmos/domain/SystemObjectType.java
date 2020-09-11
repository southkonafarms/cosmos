package com.zenred.cosmos.domain;



public enum SystemObjectType{
	DISTANCE_TO_GALAXY_CENTRE("distance_to_galaxy_centre"){
		@Override
		public String getName() {
			return "distance_to_galaxy_centre";
		}
		public void storeValue(String value){
			dbxferObject = new DBxferObject();
			system = new System();
			dbxferObject.setType(system);
			system = (System)dbxferObject.getType();
			Double distance_to_galatic_centre = new Double(value);
			system.setDistance_to_galaxy_centre(distance_to_galatic_centre);
			dbxferList = DBxferList.instance();
			dbxferList.initList();   // System is always the first object and there is only one.
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
			system.setUcoordinate(ucoordinate);
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
			system.setVcoordinate(vcoordinate);
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
				system.setSystemName(systemName);
				dbxferObject.setType(system);   // last field, update
				dbxferList.addDomainObject(dbxferObject);
			}
					
	}
	;
	
	private String type;
	private SystemObjectType( String type){
		
	}
	
	private static DBxferObject dbxferObject;
	private static System system;
	private static DBxferList dbxferList;
	
	public abstract String getName();
	public abstract void storeValue(String value);
}
