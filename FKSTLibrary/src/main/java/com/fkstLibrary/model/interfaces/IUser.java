package com.bookLords.model.interfaces;

import java.util.Comparator;

import com.bookLords.model.Rating;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.InvalidDataException;

public interface IUser {
	boolean checkEmail(String email);

	boolean checkThePassword(String password);

	void isValidString(String string) throws InvalidDataException;

	Comparator<User> getUserComparator();

	Comparator<Rating> getRatingComparator();

}
