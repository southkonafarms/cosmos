package com.zenred.cosmos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.zenred.cosmos.controller.json.BasicMessageView;
import com.zenred.cosmos.service_rules_and_infrastructure.SubClusterBuildTypes;
import com.zenred.cosmos.vizualization.BasicMessageResponse;

public class UserDesignatedSubCluster implements Controller {
	
	private static Logger logger = Logger.getLogger(UserDesignatedSubCluster.class);
	private static String headMessage = "SubClusters:";

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("SESSION ID:"+request.getRequestedSessionId());	
		String subCLusterTypes = headMessage;
		subCLusterTypes +=  SubClusterBuildTypes.getUsefullTypes();
		BasicMessageResponse basicMessageResponse = new BasicMessageResponse();
		basicMessageResponse.setTheMessage(subCLusterTypes); 
		ModelAndView modelAndView = new ModelAndView(new BasicMessageView());
		modelAndView.addObject(BasicMessageView.JSON_ROOT, basicMessageResponse);
		return modelAndView;
	}

}
