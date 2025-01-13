package org.isep.eigenflow.kanban;

import java.util.ArrayList;
import java.util.List;

// Board class representing the Kanban board
public class Board {
    private String name;
    private List<Column> columns;

    public Board(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }
}
