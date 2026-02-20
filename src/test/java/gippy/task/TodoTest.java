package gippy.task;

// Uses Github Copilot to assist in writing test cases for Todo class.

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class TodoTest {
    @Test
    public void constructor_validDescription_createsTodo() {
        Todo todo = new Todo("read book");
        assertEquals("read book", todo.getDescription());
    }

    @Test
    public void toString_unmarkedTodo_correctFormat() {
        Todo todo = new Todo("read book");
        String expected = "[T][ ] read book";
        assertEquals(expected, todo.toString());
    }

    @Test
    public void toString_markedTodo_showsX() {
        Todo todo = new Todo("read book");
        todo.markDone();
        String expected = "[T][X] read book";
        assertEquals(expected, todo.toString());
    }

    @Test
    public void taskToString_unmarkedTodo_correctStorageFormat() {
        Todo todo = new Todo("read book");
        String expected = "T | 0 | read book";
        assertEquals(expected, todo.taskToString());
    }

    @Test
    public void taskToString_markedTodo_correctStorageFormat() {
        Todo todo = new Todo("read book");
        todo.markDone();
        String expected = "T | 1 | read book";
        assertEquals(expected, todo.taskToString());
    }

    @Test
    public void getDate_returnsNull() {
        Todo todo = new Todo("read book");
        assertNull(todo.getDate());
    }

    @Test
    public void getStatusIcon_unmarked_returnsSpace() {
        Todo todo = new Todo("read book");
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void getStatusIcon_marked_returnsX() {
        Todo todo = new Todo("read book");
        todo.markDone();
        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    public void markDone_thenUnmark_statusChanges() {
        Todo todo = new Todo("read book");
        assertEquals(" ", todo.getStatusIcon());

        todo.markDone();
        assertEquals("X", todo.getStatusIcon());

        todo.markUndone();
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void getDescription_returnsDescription() {
        Todo todo = new Todo("complete assignment");
        assertEquals("complete assignment", todo.getDescription());
    }
}
