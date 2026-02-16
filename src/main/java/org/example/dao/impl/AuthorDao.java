package org.example.dao.impl;

import org.example.dao.Dao;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDao implements Dao<Long, Author> {

    private static final AuthorDao INSTANCE = new AuthorDao();

    private static final String FIND_ALL = """
            SELECT *
                        FROM authors
                        ORDER BY first_name
            """;

    private static final String FIND_BY_ID = """
             SELECT *
                        FROM authors
                        WHERE id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM authors
                        WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
                  UPDATE authors
                                        SET first_name = ?,
                                            last_name = ?,
                                            phone = ?,
                                            gmail = ?,
                                        WHERE id = ?
            """;

    private static final String SAVE_SQL = """
             INSERT INTO authors ( first_name, last_name, phone, gmail)
             VALUES (?, ?, ?, ?)
            """;

    private AuthorDao() {

    }

    public static AuthorDao getInstance() {
        return INSTANCE;
    }


    @Override
    public List<Author> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Author> authorList = new ArrayList<>();

            while (resultSet.next()){
                authorList.add(buildAuthor(resultSet));
            }
            return authorList;

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public Optional<Author> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Author author = null;
            if (resultSet.next()){
                author = buildAuthor(resultSet);
            }
            return Optional.ofNullable(author);

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)){

            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public void update(Author entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)){

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setBigDecimal(3, entity.getPhone());
            preparedStatement.setString(4, entity.getGmail());

        } catch (SQLException e){
            throw new RuntimeException("Помилка при отриманні всіх рейсів", e);
        }
    }

    @Override
    public Author save(Author entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setBigDecimal(3, entity.getPhone());
            preparedStatement.setString(4, entity.getGmail());

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

    private Author buildAuthor(ResultSet resultSet) throws SQLException {
        return new Author(
                resultSet.getObject("id", Long.class),
                resultSet.getObject("first_name", String.class),
                resultSet.getObject("last_name", String.class),
                resultSet.getObject("phone", BigDecimal.class),
                resultSet.getObject("gmail", String.class)
        );
    }
}
