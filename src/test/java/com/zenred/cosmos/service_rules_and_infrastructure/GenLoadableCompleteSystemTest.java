package com.zenred.cosmos.service_rules_and_infrastructure;

import static org.junit.Assert.*;

import org.junit.Test;

public class GenLoadableCompleteSystemTest {

	@Test
	public void test() {
		GenLoadableCompleteSystem.GenFromUVCoordinates(60606, 27912);
		GenLoadableCompleteSystem.GenFromUVCoordinates(76974, 79975);
		GenLoadableCompleteSystem.GenFromUVCoordinates(62549, 79644);
		GenLoadableCompleteSystem.GenFromUVCoordinates(37819, 32512);
	}

}
