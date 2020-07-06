package com.zenred.cosmos.domain;

import com.zenred.cosmos.domain.System;

/**
 * uncluttered version of System object
 * useful for generating JSON
 * @author johnredden
 *
 */

public class BasicSystem extends System {

	/**
	 * no arg CTOR
	 */
	public BasicSystem(){
		super();
	}
	
	public BasicSystem(Double distance_to_galaxy_centre,
			Double ucoordinate, Double vcoordinate, String systemName){
		super.setDistance_to_galaxy_centre(distance_to_galaxy_centre);
		super.setUcoordinate(ucoordinate);
		super.setVcoordinate(vcoordinate);
		super.setSystemName(systemName);
	}

	
	public Double getDistance_to_galaxy_centre() {
		return super.getDistance_to_galaxy_centre();
	}
	public void setDistance_to_galaxy_centre(Double distance_to_galaxy_centre) {
		super.setDistance_to_galaxy_centre(distance_to_galaxy_centre);
	}
	public Double getUcoordinate() {
		return super.getUcoordinate();
	}
	public void setUcoordinate(Double ucoordinate) {
		super.setUcoordinate(ucoordinate);
	}
	public Double getVcoordinate() {
		return super.getVcoordinate();
	}
	public void setVcoordinate(Double vcoordinate) {
		super.setVcoordinate(vcoordinate);
	}
	public String getSystemName() {
		return super.getSystemName();
	}
	public void setSystemName(String systemName) {
		super.setSystemName(systemName);
	}
}
