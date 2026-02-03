package edu.aitu.oop3.db.repository;

import edu.aitu.oop3.db.db.IDatabase;
import edu.aitu.oop3.db.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepositoryJdbc implements CommentRepository {

    private final IDatabase database;

    public CommentRepositoryJdbc(IDatabase database) {
        this.database = database;
    }

    @Override
    public Comment save(Comment comment) {
        String sql = """
            insert into comments (task_id, user_id, text, created_at)
            values (?, ?, ?, now())
            returning id, created_at
        """;

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, comment.getTaskId());
            ps.setLong(2, comment.getUserId());
            ps.setString(3, comment.getText());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    comment.setId(rs.getLong("id"));
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    comment.setCreatedAt(createdAt == null ? null : createdAt.toLocalDateTime());
                }
            }

            return comment;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Comment> findById(Long id) {
        String sql = "select * from comments where id = ?";

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comment> findAll() {
        String sql = "select * from comments order by id";
        List<Comment> comments = new ArrayList<>();

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                comments.add(mapRow(rs));
            }
            return comments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comment> findByTaskId(Long taskId) {
        String sql = "select * from comments where task_id = ? order by id";
        List<Comment> comments = new ArrayList<>();

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, taskId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    comments.add(mapRow(rs));
                }
            }

            return comments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Comment mapRow(ResultSet rs) throws SQLException {
        Timestamp createdAt = rs.getTimestamp("created_at");

        return new Comment(
                rs.getLong("id"),
                rs.getLong("task_id"),
                rs.getLong("user_id"),
                rs.getString("text"),
                createdAt == null ? null : createdAt.toLocalDateTime()
        );
    }
}