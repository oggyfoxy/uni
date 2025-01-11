package org.isep.eigenflow.repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.isep.eigenflow.domain.Task;

public class TaskRepository extends BaseRepository {
    private static final String DB_URL = "jdbc:sqlite:eigenflow.db";

    public TaskRepository() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        // Tasks table
        String tasksSQL = """
            CREATE TABLE IF NOT EXISTS tasks (
                uuid TEXT PRIMARY KEY,
                title TEXT NOT NULL,
                description TEXT,
                status TEXT,
                assignedMembers TEXT
            )
            """;

        // Project-Tasks join table
        String projectTasksSQL = """
            CREATE TABLE IF NOT EXISTS project_tasks (
                project_id INTEGER,
                task_uuid TEXT,
                FOREIGN KEY (project_id) REFERENCES projects(id),
                FOREIGN KEY (task_uuid) REFERENCES tasks(uuid),
                PRIMARY KEY (project_id, task_uuid)
            )
            """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(tasksSQL);
            stmt.execute(projectTasksSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void save(Task task) {
        String sql = "INSERT INTO tasks (uuid, title, description, assignedMembers, status, completed) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getUuid().toString());
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());
            pstmt.setString(4, task.getAssignedMembersAsString());
            pstmt.setString(5, task.getStatus());
            pstmt.setBoolean(6, task.isCompleted());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Task findByUUID(String uuid) {
        String sql = "SELECT * FROM tasks WHERE uuid = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uuid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Task task = new Task(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setAssignedMembersFromString(rs.getString("assignedMembers"));
                task.setStatus(rs.getString("status"));
                task.setCompleted(rs.getBoolean("completed"));
                return task;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET title = ?, description = ?, assignedMembers = ?, status = ?, completed = ? WHERE uuid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getAssignedMembersAsString());
            pstmt.setString(4, task.getStatus());
            pstmt.setBoolean(5, task.isCompleted());
            pstmt.setString(6, task.getUuid().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(String uuid) throws SQLException {
        String sql = "DELETE FROM tasks WHERE uuid = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uuid);
            pstmt.executeUpdate();
        }
    }

    public List<Task> getTasksByAssignee(String memberName) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE assignedMembers LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // use LIKE with wildcards to match member in comma-separated list
            pstmt.setString(1, "%" + memberName + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("assignedMembers"),
                        UUID.fromString(rs.getString("uuid"))
                );
                task.setCompleted(rs.getBoolean("completed"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Retrieves all tasks from the database.
     * @return A list of all tasks.
     */
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Task task = new Task(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("assignedMembers"),
                        UUID.fromString(rs.getString("uuid"))
                );
                task.setCompleted(rs.getBoolean("completed"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    public List<Task> getTasksByProject(int projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = """
            SELECT t.* FROM tasks t 
            JOIN project_tasks pt ON t.uuid = pt.task_uuid 
            WHERE pt.project_id = ?
            """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setUuid(UUID.fromString(rs.getString("uuid")));
                task.setStatus(rs.getString("status"));
                String members = rs.getString("assignedMembers");
                if (members != null && !members.isEmpty()) {
                    Arrays.stream(members.split(","))
                            .forEach(task::assignMember);
                }
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void addTaskToProject(UUID taskId, int projectId) {
        String sql = "INSERT INTO project_tasks (project_id, task_uuid) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.setString(2, taskId.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTaskStatus(UUID taskId, String newStatus) {
        String sql = "UPDATE tasks SET status = ? WHERE uuid = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, taskId.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}