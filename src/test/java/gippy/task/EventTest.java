package gippy.task;
// Uses Github Copilot to assist in writing test cases for Deadline class.

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import gippy.exception.GippyException;

public class EventTest {
    @Test
    public void constructor_invalidDateFormat_throwsException() {
        try {
            new Event("read book", "invalid date", "invalid date");
            fail();
        } catch (GippyException e) {
            assertEquals(
                    "Sorry, I don't recognise this date pattern. "
                            + "Use the following format: event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>",
                    e.getMessage());
        }
    }

    @Test
    public void constructor_startDateAfterEndDate_throwsException() {
        GippyException exception = assertThrows(GippyException.class, () -> {
            new Event("conference", "2026-03-15", "2026-03-10");
        });
        assertEquals(
                "Start date cannot be after end date! "
                        + "Please ensure the /from date is before or equal to the /to date.",
                exception.getMessage());
    }

    @Test
    public void constructor_sameDates_succeeds() throws GippyException {
        Event event = new Event("meeting", "2026-03-15", "2026-03-15");
        assertEquals("[E][ ] meeting (from: 15/03/2026 to: 15/03/2026)", event.toString());
    }

    @Test
    public void constructor_validDates_createsEvent() throws GippyException {
        Event event = new Event("conference", "2026-01-31", "2026-02-12");
        assertEquals("conference", event.getDescription());
    }

    @Test
    public void toString_unmarkedEvent_correctFormat() throws GippyException {
        Event event = new Event("read book", "2026-01-31", "2026-02-12");
        String expected = "[E][ ] read book (from: 31/01/2026 to: 12/02/2026)";
        assertEquals(expected, event.toString());
    }

    @Test
    public void toString_markedEvent_showsX() throws GippyException {
        Event event = new Event("read book", "2026-01-31", "2026-02-12");
        event.markDone();
        String expected = "[E][X] read book (from: 31/01/2026 to: 12/02/2026)";
        assertEquals(expected, event.toString());
    }

    @Test
    public void taskToString_unmarkedEvent_correctStorageFormat() throws GippyException {
        Event event = new Event("read book", "2026-01-31", "2026-02-12");
        String expected = "E | 0 | read book | 2026-01-31 | 2026-02-12";
        assertEquals(expected, event.taskToString());
    }

    @Test
    public void taskToString_markedEvent_correctStorageFormat() throws GippyException {
        Event event = new Event("read book", "2026-01-31", "2026-02-12");
        event.markDone();
        String expected = "E | 1 | read book | 2026-01-31 | 2026-02-12";
        assertEquals(expected, event.taskToString());
    }

    @Test
    public void getDate_returnsStartDate() throws GippyException {
        Event event = new Event("conference", "2026-01-31", "2026-02-12");
        LocalDate expected = LocalDate.of(2026, 1, 31);
        assertEquals(expected, event.getDate());
    }
}
