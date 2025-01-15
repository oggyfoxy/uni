package org.isep.eigenflow.repo;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.domain.Expense;

public class ProjectRepository extends BaseRepository {
    private static final String DB_URL = "jdbc:sqlite:eigenflow.db";

    public ProjectRepository() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        String[] sqlStatements = {
                // existing table creation
                "CREATE TABLE IF NOT EXISTS projects (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "deadline DATE," +
                        "members TEXT," +
                        "tasks TEXT," +
                        "status TEXT DEFAULT 'ACTIVE'" +
                        ")",

                // add expenses table
                """
                CREATE TABLE IF NOT EXISTS expenses (
                    id INTEGER PRIMARY KEY,
                    project_id INTEGER,
                    description TEXT,
                    amount REAL,
                    category TEXT,
                    date TEXT,
                    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
                )
                """,



                // add status column if not exists (will fail silently if column exists)
                "ALTER TABLE projects ADD COLUMN status TEXT DEFAULT 'ACTIVE'",

                // update null statuses
                "UPDATE projects SET status = 'ACTIVE' WHERE status IS NULL"
        };

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            for (String sql : sqlStatements) {
                try {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    // ignore column already exists error
                    if (!e.getMessage().contains("duplicate column name")) {
                        throw e;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void unarchiveProject(int projectId) {
        updateProjectStatus(projectId, "ACTIVE");
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

    // in ProjectRepository.java

    public List<Project> getActiveProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE status = 'ACTIVE' OR status IS NULL";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Project project = new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("deadline"))
                );

                // load members from comma-separated string
                String membersStr = rs.getString("members");
                if (membersStr != null && !membersStr.isEmpty()) {
                    for (String member : membersStr.split(",")) {
                        project.addMember(member);
                    }
                }

                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public List<Project> getArchivedProjects() {
        // same code but with WHERE status = 'ARCHIVED'
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE status = 'ARCHIVED'";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Project project = new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("deadline"))
                );

                // load members
                String membersStr = rs.getString("members");
                if (membersStr != null && !membersStr.isEmpty()) {
                    for (String member : membersStr.split(",")) {
                        project.addMember(member);
                    }
                }

                project.setStatus("ARCHIVED");
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    private List<Project> getAllProjectsByStatus(String status) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE status = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Project project = new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("deadline"))
                );
                project.setStatus(rs.getString("status"));
                // set other fields...
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
    
    public void updateProject(Project project) {
        String sql = """
        UPDATE projects 
        SET name = ?, 
            deadline = ?, 
            members = ?,
            status = ?
        WHERE id = ?
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getDeadline().toString());
            pstmt.setString(3, project.getMembersAsString());
            pstmt.setString(4, project.getStatus());
            pstmt.setInt(5, project.getId());

            int updated = pstmt.executeUpdate();
            if (updated == 0) {
                throw new SQLException("Failed to update project - not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update project: " + e.getMessage());
        }
    }

    private void updateProjectStatus(int projectId, String status) {
        String sql = "UPDATE projects SET status = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, projectId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update project status: " + e.getMessage());
        }
    }

    public List<Expense> getExpensesForProject(int projectId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE project_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        LocalDate.parse(rs.getString("date"), DateTimeFormatter.ISO_DATE)
                );
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }


    public void addExpense(int projectId, Expense expense) {
        String sql = "INSERT INTO expenses (project_id, description, amount, category, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.setString(2, expense.description());
            pstmt.setDouble(3, expense.amount());
            pstmt.setString(4, expense.category());
            pstmt.setString(5, expense.date().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
