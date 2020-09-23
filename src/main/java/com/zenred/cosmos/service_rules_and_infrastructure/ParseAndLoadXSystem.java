package com.zenred.cosmos.service_rules_and_infrastructure;

import com.zenred.cosmos.domain.DBxferList;

public class ParseAndLoadXSystem {

	public ParseAndLoadXSystem  ()  {
	}
	
	public static void doIt() throws Exception{
		BuildDBdataset.readAndParse("/tmp/ds.json");
		DBxferList.instance();
		DBxferList.traverseList();  //similar to a singleton
	}

}
