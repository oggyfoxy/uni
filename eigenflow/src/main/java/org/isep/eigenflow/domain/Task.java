package org.isep.eigenflow.domain;

import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private boolean completed;
    private UUID uuid;

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

   public String getTitle() {
        return title;
   }

   public String getDescription() {
        return description;
   }

   public boolean isCompleted() {
        return completed;
   }

   public UUID getUuid() {
        return uuid;
   }

   public void setTitle(String title) {
        this.title = title;
   }

   public void setDescription(String description) {
        this.description = description;
   }

   public void setCompleted(boolean completed) {
        this.completed = completed;

   }

   public void setUuid(UUID uuid) {
        this.uuid = uuid;
   }

}