package com.github.nutscoding.dmdev.project.dao;

import com.github.nutscoding.dmdev.project.entity.UserDetails;
import com.github.nutscoding.dmdev.project.exception.DaoException;
import com.github.nutscoding.dmdev.project.interfaces.Dao;
import com.github.nutscoding.dmdev.project.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDetailsDao implements Dao<Integer, UserDetails> {
    private static final UserDetailsDao INSTANCE = new UserDetailsDao();

    private static final String SAVE_SQL = """
            INSERT INTO user_details(first_name, last_name, email, age, gender)
            VALUES
            (?, ?, ?, ?, ?)
            """;
    private static final String DELETE_SQL = """
            DELETE FROM user_details
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE user_details
            SET 
            first_name = ?,
            last_name = ?,
            email = ?,
            age = ?,
            gender = ?
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT
            id,
            first_name,
            last_name,
            email,
            age,
            gender
            FROM user_details
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private UserDetailsDao() {
    }

    @Override
    public UserDetails save(UserDetails userDetails) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, userDetails.getFirstName());
            preparedStatement.setString(2, userDetails.getLastName());
            preparedStatement.setString(3, userDetails.getEmail());
            preparedStatement.setInt(4, userDetails.getAge());
            preparedStatement.setString(5, userDetails.getGender());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userDetails.setId(generatedKeys.getInt("id"));
            }
            return userDetails;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void update(UserDetails userDetails) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, userDetails.getFirstName());
            preparedStatement.setString(2, userDetails.getLastName());
            preparedStatement.setString(3, userDetails.getEmail());
            preparedStatement.setInt(4, userDetails.getAge());
            preparedStatement.setString(5, userDetails.getGender());
            preparedStatement.setInt(6, userDetails.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<UserDetails> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserDetails> userDetailsList = new ArrayList<>();
            while (resultSet.next()) {
                userDetailsList.add(buildUserDetails(resultSet));
            }
            return userDetailsList;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<UserDetails> findById(Integer id) {
        try (Connection connection = ConnectionManager.get()){
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<UserDetails> findById(Integer id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            UserDetails userDetails = null;
            if (resultSet.next()) {
                userDetails = buildUserDetails(resultSet);
            }
            return Optional.ofNullable(userDetails);

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

        private UserDetails buildUserDetails(ResultSet resultSet) throws SQLException {
        return new UserDetails(
                resultSet.getInt("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("email"),
                resultSet.getInt("age"),
                resultSet.getString("gender")
        );
    }

    public static UserDetailsDao getInstance() {
        return INSTANCE;
    }
}
