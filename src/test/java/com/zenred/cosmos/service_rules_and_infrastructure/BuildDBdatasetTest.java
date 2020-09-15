package com.zenred.cosmos.service_rules_and_infrastructure;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.zenred.cosmos.domain.DBxferList;

public class BuildDBdatasetTest {
	
	static private Logger logger = Logger.getLogger(BuildDBdatasetTest.class);

	@Test
	public void test() {
		BuildDBdataset.readAndParse("/tmp/ds.json");
		try{
			DBxferList.instance().traverseList();
		}
		catch (Exception exception){
			logger.error(exception.getMessage());
		}
	}

}
