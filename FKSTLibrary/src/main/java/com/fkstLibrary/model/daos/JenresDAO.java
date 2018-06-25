package com.bookLords.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.bookLords.model.DBConnection;
import com.bookLords.model.exceptions.BookshelfException;

@Component
public class JenresDAO {

	private Connection connection = DBConnection.getInstance().getConnection();
	private static final String ADD_GENRE = "INSERT into jenres(name)values(?)";
	private static final String UPDATE_GENRE = "Update jenres set name=? where genre_id=?";

	public synchronized void addJenre(String name) throws BookshelfException {
		try {
			connection.setAutoCommit(false);

			PreparedStatement pstmt = null; // create statement

			pstmt = connection.prepareStatement(ADD_GENRE); // sql insert query
			pstmt.setString(1, name);
			pstmt.executeUpdate(); // execute query

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new BookshelfException("Invalid genre!");
			}
			throw new BookshelfException("Invalid genre!");
		}
	}
	
	public synchronized void updateJenre(String name, int id) throws BookshelfException {
		try {
			connection.setAutoCommit(false);

			PreparedStatement pstmt = null; // create statement

			pstmt=connection.prepareStatement(UPDATE_GENRE); //sql update query 
			pstmt.setString(1,name);
			pstmt.setInt(2,id);
			pstmt.executeUpdate(); //execute query

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new BookshelfException("Invalid genre!");
			}
			throw new BookshelfException("Invalid genre!");
		}
	}

}
