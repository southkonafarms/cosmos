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
		Boolean first = Boolean.TRUE;
		for(List<Integer> deatailList: masterList){
			for(Integer idex = 0; idex < deatailList.size(); idex++){
				positions[idex] = deatailList.get(idex);
			}
			List<UV_Instance> uvList = systemDao.readSystemSegment(positions[0], positions[1], positions[2], positions[3]);
			logger.info("seg"+positions[0]+"_"+positions[1]+"_"+positions[2]+"_"
			+positions[3]+":"+"uvList length:"+uvList.size());
			total += uvList.size();
			if(first){
				String set = "";
				Integer set2 = 0;
				for(Integer idex2 = 0; idex2 < uvList.size(); idex2++){
					set += "u:"+uvList.get(idex2).getuDimension()+"v:"+uvList.get(idex2).getvDimension()+"___";
					if(++set2  == 2){
						set += ":::";
						set2 = 0;
					}
				}
				logger.info("FirstSet:"+set);
				first= Boolean.FALSE;
			}
		}
		logger.info("total:"+total);
	}

}
