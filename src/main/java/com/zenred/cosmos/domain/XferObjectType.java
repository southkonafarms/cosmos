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
	}
	;

	private String type;
	private XferObjectType( String type){
	}
	public abstract String getName();	

}
