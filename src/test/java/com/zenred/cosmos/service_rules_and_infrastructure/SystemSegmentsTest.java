/**
 * 
 */
package com.zenred.cosmos.service_rules_and_infrastructure;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.zenred.cosmos.domain.SystemDao;
import com.zenred.cosmos.domain.UV_Instance;

/**
 * @author johnredden
 *
 */
public class SystemSegmentsTest {
	private static Logger logger = Logger.getLogger(SystemSegmentsTest.class);
	
	SystemDao systemDao = new SystemDao();

	@Test
	public void test() {
		Integer total = 0;
		SystemSegments systemSegments = new SystemSegments();
		List<List<Integer>> masterList = systemSegments.builder();
		Integer [] positions = new Integer[4];
		for(List<Integer> deatailList: masterList){
			for(Integer idex = 0; idex < deatailList.size(); idex++){
				positions[idex] = deatailList.get(idex);
			}
			List<UV_Instance> uvList = systemDao.readSystemSegment(positions[0], positions[1], positions[2], positions[3]);
			logger.info("uvList length:"+uvList.size());
			total += uvList.size();
		}
		logger.info("total:"+total);
	}

}
