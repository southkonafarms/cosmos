package com.zenred.cosmos.service_rules_and_infrastructure;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.SystemDao;
import com.zenred.cosmos.domain.System;
import com.zenred.cosmos.domain.BasicSystem;
import com.zenred.cosmos.domain.ClusterRep;
import com.zenred.cosmos.domain.BasicClusterRep;
import com.zenred.cosmos.domain.ClusterRepDao;
import com.google.gson.Gson; 


public class GenLoadableCompleteSystem {
	
	
	static private SystemDao systemDao = new SystemDao();
	static private ClusterRepDao clusterRepDao = new ClusterRepDao();
	
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
	    
	}

}
