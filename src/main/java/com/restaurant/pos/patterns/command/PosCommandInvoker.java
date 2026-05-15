package com.restaurant.pos.patterns.command;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * INVOKER: Executes commands and maintains an undo history stack.
 * Scoped as a Spring prototype (or used statelessly per request).
 */
@Component
public class PosCommandInvoker {

    private final Deque<PosCommand> history = new ArrayDeque<>();

    public void execute(PosCommand command) {
        command.execute();
        history.push(command);
        System.out.println("[COMMAND] Executed: " + command.getDescription());
    }

    public void undo() {
        if (!history.isEmpty()) {
            PosCommand last = history.pop();
            last.undo();
            System.out.println("[COMMAND] Undone: " + last.getDescription());
        }
    }

    public boolean hasHistory() {
        return !history.isEmpty();
    }
}
