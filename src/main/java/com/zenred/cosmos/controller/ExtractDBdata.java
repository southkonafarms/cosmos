package com.zenred.cosmos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.zenred.cosmos.controller.json.BasicMessageView;
import com.zenred.cosmos.service_rules_and_infrastructure.GenLoadableCompleteSystem;
import com.zenred.cosmos.vizualization.BasicMessageResponse;

public class ExtractDBdata implements Controller {

	public ExtractDBdata() {
		super();
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String uvSet = request.getParameter("UVset");
		String [] uAndv = uvSet.split("_");
		Integer uCoordinate = new Integer(uAndv[0]);
		Integer vCoordinate = new Integer(uAndv[1]);
		GenLoadableCompleteSystem.GenFromUVCoordinates(uCoordinate, vCoordinate);
		BasicMessageResponse basicMessageResponse = new BasicMessageResponse();
		basicMessageResponse.setTheMessage("Okay");
		ModelAndView modelAndView = new ModelAndView(new BasicMessageView());
		modelAndView.addObject(BasicMessageView.JSON_ROOT, basicMessageResponse);
		return modelAndView;
	}

}
