package com.zenred.cosmos.service_rules_and_infrastructure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


import com.zenred.cosmos.domain.BasicSystem;
import com.zenred.cosmos.domain.BasicClusterRep;
import com.zenred.cosmos.domain.BasicRename;
import com.zenred.cosmos.domain.BasicStar;
import com.zenred.cosmos.domain.BasicPlanetoid;
import com.zenred.cosmos.domain.BasicAtmosphere;

import com.zenred.cosmos.domain.SystemDao;
import com.zenred.cosmos.domain.UnifiedPlanetoidI;
import com.zenred.cosmos.domain.System;
import com.zenred.cosmos.domain.ClusterRep;
import com.zenred.cosmos.domain.ClusterRepDao;
import com.zenred.cosmos.domain.ConfigurationDao;
import com.zenred.cosmos.domain.PlanetoidDao;
import com.zenred.cosmos.domain.Rename;
import com.zenred.cosmos.domain.RenameDao;
import com.zenred.cosmos.domain.Star;
import com.zenred.cosmos.domain.StarDao;
import com.zenred.cosmos.domain.Atmosphere;
import com.zenred.cosmos.domain.AtmosphereDao;
import com.google.gson.Gson; 


public class GenLoadableCompleteSystem {
	
	
	private static final String BasicPlanetoid = null;
	static private SystemDao systemDao = new SystemDao();
	static private ClusterRepDao clusterRepDao = new ClusterRepDao();
	static private RenameDao renameDao = new RenameDao();
	static private StarDao starDao = new StarDao();
	static private PlanetoidDao planetoidDao = new PlanetoidDao();
	static private AtmosphereDao atmosphereDao = new AtmosphereDao();
	
	static private Logger logger = Logger.getLogger(GenLoadableCompleteSystem.class);
	
