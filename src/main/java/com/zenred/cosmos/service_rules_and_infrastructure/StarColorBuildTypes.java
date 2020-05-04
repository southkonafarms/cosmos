package com.zenred.cosmos.service_rules_and_infrastructure;

import java.util.List;

import org.apache.log4j.Logger;

import com.zenred.cosmos.domain.StarDao;

/**
 * 
 * @author johnredden
 *
 *         returns a list of star colors that can be selected separated by
 *         colons
 */
public class StarColorBuildTypes {

	private static Logger logger = Logger.getLogger(StarColorBuildTypes.class);

	public static String getUsefullTypes() {

		String editedList = "";
		Boolean firstTime = true;
		StarDao clusterToStarDao = new StarDao();
		List<String> starList = clusterToStarDao.readStarColors();
		for (String starColor : starList) {
			if (!firstTime) {
				editedList += ":";
			}
			editedList += starColor;
			firstTime = false;
		}
		return editedList;
	}
}
