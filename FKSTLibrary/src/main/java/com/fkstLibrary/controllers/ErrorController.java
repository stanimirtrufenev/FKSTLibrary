package com.bookLords.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookLords.configuration.BookConfig;

@Controller
@ContextConfiguration(classes = BookConfig.class)
public class ErrorController {
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	private String sendError(Model model, HttpServletRequest request){
		
		return "errorPage";
		
	}

}
