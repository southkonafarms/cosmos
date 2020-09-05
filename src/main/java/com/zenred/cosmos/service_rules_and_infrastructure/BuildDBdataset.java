package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

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
		for (int idex = 0; idex < masterArray.length; ) {
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
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				String subList = masterArray[idex];
				parseClusterRep(subList);
				++idex;
			}
			
			xferObjectType = XferObjectType.CLUSTER_RENAMES;
			if (masterArray[idex].equals(xferObjectType.getName())) {
				++idex;
				if (masterArray[idex].equals("")) {
					++idex;
				}
				if(masterArray[idex].equals("[")){
					++idex;
				}
				idex = parseClusterRenames(masterArray, idex);
			}
			++idex;
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
			
			ClusterObjectType clusterObjectType = ClusterObjectType.CLUSTERNAME;
			String name = clusterObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String clusterName = subArray[1];
				clusterObjectType.storeValue(clusterName.replace("\"", ""));
			}
			idex +=1;
			clusterObjectType = ClusterObjectType.DISTANCETOVIRTUALCENTRE;
			name = clusterObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String distance_sys_virt_centre = subArray[1];
				clusterObjectType.storeValue(distance_sys_virt_centre.replace("\"", ""));
			}
			idex +=1;
			clusterObjectType = ClusterObjectType.ANGLEINRADIANS;
			name = clusterObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String angle_in_radians = subArray[1];
				clusterObjectType.storeValue(angle_in_radians.replace("\"", ""));
			}
			idex +=1;
			clusterObjectType = ClusterObjectType.CLUSTERTYPE;
			name = clusterObjectType.getName();
			if (subArray[0].replace("\"", "").equals(name)) {
				String cluster_type = subArray[1].replace("}", "");
				clusterObjectType.storeValue(cluster_type.replace("\"", ""));
			}
			idex +=1;

		}
	}
	
	private static int parseClusterRenames(String[] clusterRenamesTarget, int idex) {
		for (;;) {
			String clusterRenamesArray = clusterRenamesTarget[idex];
			String[] detectRename = clusterRenamesArray.split(",");
			String type = detectRename[0].replace("\"", "");
			if (!type.equals("renameObjectType:CLUSTER")) {
				break;
			}
			// do stuff
			for (int idex2 = 0; idex2 < detectRename.length;){
				String[] subArray = detectRename[idex2].split(":");
				RenameObjectLoadType renameObjectLoadType = RenameObjectLoadType.RENAMEOBJECTTYPE;
				String name = renameObjectLoadType.getName();
				if (subArray[0].replace("\"", "").equals(name)) {
					renameObjectLoadType.storeValue(subArray[1].replace("\"", ""));
				}
				idex2+=1;
				subArray = detectRename[idex2].split(":");
				renameObjectLoadType = RenameObjectLoadType.GENERICNAME;
				name = renameObjectLoadType.getName();
				if (subArray[0].replace("\"", "").equals(name)) {
					renameObjectLoadType.storeValue(subArray[1].replace("\"", ""));
				}
				idex2+=1;
				subArray = detectRename[idex2].split(":");
				renameObjectLoadType = RenameObjectLoadType.RENAMENAME;
				name = renameObjectLoadType.getName();
				if (subArray[0].replace("\"", "").equals(name)) {
					renameObjectLoadType.storeValue(subArray[1].replace("\"", ""));
				}
				idex2+=1;
				subArray = detectRename[idex2].split(":");
				renameObjectLoadType = RenameObjectLoadType.RENAMECOUNT;
				name = renameObjectLoadType.getName();
				if (subArray[0].replace("\"", "").equals(name)) {
					renameObjectLoadType.storeValue(subArray[1].replace("\"", "").replace("}", "").replace("]",""));
				}
				idex2+=1;
				
			}
			
			++idex;
		}
		return idex;
	}

	public static String readAndParse(String fileName) {

		String jsonBlock = ReadAFile.readAsString(fileName);
		String[] basicArray = jsonBlock.split("\\{", 0);
		parseHead(basicArray);
		return "Okay";
	}

}
