package gippy.task;

import gippy.exception.GippyException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Event class representing task with a start and end date.
 */
public class Event extends Task {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private LocalDate startTime;
    private LocalDate endTime;

    /**
     * Constructor for Event
     * @param description Event description
     * @param startTime Start time
     * @param endTime End time
     * @throws GippyException Exception thrown during error
     */
    public Event(String description, String startTime, String endTime) throws GippyException {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            this.startTime = LocalDate.parse(startTime, formatter);
            this.endTime = LocalDate.parse(endTime, formatter);
        } catch (DateTimeParseException e) {
            try {
                this.startTime = LocalDate.parse(startTime);
                this.endTime = LocalDate.parse(endTime);
            } catch (DateTimeParseException e1) {
                throw new GippyException("Sorry, I don't recognise this pattern. "
                        + "Use the following format: event task_name /from yyyy-MM-dd /to yyyy-MM-dd");
            }
        }
    }

    @Override
    public String taskToString() {
        return String.format("E | %d | %s | %s | %s", isDone ? 1 : 0, description, startTime, endTime);
    }

    @Override
    public String toString() {
        String start = formatDate(startTime);
        String end = formatDate(endTime);
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }

    /**
     * Formats the date to be displayed in the GUI
     * @param date Date to be formatted
     * @return Formatted date string
     */
    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public LocalDate getDate() {
        return this.startTime; 
    }
}
