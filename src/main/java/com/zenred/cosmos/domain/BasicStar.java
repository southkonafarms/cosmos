package com.zenred.cosmos.domain;

/**
 * domain object minus any database specific attributes
 * @author johnredden
 *
 */

public class BasicStar extends Star {

	public BasicStar() {
		super();
	}

	public BasicStar( String name, Double distance_clust_virt_centre,
			Double luminosity, Short no_planets_allowed, Double angle_in_radians_s, String star_color,
			String star_type, Double star_size) {
		super.setName(name);
		super.setDistance_clust_virt_centre(distance_clust_virt_centre);
		super.setLuminosity(luminosity);
		super.setNo_planets_allowed(no_planets_allowed == 1? true : false);
		super.setAngle_in_radians_s(angle_in_radians_s);
		super.setStar_color(star_color);
		super.setStar_type(star_type);
		super.setStar_size(star_size);
	}
	
	public void setName(String name){
		super.setName(name);
	}
	public String getName(){
		return super.getName();
	}
	public void setDistance_clust_virt_centre(Double distance_clust_virt_centre){
		super.setDistance_clust_virt_centre(distance_clust_virt_centre);
	}
	public Double getDistance_clust_virt_centre(){
		return super.getDistance_clust_virt_centre();
	}
	public void setLuminosity(Double luminosity){
		super.setLuminosity(luminosity);
	}
	public Double getLuminosity(){
		return super.getLuminosity();
	}
	public void setNo_planets_allowed(Boolean no_planets_allowed){
		super.setNo_planets_allowed(no_planets_allowed);
	}
	public Short getNo_planets_allowed(){
		return (super.getNo_planets_allowed());
	}
	public void setAngle_in_radians_s(Double angle_in_radians_s){
		super.setAngle_in_radians_s(angle_in_radians_s);
	}
	public Double getAngle_in_radians_s(){
		return super.getAngle_in_radians_s();
	}
	public void setStar_color(String star_color){
		super.setStar_color(star_color);
	}
	public String getStar_color(){
		return super.getStar_color();
	}
	public void setStar_type(String star_type){
		super.setStar_type(star_type);
	}
	public String getStar_type(){
		return super.getStar_type();
	}
	public void setStar_size(Double star_size){
		super.setStar_size(star_size);
	}
	public Double getStar_size(){
		return super.getStar_size();
	}
}
