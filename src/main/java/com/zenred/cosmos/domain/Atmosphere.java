package com.zenred.cosmos.domain;

import java.util.HashMap;
import java.util.Map;

public class Atmosphere {
	
	
	
	public Atmosphere() {
		super();
	}
	public Atmosphere(Integer atmosphereId, Integer planetoidId,
			String chem_name, Double percentage, String datestamp) {
		super();
		this.atmosphereId = atmosphereId;
		this.planetoidId = planetoidId;
		this.chem_name = chem_name;
		this.percentage = percentage;
		this.datestamp = datestamp;
	}

	private Integer atmosphereId;
	private Integer planetoidId;
	private String chem_name;
	private Double percentage;
	private String datestamp;
	
	
	public Integer getAtmosphereId() {
		return atmosphereId;
	}
	public void setAtmosphereId(Integer atmosphereId) {
		this.atmosphereId = atmosphereId;
	}
	public Integer getPlanetoidId() {
		return planetoidId;
	}
	public void setPlanetoidId(Integer planetoidId) {
		this.planetoidId = planetoidId;
	}
	public String getChem_name() {
		return chem_name;
	}
	public void setChem_name(String chem_name) {
		this.chem_name = chem_name;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public String getDatestamp() {
		return datestamp;
	}
	public void setDatestamp(String datestamp) {
		this.datestamp = datestamp;
	}
	@Override
	public String toString() {
		return "Atmosphere [atmosphereId=" + atmosphereId + ", planetoidId="
				+ planetoidId + ", chem_name=" + chem_name + ", percentage="
				+ percentage + ", datestamp=" + datestamp + "]";
	}
	
	
	/**
	 * 
	 * @param planetoidId
	 * @param chem_name
	 * @param percentage
	 * @return map of atmosphere attributes
	 */
	public static Map<String, Object> getAtmosphereMap(Integer planetoidId, String chem_name, Double percentage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AtmosphereDao.PLANETOID_ID, planetoidId);
		map.put(AtmosphereDao.CHEM_NAME, chem_name);
		map.put(AtmosphereDao.PERCENTAGE, percentage);
		return map;
	}
	
	/**
	 * 
	 * @return attribute array
	 */
	public static String[] csvAtmosphere(){
		return new String[] { AtmosphereDao.PLANETOID_ID,AtmosphereDao.CHEM_NAME, AtmosphereDao.PERCENTAGE};
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atmosphereId == null) ? 0 : atmosphereId.hashCode());
		result = prime * result
				+ ((chem_name == null) ? 0 : chem_name.hashCode());
		result = prime * result
				+ ((datestamp == null) ? 0 : datestamp.hashCode());
		result = prime * result
				+ ((percentage == null) ? 0 : percentage.hashCode());
		result = prime * result
				+ ((planetoidId == null) ? 0 : planetoidId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atmosphere other = (Atmosphere) obj;
		if (atmosphereId == null) {
			if (other.atmosphereId != null)
				return false;
		} else if (!atmosphereId.equals(other.atmosphereId))
			return false;
		if (chem_name == null) {
			if (other.chem_name != null)
				return false;
		} else if (!chem_name.equals(other.chem_name))
			return false;
		if (datestamp == null) {
			if (other.datestamp != null)
				return false;
		} else if (!datestamp.equals(other.datestamp))
			return false;
		if (percentage == null) {
			if (other.percentage != null)
				return false;
		} else if (!percentage.equals(other.percentage))
			return false;
		if (planetoidId == null) {
			if (other.planetoidId != null)
				return false;
		} else if (!planetoidId.equals(other.planetoidId))
			return false;
		return true;
	}

}
