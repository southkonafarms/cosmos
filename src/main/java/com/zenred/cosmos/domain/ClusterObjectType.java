package com.zenred.cosmos.domain;

public enum ClusterObjectType {
	
	CLUSTERNAME ("clusterName"){
		@Override
		public String getName() {
			return "clusterName";
		}

		@Override
		public void storeValue(String value) {
			dbxferObject = new DBxferObject();
			clusterRep = new ClusterRep();
			dbxferObject.setType(clusterRep);
			clusterRep = (ClusterRep)dbxferObject.getType();
			String clusterName = value;
			clusterRep.setClusterName(clusterName);
			dbxferList = DBxferList.instance();
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
			clusterRep.setDistance_sys_virt_centre(distance_sys_virt_centre);
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
			clusterRep.setAngle_in_radians(angle_in_radians);
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
			clusterRep.setCluster_description(cluster_type);
			dbxferObject.setType(clusterRep);
			dbxferList.addDomainObject(dbxferObject);
		}
		
	}
	;
	
	private String type;
	private ClusterObjectType( String type){
		
	}
	
	public abstract String getName();
	public abstract void storeValue(String value);
	
	private static DBxferObject dbxferObject;
	private static DBxferList dbxferList;
	private static ClusterRep clusterRep;

}
