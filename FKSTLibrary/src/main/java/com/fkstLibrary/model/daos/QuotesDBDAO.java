package com.bookLords.model.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookLords.model.DBConnection;
import com.bookLords.model.exceptions.QuotesException;

@Component
public class QuotesDBDAO {

	@Autowired
	QuotesApiDAO quotesApiDAO;

	private Connection connection = DBConnection.getInstance().getConnection();

	public String getQuoteOfTheDay() throws QuotesException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quotes where date = ?;");
			preparedStatement.setString(1, LocalDate.now().toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return addQuoteOfTheDay();
			}
			return resultSet.getString(2);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new QuotesException();
		}
	}

	public String addQuoteOfTheDay() throws QuotesException {
		try {
			String quote = quotesApiDAO.getQuoteOfTheDay();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO quotes VALUES(null, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, quote);
			preparedStatement.setString(2, LocalDate.now().toString());
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return quote;
			}

		} catch (IOException | SQLException e) {
			e.printStackTrace();
			throw new QuotesException();
		}
		return null;

	}

}
