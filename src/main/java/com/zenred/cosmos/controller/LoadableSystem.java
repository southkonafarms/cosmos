package com.zenred.cosmos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.zenred.cosmos.controller.json.BasicMessageView;
import com.zenred.cosmos.service_rules_and_infrastructure.AllSYStemsWIthClusters;
import com.zenred.cosmos.vizualization.BasicMessageResponse;

public class LoadableSystem implements Controller {

	public LoadableSystem() {
		super();
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String allOfThem= AllSYStemsWIthClusters.allTheUsAndVs();
		BasicMessageResponse basicMessageResponse = new BasicMessageResponse();
		basicMessageResponse.setTheMessage(allOfThem);
		ModelAndView modelAndView = new ModelAndView(new BasicMessageView());
		modelAndView.addObject(BasicMessageView.JSON_ROOT, basicMessageResponse);
		return modelAndView;
	}

}
