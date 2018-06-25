package com.bookLords.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookLords.model.Author;
import com.bookLords.model.Book;
import com.bookLords.model.DBConnection;
import com.bookLords.model.exceptions.AuthorException;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.BookshelfException;
import com.bookLords.model.exceptions.CommentException;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.RatingException;
import com.bookLords.model.interfaces.IBookDBDAO;

@Component
public class BookDBDAO implements IBookDBDAO {
	private static final String SELECT_FROM_EDITION_LANGUAGES_WHERE_LANGUAGE = "SELECT* FROM edition_languages where language=\'";
	private static final String INSERT_INTO_EDITION_LANGUAGES = "INSERT INTO edition_languages VALUES(null,?);";
	private static final String INSERT_INTO_BOOKS = "INSERT INTO books(title, ISBN, poster_url, description, pages, edition_language_id, read_online_url, buy_online_url, setting ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String INSERT_INTO_BOOKS_NEW = "INSERT INTO books(title, ISBN, poster_url, description, pages, edition_language_id, read_online_url, buy_online_url, setting, date, iztochnik ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_FROM_AUTHORS = "SELECT* FROM authors where name=\'";
	private static final String INSERT_INTO_BOOKS_HAS_AUTHORS = "INSERT INTO books_has_authors VALUES (?, ?);";
	private static final String SELECT_FROM_GENRES_WHERE_NAME = "SELECT* FROM genres where name=\'";
	private static final String INSERT_INTO_GENRES = "INSERT INTO genres VALUES(null,?);";
	private static final String SELECT_FROM_BOOKS_WHERE_TITLE_LIKE = "SELECT* FROM books where title like \'";
	private static final String INSERT_INTO_BOOKS_HAS_GENRES = "INSERT INTO books_has_genres VALUES (?, ?);";
	private static final String DELETE_BOOK_BY_ID = "DELETE from books where id=\'";
	private static final String UPDATE_GENRE = "Update books set title=?, ISBN=?, poster_url=?, description=?, pages=?, iztochnik=? where book_id=?";
	private Connection connection = DBConnection.getInstance().getConnection();

	@Autowired
	CommentsDAO commentsDBDAO;
	@Autowired
	AuthorApiDAO authorApiDAO;
	
	public synchronized void deleteManuallyBook(Book book) throws BookException {
		try {
			connection.setAutoCommit(false);
			
			PreparedStatement st = connection.prepareStatement("DELETE from books where id = ?");
			st.setInt(1, book.getBookId());
			st.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException();
		}
	}
	
	public synchronized void updateManuallyBook(String title, String isbn, String poster, int pages, String source, String desc, int hide) throws BookException{
		try {
			connection.setAutoCommit(false);

			PreparedStatement pstmt = null; // create statement

			pstmt=connection.prepareStatement(UPDATE_GENRE); //sql update query 
			pstmt.setString(1,title);
			pstmt.setString(2,isbn);
			pstmt.setString(3,poster);
			pstmt.setString(4,desc);
			pstmt.setInt(5,pages);
			pstmt.setString(6,source);
			pstmt.setInt(7,hide);
			pstmt.executeUpdate(); //execute query

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new BookException("Invalid book!");
			}
			throw new BookException("Invalid book!");
		}
	}
	
