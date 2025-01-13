package org.isep.eigenflow.kanban;

import java.util.HashMap;
import java.util.Map;

// Service for managing boards (GetBoardUseCase implementation)
public class BoardService implements GetBoardUseCase {
    private final Map<String, Board> boards = new HashMap<>();

    @Override
    public Board getBoardByName(String name) {
        return boards.getOrDefault(name, new Board(name));
    }

    public void addBoard(Board board) {
        boards.put(board.getName(), board);
    }
    private final BoardOutputPort boardOutputPort;

    @Override
    public Board getBoardByName(String name) {
        return boardOutputPort.findBoardByName(name).orElseThrow(
            () -> new IllegalArgumentException("Board not found with name: " + name)
        );
    }

    @Override
    public Board getBoardById(Long id) {
        return boardOutputPort.findBoardById(id).orElseThrow(
            () -> new IllegalArgumentException("Board not found with ID: " + id)
        );
    }
}
