package com.zenred.cosmos.domain;

public class UV_Instance {
	
	public UV_Instance(){
		super();
	}
	
	public UV_Instance(Integer uDimension, Integer vDimension) {
		super();
		this.uDimension = uDimension;
		this.vDimension = vDimension;
	}
	private Integer uDimension;
	private Integer vDimension;
	
	public Integer getuDimension() {
		return uDimension;
	}
	public void setuDimension(Integer uDimension) {
		this.uDimension = uDimension;
	}
	public Integer getvDimension() {
		return vDimension;
	}
	public void setvDimension(Integer vDimension) {
		this.vDimension = vDimension;
	}
	@Override
	public String toString() {
		return "UV_Instance [uDimension=" + uDimension + ", vDimension=" + vDimension + "]";
	}

}
