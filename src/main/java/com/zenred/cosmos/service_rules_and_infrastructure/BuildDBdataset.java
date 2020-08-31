package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.ReadAFile;
import com.zenred.cosmos.domain.SystemObjectType;
import com.zenred.cosmos.domain.XferObjectType;

public class BuildDBdataset {

	static private Logger logger = Logger.getLogger(BuildDBdataset.class);

	public BuildDBdataset() {
		super();
	}

	private static void parseHead(String[] masterArray) {
		for (int idex = 0; idex < masterArray.length; idex++) {
			if (masterArray[idex].equals("")) {
				continue;
			}
			XferObjectType xferObjectType = XferObjectType.SYSTEM;
			logger.info("next parse head:" + masterArray[idex]);
			logger.info("target:" + xferObjectType.getName());
			if (masterArray[idex++].equals(xferObjectType.getName())) {
				if (masterArray[idex].equals("")) {
					++idex;
				}
				String subList = masterArray[idex];
				parseSystem(subList);
			}

			xferObjectType = XferObjectType.CLUSTER_REP;
			if (masterArray[idex++].equals(xferObjectType.getName())) {
				if (masterArray[idex].equals("")) {
					++idex;
				}
				String subList = masterArray[idex];
				parseClusterRep(subList);
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
	
	private static void parseClusterRep(String clusterRepTarget){
		String[] clusterRepArray = clusterRepTarget.split(",");
		for (int idex = 0; idex < clusterRepArray.length;) {
			String[] subArray = clusterRepArray[idex].split(":");
		}
	}

	public static String readAndParse(String fileName) {

		String jsonBlock = ReadAFile.readAsString(fileName);
		String[] basicArray = jsonBlock.split("\\{", 0);
		parseHead(basicArray);
		return "Okay";
	}

}
