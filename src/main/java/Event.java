public class Event extends Task {
    private final String startTime;
    private final String endTime;

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Override
    public String taskToString() {
        return String.format("E | %d | %s | %s | %s", isDone ? 1 : 0, description, startTime, endTime);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startTime + " to: " + this.endTime + ")";
    }
}
