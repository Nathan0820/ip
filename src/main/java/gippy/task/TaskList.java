package gippy.task;

import java.util.ArrayList;

/**
 * TaskList class to manage a list of tasks using an ArrayList.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list
     * @param task Task to be added
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Deletes a task from the task list
     * @param task Task to be deleted
     */
    public void deleteTask(Task task) {
        this.tasks.remove(task);
    }

    /**
     * Gets a task from the task list based on index provided by user
     * @param index Index of the task to be returned
     * @return Task at the specified index
     */
    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Checks if the task list is empty
     * @return true if the task list is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    /**
     * Gets the number of tasks in the task list
     * @return Number of tasks in the task list
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Gets all tasks in the task list
     * @return ArrayList of all tasks
     */
    public ArrayList<Task> getAllTasks() {
        return this.tasks;
    }
}
