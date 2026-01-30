package gippy.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void toStringTest() {
        Todo todo = new Todo("read book");
        String expected = "[T][ ] read book";
        assertEquals(expected, todo.toString());
    }

    @Test
    public void taskToStringTest() {
        Todo todo = new Todo("read book");
        String expected = "T | 0 | read book";
        assertEquals(expected, todo.taskToString());
    }
}
