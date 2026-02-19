package org.example.dao.impl;

import org.example.dao.IBookDao;
import org.example.entity.Book;
import org.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao implements IBookDao<Long, Book> {

    private static final BookDao INSTANCE = new BookDao();

    private static final String FIND_ALL = """
             SELECT *
                        FROM books
                        ORDER BY year_of_publication
            """;

private static final String FIND_BY_ID = """
         SELECT *
                    FROM books
                    WHERE id = ?
        """;

private static final String FIND_NAME = """
         SELECT *
                    FROM books
                    WHERE book_title = ?
        """;

private static final String DELETE_SQL = """
        DELETE FROM books
                    WHERE id = ?
        """;

    private static final String UPDATE_SQL = """
              UPDATE books
                                    SET book_title = ?,
                                        years_of_publication = ?,
                                        genre = ?,
                                        author_id = ?,
                                    WHERE id = ?
        """;

    private static final String SAVE_SQL = """
             INSERT INTO books ( book_title, years_of_publication, genre, author_id)
             VALUES (?, ?, ?, ?)
            """;

    private BookDao() {

    }

    public static BookDao getInstance() {
        return INSTANCE;
    }


    @Override
    public Optional<Book> findName(String name) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_NAME)){

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            Book book = null;
            if (resultSet.next()){
                book = buildBook(resultSet);
            }
            return Optional.ofNullable(book);

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> bookList = new ArrayList<>();

            while (resultSet.next()){
                bookList.add(buildBook(resultSet));
            }
            return bookList;

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Book book = null;
            if (resultSet.next()){
                book = buildBook(resultSet);
            }
            return Optional.ofNullable(book);

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)){

            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public void update(Book entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)){

            preparedStatement.setString(1, entity.getBookTitle());
            preparedStatement.setInt(2, entity.getYearOfPublication());
            preparedStatement.setString(3, entity.getGenre());
            preparedStatement.setLong(4, entity.getAuthorId());

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public Book save(Book entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getBookTitle());
            preparedStatement.setInt(2, entity.getYearOfPublication());
            preparedStatement.setString(3, entity.getGenre());
            preparedStatement.setLong(4, entity.getAuthorId());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong("id"));
            }

            return entity;

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    private Book buildBook(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getObject("id", Long.class),
                resultSet.getObject("book_title", String.class),
                resultSet.getObject("year_of_publication", Integer.class),
                resultSet.getObject("genre", String.class),
                resultSet.getObject("author_id", Long.class)
    );
    }
}
