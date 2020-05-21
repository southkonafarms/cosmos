package com.zenred.cosmos.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.zenred.cosmos.controller.json.BasicMessageView;
import com.zenred.cosmos.service_rules_and_infrastructure.GenUserSpecSystem;
import com.zenred.cosmos.service_rules_and_infrastructure.SubClusterBuildTypes;
import com.zenred.cosmos.vizualization.BasicMessageResponse;

public class BuildSpecifiedClusterAndStars implements Controller {

	private static Logger logger = Logger.getLogger(BuildSpecifiedClusterAndStars.class);
	private static String headMessage = "Complete";
	private static String moreStars = "MoreStars";
	private static List<String> starList;
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("SESSION ID:"+request.getRequestedSessionId());	
		String s_UDimension = request.getParameter("udimension");	
		String s_VDimension = request.getParameter("vdimension");	
		Double uDimension = new Double(s_UDimension);
		Double vDimension = new Double(s_VDimension);
		String spread = request.getParameter("spread");
		
		String starColor0 = request.getParameter("star_color0");
		String starColor1 = request.getParameter("star_color1");
		String starColor2 = request.getParameter("star_color2");
		String starColor3 = request.getParameter("star_color3");
		starList = new ArrayList<String>();
		starList.add(starColor0);
		Boolean done = Boolean.FALSE;
		if(starColor1  != null){
			logger.info("uDim:" + uDimension + " vDim:"  
					+ vDimension + " spread:" + spread + " starColor0:" 
					+ starColor0 + " starColor1:" + starColor1);
			starList.add(starColor1);
			done = Boolean.TRUE;  // all the stars a here
		}
		if(starColor2  != null){
			logger.info("uDim:" + uDimension + " vDim:"  
					+ vDimension + " spread:" + spread + " starColor0:" 
					+ starColor0 + " starColor1:" + starColor1
					+ " starColor2:" + starColor2
					);
			starList.add(starColor2);
		}
		if(starColor3  != null){
			logger.info("uDim:" + uDimension + " vDim:"  
					+ vDimension + " spread:" + spread + " starColor0:" 
					+ starColor0 + " starColor1:" + starColor1
					+ " starColor2:" + starColor2
					+ " starColor3:" + starColor3
					);
			starList.add(starColor3);
		}
		logger.info("uDim:" + uDimension + " vDim:"  
		+ vDimension + " spread:" + spread + " starColor0:" + starColor0);
		Integer starCount = SubClusterBuildTypes.howManyStarsForCluster(spread);
		BasicMessageResponse basicMessageResponse = new BasicMessageResponse();
		String message = "";
		if(starCount < 2){
			message = headMessage;
			done = Boolean.TRUE;
		}
		else{
			if(done.equals(Boolean.FALSE)){
				message = moreStars + ":" + starCount.toString();
			}
			else{
				message = headMessage;
			}
		}
		if(done){
			GenUserSpecSystem.buildTheSystem(uDimension, vDimension, spread, starList);
		}
		basicMessageResponse.setTheMessage(message); 
		ModelAndView modelAndView = new ModelAndView(new BasicMessageView());
		modelAndView.addObject(BasicMessageView.JSON_ROOT, basicMessageResponse);
		return modelAndView;
	}

}
