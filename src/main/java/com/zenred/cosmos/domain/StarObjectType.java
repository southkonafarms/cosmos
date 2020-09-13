package com.zenred.cosmos.domain;

public enum StarObjectType {
	
	STAR_NAME("starName"){

		@Override
		public String getName() {
			return "name";
		}

		@Override
		public void storeValue(String value) {
			dbxferObject = new DBxferObject();
			star = new Star();
			dbxferObject.setType(star);
			star =(Star)dbxferObject.getType();
			String name = value; 
			star.setName(name);
			dbxferList = DBxferList.instance();
		}
		
	},
	DISTANCECLUSTVIRTUALCENTRE("distance_clust_virt_centre"){

		@Override
		public String getName() {
			return "distance_clust_virt_centre";
		}

		@Override
		public void storeValue(String value) {
			Double distance_clust_virt_centre = new Double(value);	
			star.setDistance_clust_virt_centre(distance_clust_virt_centre);
		}
		
	},
	LUMINOSITY("luminosity"){

		@Override
		public String getName() {
			return "luminosity";
		}

		@Override
		public void storeValue(String value) {
			Double luminosity = new Double(value);	
			star.setLuminosity(luminosity);
		}
		
	},
	NOPLANETSALLOWED("no_planets_allowed"){

		@Override
		public String getName() {
			return "no_planets_allowed";
		}

		@Override
		public void storeValue(String value) {
			
			Boolean no_planets_allowed = Boolean.FALSE;
			no_planets_allowed = value.equals("true");
			star.setNo_planets_allowed(no_planets_allowed);
		}
		
	},
	ANGLEINRADIANS("angle_in_radians"){

		@Override
		public String getName() {
			return "angle_in_radians_s";
		}

		@Override
		public void storeValue(String value) {
			Double angle_in_radians = new Double(value);
			star.setAngle_in_radians_s(angle_in_radians);
		}
		
	},
	STARCOLOR("star_color"){

		@Override
		public String getName() {
			return "star_color";
		}

		@Override
		public void storeValue(String value) {
			String star_color = value;
			star.setStar_color(star_color);
		}
		
	},
	STARTYPE("star_type"){

		@Override
		public String getName() {
			return "star_type";
		}

		@Override
		public void storeValue(String value) {
			String star_type = value;
			star.setStar_type(star_type);
		}
		
	},
	STARSIZE("star_size"){

		@Override
		public String getName() {
			return "star_size";
		}

		@Override
		public void storeValue(String value) {
			Double star_size = new Double(value);
			star.setStar_size(star_size);
			dbxferObject.setType(star);
			dbxferList.addDomainObject(dbxferObject);
		}
		
	}
	;
	
	private String type;
	private StarObjectType( String type){
		
	}
	public abstract String getName();
	public abstract void storeValue(String value);

	private static DBxferObject dbxferObject;
	private static DBxferList dbxferList;
	private static Star star;
}
