package com.zenred.cosmos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.zenred.cosmos.controller.json.BasicMessageView;
import com.zenred.cosmos.service_rules_and_infrastructure.GenSystem;
import com.zenred.cosmos.vizualization.BasicMessageResponse;
import com.zenred.cosmos.domain.System;

public class UserDesignatedSystem implements Controller {
	
	private static Logger logger = Logger.getLogger(UserDesignatedSystem.class);

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("SESSION ID:"+request.getRequestedSessionId());	
		
		String s_UDimension = request.getParameter("udimension");	
		String s_VDimension = request.getParameter("vdimension");	
		Double uDimension = new Double(s_UDimension);
		Double vDimension = new Double(s_VDimension);
		Boolean answer = GenSystem.doesSystemExist(uDimension, vDimension);
		logger.info("answer for " + uDimension + ':' + vDimension + " answer:" + answer);
		ModelAndView modelAndView = null;
		if(answer){
			BasicMessageResponse basicMessageResponse = new BasicMessageResponse();
			basicMessageResponse.setTheMessage("Exists"); 
			modelAndView = new ModelAndView(new BasicMessageView());
			modelAndView.addObject(BasicMessageView.JSON_ROOT, basicMessageResponse);
		}
		else {
			System system = GenSystem.genSystemFromOrigin(uDimension, vDimension);
			GenSystem.candidate(system);
			BasicMessageResponse basicMessageResponse = new BasicMessageResponse();
			String message = system.getUcoordinate().toString() + ':' + system.getVcoordinate().toString();
			basicMessageResponse.setTheMessage(message); 
			modelAndView = new ModelAndView(new BasicMessageView());
			modelAndView.addObject(BasicMessageView.JSON_ROOT, basicMessageResponse);
		}
		return modelAndView;
	}

}
