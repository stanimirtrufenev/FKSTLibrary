package dao.tests;

import java.sql.SQLException;

import org.junit.Test;

import com.bookLords.model.User;
import com.bookLords.model.daos.LoginDAO;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.UserException;

public class TestLoginDAO {

	@Test
	public void test() throws BookException {
		try {
			User user = new User(15, "Stanimir", "stanimir444@abv.bg", "Stanimir44@","addasas", "sdsdsd");
			int id = new LoginDAO().validateLogin(user);
		} catch (InvalidDataException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UserException e) {
			e.printStackTrace();
		}
	}
}
