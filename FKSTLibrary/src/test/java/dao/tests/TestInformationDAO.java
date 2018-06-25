package dao.tests;

import org.junit.Test;

import com.bookLords.model.User;
import com.bookLords.model.daos.InformationDAO;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.UserException;

public class TestInformationDAO {
	
	@Test
	public void test() throws BookException {
		try {
			User user = new User(15, "Stanimir", "2016", "image");
			boolean check = new InformationDAO().validateLoginPassword(user, "Stanimir44@");
			System.out.println(check);
		} catch (UserException e) {
			e.printStackTrace();
		}
	}
}
