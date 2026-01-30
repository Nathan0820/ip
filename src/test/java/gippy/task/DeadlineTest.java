package gippy.task;

import gippy.exception.GippyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeadlineTest {
    @Test
    public void test_constructor_exception() {
        try {
            new Deadline("read book", "invalid date");
            fail();
        } catch (GippyException e) {
            assertEquals("Sorry, I don't recognise this pattern. Use the following format: deadline task_name /by yyyy-MM-dd", e.getMessage());
        }
    }

    @Test
    public void toString_test() throws GippyException {
        Deadline deadline = new Deadline("read book", "2026-01-31");
        String expected = "[D][ ] read book (by: 31/01/2026)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    public void taskToString_test() throws GippyException {
        Deadline deadline = new Deadline("read book","2026-01-31");
        String expected = "D | 0 | read book | 2026-01-31";
        assertEquals(expected, deadline.taskToString());
    }
}
