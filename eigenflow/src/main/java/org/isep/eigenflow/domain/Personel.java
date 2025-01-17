package org.isep.eigenflow.domain;

import java.util.ArrayList;

public class Personel {
    private int employeeId;
    private String name;
    private String position;
    private String email;
    private String phoneNumber;
    private ArrayList<String> projectHistory;

    public Personel(int employeeId, String name, String position, String email, String phoneNumber) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.projectHistory = new ArrayList<>();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Employee ID: " + employeeId + "\n" +
                "Name: " + name + "\n" +
                "Position: " + position + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phoneNumber + "\n" +
                "Project History: " + projectHistory;
    }
}