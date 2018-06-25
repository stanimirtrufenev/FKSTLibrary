package com.bookLords.model.interfaces;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashSet;

import com.bookLords.model.Book;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.InvalidDataException;

public interface IBookApiDAO {

	public LinkedHashSet<Book> getBooks(String text, BookDBDAO dao) throws MalformedURLException, IOException, InvalidDataException, BookException;
}
