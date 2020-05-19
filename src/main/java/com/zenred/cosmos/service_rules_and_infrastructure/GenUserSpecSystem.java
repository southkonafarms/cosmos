package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.List;

import com.zenred.cosmos.domain.SystemDao;
import com.zenred.cosmos.domain.System;
import com.zenred.cosmos.domain.ClusterFactory;
import com.zenred.cosmos.domain.ClusterRep;

public class GenUserSpecSystem {

	
	public static void buildTheSystem(Double uDimension,
			Double vDimension, String spread, List<String> stars){
		SystemDao systemDao = new SystemDao();
		Integer iUDimension = uDimension.intValue();
		Integer iVDimension = vDimension.intValue();
		System system = systemDao.readSystemByUVCoordinates(iUDimension, iVDimension);
		ClusterRep clusterRep = GenCluster.genUserSpecCluster(spread, system);
		GenCluster.persist(clusterRep);
		ClusterFactory clusterFactory = ClusterFactory
				.fromString(clusterRep.getCluster_description());
	}
}
