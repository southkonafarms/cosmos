package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.SystemDao;
import com.zenred.cosmos.domain.System;
import com.zenred.cosmos.domain.BasicSystem;
import com.zenred.cosmos.domain.ClusterRep;
import com.zenred.cosmos.domain.BasicClusterRep;
import com.zenred.cosmos.domain.BasicRename;
import com.zenred.cosmos.domain.BasicStar;
import com.zenred.cosmos.domain.ClusterRepDao;
import com.zenred.cosmos.domain.Rename;
import com.zenred.cosmos.domain.RenameDao;
import com.zenred.cosmos.domain.Star;
import com.zenred.cosmos.domain.StarDao;
import com.google.gson.Gson; 


public class GenLoadableCompleteSystem {
	
	
	static private SystemDao systemDao = new SystemDao();
	static private ClusterRepDao clusterRepDao = new ClusterRepDao();
	static private RenameDao renameDao = new RenameDao();
	static private StarDao starDao = new StarDao();
	
	static private Logger logger = Logger.getLogger(GenLoadableCompleteSystem.class);
	
	public static void GenFromUVCoordinates(Integer uCoordinate, Integer vCoordinate){
		System system = systemDao.readSystemByUVCoordinates(uCoordinate, vCoordinate);
		BasicSystem basicSystem = new BasicSystem(system.getDistance_to_galaxy_centre(), system.getUcoordinate(), 
				system.getVcoordinate(), system.getSystemName());
		Gson gson = new Gson();    
	    String jsonSystem = gson.toJson(basicSystem);
	    logger.info("JSON_system:" + jsonSystem );
	    ClusterRep clusterRep = clusterRepDao.readClusterRepBySystemId(system);
	    BasicClusterRep basicClusterRep = new BasicClusterRep(clusterRep.getClusterName(), clusterRep.getDistance_sys_virt_centre(),
	    		clusterRep.getAngle_in_radians(), clusterRep.getCluster_description());
	    String jsonClusterRep = gson.toJson(basicClusterRep);
	    logger.info("JSON_clusterRep:" + jsonClusterRep );
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
	    		List<Rename> starRenames = renameDao.fetchRenamesForGenericName(star.getName());
	    		if(!starRenames.isEmpty()){
	    	    	List<BasicRename> basicRenames = new ArrayList<BasicRename>();
	    	    	for(Rename rename : renames){
	    	    		BasicRename basicRename = new BasicRename(rename.getRenameObjectType(), rename.getGenericName(), 
	    	    				rename.getRenameName(), rename.getRenameCount());
	    	    		basicRenames.add(basicRename);
	    	    	}
	    	    	String jsonClusterRenames = gson.toJson(basicRenames);
	    	    	logger.info("JSON_star_renames:" + jsonClusterRenames );
	    		}
	    	}
	    }
	    
	}

}
