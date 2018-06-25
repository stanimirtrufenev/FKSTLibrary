package com.bookLords.model.interfaces;

import java.time.LocalDateTime;
import java.util.Map;

import com.bookLords.model.Book;
import com.bookLords.model.Rating;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.RatingException;

public interface IRatingsDAO {

	public void updateRatings(User user, Book book, int rating, LocalDateTime date) throws RatingException, InvalidDataException;

	public Map<Integer, Rating> getUserRatings(int userId) throws RatingException, InvalidDataException;
}
