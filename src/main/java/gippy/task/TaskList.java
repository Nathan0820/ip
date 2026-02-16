package gippy.task;

import java.util.ArrayList;
import java.util.Comparator;

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
     * Adds a task to the task list and sorts by date
     * @param task Task to be added
     */
    public void addTask(Task task) {
        this.tasks.add(task);
        this.sortTask();
    }

    /**
     * Deletes a task from the task list
     * @param task Task to be deleted
     */
    public void deleteTask(Task task) {
        assert this.tasks.contains(task) : "Task not found";
        this.tasks.remove(task);
    }

    /**
     * Gets a task from the task list based on index provided by user
     * @param index Index of the task to be returned
     * @return Task at the specified index
     */
    public Task getTask(int index) {
        assert index >= 0 && index < this.tasks.size() : "Index out of bounds";
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

    /**
     * Finds tasks using the input provided by user
     * @param keyWord Word that is included inside the task description
     * @return A taskList of found tasks
     */
    public TaskList findTasks(String keyWord) {
        TaskList foundTasks = new TaskList();

        for (Task task : this.tasks) {
            if (task.getDescription().contains(keyWord)) {
                foundTasks.addTask(task);
            }
        }

        return foundTasks;
    }

    /**
     * Sorts the task list by date, with tasks without dates at the end
     */
    public void sortTask() {
        this.tasks.sort(Comparator.comparing(Task::getDate,
            Comparator.nullsLast(Comparator.naturalOrder())
        ));
    }
}
