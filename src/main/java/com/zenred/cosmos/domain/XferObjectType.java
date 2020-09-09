package com.zenred.cosmos.domain;

public enum XferObjectType {

	SYSTEM("JSON_system") {
		
		@Override
		public
		String getName() {
			return "JSON_system";
		}

	},
	CLUSTER_REP("JSON_clusterRep") {
		@Override
		public
		String getName() {
			return "JSON_clusterRep";
		}
	},
	CLUSTER_RENAMES("JSON_clustrer_renames") {
		@Override
		public
		String getName() {
			return "JSON_clustrer_renames";
		}
	},
	STAR("JSON_star"){
		@Override
		public String getName() {
			return "JSON_star";
		}
	},
	PLANET("JSON_planet"){

		@Override
		public String getName() {
			return "JSON_planet";
		}
		
	},
	PLANETATMOSPHERES("JSON_planet_atmospheres"){

		@Override
		public String getName() {
			return "JSON_planet_atmospheres";
		}
		
	},
	MOON("JSON_moon"){

		@Override
		public String getName() {
			return "JSON_moon";
		}
		
	},
	MOONATMOSPHERES("JSON_moon_atmospheres"){

		@Override
		public String getName() {
			return "JSON_moon_atmospheres";
		}
		
	}
	;

	private String type;
	private XferObjectType( String type){
	}
	public abstract String getName();	

}
