package edu.aitu.oop3.db.repository;

import edu.aitu.oop3.db.db.IDatabase;
import edu.aitu.oop3.db.entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryJdbc implements ProjectRepository {

    private final IDatabase database;

    public ProjectRepositoryJdbc(IDatabase database) {
        this.database = database;
    }

    @Override
    public Project save(Project project) {
        String sql = """
            insert into projects (title, description, owner_id, created_at)
            values (?, ?, ?, now())
            returning id, created_at
        """;

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, project.getTitle());
            ps.setString(2, project.getDescription());
            ps.setLong(3, project.getOwnerId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                project.setId(rs.getLong("id"));
                Timestamp createdAt = rs.getTimestamp("created_at");
                project.setCreatedAt(createdAt == null ? null : createdAt.toLocalDateTime());
            }
            return project;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Project> findById(Long id) {
        String sql = "select * from projects where id = ?";

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Timestamp createdAt = rs.getTimestamp("created_at");
                Project project = new Project(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("owner_id"),
                        createdAt == null ? null : createdAt.toLocalDateTime()
                );
                return Optional.of(project);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Project> findAll() {
        String sql = "select * from projects order by id";
        List<Project> projects = new ArrayList<>();

        try (Connection c = database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Timestamp createdAt = rs.getTimestamp("created_at");
                projects.add(new Project(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("owner_id"),
                        createdAt == null ? null : createdAt.toLocalDateTime()
                ));
            }
            return projects;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}