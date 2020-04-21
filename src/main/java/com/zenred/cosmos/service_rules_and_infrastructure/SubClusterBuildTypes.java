package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.List;

import com.zenred.cosmos.domain.ClusterToStarDao;
/**
 * 
 * @author johnredden
 *
 * returns a list of sub cluster types that can be selected
 * separated by colons
 */
public class SubClusterBuildTypes {
	
	public static String  getUsefullTypes(){
		
		String editedList = "";
		Boolean firstTime = true;
		ClusterToStarDao clusterToStarDao = new ClusterToStarDao();
		List<String> unEditedList = clusterToStarDao.readSubClusterDescription();
		for (String clusterType : unEditedList) {
			if(clusterType != "NONE"){
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
