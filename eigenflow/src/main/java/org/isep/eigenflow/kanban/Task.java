package org.isep.eigenflow.kanban;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

// Task class representing a task in a column
@Data
@Builder
public class Task {
    private Long id;
    private String title;
    private String description;
    @Builder.Default private TaskStatus status = TaskStatus.TODO;
    @Builder.Default private LocalDateTime createdAt = LocalDateTime.now();
    private Long position;
}
