package gippy.task;

import gippy.exception.GippyException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalDate deadline;

    public Deadline(String description, String deadline) throws GippyException {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            this.deadline = LocalDate.parse(deadline, formatter);
        } catch (DateTimeParseException e) {
            try {
                this.deadline = LocalDate.parse(deadline);
            } catch (DateTimeParseException e1) {
                throw new GippyException("Sorry, I don't recognise this pattern. Use the following format: deadline task_name /by yyyy-MM-dd");
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
}
