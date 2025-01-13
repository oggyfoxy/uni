package org.isep.eigenflow.kanban;

import lombok.Builder;
import lombok.Data;
import java.util.List;

// Column class representing each column in the board (e.g., To Do, In Progress, Done)
@Data
@Builder
public class Column {
    private Long id;
    private String name;
    private int workInProgressLimit;
    private List<Task> tasks;
}
