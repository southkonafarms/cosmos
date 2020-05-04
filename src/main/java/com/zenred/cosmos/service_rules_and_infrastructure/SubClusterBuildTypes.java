package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.List;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.ClusterToStarDao;
/**
 * 
 * @author johnredden
 *
 * returns a list of sub cluster types that can be selected
 * separated by colons
 */
public class SubClusterBuildTypes {
	
	private static Logger logger = Logger.getLogger(SubClusterBuildTypes.class);
	
	public static String  getUsefullTypes(){
		
		String editedList = "";
		Boolean firstTime = true;
		ClusterToStarDao clusterToStarDao = new ClusterToStarDao();
		List<String> unEditedList = clusterToStarDao.readSubClusterDescription();
		for (String clusterType : unEditedList) {
			logger.info("Next clusterType:" + clusterType.trim());
			if(!clusterType.trim().equals("NONE")){
				if(!firstTime){
					editedList += ":";
				}
				editedList += clusterType;
				firstTime = false;
			}
		}
		// NONE will not map to a reasonable generated sub cluster
		return editedList;
		
	}

}
