package com.zenred.cosmos.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// import java.util.Set;

// import org.apache.log4j.Logger;

import com.zenred.johntredden.domain.AbstractJDBCDao;

public class ClusterToStarDao extends AbstractJDBCDao {
	
	// private static Logger logger = Logger.getLogger(ClusterToStarDao.class);
	
	public static String  CLUSTER_TO_STAR = "ClusterToStar";
	public static String  SUB_CLUSTER_DESCRIPTION = "sub_cluster_description" ;
	
	public static String readDistinctSubClusterDescriptionSql = "SELECT DISTINCT " + SUB_CLUSTER_DESCRIPTION  + " FROM " + CLUSTER_TO_STAR;
	
	/*
	 * reads sub cluster descriptors 
	 */
	public List<String> readSubClusterDescription(){
		List<String> subClusterDesriptors = new ArrayList<String>();
		List<Map<String, Object>> subClusterListMap = super.jdbcSetUp()
				.getSimpleJdbcTemplate()
				.queryForList(readDistinctSubClusterDescriptionSql);
		for(Map<String, Object> subclusterMap :subClusterListMap ){
			// Set<String> theKeys = subclusterMap.keySet();
			// logger.info(subclusterMap.get(SUB_CLUSTER_DESCRIPTION));
			subClusterDesriptors.add((String) subclusterMap.get(SUB_CLUSTER_DESCRIPTION));
		}
		return subClusterDesriptors; 
	}
	

}