	public synchronized void addManuallyBook(Book book) throws BookException {
		try {
			connection.setAutoCommit(false);
				if (!containsISBN(book.getISBN())) {
					System.out.println("Eto sega shte vkaram!");
					Statement statement = connection.createStatement();
					String editionLanguage = book.getEditionLanguage();
					ResultSet resultSet = statement
							.executeQuery(SELECT_FROM_EDITION_LANGUAGES_WHERE_LANGUAGE + editionLanguage + "\'");

					int editionLanguageId = 0;
					PreparedStatement ps = null;
					if (!resultSet.next()) {
						ps = connection.prepareStatement(INSERT_INTO_EDITION_LANGUAGES,
								Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, editionLanguage);
						ps.executeUpdate();
						resultSet = ps.getGeneratedKeys();
						resultSet.next();
						editionLanguageId = resultSet.getInt(1);
					} else {
						editionLanguageId = resultSet.getInt("edition_language_id");
					}

					ps = connection.prepareStatement(INSERT_INTO_BOOKS_NEW, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, book.getTitle());
					ps.setString(2, book.getISBN());
					ps.setString(3, book.getPosterURL());
					ps.setString(4, book.getDescription());
					ps.setInt(5, book.getPages());
					ps.setInt(6, editionLanguageId);
					ps.setString(7, "");
					ps.setString(8, "");
					ps.setString(9, "");
					ps.setString(10, LocalDate.now().toString());
					ps.setString(11, book.getIztochnik());

					ps.executeUpdate();

					ResultSet rs = ps.getGeneratedKeys();
					rs.next();
					int bookId = rs.getInt(1);
					book.setBookId(bookId);

					for (Author author : book.getAuthors()) {
						statement = connection.createStatement();
						resultSet = statement.executeQuery(SELECT_FROM_AUTHORS + author.getName() + "\'");

						int authorId = 0;
						if (!resultSet.next()) {
							authorId = authorApiDAO.insertAuthor(author.getName());
						} else {
							authorId = rs.getInt("author_id");
						}
						if (!(authorId == 0)) {
							ps = connection.prepareStatement(INSERT_INTO_BOOKS_HAS_AUTHORS);
							ps.setInt(1, bookId);
							ps.setInt(2, authorId);
							ps.executeUpdate();
						}
					}
					for (String genreName : book.getGenres()) {
						if (!genreName.contains("\'")) {
							resultSet = statement.executeQuery(SELECT_FROM_GENRES_WHERE_NAME + genreName + "\'");

							int genreId = 0;
							if (!resultSet.next()) {
								ps = connection.prepareStatement(INSERT_INTO_GENRES, Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, genreName);
								ps.executeUpdate();
								rs = ps.getGeneratedKeys();
								rs.next();
								genreId = rs.getInt(1);
							} else {
								genreId = resultSet.getInt("genre_id");
							}

							ps = connection.prepareStatement(INSERT_INTO_BOOKS_HAS_GENRES);
							ps.setInt(1, bookId);
							ps.setInt(2, genreId);
							ps.executeUpdate();
						}
					}
					connection.commit();
				}
			
		} catch (SQLException | InvalidDataException e) {
			e.printStackTrace();
			throw new BookException();
		}
	}

	public synchronized void addBooks(Set<Book> books) throws BookException {
		try {
			connection.setAutoCommit(false);
			for (Book book : books) {
				if (!containsISBN(book.getISBN())) {
					System.out.println("Eto sega shte vkaram!");
					Statement statement = connection.createStatement();
					String editionLanguage = book.getEditionLanguage();
					ResultSet resultSet = statement
							.executeQuery(SELECT_FROM_EDITION_LANGUAGES_WHERE_LANGUAGE + editionLanguage + "\'");

					int editionLanguageId = 0;
					PreparedStatement ps = null;
					if (!resultSet.next()) {
						ps = connection.prepareStatement(INSERT_INTO_EDITION_LANGUAGES,
								Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, editionLanguage);
						ps.executeUpdate();
						resultSet = ps.getGeneratedKeys();
						resultSet.next();
						editionLanguageId = resultSet.getInt(1);
					} else {
						editionLanguageId = resultSet.getInt("edition_language_id");
					}

					ps = connection.prepareStatement(INSERT_INTO_BOOKS, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, book.getTitle());
					ps.setString(2, book.getISBN());
					ps.setString(3, book.getPosterURL());
					ps.setString(4, book.getDescription());
					ps.setInt(5, book.getPages());
					ps.setInt(6, editionLanguageId);
					ps.setString(7, book.getReadOnlineURL());
					ps.setString(8, book.getBuyOnlineURL());
					ps.setString(9, book.getSetting());

					ps.executeUpdate();

					ResultSet rs = ps.getGeneratedKeys();
					rs.next();
					int bookId = rs.getInt(1);
					book.setBookId(bookId);

					for (Author author : book.getAuthors()) {
						statement = connection.createStatement();
						resultSet = statement.executeQuery(SELECT_FROM_AUTHORS + author.getName() + "\'");

						int authorId = 0;
						if (!resultSet.next()) {
							authorId = authorApiDAO.insertAuthor(author.getName());
						} else {
							authorId = rs.getInt("author_id");
						}
						if (!(authorId == 0)) {
							ps = connection.prepareStatement(INSERT_INTO_BOOKS_HAS_AUTHORS);
							ps.setInt(1, bookId);
							ps.setInt(2, authorId);
							ps.executeUpdate();
						}
					}
					for (String genreName : book.getGenres()) {
						if (!genreName.contains("\'")) {
							resultSet = statement.executeQuery(SELECT_FROM_GENRES_WHERE_NAME + genreName + "\'");

							int genreId = 0;
							if (!resultSet.next()) {
								ps = connection.prepareStatement(INSERT_INTO_GENRES, Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, genreName);
								ps.executeUpdate();
								rs = ps.getGeneratedKeys();
								rs.next();
								genreId = rs.getInt(1);
							} else {
								genreId = resultSet.getInt("genre_id");
							}

							ps = connection.prepareStatement(INSERT_INTO_BOOKS_HAS_GENRES);
							ps.setInt(1, bookId);
							ps.setInt(2, genreId);
							ps.executeUpdate();
						}
					}
					connection.commit();
				}
			}
		} catch (SQLException | InvalidDataException e) {
			e.printStackTrace();
			throw new BookException();
		}
	}

