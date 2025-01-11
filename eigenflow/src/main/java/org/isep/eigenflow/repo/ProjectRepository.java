package org.isep.eigenflow.repo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.isep.eigenflow.domain.Project;

public class ProjectRepository extends BaseRepository {
    private static final String DB_URL = "jdbc:sqlite:eigenflow.db";

    public ProjectRepository() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS projects (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                deadline DATE,
                status TEXT DEFAULT 'ACTIVE',
                members TEXT,  -- stored as comma-separated string
                tasks TEXT    -- stored as comma-separated UUIDs
            )
            """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public void save(Project project) {
        String sql = "INSERT INTO projects (name, deadline, status, members) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getDeadline() != null ? project.getDeadline().toString() : null);
            pstmt.setString(3, "ACTIVE");  // default status for new projects
            pstmt.setString(4, String.join(",", project.getMembers()));
            pstmt.executeUpdate();
            int rows = pstmt.executeUpdate();
            System.out.println("Rows inserted: " + rows);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save project", e);
        }
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalDate deadline = rs.getString("deadline") != null ? LocalDate.parse(rs.getString("deadline")) : null;
                Project project = new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        deadline
                );
                // Split members string into list
                String members = rs.getString("members");
                if (members != null && !members.isEmpty()) {
                    for (String member : members.split(",")) {
                        project.addMember(member);
                    }
                }
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve projects", e);
        }
        return projects;
    }

    public void archiveProject(int projectId) {
        String sql = "UPDATE projects SET status = 'ARCHIVED' WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("No project found with ID: " + projectId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to archive project", e);
        }
    }

    public void deleteProject(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("No project found with ID: " + projectId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete project", e);
        }
    }


}
