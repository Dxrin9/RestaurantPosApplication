package com.restaurant.pos.patterns.command;

/**
 * ============================================================
 * DESIGN PATTERN #7: COMMAND
 * ============================================================
 * Encapsulates a request as an object, enabling:
 * - Parameterization of actions
 * - Undo/Redo support (with Memento pattern for state)
 * - Logging and queuing of operations
 *
 * Commands: AddItemCommand, RemoveItemCommand, SendOrderCommand,
 *           PayOrderCommand, VoidItemCommand
 */
public interface PosCommand {
    void execute();
    void undo();
    String getDescription();
}
