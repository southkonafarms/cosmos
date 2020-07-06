package com.zenred.cosmos.domain;

/**
 * uncluttered version of ClusterRep object
 * useful for generating JSON

 * @author johnredden
 *
 */

public class BasicClusterRep extends ClusterRep {
	
public BasicClusterRep(){}
	
	public BasicClusterRep(
			String clusterName, Double distance_sys_virt_centre,
			Double angle_in_radians, String cluster_type) {
		super.setClusterName(clusterName);
		super.setDistance_sys_virt_centre(distance_sys_virt_centre);
		super.setAngle_in_radians(angle_in_radians);
		super.setCluster_description(cluster_type);
	}
	
	public String getClusterName(){
		return super.getClusterName();
	}
	public void setClusterName(String clusterName){
		super.setClusterName(clusterName);
	}
	public Double getDistance_sys_virt_centre(){
		return super.getDistance_sys_virt_centre();
	}
	public void setDistance_sys_virt_centre(Double distance_sys_virt_centre) {
		super.setDistance_sys_virt_centre(distance_sys_virt_centre);
	}
	public Double getAngle_in_radians() {
		return super.getAngle_in_radians();
	}
	public void setAngle_in_radians(Double angle_in_radians) {
		super.setAngle_in_radians(angle_in_radians);
	}
	public String getCluster_description() {
		return super.getCluster_description();
	}
	public void setCluster_description(String cluster_type) {
		super.setCluster_description(cluster_type);
	}

}