	static private String fullPathToYourWebappRoot = com.zenred.cosmos.domain.Configuration.readReportRoot();
	static private String reportDirectory = new ConfigurationDao().reportDirectory();
	static private String relativePath;
	static private String jsonReportFile;
	static private StringBuilder fileContents;
	/**
	 * where the JSON formatted report goes
	 * String building is also initialized
	 */
	private static void initReportDirectory(Integer uCoordinate, Integer vCoordinate){
		relativePath = reportDirectory + File.separator + uCoordinate + ":" + vCoordinate;
		jsonReportFile = fullPathToYourWebappRoot + File.separator + relativePath;
		fileContents = new StringBuilder();
	}
	/**
	 * adds one JSON formated line
	 * @param line
	 * @param header
	 */
	private static void buildNextLine(String line, String header){
		fileContents.append("{").append(header).append("{").append(line).append("}}");
	}
	/**
	 * only invoked once per UV run
	 */
	private static void writeTheFile(){
		File file = new File(jsonReportFile);
		try {
			FileUtils.writeStringToFile(file, fileContents.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new RuntimeException("File IO Error:" + ioe.getMessage());
		}
		logger.info("WROTE file:" + file.getAbsolutePath());
	}
	
	public static void GenFromUVCoordinates(Integer uCoordinate, Integer vCoordinate){
		initReportDirectory(uCoordinate, vCoordinate);
		System system = systemDao.readSystemByUVCoordinates(uCoordinate, vCoordinate);
		BasicSystem basicSystem = new BasicSystem(system.getDistance_to_galaxy_centre(), system.getUcoordinate(), 
				system.getVcoordinate(), system.getSystemName());
		Gson gson = new Gson();    
	    String jsonSystem = gson.toJson(basicSystem);
	    logger.info("JSON_system:" + jsonSystem );
	    buildNextLine(jsonSystem, "JSON_system");
	    ClusterRep clusterRep = clusterRepDao.readClusterRepBySystemId(system);
	    BasicClusterRep basicClusterRep = new BasicClusterRep(clusterRep.getClusterName(), clusterRep.getDistance_sys_virt_centre(),
	    		clusterRep.getAngle_in_radians(), clusterRep.getCluster_description());
	    String jsonClusterRep = gson.toJson(basicClusterRep);
	    logger.info("JSON_clusterRep:" + jsonClusterRep );
	    buildNextLine(jsonClusterRep, "JSON_clusterRep");
	    List<Rename> renames =renameDao.fetchRenamesForGenericName(clusterRep.getClusterName());
	    if(!renames.isEmpty()){
	    	List<BasicRename> basicRenames = new ArrayList<BasicRename>();
	    	for(Rename rename : renames){
	    		BasicRename basicRename = new BasicRename(rename.getRenameObjectType(), rename.getGenericName(), 
	    				rename.getRenameName(), rename.getRenameCount());
	    		basicRenames.add(basicRename);
	    	}
	    	String jsonClusterRenames = gson.toJson(basicRenames);
	    	logger.info("JSON_clustrer_renames:" + jsonClusterRenames );
	    	buildNextLine(jsonClusterRenames, "JSON_clustrer_renames");
	    }
	    List<Star> stars = starDao.readStarsInCluster(clusterRep);
	    if(!stars.isEmpty()){
	    	for(Star star: stars){
	    		/*
	    		 * BasicStar( String name, Double distance_clust_virt_centre,
			Double luminosity, Boolean no_planets_allowed, Double angle_in_radians_s, String star_color,
			String star_type, Double star_size) {
	    		 */
	    		BasicStar basicStar = new BasicStar(star.getName(), star.getDistance_clust_virt_centre(), star.getLuminosity(),
	    				star.getNo_planets_allowed(), star.getAngle_in_radians_s(), star.getStar_color(), star.getStar_type(), 
	    				star.getStar_size());
	    		String jsonStarRep = gson.toJson(basicStar);
	    		logger.info("JSON_star:" + jsonStarRep );
	    		buildNextLine(jsonStarRep, "JSON_star");
	    		List<Rename> starRenames = renameDao.fetchRenamesForGenericName(star.getName());
	    		if(!starRenames.isEmpty()){
	    	    	List<BasicRename> basicRenames = new ArrayList<BasicRename>();
	    	    	for(Rename rename : starRenames){
	    	    		BasicRename basicRename = new BasicRename(rename.getRenameObjectType(), rename.getGenericName(), 
	    	    				rename.getRenameName(), rename.getRenameCount());
	    	    		basicRenames.add(basicRename);
	    	    	}
	    	    	String jsonStarRenames = gson.toJson(basicRenames);
	    	    	logger.info("JSON_star_renames:" + jsonStarRenames );
	    	    	buildNextLine(jsonStarRenames, "JSON_star_renames");
	    		}
	    		List<UnifiedPlanetoidI> plentoidIList = planetoidDao.readPlanetoidsAroundStar(star);
	    		for(UnifiedPlanetoidI unifiedPlanetoidI: plentoidIList){
	    			BasicPlanetoid basicPlanetoid = new BasicPlanetoid(unifiedPlanetoidI.getPlanetoid().getPlanetoidName(),
	    					unifiedPlanetoidI.getPlanetoid().getDegree(), 
	    					unifiedPlanetoidI.getPlanetoid().getTemperature(),
	    					unifiedPlanetoidI.getPlanetoid().getPercentWater(),
	    					unifiedPlanetoidI.getPlanetoid().getRadius(),
	    					unifiedPlanetoidI.getPlanetoid().getDistanceToPrimary());
	    			String jsonPlanetRep = gson.toJson(basicPlanetoid);
	    			logger.info("JSON_planet:" + jsonPlanetRep);
	    			buildNextLine(jsonPlanetRep, "JSON_planet");
		    		List<Rename> planetRenames = renameDao.fetchRenamesForGenericName(basicPlanetoid.getPlanetoidName());
		    		if(!planetRenames.isEmpty()){
		    			List<BasicRename> basicRenames = new ArrayList<BasicRename>();
		    	    	for(Rename rename : planetRenames){
		    	    		BasicRename basicRename = new BasicRename(rename.getRenameObjectType(), rename.getGenericName(), 
		    	    				rename.getRenameName(), rename.getRenameCount());
		    	    		basicRenames.add(basicRename);
		    	    	}
		    	    	String jsonPlanetRenames = gson.toJson(basicRenames);
		    	    	logger.info("JSON_planet_renames:" + jsonPlanetRenames );
		    	    	buildNextLine(jsonPlanetRenames, "JSON_planet_renames");
		    		}
		    		List<Atmosphere> planetAtmospheres = 
		    				atmosphereDao.readAtmosphereAroundPlanet(unifiedPlanetoidI.getPlanetoid());
		    		List<BasicAtmosphere> basicPlanetAtmospheres = new ArrayList<BasicAtmosphere>();
		    		for(Atmosphere atmosphere : planetAtmospheres){
		    			BasicAtmosphere basicAtmosphere = new BasicAtmosphere(atmosphere.getChem_name(), atmosphere.getPercentage());
		    			basicPlanetAtmospheres.add(basicAtmosphere);
		    		}
		    		String jsonPlanetAtmospheres = gson.toJson(basicPlanetAtmospheres);
		    		logger.info("JSON_planet_atmospheres:" + jsonPlanetAtmospheres);
		    		buildNextLine(jsonPlanetAtmospheres, "JSON_planet_atmospheres");
		    		List<UnifiedPlanetoidI> moonIList = planetoidDao.readMoonsAroundPlanetoid(unifiedPlanetoidI.getPlanetoid());
		    		for(UnifiedPlanetoidI unifiedMoonI: moonIList){
		    			BasicPlanetoid basicMoon = new BasicPlanetoid(unifiedMoonI.getPlanetoid().getPlanetoidName(),
		    					unifiedMoonI.getPlanetoid().getDegree(), 
		    					unifiedMoonI.getPlanetoid().getTemperature(),
		    					unifiedMoonI.getPlanetoid().getPercentWater(),
		    					unifiedMoonI.getPlanetoid().getRadius(),
		    					unifiedMoonI.getPlanetoid().getDistanceToPrimary());
		    			String jsonMoonRep = gson.toJson(basicMoon);
		    			logger.info("JSON_moon:" + jsonMoonRep);
		    			buildNextLine(jsonMoonRep, "JSON_moon");
			    		List<Rename> moonRenames = renameDao.fetchRenamesForGenericName(basicMoon.getPlanetoidName());
			    		if(!moonRenames.isEmpty()){
			    			List<BasicRename> basicRenames = new ArrayList<BasicRename>();
			    	    	for(Rename rename : moonRenames){
			    	    		BasicRename basicRename = new BasicRename(rename.getRenameObjectType(), rename.getGenericName(), 
			    	    				rename.getRenameName(), rename.getRenameCount());
			    	    		basicRenames.add(basicRename);
			    	    	}
			    	    	String jsonMoonRenames = gson.toJson(basicRenames);
			    	    	logger.info("JSON_moon_renames:" + jsonMoonRenames );
			    	    	buildNextLine(jsonMoonRenames, "JSON_moon_renames");
			    		}
			    		List<Atmosphere> moonAtmospheres = 
			    				atmosphereDao.readAtmosphereAroundPlanet(unifiedMoonI.getPlanetoid());
			    		List<BasicAtmosphere> basicMoonAtmospheres = new ArrayList<BasicAtmosphere>();
			    		for(Atmosphere atmosphere : moonAtmospheres){
			    			BasicAtmosphere basicAtmosphere = new BasicAtmosphere(atmosphere.getChem_name(), atmosphere.getPercentage());
			    			basicMoonAtmospheres.add(basicAtmosphere);
			    		}
			    		String jsonMoonAtmospheres = gson.toJson(basicMoonAtmospheres);
			    		logger.info("JSON_moon_atmospheres:" + jsonMoonAtmospheres);
			    		buildNextLine(jsonMoonAtmospheres, "JSON_moon_atmospheres");
		    		}
	    		}
	    	}
	    }
	   // the end
	    writeTheFile();
	}

}
