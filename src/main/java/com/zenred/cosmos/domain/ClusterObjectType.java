package com.zenred.cosmos.domain;

public enum ClusterObjectType {
	
	CLUSTERNAME ("clusterName"){
		@Override
		public String getName() {
			return "clusterName";
		}

		@Override
		public void storeValue(String value) {
			String clusterNamme = value;
			
		}
	},
	DISTANCETOVIRTUALCENTRE("distance_sys_virt_centre"){

		@Override
		public String getName() {
			return "distance_sys_virt_centre";
		}

		@Override
		public void storeValue(String value) {
			Double distance_sys_virt_centre = new Double(value);
		}
		
	},
	ANGLEINRADIANS("angle_in_radians"){

		@Override
		public String getName() {
			return "angle_in_radians";
		}

		@Override
		public void storeValue(String value) {
			Double angle_in_radians = new Double(value);
		}
		
	},
	CLUSTERTYPE("cluster_type"){

		@Override
		public String getName() {
			return "cluster_type";
		}

		@Override
		public void storeValue(String value) {
			String cluster_type = value;
		}
		
	}
	;
	
	private String type;
	private ClusterObjectType( String type){
		
	}
	
	public abstract String getName();
	public abstract void storeValue(String value);

}