	private synchronized boolean containsISBN(String isbn) throws BookException {
		try {
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT ISBN FROM books WHERE ISBN=\'" + isbn + "\'");

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookException();
		}
	}

	public synchronized Set<Book> getBooksByTitle(String bookTitle) throws BookException {

		Set<Book> books = new LinkedHashSet<Book>();
		try {
			if (bookTitle != null && !bookTitle.equals("")) {
				if (!(bookTitle.contains("\'") || bookTitle.contains("\""))) {
					connection = DBConnection.getInstance().getConnection();
					connection.setAutoCommit(false);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement
							.executeQuery(SELECT_FROM_BOOKS_WHERE_TITLE_LIKE + bookTitle + "%\';");

					while (resultSet.next()) {
						int bookId = resultSet.getInt("book_id");

						connection.commit();
						books.add(getBookByID(bookId));
					}
					return books;
				} else {
					throw new BookException();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();

			}

		} finally {
		}
		// } finally {
		// try {
		// connection.close();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
		return null;
	}

	public synchronized Set<Book> getBooksByAuthor(String author) throws BookException, InvalidDataException {
		Set<Book> books = new LinkedHashSet<Book>();
		try {
			if (author != null && !author.equals("")) {
				if (!(author.contains("\'") || author.contains("\""))) {
					connection = DBConnection.getInstance().getConnection();
					connection.setAutoCommit(false);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement
							.executeQuery("SELECT* FROM authors WHERE name LIKE \'" + author + "%\';");
					while (resultSet.next()) {
						int authorId = resultSet.getInt("author_id");
						int bookId = getBookId(authorId);
						connection.commit();
						books.add(getBookByID(bookId));
					}
					return books;
				} else {
					throw new BookException("Please enter valid data!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
		}
		return books;
	}

	public synchronized Set<Book> getBooksByGenre(String genre) throws BookException {
		Set<Book> books = new LinkedHashSet<Book>();
		try {
			if (genre != null && !genre.equals("")) {
				if (!(genre.contains("\'") || genre.contains("\""))) {
					connection = DBConnection.getInstance().getConnection();
					connection.setAutoCommit(false);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement
							.executeQuery("SELECT * FROM genres where name = \'" + genre + "\';");

					if (resultSet.next()) {
						int genreId = resultSet.getInt("genre_id");
						resultSet = statement.executeQuery(
								"SELECT* FROM books_has_genres where genres_genre_id=\'" + genreId + "\';");
						while (resultSet.next()) {
							int bookId = resultSet.getInt("books_book_id");
							connection.commit();
							books.add(getBookByID(bookId));
						}
					}
					return books;
				}
			}
		} catch (SQLException | BookException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new BookException("No books found!");
			}
			throw new BookException("No books found!");
		} finally {
		}
		return books;
	}

	public synchronized Book getBookByID(int bookId) throws BookException {
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT* FROM books where book_id = \'" + bookId + "\';");
			if (rs.next()) {
				String title = rs.getString("title");
				String ISBN = rs.getString("ISBN");
				String posterURL = rs.getString("poster_url");
				String description = rs.getString("description");
				int pages = rs.getInt("pages");
				int editionLanguageId = rs.getInt("edition_language_id");
				String editionLanguage = getBookEditionLanguage(connection, editionLanguageId);
				String readOnlineURL = rs.getString("read_online_url");
				String buyOnlineURL = rs.getString("buy_online_url");
				String setting = rs.getString("setting");
				double rating = rs.getDouble("rating");
				int numberOfRatings = rs.getInt("number_of_ratings");

				Set<Author> authors = getBookAuthors(connection, bookId);
				Set<String> genres = getBookGenres(connection, bookId);

				return new Book(bookId, title, authors, setting, description, ISBN, pages, genres, posterURL,
						editionLanguage, readOnlineURL, buyOnlineURL, rating, numberOfRatings,
						commentsDBDAO.getBookComments(bookId));
			}
		} catch (RatingException | CommentException | InvalidDataException | SQLException | AuthorException e) {
			e.printStackTrace();
			throw new BookException();
		}
		return null;

	}

	private synchronized String getBookEditionLanguage(Connection connection, int editionLanguageId)
			throws SQLException {
		Statement statement2 = connection.createStatement();
		ResultSet rs = statement2.executeQuery(
				"SELECT* FROM edition_languages where edition_language_id=\'" + editionLanguageId + "\';");
		rs.next();
		String editionLanguage = rs.getString("language");
		return editionLanguage;
	}

	private synchronized Set<Author> getBookAuthors(Connection connection, int bookId) throws AuthorException {
		try {
			Set<Author> authors = new LinkedHashSet<Author>();
			Statement statement2 = connection.createStatement();
			ResultSet rs = statement2.executeQuery("SELECT* FROM books_has_authors where book_id=\'" + bookId + "\';");
			while (rs.next()) {
				int authorId = rs.getInt("author_id");
				authors.add(getAuthorById(authorId));
			}
			return authors;
		} catch (AuthorException | SQLException e) {
			e.printStackTrace();
			throw new AuthorException("No author found!");
		}
	}

	public synchronized Author getAuthorById(int authorId) throws AuthorException {
		try {
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("SELECT* FROM authors where author_id=\'" + authorId + "\';");
			if (rs.next()) {
				String name = rs.getString("name");
				System.out.println(name);
				String posterURL = rs.getString("poster_url");
				System.out.println(posterURL);
				String born = rs.getString("born_where_and_when");
				System.out.println(born);
				String died = rs.getString("died");
				System.out.println(died);
				String biography = rs.getString("biography");
				System.out.println(biography);
				return new Author(authorId, name, posterURL, born, died, biography);
			}
		} catch (SQLException | InvalidDataException | AuthorException e) {
			e.printStackTrace();
			throw new AuthorException("No author found!");
		}
		return null;
	}

	private synchronized Set<String> getBookGenres(Connection connection, int bookId) throws SQLException {
		Set<String> genres = new HashSet<String>();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT* FROM books_has_genres where books_book_id=\'" + bookId + "\';");
		while (rs.next()) {
			int genreId = rs.getInt("genres_genre_id");
			genres.add(getGenre(genreId));
		}
		return genres;
	}

	private synchronized String getGenre(int genreId) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT name FROM genres where genre_id=\'" + genreId + "\';");
		resultSet.next();
		return resultSet.getString("name");
	}

	private synchronized int getBookId(int authorId) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT* FROM books_has_authors where author_id=\'" + authorId + "\'");
		resultSet.next();
		return resultSet.getInt("book_id");
	}
}
