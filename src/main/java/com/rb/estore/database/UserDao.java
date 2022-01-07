package com.rb.estore.database;

import com.rb.estore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class UserDao implements InterfaceUserDao {

    @Autowired
    Connection connection;

    @Override
    public void addUser(User user) {
        try {
            String sql = "INSERT INTO users VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname((resultSet.getString("surname")));
                user.setLogin((resultSet.getString("login")));
                user.setPassword(resultSet.getString("password"));

                return Optional.of(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
