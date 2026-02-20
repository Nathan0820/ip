package gippy.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void getCommand_singleWordCommand_returnsCommand() {
        assertEquals("list", Parser.getCommand("list"));
        assertEquals("bye", Parser.getCommand("bye"));
    }
    @Test
    public void getCommand_commandWithArguments_returnsFirstWord() {
        assertEquals("todo", Parser.getCommand("todo read book"));
        assertEquals("deadline", Parser.getCommand("deadline task /by 2026-01-01"));
        assertEquals("event", Parser.getCommand("event meeting /from 2026-01-01 /to 2026-01-02"));
        assertEquals("mark", Parser.getCommand("mark 1"));
        assertEquals("unmark", Parser.getCommand("unmark 2"));
        assertEquals("delete", Parser.getCommand("delete 3"));
        assertEquals("find", Parser.getCommand("find book"));
    }
    @Test
    public void getCommand_mixedCaseInput_returnsLowercase() {
        assertEquals("todo", Parser.getCommand("ToDo read book"));
        assertEquals("list", Parser.getCommand("LiST"));
        assertEquals("deadline", Parser.getCommand("DEADLINE task /by 2026-01-01"));
        assertEquals("mark", Parser.getCommand("MaRk 1"));
    }
    @Test
    public void getCommand_inputWithExtraSpaces_returnsCommand() {
        assertEquals("todo", Parser.getCommand("todo   read   book"));
    }
}
