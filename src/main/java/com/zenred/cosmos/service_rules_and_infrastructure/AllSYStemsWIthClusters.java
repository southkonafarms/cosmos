package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.List;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.SystemClusterSubSet;
import com.zenred.cosmos.domain.SystemDao;

public class AllSYStemsWIthClusters {
	
	private static SystemDao systemDao = new SystemDao();
	
	private static Logger logger = Logger.getLogger(AllSYStemsWIthClusters.class);

	public AllSYStemsWIthClusters() {
		super();
	}
	
	/**
	 *  u and v dims for systems with clusters 
	 * @return long string of u and v dimensions
	 */
	public static String allTheUsAndVs(){
		List<SystemClusterSubSet> list = systemDao.readSystemsWithClusters();
		String us_and_vs = new String();
		for(SystemClusterSubSet systemClusterSubSet : list){
			logger.info(systemClusterSubSet);
			Integer uDim = systemClusterSubSet.getUcoordinate().intValue();
			Integer vDim = systemClusterSubSet.getVcoordinate().intValue();
			us_and_vs += uDim.toString() + '_' + vDim.toString() + ":";
		}
		return us_and_vs;
	}

}
