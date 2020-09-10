package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.AtmosphereObjectType;
import com.zenred.cosmos.domain.PlanetoidObjectType;
import com.zenred.cosmos.domain.StarObjectType;
import com.zenred.cosmos.domain.ClusterObjectType;
import com.zenred.cosmos.domain.ReadAFile;
import com.zenred.cosmos.domain.RenameObjectLoadType;
import com.zenred.cosmos.domain.SystemObjectType;
import com.zenred.cosmos.domain.XferObjectType;

public class BuildDBdataset {

	static private Logger logger = Logger.getLogger(BuildDBdataset.class);

	public BuildDBdataset() {
		super();
	}

	private static void parseHead(String[] masterArray) {
		for (int idex = 0; idex < masterArray.length;) {
			if (masterArray[idex].equals("")) {
				idex++;
				continue;
			}
			XferObjectType xferObjectType = XferObjectType.SYSTEM;
			logger.info("next parse head:" + masterArray[idex]);
			if (masterArray[idex].equals(xferObjectType.getName())) {
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				String subList = masterArray[idex];
				parseSystem(subList);
				++idex;
			}

			xferObjectType = XferObjectType.CLUSTER_REP;
			if (masterArray[idex].equals(xferObjectType.getName())) {
				logger.info("Next Parse:" + xferObjectType.getName());
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				String subList = masterArray[idex];
				parseClusterRep(subList);
				++idex;
			}

			xferObjectType = XferObjectType.CLUSTER_RENAMES;

			if (masterArray[idex].equals(xferObjectType.getName())){
				logger.info("Next Parse:" + masterArray[idex]);
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				if (masterArray[idex].equals("[")) {
					++idex;
				}
				idex = parseClusterRenames(masterArray, idex);
			}
			xferObjectType = XferObjectType.STAR_RENAMES;
			if (masterArray[idex].equals(xferObjectType.getName())){
				logger.info("Next Parse:" + masterArray[idex]);
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				if (masterArray[idex].equals("[")) {
					++idex;
				}
				idex = parseStarRenames(masterArray, idex);
			}

			xferObjectType = XferObjectType.PLANET_RENAMES;
			if (masterArray[idex].equals(xferObjectType.getName())){
				logger.info("Next Parse:" + masterArray[idex]);
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				if (masterArray[idex].equals("[")) {
					++idex;
				}
				idex = parsePlanetRenames(masterArray, idex);
			}

			xferObjectType = XferObjectType.MOON_RENAMES;
			if (masterArray[idex].equals(xferObjectType.getName())){
				logger.info("Next Parse:" + masterArray[idex]);
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				if (masterArray[idex].equals("[")) {
					++idex;
				}
				idex = parseMoonRenames(masterArray, idex);
			}


			xferObjectType = XferObjectType.STAR;
			if (masterArray[idex].equals(xferObjectType.getName())) {
				logger.info("Next Parse:" + xferObjectType.getName());
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				String subList = masterArray[idex];
				parseStar(subList);
				++idex;
			}
			
			if(idex == masterArray.length){
				break;
			}

			
			xferObjectType = XferObjectType.PLANET;
			if (masterArray[idex].equals(xferObjectType.getName())) {
				logger.info("Next Parse:" + xferObjectType.getName());
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				String subList = masterArray[idex];
				parsePlanet(subList, "Planet");
				++idex;
			}
			
			if(idex == masterArray.length){
				break;
			}

			
			xferObjectType = XferObjectType.PLANETATMOSPHERES;
			if (masterArray[idex].equals(xferObjectType.getName())) {
				logger.info("Next Parse:" + xferObjectType.getName());
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				if (masterArray[idex].equals("[")) {
					++idex;
				}
				String atmosphereArray = new String("");
				for (;;){
					String[] testArray = masterArray[idex].split(":");
					if(testArray[0].replace("\"", "").equals("chem_name")){
						atmosphereArray+=(masterArray[idex]);
						++idex;
					}
					else{
						break;
					}
					if(idex == masterArray.length){
						break;
					}
				}
				
				parseAtmospheres(atmosphereArray, "Planet");
				
			}
			
			if(idex == masterArray.length){
				break;
			}


			xferObjectType = XferObjectType.MOON;
			if (masterArray[idex].equals(xferObjectType.getName())) {
				logger.info("Next Parse:" + xferObjectType.getName());
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				String subList = masterArray[idex];
				parsePlanet(subList, "Moon");
				++idex;
			}

			if(idex == masterArray.length){
				break;
			}

			xferObjectType = XferObjectType.MOONATMOSPHERES;
			if (masterArray[idex].equals(xferObjectType.getName())) {
				logger.info("Next Parse:" + xferObjectType.getName());
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				if (masterArray[idex].equals("[")) {
					++idex;
				}
				String atmosphereArray = new String("");
				for (;;){
					String[] testArray = masterArray[idex].split(":");
					if(testArray[0].replace("\"", "").equals("chem_name")){
						atmosphereArray+=(masterArray[idex]);
						++idex;
					}
					else{
						break;
					}
					if(idex == masterArray.length){
						break;
					}
				}
				
				parseAtmospheres(atmosphereArray, "Moon");

			}
			if(idex == masterArray.length){
				break;
			}


		}
	}

