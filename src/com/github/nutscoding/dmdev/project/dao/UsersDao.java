package com.github.nutscoding.dmdev.project.dao;

import com.github.nutscoding.dmdev.project.entity.Users;
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

public class UsersDao implements Dao<Integer, Users> {

    private static final UsersDao INSTANCE = new UsersDao();
    private static final String SAVE_SQL = """
            INSERT INTO users (login, password, user_type_id, user_details_id) 
            VALUES 
            (?, ?, ?, ?) 
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET 
            login = ?,
            password = ?,
            user_type_id = ?,
            user_details_id = ?
            WHERE id = ?
            """;
    public static final String FIND_ALL_SQL = """
            SELECT 
            id,
            login,
            password,
            user_type_id,
            user_details_id
            FROM users
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private final UserDetailsDao userDetailsDao = UserDetailsDao.getInstance();

    public UsersDao() {
    }

    @Override
    public Users save(Users users) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, users.getLogin());
            preparedStatement.setString(2, users.getPassword());
            preparedStatement.setInt(3, users.getUserTypeId());
            preparedStatement.setInt(4, users.getUserDetails().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                users.setId(generatedKeys.getInt("id"));
            }
            return users;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public void update(Users users) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, users.getLogin());
            preparedStatement.setString(2, users.getPassword());
            preparedStatement.setInt(3, users.getUserTypeId());
            preparedStatement.setInt(4, users.getUserDetails().getId());
            preparedStatement.setInt(5, users.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<Users> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Users> usersList = new ArrayList<>();

            while (resultSet.next()) {
                usersList.add(buildUsers(resultSet));
            }
            return usersList;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public Optional<Users> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Users users = null;
            while (resultSet.next()) {
                users = buildUsers(resultSet);
            }
            return Optional.ofNullable(users);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private Users buildUsers(ResultSet resultSet) throws SQLException {
        return new Users(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getInt("user_type"),
                userDetailsDao.findById(resultSet.getInt("user_details_id"),
                        resultSet.getStatement().getConnection()).orElse(null)
        );
    }

    public static UsersDao getInstance() {
        return INSTANCE;
    }
}
