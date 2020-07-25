package com.zenred.cosmos.domain;

public class BasicPlanetoid extends Planetoid {

	public BasicPlanetoid() {
		super();
	}

	public BasicPlanetoid( String planetoidName, Double radius,
			Double distanceToPrimary, Double degree, Double temperature, Double percentWater) {
		super.setPlanetoidName(planetoidName);
		super.setDegree(degree);
		super.setTemperature(temperature);
		super.setPercentWater(percentWater);
		super.setRadius(radius);
		super.setDistanceToPrimary(distanceToPrimary);
		}
	
	public String getPlanetoidName(){
		return super.getPlanetoidName();
	}
	
	public void setPlanetoidName(String planetoidName){
		super.setPlanetoidName(planetoidName);
	}
	
	public Double getDegree(){
		return super.getDegree();
	}
	
	public void setDegree(Double degree){
		super.setDegree(degree);
	}
	
	public Double getTemperature(){
		return super.getTemperature();
	}
	
	public void setTemperature(Double temperature){
		super.setTemperature(temperature);
	}

	public Double getPercentWater(){
		return super.getPercentWater();
	}
	
	public void setPercentWater(Double percentWater){
		super.setPercentWater(percentWater);
	}
	
	public Double getRadius(){
		return super.getRadius();
	}
	
	public void setRadius(Double radius){
		super.setRadius(radius);
	}
	
	public Double getDistanceToPrimary(){
		return super.getDistanceToPrimary();
	}
	
	public void setDistanceToPrimary(Double distanceToPrimary){
		super.setDistanceToPrimary(distanceToPrimary);
	}
}
