package gippy.task;
// Uses Github Copilot to assist in writing test cases for Deadline class.

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import gippy.exception.GippyException;

public class DeadlineTest {
    @Test
    public void constructor_invalidDateFormat_throwsException() {
        try {
            new Deadline("read book", "invalid date");
            fail();
        } catch (GippyException e) {
            assertEquals(
                    "Sorry, I don't recognise this pattern. "
                            + "Use the following format: deadline <description> /by <yyyy-MM-dd>",
                    e.getMessage());
        }
    }

    @Test
    public void constructor_slashDateFormat_throwsException() {
        GippyException exception = assertThrows(GippyException.class, () -> {
            new Deadline("read book", "2024/01/15");
        });
        assertEquals(
                "Sorry, I don't recognise this pattern. "
                        + "Use the following format: deadline <description> /by <yyyy-MM-dd>",
                exception.getMessage());
    }

    @Test
    public void constructor_validDate_createsDeadline() throws GippyException {
        Deadline deadline = new Deadline("read book", "2026-01-31");
        assertEquals("read book", deadline.getDescription());
    }

    @Test
    public void toString_unmarkedDeadline_correctFormat() throws GippyException {
        Deadline deadline = new Deadline("read book", "2026-01-31");
        String expected = "[D][ ] read book (by: 31/01/2026)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    public void toString_markedDeadline_showsX() throws GippyException {
        Deadline deadline = new Deadline("read book", "2026-01-31");
        deadline.markDone();
        String expected = "[D][X] read book (by: 31/01/2026)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    public void taskToString_unmarkedDeadline_correctStorageFormat() throws GippyException {
        Deadline deadline = new Deadline("read book", "2026-01-31");
        String expected = "D | 0 | read book | 2026-01-31";
        assertEquals(expected, deadline.taskToString());
    }

    @Test
    public void taskToString_markedDeadline_correctStorageFormat() throws GippyException {
        Deadline deadline = new Deadline("read book", "2026-01-31");
        deadline.markDone();
        String expected = "D | 1 | read book | 2026-01-31";
        assertEquals(expected, deadline.taskToString());
    }

    @Test
    public void getDate_returnsCorrectDate() throws GippyException {
        Deadline deadline = new Deadline("read book", "2026-01-31");
        LocalDate expected = LocalDate.of(2026, 1, 31);
        assertEquals(expected, deadline.getDate());
    }
}
