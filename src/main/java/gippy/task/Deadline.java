package gippy.task;

import gippy.exception.GippyException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Deadline class representing task with a due date.
 */
public class Deadline extends Task {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private LocalDate deadline;

    /**
     * Constructor for Deadline
     * @param description Deadline description
     * @param deadline Deadline date
     * @throws GippyException Exception thrown
     */
    public Deadline(String description, String deadline) throws GippyException {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            this.deadline = LocalDate.parse(deadline, formatter);
        } catch (DateTimeParseException e) {
            try {
                this.deadline = LocalDate.parse(deadline);
            } catch (DateTimeParseException e1) {
                throw new GippyException("Sorry, I don't recognise this pattern. "
                        + "Use the following format: deadline task_name /by yyyy-MM-dd");
            }
        }
    }

    @Override
    public String taskToString() {
        return String.format("D | %d | %s | %s", isDone ? 1 : 0, description, deadline);
    }

    @Override
    public String toString() {
        String date = deadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "[D]" + super.toString() + " (by: " + date + ")";
    }

    @Override
    public LocalDate getDate() {
        return this.deadline;
    }
}
