package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.SystemDao;
import com.zenred.cosmos.domain.System;
import com.zenred.cosmos.domain.Atmosphere;
import com.zenred.cosmos.domain.AtmosphereParts;
import com.zenred.cosmos.domain.ClusterFactory;
import com.zenred.cosmos.domain.ClusterRep;
import com.zenred.cosmos.domain.Planetoid;
import com.zenred.cosmos.domain.Star;

public class GenUserSpecSystem {

	private static Logger logger = Logger.getLogger(GenUserSpecSystem.class);
	
	public static void buildTheSystem(Double uDimension,
			Double vDimension, String spread, List<String> stars){
		SystemDao systemDao = new SystemDao();
		Integer iUDimension = uDimension.intValue();
		Integer iVDimension = vDimension.intValue();
		System system = systemDao.readSystemByUVCoordinates(iUDimension, iVDimension);
		ClusterRep clusterRep = GenCluster.genUserSpecCluster(spread, system);
		clusterRep = GenCluster.persist(clusterRep);
		ClusterFactory clusterFactory = ClusterFactory
				.fromString(clusterRep.getCluster_description());
		List<Star> starsInCluster = GenStar.persistUserSpecStars(clusterFactory, clusterRep, stars);
// D. R. Y. violation  make a separate method		
		for (Star star : starsInCluster) {
			logger.info("STAR:" + star);
			List<Planetoid> planetoids = GenPlanet.persistPlanetoids(star);
			if (!planetoids.isEmpty()) {
				GenAtmosphere genAtmosphere = new GenAtmosphere();
				List<Atmosphere> atmospheres = null;
				for (Planetoid planetoid : planetoids){
					atmospheres = genAtmosphere
						.persistAtmosphere(star, planetoid);
					for(Atmosphere atmosphere : atmospheres){
						if(atmosphere.getChem_name().equals(AtmosphereParts.Water.getText())){
							planetoid.setPercentWater(atmosphere.getPercentage());
						}
						logger.info("PLANETOID ATMOSPHERE_PART:"+ atmosphere);
					}
					List<Planetoid> moonsList = new ArrayList<Planetoid>();	// for debugging
					List<Planetoid>moons = GenMoon.persistPlanetoids(star, planetoid);
					if(!moons.isEmpty()){
						moonsList.addAll(moons);
					}
					List<Atmosphere> moonAtmospheres = null;
					for(Planetoid moon : moons){
						moonAtmospheres = genAtmosphere.persistAtmosphere(star, moon);
						for(Atmosphere 	atmosphere : moonAtmospheres){
							logger.info("MOON ATMOSPHERE_PART:"+ atmosphere);
						}
					}
				}
			}
		}
	}
}
