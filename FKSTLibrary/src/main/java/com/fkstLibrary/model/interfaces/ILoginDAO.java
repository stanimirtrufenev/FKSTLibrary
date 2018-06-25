package com.bookLords.model.interfaces;

import java.sql.SQLException;

import com.bookLords.model.User;
import com.bookLords.model.exceptions.UserException;

public interface ILoginDAO {

	public int validateLogin(User user) throws SQLException, UserException;
}
