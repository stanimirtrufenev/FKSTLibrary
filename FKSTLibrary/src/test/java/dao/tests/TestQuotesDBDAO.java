package dao.tests;

import org.junit.Test;

import com.bookLords.model.daos.QuotesDBDAO;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.QuotesException;

public class TestQuotesDBDAO {
	@Test
	public void test() throws BookException {
		try {
			String quote = new QuotesDBDAO().getQuoteOfTheDay();
			System.out.println(quote);
		} catch (QuotesException e) {
			e.printStackTrace();
		}
		
	}

}
