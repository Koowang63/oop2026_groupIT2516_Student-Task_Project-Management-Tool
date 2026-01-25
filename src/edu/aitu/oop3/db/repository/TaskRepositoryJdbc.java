package edu.aitu.oop3.db.repository;

import edu.aitu.oop3.db.db.IDatabase;
import edu.aitu.oop3.db.entity.Task;
import edu.aitu.oop3.db.entity.TaskStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepositoryJdbc implements TaskRepository {

    private final IDatabase database;

    public TaskRepositoryJdbc(IDatabase database) {
        this.database = database;
    }

    @Override
    public Task save(Task task) {
        String sql = """
            insert into tasks (project_id, title, status, deadline)
            values (?, ?, ?, ?)
            returning id
        """;

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, task.getProjectId());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getStatus().name());
            ps.setTimestamp(4,
                    task.getDeadline() == null ? null :
                            Timestamp.valueOf(task.getDeadline()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                task.setId(rs.getLong("id"));
            }
            return task;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        String sql = "select * from tasks where id = ?";

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Task task = new Task(
                        rs.getLong("id"),
                        rs.getLong("project_id"),
                        rs.getString("title"),
                        TaskStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("deadline") == null
                                ? null
                                : rs.getTimestamp("deadline").toLocalDateTime()
                );
                return Optional.of(task);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "select * from tasks order by id";

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getLong("id"),
                        rs.getLong("project_id"),
                        rs.getString("title"),
                        TaskStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("deadline") == null
                                ? null
                                : rs.getTimestamp("deadline").toLocalDateTime()
                ));
            }
            return tasks;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStatus(Long taskId, String status) {
        String sql = "update tasks set status = ? where id = ?";

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setLong(2, taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}