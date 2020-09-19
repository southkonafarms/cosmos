package com.zenred.cosmos.domain;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import java.util.Iterator;


class Head{
	static String head = "Head";
}


public class DBxferList {
	static private Logger logger = Logger.getLogger(DBxferList.class);
	
	private static LinkedList<DBxferObject>  xferList = new LinkedList<DBxferObject>();
	private static DBxferList DBxferListthis = new DBxferList();

	public DBxferList() {
	}
	
	public void initList(){
		DBxferObject dbxferObject = new DBxferObject();
		Head head = new Head();
		dbxferObject.setType(head);
		xferList.add(dbxferObject);
	}
	
	public void addDomainObject(DBxferObject domainObject){
		xferList.add(domainObject);
	}
	
	public static DBxferList instance(){
		return DBxferListthis;
	}
	
	public static void traverseList() throws Exception{
		
		Iterator<DBxferObject> iterator= xferList.iterator();
		SystemDao systemDao = new SystemDao();
		Integer currentSystemId = null;
		ClusterRepDao clusterRepDao = new ClusterRepDao();
		Integer currentClusterRepId = null;
		RenameDao renameDao = new RenameDao();
		StarDao starDao = new StarDao();
		Integer currentStarId = null;
		PlanetoidDao planetoidDao = new PlanetoidDao();
		AtmosphereDao atmosphereDao = new AtmosphereDao();
		
		while(iterator.hasNext()){
			DBxferObject dbxferObject = iterator.next();
			
			logger.info("Type:" + dbxferObject.getType().getClass().getCanonicalName());
			
			if(dbxferObject.getType().getClass().getCanonicalName().equals("com.zenred.cosmos.domain.System")){
				System system = (System)dbxferObject.getType();
				logger.info("System:" + system.getSystemName());
				if(systemDao.doesSystemExist(system.getUcoordinate(), system.getVcoordinate())){
					throw new Exception("system " + system.getSystemName() + " already exists!");
				}
				System newSystem = systemDao.addSystem(system);
				currentSystemId = newSystem.getSystemId();
			}
			
			if(dbxferObject.getType().getClass().getCanonicalName().equals("com.zenred.cosmos.domain.ClusterRep")){
				ClusterRep clusterRep = (ClusterRep)dbxferObject.getType();
				logger.info("ClusterRep:" + clusterRep.getClusterName());
				clusterRep.setSystemId(currentSystemId);
				ClusterRep newClusterRep = clusterRepDao.addClusterRep(clusterRep);
				currentClusterRepId = newClusterRep.getClusterRepId();
			}
			
			if(dbxferObject.getType().getClass().getCanonicalName().equals("com.zenred.cosmos.domain.Rename")){
				Rename rename = (Rename)dbxferObject.getType();
				RenameObjectType renameObjectType = rename.renameObjectType;
				if(renameObjectType.getName().equals("CLUSTER")){
					logger.info("Rename:"+ rename.renameObjectType.getName());
					rename.setRenameId(currentClusterRepId);
					renameDao.addNewName(renameObjectType, currentClusterRepId, rename.getRenameName(), rename.getGenericName());
				}
			}
			Star star = null;
			if(dbxferObject.getType().getClass().getCanonicalName().equals("com.zenred.cosmos.domain.Star")){
				star = (Star)dbxferObject.getType();
				logger.info("Star:" + star.getName());
				star.setClusterToStarId(currentClusterRepId);
				Star newStar = starDao.addStarToNonSubCluster(star, currentClusterRepId);
				currentStarId = newStar.getStarId();
			}
			
			if(dbxferObject.getType().getClass().getCanonicalName().equals("com.zenred.cosmos.domain.Rename")){
				Rename rename = (Rename)dbxferObject.getType();
				RenameObjectType renameObjectType = rename.renameObjectType;
				if(renameObjectType.getName().equals("STAR")){
					logger.info("Rename:"+ rename.renameObjectType.getName());
					rename.setRenameId(currentStarId);
					renameDao.addNewName(renameObjectType, currentStarId, rename.getRenameName(), rename.getGenericName());
				}
			}
			
			Planetoid lastPlanet = null;
			Planetoid lastMoon = null;
			Integer currentPlanetoidId = null;
			if(dbxferObject.getType().getClass().getCanonicalName().equals("com.zenred.cosmos.domain.Planetoid")){
				Planetoid planetoid = (Planetoid)dbxferObject.getType();
				String [] planetoidAndType = planetoid.getPlanetoidName().split(":");
				if(planetoidAndType[1].equals("Planet")){
					planetoid.setPlanetoidName(planetoidAndType[0]);
					UnifiedPlanetoidI unifiedPlanetoidI = planetoidDao.addStarPlanetoid(planetoid, star);
					lastPlanet = planetoid;
					currentPlanetoidId = unifiedPlanetoidI.getPlanetoid().getPlanetoidId();
				}
				else if(planetoidAndType[1].equals("Moon")){
					planetoid.setPlanetoidName(planetoidAndType[0]);
					UnifiedPlanetoidI unifiedPlanetoidI = planetoidDao.addPlanetoidPlanetoid(lastPlanet, planetoid);
					lastMoon = planetoid;
					currentPlanetoidId = unifiedPlanetoidI.getPlanetoid().getPlanetoidId();
				}
				else{
					throw new Exception("What? " + planetoidAndType[1]);
				}
			}
			
			if(dbxferObject.getType().getClass().getCanonicalName().equals("com.zenred.cosmos.domain.Rename")){
				Rename rename = (Rename)dbxferObject.getType();
				RenameObjectType renameObjectType = rename.renameObjectType;
				if(renameObjectType.getName().equals("PLANETOID")){
					logger.info("Rename:"+ rename.renameObjectType.getName());
					rename.setRenameId(currentPlanetoidId );
					renameDao.addNewName(renameObjectType, currentPlanetoidId , rename.getRenameName(), rename.getGenericName());
				}
			}
			
			if(dbxferObject.getType().getClass().getCanonicalName().equals("com.zenred.cosmos.domain.Atmosphere")){
				Atmosphere atmosphere = (Atmosphere)dbxferObject.getType();
				logger.info("Atmosphere:" + atmosphere.getChem_name());
				atmosphere.setPlanetoidId(currentPlanetoidId);
				atmosphereDao.addAtmosphere(atmosphere);
			}
		}
	}
}
