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

   
    public void editPersonel(String name, String position, String email, String phoneNumber) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

   
    public void deletePersonel() {
        this.name = null;
        this.position = null;
        this.email = null;
        this.phoneNumber = null;
        this.projectHistory.clear();
    }

    public void addProjectToHistory(String projectName) {
        this.projectHistory.add(projectName);
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
