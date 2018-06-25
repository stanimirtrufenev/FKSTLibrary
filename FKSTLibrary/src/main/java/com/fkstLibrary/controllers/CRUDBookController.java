package com.bookLords.controllers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.Author;
import com.bookLords.model.Book;
import com.bookLords.model.User;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.interfaces.ILogin;

@Controller
@ContextConfiguration(classes = BookConfig.class)
public class CRUDBookController implements ILogin{
	
	@Autowired
	private BookDBDAO bookDBDao;
	
	@RequestMapping(value = "/CreateBook", method = RequestMethod.GET)
	public String visualOptions(Model model, HttpServletRequest request) throws BookException {
		try {
			model.addAttribute("book", new Book());

			return "addBook";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookException("Something went wrong with the book!", e);
		}
	}
	
	@RequestMapping(value = "/BooksAll", method = RequestMethod.GET)
	public String visualOptionsAllBooksNow(Model model, HttpServletRequest request) throws BookException {
		try {

			return "listBooks";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookException("Something went wrong with the book!", e);
		}
	}
	
	@RequestMapping(value = "/CreateBook", method = RequestMethod.POST)
	public String createBookSecondWay(Model model, HttpServletRequest request) {
		try {
			int pages; 			
			String title, isbn, poster, source, desc, lang, genre, authors;		
			title=request.getParameter("txt_title"); 
			authors=request.getParameter("txt_author"); 
			isbn=request.getParameter("txt_isbn");
			poster=request.getParameter("txt_poster");
			pages=Integer.parseInt(request.getParameter("txt_pages"));
			source=request.getParameter("txt_source");
			desc=request.getParameter("txt_desc");
			lang=request.getParameter("txt_lang");
			genre=request.getParameter("txt_genre");
			Book book = new Book();
			Author author = new Author(authors);
			Set<Author> auths= new HashSet<Author>();
			auths.add(author);
			Set<String> genres= new HashSet<String>();
			genres.add(genre);
			book.setTitle(title);
			book.setAuthors(auths);
			book.setISBN(isbn);
			book.setPosterURL(poster);
			book.setPages(pages);
			book.setIztochnik(source);
			book.setDescription(desc);
			book.setEditionLanguage(lang);
			book.setGenres(genres);
			User loggedUser = getCurrentUser(request);
			if (loggedUser != null) {
				bookDBDao.addManuallyBook(book);
				return "listBooks";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
		return "listBooks";
	}
	
	@RequestMapping(value = "/UpdateBook", method = RequestMethod.GET)
	public String visualOptionsUpdateBookNow(Model model, HttpServletRequest request) throws BookException {
		try {

			return "updateBook";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookException("Something went wrong with the book!", e);
		}
	}

	
	@RequestMapping(value = "/UpdateBook", method = RequestMethod.POST)
	public String visualOptionsUpdateBookNow2(Model model, HttpServletRequest request) {
		try {
			int hide, pages; 			
			String title, isbn, poster, source, desc;		
			hide=Integer.parseInt(request.getParameter("txt_hide")); //it is hidden id get for update record
			title=request.getParameter("txt_title"); 
			isbn=request.getParameter("txt_isbn");
			poster=request.getParameter("txt_poster");
			pages=Integer.parseInt(request.getParameter("txt_pages"));
			source=request.getParameter("txt_source");
			desc=request.getParameter("txt_desc");
			User loggedUser = getCurrentUser(request);
			if (loggedUser != null) {
				bookDBDao.updateManuallyBook(title, isbn, poster, pages, source, desc, hide);
				return "listBooks";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
		return "listBooks";
	}

}
