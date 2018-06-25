package com.bookLords.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.Book;
import com.bookLords.model.User;
import com.bookLords.model.daos.JenresDAO;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.interfaces.ILogin;

@Controller
@ContextConfiguration(classes = BookConfig.class)
public class JenresController implements ILogin{
	
	@Autowired
	private JenresDAO jenresDao;
	
	@RequestMapping(value = "/AddGenre", method = RequestMethod.GET)
	public String visualOptions(Model model, HttpServletRequest request) throws BookException {
		try {

			return "addGenre";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookException("Something went wrong with the Genre!", e);
		}
	}
	
	@RequestMapping(value = "AddGenre", method = RequestMethod.POST)
	public String addGenre(@ModelAttribute Book book, Model model, HttpServletRequest request) {
		try {
			String name;			
			name=request.getParameter("txt_name");
			User loggedUser = getCurrentUser(request);
			if (loggedUser != null) {
				jenresDao.addJenre(name);
				return "redirect:/AddGenre";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
		return "redirect:/AddGenre";
	}
	
	@RequestMapping(value = "/Genres", method = RequestMethod.GET)
	public String visualOptionsAllGenres(Model model, HttpServletRequest request) throws BookException {
		try {

			return "listGenres";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookException("Something went wrong with the book!", e);
		}
	}
	
	@RequestMapping(value = "/UpdateGenre", method = RequestMethod.GET)
	public String visualOptionsUpdateGenre(Model model, HttpServletRequest request) throws BookException {
		try {

			return "updateGenre";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookException("Something went wrong with the book!", e);
		}
	}

	
	@RequestMapping(value = "/UpdateGenre", method = RequestMethod.POST)
	public String visualOptionsUpdateGenreNow(Model model, HttpServletRequest request) throws BookException {
		try {
			int hide; 			
			String name_up;		
			hide=Integer.parseInt(request.getParameter("txt_hide")); //it is hidden id get for update record
			name_up=request.getParameter("txt_name");  //txt_name
			User loggedUser = getCurrentUser(request);
			if (loggedUser != null) {
				jenresDao.updateJenre(name_up, hide);
				return "listGenres";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
		return "listGenres";
	}


}
