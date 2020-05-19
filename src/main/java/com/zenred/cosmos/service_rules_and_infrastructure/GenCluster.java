package com.zenred.cosmos.service_rules_and_infrastructure;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.ClusterFactory;
import com.zenred.cosmos.domain.ClusterRep;
import com.zenred.cosmos.domain.ClusterRepDao;
import com.zenred.util.GenRandomRolls;
import com.zenred.cosmos.domain.System;

public class GenCluster {
	
	private static Logger logger = Logger.getLogger(GenCluster.class);
	private static ClusterRepDao clusterRepDao = new ClusterRepDao();

	/**
	 * 
	 * @param system
	 * @return cluster rep
	 */
	public static ClusterRep genCluster(com.zenred.cosmos.domain.System system) {
		Integer draw = GenRandomRolls.Instance().getD1000() - 1;

		if (draw < 0) {
			logger.error("CLUSTER DRAW LT ZERO:" + draw);
			draw = 0;
		} else if (draw > 999) {
			logger.error("CLUSTER DRAW GT MAX:" + draw);
			draw = 999;
		}
		ClusterFactory clusterFactory = ClusterFactory
				.fromString(ClusterFactory.evaluateChance(draw));
		String cluster_type = clusterFactory.name();
		String clusterName = "Cluster." + cluster_type + "."
				+ system.getUcoordinate().intValue() + "."
				+ system.getVcoordinate().intValue();
		ClusterRep clusterRep = new ClusterRep(null, system.getSystemId(),
				clusterName, 0.0, 0.0, cluster_type, null);

		return clusterRep;
	}
	
	/**
	 * user has already specified the cluster type
	 * 
	 * @param spread  cluster type
	 * @param system
	 * @return cluster rep 
	 */
	public static ClusterRep genUserSpecCluster(String spread, System system ){
		String internalName = ClusterFactory.getInternalName(spread);
		ClusterFactory clusterFactory = ClusterFactory
				.fromString(internalName);
		String cluster_type = clusterFactory.name();
		String clusterName = "Cluster." + cluster_type + "."
				+ system.getUcoordinate().intValue() + "."
				+ system.getVcoordinate().intValue();
		Integer draw = GenRandomRolls.Instance().getD1000();
		Double distanceVirtualCentre = new Double(draw);
		Double clusterVirtualAngle = new Double(Math.toRadians(GenRandomRolls.Instance()
				.getAngle()));
		ClusterRep clusterRep = new ClusterRep(null, system.getSystemId(),
				clusterName, distanceVirtualCentre, clusterVirtualAngle, cluster_type, null);
		return clusterRep;
	}
	
	/**
	 * 
	 * @param clusterRep
	 * @return cluster rep
	 */
	public static ClusterRep persist(ClusterRep clusterRep){
		return clusterRepDao.addClusterRep(clusterRep);
	}
}
