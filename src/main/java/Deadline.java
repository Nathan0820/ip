public class Deadline extends Task {
    private final String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String taskToString() {
        return String.format("D | %d | %s | %s", isDone ? 1 : 0, description, deadline);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}
