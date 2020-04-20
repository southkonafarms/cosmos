package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.zenred.cosmos.domain.ClusterToStarDao;
/**
 * 
 * @author johnredden
 *
 * returns a list of sub cluster types that can be selected
 */
public class SubClusterBuildTypes {
	
	List<String>  getUsefullTypes(){
		
		List<String> editedList = new ArrayList<String>();
		ClusterToStarDao clusterToStarDao = new ClusterToStarDao();
		List<String> unEditedList = clusterToStarDao.readSubClusterDescription();
		for (String clusterType : unEditedList) {
			if(clusterType != "NONE"){
				editedList.add(clusterType);
			}
		}
		// NONE will not map to a reasonable generated sub cluster
		return editedList;
		
	}

}
