package gippy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import gippy.exception.GippyException;

public class EventTest {
    @Test
    public void test_constructor_exception() {
        try {
            new Event("read book", "invalid date", "invalid date");
            fail();
        } catch (GippyException e) {
            assertEquals(
                    "Sorry, I don't recognise this pattern. "
                            + "Use the following format: event task_name /from yyyy-MM-dd /to yyyy-MM-dd",
                    e.getMessage());
        }
    }

    @Test
    public void toString_test() throws GippyException {
        Event deadline = new Event("read book", "2026-01-31", "2026-02-12");
        String expected = "[E][ ] read book (from: 31/01/2026 to: 12/02/2026)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    public void taskToString_test() throws GippyException {
        Event deadline = new Event("read book", "2026-01-31", "2026-02-12");
        String expected = "E | 0 | read book | 2026-01-31 | 2026-02-12";
        assertEquals(expected, deadline.taskToString());
    }
}
