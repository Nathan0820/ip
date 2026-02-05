package gippy.task;

/**
 * Abstract Task class to be inherited by specific task types
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor for Task
     * @param description Task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the icon so that user knows which task is done
     * @return status icon
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as done
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as undone
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Transforms the string to be stored into the storage file
     * @return transformed string
     */
    public abstract String taskToString();

    /**
     * Gets the description of the task.
     * @return Description of the task
     */
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
