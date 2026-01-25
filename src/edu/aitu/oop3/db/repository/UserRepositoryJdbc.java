package edu.aitu.oop3.db.repository;

import edu.aitu.oop3.db.db.IDatabase;
import edu.aitu.oop3.db.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbc implements UserRepository {

    private final IDatabase database;

    public UserRepositoryJdbc(IDatabase database) {
        this.database = database;
    }

    @Override
    public User save(User user) {
        String sql = """
            insert into users (name, email, created_at)
            values (?, ?, now())
            returning id, created_at
        """;

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                Timestamp createdAt = rs.getTimestamp("created_at");
                user.setCreatedAt(createdAt == null ? null : createdAt.toLocalDateTime());
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "select * from users where id = ?";

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Timestamp createdAt = rs.getTimestamp("created_at");
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        createdAt == null ? null : createdAt.toLocalDateTime()
                );
                return Optional.of(user);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users order by id";
        List<User> users = new ArrayList<>();

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Timestamp createdAt = rs.getTimestamp("created_at");
                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        createdAt == null ? null : createdAt.toLocalDateTime()
                ));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}