package gippy.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void deleteTask(Task task) {
        this.tasks.remove(task);
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    public int size() {
        return this.tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return this.tasks;
    }

    public TaskList findTasks(String keyWord) {
        TaskList foundTasks = new TaskList();

        for (Task task : this.tasks) {
            if (task.getDescription().contains(keyWord)) {
                foundTasks.addTask(task);
            }
        }

        return foundTasks;
    }
}