	private static void parseSystem(String systemTarget) {
		String[] systemArray = systemTarget.split(",");
		for (int idex = 0; idex < systemArray.length;) {
			String[] subArray = systemArray[idex].split(":");

			SystemObjectType systemObjectType = SystemObjectType.DISTANCE_TO_GALAXY_CENTRE;
			String name = systemObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String distance = subArray[1];
				systemObjectType.storeValue(distance.replace("\"", ""));
			}

			idex += 1;
			subArray = systemArray[idex].split(":");
			systemObjectType = SystemObjectType.UCOORDINATE;
			name = systemObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String ucoordinate = subArray[1];
				systemObjectType.storeValue(ucoordinate.replace("\"", ""));
			}
			idex += 1;
			subArray = systemArray[idex].split(":");
			systemObjectType = SystemObjectType.VCOORDINATE;
			name = systemObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String vcoordinate = subArray[1];
				systemObjectType.storeValue(vcoordinate.replace("\"", ""));
			}
			idex += 1;
			subArray = systemArray[idex].split(":");
			systemObjectType = SystemObjectType.SYSTEMNAME;
			name = systemObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String systemName = subArray[1].replace("}", "");
				systemObjectType.storeValue(systemName.replace("\"", ""));
			}
			idex += 1;
		}
	}

	private static void parseClusterRep(String clusterRepTarget) {
		String[] clusterRepArray = clusterRepTarget.split(",");
		for (int idex = 0; idex < clusterRepArray.length;) {
			String[] subArray = clusterRepArray[idex].split(":");

			ClusterObjectType clusterObjectType = ClusterObjectType.CLUSTERNAME;
			String name = clusterObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String clusterName = subArray[1];
				clusterObjectType.storeValue(clusterName.replace("\"", ""));
			}
			idex += 1;
			subArray = clusterRepArray[idex].split(":");
			clusterObjectType = ClusterObjectType.DISTANCETOVIRTUALCENTRE;
			name = clusterObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String distance_sys_virt_centre = subArray[1];
				clusterObjectType.storeValue(distance_sys_virt_centre.replace("\"", ""));
			}
			idex += 1;
			subArray = clusterRepArray[idex].split(":");
			clusterObjectType = ClusterObjectType.ANGLEINRADIANS;
			name = clusterObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String angle_in_radians = subArray[1];
				clusterObjectType.storeValue(angle_in_radians.replace("\"", ""));
			}
			idex += 1;
			subArray = clusterRepArray[idex].split(":");
			clusterObjectType = ClusterObjectType.CLUSTERTYPE;
			name = clusterObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String cluster_type = subArray[1].replace("}", "");
				clusterObjectType.storeValue(cluster_type.replace("\"", ""));
			}
			idex += 1;

		}
	}
	/**
	 * common to all renames
	 * 
	 * @param detectRename
	 * @param idex
	 * @return
	 */
	private static int renameCommon(String[] detectRename, int idex){
		// do stuff
		for (int idex2 = 0; idex2 < detectRename.length;) {
			String[] subArray = detectRename[idex2].split(":");
			RenameObjectLoadType renameObjectLoadType = RenameObjectLoadType.RENAMEOBJECTTYPE;
			String name = renameObjectLoadType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				renameObjectLoadType.storeValue(subArray[1].replace("\"", ""));
			}
			idex2 += 1;
			subArray = detectRename[idex2].split(":");
			renameObjectLoadType = RenameObjectLoadType.GENERICNAME;
			name = renameObjectLoadType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				renameObjectLoadType.storeValue(subArray[1].replace("\"", ""));
			}
			idex2 += 1;
			subArray = detectRename[idex2].split(":");
			renameObjectLoadType = RenameObjectLoadType.RENAMENAME;
			name = renameObjectLoadType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				renameObjectLoadType.storeValue(subArray[1].replace("\"", ""));
			}
			idex2 += 1;
			subArray = detectRename[idex2].split(":");
			renameObjectLoadType = RenameObjectLoadType.RENAMECOUNT;
			name = renameObjectLoadType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				renameObjectLoadType.storeValue(subArray[1].replace("\"", "").replace("}", "").replace("]", ""));
			}
			idex2 += 1;
			idex += 1;
			
		}
		return idex;
		
	}

	private static int parseClusterRenames(String[] clusterRenamesTarget, int idex) {
		for (;;) {
			String clusterRenamesArray = clusterRenamesTarget[idex];
			String[] detectRename = clusterRenamesArray.split(",");
			String type = detectRename[0].replace("\"", "");
			if (!type.equals("renameObjectType:CLUSTER")) {
				return idex;
			}
			idex = renameCommon(detectRename, idex);
		}
	}
	private static int parseStarRenames(String[] starRenamesTarget, int idex) {
		for (;;) {
			String starRenamesArray = starRenamesTarget[idex];
			String[] detectRename = starRenamesArray.split(",");
			String type = detectRename[0].replace("\"", "");
			if (!type.equals("renameObjectType:STAR")) {
				return idex;
			}
			idex = renameCommon(detectRename, idex);
		}
	}
	private static int parsePlanetRenames(String[] planetRenamesTarget, int idex) {
		for (;;) {
			String planetRenamesArray = planetRenamesTarget[idex];
			String[] detectRename = planetRenamesArray.split(",");
			String type = detectRename[0].replace("\"", "");
			if (!type.equals("renameObjectType:PLANETOID")) {
				return idex;
			}
			idex = renameCommon(detectRename, idex);
		}
	}

	private static int parseMoonRenames(String[] moonRenamesTarget, int idex) {
		for (;;) {
			String moonRenamesArray = moonRenamesTarget[idex];
			String[] detectRename = moonRenamesArray.split(",");
			String type = detectRename[0].replace("\"", "");
			if (!type.equals("renameObjectType:PLANETOID")) {
				return idex;
			}
			idex = renameCommon(detectRename, idex);
		}
	}

	private static void parseStar(String starTarget) {
		String[] starArray = starTarget.split(",");
		for (int idex = 0; idex < starArray.length-1;) {
			String[] subArray = starArray[idex].split(":");

			StarObjectType starObjectType = StarObjectType.STAR_NAME;
			String name = starObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String starName = subArray[1];
				starObjectType.storeValue(starName);
			}
			idex += 1;
			subArray = starArray[idex].split(":");
			starObjectType = StarObjectType.DISTANCECLUSTVIRTUALCENTRE;
			name = starObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String distance_clust_virt_centre = subArray[1];
				starObjectType.storeValue(distance_clust_virt_centre);
			}
			idex += 1;
			subArray = starArray[idex].split(":");
			starObjectType = StarObjectType.LUMINOSITY;
			name = starObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String luminosity = subArray[1];
				starObjectType.storeValue(luminosity);
			}
			idex += 1;
			subArray = starArray[idex].split(":");
			starObjectType = StarObjectType.NOPLANETSALLOWED;
			name = starObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String no_planets_allowed = subArray[1];
				starObjectType.storeValue(no_planets_allowed);
			}
			idex += 1;
			subArray = starArray[idex].split(":");
			starObjectType = StarObjectType.ANGLEINRADIANS;
			name = starObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String angle_in_radians = subArray[1];
				starObjectType.storeValue(angle_in_radians);
			}
			idex += 1;
			subArray = starArray[idex].split(":");
			starObjectType = StarObjectType.STARCOLOR;
			name = starObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String star_color = subArray[1];
				starObjectType.storeValue(star_color);
			}
			idex += 1;
			subArray = starArray[idex].split(":");
			starObjectType = StarObjectType.STARSIZE;
			name = starObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String star_size = subArray[1];
				starObjectType.storeValue(star_size.replace("}", ""));
			}
			idex += 1;

		}
	}

	private static void parsePlanet(String planetTarget, String planarType){
		String [] planetArray = planetTarget.split(",");
		for(int idex=0; idex < planetArray.length;){
			String[] subArray = planetArray[idex].split(":");
			
			PlanetoidObjectType planetoidObjectType;
			
			
			planetoidObjectType = PlanetoidObjectType.PLANETOIDNAME;
			String name = planetoidObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String planetoidName = subArray[1];
				planetoidObjectType.storeValue(planetoidName+":"+planarType);
			}
			idex+=1;
			subArray = planetArray[idex].split(":");
			planetoidObjectType = PlanetoidObjectType.RADIUS;
			name = planetoidObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String radius = subArray[1];
				planetoidObjectType.storeValue(radius);
			}
			idex+=1;
			subArray = planetArray[idex].split(":");
			planetoidObjectType = PlanetoidObjectType.DISTANCETOPRIMARY;
			name = planetoidObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String distanceToPrimary = subArray[1];
				planetoidObjectType.storeValue(distanceToPrimary);
			}
			idex+=1;
			subArray = planetArray[idex].split(":");
			planetoidObjectType = PlanetoidObjectType.DEGREE;
			name = planetoidObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String degree = subArray[1];
				planetoidObjectType.storeValue(degree);
			}
			idex+=1;
			subArray = planetArray[idex].split(":");
			planetoidObjectType = PlanetoidObjectType.TEMPERATURE;
			name = planetoidObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String temperature = subArray[1];
				planetoidObjectType.storeValue(temperature);
			}
			idex+=1;
			subArray = planetArray[idex].split(":");
			planetoidObjectType = PlanetoidObjectType.PERCENTWATER;
			name = planetoidObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String percentWater = subArray[1];
				planetoidObjectType.storeValue(percentWater.replace("}", ""));
			}
			idex+=1;


		}
	}
	
	private static void parseAtmospheres(String planetTarget, String planarType){
		AtmosphereObjectType atmosphereObjectType;
		atmosphereObjectType = AtmosphereObjectType.CHEMNAME;
		atmosphereObjectType.setPlanarType(planarType);
		
		String [] basicList = planetTarget.replace("]", "").split(",");
		int idex2 = 0;
		for(; idex2 < basicList.length; ){
			String [] chemicalName = basicList[idex2].split(":");
			idex2+=1;
			String [] percentage = basicList[idex2].split(":");
			atmosphereObjectType.storeValue(chemicalName[1].replace("\"", ""), percentage[1].replace("}", ""));
			idex2+=1;
		}
		return;
	}
	
	public static String readAndParse(String fileName) {

		String jsonBlock = ReadAFile.readAsString(fileName);
		String[] basicArray = jsonBlock.split("\\{", 0);
		parseHead(basicArray);
		return "Okay";
	}

}
