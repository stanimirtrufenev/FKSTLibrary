package dao.tests;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.Author;
import com.bookLords.model.Book;
import com.bookLords.model.Comment;
import com.bookLords.model.daos.BookApiDAO;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.daos.CommentsDAO;
import com.bookLords.model.exceptions.BookException;

@Controller
@ContextConfiguration(classes = BookConfig.class)
public class TestBookDAO {
	// @Autowired
	// BookDBDAO db;
	// @Autowired
	// BookApiDAO api;

	@Test
	public void test() throws BookException {
		try {
			CommentsDAO dao = new CommentsDAO();
			BookDBDAO db = new BookDBDAO();
			BookApiDAO api = new BookApiDAO();

			for (Book book : db.getBooksByTitle("php")) {
				for (Author author : book.getAuthors()) {
					System.out.println(author);
				}
			}

//			Book booka = null;
//			for (Book book : db.getBooksByTitle("Principles")) {
//				booka = book;
//				System.out.println(book);
//			}
//			Set<Comment> comments = dao.getBookComments(booka);
//			System.out.println(comments);
			// for (Book book : db.getBooksByGenre("")) {
			// System.out.println(book);
			// }
			// try {
			 api.getBooks("inferno", db);
			 api.getBooks("nemo", db);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
