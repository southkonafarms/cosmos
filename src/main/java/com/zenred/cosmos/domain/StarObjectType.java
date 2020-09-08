package com.zenred.cosmos.domain;

public enum StarObjectType {
	
	STAR_NAME("starName"){

		@Override
		public String getName() {
			return "name";
		}

		@Override
		public void storeValue(String value) {
			String name = value;       
			
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
			
		}
		
	}
	;
	
	private String type;
	private StarObjectType( String type){
		
	}
	public abstract String getName();
	public abstract void storeValue(String value);


}
