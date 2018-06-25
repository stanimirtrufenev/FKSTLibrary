package dao.tests;

import java.sql.SQLException;

import org.junit.Test;

import com.bookLords.model.User;
import com.bookLords.model.daos.UserProfileDAO;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.InvalidDataException;

public class TestUserProfileDAO {

	@Test
	public void test() throws BookException {
		try {
			User user = new UserProfileDAO().getUserById(15);
		} catch (SQLException | InvalidDataException e) {
			e.printStackTrace();
		}
	}
}
