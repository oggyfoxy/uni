package org.isep.eigenflow.repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.isep.eigenflow.domain.Personel;

public class PersonnelRepository {
    private static final String DB_URL = "jdbc:sqlite:eigenflow.db";


    private int currentId = 1;

    public int getNextId() {
        return currentId++;
    }

    public PersonnelRepository() {
        initializeDatabase();  // add this
    }

    public void initializeDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS personnel (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                position TEXT,
                email TEXT,
                phone TEXT
            )""";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPersonnel(Personel person) {
        String sql = "INSERT INTO personnel (name, position, email, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, person.getName());
            pstmt.setString(2, person.getPosition());
            pstmt.setString(3, person.getEmail());
            pstmt.setString(4, person.getPhoneNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Personel> getAllPersonnel() {
        List<Personel> personnel = new ArrayList<>();
        String sql = "SELECT * FROM personnel";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Personel p = new Personel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                personnel.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnel;
    }

}