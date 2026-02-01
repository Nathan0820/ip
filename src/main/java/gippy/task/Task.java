package gippy.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Get the icon so that user knows which task is done
     * @return status icon
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Mark the task as done
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Mark the task as undone
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Transform the string to be stored into the storage file
     * @return transformed string
     */
    public abstract String taskToString();

    /**
     * Returns formatted string to be seen by user
     * @return formatted string
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
