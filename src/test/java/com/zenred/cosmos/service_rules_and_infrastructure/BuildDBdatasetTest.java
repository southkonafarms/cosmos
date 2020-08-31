package com.zenred.cosmos.service_rules_and_infrastructure;

import static org.junit.Assert.*;

import org.junit.Test;

public class BuildDBdatasetTest {

	@Test
	public void test() {
		BuildDBdataset.readAndParse("/tmp/ds.json");
	}

}
