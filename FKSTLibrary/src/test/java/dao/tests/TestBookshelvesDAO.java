package dao.tests;

import java.sql.SQLException;
import java.util.Set;

import org.junit.Test;

import com.bookLords.model.Bookshelf;
import com.bookLords.model.daos.BookshelvesDAO;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.BookshelfException;

public class TestBookshelvesDAO {

	@Test
	public void test() throws BookException {
		try {
			boolean check = new BookshelvesDAO().validateIds(16, 517);
			System.out.println(check);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test1() throws BookException {
		try {
			Set<Bookshelf> bookshelves = new BookshelvesDAO().getUserBookshelves(15);
			for(Bookshelf b : bookshelves){
				System.out.println(b);
			}
		} catch (BookshelfException e) {
			e.printStackTrace();
		}
	}
}
