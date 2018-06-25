package com.bookLords.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.interfaces.ILogin;

@Controller
@ContextConfiguration(classes = BookConfig.class)
public class ReportsController implements ILogin{

	@RequestMapping(value = "/Reports", method = RequestMethod.GET)
	public String visualOptionsAllBooksNow(Model model, HttpServletRequest request) throws BookException {
		try {

			return "reports";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookException("Something went wrong with the book!", e);
		}
	}
	
	@RequestMapping(value = "/Reports", method = RequestMethod.POST)
	public String visualOptionsAllBooksNowPOST(Model model, HttpServletRequest request) throws BookException {
		try {

			return "reports";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookException("Something went wrong with the book!", e);
		}
	}
}
