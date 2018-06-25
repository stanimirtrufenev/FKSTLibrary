package dao.tests;

import java.io.IOException;

import org.junit.Test;

import com.bookLords.model.daos.QuotesApiDAO;
import com.bookLords.model.exceptions.BookException;

public class TestQuotesApiDAO {

	@Test
	public void test() throws BookException {
		try {
			String quote = new QuotesApiDAO().getQuoteOfTheDay();
			System.out.println(quote);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
