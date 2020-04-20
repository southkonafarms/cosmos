package com.zenred.cosmos.domain;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

import org.apache.log4j.Logger;

public class ClusterToStarDaoTest {
	private static Logger logger = Logger.getLogger(ClusterToStarDaoTest.class);

	@Test
	public void test() {
		ClusterToStarDao clusterToStarDao = new ClusterToStarDao();
		List<String> subClusterDescriptors = clusterToStarDao.readSubClusterDescription();
		Assert.assertTrue("good", subClusterDescriptors.size() > 0);
		for (String string : subClusterDescriptors) {
			logger.info(string);
		}
	}

}
